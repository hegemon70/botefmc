package com.example.masteraaa.botefmc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Participantes extends AppCompatActivity {
    ListView listaPadrePart;
    Button btnVolver;
    ArrayList<Participante> arlParticipantes = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        listaPadrePart=(ListView)findViewById(R.id.lisParticipanteslyp);
        btnVolver=(Button)findViewById(R.id.btnVolverlyp);
        rellenoParticipantes();
        final AdaptadorParticipante adaptador = new AdaptadorParticipante(this,arlParticipantes);
        listaPadrePart.setAdapter(adaptador);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void rellenoParticipantes(){
        Participante par1 =new Participante("Fernando",0f,R.drawable.usuario_bn);
        Participante par2 =new Participante("Luis",0f,R.drawable.usuario_bn);
        Participante par3 =new Participante("Tomas",0f,R.drawable.usuario_bn);
        Participante par4 =new Participante("Guillermo",0f,R.drawable.usuario_bn);
        Participante par5 =new Participante("Toño",0f,R.drawable.usuario_bn);
        arlParticipantes.add(par1);
        arlParticipantes.add(par2);
        arlParticipantes.add(par3);
        arlParticipantes.add(par4);
        arlParticipantes.add(par5);

    }
}
