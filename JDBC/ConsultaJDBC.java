/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ConsultaPOJO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class ConsultaJDBC {
     private static final String TABLE="Consulta";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (Cliente_idCliente, Usuario_idUsuario, fecha, horaInicio, asunto, proximaVisita, estatus, resultado) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE;
    private static final String SQL_QUERY_ALL = "Select * from " + TABLE;
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idCita=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET Cliente_idCliente=?, Usuario_idUsuario=?, fecha=?, horaInicio=?, asunto=?, proximaVisita=?, estatus=?, resultado=? WHERE idCita=?";
   
    public static int insertar(ConsultaPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getCliente_idCliente());
            st.setInt(2, pojo.getUsuario_idUsuario());
            st.setDate(3, (Date) pojo.getFecha());
            st.setTime(4, pojo.getHoraInicio());
            
            
            
            
            
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
     public static boolean actualizar(ConsultaPOJO pojo) {

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = Conexion.getConnection();
            //Recuerden que el últmo es el id
            st = con.prepareStatement(SQL_UPDATE);
            st.setInt(1, pojo.getCliente_idCliente());
            st.setInt(2, pojo.getUsuario_idUsuario());
           
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
                ConsultaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getCliente_idCliente();
                ob[1] = pojo.getUsuario_idUsuario();
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
    
    private static ConsultaPOJO inflaPOJO(ResultSet rs) {

       ConsultaPOJO pojo = new ConsultaPOJO();
        try {
            pojo.setIdCita(rs.getInt("idCita"));
            pojo.setCliente_idCliente(rs.getInt("Cliente_idCliente"));
            pojo.setUsuario_idUsuario(rs.getInt("Usuario_idUsuario"));
            pojo.setFecha(rs.getDate("fecha"));
            pojo.setHoraInicio(rs.getTime("horarioInicio"));
            pojo.setAsunto(rs.getString("Asunto"));
           
            
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo " + ex);
        }
        return pojo;
    }
}
