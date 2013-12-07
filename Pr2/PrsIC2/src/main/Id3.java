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

    private class Tabla {

        public String[][] tabla;
        //private boolean[][] blocked;
        public boolean[] fil;
        public boolean[] col;
        public int height;
        public int width;

        public Tabla() {
            super();
        }

        public Tabla(int height, int width) {
            this.height = height;
            this.width = width;
            this.fil = new boolean[height];
            this.col = new boolean[width];
            this.tabla = new String[height][width];
        }
        public void clean() {
            col = new boolean[width];
            fil = new boolean[height];
        }
        public boolean empty() {
            int contador = 0;
            for(int i = 0; i < width; i++) if (col[i]) contador++; 
            if (contador == width - 1) return true;
            else {
                contador = 0;
                for (int i = 0; i < height; i++) if (fil[i]) contador++;
                if (contador == height - 1) return true;
            }
            return false;
        }
    }

    //private ArrayList<String> listEjemplos;
    //private ArrayList<String> listAtributos;
    private Id3Data id3data;
    private Tabla tabla;

    public Id3() {
        tabla = new Tabla();
    }

    public Id3(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        int height = listEjemplos.size() + 1;
        int width = listAtributos.size();
        tabla = new Tabla(height, width);
        String[] frase;
        for (int i = 0; i < height-1; i++) {
            frase = listEjemplos.get(i).split(",");
            for (int j = 0; j < width; j++) {
                if (i == 0) {
                    tabla.tabla[i][j] = listAtributos.get(j);
                } else {
                    tabla.tabla[i][j] = frase[j];
                }
            }
        }
    }

    /**
     * Devuelve 1 si todos son "+", -1 si todos sin "-" y 0 si hay de ambos.
     *
     * @param listEjemplos
     * @param listAtributos
     * @return
     */
    public int isItPlusOrMinus(Tabla tabla) {
        int plus = 0, minus = 0, retorno;
        for (int i = 1; i < tabla.height; i++) {
            if (!tabla.fil[i]) {
                if (tabla.tabla[i][tabla.width - 1].equalsIgnoreCase("si")) {
                    plus++;
                } else if (tabla.tabla[i][tabla.width - 1].equalsIgnoreCase("no")) {
                    minus++;
                }
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
    public Id3Data minMerito(Tabla tabla) {
        double tmpMerito, minMerito = 10000;
        String candidato = "";
        int pos;
        Id3Data ejemplos;
        for (int i = 0; i < tabla.width - 1; i++) {
            ejemplos = new Id3Data(tabla.height - 1);
            ejemplos.N = tabla.height - 1;
            //Miramos a ver cuántos ejemplos diferentes de cada atributo hay
            if (!tabla.col[i]) {
                for (int j = 1; j < tabla.height; j++) {
                    if (!tabla.fil[j]) {
                        pos = ejemplos.isThere(tabla.tabla[j][i]);
                        if (pos == -1) {
                            pos = ejemplos.nombre.size();
                            ejemplos.nombre.add(tabla.tabla[j][i]);
                            ejemplos.cuenta[pos] = 1;
                            ejemplos.p[pos] = 0;
                            ejemplos.n[pos] = 0;
                        } else {
                            ejemplos.cuenta[pos]++;
                        }
                        if (tabla.tabla[j][tabla.width - 1].equalsIgnoreCase("si")) {
                            ejemplos.p[pos]++;
                        } else {
                            ejemplos.n[pos]++;
                        }
                    }
                }
                //Calculamos las p, n, a, r...
                ejemplos.ajusta();
                tmpMerito = ejemplos.merito;
                if (tmpMerito < minMerito) {
                    minMerito = tmpMerito;
                    candidato = tabla.tabla[0][i];
                    id3data = ejemplos;
                    id3data.atributo = candidato;
                }
            }
        }
        return id3data;
    }

    public boolean[] ejemplosRestantes(String atributo, String valor, Tabla tabla) {
        int pos = -1;
        boolean[] aux = tabla.fil;
        for (int i = 0; i < tabla.width; i++) if (tabla.tabla[0][i].equalsIgnoreCase(atributo)) pos = i;
        if (pos != -1)
            for (int i = 1; i < tabla.height; i++) {
                if (!tabla.fil[i] && !tabla.tabla[i][pos].equalsIgnoreCase(valor)) {
                    aux[i] = true;
                }
            }

        return aux;
    }

    public boolean[] atributosRestantes(String valor, Tabla tabla) {
        boolean[] aux = tabla.col;
        for (int i = 0; i < tabla.width; i++) {
            if (!tabla.col[i] && tabla.tabla[0][i].equalsIgnoreCase(valor)) {
                aux[i] = true;
            }
        }

        return aux;
    }
    // TODO: Cambiar desde aquí
    public void exec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        Nodo arbol = new Nodo();
        Tabla tabla = new Tabla(listEjemplos.size()+1, listAtributos.size());
        String[] frase = {"",""};
        int j;
        for (int i = 0; i < tabla.height; i++) 
            for (j = 0; j < tabla.width; j++) {
                if (i == 0) {
                    //Se setean los atributos:
                    tabla.tabla[i][j] = listAtributos.get(j);
                } else {
                    //Se setean los ejemplos:
                    if (j == 0) frase = listEjemplos.get(i-1).split(",");
                    tabla.tabla[i][j] = frase[j];
                }
            }
        arbol = execRec(tabla, "");
        System.out.println("Done!");
    }

    public Nodo execRec(Tabla tabla, String atributo) {
        if (tabla.empty()) {
            return null;
        }
        int ret = isItPlusOrMinus(tabla);
        if (ret == 1) {
            Nodo tmp = new Nodo("+");
            tmp.nombre=atributo;
            return tmp;
        } else if (ret == -1) {
            Nodo tmp = new Nodo("-");
            tmp.nombre=atributo;
            return tmp;
        } else {
            if (tabla.empty()) {
                return null;
            }
            Id3Data data = minMerito(tabla);
            Nodo tmp = new Nodo();
            tmp.nombre=data.atributo;
            Tabla aux = tabla;
            boolean[] fil, col;
            
            for (int i = 0; i < data.nombre.size(); i++) {
                aux.clean();
                col = atributosRestantes(data.atributo, tabla);
                fil = ejemplosRestantes(data.atributo, data.nombre.get(i), tabla); 
                aux.fil = fil;
                aux.col = col;
                tmp.hijos.add(execRec(aux, data.nombre.get(i)));
            }
            return tmp;
        }
    }

}
