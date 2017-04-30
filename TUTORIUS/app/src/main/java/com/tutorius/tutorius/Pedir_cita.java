package com.tutorius.tutorius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import static com.tutorius.tutorius.R.id.delete;

public class Pedir_cita extends AppCompatActivity {

    String usuario;
    String profesor;

    private Context mContext;
    private Activity mActivity;
    private TextView mCLayout;

    Integer numcitas = 0;

    ListView listadias;
    String[] listaa;
    String[] listaa2;
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";

    Integer sum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_cita);

        Bundle b = this.getIntent().getExtras();
        profesor = b.getString("UVUS_PROFESOR");
        usuario=b.getString("UVUS");



        mContext = getApplicationContext();
        mActivity = Pedir_cita.this;
        mCLayout = (TextView) findViewById(R.id.errores);

        String cadenallamada =  IP + "/getDiasCitasProf.php?uvus_profesor=" + profesor;

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
                            JSONArray array = response.getJSONArray("dias");
                            // JSONArray array2 = response.getJSONArray("despacho");

                            listaa = new String[array.length()];
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject dias = array.getJSONObject(i);
                                //JSONObject despa = array2.getJSONObject(i);

                                String dia = dias.getString("DIA_SEMANA");

                                //OBTIENE EL DIA DE LA SEMANA DE HOY
                                String hoy = obtenerDiaSemana();

                                //CALCULO DEL DÍA ACTUAL
                                Calendar cal = new GregorianCalendar();
                                Date date = cal.getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                String formatteDate = df.format(date);


                                    //VER EL DIA QUE ES EL DIA Y SI ES POSTERIOR AL DIA DE LA SEMANA QUE ES HOY AÑADIRLO CON LA FECHA

                                switch (hoy){
                                    case "Lunes":
                                        switch (dia){
                                            case "Lunes":
                                                listaa[i]=dia + " " + formatteDate;
                                                break;
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,1);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]=dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,2);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]=dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,3);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]=dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,4);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]=dia + " " + c3;
                                                break;
                                        }


                                        break;
                                    case "Martes":
                                        switch (dia){
                                            case "Martes":
                                                listaa[i]=dia + " " + formatteDate;
                                                break;
                                            case "Lunes":
                                                Date a = sumarDiasFecha(date,6);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,1);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]=dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,2);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]=dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,3);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]=dia + " " + c3;
                                                break;
                                        }

                                        break;
                                    case "Miércoles":
                                        switch (dia){
                                            case "Miércoles":
                                                listaa[i]=dia + " " + formatteDate;
                                                break;
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,6);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Lunes":
                                                Date a1 = sumarDiasFecha(date,5);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]="DEL " + dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,1);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]=dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,2);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]=dia + " " + c3;
                                                break;
                                        }

                                        break;
                                    case "Jueves":
                                        switch (dia){
                                            case "Jueves":
                                                listaa[i]=dia + " " + formatteDate;
                                                break;
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,5);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,6);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]="DEL " + dia + " " + c1;
                                                break;
                                            case "Lunes":
                                                Date a2 = sumarDiasFecha(date,4);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]="DEL " + dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,1);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]=dia + " " + c3;
                                                break;
                                        }

                                        break;
                                    case "Viernes":
                                        switch (dia){
                                            case "Viernes":
                                                listaa[i]=dia + " " + formatteDate;
                                                break;
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,4);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,5);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]="DEL " + dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,6);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]="DEL " + dia + " " + c2;
                                                break;
                                            case "Lunes":
                                                Date a3 = sumarDiasFecha(date,3);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]="DEL " + dia + " " + c3;
                                                break;
                                        }

                                        break;
                                    case "Sábado":
                                        switch (dia){
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,3);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,4);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]="DEL " + dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,5);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]="DEL " + dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,6);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]="DEL " + dia + " " + c3;
                                                break;
                                            case "Lunes":
                                                Date a4 = sumarDiasFecha(date,2);
                                                SimpleDateFormat b4 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c4 = b4.format(a4);
                                                listaa[i]="DEL " + dia + " " + c4;
                                                break;
                                        }

                                        break;
                                    case "Domingo":
                                        switch (dia){
                                            case "Martes":
                                                Date a = sumarDiasFecha(date,2);
                                                SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd");
                                                String c = b.format(a);
                                                listaa[i]="DEL " + dia + " " + c;
                                                break;
                                            case "Miércoles":
                                                Date a1 = sumarDiasFecha(date,3);
                                                SimpleDateFormat b1 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c1 = b1.format(a1);
                                                listaa[i]="DEL " + dia + " " + c1;
                                                break;
                                            case "Jueves":
                                                Date a2 = sumarDiasFecha(date,4);
                                                SimpleDateFormat b2 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c2 = b2.format(a2);
                                                listaa[i]="DEL " + dia + " " + c2;
                                                break;
                                            case "Viernes":
                                                Date a3 = sumarDiasFecha(date,5);
                                                SimpleDateFormat b3 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c3 = b3.format(a3);
                                                listaa[i]="DEL " + dia + " " + c3;
                                                break;
                                            case "Lunes":
                                                Date a4 = sumarDiasFecha(date,1);
                                                SimpleDateFormat b4 = new SimpleDateFormat("yyyy-MM-dd");
                                                String c4 = b4.format(a4);
                                                listaa[i]="DEL " + dia + " " + c4;
                                                break;
                                        }

                                        break;
                                    default:
                                        break;
                                }

                            }


                            switch(listaa.length){
                                case 0:
                                    listaa2 = new String[0];
                                    sum=0;
                                    break;
                                case 1:
                                    listaa2 = new String[2];
                                    sum=1;
                                    break;
                                case 2:
                                    listaa2 = new String[4];
                                    sum=2;
                                    break;
                                case 3:
                                    listaa2 = new String[6];
                                    sum=3;
                                    break;
                                case 4:
                                    listaa2 = new String[8];
                                    sum=4;
                                    break;
                                case 5:
                                    listaa2 = new String[10];
                                    sum=5;
                                    break;
                            }



                            for (int i=0;i<listaa.length;i++){
                                String[] a = listaa[i].split(" ");
                                if(a[0].equals("DEL")){
                                    String dia = a[1];
                                    String strFecha = a[2];
                                    listaa2[i] = "No disponible";
                                    listaa2[i+sum] = dia + " " + strFecha;

                                }else{
                                    String dia = a[0];
                                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                                    String strFecha = a[1];
                                    Date fecha = null;
                                    try {

                                        fecha = formatoDelTexto.parse(strFecha);

                                    } catch (ParseException ex) {

                                        ex.printStackTrace();

                                    }
                                    Date fech = fecha;
                                    Date fechnew = sumarDiasFecha(fech,7);
                                    String f = formatoDelTexto.format(fechnew);
                                    listaa2[i] = dia + " " + strFecha;
                                    listaa2[i+sum] = dia + " " + f;
                                }
                             }

                            //RECORRER LA LISTA ANTES DE ENVIARLA AL LISTVIEW PARA PONERLE LOS DIAS DE LA SEMANA SIGUIENTE

                            ponerenlistView(listaa2);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }


                },null
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);



    }

    private static String obtenerDiaSemana(){
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
        Date hoy=new Date();
        int numeroDia=0;
        Calendar cal=  Calendar.getInstance();
        cal.setTime(hoy);
        numeroDia=cal.get(Calendar.DAY_OF_WEEK);
        return dias[numeroDia - 1];
    }

    public Date sumarDiasFecha(Date fecha, int dias){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }

    public void ponerenlistView(String[] list){

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, list);

        listadias = (ListView)findViewById(R.id.dia_cita);

        listadias.setAdapter(adaptador);

        ///prueba
        listadias.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                int posicion = position;

                //obtengo el valor del string del elemento donde se hizo clic
                final String itemValue = (String) listadias.getItemAtPosition(position);

                if(!itemValue.equals("No disponible")){

                    //PONER Q EL ALUMNO NO PUEDA RESERVAR SI TIENE YA UNA CITA ESE DIA.
                    String[] a = itemValue.split(" ");
                    String fecha2 = a[1];
                    String cadenallamada2 = IP + "/getNumCitasAlum.php?uvus_profesor=" + profesor + "&fecha=" + fecha2 + "&uvus_alumno=" + usuario;

                    // Initialize a new RequestQueue instance
                    RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                    // Initialize a new JsonObjectRequest instance
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            cadenallamada2,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Do something with response
                                    //mTextView.setText(response.toString());

                                    // Process the JSON
                                    try {
                                        // Get the JSON array
                                        JSONArray array2 = response.getJSONArray("num_citas_alumno");


                                        for (int i = 0; i < array2.length(); i++) {

                                            JSONObject reser = array2.getJSONObject(i);
                                            Integer r = reser.getInt("COUNT(*)");
                                            numcitas = r;
                                        }

                                        if(numcitas == 0){
                                            Intent intent = new Intent(getApplicationContext(), Pedir_hora.class);
                                            Bundle b = new Bundle();
                                            b.putString("DIA_CITA",itemValue);
                                            b.putString("UVUS_PROFESOR",profesor);
                                            b.putString("USUARIO",usuario);
                                            intent.putExtras(b);
                                            startActivity(intent);
                                        }else{
                                            Toast toast1 =
                                                    Toast.makeText(getApplicationContext(),
                                                            "Ya tienes una cita ese día con ese profesor", Toast.LENGTH_SHORT);
                                            toast1.show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }, null
                    );

                    // Add JsonObjectRequest to the RequestQueue
                    requestQueue.add(jsonObjectRequest);





                }


            }
        });
    }
}
