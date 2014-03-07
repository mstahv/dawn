package com.vaadin.theme.valo;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.server.VaadinServlet;
import com.vaadin.util.CurrentInstance;

public class ThemeServlet extends VaadinServlet {
    /**
     * Mutex for preventing to scss compilations to take place simultaneously.
     * This is a workaround needed as the scss compiler currently is not thread
     * safe (#10292).
     */
    private static final Object SCSS_MUTEX = new Object();

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (isStaticResourceRequest(request)) {
            // Define current servlet and service, but no request and response
            getService().setCurrentInstances(null, null);
            try {
                serveStaticResources(request, response);
                return;
            } finally {
                CurrentInstance.clearAll();
            }
        }
        super.service(request, response);
    }

    private boolean serveStaticResources(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return false;
        }

        if ((request.getContextPath() != null)
                && (request.getRequestURI().startsWith("/VAADIN/"))) {
            serveStaticResourcesInVAADIN(request.getRequestURI(), request,
                    response);
            return true;
        } else if (request.getRequestURI().startsWith(
                request.getContextPath() + "/VAADIN/")) {
            serveStaticResourcesInVAADIN(
                    request.getRequestURI().substring(
                            request.getContextPath().length()), request,
                    response);
            return true;
        }

        return false;
    }

    private URL findResourceURL(String filename, ServletContext sc)
            throws MalformedURLException {
        URL resourceUrl = sc.getResource(filename);
        if (resourceUrl == null) {
            // try if requested file is found from classloader

            // strip leading "/" otherwise stream from JAR wont work
            if (filename.startsWith("/")) {
                filename = filename.substring(1);
            }

            resourceUrl = getService().getClassLoader().getResource(filename);
        }
        return resourceUrl;
    }

    private void serveStaticResourcesInVAADIN(String filename,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        final ServletContext sc = getServletContext();
        URL resourceUrl = findResourceURL(filename, sc);

        if (resourceUrl == null) {
            // File not found, if this was a css request we still look for a
            // scss file with the same name
            if (serveOnTheFlyCompiledScss(filename, request, response, sc)) {
                return;
            } else {
                // cannot serve requested file
                getLogger()
                        .log(Level.INFO,
                                "Requested resource [{0}] not found from filesystem or through class loader."
                                        + " Add widgetset and/or theme JAR to your classpath or add files to WebContent/VAADIN folder.",
                                filename);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            return;
        }

        // security check: do not permit navigation out of the VAADIN
        // directory
        if (!isAllowedVAADINResourceUrl(request, resourceUrl)) {
            getLogger()
                    .log(Level.INFO,
                            "Requested resource [{0}] not accessible in the VAADIN directory or access to it is forbidden.",
                            filename);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        // Find the modification timestamp
        long lastModifiedTime = 0;
        URLConnection connection = null;
        try {
            connection = resourceUrl.openConnection();
            lastModifiedTime = connection.getLastModified();
            // Remove milliseconds to avoid comparison problems
            // (milliseconds
            // are not returned by the browser in the "If-Modified-Since"
            // header).
            lastModifiedTime = lastModifiedTime - lastModifiedTime % 1000;

            if (browserHasNewestVersion(request, lastModifiedTime)) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
        } catch (Exception e) {
            // Failed to find out last modified timestamp. Continue without
            // it.
            getLogger()
                    .log(Level.FINEST,
                            "Failed to find out last modified timestamp. Continuing without it.",
                            e);
        } finally {
            try {
                // Explicitly close the input stream to prevent it
                // from remaining hanging
                // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4257700
                InputStream is = connection.getInputStream();
                if (is != null) {
                    is.close();
                }
            } catch (FileNotFoundException e) {
                // Not logging when the file does not exist.
            } catch (IOException e) {
                getLogger().log(Level.INFO,
                        "Error closing URLConnection input stream", e);
            }
        }

        // Set type mime type if we can determine it based on the filename
        final String mimetype = sc.getMimeType(filename);
        if (mimetype != null) {
            response.setContentType(mimetype);
        }

        // Provide modification timestamp to the browser if it is known.
        if (lastModifiedTime > 0) {
            response.setDateHeader("Last-Modified", lastModifiedTime);
            /*
             * The browser is allowed to cache for 1 hour without checking if
             * the file has changed. This forces browsers to fetch a new version
             * when the Vaadin version is updated. This will cause more requests
             * to the servlet than without this but for high volume sites the
             * static files should never be served through the servlet. The
             * cache timeout can be configured by setting the resourceCacheTime
             * parameter in web.xml
             */
            int resourceCacheTime = getService().getDeploymentConfiguration()
                    .getResourceCacheTime();
            String cacheControl = "max-age="
                    + String.valueOf(resourceCacheTime);
            if (filename.contains("nocache")) {
                cacheControl = "public, max-age=0, must-revalidate";
            }
            response.setHeader("Cache-Control", cacheControl);
        }

        writeStaticResourceResponse(request, response, resourceUrl);
    }

    /**
     * Checks if the browser has an up to date cached version of requested
     * resource. Currently the check is performed using the "If-Modified-Since"
     * header. Could be expanded if needed.
     * 
     * @param request
     *            The HttpServletRequest from the browser.
     * @param resourceLastModifiedTimestamp
     *            The timestamp when the resource was last modified. 0 if the
     *            last modification time is unknown.
     * @return true if the If-Modified-Since header tells the cached version in
     *         the browser is up to date, false otherwise
     */
    private boolean browserHasNewestVersion(HttpServletRequest request,
            long resourceLastModifiedTimestamp) {
        if (resourceLastModifiedTimestamp < 1) {
            // We do not know when it was modified so the browser cannot
            // have an
            // up-to-date version
            return false;
        }
        /*
         * The browser can request the resource conditionally using an
         * If-Modified-Since header. Check this against the last modification
         * time.
         */
        try {
            // If-Modified-Since represents the timestamp of the version
            // cached
            // in the browser
            long headerIfModifiedSince = request
                    .getDateHeader("If-Modified-Since");

            if (headerIfModifiedSince >= resourceLastModifiedTimestamp) {
                // Browser has this an up-to-date version of the resource
                return true;
            }
        } catch (Exception e) {
            // Failed to parse header. Fail silently - the browser does not
            // have
            // an up-to-date version in its cache.
        }
        return false;
    }

    private boolean serveOnTheFlyCompiledScss(String filename,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext sc) throws IOException {
        if (!filename.endsWith(".css")) {
            return false;
        }

        String scssFilename = filename.substring(0, filename.length() - 4)
                + ".scss";
        URL scssUrl = findResourceURL(scssFilename, sc);
        if (scssUrl == null) {
            // Is a css request but no scss file was found
            return false;
        }
        // security check: do not permit navigation out of the VAADIN
        // directory
        if (!isAllowedVAADINResourceUrl(request, scssUrl)) {
            getLogger()
                    .log(Level.INFO,
                            "Requested resource [{0}] not accessible in the VAADIN directory or access to it is forbidden.",
                            filename);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // Handled, return true so no further processing is done
            return true;
        }
        if (getService().getDeploymentConfiguration().isProductionMode()) {
            // This is not meant for production mode.
            getLogger()
                    .log(Level.INFO,
                            "Request for {0} not handled by sass compiler while in production mode",
                            filename);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // Handled, return true so no further processing is done
            return true;
        }

        // synchronized (SCSS_MUTEX) {
        String realFilename = sc.getRealPath(scssFilename);
        // ScssStylesheet scss = ScssStylesheet.get(realFilename);
        // if (scss == null) {
        // // Not a file in the file system (WebContent directory). Use
        // // the
        // // identifier directly (VAADIN/themes/.../styles.css) so
        // // ScssStylesheet will try using the class loader.
        // if (scssFilename.startsWith("/")) {
        // scssFilename = scssFilename.substring(1);
        // }
        //
        // scss = ScssStylesheet.get(scssFilename);
        // }
        //
        // if (scss == null) {
        // getLogger()
        // .log(Level.WARNING,
        // "Scss file {0} exists but ScssStylesheet was not able to find it",
        // scssFilename);
        // return false;
        // }
        String compiled = "";
        try {
            getLogger().log(Level.FINE, "Compiling {0} for request to {1}",
                    new Object[] { realFilename, filename });
            compiled = Sass.compile(realFilename);
        } catch (Exception e) {
            getLogger().log(Level.WARNING, "Scss compilation failed", e);
            return false;
        }

        // This is for development mode only so instruct the browser to
        // never
        // cache it
        response.setHeader("Cache-Control", "no-cache");
        final String mimetype = getService().getMimeType(filename);
        writeResponse(response, mimetype, compiled);

        return true;
        // }
    }

    /**
     * Writes the response in {@code output} using the contentType given in
     * {@code contentType} to the provided {@link HttpServletResponse}
     * 
     * @param response
     * @param contentType
     * @param output
     *            Output to write (UTF-8 encoded)
     * @throws IOException
     */
    private void writeResponse(HttpServletResponse response,
            String contentType, String output) throws IOException {
        response.setContentType(contentType);
        final OutputStream out = response.getOutputStream();
        // Set the response type
        final PrintWriter outWriter = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(out, "UTF-8")));
        outWriter.print(output);
        outWriter.flush();
        outWriter.close();
    }

    private static final Logger getLogger() {
        return Logger.getLogger(VaadinServlet.class.getName());
    }

}
