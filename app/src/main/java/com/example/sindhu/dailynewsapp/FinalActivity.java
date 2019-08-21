package com.example.sindhu.dailynewsapp;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class FinalActivity extends AppCompatActivity {

    AdView adView;
    TextView titl,desc,pub;
    ImageView img;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    NewsViewModel viewModel;
    String[] st;
    private static Boolean saved=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        titl=findViewById(R.id.f_title);
        desc=findViewById(R.id.f_description);
        pub=findViewById(R.id.f_publish);
        img=findViewById(R.id.f_img);
        adView=findViewById(R.id.adds);
        viewModel=ViewModelProviders.of(this).get(NewsViewModel.class);
        st=getIntent().getStringArrayExtra(getString(R.string.final_intent_key));
        titl.setText(st[0]);
        Picasso.with(this).load(st[1]).placeholder(R.mipmap.loading)
                .error(R.mipmap.error).into(img);
        desc.setText(st[2]);
        pub.setText(st[3]);
        if(viewModel.countArticles(st[3])==null) {
            saved=false;
            invalidateOptionsMenu();
        }else {
            saved=true;
            invalidateOptionsMenu();
        }
        sp=getSharedPreferences(getPackageName(),MODE_PRIVATE);
        editor=sp.edit();
        StringBuffer buffer=new StringBuffer();
        buffer.append(st[0]);
        editor.putString(getString(R.string.widget_key),buffer.toString());
        Intent i=new Intent(this,NewAppWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        editor.apply();
        int[] ids= AppWidgetManager.getInstance(this)
                .getAppWidgetIds(new ComponentName(getApplication(),NewAppWidget.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(i);
        MobileAds.initialize(this);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void insertIntoWidget(){
        sp=getSharedPreferences(getPackageName(),MODE_PRIVATE);
        editor=sp.edit();
        StringBuffer buffer=new StringBuffer();
        buffer.append(st[0]);
        editor.putString(getString(R.string.widget_key),buffer.toString());
        Intent i=new Intent(this,NewAppWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        editor.apply();
        int[] ids= AppWidgetManager.getInstance(this)
                .getAppWidgetIds(new ComponentName(getApplication(),NewAppWidget.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(i);
        MobileAds.initialize(this);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.final_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_news){
            NewsModel model = new NewsModel(st[3],st[0],st[1],st[2]);
            if(viewModel.countArticles(st[3])==null) {
                saved=true;
                viewModel.insertFArticle(model);
                insertIntoWidget();
                invalidateOptionsMenu();
            }else {
                viewModel.deleteFArticle(model);
                saved=false;
                invalidateOptionsMenu();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.getItem(0);
        if(saved){
            menuItem.setIcon(R.drawable.ic_bookmark_black_24dp);
        }else {
            menuItem.setIcon(R.drawable.ic_bookmark_border_black_24dp);
        }
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void share(View view) {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder
                .from(this)
                .setType(getString(R.string.dat))
                .setChooserTitle(titl.getText())
                .setText(desc.getText()+"\n\n"+pub.getText())
                .getIntent(), getString(R.string.share)));

    }
}
