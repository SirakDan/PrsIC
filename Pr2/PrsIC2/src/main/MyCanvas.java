/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author DanSirak
 */
public class MyCanvas extends JPanel{
    private Nodo arbol;
    private int profundidad;
    private int numeroHojas;
    private int height;
    private int width;
    private int hijosDibujados;
   
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //g.drawString("BLAH", 20, 20);
        if (this.height!=0 && this.width!=0) {
            g.drawString("200x200", 200, 200);
            g.drawString("0x0", 0, 0);
            g.drawString("100x100", 100, 100);
            g.drawString("50x300", 50, 300);
            System.out.println("altura: "  + height);
            System.out.println("anchura: " + width);
        }
            
        //    dibujaArbolRec(g, arbol, height, width, 1);
        //g.drawRect(10,10,10,10);
    }
    
    public Graphics drawTree(Graphics g) {
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hijosDibujados=1;
        dibujaArbolRec(g, arbol, height, width, 1);
        return g;
    }
    
    public void setParametros(Nodo origen, int profundidad,  int numeroHojas, int height, int width) {
        this.arbol=origen;
        this.profundidad=profundidad;
        this.numeroHojas=numeroHojas;
        this.height=height;
        this.width=width;
        //dibujaArbolRec(this.getGraphics(), arbol, height, width, 1);
        
    }
    
    public void actualizaArbol() {
        repaint();
    }
    
    private class Posicion{
        private int x;
        private int y;
        private int anchura;
        private int altura;
        //private int hijosDibujados;
        public Posicion() {
            super();
        }
    }
    private Posicion dibujaArbolRec(Graphics g, Nodo nodo, int height, int width, int nivel){
        
        if (g==null || nodo==null) return null;
        //g.drawString("EXISTO!", 200,200);
        //Nodo hoja:
        //drawRect(int x, int y, int width, int height)
        int yInit;// = (height/profundidad)*nivel;
        int xInit;// = (width/numeroHojas)*this.hijosDibujados; 
        int anchura;// = (width/numeroHojas)/6;
        int altura;//= (height/profundidad)/4;
        

        Posicion pos;
        if (nodo.getHijos().isEmpty()){
            yInit = (height/profundidad)*nivel;
            xInit = (width/numeroHojas)*this.hijosDibujados; 
            anchura = (width/numeroHojas)/6;
            altura = (height/profundidad)/4;
            g.setColor(Color.gray);
            g.drawRect(xInit, yInit, anchura, altura);
            g.setColor(Color.black);
            g.drawString(nodo.getNombre(), (xInit+anchura)/3, (yInit+altura)/3);
            this.hijosDibujados++;
            pos = new Posicion();
            pos.altura=altura;
            pos.anchura=anchura;
            pos.x=xInit;
            pos.y=yInit;
            return pos;
        } else {
            ArrayList<Posicion> posi = new ArrayList();
            for (Nodo i : nodo.getHijos()) {
                posi.add(dibujaArbolRec(g, i, height, width, nivel+1));
            }
            xInit = width - (posi.get(0).x + posi.get(posi.size()-1).x);
            yInit = height - (height/profundidad)*nivel;
            anchura = (width/numeroHojas)/6;
            altura = (height/profundidad)/4;
            g.setColor(Color.gray);
            g.drawRect(xInit, yInit, anchura, altura);
            g.setColor(Color.black);
            g.drawString(nodo.getNombre(), (xInit+anchura)/3, (yInit+altura)/3);
            pos = new Posicion();
            pos.altura=altura;
            pos.anchura=anchura;
            pos.x=xInit;
            pos.y=yInit;
        }
        //TODO: cambiar este return
        return pos;
        
    }
}
