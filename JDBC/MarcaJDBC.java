/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.MarcaPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class MarcaJDBC {
     Connection con;
    Statement st;
    ResultSet rs;
   
    private static final String TABLE="Marca";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (nombre) VALUES (?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE+ " WHERE idMarca = ?";
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idMarca=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET nombre=? WHERE idMarca=?";
    private static final String SQL_SEARCH="SELECT * FROM "+TABLE+" WHERE nombre=?";
   
    public static int insertar(MarcaPOJO pojo) {
        
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, pojo.getNombre());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al insertar " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
            id = obtenerID(pojo.getNombre());
            System.out.println(id);
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
     public static boolean actualizar(MarcaPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
            st.setString(1, pojo.getNombre());;
            st.setInt(2, pojo.getIdMarca());
           
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
                Object ob[] = new Object[5];
                MarcaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdMarca();
                ob[1] = pojo.getNombre();
                dt.addRow(ob);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Marca" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    public static int obtenerID(String nombre) {
        Connection con = null;
        PreparedStatement st = null;
        int id=0;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_SEARCH);
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                MarcaPOJO pojo = inflaPOJO(rs);
                id = pojo.getIdMarca();
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla ID" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return id;
    }
    
    public static MarcaPOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        MarcaPOJO pojo = new MarcaPOJO();
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
    
    private static MarcaPOJO inflaPOJO(ResultSet rs) {

       MarcaPOJO pojo = new MarcaPOJO();
        try {
            pojo.setIdMarca(rs.getInt("idMarca"));
            pojo.setNombre(rs.getString("nombre"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo marca " + ex);
        }
        return pojo;
    }
}
