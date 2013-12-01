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
    private class tabla {
        private Object[][] data;
        private int I;
        private int J;
        
    }
    private ArrayList<String> listEjemplos;
    private ArrayList<String> listAtributos;
    private double p;
    private double n;
    
    
    public Id3() {
	this.listEjemplos = new ArrayList();
	this.listAtributos = new ArrayList();
    }

    public Id3(ArrayList<String> listEjemplos, ArrayList<String> listAtributos) {
        this.listAtributos = listAtributos;
        this.listEjemplos = listEjemplos;
        this.p = 0;
        this.n = 0;
        // TODO: es entre en número de ejemplos de este tipo qué hay
        int E = this.listEjemplos.size();
        for (int i = 0; i < E; i++) {
            if (listEjemplos.get(i).endsWith("+")) p++;
            else n++;
        }
        p = p/E;
        n = n/E;
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
            if (split[split.length].equalsIgnoreCase("+")) plus++;
            else if (split[split.length].equalsIgnoreCase("-")) minus++;
        }
        if (plus != 0 && minus == 0) retorno = 1;
        else if (plus == 0 && minus != 0) retorno = -1;
        else retorno = 0;
        return retorno;
    }
    
    public double infor(double p, double n) {
        
        return 0;
    }
    
    public String minMerito(ArrayList<String> listEjemplos, ArrayList<String> listAtributos){
        
        return "asdf";
    }
    
    public Vertice exec(ArrayList<String> listEjemplos, ArrayList<String> listAtributos, Vertice arbol) {
        if (listEjemplos.isEmpty()) return null;
        
        if (isItPlusOrMinus(listEjemplos) == 1) {
            arbol.setNodo(new Nodo("+"));
        } else if (isItPlusOrMinus(listEjemplos) == -1) {
            arbol.setNodo(new Nodo("-"));
        } else {
            if (listAtributos.isEmpty()) return null;
            String[] frase = minMerito(listEjemplos, listAtributos).split(",");
        }
        
        return arbol;
    }
<<<<<<< HEAD
=======
    
    
    
>>>>>>> e049ba1c35c7dbff853761d2b7f556b6a96a33c7
}
