package com.example.masteraaa.botefmc;

/**
 * Created by fernando on 30/05/2017.
 */

public class Actividad {
    private int IdActividad;
    private String strNombre;
    private int intIdPagador;
    private float floPrecio;

    public Actividad(String nombre,int idPagador,float precio)
    {
        this.strNombre=nombre;
        this.intIdPagador=idPagador;
        this.floPrecio=precio;
        this.IdActividad=0;
    }

    public int getIntIdActividad() {
        return IdActividad;
    }

    public void setIntIdActividad(int intIdActividad) {
        this.IdActividad = intIdActividad;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public int getIntIdPagador() {
        return intIdPagador;
    }

    public void setIntIdPagador(int intIdPagador) {
        this.intIdPagador = intIdPagador;
    }

    public float getFloPrecio() {
        return floPrecio;
    }

    public void setFloPrecio(float floPrecio) {
        this.floPrecio = floPrecio;
    }
}
