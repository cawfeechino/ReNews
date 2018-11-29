package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {
    private Repository mRepository;

    private LiveData<List<NewsItem>> mAllNewsItem;

    public NewsItemViewModel(Application application){
        super(application);
        mRepository = new Repository(application);
        mAllNewsItem = mRepository.loadAllNewsItem();
    }

    public LiveData<List<NewsItem>> getAllNewsItem(){
        return mAllNewsItem;
    }

    public void update(){
        mRepository.performSearch();
    }
}
