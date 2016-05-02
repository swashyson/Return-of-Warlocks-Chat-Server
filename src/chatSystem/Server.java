/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatSystem;

import controllers.FXMLDocumentControllerChat;
import dataStorage.NamesAndLobbysStorage;
import dataStorage.realDataStorage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    String userName = "";

    public Server() {

        startBroadCastSystem();

    }

    public void startBroadCastSystem() {

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                broadCast();
                return null;
            }
        };
        new Thread(task).start();
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

        Task task = new Task<Void>() {
            @Override
            public Void call() {
                while (true) {

                    try {

                        clientSocket = serverSocket.accept();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    chatHandler chath = new chatHandler(clientSocket);
                                    chath.start();
                                    broadCastSystem.addClientSockets(clientSocket);

                                    System.out.println("Jump to chatHandler");
                                    StartServer(port);

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return null;
                }
            }
        };
        new Thread(task).start();

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

                System.out.print("");
                i = 0;
            }
        }

    }

    public static void broadCastPlayerNames() {

        PrintWriter out = null;

        System.out.println("HELLO " + NamesAndLobbysStorage.getNames().size());

        for (int j = 0; j < broadCastSystem.getClientSockets().size(); j++) {

            try {
                Socket temp = (Socket) broadCastSystem.getClientSockets().get(j);
                out = new PrintWriter(temp.getOutputStream(), true);

                    out.println("|||||" + NamesAndLobbysStorage.getNames());
                    out.flush();
                    System.out.println("TEST");

                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
            String name = "";
            try {

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String test = in.readLine();
                    if (test.contains("|||||")) {
                        name = test.substring(5);
                        NamesAndLobbysStorage.getNames().add(name);
                        realDataStorage.appendTextArea("Client '" + name + "' Connected" + " at Socket: " + clientSocket +  "\n");
                        Server.broadCastPlayerNames();

                    } else {
                        realDataStorage.appendTextArea(test + "\n");
                        broadCastSystem.addToList(test);
                    }
                }
            } catch (SocketException ex) {
                removeIfDisconnected(name);
            } catch (Exception ex2) {

                ex2.printStackTrace();
            }
        }

        private void removeIfDisconnected(String name) {
            try {
                clientSocket.close();
                broadCastSystem.getBroadCastList().remove(clientSocket);
                broadCastSystem.getClientSockets().remove(clientSocket);
                NamesAndLobbysStorage.getNames().remove(name);
                realDataStorage.appendTextArea("Client '" + name + "' Disconnected" + " at Socket: " + clientSocket +  "\n");
                Server.broadCastPlayerNames();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
