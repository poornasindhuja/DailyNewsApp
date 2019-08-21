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

public class SportsNewsFragment extends Fragment {
    String[] sport={"BBC Sports","Bleacher Report","ESPN","FootBall Italia","FourFour Two",
            "Fox Sports","NHL News","NFL News","The Sport Bible"};
    String[] sk={"bbc-sport","bleacher-report","espn","football-italia","four-four-two",
            "fox-sports","nhl-news","nfl-news","the-sport-bible"};
    RecyclerView recyclerView;

    public SportsNewsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sports_news, container, false);
        recyclerView=view.findViewById(R.id.sp_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NamesAdapter(getContext(),sport,sk));
        return view;
    }

}
