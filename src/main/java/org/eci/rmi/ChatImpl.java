package org.eci.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatImpl extends UnicastRemoteObject implements ChatServer {

    private final String nombre;

    public ChatImpl(String nombre) throws RemoteException {
        super();
        this.nombre = nombre;
    }

    @Override
    public void recibirMensaje(String mensaje) throws RemoteException {
        System.out.println("\n[" + nombre + "] dice: " + mensaje);
    }


    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Tu nombre: ");
            String miNombre = sc.nextLine();

            System.out.print("Puerto para publicar tu objeto: ");
            int miPuerto = Integer.parseInt(sc.nextLine());

                // Iniciar RMI Registry en este proceso
            Registry miRegistro = LocateRegistry.createRegistry(miPuerto);

            ChatImpl miObjeto = new ChatImpl(miNombre);
            miRegistro.rebind("Chat", miObjeto);

            System.out.println("Objeto publicado como 'Chat' en el puerto " + miPuerto);

            System.out.print("¿Deseas conectarte a otro usuario? (s/n): ");
            String respuesta = sc.nextLine().trim();

            ChatServer otroUsuario = null;
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("IP del otro usuario: ");
                String ip = sc.nextLine();

                System.out.print("Puerto del otro usuario: ");
                int puertoRemoto = Integer.parseInt(sc.nextLine());

                Registry registroRemoto = LocateRegistry.getRegistry(ip, puertoRemoto);
                otroUsuario = (ChatServer) registroRemoto.lookup("Chat");

                System.out.println("Conectado con el otro usuario.");
            }


            while (true) {
                System.out.print("> ");
                String mensaje = sc.nextLine();

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Saliendo...");
                    break;
                }

                if (otroUsuario != null) {
                    otroUsuario.recibirMensaje(miNombre + ": " + mensaje);
                } else {
                    System.out.println("No estás conectado a nadie.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
