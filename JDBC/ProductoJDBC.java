/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ProductoPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class ProductoJDBC {
    static Statement sentencia;
    static ResultSet resultado;
    private static final String TABLE="Producto";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (Marca_idMarca, Proveedores_idProveedores, numeroIdentificacion, caracteristicas) VALUES (?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE;
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idProducto=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET Marca_idMarca=?, Proveedores_idProveedores=?, numeroIdentificacion=?, caracteristicas=? WHERE idProducto=?";
   
    public static int insertar(ProductoPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getMarca_idMarca());
            st.setInt(2, pojo.getProveedores_idProveedores());
            st.setString(3, pojo.getNumeroIdentificacion());
            st.setString(4, pojo.getCaracteristica());
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
    public static ArrayList<String>llenar_combo(){
    ArrayList<String> lista=new ArrayList<String>();
    String q="SELECT * FORM marca";
        try {
            resultado=sentencia.executeQuery(q);
            
        } catch (Exception e) {
        
        }
        try {
            while(resultado.next()){
            lista.add(resultado.getString("marca"));
            }
        } catch (Exception e) {
        }
    return lista;
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
 
     public static boolean actualizar(ProductoPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
            st.setInt(1, pojo.getMarca_idMarca());
            st.setInt(2, pojo.getProveedores_idProveedores());
            st.setString(3, pojo.getNumeroIdentificacion());
            st.setString(4, pojo.getCaracteristica());
            st.setInt(5, pojo.getIdProducto());
          
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
        String encabezados[] = {"Id","Nombre","Apellidos","Empresa","Telefono"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[5];
                ProductoPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdProducto();
                ob[1] = pojo.getMarca_idMarca();
                ob[2] = pojo.getProveedores_idProveedores();
                ob[3] = pojo.getNumeroIdentificacion();
                ob[4] = pojo.getCaracteristica();
                
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
    
    private static ProductoPOJO inflaPOJO(ResultSet rs) {

       ProductoPOJO pojo = new ProductoPOJO();
        try {
            pojo.setIdProducto(rs.getInt("idProducto"));
            pojo.setMarca_idMarca(rs.getInt("Marca_idMarca"));
            pojo.setProveedores_idProveedores(rs.getInt("Proveedores_idProveedores"));
            pojo.setNumeroIdentificacion(rs.getString("NumeroIdentificacion"));
            pojo.setCaracteristica(rs.getString("Caracteristicas"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
