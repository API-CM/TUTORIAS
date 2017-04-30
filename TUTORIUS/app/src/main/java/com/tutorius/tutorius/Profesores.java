package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Profesores extends Fragment {
    ListView li;
    ArrayList nombresProfs = new ArrayList();
    ArrayList deptProfs = new ArrayList();
    ArrayList rows;
    Button btnProf;
    EditText bsqProf;

    View rootView;
    String usuario;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.profesores, container, false);
        li= (ListView)rootView.findViewById(R.id.listViewProf); //buscas en el XML el id de dicho elemento listView
        rows = new ArrayList<Row>();
        btnProf=(Button)rootView.findViewById(R.id.btnbusqprof);
        bsqProf=(EditText)rootView.findViewById(R.id.busquedaprof);

        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        mCLayout = (TextView) rootView.findViewById(R.id.errores);

        Bundle b = getActivity().getIntent().getExtras();
        usuario = b.getString("UVUS");

        //String cadenallamada = getAlumno + "?uvus_profesor=" + usuario;


        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // setContentView(R.layout.departamento);

        getProfesores();

        btnProf.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {
                ArrayList<Row> rows2=new ArrayList<Row>();
                for(int i=0;i<rows.size();i++) {

                    Row r = (Row) rows.get(i);

                    if ( r.getTitle().contains(bsqProf.getText()) || r.getSubtitle().contains(bsqProf.getText()) ||
                            r.getTitle().toLowerCase().contains(bsqProf.getText()) ||
                            r.getSubtitle().toLowerCase().contains(bsqProf.getText()) ) {
                        rows2.add(r);
                        li.setAdapter(new CustomArrayAdapterDept(getContext(), rows2));
                    }
                }
            }
        });

        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //PEDIR CITA

                Row r= (Row) li.getItemAtPosition(position);
                String pasar=null;

                for(int k=0;k<rows.size();k++){
                    Row r2=(Row)rows.get(k);

                    if(r.getSubtitle().contains(r2.getSubtitle())){
                        pasar=r2.getId();
                    }
                }

                Intent intent = new Intent(getActivity(), AlumnoPideCitaProfesor.class);
                Bundle b = new Bundle();
                b.putString("ID",pasar);  //uvus identificatorio del profesor
                b.putString("UVUS",usuario);
                intent.putExtras(b);

                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getProfesores() {
        nombresProfs.clear();
        deptProfs.clear();
        rows.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getAllProfesores.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        Row fila = null;
                        for(int i=0;i<jsonArray.length();i++){
                            // Get current json object
                            JSONObject row = jsonArray.getJSONObject(i);
                            fila = new Row();
                            fila.setTitle(row.getString("NOMBRE") + " "+ row.getString("APELLIDO1")+ " " +
                                    row.getString("APELLIDO2") );
                            fila.setSubtitle(row.getString("DESPACHO") + " ");
                            fila.setId(row.getString("UVUS_PROFESOR")); //En este caso nuestro ID será el UVUS del profesor

                            rows.add(fila);

                        }

                        li.setAdapter(new CustomArrayAdapterDept(getContext(),rows));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}