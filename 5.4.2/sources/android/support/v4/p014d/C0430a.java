package android.support.v4.p014d;

import android.os.AsyncTask;
import android.os.Build.VERSION;

/* renamed from: android.support.v4.d.a */
public final class C0430a {
    /* renamed from: a */
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> m1910a(AsyncTask<Params, Progress, Result> asyncTask, Params... paramsArr) {
        if (asyncTask == null) {
            throw new IllegalArgumentException("task can not be null");
        }
        if (VERSION.SDK_INT >= 11) {
            C0431b.m1911a(asyncTask, paramsArr);
        } else {
            asyncTask.execute(paramsArr);
        }
        return asyncTask;
    }
}
