package com.example.nikola.callblockingtestdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.nikola.callblockingtestdemo.FragmentsBlock.ListFragment;
import com.example.nikola.callblockingtestdemo.ScamFragmentPackeg.ScamFragment;


/**
 * Created by hrvoje on 11.3.2018..
 */

public class tabspager extends FragmentStatePagerAdapter {


    String [] titles = new String []{"Block","Scam"};

    public tabspager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ListFragment listFragment = new ListFragment();
                return listFragment;
            case 1:
                ScamFragment scamFragment=new ScamFragment();
                return scamFragment;


        }




        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
