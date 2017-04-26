package com.tutorius.tutorius;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.*;
import com.github.snowdream.android.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BufferedHeader;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Departamento extends Fragment {

    ListView li;
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

        return rootView;


    }

    private void getDepartamentos() {
        nombresDept.clear();
        siglasDept.clear();


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
