package com.tutorius.tutorius;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class ModificarHorario extends AppCompatActivity implements View.OnClickListener{

    Button add;
    String usuario;
    String getAddHorario;
    Spinner dia;
    Spinner hora_ini;
    Spinner hora_fi;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_horario);

        add = (Button)findViewById(R.id.add_franja);

        add.setOnClickListener(this);

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");


        dia = (Spinner) findViewById(R.id.dia_semana);
        String[] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes"};
        dia.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dias));


        hora_ini = (Spinner) findViewById(R.id.hora_inicio);
        String[] hora = {"8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30"};
        hora_ini.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hora));

        hora_fi = (Spinner) findViewById(R.id.hora_fin);
        hora_fi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, hora));




    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_franja:


                String a = diaSeleccionado();
                String b = horainicioSeleccionado();
                String c = horafinSeleccionado();

                String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
                // Rutas de los Web Services
                getAddHorario=IP+"/getAddHorario.php?uvus_profesor="+ usuario + "&hora_inicio=" + b + "&hora_fin=" + c + "&dia_semana=" + a;

                mContext = getApplicationContext();

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        getAddHorario,
                        null,
                        null,null
                );

                // Add JsonObjectRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);

                break;
            default:

                break;
        }
    }

    public String diaSeleccionado(){
        return dia.getSelectedItem().toString();
    }

    public String horainicioSeleccionado(){
        return hora_ini.getSelectedItem().toString();
    }

    public String horafinSeleccionado(){
        return hora_fi.getSelectedItem().toString();
    }



}
