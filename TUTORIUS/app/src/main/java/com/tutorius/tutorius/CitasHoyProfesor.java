package com.tutorius.tutorius;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CitasHoyProfesor extends Fragment
{
    ListView listView;
    List<String> names= new ArrayList<String>();
    List<Row> rows;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View rootView = inflater.inflate(R.layout.fragment_citas_profesor, container, false);
        listView= (ListView) rootView.findViewById(R.id.listView1);
        rows = new ArrayList<Row>(30);
        Row row = null;
        for (int i = 1; i < 31; i++) {
            row = new Row();
            row.setTitle("Title " + i);
            row.setSubtitle("Subtitle " + i);
            rows.add(row);
        }

        listView.setAdapter(new CustomArrayAdapter(getContext(), rows));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    } }