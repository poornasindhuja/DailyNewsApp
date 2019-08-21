package com.example.sindhu.dailynewsapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;


public class NewAppWidget extends AppWidgetProvider {

    private static String def_value="News not watched";
    private static String key="news";
    private static String fav="fav";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences preferences;
        Intent intent=new Intent(context,DetailsActivity.class);
        intent.putExtra(context.getString(R.string.detail_intent),fav);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,
                2,intent,0);
        preferences=context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        String s=preferences.getString(key,def_value);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text,s);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}


