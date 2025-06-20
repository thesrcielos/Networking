package org.eci;

import org.eci.url.UrlReader;

import java.io.*;
import java.net.*;

public class ServerSockets {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String requestLine = in.readLine();
                System.out.println("Petición: " + requestLine);

                String header;
                int contentLength = 0;
                while ((header = in.readLine()) != null && !header.isEmpty()) {
                    System.out.println("Header: " + header);
                    if (header.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(header.split(":")[1].trim());
                    }
                }

                // Leer el cuerpo de la solicitud
                char[] body = new char[contentLength];
                in.read(body);
                String url = new String(body);
                System.out.println(body);
                String response= UrlReader.readUrlHtml(url);
                // Preparar respuesta HTTP
                String httpResponse = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: " + response.length() + "\r\n"
                        + "Access-Control-Allow-Origin: *\r\n"
                        + "\r\n"
                        + response;

                out.write(httpResponse.getBytes());
                out.flush();

                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
