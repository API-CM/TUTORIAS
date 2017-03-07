package com.tutorius.tutorius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pruebaLanzada(View view){
        Intent i=new Intent(this,Consulta_Pide_Cita.class);
        startActivity(i);
    }
}
