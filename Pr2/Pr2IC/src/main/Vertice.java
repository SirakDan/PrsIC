/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author DanSirak
 */
public class Vertice {
    private String valor;
    private Nodo nodo;
    
    //Constructors
    public Vertice(){
        this.valor = "";
        this.nodo = null;
    }
    
    public Vertice(String valor, Nodo nodo){
        this.valor = valor;
        this.nodo = nodo;
    }
    
    //Getters y setters
    public String getValor() {
        return this.valor;
    }
    public Nodo getNodo() {
        return this.nodo;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }
    
    //Functions
}
