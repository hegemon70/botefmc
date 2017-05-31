package com.example.masteraaa.botefmc;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fernando on 31/05/2017.
 */

public class AdaptadorActividades extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Actividad> datos;

    public AdaptadorActividades(Context context,  ArrayList<Actividad> datos) {
        super(context, R.layout.listahijaact,datos);
        this.contexto = context;
        this.datos = datos;
    }
    //reprogramamos getView para que no devuelva string sino un view
    public View getView(int position, View convertView,ViewGroup parent)
    {
        LayoutInflater inflador= LayoutInflater.from(contexto);
        convertView=inflador.inflate(R.layout.listahijaact,null);
        Actividad actividad=datos.get(position);

        ImageView iconoActividad=(ImageView)convertView.findViewById(R.id.imgActividadlyhac);
        iconoActividad.setImageResource(actividad.getIntIcono());

        TextView txtNombre=(TextView)convertView.findViewById(R.id.txtNombreaclyhac);
        txtNombre.setText(actividad.getStrNombre());

        TextView txtPrecio=(TextView)convertView.findViewById(R.id.txtPreciolyhac);
        txtPrecio.setText(actividad.getStrPrecio());
        return convertView;
    }
}
