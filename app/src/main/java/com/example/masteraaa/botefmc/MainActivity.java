package com.example.masteraaa.botefmc;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBote,btnParticipante,btnActividades;
    SQLiteDatabase db;
    Boolean debug=true;
    Boolean reset=false;
    Boolean autoRelleno=false;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBote=(Button)findViewById(R.id.btnBotelym);
        btnParticipante=(Button)findViewById(R.id.btnParticipanteslym);
        btnActividades=(Button)findViewById(R.id.btnActividadlym);
        //en elk caso de que haya varios botones la clase se implementa
        //view.OnClickListener
        //y en onCreate se le pasa this como argumento
        btnBote.setOnClickListener(this);
        btnParticipante.setOnClickListener(this);
        btnActividades.setOnClickListener(this);
        Conexion conexion = new Conexion(this,"BoteDB",null,1);
        if (autoRelleno){
            db=conexion.getWritableDatabase();
            seed(db);
            db.close();
        }
        if (reset){
            db=conexion.getWritableDatabase();
            vaciaTablas();
            db.close();
        }

    }


    @Override
    public void onClick(View v) {
        Intent i;
        Bundle datos=new Bundle();
        datos.putBoolean("DEBUG",debug);
        //datos.putDouble("PESO",peso);
        //datos.putDouble("ALTURA",altura);
        //datos.putChar("SEXO",sexo);


        switch (v.getId()){
            case R.id.btnBotelym:
                i =new Intent(this,Bote.class);
                // ASOCIO EL BUNDLE AL INTENT
                i.putExtras(datos);
                startActivity(i);
                break;
            case R.id.btnParticipanteslym:
                i =new Intent(this,Participantes.class);
                // ASOCIO EL BUNDLE AL INTENT
                i.putExtras(datos);
                startActivity(i);
                break;
            case R.id.btnActividadlym:
                i =new Intent(this,Actividades.class);
                // ASOCIO EL BUNDLE AL INTENT
                i.putExtras(datos);
                startActivity(i);
                break;
        }
    }
    public void seed(SQLiteDatabase db)
    {
        String queries="";
/*
        if (!vaciaTablas()){
            if (debug)
                Toast.makeText(this, "tablas sin vaciar" , Toast.LENGTH_LONG).show();

        }*/
        vaciaTablas();
        queries=dameSQLParticipantes(true);
        if (queries.length()>0)//query no vacia
        {
            String[] vecQueries = queries.split(";");
            if (debug)
                Toast.makeText(this, " hay "+ vecQueries.length +" queries "+queries , Toast.LENGTH_LONG).show();

            for(int j=0; j<vecQueries.length;j++)
            {
                try {
                    db.execSQL(vecQueries[j]);//aqui rellenamos Tabla participantes
                } catch (Exception e) {
                    if (debug)
                        Toast.makeText(this, "fallo en la query "+(j+1)+" ;"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
            Cursor c=db.rawQuery("SELECT id FROM Participantes",null);
            int[] vecIdPar=new int [c.getCount()];//vector de ids de participantes
            //recorro participantos extrayebdo su id
            if (c.moveToFirst()){
                int i=0;
                do{
                    vecIdPar[i]=c.getInt(0);
                }while(c.moveToNext());

            }
            dameSQLrellenoActividades(true,vecIdPar);

        }
        else
        {
            Toast.makeText(this, "query Participantes vacia" , Toast.LENGTH_SHORT).show();
        }


    }

    private void vaciaTablas() {
        String queryVaciaTablaActividades = "DELETE FROM actividades";
        String queryVaciaTablaParticipantes = "DELETE FROM participantes";
       // String Table_Name1= "actividades"
        try {
            db.execSQL(queryVaciaTablaActividades);
        } catch (Exception e) {
            if (debug) {
                Toast.makeText(this, "fallo al borrar tabla actividades: " + e.getMessage().toString(), Toast.LENGTH_SHORT);
            }
        }
        try {
            db.execSQL(queryVaciaTablaParticipantes);
        } catch (Exception e) {
            if (debug) {
                Toast.makeText(this, "fallo al borrar tabla participantes: " + e.getMessage().toString(), Toast.LENGTH_SHORT);
            }
        }
      /*  Cursor c=db.rawQuery("SELECT * FROM Participantes",null);
        Cursor c1=db.rawQuery("SELECT * FROM Actividades",null);
        return (c.getCount()==0) && (c1.getCount()==0) ;*/
    }
    /*pre: debe haberse insertado los participantes anteriormente*/
    private void dameSQLrellenoActividades(boolean test,int[] ids)
    {
        int numParticipantes=ids.length;
        String SQLAct="";
        if(test && numParticipantes!= 0){
            Actividad act =new Actividad("concierto",1,300.45f,R.drawable.actividad);
            Actividad act1 =new Actividad("comilona",1,400.45f,R.drawable.actividad);
            Actividad act2 =new Actividad("visita guiada Castillo",1,45f,R.drawable.actividad);
            Actividad act3 =new Actividad("cena Primer dia",1,215.43f,R.drawable.actividad);
            arlActividades.add(act);
            arlActividades.add(act1);
            arlActividades.add(act2);
            arlActividades.add(act3);
            for(int i=0; i>arlActividades.size();i++)
            {

                SQLAct+="INSERT INTO actividades (nombre,idParticipante,precio,icono) VALUES ("
                        + "\""+ arlActividades.get(i).getStrNombre() +"\",";
                if (i<numParticipantes)
                {
                    SQLAct+= ids[i] + ",";
                }
                else//hay mas actividades que participantes
                {
                    //asignamos las restantes acitivdades al ultimo
                    SQLAct+= ids[numParticipantes -1] + ",";
                }
                SQLAct+=  arlActividades.get(i).getFloPrecio()
                        +  arlActividades.get(i).getIntIcono() + "); ";
            }
        }
        else
        {
            Actividad vacia =new Actividad("sin actividades",0,0f);
            arlActividades.add(vacia);
        }
    }
    private String dameSQLParticipantes(boolean test){
        String SQLIPar="";
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

            for(int i=0; i<arlParticipantes.size();i++)
            {
                SQLIPar+="INSERT INTO participantes (nombre,saldo,icono) VALUES ("
                        + "\""+ arlParticipantes.get(i).getStrNombre() +"\","
                        +  arlParticipantes.get(i).getFloSaldo() + ","
                        +  arlParticipantes.get(i).getIntIcono() + "); ";
            }
        }
        else
        {
            Participante vacio = new Participante("No hay Participantes",0f);
            arlParticipantes.add(vacio);
        }
        if(debug)
            Toast.makeText(this, SQLIPar, Toast.LENGTH_LONG).show();

        return SQLIPar;
    }
}
