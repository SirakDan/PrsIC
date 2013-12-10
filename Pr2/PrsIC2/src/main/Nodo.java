/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.util.ArrayList;

/**
 *
 * @author Test User
 */
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
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public void setVertice(String vertice){
            this.vertice = vertice;
        }
        
        public void addHijo(Nodo hijo){
            this.hijos.add(hijo);
        }
        
        public String getNombre() {
            return this.nombre;
        }
        
        public String getVertice() {
            return this.vertice;
        }
        
        public ArrayList<Nodo> getHijos(){
            return this.hijos;
        }
    }