package com.example.masteraaa.botefmc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoActividad extends AppCompatActivity {
    TextView txvNombre,txvPrecio;
    ImageView imvIcono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_actividad);

        txvNombre=(TextView)findViewById(R.id.nombreActividadlyia);
        txvPrecio=(TextView)findViewById(R.id.precioActividadlyia);
        imvIcono=(ImageView)findViewById(R.id.imgActividadlyia);

        Bundle bPaquete=getIntent().getExtras();

        Actividad bitActividad=(Actividad)bPaquete.getSerializable("objActividad");

        txvNombre.setText(bitActividad.getStrNombre());
        txvPrecio.setText(bitActividad.getStrPrecio());
        imvIcono.setImageResource(bitActividad.getIntIcono());

    }
}
