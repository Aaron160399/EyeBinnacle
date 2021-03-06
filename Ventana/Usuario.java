/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import JDBC.ProveedorJDBC;
import JDBC.UsuarioJDBC;
import POJO.UsuarioPOJO;
import javax.swing.JOptionPane;
import JDBC.UsuarioJDBC;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author salva
 */
public class Usuario extends javax.swing.JFrame {

    /**
     * Creates new form Usuario
     */
    JFrame menuDoc2;
    MenuDoctora menuDoctora2;
    public Usuario() {
        initComponents();
        this.setTitle("Usuarios");
        cargarTabla();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
    }
    public Usuario(MenuDoctora menuDoctora, JFrame menuDoc) {
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setOpacity(0);
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        jToolBar1.setBackground(Color.WHITE);
        this.setTitle("Usuarios");
        cargarTabla();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menuDoctora2 = menuDoctora;
        menuDoc2 = menuDoc;
    }
    public void cargarTabla(){
        jTable1.setModel(UsuarioJDBC.cargarTabla());
        jTable1.setRowSorter(new TableRowSorter(jTable1.getModel()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(410, 689));
        setMinimumSize(new java.awt.Dimension(410, 689));
        setPreferredSize(new java.awt.Dimension(410, 689));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 300, -1));
        jPanel1.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 300, -1));

        jLabel1.setText("Usuario");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel2.setText("Contraseña");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 375, 320));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/guardar.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 620, -1, 30));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/eliminar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 620, -1, -1));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Primario");
        jPanel1.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, -1, -1));

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Secundario");
        jPanel1.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/fondoUsuario.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 410, 620));

        jToolBar1.setBackground(new java.awt.Color(204, 204, 204));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/salida.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jPanel1.add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String usario=jTextField1.getText();
        String contrasenha=jPasswordField1.getText();
        UsuarioPOJO usuarioPOJO = new UsuarioPOJO();
        usuarioPOJO.setNombre(usario);
        usuarioPOJO.setContrasena(contrasenha);
        if (jRadioButton1.isSelected()) {
            usuarioPOJO.setCategoria("Primario");
        } else if (jRadioButton2.isSelected()) {
            usuarioPOJO.setCategoria("Secundario");
        }
        
        int x=UsuarioJDBC.insertar(usuarioPOJO);
        if (x != 0) {
            JOptionPane.showMessageDialog(null, "Guardado");
            
            jTextField1.setText("");
            jPasswordField1.setText("");
            cargarTabla();
        } else {
            System.out.println("error");
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada=jTable1.getSelectedRow();
        if (filaSeleccionada==-1) {
            JOptionPane.showMessageDialog(this,"Selecciona primero una fila","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            String id=jTable1.getValueAt(filaSeleccionada,0).toString();
            if (UsuarioJDBC.eliminar(id)) {
                JOptionPane.showMessageDialog(this,"Se eliminó correctamente","Exito",JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
            }else{
                JOptionPane.showMessageDialog(this,"No se eliminó","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Funciones funciones = new Funciones();
        funciones.Desaparecer(this,menuDoc2);
        menuDoctora2.estadoBotones();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
