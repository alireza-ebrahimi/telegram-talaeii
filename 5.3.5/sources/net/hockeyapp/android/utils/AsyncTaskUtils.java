package net.hockeyapp.android.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;

public class AsyncTaskUtils {
    private static Executor sCustomExecutor;

    @SuppressLint({"InlinedApi"})
    public static void execute(AsyncTask<Void, ?, ?> asyncTask) {
        if (VERSION.SDK_INT <= 12) {
            asyncTask.execute(new Void[0]);
        } else {
            asyncTask.executeOnExecutor(sCustomExecutor != null ? sCustomExecutor : AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    public static Executor getCustomExecutor() {
        return sCustomExecutor;
    }

    public static void setCustomExecutor(Executor customExecutor) {
        sCustomExecutor = customExecutor;
    }
}
