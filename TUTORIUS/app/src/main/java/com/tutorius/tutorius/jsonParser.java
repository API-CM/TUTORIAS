package com.tutorius.tutorius;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Antonio on 11/04/17.
 */

public class jsonParser {
    static JSONArray jsonArray;

    public JSONArray GetJSONfromUrl(String url){
        StringBuilder builder=new StringBuilder();
        HttpClient client=new DefaultHttpClient();
        HttpGet getData=new HttpGet(url);

        try{
            HttpResponse response=client.execute(getData);
            StatusLine statusLine=response.getStatusLine();
            int statusCode=statusLine.getStatusCode();

            if(statusCode==200){
                HttpEntity entity=response.getEntity();
                InputStream content=entity.getContent();
                BufferedReader reader=new BufferedReader(new InputStreamReader(content));
                String line;

                while( (line=reader.readLine()) != null){
                    builder.append(line);
                }
            }else{
                Log.e("====>","Failed to download file");
            }
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            jsonArray=new JSONArray(builder.toString());
        }catch (JSONException e){
            Log.e("JSON PARSER","Error traduciendo datos "+e.toString());
        }

        return jsonArray;
    }
}
