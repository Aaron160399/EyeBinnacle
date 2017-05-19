/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.MarcaPOJO;
import POJO.ProductoPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author salva
 */
public class ProductoJDBC {
    static Statement sentencia;
    static ResultSet resultado;
    private static final String TABLE="Producto";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (Marca_idMarca, Proveedores_idProveedores, numeroIdentificacion, caracteristicas, precio, existente) VALUES (?,?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE+ " WHERE numeroIdentificacion = ?";
    private static final String SQL_QUERY_ALL = "SELECT * FROM " + TABLE ;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idProducto=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET Marca_idMarca=?, Proveedores_idProveedores=?, numeroIdentificacion=?, caracteristicas=? WHERE idProducto=?";
   
    public static int insertar(ProductoPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getMarca_idMarca());
            st.setInt(2, pojo.getProveedores_idProveedores());
            st.setString(3, pojo.getNumeroIdentificacion());
            st.setString(4, pojo.getCaracteristica());
            st.setDouble(5, pojo.getPrecioventa());
            st.setBoolean(6, pojo.isExistente());
            id = st.executeUpdate();
            System.out.println(id);
        } catch (Exception e) {
            System.out.println("Error al insertar " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
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
     
    public static ProductoPOJO consultar(String numeroidentificacion) {
        Connection con = null;
        PreparedStatement st = null;
        ProductoPOJO pojo = new ProductoPOJO();
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY);
            st.setString(1, numeroidentificacion);
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
            st = con.prepareStatement("SELECT * FROM marca ORDER BY idMarca");
            ResultSet rs = st.executeQuery();
            combo.addElement("Seleccionar marca");
            while (rs.next()) {
                String valor = rs.getString("nombre");
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
    
    public static DefaultMutableTreeNode cargarTree() {
        Connection con = null;
        PreparedStatement st = null;
        PreparedStatement marcasST = null;
        DefaultMutableTreeNode productos = new DefaultMutableTreeNode("Lentes");
        DefaultMutableTreeNode marcas[];
        try {
            con = Conexion.getConnection();
            marcasST = con.prepareStatement("SELECT * FROM marca ORDER BY nombre ASC");
            ResultSet marcasRS = marcasST.executeQuery();
            marcasRS.last();
            int tamanho = marcasRS.getRow();
            marcasRS.beforeFirst();
            marcas = new DefaultMutableTreeNode[tamanho];
            while (marcasRS.next()) {
                int pos = marcasRS.getInt("idMarca")-1;
                marcas[pos] = new DefaultMutableTreeNode(marcasRS.getString("nombre"));
                st = con.prepareStatement("SELECT * FROM producto WHERE Marca_idMarca ="+marcasRS.getInt("idMarca"));
                ResultSet rs = st.executeQuery();
                while (rs.next()) {                    
                    ProductoPOJO productoPOJO = new ProductoPOJO();
                    productoPOJO = inflaPOJO(rs);
                    DefaultMutableTreeNode productoAnhadido = new DefaultMutableTreeNode(productoPOJO.getNumeroIdentificacion());
                    marcas[pos].add(productoAnhadido);
                }
                productos.add(marcas[pos]);
            }
            marcasRS.close();
        } catch (Exception e) {
            System.out.println("Error al cargar el tree" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return productos;
    }
    
    private static ProductoPOJO inflaPOJO(ResultSet rs) {

       ProductoPOJO pojo = new ProductoPOJO();
        try {
            pojo.setIdProducto(rs.getInt("idProducto"));
            pojo.setMarca_idMarca(rs.getInt("Marca_idMarca"));
            pojo.setProveedores_idProveedores(rs.getInt("Proveedores_idProveedores"));
            pojo.setNumeroIdentificacion(rs.getString("NumeroIdentificacion"));
            pojo.setCaracteristica(rs.getString("Caracteristicas"));
            pojo.setPrecioventa(rs.getDouble("precio"));
            pojo.setExistente(rs.getBoolean("existente"));
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
