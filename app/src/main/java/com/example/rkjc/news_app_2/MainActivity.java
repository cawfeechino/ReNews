package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String results;
    private Toolbar tbar;
    private ArrayList<NewsItem> news = new ArrayList<>();
    private RecyclerView news_recyclerview;
    private NewsRecyclerViewAdapter newsAdapter;
    private NewsItemViewModel newsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbar = findViewById(R.id.tbar);
        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);

        news_recyclerview = findViewById(R.id.news_recyclerview);
        newsAdapter = new NewsRecyclerViewAdapter(this, newsItemViewModel);
        news_recyclerview.setAdapter(newsAdapter);
        news_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        newsItemViewModel.getAllNewsItem().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                newsAdapter.setNewsItems(newsItems);
            }
        });
        setSupportActionBar(tbar);
    }

    @Override
    protected void onStart(){
        super.onStart();
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
                newsItemViewModel.update();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fillWithNews(String newsJson){
        news = JsonUtils.parseNews(newsJson);
        newsAdapter.newsList = news;
        newsAdapter.notifyDataSetChanged();
    }
}
