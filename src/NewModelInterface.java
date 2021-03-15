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

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.KeyStroke;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Even;


/**
 *
 * @author aless
 */
public class NewModelInterface extends Application {
    BorderPane root= new BorderPane();
    HBox hbox_container = new HBox();
    static TextArea txtarea;
    ScrollPane scrollPane;
    VBox tutto= new VBox();
    VBox listaGrafica= new VBox();
    TabComputo tabcomputo= new TabComputo();
    Riga rigaDiInserimento= new Riga();
    int contatore=0;
    TextField txtNumRemove= new TextField();
    CheckBox check_autoNormalXLSX = new CheckBox();
    CheckBox check_autoPattXLSX = new CheckBox();
    CheckBox check_hideArea = new CheckBox();
    Stage dialog = new Stage();
    static String fileName = "tmp/AllText.txt";
    
    public static void loadArea(){
        try{
            Path path = Paths.get(fileName);
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            String testo="";
            for (int i = 0; i <allLines.size(); i++) {
                testo+=allLines.get(i)+"\n";
            }
            txtarea.setText(testo);
        }catch(Exception e){
            System.err.println(e);
            Path path = Paths.get(fileName);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        
        try {
            
            File f  =new File("tmp/");
            f.mkdir();
            FileUtils.cleanDirectory(f);
            
            primaryStage.getIcons().add(new Image(new FileInputStream("img/icon.png")));
        } catch (Exception ex ) {
            Logger.getLogger(NewModelInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        txtarea= new TextArea();
        listaGrafica.setSpacing(5);
        check_autoNormalXLSX.setText("Caricamento Automatico1");
        check_autoPattXLSX.setText("Caricamento Automatico2");
        check_hideArea.setText("Nascondi Testo");
        
        txtarea.setEditable(false);
        scrollPane= new ScrollPane();
        
        scrollPane.setFitToWidth(true);
        Button btn_sceltaFile= new Button();
        Button btn_addRiga = new Button();
        Button btn_creaXLS = new Button();
        Button btn_rmRiga= new Button();
        Button btn_Guida= new Button();
        btn_Guida.setText("GUIDA");
        txtNumRemove.setMaxWidth(50);
        txtNumRemove.setMaxHeight(btn_rmRiga.getHeight());
        
        btn_addRiga.setStyle("-fx-font-weight: bold;");
        
        
        // btn_sceltaFile.setPadding(new Insets(20));
        //**************DIALOG BOX**************************************
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Questo è un messaggio"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        //**************************************************************
        
        
        ContextMenu contextMenu = new ContextMenu();
        MenuItem addTitoletto = new MenuItem("addTitoletto");
        MenuItem Ordine = new MenuItem("Codice/Ordine");
        MenuItem Titolo = new MenuItem("Descrizione/Titolo");
        MenuItem Quantita = new MenuItem("Quantita");
        MenuItem Unitario = new MenuItem("Prezzo Unitario");
        MenuItem Sommano = new MenuItem("SOMMANO __");
        MenuItem Totale = new MenuItem("Totale");

        
        contextMenu.getItems().addAll(addTitoletto,Ordine,Titolo,Quantita,Unitario,Sommano,Totale);
        txtarea.setContextMenu(contextMenu);
        
        //enable selection multiple
        rigaDiInserimento.setCampoID(Integer.toString(contatore));
        
        //******************TASTO DESTRO********************
        addTitoletto.setOnAction((event) -> {
            set_adTitoletto();   
        });
        Ordine.setOnAction((event) -> {
            set_Ordine();   
        });
        Titolo.setOnAction((event) -> {
            set_Titolo();     
        });
        Unitario.setOnAction((event) -> {           
            set_Unitario();
        });
        Quantita.setOnAction((event) -> {
            set_Quantita();            
        });
        Sommano.setOnAction((event) -> {
            set_Sommano();
        });
        Totale.setOnAction((event) -> {
            set_Totale();
        });
        
        
        
        btn_sceltaFile.setText("Aggiungi file");
        btn_addRiga.setText("Aggiungi Riga");
        btn_creaXLS.setText("Crea FoglioXLS");
        btn_rmRiga.setText("Rimuovi Riga:");
        btn_creaXLS.setDisable(true);
        btn_addRiga.setDisable(true);
        
        btn_Guida.setOnAction(new EventHandler<ActionEvent>() {
        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        
            @Override
            public void handle(ActionEvent event) {             
                getHostServices().showDocument(path.toString()+"/MANUALE.pdf");
            }
        });
        btn_addRiga.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                btn_sceltaFile.setDisable(true);
                if (contatore==0 || rigaDiInserimento.getTitoletto()) {
                    rigaDiInserimento.setCampoID("");
                }else if(contatore>=1){
                    btn_creaXLS.setDisable(false);
                }
                System.out.println("Aggiungi riga");
               // btn_creaXLS.setDisable(false);
                tabcomputo.addRiga(rigaDiInserimento.getCampoID(),rigaDiInserimento.getCampoOrdine(),
                    rigaDiInserimento.getCampoTitolo(),rigaDiInserimento.getCampoUnitario(),
                    rigaDiInserimento.getCampoQuantita(),rigaDiInserimento.getCampoSommano(),
                    rigaDiInserimento.getCampoTotale(), rigaDiInserimento.getTitoletto());
                
                listaGrafica.getChildren().clear();
                for (int i = 0; i < tabcomputo.listRows.size(); i++) {
                    listaGrafica.getChildren().add(tabcomputo.getLista().get(i).getRigaGrafica());
                }
                System.out.println(tabcomputo.listRows.size());
                contatore++;
                rigaDiInserimento.setCampoID(Integer.toString(contatore));
                rigaDiInserimento.clearBox();
                check_autoNormalXLSX.setVisible(false);
            }
        });
        btn_creaXLS.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String msg_tmp="";
                MessageBox mb = new MessageBox();
                Stage v= new Stage();
                FileChooser fileChooser = new FileChooser();
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);
                //try per la riuscita del file
                try{
                    if(check_autoNormalXLSX.isSelected()){
                        msg_tmp="ERRORE!: Caricamento Automatico non disponibile.";
                        System.out.println("Fase 2");
                        Modify_txt modTxt=new Modify_txt(fileName, "tmp/ModText.txt");                       
                        System.out.println("Fase 3");
                        PrepareForXls prep= new PrepareForXls("tmp/ModText.txt");
                        System.out.println("Fase 4");
                        //System.out.println(prep.GetListaOrdini().size());
                        LoadXLS.creaAutoDocumentoUno(file.getAbsolutePath(),prep.GetListaOrdini());
                        System.out.println("Fine");
                    }else if(check_autoPattXLSX.isSelected()){
                        msg_tmp="ERRORE!: Caricamento Automatico non disponibile.";
                        System.out.println("Fase 2");
                        ModOnlyTable modOnlyTable=new ModOnlyTable(fileName, "tmp/Mod2Text.txt");  
                        System.out.println("Fase 3");
                        
                        LoadXLS.creaAutoDocumentoDue(file.getAbsolutePath(),modOnlyTable.modifyTxtToArray("tmp/Mod2Text.txt"));
                    }else{
                        msg_tmp="ERRORE!: File non salvato.";
                        LoadXLS.creaDocumento(file.getAbsolutePath(), tabcomputo.getLista());
                    }
                } catch (Exception ee) {
                    //mb.writeMessage(msg_tmp);
                    //mb.start(v);
                }
                if (file.exists()) {
                    mb.writeMessage("File salvato correttamente!\n"+ file.getAbsolutePath());
                }else{
                    mb.writeMessage(msg_tmp+" \nFile non salvato.");
                }
                
                mb.start(v);
                v.centerOnScreen();
            }
        });
        
        btn_sceltaFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                btn_addRiga.setDisable(false);
                DragAndDropExample ff = new DragAndDropExample();
                try {
                    Stage v= new Stage();
                    ff.start(v);
                    v.centerOnScreen();
                    
                    //((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
                } catch (Exception ex) {
                    Logger.getLogger(NewModelInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btn_rmRiga.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(txtNumRemove.getText()!=""){
                    tabcomputo.listRows.remove(Integer.parseInt(txtNumRemove.getText()));
                    txtNumRemove.setText("");
                    listaGrafica.getChildren().clear();
                    for (int i = 0; i < tabcomputo.listRows.size(); i++) {
                        listaGrafica.getChildren().add(tabcomputo.getLista().get(i).getRigaGrafica());
                    }
                }
            }
        });
        
        ///////tastiera
        txtarea.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                if (event.getCode()==KeyCode.C) {
                    set_Ordine();
                    System.out.println("D");   
                }else if(event.getCode()==KeyCode.D){
                    set_Titolo();
                    System.out.println("T");
                }else if(event.getCode()==KeyCode.P){
                    set_Unitario();
                }else if(event.getCode()==KeyCode.Q){
                    set_Quantita();
                }else if(event.getCode()==KeyCode.S){
                    set_Sommano();
                }else if(event.getCode()==KeyCode.T){
                    set_Totale();
                }else if(event.getCode()==KeyCode.X){
                        set_adTitoletto();
                } 
            }
            
        });
        
        check_autoNormalXLSX.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                if(check_autoNormalXLSX.isSelected()){
                    btn_creaXLS.setDisable(false);
                    check_autoPattXLSX.setSelected(false);
                }else{
                    btn_creaXLS.setDisable(true);

                }
            }
            });

        check_autoPattXLSX.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                if(check_autoPattXLSX.isSelected()){
                    btn_creaXLS.setDisable(false);
                    check_autoNormalXLSX.setSelected(false);
                }else{
                    btn_creaXLS.setDisable(true);
                }
            }
            });
        
            check_hideArea.setOnMouseClicked(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                if(check_hideArea.isSelected()){
                    //txtarea.setVisible(false);
                    hbox_container.getChildren().remove(txtarea);
                }else{
                    //txtarea.setVisible(true);
                    hbox_container.getChildren().add(0, txtarea);
                }
            }
            });

            
            root.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent event) {
                if (check_hideArea.isSelected()) {
                   System.out.println(event.getCode());
                    if (event.getCode()==KeyCode.C) {
                        set_Ordine();
                        System.out.println("D");   
                    }else if(event.getCode()==KeyCode.D){
                        set_Titolo();
                        System.out.println("T");
                    }else if(event.getCode()==KeyCode.P){
                        set_Unitario();
                    }else if(event.getCode()==KeyCode.Q){
                        set_Quantita();
                    }else if(event.getCode()==KeyCode.S){
                        set_Sommano();
                    }else if(event.getCode()==KeyCode.T){
                        set_Totale();
                    }else if(event.getCode()==KeyCode.X){
                        set_adTitoletto();
                    } 
                }
                
            }
            
        });
        
        scrollPane.setContent(listaGrafica);
        
        
        txtarea.setMinHeight(300);
        txtarea.setMinWidth(600);
        Label titolo= new Label("GUI FROM PDF TO XLSX");
        titolo.setAlignment(Pos.CENTER);
        titolo.setTextAlignment(TextAlignment.CENTER);
        titolo.setFont(Font.font(30));
        titolo.setPadding(new Insets(16));

        tutto.getChildren().addAll(rigaDiInserimento.getHeaderRigaGrafica(),scrollPane);
        hbox_container.getChildren().addAll(txtarea,tutto);
        root.setTop(titolo);
        root.setCenter(hbox_container);
        HBox bottoni= new HBox();
        bottoni.getChildren().addAll(btn_sceltaFile,btn_creaXLS,btn_Guida,btn_rmRiga,txtNumRemove,btn_addRiga,check_hideArea,check_autoNormalXLSX,check_autoPattXLSX);//check_hideArea
        bottoni.setPadding(new Insets(10));
        bottoni.setSpacing(10);
        
        //bottoni.getChildren().add(tabellaBottoni);
        root.setBottom(bottoni);
        
        
        Scene scene = new Scene(root, 1800, 800);
        hbox_container.setMinWidth(scene.getWidth());
        titolo.setPrefWidth(scene.getWidth());
        primaryStage.setTitle("GUI PDF Reader");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void print(){
        System.out.println(txtarea.getSelectedText());
        }
    private void set_adTitoletto(){
        if(check_hideArea.isSelected()){
            rigaDiInserimento.setCampoTitolo(rigaDiInserimento.getCampoTitolo()+" " +getClipboardContents());
        }
        else{
            rigaDiInserimento.setCampoTitolo(rigaDiInserimento.getCampoTitolo()+" " +txtarea.getSelectedText());
        }
        
        rigaDiInserimento.setTitoletto(true); 
    }
    private void set_Ordine(){
        if (check_hideArea.isSelected()) {
            rigaDiInserimento.setCampoOrdine(rigaDiInserimento.getCampoOrdine()+ " "+ getClipboardContents());
        }
        else{
        rigaDiInserimento.setCampoOrdine(rigaDiInserimento.getCampoOrdine()+ " "+txtarea.getSelectedText());
        }
    }
    private void set_Titolo(){
        if (check_hideArea.isSelected()) {
            rigaDiInserimento.setCampoTitolo(rigaDiInserimento.getCampoTitolo()+" "+getClipboardContents());
        }else{
            rigaDiInserimento.setCampoTitolo(rigaDiInserimento.getCampoTitolo()+" "+txtarea.getSelectedText());
        }
        rigaDiInserimento.setTitoletto(false);
    }
    private void set_Unitario(){
        if (check_hideArea.isSelected()) {
            rigaDiInserimento.setCampoUnitario(getClipboardContents());
        }else{
            rigaDiInserimento.setCampoUnitario(txtarea.getSelectedText());
        }
        
    }
    private void set_Quantita(){
        if (check_hideArea.isSelected()) {
            rigaDiInserimento.setCampoQuantita(getClipboardContents());
        }else{
            rigaDiInserimento.setCampoQuantita(txtarea.getSelectedText());
        }
    }
    private void set_Sommano(){
        if(check_hideArea.isSelected()){
            rigaDiInserimento.setCampoSommano(getClipboardContents());
        }else{
            rigaDiInserimento.setCampoSommano(txtarea.getSelectedText());
        }
    }
    private void set_Totale(){
        if(check_hideArea.isSelected()){
            rigaDiInserimento.setCampoTotale(getClipboardContents());
        }else{
            rigaDiInserimento.setCampoTotale(txtarea.getSelectedText());
        }        
    }
    public String getClipboardContents() {
        String result = "";
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText =(contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
          try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
          }
          catch (UnsupportedFlavorException | IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
          }
        }
        return result;
    }
}
