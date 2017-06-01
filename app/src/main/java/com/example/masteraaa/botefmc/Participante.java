package com.example.masteraaa.botefmc;

/**
 * Created by fernando on 30/05/2017.
 */

public class Participante {

    private String strNombre;
    private int id;
    private float floSaldo;
    private boolean booEsPositivo;
    private int intIcono;


    public Participante(String nombre,float saldo){
        this.strNombre=nombre;
        this.floSaldo=saldo;
        this.booEsPositivo=true;
        this.id=0;
        this.intIcono=0;

    }

    public Participante(String strNombre, int id, float floSaldo) {
        this.strNombre = strNombre;
        this.id = id;
        this.floSaldo = floSaldo;
        this.intIcono=0;
        this.booEsPositivo = true;
    }

    public Participante(String strNombre, float floSaldo, int icono) {
        this.strNombre = strNombre;
        this.id = 0;
        this.floSaldo = floSaldo;
        this.intIcono=icono;
        this.booEsPositivo = true;
    }

    public Participante(int id,String strNombre, float floSaldo,int icono) {
        this.strNombre = strNombre;
        this.id = id;
        this.floSaldo = floSaldo;
        this.intIcono=icono;
        this.booEsPositivo = this.floSaldo >= 0;//false si < 0

    }

    public int getIntIcono() {
        return intIcono;
    }

    public void setIntIcono(int intIcono) {
        this.intIcono = intIcono;
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

    public void setBooEsPositivo(boolean boEsPositivo) {
        this.booEsPositivo = booEsPositivo;
    }

    public String getStrSaldo() {
        String strSaldo="*";
        try{
            strSaldo=String.valueOf(floSaldo);
        }catch(Exception e){strSaldo="error cast float";}

        return strSaldo;
    }

   /*
    private void calculaComoMostrar(){
        if (this.isBooEsPositivo()
                this.intIcono=
                )
    }
    */
}
