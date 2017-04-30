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
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class Departamento extends Fragment implements View.OnClickListener{

    ListView li;
    Button btnDept;
    EditText bsqDept;
    ArrayList nombresDept = new ArrayList();
    ArrayList siglasDept = new ArrayList();
    ArrayList rows;

    View rootView;
    String usuario;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.departamento, container, false);
        li= (ListView)rootView.findViewById(R.id.listViewDepartamento); //buscas en el XML el id de dicho elemento listView
        btnDept = (Button)rootView.findViewById(R.id.btnbusqdepar);
        bsqDept=(EditText)rootView.findViewById(R.id.busquedadepar);
        rows = new ArrayList<Row>();

        mContext = getActivity().getApplicationContext();
        mActivity = getActivity();
        mCLayout = (TextView) rootView.findViewById(R.id.errores);


        Bundle b = getActivity().getIntent().getExtras();
        usuario = b.getString("UVUS");

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        getDepartamentos();     //carga el listView


        btnDept.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {       //busquedas en el listView
                ArrayList<Row> rows2=new ArrayList<Row>();
                for(int i=0;i<rows.size();i++) {

                    Row r = (Row) rows.get(i);

                    if ( r.getTitle().toLowerCase().contains(bsqDept.getText()) ||
                            r.getSubtitle().toLowerCase().contains(bsqDept.getText())  ||
                            r.getTitle().contains(bsqDept.getText()) ||
                            r.getSubtitle().contains(bsqDept.getText()) ) {
                        rows2.add(r);
                        li.setAdapter(new CustomArrayAdapterDept(getContext(), rows2));
                    }
                }
            }
        });

        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android

                //obtengo el valor del string del elemento donde se hizo clic
                Row r= (Row) li.getItemAtPosition(position);
                String pasar=null;

                for(int k=0;k<rows.size();k++){
                    Row r2=(Row)rows.get(k);

                    if(r.getSubtitle().contains(r2.getSubtitle())){
                        pasar=r2.getId();
                    }
                }
                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención

                Intent intent = new Intent(getActivity(), ListViewDept.class);
                Bundle b = new Bundle();
                b.putString("ID",pasar);  //siglas del departamento
                b.putString("USUARIO",usuario);
                intent.putExtras(b);

                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getDepartamentos() {
        nombresDept.clear();
        siglasDept.clear();
        rows.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getDeparts.php", new AsyncHttpResponseHandler() {
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
                            fila.setId(row.getString("ID_DEPARTAMENTO"));

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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),ListViewDept.class);
        Bundle b1 = new Bundle();
        b1.putString("UVUS",usuario);
        intent.putExtras(b1);
        startActivity(intent);
    }
}
