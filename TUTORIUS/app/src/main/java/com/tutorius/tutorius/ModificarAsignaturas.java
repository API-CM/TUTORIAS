package com.tutorius.tutorius;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ModificarAsignaturas extends AppCompatActivity implements View.OnClickListener{

    String usuario;
    Button añadir;
    EditText asig;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_asignaturas);


        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");
        añadir = (Button)findViewById(R.id.add);
        asig = (EditText)findViewById(R.id.nom_asig);

        añadir.setOnClickListener(this);

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

                break;
            default:

                break;
        }
    }
}
