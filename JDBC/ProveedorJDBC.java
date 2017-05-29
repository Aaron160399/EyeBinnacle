/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ProveedorPOJO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class ProveedorJDBC {
    private static final String TABLE="Proveedor";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (nombre, apellidos, empresa, telefono) VALUES (?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE+" WHERE idProveedores = ?";
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idproveedores=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET nombre=?, apellidos=?, empresa=?, telefono=? WHERE idProveedores=?";
   
    public static int insertar(ProveedorPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, pojo.getNombre());
            st.setString(2, pojo.getApellidos());
            st.setString(3, pojo.getEmpresa());
            st.setString(4, pojo.getTelefono());
            id = st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al insertar " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
            Conexion.close(rs);
        }
        return id;
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
     public static boolean actualizar(ProveedorPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
           st.setInt(1, pojo.getIdProveedores());
            st.setString(2, pojo.getNombre());
            st.setString(2, pojo.getApellidos());
            st.setString(2, pojo.getEmpresa());
            st.setString(2, pojo.getTelefono());
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
        String encabezados[] = {"Id","Nombre"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[2];
                ProveedorPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdProveedores();
                ob[1] = pojo.getNombre()+" "+pojo.getApellidos();
                dt.addRow(ob);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Proveedor " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    
    public static ProveedorPOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        ProveedorPOJO pojo = new ProveedorPOJO();
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                pojo = inflaPOJO(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar  " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return pojo;
    }
    
    public static DefaultComboBoxModel cargarCombo() {
        Connection con = null;
        PreparedStatement st = null;
        DefaultComboBoxModel combo = null;
        try {
            combo = new DefaultComboBoxModel();
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT * FROM proveedor ORDER BY idProveedores");
            ResultSet rs = st.executeQuery();
            combo.addElement("Seleccionar proveedor");
            while (rs.next()) {
                String valor = rs.getString("nombre")+ " "+ rs.getString("apellidos");
                combo.addElement(valor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR Combo");
        } finally {
            try {
                st.close();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR CLOSE");
            }
        }
        return combo;
    }
    
    private static ProveedorPOJO inflaPOJO(ResultSet rs) {

       ProveedorPOJO pojo = new ProveedorPOJO();
        try {
            pojo.setIdProveedores(rs.getInt("idProveedores"));
            pojo.setNombre(rs.getString("nombre"));
            pojo.setApellidos(rs.getString("apellidos"));
            pojo.setEmpresa(rs.getString("empresa"));
            pojo.setTelefono(rs.getString("telefono"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
