/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pr3ic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Kmeans {
    private int N; //d está relacionado con n.
    private int c; //c está relacionado con j.
    private ArrayList<String> clases;
    private ArrayList<double[]> Vfinal;
    
    public Kmeans() {
        clases = new ArrayList();
        
    }
    /**
     * 
     * @param X
     * @param V 
     * @param i 
     * @param b 
     * @return P.
     */
    
    
    
    private double calculaP(double[] X, ArrayList<double[]> V, int i, int b) {
        double sum = 0;
        double P;
        for (int r = 0; r < c; r++) 
            sum += pow(1/distancia(V.get(r), X), 1/(b-1));
        P = pow(1/distancia(V.get(i), X), 1/(b-1))/sum;
        return P;
    }
    
    private double distancia(double[] X, double[] V) {
        double distancia = 0.0;
        for (int i = 0; i < N; i ++) 
            distancia+=pow(X[i]-V[i],2);
        return distancia;
    }
    
    private double[] multiplica(double d, double[] v1) {
        double[] vector = new double[N];
        for (int i = 0; i < N; i++) {
            vector[i] = (d*v1[i]);
        }
        return vector;
    }
   
    private double[] suma(double[] v1, double[] v2) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i] = v1[i] + v2[i];
        return resultado;
    }
    
    private double[] divide(double[] v, double num) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i]=v[i]/num;
        return resultado;
    }
    
    private double[] vector(ArrayList<double[]> V, ArrayList<double[]> X, int i, int b){
        double[] vector, vectorAcumulado=new double[N];
        for (int j = 0; j < N; j++) vectorAcumulado[j]=0;
        double denominador=0;
        double P;
        for (int j = 0; j < X.size(); j++) {
            //Calculamos el denominador:
            P=pow(calculaP(X.get(j), V, i, b),2);
            vector=multiplica(P, X.get(j));
            vectorAcumulado=suma(vector, vectorAcumulado);
            denominador+=P;
        }
        vectorAcumulado=divide(vectorAcumulado,denominador);
        return vectorAcumulado;
    }
    
    public void añadeClase(String clase) {
        clases.add(clase);
    }
    
    public void entrena(String fichero) {

        FileReader fr = null;
        try {
            ArrayList<double[]> X = new ArrayList();
            ArrayList<double[]> V = new ArrayList();
            ArrayList<String> clases = new ArrayList();
            Boolean encontrado = false;
            double[] tmp;
            
            String[] split;
            File archivo = new File (fichero);
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while((linea=br.readLine())!=null){
                split = linea.split(",");
                tmp = new double[split.length-1];
                this.N = split.length-1;
                for (int i = 0; i < split.length; i++){
                    if (i<split.length-1) tmp[i]=Double.valueOf(split[i]);
                    else {
                        X.add(tmp);
                        for (int j = 0; j < clases.size(); j++) if (clases.get(j).equalsIgnoreCase(split[i])) encontrado = true;
                        if (!encontrado) clases.add(split[i]);
                        encontrado = false;
                    }
                }
                
            }
            tmp = new double[N];
            tmp[0]=4.6; tmp[1]=3.0; tmp[2]=4.0; tmp[3]=0.0;
            V.add((double[])tmp.clone());
            tmp[0]=6.8; tmp[1]=3.4; tmp[2]=4.6; tmp[3]=0.7;
            V.add((double[])tmp.clone());
            double epsilon = 0.01;
            int b = 2;
            ArrayList<double[]> Vt = new ArrayList();
            for (int i = 0; i < N-1; i++) tmp[i]=0;
            for (int r = 0; r < V.size(); r++) Vt.add((double[])tmp.clone());
            int i = 0;
            double distancia = Double.MAX_VALUE;
            double[] vector;
            c=V.size();
            while(distancia>epsilon){
                Vt.remove(i);
                Vt.add(i, V.get(i).clone());
                vector = vector(V, X, i, b);
                V.remove(i);
                V.add(i, vector.clone());
                if (i==1) {
                    distancia=0;
                    for (int j = 0; j < V.size(); j++) distancia+=distancia(V.get(j), Vt.get(j));
                }
                i += 1;
                i = i % 2;
            }
            Vfinal = (ArrayList<double[]>) V.clone();
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
    
    public boolean pertenece(String fichero) {
        boolean pertenencia = false;
        double[] muestra = null;
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
                muestra = new double[split.length-1];
                for(int i = 0; i < split.length-1; i++) muestra[i]=Double.parseDouble(split[i]);
                clase = split[split.length-1];
            }
            if (muestra != null){
                for(int i = 0; i < Vfinal.size(); i++) {
                    distancia = distancia(Vfinal.get(i), muestra);
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
    
