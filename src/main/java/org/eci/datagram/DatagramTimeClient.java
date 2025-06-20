package org.eci.datagram;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            byte[] buf = new byte[256];
            String lastReceivedTime = "Sin hora recibida aún";

            while (true) {
                try {

                    DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                    socket.send(packet);


                    packet = new DatagramPacket(buf, buf.length);
                    socket.setSoTimeout(3000); // Tiempo máximo para esperar respuesta: 3 segundos
                    socket.receive(packet);

                    String received = new String(packet.getData(), 0, packet.getLength());
                    lastReceivedTime = received;

                } catch (SocketTimeoutException e) {
                    System.out.println("⏱️ No se recibió respuesta del servidor. Usando hora anterior.");
                } catch (IOException e) {
                    System.out.println("❌ Error al comunicarse con el servidor.");
                }

                System.out.println("🕒 Hora actual: " + lastReceivedTime);

                Thread.sleep(5000);
            }

        } catch (SocketException | UnknownHostException e) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
