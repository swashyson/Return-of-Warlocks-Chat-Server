/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import chatSystem.Server;
import chatSystem.broadCastSystem;
import dataStorage.realDataStorage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author Swashy
 */
public class FXMLDocumentControllerChat implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea allChat;
    @FXML
    private TextField ServerTF; 
    @FXML
    private Button sendButton;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
    }
    @FXML
     void sendButton_onAction(ActionEvent event){
            broadCastSystem.addToList("SERVER: "+ ServerTF.getText());
            
        }
    

    public TextArea getAllChat() {
        return allChat;
    }

    public void setAllChat(TextArea allChat) {
        this.allChat = allChat;
    }

     

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chatSystem.Server server = new Server();
        server.CreateServer(9006);
        allChat.appendText("Creating server...\n");
        
                realDataStorage.setTextArea(allChat);
        
        server.StartServer(9006);
        allChat.appendText("Starting server...\n");
        allChat.appendText("Waiting for connection...\n");
        
        
        

    }

}
