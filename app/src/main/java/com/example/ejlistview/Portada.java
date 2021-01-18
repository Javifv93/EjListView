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

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

        iniciarSP();

        Button boton_entrar = (Button) findViewById(R.id.Act_portada_button);
        boton_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(Portada.this,CrearContacto.class);
                startActivity(intnt);
            }
        });
    }
    void iniciarSP(){
        SharedPreferences sp = getSharedPreferences("datosContacto", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        sp_editor.putString("nombre", "");
        sp_editor.putString("apellidos","");
        sp_editor.putInt("telefono", 0);
        sp_editor.putString("email","");
        sp_editor.putString("direccion","");
        sp_editor.putString("comentarios","");

        sp_editor.commit();
    }
}