package android.support.v4.p014d;

import android.annotation.TargetApi;
import android.os.AsyncTask;

@TargetApi(11)
/* renamed from: android.support.v4.d.b */
class C0431b {
    /* renamed from: a */
    static <Params, Progress, Result> void m1911a(AsyncTask<Params, Progress, Result> asyncTask, Params... paramsArr) {
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paramsArr);
    }
}
