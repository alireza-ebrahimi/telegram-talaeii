package net.hockeyapp.android.p137e;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;

/* renamed from: net.hockeyapp.android.e.a */
public class C2393a {
    /* renamed from: a */
    private static Executor f8069a;

    @SuppressLint({"InlinedApi"})
    /* renamed from: a */
    public static void m11833a(AsyncTask<Void, ?, ?> asyncTask) {
        if (VERSION.SDK_INT <= 12) {
            asyncTask.execute(new Void[0]);
        } else {
            asyncTask.executeOnExecutor(f8069a != null ? f8069a : AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }
}
