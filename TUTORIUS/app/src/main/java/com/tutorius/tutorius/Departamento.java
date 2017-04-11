package com.tutorius.tutorius;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.*;
import com.github.snowdream.android.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Departamento extends AppCompatActivity {

    ListView li;
    ArrayList listaDept = new ArrayList();
    ArrayList siglaDept = new ArrayList();
    ArrayList imagen=new ArrayList();
    String DEPART = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getDeparts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamento);

        //listView Vistas--
        li = (ListView) findViewById(R.id.listViewDepartamento);
    }

    private void getDept(){
        listaDept.clear();
        imagen.clear();
        siglaDept.clear();

        final ProgressDialog progressDialog=new ProgressDialog(Departamento.this);
        progressDialog.setMessage("Cargando departamentos ...");
        progressDialog.show();

        AsyncHttpClient conection= new AsyncHttpClient();

        conection.get(DEPART, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(new String(responseBody));

                        for(int i=0;i<jsonArray.length();i++){
                            listaDept.add(jsonArray.getJSONObject(i).getString("NOMBRE") );
                         //   imagen.add(jsonArray.getJSONObject(i).getString("imagen") );
                            siglaDept.add(jsonArray.getJSONObject(i).getString("SIGLAS") );
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

    private class ImagenAdapter extends BaseAdapter{
        Context contexto;
        LayoutInflater layoutInflater;
        SmartImageView smartImagenView;
        TextView nombreDept, siglaDeptt;

        public ImagenAdapter(Context applicationContext) {
            this.contexto=applicationContext;
            layoutInflater=(LayoutInflater)contexto.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagen.size();
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
            nombreDept=(TextView)viewGroup.findViewById(R.id.nombresDept);
            siglaDeptt=(TextView)viewGroup.findViewById(R.id.siglasDept);

            //String urlFinal=""+imagen.get(position).toString;
            //Rect rect=new Rect(smartImagenView.getLeft(),smartImagenView.getTop(),smartImagenView.getRight(),smartImagenView.getBottom())
            //smartImagenView.setImageUrl(urlFinal,rect);

            nombreDept.setText(listaDept.get(position).toString());
            siglaDeptt.setText(siglaDept.get(position).toString());

            return viewGroup;
        }
    }
}
