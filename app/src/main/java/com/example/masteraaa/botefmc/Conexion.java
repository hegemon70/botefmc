package com.example.masteraaa.botefmc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import static java.sql.DriverManager.println;

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
            ",idParticipante INTEGER " +
            ",precio REAL" +
            ",icono INTEGER," +
            "FOREIGN KEY (idParticipante) REFERENCES participantes(id))";

    String SQLDeleteParticipantes="DROP TABLE if exists participantes";
    String SQLDeleteActividad="DROP TABLE if exists actividades";

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try{
            db.execSQL(SQLParticipantes);
            db.execSQL(SQLActividad);
            //seed(db);
        }catch(Exception e){
            println("->"+e.getMessage().toString());
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQLDeleteActividad);
        db.execSQL(SQLDeleteParticipantes);

        db.execSQL(SQLParticipantes);
        db.execSQL(SQLActividad);
        //seed(db);
    }



}
