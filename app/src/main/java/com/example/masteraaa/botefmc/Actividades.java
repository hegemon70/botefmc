package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Actividades extends AppCompatActivity implements View.OnClickListener{
    Button btnAnyadir,btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        btnAnyadir =(Button)findViewById(R.id.btnAnyadirlya);
        btnVolver=(Button)findViewById(R.id.btnVolverlya);

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
}
