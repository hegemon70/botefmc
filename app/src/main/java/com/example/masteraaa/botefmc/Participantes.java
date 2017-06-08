package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.util.Log.println;

public class Participantes extends AppCompatActivity implements View.OnClickListener
{
    TextView txvSeparador;
    ListView listaPadrePart;
    Button btnVolver,btnAñadir;
    EditText edtNombre;
    String bbdd;
    int version;
    boolean debug;
    SQLiteDatabase db;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        txvSeparador=(TextView)findViewById(R.id.separadorParticipanteslyp);
        listaPadrePart = (ListView) findViewById(R.id.lisParticipanteslyp);
        btnVolver = (Button) findViewById(R.id.btnVolverlyp);
        btnAñadir = (Button) findViewById(R.id.btnAnyadirlyp);
        edtNombre = (EditText) findViewById(R.id.ediNuevolyp);
        //en el caso de que haya varios botones la clase se implementa
        //view.OnClickListener
        //y en onCreate se le pasa this como argumento
        btnVolver.setOnClickListener(this);
        btnAñadir.setOnClickListener(this);

        // RECOJO LOS DATOS DEL INTENT
        Bundle datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        bbdd=datos.getString("BBDD");
        version=datos.getInt("VERSION");
        if(!leeParticipantes()){
            Participante vacio = new Participante("No hay Participantes",0f);
            arlParticipantes.add(vacio);
        }
        //rellenaArrayParticipantes(false);
        if(debug)
            Toast.makeText(this, "hay " + arlParticipantes.size() + " participantes", Toast.LENGTH_LONG).show();

        final AdaptadorParticipante adaptador = new AdaptadorParticipante(this, arlParticipantes);
        listaPadrePart.setAdapter(adaptador);
    }
        @Override
       public void onClick(View v)
       {
           switch (v.getId())
           {
               case R.id.btnVolverlyp:
                        finish();
                        break;
               case R.id.btnAnyadirlyp:
                      if(añadeParticipante(edtNombre.getText().toString())){
                          finish();
                      }
                      else
                          Toast.makeText(this, "introduce nombre del Compañero", Toast.LENGTH_SHORT).show();

                       break;
           }
       }


    private Boolean añadeParticipante(String txtNombre)
    {   Boolean exito=false;

        //Toast.makeText(this, "entra en añadeParticipante", Toast.LENGTH_SHORT).show();
        if (txtNombre.length()!=0)
        {
            Participante par = new Participante(txtNombre, 0f, R.drawable.usuario_bn);
            arlParticipantes.add(par);
            int i = arlParticipantes.size()-1;//i es el indice del ultimo añadido
            String SQLIPar="";
            SQLIPar+="INSERT INTO participantes (nombre,saldo,icono) VALUES ("
                + "\""+ arlParticipantes.get(i).getStrNombre() +"\","
                +  arlParticipantes.get(i).getFloSaldo() + ","
                +  arlParticipantes.get(i).getIntIcono() + "); ";
            try {
                Conexion conexion = new Conexion(this,bbdd,null,version);
                db=conexion.getWritableDatabase();
                db.execSQL(SQLIPar);//aqui rellenamos Tabla participantes
                db.close();
                exito=true;
            }
            catch (Exception e)
            {
                if (debug)
                {
                    Toast.makeText(this, "fallo en la query " + (i + 1) + " ;" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    String fallo="fallo en la query " + (i + 1) + " ;" + e.getMessage().toString();
                    println(Log.ERROR,"fallo",fallo);
                }
                return exito;//false
            }
        }
        else
        {
            exito=false;
        }
        return exito;
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
}
