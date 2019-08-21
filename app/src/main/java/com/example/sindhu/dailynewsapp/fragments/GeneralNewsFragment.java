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


public class GeneralNewsFragment extends Fragment {
    String[] gen={
            "The Times of India","The Hindu","ABC News","Google News","Google News(India)",
            "Independent","Infobae"};
    String[] gk={
            "the-times-of-india","the-hindu","abc-news","google-news","google-news-in",
            "independent","infobae"};
    RecyclerView recyclerView;
    public GeneralNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_daily_news, container, false);
        recyclerView=view.findViewById(R.id.g_recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NamesAdapter(getContext(),gen,gk));
        return view;
    }

}
