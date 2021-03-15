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

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;

//import org.apache.pdfbox.io.ScratchFileBuffer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.String.format;
import static java.text.MessageFormat.format;
import java.util.ArrayList;
import java.util.List;
import static javafx.beans.binding.Bindings.format;
import javafx.stage.Stage;
import static jdk.nashorn.api.scripting.ScriptUtils.format;
import static org.apache.poi.hssf.record.formula.SheetNameFormatter.format;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import static org.apache.poi.ss.formula.SheetNameFormatter.format;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author aless
 */

public class LoadXLS {
    private static ArrayList<Riga> ordini_load= new ArrayList<Riga>();
    LoadXLS(){}
    static void creaDocumento(String progettoxls, ArrayList<Riga> GetListaOrdini){        
        try{
            ordini_load = GetListaOrdini;
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Foglio1");
            
            //UNIONE DELLE CELLE
            //CELLA TITOLO
            CellRangeAddress region = CellRangeAddress.valueOf("B" + 2 + ":C"+ 3);
            sheet.addMergedRegion(region);
            //CELLA SOTTOTITOLO
            // selecting the region in Worksheet for merging data
            region = CellRangeAddress.valueOf("B" + 4 + ":C"+ 6);

            // merging the region
            sheet.addMergedRegion(region);

            //*********FONT VARI*************/
            Font tTitle= workbook.createFont();
            tTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            tTitle.setColor(Font.COLOR_RED);
            short s= 18;
            tTitle.setFontHeightInPoints(s);
            tTitle.setFontName("Arial");
            CellStyle textTitle=workbook.createCellStyle();
            textTitle.setFont(tTitle);
            
            textTitle.setWrapText(true);
            
            Font tTitleInList= workbook.createFont();
            tTitleInList.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle textTitleInList= workbook.createCellStyle();
            textTitleInList.setAlignment(CellStyle.ALIGN_CENTER);
            textTitleInList.setFont(tTitleInList);
            textTitleInList.setWrapText(true);
            textTitleInList.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());

            Font tBold= workbook.createFont();
            tBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle textBold= workbook.createCellStyle();
            textBold.setAlignment(CellStyle.ALIGN_LEFT);
            textBold.setFont(tBold);
            textBold.setWrapText(true);
           
            CellStyle stileSum= workbook.createCellStyle();
            stileSum.setAlignment(CellStyle.ALIGN_RIGHT);
            
            CellStyle accounting= workbook.createCellStyle();
            DataFormat formatA = workbook.createDataFormat();
            accounting.setDataFormat(formatA.getFormat("_-€ * #,##0.00_-;-€ * #,##0.00_-;_-€ * \"-\"??_-;_-@_-"));
            
            CellStyle number= workbook.createCellStyle();
            DataFormat formatN = workbook.createDataFormat();
            number.setDataFormat(formatN.getFormat("0.00"));
            
            //**********TITOLO
            int NRiga=1;//riga 2
            Row row = sheet.createRow(NRiga);
            Cell cell = row.createCell(1);
            cell.setCellValue("COMPUTO ESTIMATIVO");
            cell.setCellStyle(textTitle);
            NRiga=NRiga+2;
            
            //SOTTOTITOLO
            row = sheet.createRow(NRiga);
            cell = row.createCell(1);
            cell.setCellValue(ordini_load.get(0).getCampoTitolo());
            cell.setCellStyle(textBold);
            
            //**********LISTA OGGETTI
            NRiga=8;
            row = sheet.createRow(NRiga);
			cell = row.createCell(1);
            cell.setCellValue("CODICE/ORDINE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(2);
            cell.setCellValue("DESCRIZIONE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(3);
            cell.setCellValue("UNITA'\nDI\nMISURA");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(4);
            cell.setCellValue("QUANTITA'");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(5);
            cell.setCellValue("PREZZO\nUNITARIO");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(6);
            cell.setCellValue("TOTALE");
            cell.setCellStyle(textTitleInList);
            NRiga++;
            ordini_load.remove(0);//delete titolo
            for (Riga od : ordini_load) {
                if (od.getCampoID()=="") {
                    row = sheet.createRow(NRiga);
                    cell = row.createCell(2);
                    cell.setCellValue(od.getCampoTitolo());
                    cell.setCellStyle(textTitleInList);
                    NRiga++;
                }else{
                    row = sheet.createRow(NRiga);
                    cell = row.createCell(0);
                    cell.setCellValue(od.getCampoID());
                    cell.setCellStyle(textBold);

                    //NRiga++;
                    //row = sheet.createRow(NRiga);
                    cell = row.createCell(1);
                    cell.setCellValue(od.getCampoOrdine());
                    cell.setCellStyle(textBold);

                    cell = row.createCell(2);
                    cell.setCellValue(od.getCampoTitolo());
                    cell.setCellStyle(textBold);

                    //NRiga++;
                    //row = sheet.createRow(NRiga);

                    cell = row.createCell(3);
                    cell.setCellValue(od.getCampoSommano().replace("SOMMANO ", ""));
                    cell.setCellStyle(stileSum);

                    cell = row.createCell(4);
                    cell.setCellValue(trasform(od.getCampoQuantita()));
                    cell.setCellStyle(number);

                    cell = row.createCell(5);
                    cell.setCellValue(trasform(od.getCampoUnitario()));
                    cell.setCellStyle(accounting);

                    cell = row.createCell(6);
                    cell.setCellValue(trasform(od.getCampoTotale()));//spostare la procedura
                    cell.setCellStyle(accounting);
                    NRiga=NRiga+2;
                }                
            }
            
            //INSERISCO IL TOTALE
            row = sheet.createRow(NRiga);
            cell = row.createCell(5);
            cell.setCellValue("TOTALE");
            cell.setCellStyle(textBold);

            cell = row.createCell(6);
            cell.setCellFormula("SUM(G12:G"+(NRiga-1)+")");
            cell.setCellStyle(accounting);
            // Resize all columns to fit the content size
            /*for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
                
            }*/
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 3000);
            sheet.autoSizeColumn(4);
            sheet.setColumnWidth(5,3000);
            sheet.setColumnWidth(6,4000);
			
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(progettoxls);
            workbook.write(fileOut);
            //fileOut.flush();
            fileOut.close();
            
            //eliminazione cartella temporanea
            FileUtils.deleteDirectory(new File("tmp/"));
            
            
        }catch(Exception e){
            System.err.println("---LoadXls.java-CREADOC--");
            e.printStackTrace();
        }
    }
    static void creaAutoDocumentoUno(String progettoxls, List<Ordine> GetListaOrdini){
        List<Ordine> ordini_load= new ArrayList<Ordine>();
        ordini_load=GetListaOrdini;
        try{
            
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Foglio1");
            
            //UNIONE DELLE CELLE
            //CELLA TITOLO
            CellRangeAddress region = CellRangeAddress.valueOf("B" + 2 + ":C"+ 3);
            sheet.addMergedRegion(region);
            //CELLA SOTTOTITOLO
            // selecting the region in Worksheet for merging data
            region = CellRangeAddress.valueOf("B" + 4 + ":C"+ 6);

            // merging the region
            sheet.addMergedRegion(region);
            
            //*********FONT VARI*************//
            Font tTitle= workbook.createFont();
            tTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            tTitle.setColor(Font.COLOR_RED);
            short s= 18;
            tTitle.setFontHeightInPoints(s);
            tTitle.setFontName("Arial");
            CellStyle textTitle=workbook.createCellStyle();
            textTitle.setFont(tTitle);
            
            textTitle.setWrapText(true);
            
            Font tTitleInList= workbook.createFont();
            tTitleInList.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle textTitleInList= workbook.createCellStyle();
            textTitleInList.setAlignment(CellStyle.ALIGN_CENTER);
            textTitleInList.setFont(tTitleInList);
            textTitleInList.setWrapText(true);
            //textTitleInList.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());

            Font tBold= workbook.createFont();
            tBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            
            CellStyle textBold= workbook.createCellStyle();
            textBold.setAlignment(CellStyle.ALIGN_LEFT);
            textBold.setFont(tBold);
            textBold.setWrapText(true);
           
            CellStyle stileSum= workbook.createCellStyle();
            stileSum.setAlignment(CellStyle.ALIGN_RIGHT);
            
            CellStyle accounting= workbook.createCellStyle();
            DataFormat formatA = workbook.createDataFormat();
            accounting.setDataFormat(formatA.getFormat("_-€ * #,##0.00_-;-€ * #,##0.00_-;_-€ * \"-\"??_-;_-@_-"));
            
            CellStyle number= workbook.createCellStyle();
            DataFormat formatN = workbook.createDataFormat();
            number.setDataFormat(formatN.getFormat("0.00"));
            
            //**********TITOLO
            int NRiga=1;//riga 2
            Row row = sheet.createRow(NRiga);
            Cell cell = row.createCell(1);
            cell.setCellValue("COMPUTO ESTIMATIVO");
            cell.setCellStyle(textTitle);
            NRiga=NRiga+2;
            
            //SOTTOTITOLO
            row = sheet.createRow(NRiga);
            cell = row.createCell(1);
            cell.setCellValue(ordini_load.get(0).Titolo);
            cell.setCellStyle(textBold);
            
            //**********LISTA OGGETTI
            NRiga=8;
            row = sheet.createRow(NRiga);
            cell = row.createCell(1);
            cell.setCellValue("CODICE/ORDINE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(2);
            cell.setCellValue("DESCRIZIONE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(3);
            cell.setCellValue("UNITA'\nDI\nMISURA");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(4);
            cell.setCellValue("QUANTITA'");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(5);
            cell.setCellValue("PREZZO\nUNITARIO");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(6);
            cell.setCellValue("TOTALE");
            cell.setCellStyle(textTitleInList);
            NRiga++;
            ordini_load.remove(0);//delete titolo
            for (Ordine od : ordini_load) {
                if (od.ID=="") {
                    row = sheet.createRow(NRiga);
                    cell = row.createCell(2);
                    cell.setCellValue(od.Titolo);
                    cell.setCellStyle(textTitleInList);
                    NRiga++;
                }else{
                    row = sheet.createRow(NRiga);
                    cell = row.createCell(0);
                    cell.setCellValue(od.ID);
                    cell.setCellStyle(textBold);

                    //NRiga++;
                    //row = sheet.createRow(NRiga);
                    cell = row.createCell(1);
                    cell.setCellValue(od.Codice);
                    cell.setCellStyle(textBold);

                    cell = row.createCell(2);
                    cell.setCellValue(od.Titolo);
                    cell.setCellStyle(textBold);

                    //NRiga++;
                    //row = sheet.createRow(NRiga);

                    cell = row.createCell(3);
                    cell.setCellValue(od.Sommano.replace("SOMMANO ", ""));
                    cell.setCellStyle(stileSum);

                    cell = row.createCell(4);
                    cell.setCellValue(od.Quantita);
                    cell.setCellStyle(number);

                    cell = row.createCell(5);
                    cell.setCellValue(od.Unitario);
                    cell.setCellStyle(accounting);

                    cell = row.createCell(6);
                    cell.setCellValue(od.Somma);//spostare la procedura
                    cell.setCellStyle(accounting);
                    NRiga=NRiga+2;
                }                
            }
            
            //INSERISCO IL TOTALE
            row = sheet.createRow(NRiga);
            cell = row.createCell(5);
            cell.setCellValue("TOTALE");
            cell.setCellStyle(textBold);

            cell = row.createCell(6);
            cell.setCellFormula("SUM(G12:G"+(NRiga-1)+")");
            cell.setCellStyle(accounting);
            // Resize all columns to fit the content size
            /*for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
                
            }*/
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.setColumnWidth(3, 3000);
            sheet.autoSizeColumn(4);
            sheet.setColumnWidth(5,3000);
            sheet.setColumnWidth(6,4000);
  
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(progettoxls);
            workbook.write(fileOut);
            //fileOut.flush();
            fileOut.close();
            
            //eliminazione cartella temporanea
            FileUtils.deleteDirectory(new File("tmp/"));
            
            
        }catch(Exception e){
            System.err.println("---LoadXls.java-AUTOOO--");
            e.printStackTrace();
            MessageBox mb = new MessageBox();
            Stage v= new Stage();
            mb.writeMessage("ERRORE!: trasformazione fallita.");
            mb.start(v);
            v.centerOnScreen();
        }
    }
    
    
    
    private static double trasform(String s){
       try{
            s=s.replace("´", "");
            s= s.replace(",",".");
            return Double.parseDouble(s);
       }catch(Exception g){
           //g.printStackTrace();
           return 0.0;
       }  
   }
    private static double trasformDue(String s){
       try{
            s= s.replace(".","");
            s=s.replace(",",".");
            return Double.parseDouble(s);
       }catch(Exception g){
           //g.printStackTrace();
           return 0.0;
       }  
   }
   private static int LenghtDaTogliere(String[] arr){
       int l=0;
       for (int i = arr.length-1; i > 1; i--) {
           //System.out.println("___ "+arr[i].charAt(0));
           if(isNumber(arr[i].charAt(0))|| arr[i-1].charAt(0)=='€'){
               i--;
               l++;
           }else{
               return l;
           }
           l++;
       }
        return l;
    }
    private static boolean isNumber(char c){
        String s =Character.toString(c);
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    static void creaAutoDocumentoDue(String progettoxls,ArrayList<String> lista){
        try{
            System.err.println("dim lista----"+ lista.size());
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Foglio1");
            
            //UNIONE DELLE CELLE
            //CELLA TITOLO
            CellRangeAddress region = CellRangeAddress.valueOf("B" + 2 + ":D"+ 3);
            sheet.addMergedRegion(region);
            //CELLA SOTTOTITOLO
            // selecting the region in Worksheet for merging data
            region = CellRangeAddress.valueOf("B" + 4 + ":D"+ 6);

            // merging the region
            sheet.addMergedRegion(region);
            
            //*********FONT VARI*************//
            Font tTitle= workbook.createFont();
            tTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            tTitle.setColor(Font.COLOR_RED);
            short s= 18;
            tTitle.setFontHeightInPoints(s);
            tTitle.setFontName("Arial");
            CellStyle textTitle=workbook.createCellStyle();
            textTitle.setFont(tTitle);
            
            textTitle.setWrapText(true);
            
            Font tTitleInList= workbook.createFont();
            tTitleInList.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle textTitleInList= workbook.createCellStyle();
            textTitleInList.setAlignment(CellStyle.ALIGN_CENTER);
            textTitleInList.setFont(tTitleInList);
            textTitleInList.setWrapText(true);
            //textTitleInList.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());

            Font tBold= workbook.createFont();
            tBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            
            CellStyle textBold= workbook.createCellStyle();
            textBold.setAlignment(CellStyle.ALIGN_LEFT);
            textBold.setFont(tBold);
            textBold.setWrapText(true);
           
            CellStyle stileSum= workbook.createCellStyle();
            stileSum.setAlignment(CellStyle.ALIGN_RIGHT);
            
            CellStyle accounting= workbook.createCellStyle();
            DataFormat formatA = workbook.createDataFormat();
            accounting.setDataFormat(formatA.getFormat("_-€ * #,##0.00_-;-€ * #,##0.00_-;_-€ * \"-\"??_-;_-@_-"));
            
            CellStyle number= workbook.createCellStyle();
            DataFormat formatN = workbook.createDataFormat();
            number.setDataFormat(formatN.getFormat("0.00"));
            
            //**********TITOLO
            int NRiga=1;//riga 2
            Row row = sheet.createRow(NRiga);
            Cell cell = row.createCell(1);
            cell.setCellValue("COMPUTO ESTIMATIVO");
            cell.setCellStyle(textTitle);
            NRiga=NRiga+2;
            
            //SOTTOTITOLO
            row = sheet.createRow(NRiga);
            cell = row.createCell(1);
            cell.setCellValue(lista.get(0));
            cell.setCellStyle(textBold);
            
            //**********LISTA OGGETTI
            NRiga=8;
            row = sheet.createRow(NRiga);
            cell = row.createCell(0);
            cell.setCellValue("Nr");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(1);
            cell.setCellValue("CODICE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(3);
            cell.setCellValue("DESCRIZIONE DELLA VOCE");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(4);
            cell.setCellValue("UNITA'\nDI\nMISURA");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(5);
            cell.setCellValue("QUANTITA'");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(6);
            cell.setCellValue("COSTO\nMANODOPERA\nPAT\neuro");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(7);
            cell.setCellValue("COSTO\nNOLI E\nTRASPORTI PAT\neuro");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(8);
            cell.setCellValue("COSTO MATERIALI\nPAT\neuro");
            cell.setCellStyle(textTitleInList);
            cell = row.createCell(9);
            cell.setCellValue("IMPORTO TOTALE");
            cell.setCellStyle(textTitleInList);
            NRiga++;
            lista.remove(0);//delete titolo
            int id_c=-1;
            String[] riga;
            String[] udm={"m","m2","m3","kg","Kg","cal","cad.","t","h","cm","m²","m²/cm","mq"};
            for (String ss : lista) {
                if(ss.charAt(0)==' '){
                    //ss.replaceFirst(" ", "");
                    StringBuffer temp=new StringBuffer(ss);
                    temp.replace(0, 1,"");
                    ss= temp.toString();
                }
                riga= ss.split(" ");
                System.out.println("___"+ss+" :: "+riga.length);

                if(riga.length>5){
                    System.out.println(riga.length>5);
                    row = sheet.createRow(NRiga); 
                    //System.out.println(Integer.toString(id_c).equals(riga[0]));
                    System.out.println(id_c+" :::::: "+riga[0]);
                    if (!Integer.toString(id_c).equals(riga[0])) {
                        try{
                            System.out.println("entro");
                            //ID
                            cell = row.createCell(0);
                            cell.setCellValue(riga[0]);
                            cell.setCellStyle(textBold);
                            id_c++;

                            //CODICE
                            int cont=4;
                            cell = row.createCell(1);
                            if(riga[3].charAt(0)=='O'){
                                cell.setCellValue(riga[1]+riga[2]);
                                cell.setCellStyle(textBold);

                                //OS X
                                cell = row.createCell(2);
                                cell.setCellValue(riga[3]);
                                cell.setCellStyle(textBold);
                            }else{
                                cell.setCellValue(riga[1]);
                                cell.setCellStyle(textBold);

                                //OS X
                                cell = row.createCell(2);
                                cell.setCellValue(riga[2]+riga[3]);
                                cell.setCellStyle(textBold);
                                cont=3;
                                
                            }


                            //DESCRIZIONE
                            cell = row.createCell(3);

                            String testo="";
                            /*boolean exit=false;
                            for (cont=5;cont < riga.length; cont++) {
                                for (int j = 0; j < udm.length; j++) {
                                    System.out.println(riga[cont]);
                                    if(riga[cont].equals(udm[j])){
                                        exit=true; break;
                                    }else if(riga[cont].equals("a") && riga[cont+1].equals("corpo"))
                                        exit=true;
                                    testo+=riga[cont];
                                }
                                if(exit) break;
                            }*/
                            for (cont=cont+1;cont < (riga.length-LenghtDaTogliere(riga)); cont++) {
                                testo+=riga[cont]+" ";
                            }
                            cell.setCellValue(testo);
                            cell.setCellStyle(textBold);
                            //UDM
                            cell = row.createCell(4);
                            cell.setCellValue(riga[cont]);

                            cell.setCellStyle(stileSum);
                            cont++;

                            //QUANTITA'
                            cell = row.createCell(5);
                            cell.setCellValue(trasformDue(riga[cont]));
                            cell.setCellStyle(number);
                            cont=cont+2;

                             if (LenghtDaTogliere(riga)<5) {
                                //COSTO TOTALE
                                cell = row.createCell(9);
                                cell.setCellValue(trasformDue(riga[cont]));
                                cell.setCellStyle(accounting);
                                NRiga=NRiga+2;
                            }else{
                                 //COSTO MANODOPERA
                                cell = row.createCell(6);
                                cell.setCellValue(trasformDue(riga[cont]));
                                cell.setCellStyle(accounting);
                                cont=cont+2;

                                //COSTO NOLI
                                cell = row.createCell(7);
                                cell.setCellValue(trasformDue(riga[cont]));
                                cell.setCellStyle(accounting);
                                cont=cont+2;

                                //COSTO MATERIALI
                                cell = row.createCell(8);
                                cell.setCellValue(trasformDue(riga[cont]));
                                cell.setCellStyle(accounting);
                                cont=cont+2;

                                //COSTO TOTALE
                                cell = row.createCell(9);
                                cell.setCellValue(trasformDue(riga[cont]));
                                cell.setCellStyle(accounting);
                                NRiga=NRiga+2;
                            } 
                        }catch(Exception error){
                            System.out.println(error.toString());
                            NRiga=NRiga+2;
                            
                        }
                        
                    }
                }
                                
            }
            
            //INSERISCO IL TOTALE
            row = sheet.createRow(NRiga);
            cell = row.createCell(8);
            cell.setCellValue("TOTALE");
            cell.setCellStyle(textBold);

            cell = row.createCell(6);
            cell.setCellFormula("SUM(I12:I"+(NRiga-1)+")");
            cell.setCellStyle(accounting);
            // Resize all columns to fit the content size
            /*for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
                
            }*/
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.setColumnWidth(5,3000);
            sheet.setColumnWidth(6,4000);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(progettoxls);
            workbook.write(fileOut);
            //fileOut.flush();
            fileOut.close();
            
            //eliminazione cartella temporanea
            FileUtils.deleteDirectory(new File("tmp/"));
            
            
        }catch(Exception e){
            System.err.println("---LoadXls.java-AUTO_2--");
            e.printStackTrace();
            MessageBox mb = new MessageBox();
            Stage v= new Stage();
            mb.writeMessage("ERRORE!: trasformazione fallita.");
            mb.start(v);
            v.centerOnScreen();
        }
    }
}
