package com.example.ejlistview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Contacto> listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContactos = new ArrayList<Contacto>();
        listaContactos.add(new Contacto("Manolo"));
        comprobarContactos();

        listView = (ListView) findViewById(R.id.eleuve);

        if(listaContactos.isEmpty()!=true)
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
                String nombreItem = ?????????;
                Toast tostada = Toast.makeText(getApplicationContext(), nombreItem,Toast.LENGTH_SHORT);
                tostada.show();
                startActivity(intnt);
            }
        });

        Button boton_configuracion = (Button) findViewById(R.id.boton_configuracion);
        boton_configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void comprobarContactos(){
        SharedPreferences sp = getSharedPreferences("datosContacto", Context.MODE_PRIVATE);
        Contacto nuevoContacto = new Contacto(
                sp.getString("nombre",null),
                sp.getString("apellidos",null),
                sp.getInt("telefono", -1),
                sp.getString("email",null),
                sp.getString("direccion",null),
                sp.getString("comentarios",null)
        );
        listaContactos.add(nuevoContacto);
    }
}