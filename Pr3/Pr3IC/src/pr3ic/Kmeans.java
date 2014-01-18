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
    /**
     * 
     * @param X
     * @param V 
     * @param j: Vector con el que estamos 
     * @param b 
     * @return P.
     */
    public double calculaP(double[] X, ArrayList<double[]> V, int j, int b) {
        double mu, muant, sum = 0;
        double P;
        for (int r = 0; r < c; r++) sum += pow(1/distancia(V.get(r), X), 1/(b-1));
        sum = pow(sum, 1/(b-1));
        P = pow(1/distancia(V.get(j), X), 1/(b-1))/sum;
        return P;
    }
    
    public double distancia(double[] X, double[] V) {
        double distancia = 0.0;
        for (int i = 0; i < N; i ++) distancia+=pow(X[i]+V[i],2);
        distancia = sqrt(distancia);
        return distancia;
    }
    
    public double[] multiplicaYdivideVectores(double d, double[] v1) {
        double[] vector = new double[N];
        for (int i = 0; i < N; i++) {
            vector[i] = (d*v1[i])/d;
        }
        return vector;
    }
    
    public double[] sum(double P, double[] Xj, int b) {
        double[] sum = new double[N];
        for(int j=0; j < N; j++)
            sum[j]=pow(P, b)*Xj[j];
        return sum;
    }
    
    public double[] sumaVectores(double[] v1, double[] v2) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i] = v1[i] + v2[i];
        return resultado;
    }
    
    public double[] vectorEntreNumero(double[] v, double num) {
        double[] resultado = new double[N];
        for (int i = 0; i < N; i++) 
            resultado[i]=v[i]/num;
        return resultado;
    }
    
    public double[] vector(ArrayList<double[]> V, ArrayList<double[]> X, int i, int b){
        double[] vector;
        double sum=0, P, denominador=0;
        double[] numerador, sumatorioNumerador = new double[N];
        for (int k = 0; k < N; k++) sumatorioNumerador[k]=0;
        for (int j = 0; j < N; j++) {
            P=calculaP(X.get(j), V, i, b);
            numerador = sum(P, X.get(j), b);
            sumatorioNumerador = sumaVectores(sumatorioNumerador, numerador);
            denominador+=P;
        }
        vector = vectorEntreNumero(sumatorioNumerador, denominador);
        return vector;
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
            File archivo = new File ("C:\\archivo.txt");
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
                        for (int j = 0; j < clases.size(); j++) if (clases.get(i).equalsIgnoreCase(split[i])) encontrado = true;
                        if (!encontrado) clases.add(split[i]);
                        encontrado = false;
                    }
                }
                
            }
            tmp = new double[N-1];
            tmp[0]=4.6; tmp[1]=3.0; tmp[2]=4.0; tmp[3]=0.0;
            V.add(tmp);
            tmp[0]=6.8; tmp[1]=3.4; tmp[2]=4.6; tmp[3]=0.7;
            V.add(tmp);
            double epsilon = 0.01;
            int b = 2;
            ArrayList<double[]> Vt = new ArrayList();
            for (int i = 0; i < N-1; i++) tmp[i]=0;
            for (int i = 0; i < V.size(); i++)
            Vt=(ArrayList<double[]>)V.clone();
            int i = 0;
            while(distancia(Vt.get(i), V.get(i))>epsilon);
            
            
            
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
    
    
}
