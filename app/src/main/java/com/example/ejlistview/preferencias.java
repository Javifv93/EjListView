package com.example.ejlistview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

public class preferencias extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

    @Override
    protected void onStop() {
        super.onStop();
        update();
    }

    public void update(){
        SharedPreferences sp = getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        boolean boolAd = ((CheckBoxPreference) findPreference("cbp_mostrarPrefs")).isChecked();
        sp_editor.putBoolean("cbp_mostrarPrefs", boolAd);

        String strGen = ((ListPreference) findPreference("lp_genero")).getEntry().toString();
        sp_editor.putString("lp_genero",strGen);

        String etpEdad = ((EditTextPreference) findPreference("etp_edad")).getText();
        sp_editor.putString("etp_edad", etpEdad);

        String strPais = ((ListPreference) findPreference("lp_pais")).getEntry().toString();
        sp_editor.putString("lp_pais",strPais);

        sp_editor.commit();
    }
}