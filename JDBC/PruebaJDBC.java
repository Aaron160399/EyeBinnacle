/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ProveedorPOJO;
import POJO.PruebaPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class PruebaJDBC {
    
    private static final String TABLE = "Prueba";

    private static final String SQL_INSERT = "Insert into " + TABLE
            + "(nombre) values (?)";

    private static final String SQL_DELETE = "Delete from " + TABLE
            + " where idprueba=?";

    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;

    private static final String SQL_UPDATE = "Update " + TABLE
            + " nombre=? where idprueba = ?";

    private static final String SQL_QUERY = "Select * from " + TABLE + " where idprueba=?";
    
    
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
                PruebaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdprueba();
                ob[1] = pojo.getNombre();
                
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
     private static PruebaPOJO inflaPOJO(ResultSet rs) {

       PruebaPOJO pojo = new PruebaPOJO();
        try {
            pojo.setIdprueba(rs.getInt("idPrueba"));
            pojo.setNombre(rs.getString("nombre"));
           
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
