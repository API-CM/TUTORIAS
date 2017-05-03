package com.tutorius.tutorius;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
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

public class EditarPerfilProfesor extends AppCompatActivity implements View.OnClickListener {

    String usuario;
    String nombre;
    String email;
    String despacho;
    String departamento;
    Button actualizar;
    Button modasig;
    Button modhor;
    private Context mContext;
    EditText depar;
    EditText mail;
    EditText despa;
    TextView nombreT;
    Switch dispo;
    String disponible1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_profesor);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        depar = (EditText) findViewById(R.id.departamento);
        mail = (EditText) findViewById(R.id.email);
        despa = (EditText) findViewById(R.id.despacho);
        nombreT = (TextView) findViewById(R.id.name);
        dispo = (Switch)findViewById(R.id.disponibilidad);

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");
        nombre = b.getString("NOMBRE");
        email = b.getString("EMAIL");
        despacho = b.getString("DESPACHO");
        departamento = b.getString("DEPARTAMENTO");
        disponible1 = b.getString("DISPONIBILIDAD");

        if(disponible1.equals("ON")){
            dispo.setChecked(true);
        }else{
            dispo.setChecked(false);
        }

        actualizar = (Button) findViewById(R.id.update);
        modasig = (Button) findViewById(R.id.modificarasigs);
        modhor = (Button) findViewById(R.id.modificarhorario);

        actualizar.setOnClickListener(this);
        modasig.setOnClickListener(this);
        modhor.setOnClickListener(this);

        nombreT.setText(nombre);
        depar.setText(departamento);
        mail.setText(email);
        despa.setText(despacho);


    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:
                mContext = getApplicationContext();
                String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
                // Rutas de los Web Services
                String getUpdate = IP+"/getUpdateProfesor.php?uvus_profesor=" + usuario +"&departamento=" + depar.getText() + "&despacho="+ despa.getText() + "&email="+mail.getText() + "&disponibilidad=" + dispo.isChecked();


                RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                // Initialize a new JsonObjectRequest instance
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        getUpdate,
                        null,
                        null,null
                );

                // Add JsonObjectRequest to the RequestQueue
                requestQueue.add(jsonObjectRequest);

                Intent intent = new Intent(EditarPerfilProfesor.this,ModificacionPerfilProfesor.class);
                Bundle b = new Bundle();
                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);



                break;
            case R.id.modificarasigs:
                Intent intent2 = new Intent(EditarPerfilProfesor.this,ModificarAsignaturas.class);
                Bundle b2 = new Bundle();
                b2.putString("UVUS",usuario);
                b2.putString("NOMBRE",nombre);
                b2.putString("DEPARTAMENTO",departamento);
                b2.putString("EMAIL",email);
                b2.putString("DESPACHO",despacho);
                b2.putString("DISPONIBILIDAD",disponible1);
                intent2.putExtras(b2);
                startActivity(intent2);
                break;
            case R.id.modificarhorario:
                Intent intent3 = new Intent(EditarPerfilProfesor.this,ModificarHorario.class);
                Bundle b3 = new Bundle();
                b3.putString("UVUS",usuario);
                b3.putString("NOMBRE",nombre);
                b3.putString("DEPARTAMENTO",departamento);
                b3.putString("EMAIL",email);
                b3.putString("DESPACHO",despacho);
                b3.putString("DISPONIBILIDAD",disponible1);
                intent3.putExtras(b3);
                startActivity(intent3);
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

                Intent intent = new Intent(EditarPerfilProfesor.this, ModificacionPerfilProfesor.class);

                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(EditarPerfilProfesor.this, Profesor.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_profile:

                Intent intent3 = new Intent(EditarPerfilProfesor.this, ModificacionPerfilProfesor.class);

                b.putString("UVUS",usuario);
                intent3.putExtras(b);
                startActivity(intent3);
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(EditarPerfilProfesor.this, MainActivity.class);

                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //

    //EN CASO DE PULSAR EL BOTÓN ATRÁS DEL MÓVIL SE RECARGARA LA ACTIVITY DE MODICIACIONPERFILPROFESOR YA QUE PUEDE HABER CAMBIADO SU HORARIO O LAS ASIGNATURAS
    public void onBackPressed (){
        Intent intent4 = new Intent(EditarPerfilProfesor.this,ModificacionPerfilProfesor.class);
        Bundle b4 = new Bundle();
        b4.putString("UVUS",usuario);
        intent4.putExtras(b4);
        startActivity(intent4);
    }

}
