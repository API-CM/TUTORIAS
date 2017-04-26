package com.tutorius.tutorius;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Datos_cita extends AppCompatActivity implements View.OnClickListener{


    String usuario;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cita);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");

        boton = (Button) findViewById(R.id.boton);

        boton.setOnClickListener(this);

    }


    //metodos para el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        Bundle b = new Bundle();

        switch(id) {

            case R.id.action_settings_back:

                Intent intent1 = new Intent(Datos_cita.this, MainActivity.class);

                b.putString("UVUS",usuario);
                intent1.putExtras(b);
                startActivity(intent1);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(Datos_cita.this, MainActivity.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_out:

                Intent intent3 = new Intent(Datos_cita.this, MainActivity.class);
                startActivity(intent3);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //

    public void onClick(View v) {

        Intent intent = new Intent(Datos_cita.this,MainActivity.class);
        startActivity(intent);

    }

}
