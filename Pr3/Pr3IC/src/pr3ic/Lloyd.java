/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pr3ic;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Lloyd {
    private ArrayList<double[][]> centros;
    private int caracteristicas;
    private int N;
    private ArrayList<String> muestras;
    private ArrayList<String> claseMuestra;
    private ArrayList<String> clases;
    private ArrayList<Double[][]> muestra;
    
    public Lloyd() {
        muestras = new ArrayList();
        claseMuestra = new ArrayList();
        clases = new ArrayList();
    }
    
    public int getCaracteristicas(){
        return caracteristicas;
    }
    
    public ArrayList<double[][]> getCentros(){
        return centros;
    }
    
    public ArrayList<String> getMuestras() {
        return muestras;
    }
    
    public ArrayList<String> getClaseMuestra() {
        return claseMuestra;
    }
    
    public ArrayList<String> getClases(){
        return clases;
    }
    
    private double distancia(double[][] X, double[][] V) {
        double distancia = 0.0;
        for (int i = 0; i < caracteristicas; i ++) 
            distancia+=pow(X[i][0]-V[i][0],2);
        return distancia;
    }
     
    
    public void calculaCentros(ArrayList<double[][]> muestras) {
        
        double distanciaMin = 100000; 
        double disTmp;
        int centro = 0;
        double[][] centroTmp;
        Matrix centTmp, cent;
        Matrix resta;
        double peso = 0.1;
        int n = 10, iter=0;
        ArrayList<double[][]> centrosIniciales = new ArrayList();
        double[][] tmpd, tmpM;
        boolean termina = false;
        double epsilon = pow(10,-10);
        while(!termina && iter < n) {
            tmpd = new double[caracteristicas][1];
            centrosIniciales = new ArrayList();
            for (int i = 0; i < centros.size(); i++) {
                tmpd = new double[caracteristicas][1];
                for (int k = 0; k < caracteristicas; k++) {
                    tmpd[k][0] = centros.get(i)[k][0];
                }
                centrosIniciales.add(tmpd);

            }
            for (int k = 0; k < muestras.size(); k++){
                distanciaMin=Double.MAX_VALUE;
                for (int i = 0; i < centros.size(); i++){
                    disTmp = distancia(muestras.get(k), centros.get(i));
                    if (disTmp < distanciaMin){
                        distanciaMin=disTmp;
                        centro = i;
                    }
                }
                
                
                tmpd = new double[caracteristicas][1];
                tmpM = new double[caracteristicas][1];
                    for (int j = 0; j < caracteristicas; j++) {
                        tmpd[j][0] = centros.get(centro)[j][0];
                        tmpM[j][0] = muestras.get(k)[j][0];
                }

            
                
                centTmp = new Matrix(tmpd);
                cent = new Matrix(tmpd);
                resta = new Matrix(tmpM);
                resta = resta.minusEquals(cent);
                resta = resta.timesEquals(peso);
                centTmp = centTmp.plusEquals(resta);
                centros.remove(centro);
                centros.add(centro, centTmp.getArrayCopy());
            }
            double tmp;

            for (int k = 0; k < centros.size(); k++){
                if (distancia(centros.get(k), centrosIniciales.get(k))<epsilon) termina = true; 
            }
            iter++;
        }
    }
    
    public void entrena(String fichero){
        FileReader fr = null;
           try {
               ArrayList<double[][]> X = new ArrayList();
               centros = new ArrayList();
               //ArrayList<String> clases = new ArrayList();
               Boolean encontrado = false;
               double[][] tmp;
               String[] split;
               File archivo = new File (fichero);
               fr = new FileReader (archivo);
               BufferedReader br = new BufferedReader(fr);
               String linea;
               String tmp1 = "";
               while((linea=br.readLine())!=null){
                   split = linea.split(",");
                   tmp = new double[split.length-1][1];
                   this.caracteristicas = split.length-1;
                   tmp1 = "( ";
                   for (int i = 0; i < split.length; i++){
                       if (i<split.length-1){
                           tmp[i][0]=Double.valueOf(split[i]);
                           tmp1 += tmp[i][0] + ",";
                           
                       }
                       else {
                           X.add(tmp.clone());
                           tmp1 += " )";
                           muestras.add(tmp1);
                           claseMuestra.add(split[i]);
                           for (int j = 0; j < clases.size(); j++) if (clases.get(j).equalsIgnoreCase(split[i])) encontrado = true;
                           if (!encontrado) clases.add(split[i]);
                           encontrado = false;
                       }
                   }

               }
               this.N=X.size();
               tmp = new double[caracteristicas][1];
               
               tmp[0][0]=4.6; tmp[1][0]=3.0; tmp[2][0]=4.0; tmp[3][0]=0.0;
               centros.add((double[][])tmp.clone());
               
               tmp = new double[caracteristicas][1];
               tmp[0][0]=6.8; tmp[1][0]=3.4; tmp[2][0]=4.6; tmp[3][0]=0.7;
               centros.add((double[][])tmp.clone());
               calculaCentros(X);       
           } catch (FileNotFoundException ex) {
               System.err.println("Archivo no encontrado.");
           } catch (IOException ex) {
               Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
               try {
                   if (fr != null)
                       fr.close();
               } catch (IOException ex) {
                   Logger.getLogger(Kmeans.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
    public boolean pertenencia(String fichero) {
        boolean pertenencia = false;
        double[][] muestra = null;
        String clase = "";
        FileReader fr = null;
        int p = -1;
        
        double distancia, distanciaMin = Double.MAX_VALUE;
        String[] split;
        File archivo = new File (fichero);

        try {
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            if ((linea=br.readLine())!=null){
                split=linea.split(",");
                muestra = new double[split.length-1][1];
                for(int i = 0; i < split.length-1; i++) muestra[i][0]=Double.parseDouble(split[i]);
                clase = split[split.length-1];
            }
            if (muestra != null){
                for(int i = 0; i < centros.size(); i++) {
                    distancia = distancia(centros.get(i), muestra);
                    if (distancia  < distanciaMin) {
                        distanciaMin = distancia;
                        p = i;
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
