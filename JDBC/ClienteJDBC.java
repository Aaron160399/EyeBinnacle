/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ClientePOJO;
import com.mxrck.autocompleter.TextAutoCompleter;
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
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (nombre, apellidos, telefono, celular, tipoCliente) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE+ " WHERE idCliente = ?";
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idCliente=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET tipoCliente=? WHERE idCliente=?";
   
    public static int insertar(ClientePOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, pojo.getNombre());
            st.setString(2, pojo.getApellidos());
            st.setString(3, pojo.getTelefono());
            st.setString(4, pojo.getCelular());
            st.setString(5, pojo.getTipoCliente());
            id = st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al insertar Cliente" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return id;
    }
    
    public static int obtenerRecienInsertado() {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT * FROM cliente");
            rs = st.executeQuery();
            rs.last();
            ClientePOJO clientePOJO = new ClientePOJO();
            clientePOJO = inflaPOJO(rs);
            id = clientePOJO.getIdCliente();
            System.out.println(id);
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
            st.setString(1, pojo.getTipoCliente());
            st.setInt(2, pojo.getIdCliente());
            int x = st.executeUpdate();
            if (x == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar Cliente" + e);
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
        String encabezados[] = {"Id","Nombre","Apellidos"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[3];
                ClientePOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdCliente();
                ob[1] = pojo.getNombre();
                ob[2] = pojo.getApellidos();
                
                dt.addRow(ob);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Cliente " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    
    public static void cargarCompleter(TextAutoCompleter completador) {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT * FROM cliente");//Aquí cambia "persona" por el nombre de tu tabla
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                //Aquí crea el objeto de tu propia tabla en la que buscas información
                ClientePOJO pojo = new ClientePOJO(rs.getInt("idCliente"), rs.getString("nombre"), rs.getString("apellidos"), 
                        rs.getString("telefono"), rs.getString("celular"), rs.getString("tipoCliente"));
                    completador.addItem(pojo);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar el AutoCompleter " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
    }
    
    public static ClientePOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        ClientePOJO pojo = new ClientePOJO();
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
    
    private static ClientePOJO inflaPOJO(ResultSet rs) {

       ClientePOJO pojo = new ClientePOJO();
        try {
            pojo.setIdCliente(rs.getInt("idCliente"));
            pojo.setNombre(rs.getString("nombre"));
            pojo.setApellidos(rs.getString("apellidos"));
            pojo.setTelefono(rs.getString("telefono"));
            pojo.setCelular(rs.getString("celular"));
            pojo.setTipoCliente(rs.getString("tipoCliente"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
