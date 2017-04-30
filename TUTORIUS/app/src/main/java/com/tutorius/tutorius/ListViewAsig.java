package com.tutorius.tutorius;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListViewAsig extends AppCompatActivity {

    ListView li;
    ArrayList nombresProf = new ArrayList();
    ArrayList deptProf = new ArrayList();
    ArrayList idProf =new ArrayList();
    ArrayList dispProf=new ArrayList();
    ArrayList uvusProf=new ArrayList(); //array de uvus de profesores
    Button btnProf;
    EditText bsqProf;
    String posicionAsig;    //ID de la asignatura seleccionada
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profesores);

        //cabecera
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fin cabecera

        //listView Vistas--
        li = (ListView) findViewById(R.id.listViewProf);
        btnProf=(Button)findViewById(R.id.btnbusqprof);
        bsqProf=(EditText)findViewById(R.id.busquedaprof);
        Bundle b = this.getIntent().getExtras();
        posicionAsig= b.getString("ID");    //valor de las siglas seleccionadas de la asignatura
        usuario=b.getString("USUARIO");

        getAsigImpartidas();
        getProf();

        btnProf.setOnClickListener(new View.OnClickListener() { //botón para buscar valor
            public void onClick(View arg0)
            {
                String busqueda= String.valueOf(bsqProf.getText());
                String[] nombreCompleto;
                String nombreP;
                String ap1;
                String ap2;

                if(busqueda.isEmpty()){
                    getAsigImpartidas();
                    getProf();
                }

                for(int i=0;i<nombresProf.size();i++){
                    String cadena= (String) nombresProf.get(i);
                    String cadena2=(String)deptProf.get(i);
                    nombreCompleto=cadena.split(" ");
                    nombreP=nombreCompleto[0];
                    ap1=nombreCompleto[1];
                    ap2=nombreCompleto[2];

                    if(nombreP.toLowerCase().contains(busqueda) || nombreP.contains(busqueda) ||
                            ap1.toLowerCase().contains(busqueda) || ap1.contains(busqueda) ||
                            ap2.toLowerCase().contains(busqueda) || ap2.contains(busqueda) ||
                            cadena2.contains(busqueda) || cadena2.toLowerCase().contains(busqueda)){
                        nombresProf.clear();
                        deptProf.clear();
                        nombresProf.add(cadena);
                        deptProf.add(cadena2);
                        li.setAdapter(new ListViewAsig.ImagenAdapter(getApplicationContext()));
                    }
                }
            }
        });


        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //listView clic
                Integer x= (Integer) li.getItemAtPosition(position);
                String pasar=null;

                for(int i=0;i<nombresProf.size();i++){
                    if(x==i){
                        pasar=idProf.get(i).toString();
                    }
                }
                Intent intent = new Intent(getApplicationContext(), AlumnoPideCitaProfesor.class);
                Bundle b = new Bundle();
                //b.putString("CITA",itemValue);
                b.putString("UVUS",usuario);    //UVUS DEL ALUMNO
                b.putString("ID",pasar);   //UVUS DEL PROFESOR
                intent.putExtras(b);

                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
            }
        });

    }

    private void getProf() {    //Consulta todos los profesores con el filtro
        nombresProf.clear();
        deptProf.clear();
        idProf.clear();
        dispProf.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getAllProfesores.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String x=jsonArray.getJSONObject(i).getString("UVUS_PROFESOR");

                            for(int j=0;j<uvusProf.size();j++){
                                if(x.equals(uvusProf.get(j).toString())){
                                    nombresProf.add(jsonArray.getJSONObject(i).getString("NOMBRE")+" "+
                                            jsonArray.getJSONObject(i).getString("APELLIDO1")+ " "+
                                            jsonArray.getJSONObject(i).getString("APELLIDO2"));
                                    deptProf.add(jsonArray.getJSONObject(i).getString("DESPACHO"));

                                    //identificador del profesor
                                    idProf.add(jsonArray.getJSONObject(i).getString("UVUS_PROFESOR"));

                                    //disponibilidad de profesor
                                    dispProf.add(jsonArray.getJSONObject(i).getString("DISPONIBILIDAD"));
                                }
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

    private void getAsigImpartidas() {    //Consulta las asignaturas impartidas por los profesores
        uvusProf.clear();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com/getAsigImpartidas.php", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String x=jsonArray.getJSONObject(i).getString("ID_ASIGNATURA");

                            if(x.equals(posicionAsig))
                                uvusProf.add(jsonArray.getJSONObject(i).getString("UVUS_PROFESOR"));
                        }

                       // li.setAdapter(new ImagenAdapter(getApplicationContext()));
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
        ImageView imageView;
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

            imageView=(ImageView)viewGroup.findViewById(R.id.imageDisponibilidad);

            //String urlFinal=""+imagen.get(position).toString;
            //Rect rect=new Rect(smartImagenView.getLeft(),smartImagenView.getTop(),smartImagenView.getRight(),smartImagenView.getBottom())
            //smartImagenView.setImageUrl(urlFinal,rect);

            nombreProf.setText(nombresProf.get(position).toString());
            depttProf.setText(deptProf.get(position).toString());

            if(dispProf.get(position).toString().contains("0"))
                imageView.setImageResource(android.R.drawable.presence_busy);
            else{
                imageView.setImageResource(android.R.drawable.presence_online);
            }

            return viewGroup;
        }
    }


    //metodos para el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        Bundle b = new Bundle();

        switch(id) {

            case R.id.action_settings_back:

                Intent intent = new Intent(ListViewAsig.this, Alumnos.class);

                b.putString("UVUS",usuario);
                intent.putExtras(b);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:

                Intent intent2 = new Intent(ListViewAsig.this, Principal_alumno.class);

                b.putString("UVUS",usuario);
                intent2.putExtras(b);
                startActivity(intent2);
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(ListViewAsig.this, MainActivity.class);
                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //
}