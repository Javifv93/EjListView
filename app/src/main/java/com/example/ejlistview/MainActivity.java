package com.example.ejlistview;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ListMenuItemView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        persistirDatosContacto_GET();

        listView = (ListView) findViewById(R.id.eleuve);

        Button boton_contacto = (Button) findViewById(R.id.boton_contacto);
        boton_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(MainActivity.this, CrearContacto.class);
                persistirDatosContacto_SET();
                startActivity(intnt);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intnt = new Intent(MainActivity.this, CrearContacto.class);
               //Object itemSeleccionado = listView.getItemAtPosition(position);
                String nombreItem = listaContactos.get(position).getNombre();

                for(Contacto x:listaContactos)
                {
                    String nombreContacto = x.getNombre();
                    if(nombreContacto.equals(nombreItem))
                    {
                        if(x.getId()==listaContactos.indexOf(x))
                        {
                            intnt.putExtra("contactoExistente", true);
                            SharedPreferences sp = getSharedPreferences("contactoTemp", Context.MODE_PRIVATE);
                            SharedPreferences.Editor sp_editor = sp.edit();
                            sp_editor.putInt("id",x.getId());
                            sp_editor.putString("nombre",x.getNombre());
                            sp_editor.putString("apellidos",x.getApellidos());
                            sp_editor.putInt("telefono",x.getTelefono());
                            sp_editor.putString("email",x.getEmail());
                            sp_editor.putString("direccion",x.getDireccion());
                            sp_editor.putString("comentarios",x.getObservaciones());

                            sp_editor.commit();
                            persistirDatosContacto_SET();
                            startActivity(intnt);
                        }
                    }
                }
            }
        });

        Button boton_configuracion = (Button) findViewById(R.id.boton_configuracion);
        boton_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(Settings.ACTION_SETTINGS);
                startActivityForResult(intnt,0);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(listaContactos.isEmpty()!=true)
        {
            AdaptadorLista adapter = new AdaptadorLista(this, listaContactos);
            listView.setAdapter(adapter);
        }
        persistirDatosContacto_SET();
    }
    public void persistirDatosContacto_SET(){
        String registro = "";
        for (Contacto x:listaContactos)
        {
            String id, nombre, apellidos, telefono, email, direccion, comentarios;
            x.setId(listaContactos.indexOf(x));
            id = String.valueOf(x.getId());
            nombre = x.getNombre();
            apellidos = x.getApellidos();
            telefono = String.valueOf(x.getTelefono());
            email = x.getEmail();
            direccion = x.getDireccion();
            comentarios = x.getObservaciones();

            registro +=
                     id+"%"+
                     nombre+"%"+
                     apellidos+"%"+
                     telefono+"%"+
                     email+"%"+
                     direccion+"%"+
                     comentarios+"$"
                    ;
        }
        SharedPreferences sp = getSharedPreferences("registroContactos", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();
        sp_editor.putString("registro", registro);
        sp_editor.commit();
    }
    public void persistirDatosContacto_GET()
    {
        SharedPreferences sp = getSharedPreferences("registroContactos", Context.MODE_PRIVATE);
        try {
            String registro = sp.getString("registro","");
            String[] arrayContactos = registro.split("\\$");
            for(int x=0;x<arrayContactos.length;x++)
            {
                try{
                    String [] arrayDatos = arrayContactos[x].split("%");
                    Contacto nuevoContacto = new Contacto(
                            Integer.parseInt(arrayDatos[0]),    //id
                            arrayDatos[1],                      //nombre
                            arrayDatos[2],                      //apellidos
                            Integer.parseInt(arrayDatos[3]),    //telefono
                            arrayDatos[4],                      //email
                            arrayDatos[5],                      //direccion
                            arrayDatos[6]                       //comentarios
                    );
                    listaContactos.add(nuevoContacto);
                }catch (Exception e){
                    System.out.println("Error en persistirDatosContacto_GET dentro del for en vuelta "+x);
                    e.printStackTrace();
                }
            }
        }catch (Exception e)
        {
            Toast tostada = Toast.makeText(MainActivity.this,"Error al acceder a los contactos guardados",Toast.LENGTH_SHORT);
            tostada.show();
            System.out.println("Error en persistirDatosContacto_GET fuera del for");
            e.printStackTrace();
        }
    }
}