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
import javafx.stage.Stage;
 
public class DragAndDropExample extends Application {
 
 
    ImageView imageView;
    StackPane contentPane;
    BorderPane layout;
    public String link="";
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drag and Drop file");
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("img/icon.png")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DragAndDropExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        layout = new BorderPane();
        contentPane = new StackPane();
        //contentPane.setMaxSize(300, 300);
        contentPane.setStyle("-fx-background-color: #ffffff;");
        Scene scene = new Scene(layout, 400, 400, Color.WHITE);
 

        contentPane.setOnDragOver(new EventHandler() {
            @Override
            public void handle(Event event) {
                mouseDragOver((DragEvent)event);
            }
        });
 
        contentPane.setOnDragDropped(new EventHandler() {
            @Override
            public void handle(Event event) {
                mouseDragDropped((DragEvent)event);
            }
        });
 
         contentPane.setOnDragExited(new EventHandler() {
            @Override
            public void handle(Event event) {
                contentPane.setStyle("-fx-border-color: #C6C6C6;-fx-background-color: #ffffff;");
            }
        });
 
       HBox bottoni= new HBox();
       Button btn_conferma= new Button();
       Button btn_rimuovi= new Button();
       
       btn_conferma.setText("CONFERMA");
       btn_rimuovi.setText("RIMUOVI");
       bottoni.getChildren().addAll(btn_conferma,btn_rimuovi);
       
       btn_conferma.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                PDF_Reader.translatePDF(link, "tmp/AllText.txt");
                ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
            }
        });
       
       btn_rimuovi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                contentPane.getChildren().clear();
            }
        });
       layout.setCenter(contentPane);
       layout.setBottom(bottoni);
       primaryStage.setScene(scene);
       primaryStage.show();
    }
 
    void addImage(Image i, StackPane pane){
        imageView = new ImageView();
        imageView.setImage(i);
        Label lbl= new Label(link);
        pane.getChildren().addAll(imageView,lbl);
    }
 
  private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            final File file = db.getFiles().get(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println(file.getAbsolutePath());
                    link=file.getAbsolutePath();
                    try {
                        if(!contentPane.getChildren().isEmpty()){
                            contentPane.getChildren().clear();
                        }
                        Image img = new Image(new FileInputStream("img/iconPDF.png"));  
 
                        addImage(img, contentPane);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DragAndDropExample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }
 
    private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();
 
        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf");
 
        if (db.hasFiles()) {
            if (isAccepted) {
                contentPane.setStyle("-fx-border-color: red;"
              + "-fx-border-width: 5;"
              + "-fx-background-color: #C6C6C6;"
              + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }
 
}