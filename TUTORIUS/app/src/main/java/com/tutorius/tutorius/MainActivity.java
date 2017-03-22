package com.tutorius.tutorius;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //conexiones php --------
    Button entrar;
    EditText usuario;
    EditText password;
    TextView errores;

    // IP de mi Url
    String IP = "http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
    String ASIGNATURAS="http://ec2-52-39-181-148.us-west-2.compute.amazonaws.com";
    // Rutas de los Web Services
    String AUTENTICACION = IP + "/autenticacion.php";
    String getASIGNATURAS=ASIGNATURAS+"getAsignaturas.php";

    WebService hiloconexion;

    // ------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar = (Button)findViewById(R.id.login);
        usuario = (EditText)findViewById(R.id.uvus);
        password = (EditText)findViewById(R.id.password);

        entrar.setOnClickListener(this);
        errores = (TextView)findViewById(R.id.errores);


    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:

                hiloconexion = new WebService();
                String cadenallamada = AUTENTICACION + "?uvus_alumno=" + usuario.getText().toString() + "&password=" + password.getText().toString();
                hiloconexion.execute(cadenallamada,"1");   // Parámetros que recibe doInBackground

                break;
            default:

                break;
        }
    }

    public class WebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cad = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";



            if(params[1]=="1"){    // consulta por id

                try {
                    url = new URL(cad);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión

                    //connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    //        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");
                    connection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1)");
                    int res = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (res == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON=="1"){      // hay un alumno por lo tanto el login es correcto.
                            //PASAMOS A LA HOJA DE ALUMNOS
                            //Intent intent = new Intent(MainActivity.this,Alumnos.class);
                            //startActivity(intent);
                            devuelve = " ";
                            Intent intent = new Intent(MainActivity.this,Alumnos.class);
                            startActivity(intent);

                              //      DEBERIAMOS PROBAR TAMBIEN EN LA TABLA DE PROFESORES PARA SABER SI ES UN PROFESOR Y ENTONCES PASARLO A LA PAGINA DE PROFESORES

                        }
                        else if (resultJSON=="2"){
                            devuelve = " ";
                            Intent intent = new Intent(MainActivity.this,Profesor.class);
                            startActivity(intent);
                        }
                        else if (resultJSON=="3"){
                            devuelve = respuestaJSON.getString("mensaje");
                        }
                        else if (resultJSON=="4"){
                            devuelve = respuestaJSON.getString("mensaje");
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;


            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            errores.setText(s);
            //super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
