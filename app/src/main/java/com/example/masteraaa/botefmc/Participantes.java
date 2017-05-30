package com.example.masteraaa.botefmc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Participantes extends AppCompatActivity {
    Button btnVolver;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        btnVolver=(Button)findViewById(R.id.btnVolverlyp);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void rellenoParticipantes(){
        Participante par1 =new Participante("Fernando",0f);
        Participante par2 =new Participante("Luis",0f);
        Participante par3 =new Participante("Tomas",0f);
        Participante par4 =new Participante("Guillermo",0f);
        Participante psr5 =new Participante("Toño",0f);
        arlParticipantes.add(par1);
    }
}
