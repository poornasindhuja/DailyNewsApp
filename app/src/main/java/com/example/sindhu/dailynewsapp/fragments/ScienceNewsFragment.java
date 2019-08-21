package com.example.sindhu.dailynewsapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sindhu.dailynewsapp.adapters.NamesAdapter;
import com.example.sindhu.dailynewsapp.R;


public class ScienceNewsFragment extends Fragment {
    String[] science={"National Geographic","New Scientist","Next Big Future"};
    String[] sck={"national-geographic","new-scientist","next-big-future"};
    RecyclerView recyclerView;
    public ScienceNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_science_news, container, false);
        recyclerView=view.findViewById(R.id.s_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NamesAdapter(getContext(),science,sck));
        return view;
    }

}
