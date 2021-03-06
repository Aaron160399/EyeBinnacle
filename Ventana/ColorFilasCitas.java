/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import JDBC.ConsultaJDBC;
import JDBC.VentaJDBC;
import POJO.ConsultaPOJO;
import POJO.VentaPOJO;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author aaron
 */
public class ColorFilasCitas extends DefaultTableCellRenderer{
    int nFilas;
    VentaPOJO ventaPojo;
    ConsultaPOJO consultaPojo;

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        String id = jtable.getValueAt(i, 0).toString();
        
        try {
            consultaPojo = ConsultaJDBC.consultar(id);
            boolean haCobrado = VentaJDBC.seHaCobrado(consultaPojo.getIdCita());
            if (haCobrado == true) {
                setBackground(Color.CYAN);
            } else if (consultaPojo.getEstatus().equalsIgnoreCase("sin comenzar")) {
                setBackground(Color.WHITE);
            } else if (consultaPojo.getEstatus().equalsIgnoreCase("cancelada")) {
                setBackground(Color.RED);
            } else if (consultaPojo.getEstatus().equalsIgnoreCase("finalizada")) {
                setBackground(Color.GREEN);
            }
        } catch (Exception e) {
            
        }
        
        return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
    }
    
}
