package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ActividadNueva extends AppCompatActivity implements View.OnClickListener{
    Button btnVolver,btnInsertar;
    EditText ediNombre,ediPrecio;
    Spinner spPagadores;
    Participante pagador;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    String bbdd;
    int version;
    SQLiteDatabase db;
    Bundle datos;
    Boolean debug;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nueva);

        btnInsertar=(Button)findViewById(R.id.btnInsertarlyan);
        btnVolver=(Button)findViewById(R.id.btnVolverlyan);
        spPagadores=(Spinner)findViewById(R.id.spiPagadorlyan);
        ediNombre=(EditText)findViewById(R.id.ediActividadlyan);
        ediPrecio=(EditText)findViewById(R.id.ediEuroslyan);
        //recojo bundle
        datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        bbdd=datos.getString("BBDD");
        version=datos.getInt("VERSION");

        leeParticipantes();
        //declaro vector del tamaño del arraylist
        String vecNombres[]=new String[arlParticipantes.size()];
        for (int i=0;i<arlParticipantes.size();i++){
            vecNombres[i]=arlParticipantes.get(i).getStrNombre();
        }
        /*
        for(Participante cursor: arlParticipantes)
        {
            vecNonbre[]
        }*/

        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,vecNombres);
        spPagadores.setAdapter(adaptador);
        spPagadores.setSelection(0);


        spPagadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //strPagador=String.valueOf(parent.getItemAtPosition(position));
                if (debug)
                    Toast.makeText(getApplicationContext(), String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
                pagador=arlParticipantes.get(position);

                /*
                while(! insertaActividad(arlParticipantes.get(position)) ){
                    Toast.makeText(getApplicationContext(), "rellena todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnInsertar.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnInsertarlyan:
                Toast.makeText(this, "aqui insertamos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnVolverlyan:
                finish();
                break;
        }
    }
    /*
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
        else{
            Participante vacio = new Participante("No hay Participantes",0f);
            arlParticipantes.add(vacio);
        }

    }*/
  private Boolean insertaActividad(){
      Boolean exito=false;
      if (camposValidados())//todos los campos correctos
      {

      }

      return exito;
  }
  private Boolean camposValidados()
  {   Boolean exito=false;
      Boolean hayPagador,hayNombre,hayPrecio;
      hayPagador=false;
      hayNombre=false;
      hayPrecio=false;

      if (pagador!=null)//hay un pagador elegido
          hayPagador=true;

      if (ediPrecio.getText().length()!=0)
      {
          
      }

      if(hayPagador&&hayNombre&&hayPrecio)
          exito=true;

      return exito;
  }


    private void leeParticipantes()//(boolean test)
    {

        Conexion conexion = new Conexion(this,bbdd,null,version);
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
    }
}
