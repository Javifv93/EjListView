package com.example.ejlistview;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
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
    private ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();//todo Hay que persistir este pedazo de mierda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.eleuve);
        Toast t = Toast.makeText(MainActivity.this, "Tamaño: "+listaContactos.size(),Toast.LENGTH_SHORT);
        t.show();

        Button boton_contacto = (Button) findViewById(R.id.boton_contacto);
        boton_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(MainActivity.this, CrearContacto.class);
                startActivity(intnt);
            }
        });
        /*Queda buscar la forma de recuperar el nombre de la lista, y una vez recuperado buscar en el arrayList de listaContactos por nombre de objeto y comparar para obtener el resto de los datos
        * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intnt = new Intent(MainActivity.this, CrearContacto.class);
                Object itemSeleccionado = listView.getItemAtPosition(position);
                String nombreItem = itemSeleccionado.toString();

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

                            /*Toast tostada =  Toast.makeText(MainActivity.this, x.getNombre() +""
                                    + x.getId()+""
                                    +x.getApellidos()+""
                                    +x.getTelefono()+""
                                    +x.getEmail()+""
                                    +x.getDireccion()+""
                                    +x.getObservaciones(),Toast.LENGTH_SHORT);
                            tostada.show();*/

                            sp_editor.commit();
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

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        comprobarContactos();
        if(listaContactos.isEmpty()!=true)  //todo: mirar que esto funcione mejor, no esta filtrando
        {
            ArrayList<String> listaNombres = new ArrayList<String>();
            for(int x=0;x<listaContactos.size();x++)
            {
                Contacto contacto = listaContactos.get(x);
                listaNombres.add(contacto.getNombre());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaNombres);

            listView.setAdapter(adapter);
        }
        persistirDatosContacto_SET();
    }

    private void comprobarContactos() {
        SharedPreferences sp = getSharedPreferences("datosContacto", Context.MODE_PRIVATE);
        if (sp.getBoolean("semaforo", true)) {
            Toast tostada = Toast.makeText(MainActivity.this, "ENTRA", Toast.LENGTH_SHORT);
            tostada.show();
            Contacto nuevoContacto = new Contacto(
                    sp.getString("nombre", null),
                    sp.getString("apellidos", null),
                    sp.getInt("telefono", -1),
                    sp.getString("email", null),
                    sp.getString("direccion", null),
                    sp.getString("comentarios", null)
            );
            nuevoContacto.setId(listaContactos.size());
            SharedPreferences.Editor sp_editor = sp.edit();
            sp_editor.putBoolean("semaforo", false);
            sp_editor.commit();
            listaContactos.add(nuevoContacto);
            Toast t = Toast.makeText(MainActivity.this, "Tamaño: " + listaContactos.size(), Toast.LENGTH_SHORT);
            t.show();
        }
    }
    public void persistirDatosContacto_SET(){
        String registro = "$";
        for (Contacto x:listaContactos)
        {
            String id, nombre, apellidos, telefono, email, direccion, comentarios;
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
        String registro = sp.getString("registro","");

        String[] arrayContactos = registro.split("$");
        for(int x=0;x<arrayContactos.length;x++)
        {
            String [] arrayDatos = arrayContactos[x].split("%");
            //todo SEGUIR AQUI!
        }
    }
}