package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
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

import java.util.Date;

public class Pedir_cita extends AppCompatActivity {

    String usuario;
    String profesor;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;

    ListView listadias;
    String[] listaa;
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita);

        Bundle b = this.getIntent().getExtras();
        profesor = b.getString("UVUS_PROFESOR");
        usuario=b.getString("UVUS");



        mContext = getApplicationContext();
        mActivity = Pedir_cita.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        String cadenallamada =  IP + "/getDiasCitasProf.php?uvus_profesor=" + profesor;

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
                            JSONArray array = response.getJSONArray("dias");
                            // JSONArray array2 = response.getJSONArray("despacho");

                            listaa = new String[array.length()];
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject dias = array.getJSONObject(i);
                                //JSONObject despa = array2.getJSONObject(i);

                                String dia = dias.getString("DIA_SEMANA");

                                //OBTIENE EL DIA DE LA SEMANA DE HOY
                                String hoy = obtenerDiaSemana();

                                //CALCULO DEL DÍA ACTUAL
                                Calendar cal = new GregorianCalendar();
                                Date date = cal.getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String formatteDate = df.format(date);

                                if(dia != hoy){
                                    //VER EL DIA QUE ES EL DIA Y SI ES POSTERIOR AL DIA DE LA SEMANA QUE ES HOY AÑADIRLO CON LA FECHA



                                }else{
                                    listaa[i]=dia + " " + formatteDate;
                                }



                                // Display the formatted json data in text view

                               // String fecha =



                            }

                            //RECORRER LA LISTA ANTES DE ENVIARLA AL LISTVIEW PARA PONERLE LOS DIAS DE LA SEMANA SIGUIENTE

                            ponerenlistView(listaa);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                },null
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);



    }

    private static String obtenerDiaSemana(){
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
        Date hoy=new Date();
        int numeroDia=0;
        Calendar cal=  Calendar.getInstance();
        cal.setTime(hoy);
        numeroDia=cal.get(Calendar.DAY_OF_WEEK);
        return dias[numeroDia - 1];
    }

    public void ponerenlistView(String[] list){

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);

        listadias = (ListView)findViewById(R.id.dia_cita);

        listadias.setAdapter(adaptador);

        ///prueba
        listadias.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                int posicion = position;

                //obtengo el valor del string del elemento donde se hizo clic
                String itemValue = (String) listadias.getItemAtPosition(position);

                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención

                Intent intent = new Intent(getApplicationContext(), Datos_cita.class);
                Bundle b = new Bundle();
                b.putString("UVUS_PROFESOR","profesor1");
                b.putString("USUARIO",usuario);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
