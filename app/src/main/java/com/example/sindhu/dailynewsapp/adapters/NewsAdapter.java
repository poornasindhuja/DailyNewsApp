package com.example.sindhu.dailynewsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sindhu.dailynewsapp.DetailsActivity;
import com.example.sindhu.dailynewsapp.FinalActivity;
import com.example.sindhu.dailynewsapp.NewsModel;
import com.example.sindhu.dailynewsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyInnerClass> {
    Context context;
    List<NewsModel> newsModels;

    public NewsAdapter(DetailsActivity detailsActivity, List<NewsModel> models) {
        this.context = detailsActivity;
        this.newsModels = models;
    }

    @Override
    public NewsAdapter.MyInnerClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_eachrow,parent,false);
        return new  MyInnerClass(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyInnerClass holder, int position) {
        final NewsModel model=newsModels.get(position);
        holder.pub.setText(model.getTime());
        holder.ntitle.setText(model.getTitle());
        Picasso.with(context).load(model.getImage()).
                placeholder(R.mipmap.loading).into(holder.npic);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] s=new String[5];
                s[0]=model.getTitle();
                s[1]=model.getImage();
                s[2]=model.getDescription();
                s[3]=model.getTime();
                Intent intent=new Intent(context,FinalActivity.class);
                intent.putExtra(context.getResources().getString(R.string.final_intent_key),s);
                context.startActivity(intent);
               }
        });
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class MyInnerClass extends RecyclerView.ViewHolder {
        ImageView npic;
        TextView pub,ntitle;
        CardView cv;
        public MyInnerClass(View itemView) {
            super(itemView);
            npic=itemView.findViewById(R.id.news_img);
            pub=itemView.findViewById(R.id.published);
            ntitle=itemView.findViewById(R.id.news_title);
            cv=itemView.findViewById(R.id.card);
        }
    }
}
