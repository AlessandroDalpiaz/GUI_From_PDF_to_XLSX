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

/**
 *
 * @author aless
 */
public class Ordine {
    String Titolo="";
    String ID="";
    String Codice="";
    String Sommano="";
    Double Quantita=0.0;
    Double Unitario=0.0;
    Double Somma=0.0;
    
   public void SetTitolo(String s){
       Titolo=s;
   }
   public void SetID(String s){
       ID=s;
   }
   public void SetSommano(String s){
       Sommano=s;
   }
   public void setCodice(String s){
       Codice=s;
   }
   public void setQuantita(String s){
       Quantita=trasform(s);
   }
   public void setUnitario(String s){
       Unitario=trasform(s);
   }
   public void setSomma(String s){
       Somma=trasform(s);
   }
   private double trasform(String s){
       try{
            s=s.replace("´", "");
            s= s.replace(",",".");
            return Double.parseDouble(s);
       }catch(Exception g){
           g.printStackTrace();
           return 0.0;
       }
       
   }
   //stampa
   public void printOrdine(){
        System.out.println(ID);
        System.out.println(Titolo);
        System.out.println(Codice);
        System.out.println("");
        System.out.println(Sommano);
        System.out.println(Quantita);
        System.out.println(Unitario);
        System.out.println(Somma);
        System.out.println("");
        System.out.println("");
   }
}
