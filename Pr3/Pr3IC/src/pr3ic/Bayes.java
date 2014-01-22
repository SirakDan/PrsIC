/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pr3ic;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class Bayes {
    private int caracteristicas;
    
    
    //TODO: Al sacar los datos hay que dividir los ejemplos.
    
    //Se debe sacar un m por cada clase.
    private double[] m(ArrayList<double[]> X){
        double[] sum = new double[caracteristicas];
        for (int i = 0; i < caracteristicas; i++) sum[i] = 0;
        for (int i = 0; i < X.size(); i++) {
            sum = suma(sum,X.get(i));
        }
        sum = divide(sum, X.size());
        return sum.clone();
    }
    
    private double[] suma(double[] v1, double[] v2) {
        double[] resultado = new double[caracteristicas];
        for (int i = 0; i < caracteristicas; i++) 
            resultado[i] = v1[i] + v2[i];
        return resultado.clone();
    }
    
    private double[][] suma(double[][] v1, double[][] v2) {
        int I = v1.length, J = v2.length;
        double[][] suma = new double[I][J];
        for (int i = 0; i < I; i++)
            for (int j = 0; j < J; j++)
                suma[i][j] = v1[i][j] + v2[j][j];
        
        return suma.clone();
    }
    
    private double[] divide(double[] v, double num) {
        double[] resultado = new double[caracteristicas];
        for (int i = 0; i < caracteristicas; i++) 
            resultado[i]=v[i]/num;
        return resultado.clone();
    }
    
    private double[][] divide(double[][] v, double num) {
        int N = v.length;
        double[][] division = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                division[i][j] = v[i][j]/num;
        return division.clone();
    }
    
    private double[] resta(double[] v1, double[] v2) {
        double[] resta = new double[caracteristicas];
        for (int i = 0; i < caracteristicas; i++) 
            resta[i] = v1[i] - v2[i];
        return resta.clone();
    }
    
    private double[][]multiplicaTraspuesta(double[] X){
        int N = X.length;
        double[][] mul = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) 
                mul[i][j]=X[i]*X[j];
        return mul.clone();
    }
    
    public double[][] c(ArrayList<double[]> X) {
        int N = X.get(0).length;
        double[] r;
        double[][] c = new double[N][N];
        double[][] mul;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                c[i][j] = 0;
        double[] m =  m(X);
        for (int i =0; i < N; i++) {
            r = resta(X.get(i), m);
            mul = multiplicaTraspuesta(r);
            c = suma(c, mul);
        }
        c = divide(c, N);
        return c.clone();
    }
}
