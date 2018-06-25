package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class aj implements Runnable {
    /* renamed from: a */
    private final ai f5696a;
    /* renamed from: b */
    private final Bundle f5697b;
    /* renamed from: c */
    private final TaskCompletionSource f5698c;

    aj(ai aiVar, Bundle bundle, TaskCompletionSource taskCompletionSource) {
        this.f5696a = aiVar;
        this.f5697b = bundle;
        this.f5698c = taskCompletionSource;
    }

    public final void run() {
        this.f5696a.m8804a(this.f5697b, this.f5698c);
    }
}
