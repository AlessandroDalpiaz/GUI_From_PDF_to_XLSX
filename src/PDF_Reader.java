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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
/**
 *
 * @author aless
 */
public class PDF_Reader {
    
    public static void translatePDF(String input,String output){
        String testoString="";
        try (PDDocument document = PDDocument.load(new File(input))){
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent())
            {
                throw new IOException("You do not have permission to extract text");
            }
            
            PDFTextStripper stripper = new PDFTextStripper();
            
            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);
            
            for (int p = 1; p <= document.getNumberOfPages(); ++p)
            {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);
                
                // let the magic happen
                String text = stripper.getText(document);
                
                // do some nice output with a header
                String pageStr = String.format("page %d:", p);
                //System.out.println(pageStr);
                //myWriter.write(pageStr+"\n");
                testoString+=pageStr+"\n";
                for (int i = 0; i < pageStr.length(); ++i)
                {
                    //System.out.print("-");
                    //myWriter.write("_");
                    testoString+="_";
                }
                
                //System.out.println();
                //System.out.println(text.trim());
                testoString+="\n";
                testoString+=text.trim();
                //System.out.println();
                testoString+="\n";
                
                /*myWriter.write("\n");
                myWriter.write(text.trim());
                myWriter.write("\n");*/
                
                // If the extracted text is empty or gibberish, please try extracting text
                // with Adobe Reader first before asking for help. Also read the FAQ
                // on the website:
                // https://pdfbox.apache.org/2.0/faq.html#text-extraction
            }
            //myWriter.close();
            //System.out.println("Successfully wrote to the file.");
            
            ///////convert to utf8//////////////
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output),"UTF8"));
            out.write(testoString);
            out.close();
            ////////////////////////////////////
            NewModelInterface.loadArea();
            
        }catch(Exception f){
            MessageBox mb = new MessageBox();
            Stage v= new Stage();
            mb.writeMessage("ERRORE!: lettura file PDF fallita.\nFile non supportato.");
            mb.start(v);
            v.centerOnScreen();
        }   
    }
    
}
