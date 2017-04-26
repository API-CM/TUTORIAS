package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Principal_alumno extends AppCompatActivity implements View.OnClickListener{

    Button boton;
    String usuario;
    String despacho;
    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;
    ListView listacitas;
    String[] listaa;
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_alumno);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        boton = (Button) findViewById(R.id.boton);

        boton.setOnClickListener(this);

        //-----------------
        mContext = getApplicationContext();
        mActivity = Principal_alumno.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");

        String cadenallamada =  IP + "/getCitasAlumno.php?uvus_alumno=" + usuario;

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
                            JSONArray array = response.getJSONArray("citas");
                         // JSONArray array2 = response.getJSONArray("despacho");

                            listaa = new String[array.length()];
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject cita = array.getJSONObject(i);
                              //JSONObject despa = array2.getJSONObject(i);

                                String fecha = cita.getString("FECHA");
                                String hora = cita.getString("HORA_INICIO");
                                String id_reserva = cita.getString("ID_RESERVA");




                                // Display the formatted json data in text view

                                listaa[i]="ID-CITA: " + id_reserva + "  -  " + fecha + "  -  " + hora;

                            }

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






    public void ponerenlistView(String[] list){

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);

        listacitas = (ListView)findViewById(R.id.citas_alumno);

        listacitas.setAdapter(adaptador);

        ///prueba
        listacitas.setOnItemClickListener(new AdapterView.OnItemClickListener(){

              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  // La posición donde se hace clic en el elemento de lista se obtiene de la
                  // la posición de parámetro de la vista de lista de Android
                  int posicion = position;

                  //obtengo el valor del string del elemento donde se hizo clic
                  String itemValue = (String) listacitas.getItemAtPosition(position);

                  //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención

                  Intent intent = new Intent(getApplicationContext(), Datos_cita.class);
                  Bundle b = new Bundle();
                  b.putString("CITA",itemValue);
                  b.putString("USUARIO",usuario);
                  intent.putExtras(b);

                  // Aquí pasaremos el parámetro de la intención creada previamente
                  startActivity(intent);
              }
        });
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

                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings_home:

                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(Principal_alumno.this, MainActivity.class);
                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //

    public void onClick(View v) {

        Intent intent = new Intent(Principal_alumno.this,Alumnos.class);
        Bundle b1 = new Bundle();
        b1.putString("UVUS",usuario);
        intent.putExtras(b1);
        startActivity(intent);

    }
}
