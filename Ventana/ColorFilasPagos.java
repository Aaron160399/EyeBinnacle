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
public class ColorFilasPagos extends DefaultTableCellRenderer{
    int nFilas;
    VentaPOJO ventaPojo;
    ConsultaPOJO consultaPojo;

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        String id = jtable.getValueAt(i, 0).toString();
        
        try {
            ventaPojo = VentaJDBC.consultar(id);
            if (ventaPojo.getEstado().equalsIgnoreCase("Anticipo")) {
                setBackground(Color.LIGHT_GRAY);
            } else if (ventaPojo.getEstado().equalsIgnoreCase("Pagado")) {
                setBackground(new Color(121, 255, 130));
            } else if (ventaPojo.getEstado().equalsIgnoreCase("Pagado y entregado")) {
                setBackground(Color.MAGENTA);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
    }
    
}
