/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.sql.Date;

/**
 *
 * @author salva
 */
public class ClientePOJO {
    private int IdCliente;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String celular;
    private String tipoCliente;
    private Date proximavisita;
    private Date ultimavisita;

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int IdCliente) {
        this.IdCliente = IdCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Date getProximavisita() {
        return proximavisita;
    }

    public void setProximavisita(Date proximavisita) {
        this.proximavisita = proximavisita;
    }

    public Date getUltimavisita() {
        return ultimavisita;
    }

    public void setUltimavisita(Date ultimavisita) {
        this.ultimavisita = ultimavisita;
    }
    

    
}