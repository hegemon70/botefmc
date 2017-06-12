package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private void aplicaCambios(SharedPreferences preferencias,boolean debug,boolean demo,boolean reset)
    {
        debug=(swiDebug.isChecked())?true:false;
        demo=(swiDemo.isChecked())?true:false;
        reset=(swiReset.isChecked())?true:false;
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putBoolean("DEBUG",debug);
        editor.putBoolean("DEMO",demo);
        editor.putBoolean("RESET",reset);
        editor.commit();
    }
    private void leePreferencias(SharedPreferences preferencias,boolean debug,boolean demo,boolean reset)
    {
        debug=preferencias.getBoolean("DEBUG",false);
        demo=preferencias.getBoolean("DEMO",false);
        reset=preferencias.getBoolean("RESET",false);
    }
    /*
    private boolean hayCambios(boolean debug,boolean demo,boolean reset)
    {   boolean cambioDemo=false;
        boolean cambioDebug=false;
        boolean cambioReset=false;
        boolean cambios=false;
        cambioDebug= swiDebug.isChecked()&& debug;
        return cambios;
    }
    */
    private void calculaAjustes(){
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        boolean debug,demo,reset;
        debug=false;
        demo=false;
        reset=false;

        leePreferencias(preferencias,debug,demo,reset);
        aplicaCambios(preferencias,debug,demo,reset);


        if (debug){
            String muestraBoolean="demo: ";
            muestraBoolean += (demo)?"true ":"false ";
            muestraBoolean+="debug:true ";
            muestraBoolean="reset: ";
            muestraBoolean += (demo)?"true ":"false";

        }

    }
}
