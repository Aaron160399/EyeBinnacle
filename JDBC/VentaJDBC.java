/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ClientePOJO;
import POJO.ConsultaPOJO;
import POJO.VentaPOJO;
import Ventana.Funciones;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class VentaJDBC {
     private static final String TABLE="Venta";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (Usuario_idUsuario, Consulta_idCita, total, pago, cambio,"
            + "receptor, orden, tipopago, tipo, estado) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_INSERT2="INSERT INTO "+TABLE+" (Usuario_idUsuario, total, pago, cambio,"
            + "receptor, orden, tipopago, tipo, estado, tipomica, preciomica) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE;
    private static final String SQL_QUERY_ESPECIFICO="SELECT * FROM "+TABLE+" WHERE idVenta = ?";
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idVenta=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET Usuario_idUsuario=?, total=?, pago=?, cambio=?, receptor=?, fecha=?, orden=?, tipopago=?, tipo=? WHERE idVenta=?";
    private static final String SQL_UPDATE_PAGO="UPDATE "+TABLE+" SET pago=?, receptor=?, fecha=CURRENT_TIMESTAMP, cambio=? ,estado=? WHERE idVenta=?";
   
    public static int insertar(VentaPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getUsuario_idUsuario());
            st.setInt(2, pojo.getConsulta_idCita());
            st.setDouble(3, pojo.getTotal());
            st.setDouble(4, pojo.getPago());
            st.setDouble(5, pojo.getCambio());
            st.setString(6, pojo.getReceptor());
            st.setString(7, pojo.getOrden());
            st.setString(8, pojo.getTipopago());
            st.setString(9, pojo.getTipo());               
            st.setString(10, pojo.getEstado());               
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            while(rs.next()){
                id = rs.getInt(1);
            }
            System.out.println("ID Venta JDBC "+id);
        } catch (Exception e) {
            System.out.println("Error al insertar " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
            Conexion.close(rs);
        }
        return id;
    }
    
    public static int insertar2(VentaPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT2, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getUsuario_idUsuario());
            st.setDouble(2, pojo.getTotal());
            st.setDouble(3, pojo.getPago());
            st.setDouble(4, pojo.getCambio());
            st.setString(5, pojo.getReceptor());
            st.setString(6, pojo.getOrden());
            st.setString(7, pojo.getTipopago());
            st.setString(8, pojo.getTipo());               
            st.setString(9, pojo.getEstado());               
            st.setString(10, pojo.getTipoMica());               
            st.setDouble(11, pojo.getPrecioMica());               
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("ID Venta JDBC "+id);
        } catch (Exception e) {
            System.out.println("Error al insertar " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
            Conexion.close(rs);
        }
        return id;
    }
    
    public static VentaPOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        VentaPOJO pojo = new VentaPOJO();
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ESPECIFICO);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                pojo = inflaPOJO(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar venta " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return pojo;
    }
    
    public static String obtenerNumeroDeCitaActual(){
        Connection con = null;
        PreparedStatement st = null;
        VentaPOJO pojo = new VentaPOJO();
        int a = 0;
        String nConsulta = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY);
            ResultSet rs = st.executeQuery();
            rs.last();
            a = rs.getInt(1) + 1;
            nConsulta = String.valueOf(a);
        } catch (Exception e) {
            System.out.println("Error al consultar  " + e);
            nConsulta = "1";
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return nConsulta;
    }
    
    public static boolean seHaCobrado(int idCita){
        Connection con = null;
        PreparedStatement st = null;
        VentaPOJO pojo = new VentaPOJO();
        String nConsulta = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT * FROM venta WHERE Consulta_idCita = ?");
            st.setInt(1, idCita);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("Se ha cobrado");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al verificar cobro  " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return false;
    }
    
    public static int[] obtenerCantidades(String tipoPago, java.util.Date fecha1, java.util.Date fecha2){
        Connection con = null;
        PreparedStatement st = null;
        VentaPOJO pojo = new VentaPOJO();
        int totales[] = new int[2];
        try {
            java.sql.Date fecha1SQL = new java.sql.Date(fecha1.getTime());
            java.sql.Date fecha2SQL = new java.sql.Date(fecha2.getTime());
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT COUNT(idVenta), SUM(total) FROM venta WHERE tipopago = '"+tipoPago+"' and fecha >= '"+fecha1SQL+"' and fecha <= '"+fecha2SQL+" 23:59:59'");
            ResultSet rs = st.executeQuery();
            rs.next();
            totales[0] = rs.getInt(1);
            totales[1] = rs.getInt(2);
            System.out.println(totales[0]);
            System.out.println(totales[1]);
        } catch (Exception e) {
            System.out.println("Error al verificar cobro  " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return totales;
    }
    
    public static boolean eliminar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_DELETE);
            st.setString(1, id);
            int num = st.executeUpdate();
            if (num == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar = " + e);
            return false;
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return true;
    }
    
    public static boolean actualizar(VentaPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
            st.setInt(1, pojo.getUsuario_idUsuario());
            st.setDouble(2, pojo.getTotal());
            st.setDouble(3, pojo.getPago());
            st.setDouble(4, pojo.getCambio());
            st.setString(5, pojo.getReceptor());
            st.setTimestamp(6, pojo.getFecha());
            st.setString (7, pojo.getOrden());
            st.setString (8, pojo.getTipopago());
            st.setString (9, pojo.getTipo());
            st.setInt(10, pojo.getIdVenta());
           
            int x = st.executeUpdate();

            if (x == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar " + e);
            return false;
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return true;
    }
    public static boolean actualizarPago(VentaPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE_PAGO);
            st.setDouble(1, pojo.getPago());
            st.setString(2, pojo.getReceptor());
            st.setDouble(3, pojo.getCambio());
            st.setString(4, pojo.getEstado());
            st.setInt(5, pojo.getIdVenta());
           
            int x = st.executeUpdate();

            if (x == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar " + e);
            return false;
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return true;
    }
     
    public static DefaultTableModel cargarTabla() {
        Connection con = null;
        PreparedStatement st = null;
        DefaultTableModel dt = null;
        String encabezados[] = {"Id","Receptor/Pagador","Fecha","Tipo"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[4];
                VentaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdVenta();
                ob[1] = pojo.getReceptor();
                ob[2] = pojo.getFecha();
                ob[3] = pojo.getTipo();
                dt.addRow(ob);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Venta " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    public static PdfPTable cargarTablaPDF(java.util.Date fecha1, java.util.Date fecha2, int tabla) {
        Connection con = null;
        PreparedStatement st = null;
        String encabezadosConsulta[] = {"Orden","Orden de consulta","Fecha","Paciente","Total","Tipo de pago", "Estado"};
        String encabezadosProducto[] = {"Orden","Fecha","Receptor/Quien paga","Producto(s)","Total","Tipo de pago","Estado"};
        PdfPTable pdfPTable = null;
        try {
            con = Conexion.getConnection();
            java.sql.Date fecha1SQL = new java.sql.Date(fecha1.getTime());
            java.sql.Date fecha2SQL = new java.sql.Date(fecha2.getTime());
            ResultSet rs = null;
            Funciones funciones = new Funciones();
            if (tabla == 1) {
                pdfPTable = new PdfPTable(funciones.generarTabla(encabezadosConsulta));
                st = con.prepareStatement("SELECT * FROM venta WHERE tipo = 'consulta' and fecha >= '"+fecha1SQL+"' and fecha <= '"+fecha2SQL+" 23:59:59'");
                rs = st.executeQuery();
                while (rs.next()) {
                    String valores[] = new String[7];
                    VentaPOJO pojo = inflaPOJO(rs);
                    ConsultaPOJO consulta = ConsultaJDBC.consultar(String.valueOf(pojo.getConsulta_idCita()));
                    ClientePOJO cliente = ClienteJDBC.consultar(String.valueOf(consulta.getCliente_idCliente()));
                    valores[0] = pojo.getIdVenta()+"";
                    valores[1] = pojo.getConsulta_idCita()+"";
                    valores[2] = pojo.getFecha()+"";
                    valores[3] = cliente.getNombre()+" "+cliente.getApellidos();
                    valores[4] = "$"+pojo.getTotal()+"";
                    valores[5] = pojo.getTipopago();
                    valores[6] = pojo.getEstado();
                    for (int i = 0; i < valores.length; i++) {
                        PdfPCell pdfPCell = new PdfPCell(new Paragraph(valores[i]));
                        pdfPTable.addCell(pdfPCell);
                    }
                }
            } else {
                pdfPTable = new PdfPTable(funciones.generarTabla(encabezadosProducto));
                st = con.prepareStatement("SELECT * FROM venta WHERE tipo = 'producto' and fecha >= '"+fecha1SQL+"' and fecha <= '"+fecha2SQL+" 23:59:59'");
                rs = st.executeQuery();
                while (rs.next()) {
                    String valores[] = new String[7];
                    VentaPOJO pojo = inflaPOJO(rs);
                    ConsultaPOJO consulta = ConsultaJDBC.consultar(String.valueOf(pojo.getConsulta_idCita()));
                    ClientePOJO cliente = ClienteJDBC.consultar(String.valueOf(consulta.getCliente_idCliente()));
                    String productos[] = Venta_has_ProductoJDBC.obtenerProductos(String.valueOf(pojo.getIdVenta()));
                    valores[0] = pojo.getIdVenta()+"";
                    valores[1] = pojo.getFecha()+"";
                    valores[2] = pojo.getReceptor()+"";
                    valores[3] = "";
                    for (int i = 0; i < productos.length; i++) {
                            valores[3] = valores[3] + productos[i] +"\n"+pojo.getTipoMica()+"-$"+pojo.getPrecioMica();
                    }
                    valores[4] = "$"+pojo.getTotal()+"";
                    valores[5] = pojo.getTipopago();
                    valores[6] = pojo.getEstado();
                    for (int i = 0; i < valores.length; i++) {
                        PdfPCell pdfPCell = new PdfPCell(new Paragraph(valores[i]));
                        pdfPTable.addCell(pdfPCell);
                    }
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al generar PDF venta " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return pdfPTable;
    }
    
    private static VentaPOJO inflaPOJO(ResultSet rs) {

       VentaPOJO pojo = new VentaPOJO();
        try {
            pojo.setIdVenta(rs.getInt("idVenta"));
            pojo.setUsuario_idUsuario(rs.getInt("Usuario_idUsuario"));
            pojo.setConsulta_idCita(rs.getInt("Consulta_idCita"));
            pojo.setTotal(rs.getDouble("total"));
            pojo.setPago(rs.getDouble("pago"));
            pojo.setCambio(rs.getDouble("cambio"));
            pojo.setReceptor(rs.getString("receptor"));
            pojo.setFecha(rs.getTimestamp("fecha"));
            pojo.setOrden(rs.getString("Orden"));
            pojo.setTipopago(rs.getString("TipoPago"));
            pojo.setTipo(rs.getString("Tipo"));
            pojo.setEstado(rs.getString("Estado"));
            pojo.setTipoMica(rs.getString("tipomica"));
            pojo.setPrecioMica(rs.getDouble("preciomica"));
            
           
            
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
