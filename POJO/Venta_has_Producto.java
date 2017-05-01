/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author salva
 */
public class Venta_has_Producto {
  private int Venta_idVenta;
  private int Producto_idProducto;
  private double preciounitario;

    public int getVenta_idVenta() {
        return Venta_idVenta;
    }

    public void setVenta_idVenta(int Venta_idVenta) {
        this.Venta_idVenta = Venta_idVenta;
    }

    public int getProducto_idProducto() {
        return Producto_idProducto;
    }

    public void setProducto_idProducto(int Producto_idProducto) {
        this.Producto_idProducto = Producto_idProducto;
    }

    public double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(double preciounitario) {
        this.preciounitario = preciounitario;
    }
  
}
