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

public class Departamento extends Fragment {

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

       // super.onCreate(savedInstanceState);

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

        //String cadenallamada = getAlumno + "?uvus_profesor=" + usuario;

        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

       // setContentView(R.layout.departamento);

        getDepartamentos();

        btnDept.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {
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
                            // Get current json object
                            JSONObject row = jsonArray.getJSONObject(i);
                            fila = new Row();
                            fila.setTitle(row.getString("NOMBRE") + " ");
                            fila.setSubtitle(row.getString("SIGLAS") + " ");

                            // Display the formatted json data in text view

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
