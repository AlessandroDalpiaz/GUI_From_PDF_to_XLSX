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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author aless
 */
public class Modify_txt {
    
    public Modify_txt(String input,String output){
        String testoString="";
        try {
            FileWriter myWriter = new FileWriter(output);

            String fileName = input;
            Path path = Paths.get(fileName);
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (int i = 0; i <allLines.size(); i++) {
                if (allLines.get(i).contains(" / ")) {
                        while(!allLines.get(i).contains("SOMMANO")){
                            //TOLGO  RIPORTARE E RIPORTO
                            if (allLines.get(i).contains("R I P O R T A R E")) {
                                while(!allLines.get(i).contains("R I P O R T O")){
                                    i++;
                                }
                                i++;
                            }
                            testoString+=allLines.get(i);
                            testoString+="\n";
                            myWriter.write(allLines.get(i));
                            myWriter.write("\n");

                            
                            i++;
                        }
                        //STAMPO SOMMANO
                        //System.out.println(allLines.get(i));
                        testoString+=allLines.get(i);
                        testoString+="\n";
                        myWriter.write(allLines.get(i));                        
                        myWriter.write("\n");
                 
                }else if(allLines.get(i).contains("Riepilogo")){
                    while(!allLines.get(i).contains("Totale")){
                            i++;
                    }
                }else {
                    if (allLines.get(i).equals("pag. 1")) {
                        /*System.out.println("TT / TT");
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println("-- / --");*/
                       
                        testoString+="ST / ST";
                        testoString+="\n";
                        testoString+=allLines.get(i=i+2).replace("OGGETTO: ", "");
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+="-- / --";
                        testoString+="\n";
                        /*
                        myWriter.write("ST / ST");                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(i=i+2).replace("OGGETTO: ", ""));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                                               
                        myWriter.write("\n");
                        myWriter.write("-- / --");                        
                        myWriter.write("\n");*/
                    }
                    if (allLines.get(i).contains("ONERI")) {
                        /*System.out.println("$$ / $$");
                        System.out.println(allLines.get(i));
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println(allLines.get(++i));
                        System.out.println("-- / --");*/
                        
                        testoString+="$$ / $$";
                        testoString+="\n";
                        testoString+=allLines.get(i);
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+=allLines.get(++i);
                        testoString+="\n";
                        testoString+="-- / --";
                        testoString+="\n";
                        /*
                        myWriter.write("$$ / $$");                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(i));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                        
                        myWriter.write("\n");
                        myWriter.write(allLines.get(++i));                        
                        myWriter.write("\n");
                        myWriter.write("-- / --");                        
                        myWriter.write("\n");*/
                    }

                    }
                        
                 
            }
            myWriter.close();
            ///////convert to utf8//////////////
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"UTF8"));
            
            out.write(testoString);
            out.close();
            ////////////////////////////////////
            
        } catch (IOException e) {
            
            System.err.println("---modify_txt.java---");
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
