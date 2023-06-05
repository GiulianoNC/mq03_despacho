package com.quantum.despacho.Parseo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parametros {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("ORDEN_TIPO")
    @Expose
    private String ordenTipo;
    @SerializedName("ORDEN_NRO")
    @Expose
    private String ordenNro;
    @SerializedName("LOTE")
    @Expose
    private String lote;
    @SerializedName("ITEM")
    @Expose
    private String item;
    @SerializedName("UBICACION")
    @Expose
    private String ubicacion;
    @SerializedName("CANTIDAD")
    @Expose
    private String cantidad;

    /**
     * No args constructor for use in serialization
     *
     */
    public Parametros() {
    }

    /**
     *
     * @param password
     * @param item
     * @param ubicacion
     * @param lote
     * @param cantidad
     * @param ordenTipo
     * @param ordenNro
     * @param username
     */
    public Parametros(String username, String password, String ordenTipo, String ordenNro, String lote, String item, String ubicacion, String cantidad) {
        super();
        this.username = username;
        this.password = password;
        this.ordenTipo = ordenTipo;
        this.ordenNro = ordenNro;
        this.lote = lote;
        this.item = item;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrdenTipo() {
        return ordenTipo;
    }

    public void setOrdenTipo(String ordenTipo) {
        this.ordenTipo = ordenTipo;
    }

    public String getOrdenNro() {
        return ordenNro;
    }

    public void setOrdenNro(String ordenNro) {
        this.ordenNro = ordenNro;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }



    @SerializedName("CONTADOR")
    @Expose
    private Integer contador;
    @SerializedName("MQ0301A_RULE_Repeating")
    @Expose
    private List<MQ0301ARULERepeating> mQ0301ARULERepeating = null;
    @SerializedName("jde__simpleMessage")
    @Expose
    private String jdeSimpleMessage;
    @SerializedName("jde__status")
    @Expose
    private String jdeStatus;




    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public List<MQ0301ARULERepeating> getMQ0301ARULERepeating() {
        return mQ0301ARULERepeating;
    }

    public void setMQ0301ARULERepeating(List<MQ0301ARULERepeating> mQ0301ARULERepeating) {
        this.mQ0301ARULERepeating = mQ0301ARULERepeating;
    }

    public String getJdeSimpleMessage() {
        return jdeSimpleMessage;
    }

    public void setJdeSimpleMessage(String jdeSimpleMessage) {
        this.jdeSimpleMessage = jdeSimpleMessage;
    }

    public String getJdeStatus() {
        return jdeStatus;
    }

    public void setJdeStatus(String jdeStatus) {
        this.jdeStatus = jdeStatus;
    }

}

