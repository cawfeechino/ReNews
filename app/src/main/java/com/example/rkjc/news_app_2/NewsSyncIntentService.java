package com.example.rkjc.news_app_2;

import android.app.IntentService;
import android.content.Intent;

public class NewsSyncIntentService extends IntentService {
    public NewsSyncIntentService(){super("NewsSyncIntentService");}

    @Override
    protected void onHandleIntent(Intent intent){
        String action = intent.getAction();
        UpdateService.executeTask(this, action);
    }
}
