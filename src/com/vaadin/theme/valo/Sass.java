package com.vaadin.theme.valo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Sass {

    public static String compile(String realFilename) {
        Runtime runtime = Runtime.getRuntime();
        Process p;
        String output = "";
        try {
            // p = runtime.exec("/usr/bin/sass --trace -- " + tmpFile);
            p = runtime.exec("sass --trace -- " + realFilename);
            output = getString(p.getInputStream());
            String error = getString(p.getErrorStream());
            p.waitFor();
            int exitValue = p.exitValue();
            if (error != null && !error.isEmpty()) {
                if (exitValue != 0) {
                    System.out
                            .println("Sass compiler error code: " + exitValue);
                    output = "";
                }
                System.out.println(error);
            }
            p.destroy();
        } catch (Exception e) {
        }
        return output;
    }

    private static String getString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("/VAADIN")) {
                output.append(line.split("/VAADIN")[1]);
            } else {
                output.append(line);
            }
            output.append("\n");
        }
        return output.toString();
    }

}
