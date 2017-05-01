/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ClientePOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class ClienteJDBC {
        private static final String TABLE="Cliente";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (nombre, apellidos, telefono, celular, tipoCliente, ultimavisita, proximavisita) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE;
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idCliente=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET nombre=?, apellidos=?, telefono=?, celular=?, tipoCliente=?, ultimavisita=?, proximavisita=?  WHERE idCliente=?";
   
    public static int insertar(ClientePOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, pojo.getNombre());
            st.setString(2, pojo.getApellidos());
            st.setString(3, pojo.getTelefono());
            st.setString(4, pojo.getCelular());
            st.setString(5, pojo.getTipoCliente());
            st.setDate(6, pojo.getUltimavisita());
            st.setDate(7, pojo.getProximavisita());
            st.executeUpdate();
            while (rs.next()) {                
                id = rs.getInt(1);
            }
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
     public static boolean actualizar(ClientePOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
           
            st.setString(1, pojo.getNombre());
            st.setString(2, pojo.getApellidos());
            st.setString(3, pojo.getTelefono());
            st.setString(4, pojo.getCelular());
           st.setInt(5, pojo.getIdCliente());
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
        String encabezados[] = {"Id","Nombre","Apellidos","Telefono","Celular","TipoCliente","UltimaVisita","ProximaVisita"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[8];
                ClientePOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdCliente();
                ob[1] = pojo.getNombre();
                ob[2] = pojo.getApellidos();
                ob[3] = pojo.getTelefono();
                ob[4] = pojo.getCelular();
                ob[5] = pojo.getTipoCliente();
                ob[6] = pojo.getUltimavisita();
                ob[7] = pojo.getProximavisita();
                
                dt.addRow(ob);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    
    private static ClientePOJO inflaPOJO(ResultSet rs) {

       ClientePOJO pojo = new ClientePOJO();
        try {
            pojo.setIdCliente(rs.getInt("idCliente"));
            pojo.setNombre(rs.getString("nombre"));
            pojo.setApellidos(rs.getString("apellidos"));
            pojo.setTelefono(rs.getString("telefono"));
            pojo.setCelular(rs.getString("celular"));
            pojo.setTipoCliente(rs.getString("tipoCliente"));
            pojo.setUltimavisita(rs.getDate("ultimavisita"));
            pojo.setProximavisita(rs.getDate("proximavisita"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
