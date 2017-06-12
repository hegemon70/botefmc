package com.example.masteraaa.botefmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Ajustes extends AppCompatActivity implements View.OnClickListener{

    Switch swiDemo,swiDebug,swiReset;
    Button btnAplicar,btnVolver;
    boolean demo,debug,reset;
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

        leePreferenciasYMuestraSwitches();
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

    private void aplicaCambios(SharedPreferences preferencias)
    {
        actualizaValoresSegunSwitches();
        guardaCambios(preferencias);
    }
    private void guardaCambios(SharedPreferences preferencias)
    {
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putBoolean("DEBUG",debug);
        editor.putBoolean("DEMO",demo);
        editor.putBoolean("RESET",reset);
        editor.commit();
    }
    private void leePreferencias(SharedPreferences preferencias)
    {
        debug=preferencias.getBoolean("DEBUG",false);
        demo=preferencias.getBoolean("DEMO",false);
        reset=preferencias.getBoolean("RESET",false);
    }

    private boolean hayCambios()
    {   boolean cambioDemo=false;
        boolean cambioDebug=false;
        boolean cambioReset=false;
        boolean cambios=false;
        cambioDemo=swiDemo.isChecked()^demo;//xor de devuelve true si son distintos
        cambioDebug= swiDebug.isChecked()^debug;
        cambioReset=swiReset.isChecked()^reset;
        cambios=(cambioDemo||cambioDebug||cambioReset);//devuelve true si hay algun true
        return cambios;
    }

    private void calculaAjustes(){
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        boolean debug,demo,reset;
        debug=false;
        demo=false;
        reset=false;

        leePreferencias(preferencias);

        if (hayCambios())
        {
            aplicaCambios(preferencias);
        }

        debugeaResultado();


    }

    private void debugeaResultado(){
        String muestraBoolean="demo: ";
        muestraBoolean += (demo)?"true ":"false ";
        muestraBoolean += "debug:true ";
        muestraBoolean +="reset: ";
        muestraBoolean += (demo)?"true ":"false";
        if (debug){
            Toast.makeText(this, muestraBoolean, Toast.LENGTH_LONG).show();
        }
        else
        {
            Log.d("D", muestraBoolean);
        }
    }
    private void posicionaSwitches()
    {
        swiDemo.setChecked(demo);
        swiDebug.setChecked(debug);
        swiReset.setChecked(reset);
    }
    private void actualizaValoresSegunSwitches()
    {
        //boolean debug, boolean demo,boolean reset) {
        debug=(swiDebug.isChecked())?true:false;
        demo=(swiDemo.isChecked())?true:false;
        reset=(swiReset.isChecked())?true:false;
    }

    private void leePreferenciasYMuestraSwitches()
    {
        final SharedPreferences preferencias=getSharedPreferences("configuracion",MODE_PRIVATE);
        //boolean debug,demo,reset;
        debug=false;
        demo=false;
        reset=false;
        leePreferencias(preferencias);
        //,debug,demo,reset);
        posicionaSwitches();
        //debug,demo,reset);

    }
}
