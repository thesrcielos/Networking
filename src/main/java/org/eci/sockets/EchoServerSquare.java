package org.eci.sockets;

import org.eci.sockets.ex3.TrigonometricOperator;

import java.net.*;
import java.io.*;

public class EchoServerSquare {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        TrigonometricOperator trigonometricOperator = new TrigonometricOperator();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Mensaje:" + inputLine);
            try {
                outputLine = String.valueOf(Math.pow(Double.parseDouble(inputLine), 2));
            }catch (Exception e){
                outputLine = "Invalid number format";
            }

            out.println(outputLine);
            if (outputLine.equals("Respuestas: Bye."))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}