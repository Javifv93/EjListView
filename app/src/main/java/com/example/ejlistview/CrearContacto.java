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

public class CrearContacto extends AppCompatActivity {
    EditText et_nombre, et_apellidos, et_telefono, et_email, et_direccion, et_comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_contacto);

        et_nombre = (EditText) findViewById(R.id.Act_crearContactos_et_nombre);
        et_apellidos = (EditText) findViewById(R.id.Act_crearContactos_et_apellidos);
        et_telefono = (EditText) findViewById(R.id.Act_crearContactos_et_telefono);
        et_email = (EditText) findViewById(R.id.Act_crearContactos_et_email);
        et_direccion = (EditText) findViewById(R.id.Act_crearContactos_et_direccion);
        et_comentarios = (EditText) findViewById(R.id.Act_crearContactos_et_comentarios);

        /*SharedPreferences sp = getSharedPreferences("contactoTemp", Context.MODE_PRIVATE);
        Toast tostada =  Toast.makeText(CrearContacto.this,
                sp.getString("nombre","N")+
                        sp.getString("apellidos","N")+
                        sp.getInt("telefono",0)+
                        sp.getString("email","N")+
                        sp.getString("direccion","N")+
                        sp.getString("comentarios","N"),
                Toast.LENGTH_SHORT);
        tostada.show();*/

       if(getIntent().getBooleanExtra("contactoExistente",false))
        {
           SharedPreferences sp = getSharedPreferences("contactoTemp", Context.MODE_PRIVATE);
            et_nombre.setText(sp.getString("nombre","Np"));
            et_apellidos.setText(sp.getString("apellidos","Np"));
            et_telefono.setText(String.valueOf(sp.getInt("telefono",0)));
            et_email.setText(sp.getString("email","Np"));
            et_direccion.setText(sp.getString("direccion","Np"));
            et_comentarios.setText(sp.getString("comentarios","Np"));
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

        nombre = et_nombre.getText().toString();
        apellidos = et_apellidos.getText().toString();
        telefono = Integer.valueOf(et_telefono.getText().toString());
        email = et_email.getText().toString();
        direccion = et_direccion.getText().toString();
        comentarios = et_comentarios.getText().toString();

        SharedPreferences sp = getSharedPreferences("datosContacto", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        sp_editor.putBoolean("semaforo",true);
        sp_editor.putString("nombre", nombre);
        sp_editor.putString("apellidos",apellidos);
        sp_editor.putInt("telefono", telefono);
        sp_editor.putString("email",email);
        sp_editor.putString("direccion",direccion);
        sp_editor.putString("comentarios",comentarios);

        sp_editor.commit();

        volver();
    }
}