package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfesoresHoy extends Fragment
{
    ListView listView;
    List<String> names= new ArrayList<String>();
    List<Row> rows;
    String usuario;
    // IP de mi Url
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
    // Rutas de los Web Services
    String getProfesor=IP+"/getCitasProfesorHoy.php";

    String[] listaa;

    View rootView;

    Button btnCancelar;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub



        rootView = inflater.inflate(R.layout.fragment_citas_profesor, container, false);
        listView= (ListView) rootView.findViewById(R.id.listView1);
        rows = new ArrayList<Row>();

        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        mCLayout = (TextView) rootView.findViewById(R.id.errores);

        btnCancelar = (Button)rootView.findViewById(R.id.btnCancelar);



        Bundle b = getActivity().getIntent().getExtras();
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
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("citas");



                            listaa = new String[array.length()];
                            rows = new ArrayList<Row>();
                            Row fila = null;
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject row = array.getJSONObject(i);

                                fila = new Row();
                                fila.setTitle(row.getString("HORA_INICIO") + " ");
                                fila.setSubtitle(row.getString("FECHA") + " ");
                                fila.setId(row.getString("ID_RESERVA") + " ");
                                fila.setCancelada(row.getString("CANCELADA"));


                                // Display the formatted json data in text view

                                rows.add(fila);

                            }


                            listView= (ListView) rootView.findViewById(R.id.listView1);


                            listView.setAdapter(new CustomArrayAdapter(getContext(), rows));

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android

                //obtengo el valor del string del elemento donde se hizo clic
                //String itemValue = (String) li.getItemAtPosition(position);
                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
                cb.setChecked(!cb.isChecked());

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {

                View v;
                CheckBox cb;
                for(int i=0;i < listView.getCount();i++) {
                    Row item = (Row) listView.getItemAtPosition(i);

                    v = listView.getChildAt(i);
                    cb = (CheckBox) v.findViewById(R.id.checkBox);
                    if (cb.isChecked()){


                        String cadenallamada = IP + "/getDeleteCitasProfesor.php?id_reserva=" + item.getId();


                        // Initialize a new RequestQueue instance
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                        // Initialize a new JsonObjectRequest instance
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.GET,
                                cadenallamada,
                                null,
                                null,
                                null
                        );

                        // Add JsonObjectRequest to the RequestQueue
                        requestQueue.add(jsonObjectRequest);



                        getActivity().finish();
                        startActivity(getActivity().getIntent());


                    }


                }


            }
        });

        return rootView;
    }

}
