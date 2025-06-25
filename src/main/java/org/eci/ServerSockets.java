package org.eci;

import org.eci.url.UrlReader;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerSockets {

    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.submit(() -> handleClient(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()
        ) {
            String requestLine = in.readLine();
            System.out.println("Petición: " + requestLine);

            String header;
            int contentLength = 0;
            String cookie = null;

            while ((header = in.readLine()) != null && !header.isEmpty()) {
                if (header.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(header.split(":")[1].trim());
                }
                if (header.startsWith("Cookie:")) {
                    cookie = header.split(":", 2)[1].trim();
                }
            }

            char[] body = new char[contentLength];
            in.read(body);
            String url = new String(body).trim();

            String html = UrlReader.readUrlHtml(url);

            FileWriter fw = new FileWriter("page-" + Thread.currentThread().getId() + ".html");
            fw.write(html);
            fw.close();

            StringBuilder responseHeader = new StringBuilder();
            responseHeader.append("HTTP/1.1 200 OK\r\n");
            responseHeader.append("Content-Type: text/html\r\n");
            responseHeader.append("Content-Length: ").append(html.length()).append("\r\n");
            responseHeader.append("Access-Control-Allow-Origin: *\r\n");
            
            if (cookie == null) {
                responseHeader.append("Set-Cookie: user=usuario").append(Thread.currentThread().getId()).append("\r\n");
            }

            responseHeader.append("\r\n");

            out.write(responseHeader.toString().getBytes());
            out.write(html.getBytes());
            out.flush();

        } catch (IOException e) {
            System.err.println("Error en cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignore) {}
        }
    }
}
