package org.eci.url;

import java.net.MalformedURLException;
import java.net.URL;

public class Exercise1 {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.escuelaing.edu.co:80/es/noticias/");
            System.out.println(url.getAuthority());
            System.out.println(url.getFile());
            System.out.println(url.getHost());
            System.out.println(url.getPort());
            System.out.println(url.getPath());
            System.out.println(url.getProtocol());
            System.out.println(url.getQuery());
            System.out.println(url.getRef());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
