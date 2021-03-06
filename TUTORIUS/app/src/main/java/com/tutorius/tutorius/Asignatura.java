package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class Asignatura extends Fragment {
    ListView li;
    ArrayList nombresAsig = new ArrayList();
    ArrayList siglasAsig = new ArrayList();
    ArrayList rows;
    Button btnAsig;
    EditText bsqAsig;
    View rootView;
    String usuario;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.asignatura, container, false);
        li= (ListView)rootView.findViewById(R.id.listViewAsig); //buscas en el XML el id de dicho elemento listView
        rows = new ArrayList<Row>();
        btnAsig=(Button)rootView.findViewById(R.id.btnbusqasig);
        bsqAsig=(EditText)rootView.findViewById(R.id.busquedaasig);

        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        mCLayout = (TextView) rootView.findViewById(R.id.errores);

        Bundle b = getActivity().getIntent().getExtras();
        usuario = b.getString("UVUS");


        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        getAsignaturas();

        btnAsig.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {
                ArrayList<Row> rows2=new ArrayList<Row>();
                for(int i=0;i<rows.size();i++) {

                    Row r = (Row) rows.get(i);

                    if ( r.getTitle().contains(bsqAsig.getText()) || r.getSubtitle().contains(bsqAsig.getText()) ||
                        r.getTitle().toLowerCase().contains(bsqAsig.getText()) ||
                            r.getSubtitle().toLowerCase().contains(bsqAsig.getText()) ) {
                        rows2.add(r);
                        li.setAdapter(new CustomArrayAdapterDept(getContext(), rows2));
                    }
                }
            }
        });

        //PASO LAS VISTAS A UNA NUEVA ACTIVITY DESDE LA SELECCIONADA
        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){    //HACINEDO ----

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Row r= (Row) li.getItemAtPosition(position);
                String pasar=null;

                for(int k=0;k<rows.size();k++){
                    Row r2=(Row)rows.get(k);

                    if(r.getSubtitle().equals(r2.getSubtitle())){
                        pasar=r2.getId();
                    }
                }
                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención

                Intent intent = new Intent(getActivity(), ListViewAsig.class);
                Bundle b = new Bundle();
                b.putString("ID",pasar);  //id asig
                b.putString("USUARIO",usuario);
                intent.putExtras(b);

                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getAsignaturas() {
        nombresAsig.clear();
        siglasAsig.clear();
        rows.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getAsignaturas.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        Row fila = null;
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject row = jsonArray.getJSONObject(i);
                            fila = new Row();

                            fila.setTitle(row.getString("NOMBRE") + " ");
                            fila.setSubtitle(row.getString("SIGLAS") + " ");
                            fila.setId(row.getString("ID_ASIGNATURA"));

                            rows.add(fila);

                        }

                        li.setAdapter(new CustomArrayAdapterAsig(getContext(),rows));
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
