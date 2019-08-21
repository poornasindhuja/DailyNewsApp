package com.example.sindhu.dailynewsapp.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sindhu.dailynewsapp.fragments.GeneralNewsFragment;
import com.example.sindhu.dailynewsapp.fragments.EntertainmentNewsFragment;
import com.example.sindhu.dailynewsapp.fragments.ScienceNewsFragment;
import com.example.sindhu.dailynewsapp.fragments.SportsNewsFragment;
import com.example.sindhu.dailynewsapp.fragments.TechnicalNewsFragment;

public class TabAdapter extends FragmentPagerAdapter {
    private static String gen="General";
    private static String sports="Sports";
    private static String tech="Technical";
    private static String sci="Science";
    private static String entertainment="Entertainment";

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new GeneralNewsFragment();
            case 1:return new SportsNewsFragment();
            case 2:return new TechnicalNewsFragment();
            case 3:return new ScienceNewsFragment();
            case 4:return new EntertainmentNewsFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return gen;
            case 1:return sports;
            case 2:return tech;
            case 3:return sci;
            case 4:return entertainment;
        }
        return super.getPageTitle(position);
    }
}
