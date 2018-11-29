package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    private String results;
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItem;

    public Repository(Application application){
        NewsItemRoomDatabase db = NewsItemRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItem = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> loadAllNewsItem(){
        return mAllNewsItem;
    }

    public void insert(List<NewsItem> newsItem){
        new insertAsyncTask(mNewsItemDao).execute(newsItem);
    }

    public void delete(){
        new deleteAsyncTask(mNewsItemDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<NewsItem>... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<List<NewsItem>, Void, Void>{
        private NewsItemDao mAsyncTaskDao;
        deleteAsyncTask(NewsItemDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<NewsItem>... params){
            mAsyncTaskDao.clearAll();
            return null;
        }
    }

    public void performSearch(){
        NewsTask task = new NewsTask();
        task.execute();
    }

    public class NewsTask extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            delete();
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                results = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());

            } catch (Exception e){
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String results){
            super.onPostExecute(results);
            List<NewsItem> news = JsonUtils.parseNews(results);
            insert(news);
        }
    }
}
