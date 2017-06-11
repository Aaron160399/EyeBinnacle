/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import JDBC.ClienteJDBC;
import JDBC.ConsultaJDBC;
import POJO.ClientePOJO;
import POJO.ConsultaPOJO;
import ch.randelshofer.quaqua.QuaquaLookAndFeel;
import ch.randelshofer.quaqua.QuaquaLookAndFeel15;
import ch.randelshofer.quaqua.jaguar.Quaqua15JaguarLookAndFeel;
import ch.randelshofer.quaqua.leopard.Quaqua15LeopardCrossPlatformLookAndFeel;
import ch.randelshofer.quaqua.tiger.Quaqua15TigerLookAndFeel;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author aaron
 */
public class VerCita extends javax.swing.JFrame {
    JFrame menuSecre;
    JButton botonPres;
    MenuSecretaria menuSecretaria;
    int id2;
    TextAutoCompleter nombres;
    ClientePOJO clientePOJO;
    ConsultaPOJO consultaPOJO;
    /**
     * Creates new form VerCita
     */
    public VerCita() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
    }
    
    public VerCita(JFrame menu, JButton boton, MenuSecretaria menuSecretaria2, int id) {
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setOpacity(0);
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setLocation(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-(this.getSize().width/2), 
                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(this.getSize().height/2));
        menuSecre = menu;
        botonPres = boton;
        menuSecretaria = menuSecretaria2;
        jToolBar1.setFloatable(false);
        id2 = id;
        cargarInformacion();
        
        nombres = new TextAutoCompleter(nombre, new AutoCompleterCallback() {
            @Override
            public void callback(Object o) {
                Object a = nombres.findItem(o);
                clientePOJO = (ClientePOJO) a;
                nombre.setText(clientePOJO.getNombre());
                apellido.setText(clientePOJO.getApellidos());
                telefono.setText(clientePOJO.getTelefono());
                celular.setText(clientePOJO.getCelular());
            }
        });
        ClienteJDBC.cargarCompleter(nombres);
        jCheckBoxMenuItem1.setSelected(false);
        jCheckBoxMenuItem2.setSelected(false);
        jCheckBoxMenuItem3.setSelected(false);
        jCheckBoxMenuItem4.setSelected(false);
        jCheckBoxMenuItem5.setSelected(false);
    }
    
    public void cargarInformacion(){
        consultaPOJO = ConsultaJDBC.consultar(String.valueOf(id2));
        ClientePOJO clientePOJO = ClienteJDBC.consultar(String.valueOf(consultaPOJO.getCliente_idCliente()));
        nombre.setText(clientePOJO.getNombre());
        apellido.setText(clientePOJO.getApellidos());
        telefono.setText(clientePOJO.getTelefono());
        celular.setText(clientePOJO.getCelular());
        celular.setText(clientePOJO.getCelular());
        jDateChooser1.setDate(consultaPOJO.getFecha());
        jTextField1.setText(consultaPOJO.getHoraInicio()+"");
        jTextField3.setText(consultaPOJO.getVisita());
        jTextField2.setText(consultaPOJO.getAseguradora_empresa());
        jTextArea1.setText(consultaPOJO.getAsunto());
        if (consultaPOJO.getEstatus().equalsIgnoreCase("Sin comenzar")) {
            jComboBox1.setSelectedIndex(0);
        } else if (consultaPOJO.getEstatus().equalsIgnoreCase("Finalizada")) {
            jComboBox1.setSelectedIndex(1);
        } else if (consultaPOJO.getEstatus().equalsIgnoreCase("Cancelada")) {
            jComboBox1.setSelectedIndex(2);
        }
    }
    
    public void cargarInformacionAEditar(){
        consultaPOJO.setIdCita(id2);
        
        if (jCheckBoxMenuItem1.isSelected()) {
            consultaPOJO.setCliente_idCliente(clientePOJO.getIdCliente());
        } else {
            consultaPOJO.setCliente_idCliente(consultaPOJO.getCliente_idCliente());
        }
        
        if (jCheckBoxMenuItem2.isSelected()) {
            consultaPOJO.setFecha(jDateChooser1.getDate());
        } else {
            consultaPOJO.setFecha(consultaPOJO.getFecha());
        }
        
        if (jCheckBoxMenuItem3.isSelected()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            java.sql.Time horaInicio = null;
            java.util.Date hora = null;

            try {
                hora = (java.util.Date)simpleDateFormat.parse(jTextField1.getText());
            } catch (ParseException ex) {
                Logger.getLogger(VerCita.class.getName()).log(Level.SEVERE, null, ex);
            }
            horaInicio = new java.sql.Time(hora.getTime());
            consultaPOJO.setHoraInicio(horaInicio);
        } else {
            consultaPOJO.setHoraInicio(consultaPOJO.getHoraInicio());
        }
        
        consultaPOJO.setAsunto(jTextArea1.getText());
        consultaPOJO.setEstatus(jComboBox1.getSelectedItem().toString());
        consultaPOJO.setVisita(jTextField3.getText());
        consultaPOJO.setAseguradora_empresa(jTextField2.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        apellido = new javax.swing.JTextField();
        telefono = new javax.swing.JTextField();
        celular = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem5 = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(376, 630));
        setMinimumSize(new java.awt.Dimension(376, 630));
        setPreferredSize(new java.awt.Dimension(376, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Apellido(s)");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Teléfono Casa/Empresa");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Celular");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Fecha");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Hora");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Asunto");

        nombre.setEditable(false);

        apellido.setEditable(false);

        telefono.setEditable(false);

        celular.setEditable(false);
        celular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celularActionPerformed(evt);
            }
        });

        jTextField1.setEditable(false);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Visita");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Aseguradora/Empresa");

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Estatus");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sin comenzar", "Finalizada", "Cancelada" }));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/guardar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(apellido, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(telefono))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(celular)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(22, 22, 22)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 164, 356, 430));

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/salida.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 376, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/fondoVerCita.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jMenu2.setText("Editar");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Cliente");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem1);

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("Fecha");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem2);

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("Hora");
        jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem3);

        jCheckBoxMenuItem4.setSelected(true);
        jCheckBoxMenuItem4.setText("Tipo de visita");
        jCheckBoxMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem4);

        jCheckBoxMenuItem5.setSelected(true);
        jCheckBoxMenuItem5.setText("Asunto");
        jCheckBoxMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void celularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celularActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Funciones funciones = new Funciones();
        funciones.Desaparecer(this,menuSecre);
        menuSecretaria.estadoBotones();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        cargarInformacionAEditar();        
        if (ConsultaJDBC.actualizar(consultaPOJO) == true) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa");
                menuSecretaria.cargarTabla();
            } else {
                System.out.println("Cita no actualizada");
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem1.isSelected()) {
            nombre.setEditable(true);
        } else if (jCheckBoxMenuItem1.isSelected() == false){
            nombre.setEditable(false);
        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

    private void jCheckBoxMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem3ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem3.isSelected()) {
            jTextField1.setEditable(true);
            jTextField1.setText(null);
        } else if (jCheckBoxMenuItem3.isSelected() == false){
            jTextField1.setEditable(false);
            jTextField1.setText(consultaPOJO.getHoraInicio()+"");
        }
    }//GEN-LAST:event_jCheckBoxMenuItem3ActionPerformed

    private void jCheckBoxMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem4ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem4.isSelected()) {
            jTextField3.setEditable(true);
            jTextField2.setEditable(true);
            jTextField3.setText(null);
            jTextField2.setText(null);
        } else if (jCheckBoxMenuItem4.isSelected() == false){
            jTextField3.setEditable(false);
            jTextField2.setEditable(false);
            jTextField3.setText(consultaPOJO.getVisita());
            jTextField2.setText(consultaPOJO.getAseguradora_empresa());
        }
    }//GEN-LAST:event_jCheckBoxMenuItem4ActionPerformed

    private void jCheckBoxMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem5ActionPerformed
        // TODO add your handling code here:
        if (jCheckBoxMenuItem5.isSelected()) {
            jTextArea1.setEditable(true);
            jTextArea1.setText(null);
        } else {
            jTextArea1.setEditable(false);
            jTextArea1.setText(consultaPOJO.getAsunto());
        }
    }//GEN-LAST:event_jCheckBoxMenuItem5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
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
            java.util.logging.Logger.getLogger(VerCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new Quaqua15LeopardCrossPlatformLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(VerCita.class.getName()).log(Level.SEVERE, null, ex);
                }
                new VerCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellido;
    private javax.swing.JTextField celular;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem5;
    private javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField telefono;
    // End of variables declaration//GEN-END:variables
}
