package com.example.nikola.callblockingtestdemo.FragmentsBlock;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.example.nikola.callblockingtestdemo.ObjectSerializer;
import com.example.nikola.callblockingtestdemo.R;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public static ArrayList<String> people=new ArrayList<String>();
    FloatingActionButton floatingActionButton;
    EditText input;

    ListView listView;
    Switch startstop;
    private SharedPreferences preferencesswitch;
    public static final String ex="Switch";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;



    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startstop=(Switch)view.findViewById(R.id.switch1);


        floatingActionButton=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        input=(EditText)view.findViewById(R.id.editText);
        listView = (ListView) view.findViewById(R.id.listabrojeva);
        input=(EditText)view.findViewById(R.id.editText);

        preferencesswitch=getActivity().getSharedPreferences(" ",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=preferencesswitch.edit();
        startstop.setChecked(preferencesswitch.getBoolean(ex,false));
        startstop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editor.putBoolean(ex,true);
                }
                else {
                    editor.putBoolean(ex,false);
                }
                editor.commit();
            }
        });






        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("blocked",getActivity().MODE_PRIVATE);
        try {
            people = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("numbers",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        startstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (startstop.isChecked()){
                getContext().startService(new Intent(getActivity(),Myservice.class));

              }else {

                  getContext().stopService(new Intent(getActivity(),Myservice.class));

              }

            }
        });
        listView = (ListView) view.findViewById(R.id.listabrojeva);

        final  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, people);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=input.getText().toString();
                if (people.contains(number)){
                    input.setError("Number allready added");
                    input.requestFocus();
                    return;
                }
                if (number.isEmpty()){
                    input.setError("Please enter a valid number");
                    input.requestFocus();
                    return;
                }

                else{
                    people.add(number);
                    try {

                        sharedPreferences.edit().putString("numbers",ObjectSerializer.serialize(people)).apply();
                        adapter.notifyDataSetChanged();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }





            }}
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                people.remove(position);
                try {
                    sharedPreferences.edit().putString("numbers",ObjectSerializer.serialize(people)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();


                return false;
            }
        });




    }


    }





