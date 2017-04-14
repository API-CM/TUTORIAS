package com.tutorius.tutorius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Cabecera_profesor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabecera_profesor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
  //      tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        switch(id) {

            case R.id.action_settings_back:
                Intent intent = new Intent(Cabecera_profesor.this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings_home:
                Intent intent2 = new Intent(Cabecera_profesor.this, MainActivity.class);
                startActivity(intent2);
                return true;

            case R.id.action_settings_profile:
                Intent intent3 = new Intent(Cabecera_profesor.this, MainActivity.class);
                startActivity(intent3);
                return true;

            case R.id.action_settings_out:
                Intent intent4 = new Intent(Cabecera_profesor.this, MainActivity.class);
                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //
}
