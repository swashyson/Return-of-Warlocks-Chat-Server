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
    private PrintWriter out;

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

                    } catch (IOException e) {
                        System.out.println("Client failed to connect!");
                    } finally {

                        try {
                            serverSocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        t1.start();

    }

    private static class chatHandler extends Thread {

        private Socket clientSocket;

        private chatHandler(Socket clientSocket) {
            super("chatHandler");
            this.clientSocket = clientSocket;

        }
    }

    public void run() {

        //Kör chatten här
    }
}
