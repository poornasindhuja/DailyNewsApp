package com.example.sindhu.dailynewsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sindhu.dailynewsapp.DetailsActivity;
import com.example.sindhu.dailynewsapp.R;

import java.util.Random;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.MyViewHolder> {
    Context context;
    String[] names;
    String[] values;

    public NamesAdapter(Context context, String[] names, String[] values) {
        this.context = context;
        this.names = names;
        this.values = values;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.namerow_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int randomAndroidColor = holder.androidColors[new Random().nextInt(holder.androidColors.length)];
        holder.pname.setBackgroundColor(randomAndroidColor);
        holder.pname.setText(names[position]);
            holder.pname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,DetailsActivity.class);
                    i.putExtra(context.getString(R.string.detail_intent),values[position]);
                    context.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pname;
        int androidColors[];
        public MyViewHolder(View itemView) {
            super(itemView);
            pname=itemView.findViewById(R.id.papername);
            androidColors=itemView.getResources().getIntArray(R.array.namescolors);
        }
    }
}
