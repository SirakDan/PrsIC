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
    private ArrayList<String> muestras;
    private ArrayList<String> claseMuestra;
    public Kmeans() {
        clases = new ArrayList();
        muestras = new ArrayList();
        claseMuestra = new ArrayList();
    }
    /**
     * Getter de muestras
     * @return listado con las muestras cargadas.
     */
    public ArrayList<String> getMuestras() {
        return muestras;
    }
    
    /**
     * Getter de las clases.
     * @return listado con las clases de las muestras.
     */
    public ArrayList<String> getClases() {
        return claseMuestra;
    }
    
    /**
     * 
     * Funcion para calcular la probabilidad de pertenencia de X a V(i) con peso b.
     * @param X: La muestra candidata.
     * @param V: Listado de centros de esta iteración.
     * @param i: Centro del cual queremos obtener la probabilidad.
     * @param b: Peso del método.
     * @return P: Probabilidad de pertenencia de X a V(i) con peso b.
     */
    private double calculaP(double[] X, ArrayList<double[]> V, int i, int b) {
        double sum = 0;
        double P;
        for (int r = 0; r < c; r++) 
            sum += pow(1/distancia(V.get(r), X), 1/(b-1));
        P = pow(1/distancia(V.get(i), X), 1/(b-1))/sum;
        return P;
    }
    
    /**
     * Función para calcular la distancia entre dos puntos.
     * @param X; Primer punto.
     * @param V: Segundo punto.
     * @return: distancia de X a V. 
     */
    private double distancia(double[] X, double[] V) {
        double distancia = 0.0;
        for (int i = 0; i < N; i ++) 
            distancia+=pow(X[i]-V[i],2);
        return distancia;
    }
    
    /**
     * Función que multiplica una matriz por un escalar.
     * @param d: Escalar.
     * @param v1: Matriz a multiplicar.
     * @return el resultado de multiplicar todos los elementos de v1 por d.
     */
    private double[] multiplica(double d, double[] v1) {
        double[] vector = new double[N];
        for (int i = 0; i < N; i++) {
            vector[i] = (d*v1[i]);
        }
        return vector;
    }
   
    /**
     * Suma elemento a elemento de dos matrices (v1 + v2)
     * @param v1: Matriz v1.
     * @param v2: Matriz v2.
     * @return: Suma elemento a elemento de las matrices v1 y v2.
     */
    private double[] suma(double[] v1, double[] v2) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i] = v1[i] + v2[i];
        return resultado;
    }
    
    /**
     * División de la matriz v por el escalar num.
     * @param v: Matriz.
     * @param num: escalar.
     * @return: División elemento a elemento de la matriz V.
     */
    private double[] divide(double[] v, double num) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i]=v[i]/num;
        return resultado;
    }
    
    /**
     * Cálculo del V(t+1) a partir de V y las muestras.
     * @param V: ArrayList con todos los centros.
     * @param X: ArrayList con todas las muestras.
     * @param i: Número del centro con el que estamos trabajando.
     * @param b: Peso de la función.
     * @return: V(t+1)
     */
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
    
    /**
     * Método para añadir una clase 
     * @param clase: Clase a añadir. 
     */
    @Deprecated
    public void añadeClase(String clase) {
        clases.add(clase);
    }
    
    /**
     * Función para entrenar el clasificador a partir de las muestras de fichero.
     * El formato de @param fichero debe ser el siguiente:
     * Valor1,valor2,...,valorN,nombreClase.
     * @param fichero: Fichero con las muestras
     */
    public void entrena(String fichero) {

        FileReader fr = null;
        try {
            ArrayList<double[]> X = new ArrayList();
            ArrayList<double[]> V = new ArrayList();
            //ArrayList<String> clases = new ArrayList();
            Boolean encontrado = false;
            double[] tmp;
            
            String[] split;
            File archivo = new File (fichero);
            fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            String tmp1 = "";
            
            while((linea=br.readLine())!=null){
                split = linea.split(",");
                tmp = new double[split.length-1];
                this.N = split.length-1;
                tmp1 = "( ";
                for (int i = 0; i < split.length; i++){
                    if (i<split.length-1){
                        tmp[i]=Double.valueOf(split[i]);
                        if (i < split.length-2)tmp1 += tmp[i] + ",";
                        else tmp1 += tmp[i];
                    }
                    else {
                        X.add(tmp);
                        tmp1 += " )";
                        muestras.add(tmp1);
                        claseMuestra.add(split[i]);
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
    
    
    /**
     * Función para comprobar la correcta clasificación de la muestra de fichero.
     * @param fichero: Fichero con la muestra a comprobar.
     * 
     * Formato:
     * valor1,valor2,...,valorN,nombreClase
     * 
     * @return true si valor1...valorN corresponde a la clase nombreClase 
     */
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
    
    /**
     * Getter de las medias
     * @return ArrayList con los means
     */
    public ArrayList<double[]> getMeans() {
        return Vfinal;
    }
    
    /**
     * Getter con las clases de las medias.
     * @return ArrayList con las clases de las medias.
     */
    public ArrayList<String> getVClases() {
        return clases;
    }
        
    }
    

