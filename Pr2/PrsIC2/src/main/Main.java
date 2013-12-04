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
        File ejemploFile= new File("Juego.txt");
        File atributoFile = new File("AtributosJuego.txt");
        
        ArrayList<String> atributoList = new ArrayList();
        ArrayList<String> ejemploList = new ArrayList();
        String fraseCompleta;
        String[] frase;
        try {
            FileReader fr1 = new FileReader(atributoFile);
            BufferedReader br1 = new BufferedReader(fr1);
            fraseCompleta = br1.readLine();
            frase = fraseCompleta.split(",");
            for (int i = 0; i < frase.length; i++) atributoList.add(frase[i]);
            fr1.close();
            FileReader fr2 = new FileReader(ejemploFile);
            BufferedReader br2 = new BufferedReader(fr2);
            while((fraseCompleta = br2.readLine())!= null) {
                ejemploList.add(fraseCompleta);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Id3 id3 = new Id3(ejemploList, atributoList);
            id3.exec(ejemploList, atributoList);
        }

    }
    
}
