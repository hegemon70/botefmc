package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Actividades extends AppCompatActivity implements View.OnClickListener{
    ListView listaPadreActiv;
    Button btnAnyadir,btnVolver;
    ArrayList<Actividad> arlActividades =new ArrayList<Actividad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        listaPadreActiv=(ListView)findViewById(R.id.lisActividadeslya);
        btnAnyadir =(Button)findViewById(R.id.btnAnyadirlya);
        btnVolver=(Button)findViewById(R.id.btnVolverlya);

        rellenoActividades(true);
        final AdaptadorActividades adaptador = new AdaptadorActividades(this,arlActividades);
        listaPadreActiv.setAdapter(adaptador);

        btnAnyadir.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnAnyadirlya:
                i = new Intent(this, ActividadNueva.class);
                startActivity(i);
                break;
            case R.id.btnVolverlya:
                finish();
                break;

        }
    }

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

    }
}
