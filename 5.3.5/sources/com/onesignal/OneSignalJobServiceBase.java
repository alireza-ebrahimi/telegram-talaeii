package com.onesignal;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.annotation.RequiresApi;

@RequiresApi(api = 21)
abstract class OneSignalJobServiceBase extends JobService {
    abstract void startProcessing(JobService jobService, JobParameters jobParameters);

    OneSignalJobServiceBase() {
    }

    public boolean onStartJob(JobParameters jobParameters) {
        if (jobParameters.getExtras() == null) {
            return false;
        }
        final JobService jobService = this;
        final JobParameters finalJobParameters = jobParameters;
        new Thread(new Runnable() {
            public void run() {
                OneSignalJobServiceBase.this.startProcessing(jobService, finalJobParameters);
                OneSignalJobServiceBase.this.jobFinished(finalJobParameters, false);
            }
        }, "OS_JOBSERVICE_BASE").start();
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
