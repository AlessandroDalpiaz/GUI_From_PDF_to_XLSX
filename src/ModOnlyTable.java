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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
/**
 *
 * @author THOQ LUONG Mar 22, 2015 3:34:29 PM
 */
public class ModOnlyTable {
    public ModOnlyTable(String input,String output){
        String testoString="";
        try {

            String fileName = input;
            Path path = Paths.get(fileName);
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 0; i <allLines.size(); i++) {
                char charFirst=allLines.get(i).charAt(0);
                if (charFirst=='X' || isNumber(charFirst)) {
                    testoString+=allLines.get(i);
                    testoString+="\n";
                 
                }else if (allLines.get(i).contains("Oggetto dei lavori")) {
                        testoString+="OGGETTO";
                        testoString+=allLines.get(i).replace("Oggetto dei lavori ", "");
                        while(!allLines.get(i).contains("Importo")){
                            testoString+=allLines.get(i);
                            i++;
                        }
                        testoString+="\n";
                    }
                    
                        
                 
            }
            ///////convert to utf8//////////////
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"UTF8"));
            
            out.write(testoString);
            out.close();
            ////////////////////////////////////
            
        } catch (IOException e) {
            
            System.err.println("---modifyPatt_txt.java---");
            System.err.println(e);
            e.printStackTrace();
        }
    }
    private boolean isNumber(char c){
        String s =Character.toString(c);
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public ArrayList <String> modifyTxtToArray(String filemod){
        ArrayList<String> listToSave= new ArrayList();
        try{
            Path path = Paths.get(filemod);
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLiness = Files.readAllLines(path, StandardCharsets.UTF_8);
            String riga;
            //FARE L'OGGETTO
            listToSave.add(allLiness.get(0));
            for (int k = 1; k < allLiness.size(); k++) {
                if(allLiness.get(k).charAt(0)=='X' && allLiness.get(k).charAt(1)==' '){
                    StringBuffer temp= new StringBuffer(allLiness.get(k));
                    temp.replace(0,1, ""); 
                    listToSave.add(temp.toString());
                }else{
                    listToSave.add(allLiness.get(k));
                }
                
            }
        }catch(Exception e){
            System.err.println("---prepareforxlsDue.java---");
            e.printStackTrace();
        }
        return listToSave;
    }
}