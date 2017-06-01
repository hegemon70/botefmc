package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Participantes extends AppCompatActivity {
    ListView listaPadrePart;
    Button btnVolver;
    SQLiteDatabase db;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        listaPadrePart=(ListView)findViewById(R.id.lisParticipanteslyp);
        btnVolver=(Button)findViewById(R.id.btnVolverlyp);


        rellenaArrayParticipantes(false);

        Toast.makeText(this,"hay "+arlParticipantes.size()+" participantes",Toast.LENGTH_LONG).show();
        final AdaptadorParticipante adaptador = new AdaptadorParticipante(this,arlParticipantes);
        listaPadrePart.setAdapter(adaptador);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void rellenaArrayParticipantes(boolean test){
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
        else{
            Conexion conexion = new Conexion(this,"BoteDB",null,1);
            db=conexion.getReadableDatabase();
            Cursor c=db.rawQuery("SELECT * FROM Participantes",null);
            Toast.makeText(this,"hay "+c.getCount()+" participantes",Toast.LENGTH_LONG).show();
            if (c.moveToFirst()){
                int i=0;
                do{
                    //construimos un objeto participante que tenga id: 0, nombre: 1    ,saldo:2      ,icono:3
                    Participante partiAct =new Participante(c.getInt(0),c.getString(1),c.getFloat(2),c.getInt(3));
                    arlParticipantes.add(partiAct);
                }while(c.moveToNext());
            }
            db.close();
           /* Participante vacio = new Participante("No hay Participantes",0f);
            arlParticipantes.add(vacio);*/
        }

    }
}
