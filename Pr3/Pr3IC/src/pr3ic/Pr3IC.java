/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pr3ic;

/**
 *
 * @author Daniel
 */
public class Pr3IC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String prueba = "TestIris03.txt";
        String fichero = "Iris2Clases.txt";
        Kmeans km = new Kmeans();
        km.entrena(fichero);
        km.añadeClase("Iris-setosa");
        km.añadeClase("Iris-versicolor");
        if (km.pertenece(prueba))
            System.out.println("Clasificación de " + prueba + " correcta.");
        else 
            System.out.println("Clasificación de " + prueba + " incorrecta.");
        Bayes by = new Bayes();
        by.entrena(fichero);
        if (by.pertenencia(prueba)) System.out.println("OH YEAH");
        else System.out.println("OH NO...");
    }
    
}
