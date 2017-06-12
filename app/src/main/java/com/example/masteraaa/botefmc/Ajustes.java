package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Ajustes extends AppCompatActivity implements View.OnClickListener{

    Switch swiDemo,swiDebug,swiReset;
    Button btnAplicar,btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        swiDemo = (Switch) findViewById(R.id.BtnSwitchDemolyaj);
        swiDebug = (Switch) findViewById(R.id.BtnSwitchDebuglyaj);
        swiReset = (Switch) findViewById(R.id.BtnSwitchResetlyaj);
        btnVolver = (Button) findViewById(R.id.btnVolverlyaj);
        btnAplicar = (Button) findViewById(R.id.btnAplicarlyaj);

        btnAplicar.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
        swiDemo.setOnClickListener(this);
        swiDebug.setOnClickListener(this);
        swiReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.BtnSwitchDemolyaj:
                Toast.makeText(this, "borra los datos y crea unos ficticios", Toast.LENGTH_LONG).show();
                break;
            case R.id.BtnSwitchDebuglyaj:
                Toast.makeText(this, "activara los toast que debugean codigo: no recomendado para usuarios", Toast.LENGTH_LONG).show();
                break;
            case R.id.BtnSwitchResetlyaj:
                Toast.makeText(this, "crea nuevo bote: se pierden todos los datos anteriores", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnVolverlyaj:
                finish();
                break;
            case R.id.btnAplicarlyaj:
                calculaAjustes();
                finish();
                break;
        }

    }

    private void calculaAjustes(){
        
    }
}
