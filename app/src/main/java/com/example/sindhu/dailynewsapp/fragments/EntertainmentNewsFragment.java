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

public class EntertainmentNewsFragment extends Fragment {
    String[] entertainment={"BuzzFeed","Daily Mail","Entertainment Weekly","Mashable"
            ,"MTV News","Polygon"};
    String[] ek={"buzzfeed","daily-mail","entertainment-weekly","mashable","mtv-news","polygon"};

    RecyclerView recyclerView;

    public EntertainmentNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_entertainment_news, container, false);
        recyclerView=view.findViewById(R.id.e_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NamesAdapter(getContext(),entertainment,ek));

        return view;
    }

}
