package com.example.sindhu.dailynewsapp;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sindhu.dailynewsapp.adapters.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class DetailsActivity extends AppCompatActivity {
    String nurl1 = "https://newsapi.org/v2/everything?sources=";
    String nurl_end = "&pagesize=100&apiKey=d5c4f8cf60b84d099fa7e72d96ab9edb";
    RecyclerView rv;
    NewsViewModel viewModel;
    List<NewsModel> models;
    //Button date_select;
    //TextView selected;
    Boolean removeDPicker=false;
    LinearLayout linearLayout, progressBar;
    String nname;
    private String fav = "fav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        rv = findViewById(R.id.details_recyclerview);
        progressBar = findViewById(R.id.progress);
        models = new ArrayList<>();
        /*date_select=findViewById(R.id.select_date_button);
        selected=findViewById(R.id.selected_date);
        */viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        linearLayout = findViewById(R.id.layout1);
        nname = getIntent().getStringExtra(getResources().getString(R.string.detail_intent));
        initialSetup();
       /* date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                calender.setTimeInMillis(System.currentTimeMillis());
                DatePickerDialog mDialog = new DatePickerDialog(DetailsActivity.this,
                        mDatesetListener, calender.get(Calendar.YEAR),
                        calender.get(Calendar.MONTH), calender
                        .get(Calendar.DAY_OF_MONTH));
                mDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                calender.add(Calendar.MONTH,-1);
                mDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
                mDialog.show();
            }
        });*/
    }

    public void initialSetup() {
        if (nname.equals(fav)) {
            removeDPicker=true;
            showFavourites();
        } else {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                new NewsDisplayTask().execute(nurl1
                        + nname + nurl_end);
            } else {
                Snackbar snackbar = Snackbar.make(linearLayout, R.string.no_internet
                        , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        setTitle(nname);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new NewsAdapter(this, models));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(removeDPicker){

        }else {
            getMenuInflater().inflate(R.menu.details_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.news_date_selctor){
            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(System.currentTimeMillis());
            DatePickerDialog mDialog = new DatePickerDialog(DetailsActivity.this,
                    mDatesetListener, calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH), calender
                    .get(Calendar.DAY_OF_MONTH));
            mDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            calender.add(Calendar.MONTH,-1);
            mDialog.getDatePicker().setMinDate(calender.getTimeInMillis());
            mDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private DatePickerDialog.OnDateSetListener mDatesetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String s=year+"-"+(month+1)+"-"+dayOfMonth;
            //selected.setText(s);
            
            models.clear();
           new NewsDisplayTask().execute(nurl1+nname+"&from="+s+"&to="+s+nurl_end);
        }
    };

    public void showFavourites() {
        viewModel.getAllFavArticle().observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsModel> newsModels) {
                if (newsModels.size() == 0) {
                    progressBar.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar.make(linearLayout,
                            R.string.no_favorite, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setAdapter(new NewsAdapter(DetailsActivity.this, newsModels));
                }
            }
        });

    }

    public class NewsDisplayTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                Log.i("data", String.valueOf(url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner s = new Scanner(inputStream);
                s.useDelimiter("\\A");
                if (s.hasNext()) {
                    return s.next();
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if(s!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray articles = jsonObject.getJSONArray(getString(R.string.article));
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject ob = articles.getJSONObject(i);
                        String title = ob.getString(getString(R.string.title));
                        String img = ob.getString(getString(R.string.image));
                        String desc = ob.getString(getString(R.string.desc));
                        String pub = ob.getString(getString(R.string.published));
                        models.add(new NewsModel(pub, title, img, desc));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(DetailsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
