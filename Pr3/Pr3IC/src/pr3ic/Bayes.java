/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pr3ic;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Daniel
 */
public class Bayes {
    private int caracteristicas;
    private ArrayList<double[][]> C;
    private ArrayList<double[][]> m;
    private ArrayList<String> clases;
    
    //TODO: Al sacar los datos hay que dividir los ejemplos.
    
    //Se debe sacar un m por cada clase.
    private double[][] m(ArrayList<double[][]> X){
        double[][] sum = new double[caracteristicas][1];
        Matrix a = new Matrix(sum);
        Matrix b;
        for (int i = 0; i < X.size(); i++) {
            b = new Matrix(X.get(i));
            a = a.plus(b);
        }
        double div = 1./X.size();
        a.timesEquals(div);
        return a.getArrayCopy();
    }
    
    public double[][] c(ArrayList<double[][]> X) {
        int N = new Matrix(X.get(0)).getRowDimension();
        double[][] r;
        double[][] c = new double[N][N];
        double[][] mul;
        Matrix C = new Matrix(N, N), R;
        Matrix TRAS;
        double[][] m =  m(X);
        Matrix M = new Matrix(m);
        for (int i =0; i < X.size(); i++) {
            R = new Matrix(X.get(i));
            R = R.minus(M);
            TRAS = R.transpose();
            R = R.times(TRAS);
            C = C.plus(R);
        }
        double div = 1./X.size();
        C = C.times(div);
        return C.getArrayCopy();
    }
    
    public void entrena(String fichero) {
        FileReader fr = null;
        try {
            ArrayList<double[][]> X = new ArrayList();
            ArrayList<ArrayList> XV = new ArrayList();
            clases = new ArrayList();
            Boolean encontrado = false;
            double[][] tmp;
            int N;
            String[] split = null;
            File archivo = new File (fichero);
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while((linea=br.readLine())!=null){
                split = linea.split(",");
                tmp = new double[split.length-1][1];
                N = split.length-1;
                for (int i = 0; i < split.length; i++){
                    if (i<split.length-1) tmp[i][0]=Double.valueOf(split[i]);
                    else {
                        for (int j = 0; j < clases.size(); j++) 
                            if (clases.get(j).equalsIgnoreCase(split[i])) {
                                XV.get(j).add(tmp);
                                encontrado = true;
                            }
                        if (!encontrado){
                            clases.add(split[i]);
                            XV.add(new ArrayList());
                            XV.get(XV.size()-1).add(tmp);
                        }
                        encontrado = false;
                    }
                    
                }
                
            }
            caracteristicas = split.length-1;
            this.C = new ArrayList();
            this.m = new ArrayList();
            for (int i = 0; i < XV.size(); i++) {
                C.add(c(XV.get(i)));
                m.add(m(XV.get(i)));
            }
                

            String frase = "C= \n {";
            for (int k = 0; k < XV.size(); k++) {
                for (int i = 0; i < caracteristicas; i++) {
                    for(int j = 0; j < caracteristicas; j++)
                        frase += C.get(k)[i][j] + ", ";
                    if (i < caracteristicas-1) frase += "\n  ";
                }
                frase += "} \n\n\n\n {";
            }
            System.out.println(frase);
        } catch(IOException | NumberFormatException e) {
            System.err.println("Error IO / Numero");
        }
    }
    
    public double probabilidad (double[][] Xi, double[][] m, double[][] c) {
        double probabilidad = 0;
        int d = 2;
        double PI = Math.PI;
        Matrix C = new Matrix(c);
        Matrix M = new Matrix(m);
        double frac1 = d/2, frac2 = 1./2;
        double fraccion = 1/(Math.pow(2*PI, frac1)*Math.pow(C.det(), frac2));
        frac2 = -frac2;
        Matrix resta = new Matrix(Xi);
        resta.minusEquals(M);
        Matrix tras = resta.transpose();
        Matrix Cinv = C.inverse();
        Matrix exp = tras.times(Cinv);
        exp = exp.times(resta);
        exp = exp.times(frac2);
        double EXP = exp.get(0, 0);
        probabilidad = fraccion * Math.pow(Math.E, EXP);
        return probabilidad;
    }
    
    public boolean pertenencia(String fichero) {
        boolean pertenencia = false;
        double[][] muestra = null;
        String clase = "";
        FileReader fr = null;
        int p = -1;
        String[] split;
        File archivo = new File (fichero);

        try {
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            double probabilidad = 0, probAnt = 0;
            if ((linea=br.readLine())!=null){
                split=linea.split(",");
                muestra = new double[split.length-1][1];
                for(int i = 0; i < split.length-1; i++) muestra[i][0]=Double.parseDouble(split[i]);
                clase = split[split.length-1];
            }
            if (muestra != null){
                for(int i = 0; i < clases.size(); i++) {
                    probabilidad = probabilidad(muestra, m.get(i), C.get(i));
                    if (probAnt < probabilidad){
                        p = i;
                        probAnt = probabilidad;
                    }
                }
                if (clase.equalsIgnoreCase(clases.get(p)))
                    pertenencia = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            System.err.println("Error al parsear los números del archivo " + fichero + ". Por favor, revise y vuelva a intentarlo.");
        }
        finally {
            
        }
        return pertenencia;   
        
    }
    
}
