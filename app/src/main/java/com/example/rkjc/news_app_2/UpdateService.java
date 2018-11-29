package com.example.rkjc.news_app_2;

import android.content.Context;

public class UpdateService {

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String SYNC_NEWS= "sync_news";

    public static void executeTask(Context context, String action){
        if(ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        } else if(SYNC_NEWS.equals(action)){
            UpdateService instance = new UpdateService();
            instance.update();
            NotificationUtils.newsPush(context);
        }

    }

    public void update(){
    }
}
