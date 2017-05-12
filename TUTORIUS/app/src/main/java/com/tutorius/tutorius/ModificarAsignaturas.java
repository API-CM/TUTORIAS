package com.tutorius.tutorius;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ModificarAsignaturas extends AppCompatActivity implements View.OnClickListener{

    String usuario;
    String nombre;
    String email;
    String departamento;
    String despacho;
    Button añadir;
    Button eliminar;
    EditText asig;
    ListView lista;
    List<Row> rows;
    String[] listaa;
    private Context mContext;
    String disponible1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_asignaturas);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");
        nombre = b.getString("NOMBRE");
        email = b.getString("EMAIL");
        despacho = b.getString("DESPACHO");
        departamento = b.getString("DEPARTAMENTO");
        disponible1 = b.getString("DISPONIBILIDAD");
        añadir = (Button)findViewById(R.id.add);
        eliminar = (Button)findViewById(R.id.delete);
        asig = (EditText)findViewById(R.id.nom_asig);

        añadir.setOnClickListener(this);
        eliminar.setOnClickListener(this);





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
                        try{
                            JSONArray array = response.getJSONArray("asignaturas");

                            listaa = new String[array.length()];
                            rows = new ArrayList<Row>();
                            Row fila = null;
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject row = array.getJSONObject(i);
                                fila = new Row();
                                fila.setTitle(row.getString("NOMBRE"));

                                rows.add(fila);
                            }

                            lista= (ListView) findViewById(R.id.list_asigs);
                            lista.setAdapter(new CustomArrayAdapter(mContext, rows));

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
            case R.id.add:
                String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
                // Rutas de los Web Services
                String getAddAsig=IP+"/getAddAsignatura.php?uvus_profesor="+ usuario + "&asignatura=" + asig.getText();

                mContext = getApplicationContext();

                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        getAddAsig,
                        null,
                        null,null
                );

                // Add JsonObjectRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);

                finish();
                startActivity(getIntent());

                break;
            case R.id.delete:
                ArrayList<String> elegidas = new ArrayList<>();

                for(int i=0;i<rows.size();i++){
                    Row ro = rows.get(i);
                    if(ro.isChecked()){
                        elegidas.add(ro.getTitle());
                    }
                }

                if(elegidas.size() != 0) {

                    for (int i = 0; i < elegidas.size(); i++) {
                        String del = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getDeleteAsig.php?uvus_profesor=" + usuario + "&asignatura=" + elegidas.get(i);
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

    //metodos para el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

                Intent intent = new Intent(ModificarAsignaturas.this, EditarPerfilProfesor.class);

                b.putString("UVUS",usuario);
                b.putString("NOMBRE",nombre);
                b.putString("EMAIL",email);
                b.putString("DESPACHO",despacho);
                b.putString("DEPARTAMENTO",departamento);
                b.putString("DISPONIBILIDAD",disponible1);
                intent.putExtras(b);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(ModificarAsignaturas.this, Profesor.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_profile:

                Intent intent3 = new Intent(ModificarAsignaturas.this, ModificacionPerfilProfesor.class);

                b.putString("UVUS",usuario);
                intent3.putExtras(b);
                startActivity(intent3);
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(ModificarAsignaturas.this, MainActivity.class);

                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    
}
