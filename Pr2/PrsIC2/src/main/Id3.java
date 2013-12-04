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
    public class Nodo {

            private String nombre;
            private String vertice;
            private ArrayList<Nodo> hijos;

            //Constructors
            public Nodo() {
                this.nombre = "";
                this.hijos = new ArrayList();
            }

            public Nodo(String atributo) {
                this.nombre = atributo;
                this.hijos = new ArrayList();
            }

            public Nodo(String atributo, ArrayList<Nodo> nodos) {
                this.nombre = atributo;
                this.hijos = nodos;
            }

        }
    private class Id3Data {

        public String atributo;
        public ArrayList<String> nombre;
        public int[] cuenta;
        public double[] p;
        public double[] n;
        public double N;
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
            while (i < nombre.size() && !thereIs) {
                if (nombre.get(i).equalsIgnoreCase(ejem)) {
                    thereIs = true;
                } else {
                    i++;
                }
            }
            if (!thereIs) {
                i = -1;
            }
            return i;
        }

        public double infor(double p, double n) {
            double sol1, sol2;
            if (p == 0) {
                sol1 = 0;
            } else {
                sol1 = Math.log(p) / Math.log(2);
            }
            if (n == 0) {
                sol2 = 0;
            } else {
                sol2 = Math.log(n) / Math.log(2);
            }
            return ((-1 * p) * sol1) - (n * sol2);

        }

        public void ajusta() {
            for (int i = 0; i < nombre.size(); i++) {
                p[i] = p[i] / cuenta[i];
                n[i] = n[i] / cuenta[i];
                r[i] = cuenta[i] / N;
                merito += r[i] * infor(p[i], n[i]);

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
     *
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
            if (split[split.length - 1].equalsIgnoreCase("si")) {
                plus++;
            } else if (split[split.length - 1].equalsIgnoreCase("no")) {
                minus++;
            }
        }
        if (plus != 0 && minus == 0) {
            retorno = 1;
        } else if (plus == 0 && minus != 0) {
            retorno = -1;
        } else {
            retorno = 0;
        }
        return retorno;
    }

    /**
     * Devuelve la cantidad de información de un conjunto de datos.
     *
     * @param p: Porcentaje de ejemplos positivos
     * @param n: Porcentaje de ejemplos negativos
     * @return
     */
    public Id3Data minMerito(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        double tmpMerito, minMerito = 10000;
        String candidato = "";
        int colocados, pos;
        Id3Data ejemplos;
        String[] frase;
        for (int i = 0; i < listAtributos.size() - 1; i++) {
            ejemplos = new Id3Data(listEjemplos.size());
            ejemplos.N = listEjemplos.size();
            //Miramos a ver cuántos ejemplos diferentes de cada atributo hay
            for (int j = 0; j < listEjemplos.size(); j++) {
                frase = listEjemplos.get(j).split(",");
                pos = ejemplos.isThere(frase[i]);
                if (pos == -1) {
                    pos = ejemplos.nombre.size();
                    ejemplos.nombre.add(frase[i]);
                    ejemplos.cuenta[pos] = 1;
                    ejemplos.p[pos] = 0;
                    ejemplos.n[pos] = 0;
                } else {
                    ejemplos.cuenta[pos]++;
                }
                if (frase[listAtributos.size() - 1].equalsIgnoreCase("si")) {
                    ejemplos.p[pos]++;
                } else {
                    ejemplos.n[pos]++;
                }
            }
            //Calculamos las p, n, a, r...
            ejemplos.ajusta();
            tmpMerito = ejemplos.merito;
            if (tmpMerito < minMerito) {
                minMerito = tmpMerito;
                candidato = listAtributos.get(i);
                System.out.println(candidato);
                id3data = ejemplos;
                id3data.atributo = candidato;
            }
        }
        return id3data;
    }

    public ArrayList<String> ejemplosRestantes(String valor, ArrayList<String> ejemplos) {
        ArrayList<String> retorno = new ArrayList();
        String[] frase;
        String recon = "";
        boolean encontrado = false;
        int j = 0;
        for (int i = 0; i < ejemplos.size(); i++) {
            frase = ejemplos.get(i).split(",");
            for (int k = 0; k < frase.length; k++) {
                if (!frase[k].equalsIgnoreCase(valor) && k < frase.length - 1) {
                    recon += frase[k] + ",";
                } else if (!frase[k].equalsIgnoreCase(valor) && k == frase.length - 1) {
                    recon += frase[k];
                }
            }
            retorno.add(recon);
            System.out.println(recon);
            recon = "";
        }

        return retorno;
    }

    public ArrayList<String> atributosRestantes(String valor, ArrayList<String> atributos) {
        ArrayList<String> retorno = atributos;
        int pos = -1;
        for (int i = 0; i < atributos.size(); i++) {
            if (atributos.get(i).equalsIgnoreCase(valor)) {
                pos = i;
            }
        }
        if (pos != -1) {
            retorno.remove(pos);
        }
        return retorno;
    }

    public void exec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        Nodo arbol = new Nodo();
        arbol = execRec(listEjemplos, listAtributos, "");
        System.out.println("Done!");
    }
    
    public Nodo execRec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos, String atributo) {
        if (listEjemplos.isEmpty()) {
            return null;
        }
        if (isItPlusOrMinus(listEjemplos) == 1) {
            return new Nodo("+");
        } else if (isItPlusOrMinus(listEjemplos) == -1) {
            return new Nodo("-");
        } else {
            if (listAtributos.isEmpty()) {
                return null;
            }
            id3data = minMerito(listEjemplos, listAtributos);
            Nodo tmp = new Nodo();
            for (int i = 0; i < id3data.nombre.size(); i++) {
                tmp.hijos.add(execRec(ejemplosRestantes(id3data.nombre.get(i), listEjemplos),
                        atributosRestantes(id3data.atributo, listAtributos)
                        , id3data.atributo));
            }
            return tmp;
        }
    }

}
