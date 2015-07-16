package org.peimari.dawn;

import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@WebListener
public class CdnFonts implements javax.servlet.http.HttpSessionListener {
    private boolean inited = "true".equals(System.getProperty("skipDawnCloudFonts"));

    public CdnFonts() { 
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        if(!inited) {
            VaadinServletService.getCurrent().addSessionInitListener(new SessionInitListener() {

                @Override
                public void sessionInit(SessionInitEvent event) throws ServiceException {
            VaadinSession.getCurrent().addBootstrapListener(new BootstrapListener() {

                @Override
                public void modifyBootstrapFragment(
                        BootstrapFragmentResponse response) {
                }

                @Override
                public void modifyBootstrapPage(BootstrapPageResponse response) {
                // Update the bootstrap page
                    Document document = response.getDocument();
                    Element head = document.getElementsByTag("head").first();
                    Element link = document.createElement("link");
                    link.attr("href", "//fonts.googleapis.com/css?family=Open+Sans:300,400,600,700");
                    link.attr("rel", "stylesheet");
                    link.attr("type", "text/css");
                    head.appendChild(link);
                    link = document.createElement("link");
                    link.attr("href", "//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css");
                    link.attr("rel", "stylesheet");
                    link.attr("type", "text/css");
                    head.appendChild(link);
                }
            });
            inited = true;
                }
            });
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
