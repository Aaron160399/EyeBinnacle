/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ProductoPOJO;
import POJO.Venta_has_Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aaron
 */
public class Venta_has_ProductoJDBC {
    private static final String TABLE = "venta_has_producto";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE
            + "(Venta_idVenta, Producto_idProducto) VALUES (?,?)";
    private static final String SQL_OBTENER_PRODUCTOS = "SELECT Producto.numeroIdentificacion FROM Producto, Venta "
            + "INNER JOIN Venta_has_Producto "
            + "ON venta.idVenta = Venta_has_Producto.Venta_idVenta "
            + "WHERE Producto.idProducto = Venta_has_Producto.Producto_idProducto and Venta.idVenta = ?";
    
    public static boolean insertar(Venta_has_Producto pojo) {
        Connection con = null;
        PreparedStatement ps = null;
        int x;
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setInt(1, pojo.getVenta_idVenta());
            ps.setInt(2, pojo.getProducto_idProducto());
            
            x = ps.executeUpdate();
            if (x == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al insertar "+e);
            return false;
        } finally {
            Conexion.close(con);
            Conexion.close(ps);
        }
    }
    
    public static DefaultTableModel cargarTabla(String id) {
        Connection con = null;
        PreparedStatement st = null;
        DefaultTableModel dt = null;
        String encabezados[] = {"No. Serie", "Precio"};
        System.out.println("ID "+id);
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_OBTENER_PRODUCTOS);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                //Promocion_has_productoPOJO promocion_has_productoPOJO = inflaPOJO(rs);
                String id2 = rs.getString(1);
                ProductoPOJO productoPOJO = ProductoJDBC.consultar(id2);
                Object ob[] = new Object[2];
                ob[0] = productoPOJO.getNumeroIdentificacion();
                ob[1] = productoPOJO.getPrecioventa();
                
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
    public static String[] obtenerProductos(String id) {
        Connection con = null;
        PreparedStatement st = null;
        String productos[] = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_OBTENER_PRODUCTOS);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            rs.last();
            productos = new String[rs.getRow()];
            System.out.println(productos.length);
            rs.beforeFirst();
            while (rs.next()) {
                //Promocion_has_productoPOJO promocion_has_productoPOJO = inflaPOJO(rs);
                String numeroIdentificacion = rs.getString(1);
                System.out.println(numeroIdentificacion);
                ProductoPOJO productoPOJO = ProductoJDBC.consultar(numeroIdentificacion);
                productos[rs.getRow()-1] = productoPOJO.getNumeroIdentificacion()+"-$"+productoPOJO.getPrecioventa();
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al obtener productos " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return productos;
    }
    
}
