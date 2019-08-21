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


public class TechnicalNewsFragment extends Fragment {
    String[] tech={"Ars Technica","Crypto coins news","Engadget","Gruenderszene",
            "Hacker News","recode","T3n","TechChrunch","TechRador","The Next Web",
    "The Verge","Wired","Wired.de"};
    String[] tk={"ars-technica","crypto-coins-news","engadget","gruenderszene",
            "hacker-new","recode","t3n","techchrunch","techradar","the-next-web"
    ,"the-verge","wired","wired-de"};
    RecyclerView recyclerView;

    public TechnicalNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_technical_news, container, false);
        recyclerView=view.findViewById(R.id.t_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NamesAdapter(getContext(),tech,tk));
        return view;
    }

}
