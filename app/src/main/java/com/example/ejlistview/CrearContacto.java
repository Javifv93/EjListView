package com.example.ejlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class CrearContacto extends AppCompatActivity {
    EditText et_nombre, et_apellidos, et_telefono, et_email, et_direccion, et_comentarios;
    private ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
    int idModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        persistirDatosContacto_GET();

        et_nombre = (EditText) findViewById(R.id.Act_crearContactos_et_nombre);
        et_apellidos = (EditText) findViewById(R.id.Act_crearContactos_et_apellidos);
        et_telefono = (EditText) findViewById(R.id.Act_crearContactos_et_telefono);
        et_email = (EditText) findViewById(R.id.Act_crearContactos_et_email);
        et_direccion = (EditText) findViewById(R.id.Act_crearContactos_et_direccion);
        et_comentarios = (EditText) findViewById(R.id.Act_crearContactos_et_comentarios);

       if(getIntent().getBooleanExtra("contactoExistente",false))
        {
           SharedPreferences sp = getSharedPreferences("contactoTemp", Context.MODE_PRIVATE);
            et_nombre.setText(sp.getString("nombre","Np"));
            et_apellidos.setText(sp.getString("apellidos","Np"));
            et_telefono.setText(String.valueOf(sp.getInt("telefono",0)));
            et_email.setText(sp.getString("email","Np"));
            et_direccion.setText(sp.getString("direccion","Np"));
            et_comentarios.setText(sp.getString("comentarios","Np"));
            idModificar = sp.getInt("id",-1);
        }

        Button boton_registrar = (Button) findViewById(R.id.Act_crearContactos_butt_registrar);
        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro();
            }
        });
        Button boton_volver = (Button) findViewById(R.id.Act_crearContactos_butt_volver);
        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });
    }
    private void volver(){
        Intent intnt = new Intent(CrearContacto.this, MainActivity.class);
        startActivity(intnt);
    }
    private void registro() {
        String nombre, apellidos, email, direccion, comentarios;
        int telefono;
        Contacto nuevoContacto = null;
        boolean todoOk = true;
        try {
            nombre = et_nombre.getText().toString();
            apellidos = et_apellidos.getText().toString();
            telefono = Integer.valueOf(et_telefono.getText().toString());
            email = et_email.getText().toString();
            direccion = et_direccion.getText().toString();
            comentarios = et_comentarios.getText().toString();

            nuevoContacto = new Contacto(
                    nombre,
                    apellidos,
                    telefono,
                    email,
                    direccion,
                    comentarios
            );
        }catch (Exception e){
            Toast tostada = Toast.makeText(CrearContacto.this, "Debes introducir todos los campos para guardar el contacto", Toast.LENGTH_SHORT);
            tostada.show();
            todoOk = false;
        }
        if(todoOk) {
            if (comprobarDatos(nuevoContacto)) {
                if (getIntent().getBooleanExtra("contactoExistente", false)) {
                    Iterator it = listaContactos.listIterator();
                    while (it.hasNext()) {
                        Contacto contactoActual = (Contacto) it.next();
                        if (contactoActual.getId() == idModificar) {
                            it.remove();
                        }
                    }
                }
                listaContactos.add(nuevoContacto);
                persistirDatosContacto_SET();
                volver();
            }
        }
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
            Toast tostada = Toast.makeText(CrearContacto.this,"Error al acceder a los contactos guardados",Toast.LENGTH_SHORT);
            tostada.show();
            System.out.println("Error en persistirDatosContacto_GET fuera del for");
            e.printStackTrace();
        }
    }
    boolean comprobarDatos(Contacto nuevoContacto){
        String nombre, apellidos, telefono, email, direccion, comentarios;
        nombre = nuevoContacto.getNombre();
        apellidos = nuevoContacto.getApellidos();
        telefono = String.valueOf(nuevoContacto.getTelefono());
        email = nuevoContacto.getEmail();
        direccion = nuevoContacto.getDireccion();
        comentarios = nuevoContacto.getObservaciones();

        boolean todoOk = true;

        String[] caracteresProhibidos = {"\\","%","&","$",";","=","+","(",")","?","<",">"};
        for(String caracter:caracteresProhibidos) {
            if(nombre.contains(caracter)){
                todoOk = false;
                Toast tostada = Toast.makeText(CrearContacto.this, "El nombre introducido contiene carácteres no permitidos", Toast.LENGTH_SHORT);
                tostada.show();
            }
            else if(apellidos.contains(caracter)){
                todoOk = false;
                Toast tostada = Toast.makeText(CrearContacto.this, "Los apellidos introducidos contienen carácteres no permitidos", Toast.LENGTH_SHORT);
                tostada.show();
            }
            else if(email.contains(caracter)){
                todoOk = false;
                Toast tostada = Toast.makeText(CrearContacto.this, "El email introducido contiene carácteres no permitidos", Toast.LENGTH_SHORT);
                tostada.show();
            }
            else if(direccion.contains(caracter)){
                todoOk = false;
                Toast tostada = Toast.makeText(CrearContacto.this, "La dirección introducida contiene carácteres no permitidos", Toast.LENGTH_SHORT);
                tostada.show();
            }
            else if(comentarios.contains(caracter)){
                todoOk = false;
                Toast tostada = Toast.makeText(CrearContacto.this, "Los comentarios introducidos contienen carácteres no permitidos", Toast.LENGTH_SHORT);
                tostada.show();
            }
        }

        if((telefono.length()<3)||(telefono.length()>20)){
            todoOk = false;
            Toast tostada = Toast.makeText(CrearContacto.this, "El número de teléfono introducido tiene una longitud inapropiada", Toast.LENGTH_SHORT);
            tostada.show();
        }
        return todoOk;
    }
}