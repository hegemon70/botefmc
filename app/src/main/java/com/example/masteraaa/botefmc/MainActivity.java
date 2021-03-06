package com.example.masteraaa.botefmc;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBote,btnParticipante,btnActividades,btnSalir,btnAjustes;
    ImageView imgModoDemo;
    SQLiteDatabase db;
    String bbdd="BoteDB1";
    Boolean debug;//=true;
    Boolean reset;//=false;
    Boolean autoRelleno=false;
    Boolean demoActivada;
    Boolean demo;
    //Boolean resetHard=true;//drop tables
    int version=5;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgModoDemo=(ImageView)findViewById(R.id.imgModoDemolym);
        btnBote=(Button)findViewById(R.id.btnBotelym);
        btnParticipante=(Button)findViewById(R.id.btnParticipanteslym);
        btnActividades=(Button)findViewById(R.id.btnActividadlym);
        btnSalir=(Button)findViewById(R.id.btnSalirlym);
        btnAjustes=(Button)findViewById(R.id.btnAjusteslym);
        //en el caso de que haya varios botones la clase se implementa
        //view.OnClickListener
        //y en onCreate se le pasa this como argumento
        btnBote.setOnClickListener(this);
        btnParticipante.setOnClickListener(this);
        btnActividades.setOnClickListener(this);
        btnAjustes.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        leePreferencias();

        Conexion conexion = new Conexion(this,bbdd,null,version);
        gestionaPreferencias(conexion);
        if(demo)
        {
            imgModoDemo.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v)
    {
        Intent i;
        Bundle datos=new Bundle();
        datos.putBoolean("DEBUG",debug);
        datos.putString("BBDD",bbdd);
        datos.putInt("VERSION",version);
        datos.putBoolean("DEMOACTIVADA",demoActivada);

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
            case R.id.btnAjusteslym:
                i =new Intent(this,Ajustes.class);
                // ASOCIO EL BUNDLE AL INTENT
                i.putExtras(datos);
                startActivity(i);
                break;

            case R.id.btnSalirlym:
                finish();
                break;
        }
    }
    public void seed(SQLiteDatabase db)
    {
        String queries="";
/*        if (!vaciaTablas()){
            if (debug)
                Toast.makeText(this, "tablas sin vaciar" , Toast.LENGTH_LONG).show();
        }*/
        vaciaTablas();
        queries=dameSQLParticipantes(true);
        if (queries.length()>0)//query no vacia
        {
            ejecutaQueryMultipleDeAccion(queries);//creamos participantes
            Cursor c=db.rawQuery("SELECT id FROM Participantes",null);
            int[] vecIdPar=new int [c.getCount()];//vector de ids de participantes
            //recorro participantos extrayebdo su id
            if (c.moveToFirst())
            {
                int i=0;
                do
                {
                    vecIdPar[i]=c.getInt(0);
                    i++;
                }
                while(c.moveToNext());

            }
            queries=dameSQLrellenoActividades(true,vecIdPar);
            if (queries.length()!=0){
                ejecutaQueryMultipleDeAccion(queries);//creamos Actividades
            }
            else
            {
                Toast.makeText(this, "query Accciones vacia" , Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(this, "query Participantes vacia" , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * le pasamos un string con varias sentencias sql seguidas
     * es necesario que las queries existan
     * @param queries
     */
    private void ejecutaQueryMultipleDeAccion(String queries){
        String[] vecQueries = queries.split(";");
        int limite=(vecQueries.length - 1);//el split da un ultimo elem vacio por el ; final
        if (debug)
            Toast.makeText(this, " hay "+ limite +" queries "+queries , Toast.LENGTH_LONG).show();

        for(int j=0; j<limite;j++)
        {
            try {
                db.execSQL(vecQueries[j]);
            }
            catch (Exception e)
            {
                if (debug)
                    Toast.makeText(this, "fallo en la query "+(j+1)+" ;"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
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

    }
    /*pre: debe haberse insertado los participantes anteriormente*/
    private String dameSQLrellenoActividades(boolean test,int[] ids)
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
            //int j=0; //indice para el indice del participante
            for(int i=0; i<arlActividades.size();i++)
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
                SQLAct+=  arlActividades.get(i).getFloPrecio() + ","
                        +  arlActividades.get(i).getIntIcono() + "); ";
            }
        }
        else
        {
            Actividad vacia =new Actividad("sin actividades",0,0f);
            arlActividades.add(vacia);
        }
        return SQLAct;
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

    private void leePreferencias()
    {
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        debug=false;
        demo=false;
        reset=false;
        demoActivada=false;
        debug=preferencias.getBoolean("DEBUG",false);
        demo=preferencias.getBoolean("DEMO",false);
        demoActivada=preferencias.getBoolean("DEMOACTIVADA",false);
        reset=preferencias.getBoolean("RESET",false);
    }
    private void gestionaPreferencias(Conexion conexion)
    {
        if (demo)
        {

            if(!demoActivada)//PRIMER ARRANQUE EN DEMO
            {
                reset=true;//VACIAREMOS LAS TABLAS DE LO ANTERIOR
                autoRelleno=true;
                activaflagDemoActivada();
            }
            else //SUBSIGUIENTES ARRANQUES EN MODO DEMO
            {
                autoRelleno=false;
            }

        }
        else//demo: false
        {
            if(demoActivada)//ULTIMO ARRANQUE EN MODO DEMO, NO QUEREMOS NINGUNO MAS
            {
                reset=true; //VACIAREMOS LAS TABLAS DE LA DEMO
                desactivaflagDemoActivada();
            }
        }

        if (reset){
            db=conexion.getWritableDatabase();
            vaciaTablas();
            db.close();
            desactivaReseteo();//NO QUEREMOS QUE RESETEE DESDE AHORA NI EN EL SIGUIENTE ARRANQUE
        }

        if (autoRelleno)//
        {
            db=conexion.getWritableDatabase();
            seed(db);
            db.close();
            autoRelleno=false;

        }
    }
    private void desactivaReseteo()
    {
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        reset=false;//cambiamos el valor de reset en el actual arranque
        editor.putBoolean("RESET",reset);//cambiamos el valor de reset para el proximo arranque
        editor.commit();
    }
    private void activaflagDemoActivada()
    {
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        demoActivada=true;//cambiamos el valor de actual
        editor.putBoolean("DEMOACTIVADA",demoActivada);//cambiamos el valor demoactivada para el proximo arranque
        editor.commit();
    }
    private void desactivaflagDemoActivada()
    {
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        demoActivada=false;//cambiamos el valor actual
        editor.putBoolean("DEMOACTIVADA",demoActivada);//cambiamos el valor demoactivada para el proximo arranque
        editor.commit();
    }
}
