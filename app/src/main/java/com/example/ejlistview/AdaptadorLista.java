package com.example.ejlistview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorLista extends ArrayAdapter<Contacto> {
    private Activity activity;
    ArrayList<Contacto> listaContactos;
    Contacto contacto;

    public AdaptadorLista(Activity activity, ArrayList<Contacto> listaContactos){
        super(activity, R.layout.item);
        this.activity = activity;
        this.listaContactos = listaContactos;
    }

    static class ViewHolder{
        protected TextView nombreTv;
        protected TextView apellidosTv;
        protected TextView telefonoTv;
        protected TextView emailTv;
        protected TextView direccionTv;
        protected TextView comentariosTv;
    }

    public int getCount(){
        return listaContactos.size();
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){
        View view = null;
        LayoutInflater inflator = activity.getLayoutInflater();
        view = inflator.inflate(R.layout.item, null);
        final ViewHolder viewHolder = new ViewHolder();



        viewHolder.nombreTv = (TextView) view.findViewById(R.id.nombreItem);
        viewHolder.apellidosTv = (TextView) view.findViewById(R.id.apellidosItem);
        viewHolder.telefonoTv = (TextView) view.findViewById(R.id.telefonoItem);
        viewHolder.emailTv = (TextView) view.findViewById(R.id.emailItem);
        viewHolder.direccionTv = (TextView) view.findViewById(R.id.direccionItem);
        viewHolder.comentariosTv = (TextView) view.findViewById(R.id.comentariosItem);

        contacto = listaContactos.get(position);
        viewHolder.nombreTv.setText(contacto.getNombre());
        viewHolder.apellidosTv.setText(contacto.getApellidos());
        viewHolder.telefonoTv.setText(String.valueOf(contacto.getTelefono()));
        viewHolder.emailTv.setText(contacto.getEmail());
        viewHolder.direccionTv.setText(contacto.getDireccion());
        viewHolder.comentariosTv.setText(contacto.getObservaciones());

        return view;
    }
}
