package org.eci.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void recibirMensaje(String mensaje) throws RemoteException;
}