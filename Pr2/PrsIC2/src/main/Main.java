/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DanSirak
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        File ejemploFile= new File("ejemplo1.txt");
        File atributoFile = new File("juego1.txt");
        
        ArrayList<String> atributoList = new ArrayList();
        ArrayList<String> ejemploList = new ArrayList();
        String fraseCompleta;
        String[] frase;
        FileReader fr1 = null;
        BufferedReader br1 = null;
        FileReader fr2 = null;
        BufferedReader br2 = null;
        try {
            fr1 = new FileReader(atributoFile);
            br1 = new BufferedReader(fr1);
            fraseCompleta = br1.readLine();
            frase = fraseCompleta.split(",");
            for (int i = 0; i < frase.length; i++) atributoList.add(frase[i]);
            
            //br1.close();
            fr2 = new FileReader(ejemploFile);
            br2 = new BufferedReader(fr2);
            while((fraseCompleta = br2.readLine())!= null) {
                ejemploList.add(fraseCompleta);
            }
            //br2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Id3 id3 = new Id3(ejemploList, atributoList);
            id3.exec(ejemploList, atributoList);
            try {
                fr1.close();
                fr2.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

    }
    
}
