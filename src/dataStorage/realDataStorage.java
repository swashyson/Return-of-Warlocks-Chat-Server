/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStorage;

import chatSystem.Server;
import controllers.FXMLDocumentControllerChat;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

/**
 *
 * @author Swashy
 */
final public class realDataStorage {
    
    static TextArea textArea;

    public static TextArea getTextArea() {
        return textArea;
    }

    public static void setTextArea(TextArea textArea) {
        realDataStorage.textArea = textArea;
    }
    
    public static void appendTextArea(String value){
        Platform.runLater(new Runnable() {
                           @Override
                            public void run() {
                                textArea.appendText(value); 
                            }
                        });
        System.out.println(value);
        
    }
    
    public realDataStorage(){
    
    }
    
}
