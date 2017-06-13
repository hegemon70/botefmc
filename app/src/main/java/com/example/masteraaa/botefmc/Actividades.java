package com.example.masteraaa.botefmc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Actividades extends AppCompatActivity implements View.OnClickListener{
    ImageView imgModoDemo;
    ListView listaPadreActiv;
    TextView txvSeparador;
    Button btnAnyadir,btnVolver;
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();
    String bbdd;
    int version;
    SQLiteDatabase db;
    Boolean debug,demo;
    Bundle datos;
    static final int REQUEST_CODE=1;
    static final int ALTURA_VENTANA_ACTIVIDADES=10;
    static final int ALTURA_ACTIVIDAD=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        imgModoDemo=(ImageView)findViewById(R.id.imgModoDemolya);
        txvSeparador=(TextView)findViewById(R.id.separadorActividadeslya);
        listaPadreActiv=(ListView)findViewById(R.id.lisActividadeslya);
        btnAnyadir =(Button)findViewById(R.id.btnAnyadirlya);
        btnVolver=(Button)findViewById(R.id.btnVolverlya);

       // ponAlturaSeparador();
        // RECOJO LOS DATOS DEL INTENT
        datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        bbdd=datos.getString("BBDD");
        version=datos.getInt("VERSION");
        demo=datos.getBoolean("DEMOACTIVADA");

        if(demo)
        {
            imgModoDemo.setVisibility(View.VISIBLE);
        }
        Conexion conexion=new Conexion(this,bbdd,null,version);
        db=conexion.getReadableDatabase();
        muestraActividades(db);
        db.close();
        /*
       if(!leeActividades())//si no hay actividades
       {  Actividad actividad  =new Actividad(0,"No hay ninguna Actividad",-1,0.0f,R.drawable.actividad);
           arlActividades.add(actividad);
       }
        final AdaptadorActividades adaptador = new AdaptadorActividades(this,arlActividades);
        listaPadreActiv.setAdapter(adaptador);
*/
        btnAnyadir.setOnClickListener(this);
        btnVolver.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnAnyadirlya:
                i = new Intent(this, ActividadNueva.class);
                i.putExtras(datos);
                startActivityForResult(i,REQUEST_CODE);
                break;
            case R.id.btnVolverlya:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            Conexion conexion=new Conexion(this,bbdd,null,version);
            db=conexion.getReadableDatabase();
            muestraActividades(db);
            db.close();
        }


    }

    private void muestraActividades(SQLiteDatabase db){
        if(!leeActividades())//si no hay actividades
        {  Actividad actividad  =new Actividad(0,"No hay ninguna Actividad",-1,0.0f,R.drawable.actividad);
            arlActividades.add(actividad);
        }

        final AdaptadorActividades adaptador = new AdaptadorActividades(this,arlActividades,db);
        listaPadreActiv.setAdapter(adaptador);
        //db.close();
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
/* private void ponAlturaSeparador(){
        int intVentanaFija=ALTURA_ACTIVIDAD*ALTURA_VENTANA_ACTIVIDADES;//quiero dejar una ventana de 10
         int altura= intVentanaFija-(arlActividades.size()*ALTURA_ACTIVIDAD);
        txvSeparador.setHeight(altura);
    }*/
    /*
    private void rellenoActividades(boolean test){
        if(test){
            Actividad act =new Actividad("concierto",1,300.45f,R.drawable.actividad);
            Actividad act1 =new Actividad("comilona",1,400.45f,R.drawable.actividad);
            Actividad act2 =new Actividad("visita guiada Catillo",1,45f,R.drawable.actividad);
            Actividad act3 =new Actividad("cena Primer dia",1,215.43f,R.drawable.actividad);
            arlActividades.add(act);
            arlActividades.add(act1);
            arlActividades.add(act2);
            arlActividades.add(act3);

        }else{
            Actividad vacia =new Actividad("sin actividades",0,0f);
            arlActividades.add(vacia);
        }

    }*/
}
