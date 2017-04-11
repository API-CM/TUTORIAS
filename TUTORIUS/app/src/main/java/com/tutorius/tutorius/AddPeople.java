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

public class AddPeople extends Fragment
{
    ListView listView;
    List<String> names= new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View rootView = inflater.inflate(R.layout.fragment_addpeople, container, false);
        listView= (ListView) rootView.findViewById(R.id.listView1);
        List<String> nameList = new ArrayList<String>();
        nameList.add("Prueba 1");
        nameList.add("Prueba 2");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 1");
        nameList.add("Prueba 2");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 1");
        nameList.add("Prueba 2");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 1");
        nameList.add("Prueba 2");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");
        nameList.add("Prueba 3");

        CustomAdapter adapter= new CustomAdapter(getActivity(), nameList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                String name = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(),
                        "Clicked on Row: " + name,
                        Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    } }