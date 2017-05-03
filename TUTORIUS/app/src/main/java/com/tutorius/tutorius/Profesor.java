package com.tutorius.tutorius;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;

        import java.util.ArrayList;
        import java.util.List;

public class Profesor extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = this.getIntent().getExtras();
        usuario = b.getString("UVUS");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfesoresHoy(), "Hoy");
        adapter.addFragment(new ProfesoresManana(), "Mañana");
        adapter.addFragment(new ProfesoresPasMan(), "Pasado Mañana");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    //metodos para el menu
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
        Bundle b = new Bundle();

        switch(id) {

            case R.id.action_settings_back:

                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings_home:

                finish();
                startActivity(getIntent());
                return true;

            case R.id.action_settings_profile:

                Intent intent3 = new Intent(Profesor.this, ModificacionPerfilProfesor.class);

                b.putString("UVUS",usuario);
                intent3.putExtras(b);
                startActivity(intent3);
                return true;

            case R.id.action_settings_out:

                Intent intent4 = new Intent(Profesor.this, MainActivity.class);

                startActivity(intent4);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    //

}