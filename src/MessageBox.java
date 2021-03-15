/*******************************************************************************
 * Copyright (C) 2021 DALPIAZ ALESSANDRO
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the licence
 * which accompanies this distribution, and is available at ./lib
 *  
 * Contributors:
 *     @ALESSANDRO_DALPIAZ -software per la trasformazione di specifici file pdf 
 *                            in xlsx tramite GUI. 
 *                              
 ******************************************************************************/
package newmodelinterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class MessageBox extends Application {
    
    private TextArea boxmsg;

    public MessageBox() {
        boxmsg= new TextArea();
        boxmsg.setEditable(false);
        boxmsg.setText("Default Message");
        boxmsg.setWrapText(true);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("img/icon.png")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DragAndDropExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        boxmsg.setFont(new Font(16));
        BorderPane root = new BorderPane();
        Button btn_letto = new Button();
        btn_letto.setText("HO LETTO");
       
        btn_letto.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
            }
        });
        

        root.setCenter(boxmsg);
        root.setBottom(btn_letto);
        Scene scene = new Scene(root, 300, 150);
         btn_letto.setPrefWidth(scene.getWidth());
        primaryStage.setTitle("Message");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    void writeMessage(String message){
        boxmsg.setText(message);
    }

}