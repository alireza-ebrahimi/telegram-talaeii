package com.onesignal;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.RequiresApi;

@RequiresApi(api = 21)
public class SyncJobService extends JobService {
    public boolean onStartJob(JobParameters jobParameters) {
        OneSignalSyncServiceUtils.doBackgroundSync(this, new LollipopSyncRunnable(this, jobParameters));
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return OneSignalSyncServiceUtils.stopSyncBgThread();
    }
}
