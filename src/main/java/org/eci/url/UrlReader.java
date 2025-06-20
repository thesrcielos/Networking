package org.eci.url;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlReader {
    public static String readUrl(String path) throws MalformedURLException {
        URL url = new URL(path);
        String name = "resultado.html";
        try (BufferedReader reader
                       = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter bw = new BufferedWriter(new FileWriter(name));

        ) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                bw.write(inputLine);
                bw.newLine();
            }
        } catch (IOException x) {
            return x.getMessage();
        }
        return "Succesfully created page " + url.toString();
    }

    public static String readUrlHtml(String path) {
        URL url;
        try {
             url = new URL(path);
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            return "";
        }

        StringBuilder res= new StringBuilder();
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(url.openStream()));
        ) {

            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                res.append(inputLine);
            }
        } catch (IOException x) {
            return x.getMessage();
        }
        return res.toString();
    }
}
