package com.tutorius.tutorius;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class Alumnos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPageAdapter());


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }




    class MainPageAdapter extends PagerAdapter {

        private LinearLayout page1;
        private LinearLayout page2;
        private LinearLayout page3;
        private final int[] titles = {R.string.page1, R.string.page2, R.string.page3};

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            View page;
            switch (position) {
                case 0:
                    if (page1 == null) {
                        page1 = (LinearLayout) LayoutInflater.from(Alumnos.this).inflate(R.layout
                                .departamento, collection, false);
                    }
                    page = page1;
                    break;
                case 1:
                    if (page2 == null) {
                        page2 = (LinearLayout) LayoutInflater.from(Alumnos.this).inflate(R.layout
                                .asignatura, collection, false);
                    }
                    page = page2;
                    break;
                default:
                    if (page3 == null) {
                        page3 = (LinearLayout) LayoutInflater.from(Alumnos.this).inflate(R.layout
                                .profesores, collection, false);
                    }
                    page = page3;
                    break;
            }

            collection.addView(page, 0);

            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }


    }

}

