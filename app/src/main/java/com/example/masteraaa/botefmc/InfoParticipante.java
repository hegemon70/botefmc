package com.example.masteraaa.botefmc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoParticipante extends AppCompatActivity {
    TextView txvNombre;
    ImageView imvIcono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_participante);

        txvNombre=(TextView)findViewById(R.id.nombreParticipantelyip);
        imvIcono=(ImageView)findViewById(R.id.imgParticipantelyip);

        Bundle bPaquete=getIntent().getExtras();

        Participante bitParticipante=(Participante)bPaquete.getSerializable("objParticipante");

        txvNombre.setText(bitParticipante.getStrNombre());
        imvIcono.setImageResource(bitParticipante.getIntIcono());
    }
}
