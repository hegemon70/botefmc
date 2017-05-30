package com.example.masteraaa.botefmc;

/**
 * Created by fernando on 30/05/2017.
 */

public class Participante {

    private String strNombre;
    private int id;
    private float floSaldo;
    private boolean booEsPositivo;


    public Participante(String nombre,float saldo){
        this.strNombre=nombre;
        this.floSaldo=saldo;
        this.booEsPositivo=true;
        this.id=0;

    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFloSaldo() {
        return floSaldo;
    }

    public void setFloSaldo(float floSaldo) {
        this.floSaldo = floSaldo;
    }

    public boolean isBooEsPositivo() {
        return booEsPositivo;
    }

    public void setBooEsPositivo(boolean booEsPositivo) {
        this.booEsPositivo = booEsPositivo;
    }
}
