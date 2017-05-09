/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.UsuarioPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class UsuarioJDBC {
    private static final String TABLE="Usuario";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (nombre) VALUES (?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE+" WHERE idUsuario = ?";
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idMarca=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET nombre=? WHERE idMarca=?";

public static int insertar(UsuarioPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, pojo.getNombre());
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
     public static boolean actualizar(UsuarioPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
            st.setString(1, pojo.getNombre());;
            st.setString(1, pojo.getContrasena());;
            st.setInt(2, pojo.getIdUsuario());
           
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
                Object ob[] = new Object[3];
                UsuarioPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdUsuario();
                ob[1] = pojo.getNombre();
                ob[2] = pojo.getContrasena();
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
    
    public static UsuarioPOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        UsuarioPOJO pojo = new UsuarioPOJO();
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                pojo = inflaPOJO(rs);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar usuario " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return pojo;
    }
    
    public static int login (String usuario, String password){
        Connection con = null;
        int id = 0;
        try {
            String consult = "SELECT * FROM "+TABLE+" WHERE nombre = '"+usuario+"' and contrasena = '"+password+"'";
            con = Conexion.getConnection();
            PreparedStatement st = con.prepareStatement(consult);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {                
                id = rs.getInt("idUsuario");
            }
        } catch (Exception e) {
            System.out.println("Error on login "+e);
        }
        return id;
    }
    
    private static UsuarioPOJO inflaPOJO(ResultSet rs) {

       UsuarioPOJO pojo = new UsuarioPOJO();
        try {
            pojo.setIdUsuario(rs.getInt("idUsuario"));
            pojo.setNombre(rs.getString("nombre"));
            pojo.setContrasena(rs.getString("contrasena"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
