/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import JDBC.ClienteJDBC;
import JDBC.ConsultaJDBC;
import JDBC.VentaJDBC;
import JDBC.Venta_has_ProductoJDBC;
import POJO.ClientePOJO;
import POJO.ConsultaPOJO;
import POJO.VentaPOJO;
import ch.randelshofer.quaqua.QuaquaLookAndFeel;
import ch.randelshofer.quaqua.QuaquaLookAndFeel15;
import ch.randelshofer.quaqua.jaguar.Quaqua15JaguarLookAndFeel;
import ch.randelshofer.quaqua.leopard.Quaqua15LeopardCrossPlatformLookAndFeel;
import ch.randelshofer.quaqua.tiger.Quaqua15TigerLookAndFeel;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class TodasLosCobros extends javax.swing.JFrame {
    JFrame menuSecre;
    JButton botonPres;
    MenuSecretaria menuSecretaria;
    TextAutoCompleter nombres;
    ClientePOJO clientePOJO;
    ConsultaPOJO consultaPOJO;
    /**
     * Creates new form VerCita
     */
    public TodasLosCobros() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
    }
    
    public TodasLosCobros(JFrame menu, JButton boton, MenuSecretaria menuSecretaria2) {
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
        
        productos.setVisible(false);
        consulta.setVisible(false);
        cargaTabla();
    }
    
    public void cargaTabla(){
        jTable1.setModel(VentaJDBC.cargarTabla());
        pintarTabla();
    }
    public void pintarTabla(){
        ColorFilasPagos colorFilas = new ColorFilasPagos();
        jTable1.setDefaultRenderer(Object.class, colorFilas);
    }
    public java.util.Date obtenerFechaHoy(){
        Calendar calendar = Calendar.getInstance();
        int anho = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH)+1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        String fecha = anho+"-"+mes+"-"+dia;
        System.out.println(fecha);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaUTIL = null;
        try {
             fechaUTIL = simpleDateFormat.parse(fecha);
             return fechaUTIL;
        } catch (ParseException ex) {
            Logger.getLogger(TodasLosCobros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaUTIL;
    }
    
    public void cargarInformacion(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try {
            VentaPOJO ventaPOJO = VentaJDBC.consultar(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            tipoPago.setText(ventaPOJO.getTipopago());
            total.setText(decimalFormat.format(ventaPOJO.getTotal())+"");
            pagado.setText(decimalFormat.format(ventaPOJO.getPago())+"");
            double monto = Double.parseDouble(total.getText());
            double pagadoD = Double.parseDouble(pagado.getText());
            double restanteD = monto - pagadoD;
            if (restanteD <= 0) {
                jLabel6.setText("Cambio");
                restanteD = pagadoD - monto;
                restante.setText(decimalFormat.format(restanteD)+"");
                jCheckBox1.setEnabled(true);
            } else {
                jLabel6.setText("Restante");
                restante.setText(decimalFormat.format(restanteD)+"");
            }
            
            if (ventaPOJO.getTipo().equalsIgnoreCase("consulta")) {
                consulta.setVisible(true);
                productos.setVisible(false);
                jCheckBox1.setEnabled(false);
                consultaPOJO = ConsultaJDBC.consultar(String.valueOf(ventaPOJO.getConsulta_idCita()));
                clientePOJO = ClienteJDBC.consultar(String.valueOf(consultaPOJO.getCliente_idCliente()));
                ordenConsulta.setText(consultaPOJO.getIdCita()+"");
                paciente.setText(clientePOJO.getNombre());
                telefono.setText(clientePOJO.getTelefono());
                celular.setText(clientePOJO.getCelular());
                if (ventaPOJO.getEstado().equalsIgnoreCase("pagado")) {
                    jButton2.setEnabled(false);
                } else {
                    jButton2.setEnabled(true);
                }
            } else if (ventaPOJO.getTipo().equalsIgnoreCase("producto")) {
                consulta.setVisible(false);
                productos.setVisible(true);
                jCheckBox1.setEnabled(true);
                String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                jTable2.setModel(Venta_has_ProductoJDBC.cargarTabla(id));
                tipoDeMica.setText(ventaPOJO.getTipoMica());
                precioDeMica.setText(ventaPOJO.getPrecioMica()+"");
                receptor.setText(ventaPOJO.getReceptor());
                if (ventaPOJO.getEstado().equalsIgnoreCase("pagado y entregado")) {
                    jButton2.setEnabled(false);
                    jCheckBox1.setEnabled(false);
                } else {
                    jButton2.setEnabled(true);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en los cobros (tabla) "+e);
            System.out.println("No hay selección");
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tipoPago = new javax.swing.JTextField();
        total = new javax.swing.JTextField();
        pagado = new javax.swing.JTextField();
        restante = new javax.swing.JTextField();
        productos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        tipoDeMica = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        receptor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        precioDeMica = new javax.swing.JTextField();
        consulta = new javax.swing.JPanel();
        ordenConsulta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        paciente = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        telefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        celular = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        pago = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        restante1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(630, 630));
        setMinimumSize(new java.awt.Dimension(630, 630));
        setPreferredSize(new java.awt.Dimension(630, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setHorizontalScrollBar(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Tipo pago");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Total");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Pagado");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Restante");

        tipoPago.setEditable(false);

        total.setEditable(false);

        pagado.setEditable(false);

        restante.setEditable(false);
        restante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(restante, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(pagado)
                            .addComponent(total))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pagado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(restante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        productos.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Tipo de mica(s)");

        tipoDeMica.setEditable(false);
        tipoDeMica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoDeMicaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Receptor");

        receptor.setEditable(false);
        receptor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receptorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Precio de mica(s)");

        precioDeMica.setEditable(false);
        precioDeMica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioDeMicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productosLayout = new javax.swing.GroupLayout(productos);
        productos.setLayout(productosLayout);
        productosLayout.setHorizontalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tipoDeMica)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(precioDeMica, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                    .addGroup(productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(receptor, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        productosLayout.setVerticalGroup(
            productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoDeMica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precioDeMica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(receptor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        consulta.setBackground(new java.awt.Color(255, 255, 255));

        ordenConsulta.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Orden de consulta");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Paciente");

        paciente.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Teléfono");

        telefono.setEditable(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Celular");

        celular.setEditable(false);

        javax.swing.GroupLayout consultaLayout = new javax.swing.GroupLayout(consulta);
        consulta.setLayout(consultaLayout);
        consultaLayout.setHorizontalGroup(
            consultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(consultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ordenConsulta)
                    .addComponent(paciente)
                    .addGroup(consultaLayout.createSequentialGroup()
                        .addGroup(consultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(telefono)
                    .addComponent(celular))
                .addContainerGap())
        );
        consultaLayout.setVerticalGroup(
            consultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(consultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ordenConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/guardar.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pagoKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Pago");

        restante1.setEditable(false);
        restante1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restante1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Restante");

        jCheckBox1.setText("Entrega de producto");
        jCheckBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(restante1, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addComponent(pago))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addGap(8, 8, 8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(restante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1))))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(consulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel4);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 57, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        );
        jLayeredPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jLayeredPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 390, 440));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Orden", "Paciente", "Fecha"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTable1MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Nombre");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 270, 430));

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

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/fondoTodosCobros.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 630, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Funciones funciones = new Funciones();
        funciones.Desaparecer(this,menuSecre);
        menuSecretaria.estadoBotones();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseExited

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        cargarInformacion();
        pago.setText(null);
        restante1.setText(null);
    }//GEN-LAST:event_jTable1MouseReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        VentaPOJO ventaPOJO = new VentaPOJO();
        VentaPOJO ventaPOJO2 = VentaJDBC.consultar(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
        if (ventaPOJO2.getEstado().equalsIgnoreCase("pagado") && jCheckBox1.isSelected()) {
            ventaPOJO.setPago(ventaPOJO2.getPago());
            ventaPOJO.setReceptor(ventaPOJO2.getReceptor());
            ventaPOJO.setCambio(ventaPOJO2.getCambio());
            ventaPOJO.setEstado("pagado y entregado");
            ventaPOJO.setIdVenta(ventaPOJO2.getIdVenta());
            if (VentaJDBC.actualizarPago(ventaPOJO) == true) {
            JOptionPane.showMessageDialog(null, "Actualización exitosa");
            cargaTabla();
            } else {
                System.out.println("Venta no actualizada");
            }
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            int id = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
            Double pagadoS = Double.parseDouble(pagado.getText());
            Double pagoS = Double.parseDouble(pago.getText());
            double pagoTotal = pagadoS + pagoS;
            String receptorA = receptor.getText();
            String estado = null;
            double cambio = 0;
            if (jLabel15.getText().equalsIgnoreCase("Cambio")) {
                cambio = Double.parseDouble(restante1.getText());
                estado = "pagado";
            } else if (jLabel15.getText().equalsIgnoreCase("Cambio") && jCheckBox1.isSelected()) {
                cambio = Double.parseDouble(restante1.getText());
                estado = "pagado y entregado";
            } else if (jLabel15.getText().equalsIgnoreCase("Restante")) {
                cambio = 0;
                estado = "anticipo";
            }
            ventaPOJO.setPago(pagoTotal);
            ventaPOJO.setReceptor(receptorA);
            ventaPOJO.setEstado(estado);
            ventaPOJO.setCambio(cambio);
            ventaPOJO.setIdVenta(id);
            if (VentaJDBC.actualizarPago(ventaPOJO) == true) {
                JOptionPane.showMessageDialog(null, "Actualización exitosa");
                cargaTabla();
            } else {
                System.out.println("Venta no actualizada");
            }
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tipoDeMicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoDeMicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoDeMicaActionPerformed

    private void receptorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receptorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receptorActionPerformed

    private void restanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_restanteActionPerformed

    private void restante1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restante1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_restante1ActionPerformed

    private void precioDeMicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioDeMicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioDeMicaActionPerformed

    private void pagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pagoKeyReleased
        // TODO add your handling code here:
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        try {
                double restanteD = Double.parseDouble(restante.getText()) - Double.parseDouble(pago.getText());
            if (restanteD <= 0) {
                jLabel15.setText("Cambio");
                restanteD = Double.parseDouble(pago.getText()) - Double.parseDouble(restante.getText());
                restante1.setText(decimalFormat.format(restanteD));
                jCheckBox1.setEnabled(true);
            } else {
                jLabel15.setText("Restante");
                restante1.setText(decimalFormat.format(restanteD));
                jCheckBox1.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println("Está vacío el textfield");
        }
        
    }//GEN-LAST:event_pagoKeyReleased

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
            java.util.logging.Logger.getLogger(TodasLosCobros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TodasLosCobros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TodasLosCobros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TodasLosCobros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new Quaqua15LeopardCrossPlatformLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(TodasLosCobros.class.getName()).log(Level.SEVERE, null, ex);
                }
                new TodasLosCobros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField celular;
    private javax.swing.JPanel consulta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField ordenConsulta;
    private javax.swing.JTextField paciente;
    private javax.swing.JTextField pagado;
    private javax.swing.JTextField pago;
    private javax.swing.JTextField precioDeMica;
    private javax.swing.JPanel productos;
    private javax.swing.JTextField receptor;
    private javax.swing.JTextField restante;
    private javax.swing.JTextField restante1;
    private javax.swing.JTextField telefono;
    private javax.swing.JTextField tipoDeMica;
    private javax.swing.JTextField tipoPago;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
