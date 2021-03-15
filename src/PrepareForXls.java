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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aless
 */
public class PrepareForXls {
    List<Ordine> ordini= new ArrayList<Ordine>();
    public PrepareForXls(String input){
        try{
            String fileName = input;
            Path path = Paths.get(fileName);
            byte[] bytes = Files.readAllBytes(path);
            List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);

            //BOOLEANI PER VARIE FRASI
            boolean lavori=false;
            boolean serramenti= false;
            boolean recinzione= false;
            
            Ordine ord= new Ordine();

            for (int k = 0; k < allLines.size(); k++) {
                lavori=false;
                serramenti=false;
                recinzione=false;
                ord=new Ordine();

                
                //MEMORIZZO IL SOTTOTITOLO
                if (allLines.get(k).contains("ST / ST")) {
                    k++;
                    while(!allLines.get(k).contains("-- / --")){
                        ord.Titolo+= allLines.get(k)+ "\n";
                        k++;
                    }
                    ordini.add(ord);
                //MEMORIZZO TITOLLO IN IN LISTA(SICUREZZA)
                }else if(allLines.get(k).contains("$$ / $$")){
                    k++;
                    boolean tmp=false;
                    while(!allLines.get(k).contains("-- / --")){
                        if (tmp) {
                            ord.Titolo+="\n";
                        }
                        ord.Titolo+= allLines.get(k);
                        k++;
                        tmp=!tmp;
                    }
                    ordini.add(ord);
                //MEMORIZZO ORDINE COMPLETO
                }else{
                /****************prima riga****************/
                String [] tmpArr= allLines.get(k).split(" ");
                String linea= allLines.get(k);
                //creo numeroPrimaColonna
                ord.ID=tmpArr[0];
                //propongo i vari casi
                //LAVORI --> 2 RIGHE
                //SERRAMENTI --> while cm
                //RECINZIONE --> frazione
                    if (linea.contains("LAVORI")) {
                        lavori= true;
                    }else if(linea.contains("SERRAMENTI")){
                        serramenti= true;
                    }else if(linea.contains("RECINZIONE")) {
                        recinzione=true;
                    }
                for (int j =3; j < tmpArr.length; j++) {
                    ord.Titolo+=tmpArr[j]+ " ";
                }
               
                /****************seconda riga****************/
                tmpArr= allLines.get(++k).split(" ");
                if (tmpArr[0].length()==3 && verify(tmpArr[0])) {// "002"
                    for (int i = 1; i < tmpArr.length; i++) {
                        ord.Titolo+=tmpArr[i]+ " ";
                    }

                    /****************terza riga senza 000****************/
                    tmpArr= allLines.get(++k).split(" ");
                    int valTmp=1;
                    if (allLines.get(k).contains(" - ")) {
                        ord.Codice+=tmpArr[0]+" ";
                        ord.Codice+=tmpArr[1]+" ";
                        ord.Codice+=tmpArr[2]+" ";
                        ord.Codice+=tmpArr[3]+" ";
                        valTmp=4;
                    }else{
                        ord.Codice=tmpArr[0]+" ";
                    }
                    for (int i = valTmp; i < tmpArr.length; i++) {
                        ord.Titolo+=tmpArr[i]+ " ";  
                    }
                    /****************quarta riga senza 000****************/
                    tmpArr= allLines.get(++k).split(" ");
                    ord.Codice+=tmpArr[0];

                }else{
                    tmpArr= allLines.get(k).split(" ");
                    int valTmp=1;
                    if (allLines.get(k).contains(" - ")) {
                        ord.Codice+=tmpArr[0]+" ";
                        ord.Codice+=tmpArr[1]+" ";
                        ord.Codice+=tmpArr[2]+" ";
                        ord.Codice+=tmpArr[3]+" ";
                        valTmp=4;
                    }else{
                        ord.Codice=tmpArr[0];
                    }
                    for (int i = valTmp; i < tmpArr.length; i++) {
                        ord.Titolo+=tmpArr[i]+ " ";
                    }
                    /****************terza riga con 000****************/
                    tmpArr= allLines.get(++k).split(" ");
                    ord.Codice+=tmpArr[0];
                }
                
                ////////////sommano//////////////////////////

                while(!allLines.get(k).contains("SOMMANO")){
                    k++;
                }

                ord.Sommano=allLines.get(k).split(" ")[0]+" ";
                ord.Sommano+=allLines.get(k).split(" ")[1];

                //ord.Quantita=allLines.get(k).split(" ")[2];
                //ord.Unitario=allLines.get(k).split(" ")[3];
                
                
                
                if(allLines.get(k).split(" ").length==3){
                    ord.setSomma(allLines.get(k).split(" ")[2]);
                }else if(allLines.get(k).split(" ").length==4){
                    ord.Sommano+=allLines.get(k).split(" ")[2];
                    ord.setSomma(allLines.get(k).split(" ")[3]);
                }else if(allLines.get(k).split(" ").length==5){
                    ord.setQuantita(allLines.get(k).split(" ")[2]);
                    ord.setUnitario(allLines.get(k).split(" ")[3]); 
                    ord.setSomma(allLines.get(k).split(" ")[4]);
                }
                
                
                ord.printOrdine();
                ordini.add(ord);
                }
            }
        }catch(Exception e){
            System.err.println("---prepareforxls.java---");
            e.printStackTrace();
        }
        
    }
    public boolean verify(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private int method(int startI, Ordine ord,List<String> testo){
        
        ordini.add(ord);
        return startI;
    }
    public List<Ordine> GetListaOrdini(){
        return ordini;
    }
}
