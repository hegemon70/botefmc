package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Bote extends AppCompatActivity {
    Button btnVolver;
    String bbdd;
    int version;
    boolean debug;
    SQLiteDatabase db;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bote);
        btnVolver=(Button)findViewById(R.id.btnVolverlyb);

        // RECOJO LOS DATOS DEL INTENT
        Bundle datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        bbdd=datos.getString("BBDD");
        version=datos.getInt("VERSION");

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void muestraBote()
    {
        calculaBote();
        String resultado="";
        for(Participante participante : arlParticipantes)
        {
            if(participante.isBooEsPositivo()){
                resultado +=resultado+" ";
            }

        }
    }
    private void calculaBote()
    {
            leeParticipantes();
            leeActividades();
        int numP=arlParticipantes.size();
        //TODO bucle que recorra cada actividad y suma o reste a los participantes
        for(Actividad actividad:arlActividades)
        {
            repartePrecioActividad(actividad);
        }


    }

    private void repartePrecioActividad(Actividad actividad){
        //int numParticipantes=arlParticipantes.size();
        Float derrama=actividad.getFloPrecio()/arlParticipantes.size();
        for (Participante participante : arlParticipantes) {
            if (actividad.getIntIdPagador()==participante.getId())//es el pagador
            {
                float haber = actividad.getFloPrecio()- derrama;
                participante.setFloSaldo( participante.getFloSaldo()+ haber);
            }
            else//no es le pagador
            {
               participante.setFloSaldo(participante.getFloSaldo()-derrama);
            }
        }

    }
    private Boolean leeParticipantes()//(boolean test)
    {
        boolean exito=false;
        try{
            Conexion conexion = new Conexion(this,bbdd,null,version);
            db=conexion.getReadableDatabase();
            Cursor c=db.rawQuery("SELECT * FROM participantes",null);
            Toast.makeText(this,"hay "+c.getCount()+" participantes",Toast.LENGTH_LONG).show();
            if (c.moveToFirst()){
                //int i=0;
                do{
                    //construimos un objeto participante que tenga id: 0, nombre: 1    ,saldo:2      ,icono:3
                    Participante partiAct =new Participante(c.getInt(0),c.getString(1),c.getFloat(2),c.getInt(3));
                    arlParticipantes.add(partiAct);
                }while(c.moveToNext());
            }
            db.close();

        }catch(Exception e){
            if (debug)
                Toast.makeText(this, "fallo al leer participantes "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            db.close();
        }
        if (arlParticipantes.size()!=0)
            exito=true;

        return exito;
    }

    private Boolean leeActividades()
    {
        Boolean exito=false;
        try{
            Conexion conexion = new Conexion(this,bbdd,null,version);
            db=conexion.getReadableDatabase();

            Cursor c=db.rawQuery("SELECT * FROM actividades",null);
            Toast.makeText(this,"hay "+c.getCount()+" actividades",Toast.LENGTH_LONG).show();
            if (c.getCount()!=0)
            {
                if (c.moveToFirst()){
                    // int i=0;
                    do{
                        //construimos un objeto actividad IdActividad: 0 ,Nombre: 1,IdPagador: 2, Precio: 3, Icono: 4
                        Actividad actividad  =new Actividad(c.getInt(0),c.getString(1),c.getInt(2),c.getFloat(3),c.getInt(4));
                        arlActividades.add(actividad);
                    }while(c.moveToNext());
                }
            }
            db.close();

        }catch(Exception e){
            if (debug)
                Toast.makeText(this, "fallo al leer actividades "+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            db.close();
        }

        if (arlActividades.size()!=0)
            exito=true;

        return exito;
    }

}
