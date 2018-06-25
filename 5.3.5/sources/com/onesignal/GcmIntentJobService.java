package com.onesignal;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.RequiresApi;

@RequiresApi(api = 22)
public class GcmIntentJobService extends OneSignalJobServiceBase {
    public /* bridge */ /* synthetic */ boolean onStartJob(JobParameters jobParameters) {
        return super.onStartJob(jobParameters);
    }

    public /* bridge */ /* synthetic */ boolean onStopJob(JobParameters jobParameters) {
        return super.onStopJob(jobParameters);
    }

    void startProcessing(JobService jobService, JobParameters jobParameters) {
        NotificationBundleProcessor.ProcessFromGCMIntentService(jobService, new BundleCompatPersistableBundle(jobParameters.getExtras()), null);
    }
}
