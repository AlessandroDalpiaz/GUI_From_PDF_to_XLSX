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

import java.util.ArrayList;

/**
 *
 * @author aless
 */
public class TabComputo {
    ArrayList<Riga> listRows= new ArrayList<Riga>();
    public void addRiga(String i,String o,String t,String u,String q,String s,String tt,Boolean titoletto){
        
        Riga riga=new Riga();
        if (titoletto) {
            riga.setCampoTitolo(t);
            riga.setTitoletto(true);
        }else{
            riga.setCampoID(i);
            riga.setCampoOrdine(o);
            riga.setCampoTitolo(t);
            riga.setCampoQuantita(q);
            riga.setCampoUnitario(u);
            riga.setCampoSommano(s);
            riga.setCampoTotale(tt);
            riga.setTitoletto(false);
        }
        
        
        listRows.add(riga);
    }
    
    public ArrayList<Riga> getLista(){
        return listRows;
    }
    public void printList(){
        for (Riga r : listRows) {
            r.printRiga();
        }
    }
}
