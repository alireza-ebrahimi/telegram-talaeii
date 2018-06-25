package com.persianswitch.sdk.base.webservice;

import android.os.AsyncTask;

public class AsyncTaskUtil {
    /* renamed from: a */
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> m10813a(AsyncTask<Params, Progress, Result> asyncTask, Params... paramsArr) {
        if (asyncTask == null) {
            throw new IllegalArgumentException("task can not be null");
        }
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramsArr);
        return asyncTask;
    }
}
