package com.example.rkjc.news_app_2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private String results;
    private Toolbar tbar;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private RecyclerView news_recyclerview;
    private NewsRecyclerViewAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbar = findViewById(R.id.tbar);
        news_recyclerview = findViewById(R.id.news_recyclerview);
        newsAdapter = new NewsRecyclerViewAdapter(this, news);
        news_recyclerview.setAdapter(newsAdapter);
        news_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(tbar);
        NewsTask task = new NewsTask();
        task.execute();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    class NewsTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids){
            try{
                results = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_search:
                fillWithNews(results);
        }
        return super.onOptionsItemSelected(item);
    }

    public void fillWithNews(String newsJson){
        news = JsonUtils.parseNews(newsJson);
        newsAdapter.newsList = news;
        newsAdapter.notifyDataSetChanged();
    }
}
