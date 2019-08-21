package com.example.sindhu.dailynewsapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(NewsModel newsModel);

    @Query("select * from NewsModel")
    LiveData<List<NewsModel>> getAllNewsArticles();

    @Query("select time from newsmodel where time= :time")
    String checkArticle(String time);

    @Delete
    void deleteArticle(NewsModel newsModel);
}
