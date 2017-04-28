package com.tutorius.tutorius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pedir_hora extends AppCompatActivity {

    String profesor;
    String usuario;
    String datos_cita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_hora);

        Bundle b = this.getIntent().getExtras();
        profesor = b.getString("UVUS_PROFESOR");
        usuario=b.getString("USUARIO");
        datos_cita =b.getString("DIA_CITA");





    }
}
