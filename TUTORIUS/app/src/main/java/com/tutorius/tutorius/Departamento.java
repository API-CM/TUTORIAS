package com.tutorius.tutorius;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Departamento extends AppCompatActivity {


    ListView li;
    ArrayList nombresDept = new ArrayList();
    ArrayList siglasDept = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamento);

        //listView Vistas--
        li = (ListView) findViewById(R.id.listViewDepartamento);
        getDepartamentos();


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

                        for (int i = 0; i < jsonArray.length(); i++) {
                            nombresDept.add(jsonArray.getJSONObject(i).getString("NOMBRE"));
                            siglasDept.add(jsonArray.getJSONObject(i).getString("SIGLAS"));
                        }

                        li.setAdapter(new ImagenAdapter(getApplicationContext()));
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

    private class ImagenAdapter extends BaseAdapter {
        Context contexto;
        LayoutInflater layoutInflater;
        SmartImageView smartImagenView;
        TextView nombreDept, siglaDept;

        public ImagenAdapter(Context applicationContext) {
            this.contexto = applicationContext;
            layoutInflater = (LayoutInflater) contexto.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nombresDept.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.departamento_item,null);
            smartImagenView=(SmartImageView)viewGroup.findViewById(R.id.imagen2);
            nombreDept=(TextView)viewGroup.findViewById(R.id.nombreDept);
            siglaDept=(TextView)viewGroup.findViewById(R.id.siglaDept);

            //String urlFinal=""+imagen.get(position).toString;
            //Rect rect=new Rect(smartImagenView.getLeft(),smartImagenView.getTop(),smartImagenView.getRight(),smartImagenView.getBottom())
            //smartImagenView.setImageUrl(urlFinal,rect);

            nombreDept.setText(nombresDept.get(position).toString());
            siglaDept.setText(siglasDept.get(position).toString());

            return viewGroup;
        }
    }

}
