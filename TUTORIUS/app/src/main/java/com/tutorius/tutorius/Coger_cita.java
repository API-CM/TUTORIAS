package com.tutorius.tutorius;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Coger_cita extends AppCompatActivity implements View.OnClickListener{

    String profesor;
    String usuario;
    String fecha;
    String dia_semana;
    String hora;
    TextView fe;
    TextView ho;
    TextView dia;
    TextView prof;
    Button save;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coger_cita);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        Bundle b = this.getIntent().getExtras();
        profesor = b.getString("UVUS_PROFESOR");
        usuario=b.getString("USUARIO");
        fecha =b.getString("FECHA");
        dia_semana =b.getString("DIA_SEMANA");
        hora =b.getString("HORA");

        fe= (TextView)findViewById(R.id.fech);
        ho= (TextView)findViewById(R.id.hor);
        dia= (TextView)findViewById(R.id.dia_sem);
        prof= (TextView)findViewById(R.id.profesor);
        save = (Button)findViewById(R.id.guardar);

        save.setOnClickListener(this);

        fe.setText(fecha);
        ho.setText(hora);
        dia.setText(dia_semana);
        prof.setText(profesor);

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guardar:
                String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
                // Rutas de los Web Services
                String getAddCita=IP+"/getAddCita.php?uvus_profesor="+ prof.getText() + "&hora_inicio=" + ho.getText() + "&fecha=" + fe.getText() + "&uvus_alumno=" + usuario;

                mContext = getApplicationContext();

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        getAddCita,
                        null,
                        null,null



                );

                Intent intent = new Intent(getApplicationContext(), Principal_alumno.class);
                Bundle b = new Bundle();
                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);

                // Add JsonObjectRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);



                break;
            default:

                break;
        }
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

                Intent intent = new Intent(Coger_cita.this, Alumnos.class);

                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(Coger_cita.this, Principal_alumno.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(Coger_cita.this, MainActivity.class);
                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //
}
