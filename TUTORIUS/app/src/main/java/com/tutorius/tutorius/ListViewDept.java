package com.tutorius.tutorius;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListViewDept extends AppCompatActivity {

    ListView li;
    ArrayList nombresProf = new ArrayList();
    ArrayList deptProf = new ArrayList();
    Button btnProf;
    EditText bsqProf;
    String posicionDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profesores);

        //listView Vistas--
        li = (ListView) findViewById(R.id.listViewProf);
        btnProf=(Button)findViewById(R.id.btnbusqprof);
        bsqProf=(EditText)findViewById(R.id.busquedaprof);
        Bundle b = this.getIntent().getExtras();
        posicionDept= b.getString("ID");    //valor de las siglas seleccionadas

        getProf();

        btnProf.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {   //perfilar búsqueda NO ANDA
                ArrayList<Row> rows2=new ArrayList<Row>();
                for(int i=0;i<nombresProf.size();i++) {

                    if ( nombresProf.get(i).equals(bsqProf.getText())) {

                        li.setAdapter(new ImagenAdapter(getApplicationContext()));
                    }
                }
            }
        });
    }

    private void getProf() {
        nombresProf.clear();
        deptProf.clear();


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getAllProfesores.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String x=jsonArray.getJSONObject(i).getString("ID_DEPARTAMENTO");

                            if(x.contains(posicionDept)){

                                nombresProf.add(jsonArray.getJSONObject(i).getString("NOMBRE")+" "+
                                        jsonArray.getJSONObject(i).getString("APELLIDO1")+ " "+
                                        jsonArray.getJSONObject(i).getString("APELLIDO2"));
                                deptProf.add(jsonArray.getJSONObject(i).getString("DESPACHO"));
                            }

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
        TextView nombreProf, depttProf;

        public ImagenAdapter(Context applicationContext) {
            this.contexto = applicationContext;
            layoutInflater = (LayoutInflater) contexto.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nombresProf.size();
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

            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.profesor_item,null);
            //smartImagenView=(SmartImageView)viewGroup.findViewById(R.id.imagen2);
            nombreProf=(TextView)viewGroup.findViewById(R.id.nombreProf);
            depttProf=(TextView)viewGroup.findViewById(R.id.deptProf);

            //String urlFinal=""+imagen.get(position).toString;
            //Rect rect=new Rect(smartImagenView.getLeft(),smartImagenView.getTop(),smartImagenView.getRight(),smartImagenView.getBottom())
            //smartImagenView.setImageUrl(urlFinal,rect);

            nombreProf.setText(nombresProf.get(position).toString());
            depttProf.setText(deptProf.get(position).toString());

            return viewGroup;
        }
    }

}