package com.tutorius.tutorius;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModificarHorario extends AppCompatActivity implements View.OnClickListener{

    Button add;
    String usuario;
    String getAddHorario;
    Spinner dia;
    Spinner hora_ini;
    Spinner hora_fi;
    ListView lista_horario;
    Button delete_horario;
    List<Row> rows;
    String[] listaa;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_horario);

        add = (Button)findViewById(R.id.add_franja);
        delete_horario = (Button)findViewById(R.id.delhorario);

        add.setOnClickListener(this);
        delete_horario.setOnClickListener(this);

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


        rows = new ArrayList<Row>(30);

        String cadenallamada = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getProfesores.php?uvus_profesor=" + usuario;

        mContext = getApplicationContext();

        RequestQueue requestQueue2 = Volley.newRequestQueue(mContext);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                cadenallamada,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("horario");



                            listaa = new String[array.length()];
                            rows = new ArrayList<Row>();
                            Row fila = null;
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject row = array.getJSONObject(i);
                                fila = new Row();
                                String dia = row.getString("DIA_SEMANA");
                                String hora_ini = row.getString("HORA_INICIO");
                                String hora_fin = row.getString("HORA_FIN");
                                fila.setTitle(dia + " - " + hora_ini + " - " + hora_fin);


                                // Display the formatted json data in text view

                                rows.add(fila);

                            }


                            lista_horario= (ListView) findViewById(R.id.list_horario);


                            lista_horario.setAdapter(new CustomArrayAdapter(mContext, rows));


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, null
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue2.add(jsonObjectRequest2);


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
            case R.id.delhorario:
                ArrayList<String> elegidas = new ArrayList<>();

                for(int i=0;i<rows.size();i++){
                    Row ro = rows.get(i);
                    if(ro.isChecked()){
                        elegidas.add(ro.getTitle());
                    }
                }

                if(elegidas.size() != 0) {

                    for (int i = 0; i < elegidas.size(); i++) {
                        String[] split = elegidas.get(i).split(" - ");
                        String del = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getDeleteHorario.php?uvus_profesor=" + usuario + "&dia_semana=" + split[0] + "&hora_inicio=" + split[1] + "&hora_fin=" + split[2];
                        // Rutas de los Web Services


                        mContext = getApplicationContext();

                        RequestQueue requestQueue2 = Volley.newRequestQueue(mContext);

                        // Initialize a new JsonObjectRequest instance
                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                                Request.Method.GET,
                                del,
                                null,
                                null,null
                        );

                        // Add JsonObjectRequest to the RequestQueue
                        requestQueue2.add(jsonObjectRequest2);
                    }
                }

                finish();
                startActivity(getIntent());


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
