/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.sql.Timestamp;


/**
 *
 * @author salva
 */
public class VentaPOJO {
    private int idVenta;
    private int Usuario_idUsuario;
    private int Consulta_idCita;
    private double total;
    private double pago;
    private double cambio;
    private String receptor;
    private Timestamp fecha;
    private String orden;
    private String tipopago;
    private String tipo;
    private String tipoMica;
    private double precioMica;
    private String estado;

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getUsuario_idUsuario() {
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(int Usuario_idUsuario) {
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPago() {
        return pago;
    }

    public void setPago(double pago) {
        this.pago = pago;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getTipopago() {
        return tipopago;
    }

    public void setTipopago(String tipopago) {
        this.tipopago = tipopago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getConsulta_idCita() {
        return Consulta_idCita;
    }

    public void setConsulta_idCita(int Consulta_idCita) {
        this.Consulta_idCita = Consulta_idCita;
    }

    public String getTipoMica() {
        return tipoMica;
    }

    public void setTipoMica(String tipoMica) {
        this.tipoMica = tipoMica;
    }

    public double getPrecioMica() {
        return precioMica;
    }

    public void setPrecioMica(double precioMica) {
        this.precioMica = precioMica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
}
