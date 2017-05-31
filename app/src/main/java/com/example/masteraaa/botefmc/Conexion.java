package com.example.masteraaa.botefmc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by fernando on 31/05/2017.
 */

public class Conexion extends SQLiteOpenHelper {
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();
    ArrayList<Participante> arlParticipantes = new ArrayList();

    String SQLParticipantes="CREATE TABLE participantes(id INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",nombre VARCHAR(50)" +
            ",saldo REAL" +
            ",icono INTEGER)";

    String SQLActividad="CREATE TABLE actividades(id INTEGER PRIMARY KEY AUTOINCREMENT" +
            ",nombre VARCHAR(50)" +
            ",idParticipante INTEGER, FOREIGN KEY (idParticipante) REFERENCES participantes(id)" +
            ",precio REAL" +
            ",icono INTEGER)";

    String SQLDeleteParticipantes="DROP TABLE if exists participantes";
    String SQLDeleteActividad="DROP TABLE if exists actividades";

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQLParticipantes);
        db.execSQL(SQLActividad);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQLDeleteActividad);
        db.execSQL(SQLDeleteParticipantes);

        db.execSQL(SQLParticipantes);
        db.execSQL(SQLActividad);
    }


    private String[] rellenoBBDD(boolean test)
    {
        String vecSQLInsert[] = new String[2];

        rellenoParticipantes(test);
        rellenoActividades(test);/*
        for(int i=0; i>arlParticipantes;i++)
        {

        }*/
        return vecSQLInsert;
    }
    private void rellenoActividades(boolean test)
    {
        if(test){
            Actividad act =new Actividad("concierto",1,300.45f,R.drawable.actividad);
            Actividad act1 =new Actividad("comilona",1,400.45f,R.drawable.actividad);
            Actividad act2 =new Actividad("visita guiada Castillo",1,45f,R.drawable.actividad);
            Actividad act3 =new Actividad("cena Primer dia",1,215.43f,R.drawable.actividad);
            arlActividades.add(act);
            arlActividades.add(act1);
            arlActividades.add(act2);
            arlActividades.add(act3);
        }
        else
        {
            Actividad vacia =new Actividad("sin actividades",0,0f);
            arlActividades.add(vacia);
        }

    }
    private void rellenoParticipantes(boolean test){
        if (test) {
            Participante par1 = new Participante("Fernando", 0f, R.drawable.usuario_bn);
            Participante par2 = new Participante("Luis", 0f, R.drawable.usuario_bn);
            Participante par3 = new Participante("Tomas", 0f, R.drawable.usuario_bn);
            Participante par4 = new Participante("Guillermo", 0f, R.drawable.usuario_bn);
            Participante par5 = new Participante("Toño", 0f, R.drawable.usuario_bn);
            arlParticipantes.add(par1);
            arlParticipantes.add(par2);
            arlParticipantes.add(par3);
            arlParticipantes.add(par4);
            arlParticipantes.add(par5);
        }
        else
        {
            Participante vacio = new Participante("No hay Participantes",0f);
            arlParticipantes.add(vacio);
        }

    }
}
