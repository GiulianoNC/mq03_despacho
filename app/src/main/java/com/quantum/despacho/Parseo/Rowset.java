package com.quantum.despacho.Parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rowset {


    @SerializedName("CANTIDAD")
    @Expose
    private int cantidad;
    @SerializedName("NRO_CLIENTE")
    @Expose
    private String nroCliente;
    @SerializedName("ORDEN")
    @Expose
    private String orden;
    @SerializedName("TIPO")
    @Expose
    private String tipo;
    @SerializedName("CLIENTE")
    @Expose
    private String cliente;
    @SerializedName("DEPOSITO")
    @Expose
    private String deposito;
    @SerializedName("FECHA")
    @Expose
    private String fecha;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(String nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Rowset(int cantidad, String nroCliente, String orden, String tipo, String cliente, String deposito, String fecha) {
        this.cantidad = cantidad;
        this.nroCliente = nroCliente;
        this.orden = orden;
        this.tipo = tipo;
        this.cliente = cliente;
        this.deposito = deposito;
        this.fecha = fecha;
    }

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("deposito")
    @Expose
    private String deposito1;



    public Rowset(String username, String password, String deposito1) {
        this.username = username;
        this.password = password;
        this.deposito1 = deposito1;
    }
}
