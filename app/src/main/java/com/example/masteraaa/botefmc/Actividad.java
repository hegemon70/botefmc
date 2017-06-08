package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fernando on 30/05/2017.
 */

public class Actividad {
    private int IdActividad;
    private String strNombre;
    private int intIdPagador;
    private float floPrecio;
    private int intIcono;



    public Actividad(String nombre, int idPagador, float precio)
    {
        this.strNombre=nombre;
        this.intIdPagador=idPagador;
        this.floPrecio=precio;
        this.IdActividad=0;
        this.intIcono=0;
    }

    public Actividad(String nombre, int idPagador, float precio,int icono)
    {
        this.strNombre=nombre;
        this.intIdPagador=idPagador;
        this.floPrecio=precio;
        this.IdActividad=0;
        this.intIcono=icono;
    }
    public Actividad(int idActividad,String nombre, int idPagador, float precio,int icono)
    {
        this.strNombre=nombre;
        this.intIdPagador=idPagador;
        this.floPrecio=precio;
        this.IdActividad=0;
        this.intIcono=icono;
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

    public String getStrPrecio() {
       String strPrecio="*";
        try{
            strPrecio=String.valueOf(floPrecio);
        }catch(Exception e){strPrecio="error cast float";}

        return strPrecio;
    }
    public int getIntIcono() {
        return intIcono;
    }

    public void setIntIcono(int intIcono) {
        this.intIcono = intIcono;
    }

    public String  getNombrePagador(SQLiteDatabase db,int idPagador){
        //this.intIdPagador
        String nombre="";
        String query="SELECT nombre FROM participantes WHERE id=";
        query += idPagador +" ;";
            try{
                Cursor cursor =db.rawQuery(query,null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    nombre= cursor.getString(0).toString();
                }
                else
                    nombre="";

            }
            catch(Exception e)
            {
                return nombre;
            }


        return nombre;
    }
}
