package com.example.ejlistview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

        //limpiarSP();

        Button boton_entrar = (Button) findViewById(R.id.Act_portada_button);
        boton_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(Portada.this,MainActivity.class);
                startActivity(intnt);
            }
        });
        Button boton_limpiar = (Button) findViewById(R.id.Act_portada_limpiar);
        boton_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarSP();
            }
        });
    }
    void limpiarSP(){
        AlertDialog.Builder confirmacion = new AlertDialog.Builder(this);
        confirmacion.setTitle("Confirmación");
        confirmacion.setMessage("Estas seguro de que quieres borrar los datos?");

        confirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        confirmacion.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor sp_editor = getSharedPreferences("registroContactos",MODE_PRIVATE).edit();
                sp_editor.clear().apply();

                Toast tostada = Toast.makeText(Portada.this, "Los datos de contacto de la agenda han sido eliminados", Toast.LENGTH_SHORT);
                tostada.show();
            }
        });
        confirmacion.show();


    }
}