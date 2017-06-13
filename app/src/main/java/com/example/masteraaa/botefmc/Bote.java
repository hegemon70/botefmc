package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bote extends AppCompatActivity {
    ImageView imgModoDemo;
    Button btnVolver;
    ListView lstMostrar;
    String bbdd;
    int version;
    boolean debug,demo;
    SQLiteDatabase db;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bote);

        imgModoDemo=(ImageView)findViewById(R.id.imgModoDemolyb);
        btnVolver=(Button)findViewById(R.id.btnVolverlyb);
        lstMostrar=(ListView)findViewById(R.id.lisBotelyb);

        // RECOJO LOS DATOS DEL INTENT
        Bundle datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        bbdd=datos.getString("BBDD");
        version=datos.getInt("VERSION");
        demo=datos.getBoolean("DEMOACTIVADA");

        if(demo)
        {
            imgModoDemo.setVisibility(View.VISIBLE);
        }
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        muestraBote();
    }

    private void muestraBote()
    {
        calculaBote();
        String resultado="";
        int numP=arlParticipantes.size();
        int numA=arlActividades.size();
        if (numP==0)
        {
            if (numA==0)
            {
                resultado="No hay Participantes ni Actividades ";
            }
        }


        for(Participante participante : arlParticipantes)
        {
            if(participante.isBooEsPositivo()){
                resultado +=" A "+ participante.getStrNombre()
                        +" le debe el Bote "+ participante.getStrSaldo()
                        + " euros ";
            }
            else
            {
                resultado +=participante.getStrNombre()
                        +" debe al bote "+ participante.getStrSaldo()
                        + " euros ";
                ;
            }
            resultado +="&";
        }


        String[] resultados=resultado.split("&");
        if (debug)
            Log.d("D",resultado);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,resultados);
        lstMostrar.setAdapter(adaptador);
    }
    private void calculaBote()
    {
            leeParticipantes();
            leeActividades();

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
