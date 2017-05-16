package com.example.masteraaa.botefmc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button btnBote,btnParticipante,btnActividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBote=(Button)findViewById(R.id.btnBotelym);
        btnParticipante=(Button)findViewById(R.id.btnParticipanteslym);
        btnActividades=(Button)findViewById(R.id.btnActividadlym);

        btnBote.setOnClickListener(this);
        btnParticipante.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btnBotelym:
                i =new Intent(this,Bote.class);
                startActivity(i);
                break;
            case R.id.btnParticipanteslym:
                i =new Intent(this,Participantes.class);
                startActivity(i);
                break;
            case R.id.btnActividadlym:
                i =new Intent(this,Actividades.class);
                startActivity(i);
                break;
        }
    }
}
