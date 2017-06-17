/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import JDBC.ClienteJDBC;
import JDBC.ConsultaJDBC;
import JDBC.ProductoJDBC;
import JDBC.VentaJDBC;
import JDBC.Venta_has_ProductoJDBC;
import POJO.ClientePOJO;
import POJO.ConsultaPOJO;
import POJO.ProductoPOJO;
import POJO.UsuarioPOJO;
import POJO.VentaPOJO;
import POJO.Venta_has_Producto;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author aaron
 */
public class Ventas extends javax.swing.JFrame {
    JFrame menuSecre;
    JButton botonPres;
    MenuSecretaria menuSecretaria;
    UsuarioPOJO usuarioPOJO2;
    double total;
    /**
     * Creates new form Ventas
     */
    public Ventas() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        jToolBar1.setFloatable(false);
        setLocationRelativeTo(null);
    }
    
    public Ventas(JFrame menu, JButton boton, MenuSecretaria menuSecretaria2, UsuarioPOJO usuarioPOJO) {
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setOpacity(0);
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        jToolBar1.setFloatable(false);
        setLocation(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-(this.getSize().width/2), 
                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(this.getSize().height/2));
        menuSecre = menu;
        botonPres = boton;
        menuSecretaria = menuSecretaria2;
        cargaTablaProducto();
        TableColumnModel tableColumnModel = jTable4.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(106);
        tableColumnModel.getColumn(1).setPreferredWidth(106);
        tableColumnModel.getColumn(2).setPreferredWidth(106);
        
        jTextField8.setText(VentaJDBC.obtenerNumeroDeCitaActual());
        
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        String [] identi = {"ID","No. Identificación", "Precio"};
        defaultTableModel.setColumnIdentifiers(identi);
        jTable4.setModel(defaultTableModel);
        usuarioPOJO2 = usuarioPOJO;
        Pconsulta.setVisible(false);
        Pproductos.setVisible(false);
        
    }
    
    public Ventas(JFrame menu, JButton boton, MenuSecretaria menuSecretaria2, String id, UsuarioPOJO usuarioPOJO) {
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setOpacity(0);
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        jToolBar1.setFloatable(false);
        setLocation(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-(this.getSize().width/2), 
                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(this.getSize().height/2));
        menuSecre = menu;
        botonPres = boton;
        menuSecretaria = menuSecretaria2;
        cargaTablaProducto();
        TableColumnModel tableColumnModel = jTable4.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(106);
        tableColumnModel.getColumn(1).setPreferredWidth(106);
        tableColumnModel.getColumn(2).setPreferredWidth(106);
        
        jTextField8.setText(VentaJDBC.obtenerNumeroDeCitaActual());
        
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        String [] identi = {"ID","No. Identificación", "Precio"};
        defaultTableModel.setColumnIdentifiers(identi);
        jTable4.setModel(defaultTableModel);
        jFormattedTextField1.setEditable(true);
        jRadioButton6.setSelected(true);
        usuarioPOJO2 = usuarioPOJO;
        Pconsulta.setVisible(true);
        Pproductos.setVisible(false);
        llenaInformacion(id);
    }
    
    public void llenaInformacion (String id){
        ConsultaPOJO consultaPOJO = ConsultaJDBC.consultar(id);
        jTextField9.setText(consultaPOJO.getIdCita()+"");
        ClientePOJO clientePOJO = ClienteJDBC.consultar(String.valueOf(consultaPOJO.getCliente_idCliente()));
        jTextField1.setText(clientePOJO.getNombre() + " " + clientePOJO.getApellidos());
        jTextField2.setText(clientePOJO.getTelefono());
        jTextField3.setText(clientePOJO.getCelular());
    }
    
    public void calculaTotal(){
        int nLineas = jTable4.getRowCount();
        System.out.println(nLineas);
        total = 0;
        for (int i = 0; i < nLineas; i++) {
            total += Double.parseDouble(jTable4.getValueAt(i, 2).toString());
        }
        jFormattedTextField1.setText(total+"");
    }
    
    public void cargaTablaProducto(){
        jTable3.setModel(ProductoJDBC.cargarTabla());
        TableColumnModel tableColumnModel = jTable3.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(159);
        tableColumnModel.getColumn(1).setPreferredWidth(159);
    }
    
    public void agregar(){
        Object[] object = new Object[3];
        String id = jTable3.getValueAt(jTable3.getSelectedRow(), 1).toString();
        ProductoPOJO productoPOJO = ProductoJDBC.consultar(id);
        object[0] = productoPOJO.getIdProducto();
        object[1] = productoPOJO.getNumeroIdentificacion();
        object[2] = productoPOJO.getPrecioventa();
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTable4.getModel();
        defaultTableModel.addRow(object);
    }
    
    public void cerrarVentas(){
        Funciones funciones = new Funciones();
        funciones.Desaparecer(this,menuSecre);
    }
    
    public void cobrarConsulta(int idCita){
        VentaPOJO ventaPOJO = new VentaPOJO();
        ventaPOJO.setUsuario_idUsuario(usuarioPOJO2.getIdUsuario());
        ventaPOJO.setConsulta_idCita(idCita);
        ventaPOJO.setTotal(Double.parseDouble(jFormattedTextField1.getText()));
        ventaPOJO.setPago(Double.parseDouble(jFormattedTextField2.getText()));
        ventaPOJO.setCambio(Double.parseDouble(jFormattedTextField3.getText()));
        ventaPOJO.setReceptor(jTextField11.getText());
        ventaPOJO.setOrden(jTextField8.getText());
        if (jRadioButton8.isSelected()) {
            ventaPOJO.setTipopago("tarjeta con recibo");
        } else if (jRadioButton9.isSelected()) {
            ventaPOJO.setTipopago("tarjeta sin recibo");
        } else if (jRadioButton10.isSelected()) {
            ventaPOJO.setTipopago("efectivo con recibo");
        }
        ventaPOJO.setEstado("pagado");
        ventaPOJO.setTipo("consulta");
        
        int x = VentaJDBC.insertar(ventaPOJO);

        if (x != 0) {
            JOptionPane.showMessageDialog(null, "Venta finalizada\nGracias por su compra");
            cerrarVentas();
            menuSecretaria.estadoBotones();
        } else {
            JOptionPane.showMessageDialog(null, "CATASTROPHIC ERROR");
        }
    }
    
    public void cobrarProducto(){
        VentaPOJO ventaPOJO = new VentaPOJO();
        ventaPOJO.setUsuario_idUsuario(usuarioPOJO2.getIdUsuario());
        ventaPOJO.setTotal(Double.parseDouble(jFormattedTextField1.getText()));
        ventaPOJO.setPago(Double.parseDouble(jFormattedTextField2.getText()));
        if (jFormattedTextField3.getText().isEmpty()) {
            ventaPOJO.setCambio(0);
        } else {
            ventaPOJO.setCambio(Double.parseDouble(jFormattedTextField3.getText()));
        }
        ventaPOJO.setReceptor(jTextField11.getText());
        ventaPOJO.setOrden(jTextField8.getText());
        if (jRadioButton8.isSelected()) {
            ventaPOJO.setTipopago("tarjeta con recibo");
        } else if (jRadioButton9.isSelected()) {
            ventaPOJO.setTipopago("tarjeta sin recibo");
        } else if (jRadioButton10.isSelected()) {
            ventaPOJO.setTipopago("efectivo con recibo");
        }
        ventaPOJO.setTipoMica(jTextField4.getText());
        ventaPOJO.setPrecioMica(Double.parseDouble(jFormattedTextField4.getText()));
        Double monto = Double.parseDouble(jFormattedTextField1.getText());
        Double pago = Double.parseDouble(jFormattedTextField2.getText());
        if (pago < monto) {
            ventaPOJO.setEstado("anticipo");
        } else if (pago >= monto && jCheckBox1.isSelected() == false) {
            ventaPOJO.setEstado("pagado");
        } else if (pago >= monto && jCheckBox1.isSelected() == true) {
            ventaPOJO.setEstado("pagado y entregado");
        }
        ventaPOJO.setTipoMica(jTextField4.getText());
        ventaPOJO.setPrecioMica(Double.parseDouble(jFormattedTextField4.getText()));
        ventaPOJO.setTipo("producto");
        
        int idVenta = VentaJDBC.insertar2(ventaPOJO);
        System.out.println("Venta "+idVenta);
        DefaultTableModel modelo = (DefaultTableModel)jTable4.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Venta_has_Producto pojito = new Venta_has_Producto();
            ProductoPOJO productoPOJO = ProductoJDBC.consultar(jTable4.getValueAt(i, 1).toString());
            int idProducto = productoPOJO.getIdProducto();
            pojito.setProducto_idProducto(idProducto);
            pojito.setVenta_idVenta(idVenta);
            Venta_has_ProductoJDBC.insertar(pojito);
            ProductoJDBC productoJDBC = new ProductoJDBC();
            if(productoJDBC.eliminar(String.valueOf(productoPOJO.getIdProducto()), false)){
                System.out.println("Eliminado correctamente");
            }
        }

        if (idVenta != 0) {
            JOptionPane.showMessageDialog(null, "Venta finalizada\nGracias por su compra");
            cerrarVentas();
            menuSecretaria.estadoBotones();
        } else {
            JOptionPane.showMessageDialog(null, "CATASTROPHIC ERROR");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        PtipoConsulta = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        Pconsulta = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Pproductos = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField10 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jTextField4 = new javax.swing.JTextField();
        PinfoPago = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jRadioButton10 = new javax.swing.JRadioButton();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jRadioButton9 = new javax.swing.JRadioButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setRollover(true);

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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/guardar.png"))); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, -1));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        PtipoConsulta.setBackground(new java.awt.Color(255, 255, 255));
        PtipoConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de cobro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Tipo");

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setText("Cobro por consulta");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton7);
        jRadioButton7.setText("Venta de producto");
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("No. de orden");

        jTextField8.setEditable(false);

        javax.swing.GroupLayout PtipoConsultaLayout = new javax.swing.GroupLayout(PtipoConsulta);
        PtipoConsulta.setLayout(PtipoConsultaLayout);
        PtipoConsultaLayout.setHorizontalGroup(
            PtipoConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PtipoConsultaLayout.createSequentialGroup()
                .addGroup(PtipoConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton7)
                    .addComponent(jRadioButton6)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PtipoConsultaLayout.setVerticalGroup(
            PtipoConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PtipoConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(8, 8, 8)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Pconsulta.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Orden de consulta");

        jTextField9.setEditable(false);

        jButton6.setText("Llenar");
        jButton6.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Nombre");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Teléfono");

        jTextField3.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Celular");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout PconsultaLayout = new javax.swing.GroupLayout(Pconsulta);
        Pconsulta.setLayout(PconsultaLayout);
        PconsultaLayout.setHorizontalGroup(
            PconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PconsultaLayout.createSequentialGroup()
                .addGroup(PconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PconsultaLayout.createSequentialGroup()
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jButton6))
                    .addGroup(PconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel22)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        PconsultaLayout.setVerticalGroup(
            PconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PconsultaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Pproductos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Productos");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.setEnabled(false);
        jScrollPane3.setViewportView(jTable3);

        jTextField10.setEditable(false);

        jButton7.setText("Agregar");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "No. identificación", "Precio"
            }
        ));
        jTable4.setEnabled(false);
        jScrollPane4.setViewportView(jTable4);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setText("Tipo de mica");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setText("Costo");

        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PproductosLayout = new javax.swing.GroupLayout(Pproductos);
        Pproductos.setLayout(PproductosLayout);
        PproductosLayout.setHorizontalGroup(
            PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PproductosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PproductosLayout.createSequentialGroup()
                        .addComponent(jTextField10)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7))
                    .addGroup(PproductosLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PproductosLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PproductosLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4)))
                .addContainerGap())
        );
        PproductosLayout.setVerticalGroup(
            PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PproductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PinfoPago.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setText("Tarjeta con recibo");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Cambio");

        buttonGroup2.add(jRadioButton10);
        jRadioButton10.setText("Efectivo con recibo");

        jFormattedTextField3.setEditable(false);
        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        buttonGroup2.add(jRadioButton9);
        jRadioButton9.setText("Tarjeta sin recibo");
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Pago");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Monto");

        jFormattedTextField1.setEditable(false);
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Receptor");

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Tipo de pago");

        jCheckBox1.setText("Entrega de lentes");

        javax.swing.GroupLayout PinfoPagoLayout = new javax.swing.GroupLayout(PinfoPago);
        PinfoPago.setLayout(PinfoPagoLayout);
        PinfoPagoLayout.setHorizontalGroup(
            PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PinfoPagoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PinfoPagoLayout.createSequentialGroup()
                        .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel25)
                                .addComponent(jLabel27)
                                .addComponent(jLabel28)
                                .addComponent(jFormattedTextField1)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRadioButton9)
                            .addComponent(jRadioButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRadioButton8)
                            .addComponent(jLabel26)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        PinfoPagoLayout.setVerticalGroup(
            PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PinfoPagoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PinfoPagoLayout.createSequentialGroup()
                        .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PinfoPagoLayout.createSequentialGroup()
                                .addComponent(jRadioButton8)
                                .addGap(26, 26, 26))
                            .addComponent(jRadioButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton10))
                    .addGroup(PinfoPagoLayout.createSequentialGroup()
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(PinfoPagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PinfoPagoLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCheckBox1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Pconsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pproductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(PinfoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(Pconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Pproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PinfoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1.setLayer(Pconsulta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(Pproductos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(PinfoPago, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PtipoConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(PtipoConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(jPanel3);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 390, 440));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/fondoVentas.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 400, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        cerrarVentas();
        menuSecretaria.estadoBotones();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton6.isSelected()) {
            jTextField9.setEditable(true);
            jButton6.setEnabled(true);
            jTable3.setEnabled(false);
            jTable4.setEnabled(false);
            jTextField10.setEditable(false);
            jButton7.setEnabled(false);
            jFormattedTextField1.setEditable(true);
            Pconsulta.setVisible(true);
            Pproductos.setVisible(false);
        } else {
            
            jTextField9.setEditable(false);
            jButton6.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton7.isSelected()) {
            jTable3.setEnabled(true);
            jTable4.setEnabled(true);
            jTextField10.setEditable(true);
            jButton7.setEnabled(true);
            jTextField9.setEditable(false);
            jButton6.setEnabled(false);
            jFormattedTextField1.setEditable(false);
            Pconsulta.setVisible(false);
            Pproductos.setVisible(true);
        } else {
            jTextField9.setEditable(false);
            jButton6.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        agregar();
        calculaTotal();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jFormattedTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyReleased
        // TODO add your handling code here:
        try {
            double monto = Double.parseDouble(jFormattedTextField1.getText());
            double pago = Double.parseDouble(jFormattedTextField2.getText());
            double cambio = pago - monto;
            if (pago < monto) {
                jFormattedTextField3.setText(null);
            } else {
                jFormattedTextField3.setText(cambio + "");
            }
        } catch (Exception e) {
            jFormattedTextField3.setText(null);
        }
    }//GEN-LAST:event_jFormattedTextField2KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton6.isSelected()) {
            int id = Integer.parseInt(jTextField9.getText());
            System.out.println("Opcion 1");
            cobrarConsulta(id);
        } else if (jRadioButton7.isSelected()) {
            System.out.println("Opcion 2");
            cobrarProducto();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyReleased
        // TODO add your handling code here:
        double total2 = Double.parseDouble(jFormattedTextField4.getText());
        jFormattedTextField1.setText(total+total2+"");
    }//GEN-LAST:event_jFormattedTextField4KeyReleased

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
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pconsulta;
    private javax.swing.JPanel PinfoPago;
    private javax.swing.JPanel Pproductos;
    private javax.swing.JPanel PtipoConsulta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
