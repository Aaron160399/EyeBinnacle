/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;

import POJO.ClientePOJO;
import POJO.ConsultaPOJO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author salva
 */
public class ConsultaJDBC {
    
    static Calendar calendar = Calendar.getInstance();
    static int anho = calendar.get(Calendar.YEAR);
    static int mes = calendar.get(Calendar.MONTH)+1;
    static int dia = calendar.get(Calendar.DAY_OF_MONTH);
    private static final String TABLE="consulta";
    private static final String SQL_INSERT="INSERT INTO "+TABLE+" (Cliente_idCliente, Usuario_idUsuario, fecha, horaInicio, asunto,"
            + " estatus, visita, aseguradora_empresa) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_QUERY="SELECT * FROM "+TABLE + " WHERE idCoonsulta = ?";
    private static final String SQL_QUERY_CLIENTE="SELECT * FROM "+TABLE + " WHERE Cliente_idCliente = ?";
    private static final String SQL_QUERY_ALL = "SELECT * FROM " + TABLE+ " "
            + "WHERE fecha = '"+anho+"-"+mes+"-"+dia+"'";
    private static final String SQL_DELETE="DELETE FROM "+TABLE+" WHERE idCita=?";
    private static final String SQL_UPDATE="UPDATE "+TABLE+" SET Cliente_idCliente=?, fecha=?, horaInicio=?, asunto=?, "
            + "estatus=?, resultado=?, visita=?, aseguradora_empresa=? WHERE idCoonsulta=?";
    
    public static int insertar(ConsultaPOJO pojo) {
        Connection con = null;
        PreparedStatement st = null;
        int id = 0;
        ResultSet rs = null;
        try {
            java.sql.Date fecha = new java.sql.Date(pojo.getFecha().getTime());
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, pojo.getCliente_idCliente());
            st.setInt(2, pojo.getUsuario_idUsuario());
            st.setDate(3, fecha);
            st.setTime(4, pojo.getHoraInicio());
            st.setString(5, pojo.getAsunto());
            st.setString(6, pojo.getEstatus());
            st.setString(7, pojo.getVisita());
            st.setString(8, pojo.getAseguradora_empresa());
            id = st.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al insertar Cita" + e);
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
           java.sql.Date fecha = new java.sql.Date(pojo.getFecha().getTime());
           st.setInt(1, pojo.getCliente_idCliente());
           st.setDate(2, fecha);
           st.setTime(3, pojo.getHoraInicio());
           st.setString(4, pojo.getAsunto());
           st.setString(5, pojo.getEstatus());
           st.setString(6, pojo.getResultado());
           st.setString(7, pojo.getVisita());
           st.setString(8, pojo.getAseguradora_empresa());
           st.setInt(9, pojo.getIdCita());

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
    public static boolean actualizar2(ConsultaPOJO pojo) {
       Connection con = null;
       PreparedStatement st = null;
       try {
           con = Conexion.getConnection();
           //Recuerden que el últmo es el id
           st = con.prepareStatement("UPDATE "+TABLE+" SET resultado=? WHERE idCoonsulta=?");
           st.setString(1, pojo.getResultado());
           st.setInt(2, pojo.getIdCita());

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
     
     public static ConsultaPOJO consultar(String id) {
        Connection con = null;
        PreparedStatement st = null;
        ConsultaPOJO pojo = new ConsultaPOJO();
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
     
    public static DefaultTableModel cargarTabla() {
        Connection con = null;
        PreparedStatement st = null;
        DefaultTableModel dt = null;
        String encabezados[] = {"Id","Cliente","Hora"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_ALL);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[3];
                ConsultaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdCita();
                ClientePOJO clientePOJO = ClienteJDBC.consultar(String.valueOf(pojo.getCliente_idCliente()));
                ob[1] = clientePOJO.getNombre()+" "+clientePOJO.getApellidos();
                ob[2] = pojo.getHoraInicio();
                dt.addRow(ob);
            }
            rs.close();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Consulta" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    
    public static DefaultTableModel cargarTablaCliente(int id) {
        Connection con = null;
        PreparedStatement st = null;
        DefaultTableModel dt = null;
        String encabezados[] = {"Id","Fecha","Estatus"};
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_CLIENTE);
            st.setInt(1, id);
            dt = new DefaultTableModel();
            dt.setColumnIdentifiers(encabezados);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Object ob[] = new Object[3];
                ConsultaPOJO pojo = inflaPOJO(rs);
                ob[0] = pojo.getIdCita();
                ob[1] = pojo.getFecha();
                ob[2] = pojo.getEstatus();
                System.out.println(pojo.getEstatus());
                dt.addRow(ob);
            }
            rs.close();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error al cargar la tabla Consulta" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return dt;
    }
    
    public static String cargarExpediente(int id) {
        Connection con = null;
        PreparedStatement st = null;
        String expediente = "";
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement(SQL_QUERY_CLIENTE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ConsultaPOJO pojo = inflaPOJO(rs);
                if (pojo.getResultado() == null) {
                    expediente += "";
                } else {
                    expediente += pojo.getResultado()+"\n";
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al cargar el expediente" + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return expediente;
    }
    
    public static ConsultaPOJO obtenerUltimaVisita(int id) {
        Connection con = null;
        PreparedStatement st = null;
        ConsultaPOJO pojo = null;
        try {
            con = Conexion.getConnection();
            st = con.prepareStatement("SELECT * FROM consulta WHERE Cliente_idCliente = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.last();
            pojo = inflaPOJO(rs);
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            
            int mes = calendar.get(Calendar.MONTH)+1;
            java.util.Date fechaHoy = fecha.parse(calendar.get(Calendar.YEAR)+"-"+mes+"-"+calendar.get(Calendar.DAY_OF_MONTH));
            java.sql.Date fechaHoySql  = new java.sql.Date(fechaHoy.getTime());
            
            java.util.Date fechaPOJO = rs.getDate("fecha");
            java.sql.Date fechaPOJOSql = new java.sql.Date(fechaPOJO.getTime());
            
            System.out.println("fecha Hoy "+fechaHoySql);
            System.out.println("Fecha consulta"+fechaPOJOSql);
            if (fechaPOJOSql.before(fechaHoySql)) {
                pojo = inflaPOJO(rs);
                System.out.println(pojo.getFecha());
            } else if (fechaPOJOSql.after(fechaHoySql)||fechaPOJOSql.equals(fechaHoySql)) {
                while (rs.previous()){
                    pojo = inflaPOJO(rs);
                    fechaPOJO = rs.getDate("fecha");
                    fechaPOJOSql = new java.sql.Date(fechaPOJO.getTime());
                    System.out.println("SQL "+fechaHoySql);
                    if (fechaPOJOSql.before(fechaHoy)) {
                        break;
                    }
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error al obtener Ultima Visita " + e);
        } finally {
            Conexion.close(con);
            Conexion.close(st);
        }
        return pojo;
    }
    
    private static ConsultaPOJO inflaPOJO(ResultSet rs) {

       ConsultaPOJO pojo = new ConsultaPOJO();
        try {
            pojo.setIdCita(rs.getInt("idCoonsulta"));
            pojo.setCliente_idCliente(rs.getInt("Cliente_idCliente"));
            pojo.setUsuario_idUsuario(rs.getInt("Usuario_idUsuario"));
            pojo.setFecha(rs.getDate("fecha"));
            pojo.setHoraInicio(rs.getTime("horaInicio"));
            pojo.setAsunto(rs.getString("Asunto"));
            pojo.setVisita(rs.getString("Visita"));
            pojo.setAseguradora_empresa(rs.getString("Aseguradora_empresa"));
            pojo.setEstatus(rs.getString("Estatus"));
            pojo.setResultado(rs.getString("Resultado"));
           
            
        } catch (SQLException ex) {
            System.out.println("Error al inflar pojo Cita" + ex);
        }
        return pojo;
    }
}
