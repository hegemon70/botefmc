package com.example.masteraaa.botefmc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Participantes extends AppCompatActivity implements View.OnClickListener
{
    boolean debug;//=true;
    ListView listaPadrePart;
    Button btnVolver,btnAñadir;
    EditText edtNombre;
    SQLiteDatabase db;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        listaPadrePart = (ListView) findViewById(R.id.lisParticipanteslyp);
        btnVolver = (Button) findViewById(R.id.btnVolverlyp);
        btnAñadir = (Button) findViewById(R.id.btnAnyadirlyp);
        edtNombre = (EditText) findViewById(R.id.ediNuevolyp);

        // RECOJO LOS DATOS DEL INTENT
        Bundle datos=getIntent().getExtras();
        debug=datos.getBoolean("DEBUG");
        leeParticipantes();
        //rellenaArrayParticipantes(false);

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
                       añadeParticipante(edtNombre.getText().toString());
                       break;
           }
       }


    private Boolean añadeParticipante(String txtNombre)
    {   Boolean exito=true;

        Toast.makeText(this, "entra en añadeParticipante", Toast.LENGTH_SHORT).show();
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
                db.execSQL(SQLIPar);//aqui rellenamos Tabla participantes
            } catch (Exception e) {
                if (debug)
                    Toast.makeText(this, "fallo en la query "+(i+1)+" ;"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            exito=false;
        }
        return exito;
    }

    private void leeParticipantes()//(boolean test)
    {

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


    }
}
