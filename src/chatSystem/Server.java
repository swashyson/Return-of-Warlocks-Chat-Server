/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatSystem;

import controllers.FXMLDocumentControllerChat;
import dataStorage.realDataStorage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 *
 * @author Swashy
 */
public class Server {

    private static final int PORT = 9006;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server() {

        startBroadCastSystem();
        
    }

    public void startBroadCastSystem() {

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                broadCast();
            }
        });
        t2.start();

    }

    public void CreateServer(int port) {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Creating Server...");

        } catch (IOException e) {
            System.err.println("Error in creation of the server socket");
            System.exit(0);
        }
    }

    public void StartServer(int port) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Starting Server...");
                waitForConnect();
            }

            private void waitForConnect() {
                while (true) {

                    try {

                        clientSocket = serverSocket.accept();
                        System.out.println("Client Connected");
                        realDataStorage.appendTextArea("Client Connected\n");
                        chatHandler chath = new chatHandler(clientSocket);
                        chath.start();
                        broadCastSystem.addClientSockets(clientSocket);
                        System.out.println("Jump to chatHandler");

                    } catch (IOException e) {
                        System.out.println("Client failed to connect!");

                    }
                }
            }
        });
        t1.start();

    }

    public void broadCast() {

        PrintWriter out = null;

        int i = 0;
        while (true) {

            if (!broadCastSystem.getClientSockets().isEmpty() && !broadCastSystem.getBroadCastList().isEmpty()) {
                for (i = 0; i < broadCastSystem.getClientSockets().size(); i++) {

                    try {
                        Socket temp = (Socket) broadCastSystem.getClientSockets().get(i);
                        out = new PrintWriter(temp.getOutputStream(), true);

                        if (!broadCastSystem.getBroadCastList().isEmpty()) {
                            out.println(broadCastSystem.getBroadCastList().get(0));
                            out.flush();
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (!broadCastSystem.getBroadCastList().isEmpty()) {
                    broadCastSystem.getBroadCastList().remove(0);
                }
            } else {

                System.out.println("None connected/no messages detected" + i);
                i = 0;
            }
        }

    }

    private static class chatHandler extends Thread {

        private Socket clientSocket;

        private chatHandler(Socket clientSocket) {
            super("chatHandler");
            this.clientSocket = clientSocket;

        }

        @Override
        public void run() {
            BufferedReader in;

            try {

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String test = in.readLine();
                    realDataStorage.appendTextArea(test + "\n");
                    broadCastSystem.addToList(test);
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
