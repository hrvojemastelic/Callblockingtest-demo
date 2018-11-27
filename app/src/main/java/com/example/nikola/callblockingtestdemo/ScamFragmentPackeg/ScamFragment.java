package com.example.nikola.callblockingtestdemo.ScamFragmentPackeg;


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
public class ScamFragment extends Fragment {


    public static ArrayList<String> scam=new ArrayList<String>();
    EditText input;
    FloatingActionButton floatingActionButton;
    ListView listView;
    Switch startstop;
    private SharedPreferences preferencesswitchh;
    public static final String exx="Switch2";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scam, container, false);
        return view;    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


floatingActionButton=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);
        startstop=(Switch)view.findViewById(R.id.switch1);



        input=(EditText)view.findViewById(R.id.editText);
        listView = (ListView) view.findViewById(R.id.listabrojeva);
        input=(EditText)view.findViewById(R.id.editText);



        preferencesswitchh=getActivity().getSharedPreferences("sw ",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editorr=preferencesswitchh.edit();


        startstop.setChecked(preferencesswitchh.getBoolean(exx,false));
        startstop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editorr.putBoolean(exx,true);
                }
                else {
                    editorr.putBoolean(exx,false);
                }
                editorr.commit();
            }
        });



            final SharedPreferences sharedPreferencesss = getContext().getSharedPreferences("scamm", getActivity().MODE_PRIVATE);
            try {
                scam = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferencesss.getString("snumbers", ObjectSerializer.serialize(new ArrayList<String>())));
            } catch (IOException e) {
                e.printStackTrace();
            }





        startstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startstop.isChecked()){
                    getContext().startService(new Intent(getActivity(),ScamService.class));

                }else {

                    getContext().stopService(new Intent(getActivity(),ScamService.class));

                }

            }
        });

        listView = (ListView) view.findViewById(R.id.listabrojeva);
        final ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, scam);
        adapterr.notifyDataSetChanged();
        listView.setAdapter(adapterr);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberr=input.getText().toString();
                if (scam.contains(numberr)){
                    input.setError("Number allready added");
                    input.requestFocus();
                    return;
                }
                if (numberr.isEmpty()){
                    input.setError("Please enter a valid number");
                    input.requestFocus();
                    return;
                }
                else{
                    scam.add(numberr);
                    try {

                        sharedPreferencesss.edit().putString("snumbers",ObjectSerializer.serialize(scam)).apply();
                            adapterr.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }





                }}
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                scam.remove(position);
                try {
                    sharedPreferencesss.edit().putString("snumbers",ObjectSerializer.serialize(scam)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapterr.notifyDataSetChanged();


                return false;
            }
        });



    }



}
