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
public class Nodo {
    private String atributo;
    private ArrayList<Vertice> vertice;
    
    //Constructors
    public Nodo() {
        this.atributo = "";
        this.vertice = new ArrayList();
    }
    public Nodo(String atributo) {
        this.atributo = atributo;
        this.vertice = new ArrayList();
    }
    public Nodo(String atributo, ArrayList<Vertice> vertice) {
        this.atributo = atributo;
        this.vertice = vertice;
    }
    
    //Getters, setters, add, remove:
    public String getAtributo() {
        return this.atributo;
    }
    public ArrayList<Vertice> getVertices() {
        return this.vertice;
    }
    
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }
    public void setVertices(ArrayList<Vertice> vertice) {
        this.vertice = vertice;
    }
    
    public void addVertice(Vertice vertice) {
        this.vertice.add(vertice);
    }
    public void removeVertice(int i) {
        this.vertice.remove(i);
    }
    
    //Functions
}
