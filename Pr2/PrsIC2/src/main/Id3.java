/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;
/**
 *
 * @author DanSirak
 */
public class Id3 {
    
    private class Id3Data {
        public ArrayList<String> nombre;
        public int[] cuenta;
        public double[] p;
        public double[] n;
        public int N;
        public double[] r;
        public double merito;
        
        public Id3Data(int size) {
            this.nombre = new ArrayList();
            this.cuenta = new int[size];
            this.p = new double[size];
            this.n = new double[size];
            this.r = new double[size];
            this.merito = 0;
        }
        
        public int isThere(String ejem) {
            boolean thereIs = false;
            int i = 0;
            while (i < nombre.size() && !thereIs){
                if (nombre.get(i).equalsIgnoreCase(ejem)) thereIs=true;
                else i++;
            }
            if (!thereIs) i = -1; 
            return i;
        }
        
        public double infor(double p, double n) {
            return -p*Math.log(p)-n*Math.log(n);
        }
        
        public void ajusta() {
            for (int i = 0; i < nombre.size(); i++) {
                p[i] = p[i]/cuenta[i];
                n[i] = n[i]/cuenta[i];
                r[i] = cuenta[i]/N;
                merito += r[i]*infor(p[i],n[i]);
            }
        }
    }
    
    private ArrayList<String> listEjemplos;
    private ArrayList<String> listAtributos;
    private Id3Data id3data;
    
    
    public Id3() {
	this.listEjemplos = new ArrayList();
	this.listAtributos = new ArrayList();
    }

    public Id3(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        this.listAtributos = listAtributos;
        this.listEjemplos = listEjemplos;
    }
    /**
     * Devuelve 1 si todos son "+", -1 si todos sin "-" y 0 si hay de ambos.
     * @param listEjemplos
     * @param listAtributos
     * @return 
     */
    public int isItPlusOrMinus(ArrayList<String> listEjemplos) {
        String frase;
        String[] split;
        int plus = 0, minus = 0, retorno;
        for (int i = 0; i < listEjemplos.size(); i++) {
            frase = listEjemplos.get(i);
            split = frase.split(",");
            if (split[split.length-1].equalsIgnoreCase("+")) plus++;
            else if (split[split.length-1].equalsIgnoreCase("-")) minus++;
        }
        if (plus != 0 && minus == 0) retorno = 1;
        else if (plus == 0 && minus != 0) retorno = -1;
        else retorno = 0;
        return retorno;
    }
    
    /**
     * Devuelve la cantidad de información de un conjunto de datos.
     * @param p: Porcentaje de ejemplos positivos
     * @param n: Porcentaje de ejemplos negativos
     * @return 
     */

    
    public String minMerito(ArrayList<String> listEjemplos, ArrayList<String> listAtributos){
        double tmpMerito, minMerito=Double.MAX_VALUE;
        String candidato = "";
        int colocados, pos;
        Id3Data ejemplos;
        String[] frase;
        for (int i = 0; i < listAtributos.size(); i++) {
            ejemplos = new Id3Data(listEjemplos.size());
            ejemplos.N = listEjemplos.size();
            //Miramos a ver cuántos ejemplos diferentes de cada atributo hay
            for (int j = 0; j < listEjemplos.size(); j++) {
                frase = listEjemplos.get(j).split(",");
                pos = ejemplos.isThere(frase[i]);
                if (pos == -1) {
                    ejemplos.nombre.add(frase[i]);
                    pos = ejemplos.nombre.size();
                    ejemplos.cuenta[pos] = 1;
                    ejemplos.p[pos] = 0;
                    ejemplos.n[pos] = 0;
                } else {
                    ejemplos.cuenta[pos]++;
                }
                if (frase[listAtributos.size()-1].equalsIgnoreCase("+")) ejemplos.p[pos]++;
                else ejemplos.n[pos]++;
            }
            //Calculamos las p, n, a, r...
            ejemplos.ajusta();
            tmpMerito = ejemplos.merito;
            if (tmpMerito < minMerito) {
                minMerito=tmpMerito;
                candidato=listAtributos.get(i);
                id3data = ejemplos;
            }
        }
        return candidato;
    }
    
    public ArrayList<String> ejemplosRestantes(String valor, ArrayList<String> ejemplos) {
        ArrayList<String> retorno = new ArrayList();
        for (int i = 0; i < ejemplos.size(); i++) {
            
        }
        
        return retorno;
    }
    
    public ArrayList<String> atributosRestantes(String valor, ArrayList<String> atributos) {
        ArrayList<String> retorno = new ArrayList();
        for (int i = 0; i < atributos.size(); i++) {
            
        }
        
        return retorno;
    }
    
    public Vertice exec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos, Vertice arbol) {
        if (listEjemplos.isEmpty()) return null;
        
        if (isItPlusOrMinus(listEjemplos) == 1) {
            arbol.setNodo(new Nodo("+"));
        } else if (isItPlusOrMinus(listEjemplos) == -1) {
            arbol.setNodo(new Nodo("-"));
        } else {
            if (listAtributos.isEmpty()) return null;
            String mejor = minMerito(listEjemplos, listAtributos);
            arbol.setNodo(new Nodo(mejor));
            for (int i = 0; i < id3data.nombre.size(); i++) {
                
            }
        }
        
        return arbol;
    }

}
