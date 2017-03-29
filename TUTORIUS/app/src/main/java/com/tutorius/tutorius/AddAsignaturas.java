package com.tutorius.tutorius;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;


public class AddAsignaturas extends Activity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asignaturas);

        //Recuperando el nombre de usuario pasado en el login.
        Bundle b = this.getIntent().getExtras();
        user = b.getString("UVUS");



        String str[] = {"Circuitos","ADDA","Redes","Arquitectura"};

        AutoCompleteTextView t1 = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,str);

        t1.setThreshold(1);
        t1.setAdapter(adp);
    }
/*
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonaddsubject:
                //Mostramos el logo de loading
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                hiloconexion = new AddAsignaturas().WebService();
                String cadenallamada = AUTENTICACION + "?uvus_alumno=" + usuario.getText().toString() + "&password=" + password.getText().toString();
                hiloconexion.execute(cadenallamada,"1",user);   // Parámetros que recibe doInBackground
                break;
            default:

                break;
        }
    }
*/
}