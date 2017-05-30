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
import java.util.List;

/**
 * Created by fernando on 30/05/2017.
 */

public class AdaptadorParticipante extends ArrayAdapter {
    private Context contexto;
    private ArrayList<Participante> datos;

    public AdaptadorParticipante( Context c, ArrayList<Participante> datos) {
        super(c, R.layout.listahijapar,datos);
        this.contexto = c;
        this.datos = datos;
    }
    //reprogramamos getView para que no devuelva string sino un view
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflador =LayoutInflater.from(contexto);
        convertView=inflador.inflate(R.layout.listahijapar,null);
        Participante participante=datos.get(position);

        ImageView iconoParticipante=(ImageView)convertView.findViewById(R.id.iconoParlylh);
        iconoParticipante.setImageResource(participante.getIntIcono());

        TextView txtNombre=(TextView)convertView.findViewById(R.id.nombreParlylh);
        txtNombre.setText(participante.getStrNombre());

        return convertView;
    }
}
