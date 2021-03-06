/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JPanel;

/**
 *
 * @author DanSirak
 */
public class Id3 {

    private Nodo origen;
    private int profundidad;
    private int numeroHojas;
    private int hijosDibujados;
    private ArrayList<String> reglas;
    
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

    private class Tabla{

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

        public Tabla copia() {
            Tabla retorno = new Tabla(this.height, this.width);
            retorno.height = this.height;
            retorno.width = this.width;
            System.arraycopy(fil, 0, retorno.fil, 0, this.height);
            System.arraycopy(col, 0, retorno.col, 0, this.width);
            retorno.tabla = new String[height][width];
            int j;
            for (int i = 0; i < retorno.height; i++) 
                for (j = 0; j < retorno.width; j++) {
                    retorno.tabla[i][j] = this.tabla[i][j];
            }
            return retorno;
            
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
        this.numeroHojas = 0;
        this.profundidad = 0;
        this.hijosDibujados = 0;
        this.reglas = new ArrayList();
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
        this.numeroHojas = 0;
        this.profundidad = 0;
        this.hijosDibujados = 0;
        this.reglas = new ArrayList();
    }

    /**
     * Devuelve 1 si todos son "+", -1 si todos sin "-" y 0 si hay de ambos.
     *
     * @param tabla
     * @return
     */
    private int isItPlusOrMinus(Tabla tabla) {
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
     * @param tabla
     * @return
     */
    private Id3Data minMerito(Tabla tabla) {
        double tmpMerito, minMerito = 10000;
        String candidato;
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

    private boolean[] ejemplosRestantes(String atributo, String valor, Tabla tabla) {
        int pos = -1;
        boolean[] aux = new boolean[tabla.height];
        System.arraycopy(tabla.fil, 0, aux, 0, tabla.height);
        for (int i = 0; i < tabla.width; i++) if (tabla.tabla[0][i].equalsIgnoreCase(atributo)) pos = i;
        if (pos != -1)
            for (int i = 1; i < tabla.height; i++) {
                if (!tabla.fil[i] && !tabla.tabla[i][pos].equalsIgnoreCase(valor)) {
                    aux[i] = true;
                }
            }

        return aux;
    }

    private boolean[] atributosRestantes(String valor, Tabla tabla) {
        boolean[] aux = new boolean[tabla.width];
        System.arraycopy(tabla.col, 0, aux, 0, tabla.width);
        for (int i = 0; i < tabla.width; i++) {
            if (!tabla.col[i] && tabla.tabla[0][i].equalsIgnoreCase(valor)) {
                aux[i] = true;
            }
        }

        return aux;
    }
    // TODO: Cambiar desde aquí
    public void exec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        Nodo arbol;
        Tabla table = new Tabla(listEjemplos.size()+1, listAtributos.size());
        String[] frase = {"",""};
        int j;
        for (int i = 0; i < table.height; i++) 
            for (j = 0; j < table.width; j++) {
                if (i == 0) {
                    //Se setean los atributos:
                    table.tabla[i][j] = listAtributos.get(j);
                } else {
                    //Se setean los ejemplos:
                    if (j == 0) frase = listEjemplos.get(i-1).split(",");
                    table.tabla[i][j] = frase[j];
                }
            }
        arbol = execRec(table, "",0);
        origen = arbol;
        System.out.println("Done!");
        //TODO: Añadir la ventana
    }

    private Nodo execRec(Tabla tabla, String atributo, int relProf) {
        if (tabla.empty()) {
            return null;
        }
        int ret = isItPlusOrMinus(tabla);
        relProf++;
        if (relProf > profundidad) profundidad = relProf;
        if (ret == 1) {
            Nodo tmp = new Nodo("+");
            tmp.setVertice(atributo);
            numeroHojas++;
            return tmp;
        } else if (ret == -1) {
            Nodo tmp = new Nodo("-");
            tmp.setVertice(atributo);
            numeroHojas++;
            return tmp;
        } else {
            
            Id3Data data = minMerito(tabla);
            Nodo tmp = new Nodo(data.atributo);
            tmp.setVertice(atributo);
            Tabla aux;
            boolean[] fil, col;
            for (int i = 0; i < data.nombre.size(); i++) {
                aux = tabla.copia();
                col = atributosRestantes(data.atributo, tabla);
                fil = ejemplosRestantes(data.atributo, data.nombre.get(i), tabla); 
                aux.fil = fil;
                aux.col = col;
                tmp.addHijo(execRec(aux, data.nombre.get(i),relProf));
                
            }
            return tmp;
        }
        
    }
    public ArrayList<String> dameReglas() {
        dameReglasRec("", origen);
        return reglas;
    }
    
    public void dameReglasRec(String frase, Nodo arbol) {
        if(arbol==null) return;
        if (arbol.getHijos().isEmpty()) {
            String respuesta = (arbol.getNombre().equalsIgnoreCase("+"))?"si":"no";
            frase += " " + arbol.getVertice() + " entonces: " + respuesta;
            reglas.add(frase);
        }
        else {
            if (arbol.getVertice().equalsIgnoreCase(""))
                frase += "si " + arbol.getNombre() + " ==";
            else
                frase += " " + arbol.getVertice() + " && " + arbol.getNombre() + " == ";
            for (Nodo i : arbol.getHijos()) 
                dameReglasRec(frase, i); 
        }
        
    }
    public Nodo getNodoOrigen(){
        return this.origen;
    }
    public int getNodosHojas(){
        return this.numeroHojas;
    }
    public int getProfundidad(){
        return this.profundidad;
    }
    
    private String trim(String cadenaConEspacios) {
        StringTokenizer tokenizer = new StringTokenizer(cadenaConEspacios);
        StringBuilder cadenaSinEspacios = new StringBuilder();
 
    while(tokenizer.hasMoreTokens()){
        cadenaSinEspacios.append(tokenizer.nextToken());
    }
 
    return cadenaSinEspacios.toString();
    }
    //-1 : imposible
    //0 :  "SI"
    //1 : "NO"
    public int reglaContiene(ArrayList<String> reglas) {
        String[] tmp;
        String dbg;
        boolean retorno = false;
        CharSequence cs;
        
        for (int i = 0; i < this.reglas.size(); i++) {
            if (!retorno) {
                if (this.reglas.get(i).split("&&").length == reglas.size() )
                    for (int j = 0; j < reglas.size(); j++) {
                        cs = trim(reglas.get(j).toLowerCase());
                        dbg = trim(this.reglas.get(i).toLowerCase());
                        System.out.println("THIS: " + cs);
                        System.out.println("THOS: " + reglas.get(j).toLowerCase());
                        retorno = dbg.contains(cs);
                    }
            } if (retorno){
                tmp = this.reglas.get(i).split("entonces: ");
                if (tmp[1].equalsIgnoreCase("si")) return 0;
                else return 1;
            }
        }
        return -1;
    }
    
}
