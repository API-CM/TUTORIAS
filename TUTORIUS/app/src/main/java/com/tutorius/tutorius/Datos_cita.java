package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class Datos_cita extends AppCompatActivity implements View.OnClickListener{


    String cita;
    String usuario;
    Button boton;
    Button pasar;
    String fecha;
    String hora;
    String id;
    String cancel;

    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;

    TextView faux;
    TextView haux;
    TextView nombre1;
    TextView email1;
    TextView despacho1;
    ImageView foto1;
    TextView fcancelado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cita);

        faux = (TextView)findViewById(R.id.date);
        haux = (TextView)findViewById(R.id.hourCitaProf);
        nombre1 = (TextView) findViewById(R.id.nombre);
        email1 = (TextView)findViewById(R.id.email);
        despacho1 = (TextView)findViewById(R.id.despacho);
        foto1 = (ImageView)findViewById(R.id.imageView);
        fcancelado = (TextView)findViewById(R.id.cancelado);


        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        Bundle b = this.getIntent().getExtras();
        cita = b.getString("CITA");
        usuario=b.getString("USUARIO");

        String aux[] = cita.split("  -  ");
        String aux2[] = aux[0].split(" ");
        String aux3[] = aux[2].split(" ");

        fecha = aux[1].trim();
        hora = aux3[0].trim();

        if(aux3.length >= 2) {
            cancel = aux3[1].trim();
        }else{
            cancel = "";
        }

        id=aux2[1];

        //----------------------peticion----------------------
        //-----------------
        mContext = getApplicationContext();
        mActivity = Datos_cita.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        String cadenallamada =  IP + "/getProfesorCitas.php?id_reserva=" + id;

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
                            JSONArray array = response.getJSONArray("profesor");
                            // JSONArray array2 = response.getJSONArray("despacho");

                            for(int i=0;i<array.length();i++) {
                                // Get current json object
                                JSONObject profesor = array.getJSONObject(i);

                                // Get the current student (json object) data
                                String firstName = profesor.getString("NOMBRE");
                                String lastName = profesor.getString("APELLIDO1");
                                String mail = profesor.getString("EMAIL");
                                String despa = profesor.getString("DESPACHO");
                                String urlfoto = profesor.getString("FOTO_PERSONAL");


                                // Display the formatted json data in text view
                                nombre1.setText(firstName + " " + lastName);
                                despacho1.setText(despa);
                                email1.setText(mail);

                                String site = IP + "/img/" + urlfoto;

                                if (urlfoto != null) {
                                    RequestQueue colaPeticiones = Volley.newRequestQueue(mContext);
                                    ImageRequest peticion = new ImageRequest(
                                            site,
                                            new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    foto1.setImageBitmap(bitmap);
                                                }
                                            }, 0, 0, null, null);
                                    colaPeticiones.add(peticion);
                                }
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener(){
                    @Override
                        public void onErrorResponse(VolleyError error){
                            // Do something when error occurred
                            Snackbar.make(
                                    mCLayout,
                                    "Error",
                                    Snackbar.LENGTH_LONG
                            ).show();
                        }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);

        faux.setText(fecha);
        haux.setText(hora);
        fcancelado.setText(cancel);

        boton = (Button)findViewById(R.id.delete);

        boton.setOnClickListener(this);

        //PARA PASAR A LA PARTE DE ENRIQUE Y ALVARO SOLO ES TEMPORAL
        pasar = (Button) findViewById(R.id.olakase);
        pasar.setOnClickListener(this);


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

                Intent intent1 = new Intent(Datos_cita.this, Principal_alumno.class);

                b.putString("UVUS",usuario);
                intent1.putExtras(b);
                startActivity(intent1);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(Datos_cita.this, Principal_alumno.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_out:

                Intent intent3 = new Intent(Datos_cita.this, Principal_alumno.class);
                startActivity(intent3);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.delete:
                String del = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getDeleteReserva.php?id_reserva=" + id;
                // Rutas de los Web Services

                mContext = getApplicationContext();

                RequestQueue requestQueue2 = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                        Request.Method.GET,
                        del,
                        null,
                        null, null
                );

                // Add JsonObjectRequest to the RequestQueue
                requestQueue2.add(jsonObjectRequest2);

                Intent intent = new Intent(Datos_cita.this, Principal_alumno.class);
                Bundle b = new Bundle();
                b.putString("UVUS", usuario);
                intent.putExtras(b);
                startActivity(intent);
                break;

            case R.id.olakase:
                Intent intent2 = new Intent(Datos_cita.this, Pedir_cita.class);
                Bundle b1 = new Bundle();
                b1.putString("UVUS", usuario);
                b1.putString("UVUS_PROFESOR", "profesor1");
                intent2.putExtras(b1);
                startActivity(intent2);

                break;
            default:

                break;
        }
    }

}
