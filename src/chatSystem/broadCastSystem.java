/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatSystem;

import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Swashy
 */
final public class broadCastSystem {

    static ArrayList broadCastList = new ArrayList();
    static ArrayList<Socket> clientSockets = new ArrayList<>();

    public static ArrayList getClientSockets() {
        return clientSockets;
    }

    public static void setClientSockets(ArrayList clientSockets) {
        broadCastSystem.clientSockets = clientSockets;
    }

    public static void addClientSockets(Socket socket) {

        clientSockets.add(socket);
    }

    public broadCastSystem() {

    }

    public static ArrayList getBroadCastList() {
        return broadCastList;
    }

    public static void setBroadCastList(ArrayList broadCastList) {
        broadCastSystem.broadCastList = broadCastList;
    }

    public static void addToList(String message) {

        broadCastList.add(message);
    }

}
