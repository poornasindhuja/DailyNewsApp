package com.example.sindhu.dailynewsapp;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class NewsRepositary {
    public static MyDao myDao;

    LiveData<List<NewsModel>> articles;

    public NewsRepositary(Context context){
        NewsDatabase favoriteDatabase=NewsDatabase.getInstance(context);
        myDao=favoriteDatabase.myDao();
        articles=myDao.getAllNewsArticles();
    }
    LiveData<List<NewsModel>> getAllArticles(){
        return articles;
    }

    public String getArticleCt(String pubTime) {
        return myDao.checkArticle(pubTime);
    }
    public void insertArticle(NewsModel model){
        new InsertTask().execute(model);
    }

    public class InsertTask extends AsyncTask<NewsModel,Void,Void> {
        @Override
        protected Void doInBackground(NewsModel... newsModels) {
            myDao.insertArticle(newsModels[0]);
            return null;
        }
    }

    public void DeleteArticle(NewsModel model){
        new DeleteTask().execute(model);
    }

    public class DeleteTask extends AsyncTask<NewsModel,Void,Void>{
        @Override
        protected Void doInBackground(NewsModel... newsModels) {
            myDao.deleteArticle(newsModels[0]);
            return null;
        }
    }

}
