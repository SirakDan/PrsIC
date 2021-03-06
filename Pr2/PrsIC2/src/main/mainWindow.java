/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.ServiceUIFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Test User
 */
public class mainWindow extends javax.swing.JFrame {
   private Id3 id3;
    
    /**
     * Creates new form mainWindow
     */
    public mainWindow() {
        initComponents();
        this.resultadoArea.setEditable(false);
        id3 = new Id3();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        atributosField = new javax.swing.JTextField();
        ejemploField = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultadoArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        consultaUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        botonConsulta = new javax.swing.JButton();
        botonCerrar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        atributosField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atributosFieldActionPerformed(evt);
            }
        });

        startButton.setText("Generar reglas");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        resultadoArea.setColumns(20);
        resultadoArea.setRows(5);
        jScrollPane2.setViewportView(resultadoArea);

        jLabel1.setText("Atributos");

        jLabel2.setText("Juego");

        jLabel3.setText("Reglas generadas");

        jLabel4.setText("Introduce tu consulta:");

        botonConsulta.setText("Consultar");
        botonConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultaActionPerformed(evt);
            }
        });

        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        jLabel5.setText("Formato: AT1 == ej1; AT2 == ej2 ; ... ATN == ejN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consultaUsuario)
                    .addComponent(jScrollPane2)
                    .addComponent(atributosField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ejemploField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonCerrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(atributosField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ejemploField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consultaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(botonConsulta)
                    .addComponent(botonCerrar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        File ejemploFile= new File(this.ejemploField.getText());
        File atributoFile = new File(this.atributosField.getText());
        
        ArrayList<String> atributoList = new ArrayList();
        ArrayList<String> ejemploList = new ArrayList();
        String fraseCompleta;
        String[] frase;
        FileReader fr1 = null;
        BufferedReader br1;
        FileReader fr2 = null;
        BufferedReader br2;
        try {
            fr1 = new FileReader(atributoFile);
            br1 = new BufferedReader(fr1);
            fraseCompleta = br1.readLine();
            frase = fraseCompleta.split(",");
            boolean addAll;
            addAll = atributoList.addAll(Arrays.asList(frase));
            
            //br1.close();
            fr2 = new FileReader(ejemploFile);
            br2 = new BufferedReader(fr2);
            while((fraseCompleta = br2.readLine())!= null) {
                ejemploList.add(fraseCompleta);
            }
            //br2.close();
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            id3 = new Id3(ejemploList, atributoList);
            id3.exec(ejemploList, atributoList);
            this.resultadoArea.setText("");
            for (String i : id3.dameReglas()) 
                this.resultadoArea.setText(this.resultadoArea.getText() + "\n"+ i);
            try {
                fr1.close();
                fr2.close();
            } catch (IOException ex) {
                Logger.getLogger(mainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void atributosFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atributosFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_atributosFieldActionPerformed

    private void botonConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultaActionPerformed
        buscaRegla();
       
    }//GEN-LAST:event_botonConsultaActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed
    public void buscaRegla() {
        String[] reglasUsuario = this.consultaUsuario.getText().split(";"), tmp;
        String atributo, valor;
        ArrayList<String> arl = new ArrayList();
        arl.addAll(Arrays.asList(reglasUsuario));
        int respuesta = id3.reglaContiene(arl);
        if (respuesta == -1) JOptionPane.showMessageDialog(this, "No se ha podido predecir nada con esos datos");
        else if (respuesta == 0) JOptionPane.showMessageDialog(this, "LA RESPUESTA ES: SI");
        else if (respuesta == 1) JOptionPane.showMessageDialog(this, "LA RESPUESTA ES: NO");
    }
    // -1 = No se puede contruir una afirmación
    // 0 = La respuesta es "SI"
    // 1 = La respuesta es "NO"
    public boolean buscaReglaRec(String[] atributo_valor, Nodo nodo, int i) {
        return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainWindow().setVisible(true);
               
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField atributosField;
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonConsulta;
    private javax.swing.JTextField consultaUsuario;
    private javax.swing.JTextField ejemploField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea resultadoArea;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables

}
