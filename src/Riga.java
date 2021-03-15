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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author aless
 */
public class Riga {
    private TextArea campoID= new TextArea();
    private TextArea campoOrdine= new TextArea();
    private TextArea campoTitolo= new TextArea();
    private TextArea campoQuantita= new TextArea();
    private TextArea campoUnitario= new TextArea();
    private TextArea campoSommano= new TextArea();
    private TextArea campoTotale= new TextArea();
    private Boolean titoletto;

    
    public Riga(){
        campoID.setText("");
        campoOrdine.setText("");
        campoTitolo.setText("");
        
        //////da fare////////
        campoTitolo.setWrapText(true);
        
        campoQuantita.setText("");
        campoUnitario.setText("");
        campoSommano.setText("");
        campoTotale.setText("");
        titoletto=false;
        
        campoID.setPrefWidth(20.0);
        //campoTotale.setPrefWidth();
    }
    
    public void setCampoID(String s){
        campoID.setText(s);
    }
    public String getCampoID(){
        return campoID.getText();
    }
    public Boolean getTitoletto() {
        return titoletto;
    }
    public void setTitoletto(Boolean b) {
        titoletto =b;
    }
    
    public String getCampoOrdine() {
        return campoOrdine.getText();
    }

    public void setCampoOrdine(String s) {
        this.campoOrdine.setText(s);
    }

    public String getCampoTitolo() {
        return campoTitolo.getText();
    }

    public void setCampoTitolo(String s) {
        this.campoTitolo.setText(s);
    }

    public String getCampoQuantita() {
        return campoQuantita.getText();
    }

    public void setCampoQuantita(String s) {
        this.campoQuantita.setText(s);
    }

    public String getCampoUnitario() {
        return campoUnitario.getText();
    }

    public void setCampoUnitario(String s) {
        this.campoUnitario.setText(s);
    }
    public String getCampoSommano() {
        return campoSommano.getText();
    }
    public void setCampoSommano(String s) {
        this.campoSommano.setText(s);
    }
    public String getCampoTotale() {
        return campoTotale.getText();
    }
    public void setCampoTotale(String s) {
        this.campoTotale.setText(s);
    }
    
    public void printRiga(){
        System.out.println("ID: " +getCampoID());
        System.out.println("Ordine: " +getCampoOrdine());
        System.out.println("Titolo: "+getCampoTitolo());
        System.out.println("Quantita: "+ getCampoQuantita());
        System.out.println("Unitario: "+ getCampoUnitario());
        System.out.println(getCampoSommano());
        System.out.println("TOTALE: "+ getCampoTotale());
        System.out.println("--------------------------------");
    }
    
    public HBox getRigaGrafica(){
        HBox rigaTmp= new HBox();
        rigaTmp.getChildren().addAll(campoID,campoOrdine,campoTitolo,campoSommano,campoQuantita,campoUnitario,campoTotale);
        rigaTmp.setMaxHeight(100);
        rigaTmp.setSpacing(5);
        return rigaTmp;
    }
    public HBox getHeaderRigaGrafica(){
        HBox rigaTmp= new HBox();
        VBox colUno= new VBox();
        VBox colDue= new VBox();
        VBox colTre= new VBox();
        VBox colQuattro= new VBox();
        VBox colCinque= new VBox();
        VBox colSex= new VBox();
        VBox colSette= new VBox();
        Label title_1= new Label("ID");
        Label title_2= new Label("CODICE/ORDINE [C]");
        Label title_3= new Label("DESCRIZIONE/TITOLO [D]");
        Label title_4= new Label("QUANTITA [Q]");
        Label title_5= new Label("PREZZO UNITARIO [P]");
        Label title_6= new Label("SOMMANO [TIPO] [S]");
        Label title_7= new Label("TOTALE [T]");
        title_1.setAlignment(Pos.CENTER);
        title_1.setTextAlignment(TextAlignment.CENTER);
        
        colUno.getChildren().addAll(title_1,campoID);
        colDue.getChildren().addAll(title_2,campoOrdine);
        colTre.getChildren().addAll(title_3,campoTitolo);
        colSex.getChildren().addAll(title_6,campoSommano);
        colQuattro.getChildren().addAll(title_4,campoQuantita);
        colCinque.getChildren().addAll(title_5,campoUnitario);
        colSette.getChildren().addAll(title_7,campoTotale);
        rigaTmp.getChildren().addAll(colUno,colDue,colTre,colSex,colQuattro,colCinque,colSette);
        
        rigaTmp.setMaxHeight(150);
        //rigaTmp.setMaxWidth(1000);
        rigaTmp.setSpacing(5);
        rigaTmp.setPadding(new Insets(5,5,20,5)); //margins around the whole grid (top/right/bottom/left)
        return rigaTmp;
    }
    public void clearBox(){
        //campoID.setText("");
        campoOrdine.setText("");
        campoTitolo.setText("");
        campoQuantita.setText("");
        campoUnitario.setText("");
        campoSommano.setText("");
        campoTotale.setText("");
    }
}

