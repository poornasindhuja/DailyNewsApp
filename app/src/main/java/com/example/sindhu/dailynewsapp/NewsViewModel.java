package com.example.sindhu.dailynewsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    public NewsRepositary newsRepositary;

    public LiveData<List<NewsModel>> favNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepositary=new NewsRepositary(application);
        favNews=newsRepositary.getAllArticles();
    }
    public void insertFArticle(NewsModel newsModel){
        newsRepositary.insertArticle(newsModel);
    }
    public void deleteFArticle(NewsModel newsModel){
        newsRepositary.DeleteArticle(newsModel);
    }

    LiveData<List<NewsModel>> getAllFavArticle(){
        return favNews;
    }


    public String countArticles(String pt){
        return newsRepositary.getArticleCt(pt);
    }


}
