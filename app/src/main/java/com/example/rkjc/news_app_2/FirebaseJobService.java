package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import com.firebase.jobdispatcher.JobService;

public class FirebaseJobService extends JobService{
    private AsyncTask mBackgroundTask;
    Repository mRepository;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = FirebaseJobService.this;
                UpdateService.executeTask(context, UpdateService.SYNC_NEWS);
                mRepository = new Repository(getApplication());
                mRepository.performSearch();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false );
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
