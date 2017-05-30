package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActividadNueva extends AppCompatActivity implements View.OnClickListener{
Button btnVolver,btnInsertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nueva);

        btnInsertar=(Button)findViewById(R.id.btnInsertarlyan);
        btnVolver=(Button)findViewById(R.id.btnVolverlyan);

        btnInsertar.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnInsertarlyan:
                Toast.makeText(this, "aqui insertamos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnVolverlyan:
                finish();
                break;
        }
    }
}
