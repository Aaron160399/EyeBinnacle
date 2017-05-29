/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.Venta_has_Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author aaron
 */
public class Venta_has_ProductoJDBC {
    private static final String TABLE = "venta_has_producto";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE
            + "(Venta_idVenta, Producto_idProducto) VALUES (?,?)";
    
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
    
}
