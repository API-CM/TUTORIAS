package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.bitmap;

public class ModificacionPerfilProfesor extends AppCompatActivity implements View.OnClickListener{

    TextView nombre;
    TextView departamento;
    TextView email;
    TextView horario;
    TextView despacho;
    ListView listaasigs;
    TextView dispo;

    ImageView foto;


    Button edit;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;




    String usuario;
    // IP de mi Url
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
    // Rutas de los Web Services
    String getProfesor=IP+"/getProfesores.php";

    String[] listaa;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_perfil_profesor);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        nombre = (TextView) findViewById(R.id.nombre);
        departamento = (TextView)findViewById(R.id.departamento);
        email = (TextView)findViewById(R.id.email);
        horario = (TextView)findViewById(R.id.horario);
        despacho = (TextView)findViewById(R.id.despacho);
        dispo = (TextView) findViewById(R.id.disponibilidad);
        edit = (Button)findViewById(R.id.editar);
        final Bitmap bitmap;

        foto = (ImageView)findViewById(R.id.imageView);



        edit.setOnClickListener(this);

        mContext = getApplicationContext();
        mActivity = ModificacionPerfilProfesor.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");



        String cadenallamada = getProfesor + "?uvus_profesor=" + usuario;



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

                        try{
                            JSONArray array = response.getJSONArray("profesor");
                            JSONArray array2 = response.getJSONArray("asignaturas");
                            JSONArray array3 = response.getJSONArray("horario");

                            for(int i=0;i<array.length();i++){
                                JSONObject profesor = array.getJSONObject(i);

                                String firstName = profesor.getString("NOMBRE");
                                String lastName = profesor.getString("APELLIDO1");
                                String mail = profesor.getString("EMAIL");
                                String despa = profesor.getString("DESPACHO");
                                String depar = profesor.getString("SIGLAS");
                                String dispo2 = profesor.getString("DISPONIBILIDAD");
                                String urlfoto = profesor.getString("FOTO_PERSONAL");



                                if(dispo2.equals("0")){
                                    dispo.setText("OFF");
                                }else{
                                    dispo.setText("ON");
                                }

                                nombre.setText(firstName +" " + lastName);
                                email.setText(mail);
                                despacho.setText(despa);
                                departamento.setText(depar);

                                String site = IP + "/img/" + urlfoto;

                                if(urlfoto != null) {
                                    RequestQueue colaPeticiones = Volley.newRequestQueue(mContext);
                                    ImageRequest peticion = new ImageRequest(
                                            site,
                                            new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap bitmap) {
                                                    foto.setImageBitmap(bitmap);
                                                }
                                            }, 0, 0, null, null);
                                    colaPeticiones.add(peticion);
                                }
                            }

                            listaa = new String[array2.length()];
                            for(int i=0;i<array2.length();i++){
                                // Get current json object
                                JSONObject asig = array2.getJSONObject(i);

                                listaa[i] = asig.getString("NOMBRE") + " ";

                            }

                            ponerenlistView(listaa);

                            for(int i=0;i<array3.length();i++){
                                // Get current json object
                                JSONObject hora = array3.getJSONObject(i);

                                String dia = hora.getString("DIA_SEMANA");
                                String hora_inicio = hora.getString("HORA_INICIO");
                                String hora_fin = hora.getString("HORA_FIN");
                                // Display the formatted json data in text view
                                //EL if es para evitar que el ultimo meta tambien un salto de linea.
                                if(i!=array3.length()-1){
                                    horario.append(dia + ": " + hora_inicio + " - " + hora_fin + "\n");
                                }else{
                                    horario.append(dia + ": " + hora_inicio + " - " + hora_fin);
                                }

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
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



    }

    //metodos para el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void ponerenlistView(String[] list){

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);

        listaasigs = (ListView)findViewById(R.id.listasig);

        listaasigs.setAdapter(adaptador);
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

                Intent intent = new Intent(ModificacionPerfilProfesor.this, Profesor.class);
                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(ModificacionPerfilProfesor.this, Profesor.class);
                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_profile:
                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings_out:
                Intent intent4 = new Intent(ModificacionPerfilProfesor.this, MainActivity.class);
                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editar:
                Intent intent = new Intent(ModificacionPerfilProfesor.this,EditarPerfilProfesor.class);
                Bundle b = new Bundle();
                b.putString("UVUS",usuario);
                b.putString("NOMBRE",nombre.getText().toString());
                b.putString("EMAIL",email.getText().toString());
                b.putString("DEPARTAMENTO",departamento.getText().toString());
                b.putString("DESPACHO",despacho.getText().toString());
                b.putString("DISPONIBILIDAD", dispo.getText().toString());

                intent.putExtras(b);
                startActivity(intent);

                break;
            default:

                break;
        }
    }


}
