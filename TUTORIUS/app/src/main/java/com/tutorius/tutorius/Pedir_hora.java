package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedir_hora extends AppCompatActivity {

    String profesor;
    String usuario;
    String fecha;
    String dia_semana;
    String datos_cita;
    ListView lista_horas;
    String[] listaa;
    String hora_inicio;
    Date in;
    Date fi;
    String hora_fin;
    ArrayList<String> reserva = new ArrayList<>();
    ArrayList<String> all_hour = new ArrayList<>();
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm");


    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;

    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_hora);

        Bundle b = this.getIntent().getExtras();
        profesor = b.getString("UVUS_PROFESOR");
        usuario=b.getString("USUARIO");
        datos_cita =b.getString("DIA_CITA");

        mContext = getApplicationContext();
        mActivity = Pedir_hora.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        String[] datos = datos_cita.split(" ");
        dia_semana = datos[0];
        fecha = datos[1];


        String cadenallamada =  IP + "/getHorasCitasProf.php?uvus_profesor=" + profesor + "&dia_semana=" + dia_semana;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
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
                            JSONArray array = response.getJSONArray("horas");

                            listaa = new String[array.length()];
                            for(int i=0;i<array.length();i++) {
                                // Get current json object
                                JSONObject hora = array.getJSONObject(i);

                                hora_inicio = hora.getString("HORA_INICIO");
                                hora_fin = hora.getString("HORA_FIN");

                            }


                            Date hora_in = null;
                            Date hora_fi = null;
                            try {

                                hora_in = formatoDelTexto.parse(hora_inicio);
                                hora_fi = formatoDelTexto.parse(hora_fin);

                            } catch (ParseException ex) {

                                ex.printStackTrace();

                            }
                            in = hora_in;
                            fi = hora_fi;

                            //COGER CITAS DE ESE DIA CON ESE PROFESOR QUE ESTEN YA RESERVADAS
                            String cadenallamada2 = IP + "/getCitasReservadas.php?uvus_profesor=" + profesor + "&fecha=" + fecha;

                            // Initialize a new RequestQueue instance
                            RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                            // Initialize a new JsonObjectRequest instance
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.GET,
                                    cadenallamada2,
                                    null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            // Do something with response
                                            //mTextView.setText(response.toString());

                                            // Process the JSON
                                            try {
                                                // Get the JSON array
                                                JSONArray array2 = response.getJSONArray("reservadas");


                                                for (int i = 0; i < array2.length(); i++) {

                                                    JSONObject reser = array2.getJSONObject(i);
                                                    String r = reser.getString("HORA_INICIO");
                                                    reserva.add(r);

                                                }

                                                while(in.before(fi)){
                                                    String f = formatoDelTexto.format(in);
                                                    if(!reserva.contains(f)) {
                                                        all_hour.add(f);
                                                        in = sumar10min(in);
                                                    }else{
                                                        in = sumar10min(in);
                                                    }

                                                }

                                                listaa = new String[all_hour.size()];
                                                for(int i = 0; i<all_hour.size();i++){
                                                    listaa[i]=all_hour.get(i);

                                                }

                                                ponerenlistView(listaa);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }


                                    }, null
                            );

                            // Add JsonObjectRequest to the RequestQueue
                            requestQueue.add(jsonObjectRequest);















                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                },null
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);




    }

    public Date sumar10min(Date hora){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hora); // Configuramos la fecha que se recibe
        calendar.add(Calendar.MINUTE, 10);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }


    public void ponerenlistView(String[] list){

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);

        lista_horas = (ListView)findViewById(R.id.dia_cita);

        lista_horas.setAdapter(adaptador);

        ///prueba
        lista_horas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                int posicion = position;

                //obtengo el valor del string del elemento donde se hizo clic
                String itemValue = (String) lista_horas.getItemAtPosition(position);



                Intent intent = new Intent(getApplicationContext(), Coger_cita.class);
                Bundle b = new Bundle();
                b.putString("DIA_SEMANA", dia_semana);
                b.putString("FECHA", fecha);
                b.putString("HORA",itemValue);
                b.putString("UVUS_PROFESOR",profesor);
                b.putString("USUARIO",usuario);
                intent.putExtras(b);
                startActivity(intent);


            }
        });
    }
}
