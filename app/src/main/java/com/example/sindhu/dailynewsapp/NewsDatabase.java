package com.example.sindhu.dailynewsapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = NewsModel.class,version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    private static String newsModel="NewsModel";

    public abstract MyDao myDao();

    public static NewsDatabase instance;

    public static NewsDatabase getInstance(Context context){

        if(instance==null){
            instance= Room.databaseBuilder(context, NewsDatabase.class,newsModel)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

}
