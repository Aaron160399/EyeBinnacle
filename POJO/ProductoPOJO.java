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
public class ProductoPOJO {
    private int idProducto;
    private int Marca_idMarca;
    private int Proveedores_idProveedores;
    private String numeroIdentificacion;
    private String caracteristica;
    private String imagen;
    private double precioventa;
    private boolean existente;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getMarca_idMarca() {
        return Marca_idMarca;
    }

    public void setMarca_idMarca(int Marca_idMarca) {
        this.Marca_idMarca = Marca_idMarca;
    }

    public int getProveedores_idProveedores() {
        return Proveedores_idProveedores;
    }

    public void setProveedores_idProveedores(int Proveedores_idProveedores) {
        this.Proveedores_idProveedores = Proveedores_idProveedores;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public double getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(double precioventa) {
        this.precioventa = precioventa;
    }

    public boolean isExistente() {
        return existente;
    }

    public void setExistente(boolean existente) {
        this.existente = existente;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
   
    
}
