package com.google.firebase.iid;

import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class ae implements Runnable {
    /* renamed from: a */
    private final FirebaseInstanceId f5678a;
    /* renamed from: b */
    private final String f5679b;
    /* renamed from: c */
    private final String f5680c;
    /* renamed from: d */
    private final TaskCompletionSource f5681d;
    /* renamed from: e */
    private final String f5682e;

    ae(FirebaseInstanceId firebaseInstanceId, String str, String str2, TaskCompletionSource taskCompletionSource, String str3) {
        this.f5678a = firebaseInstanceId;
        this.f5679b = str;
        this.f5680c = str2;
        this.f5681d = taskCompletionSource;
        this.f5682e = str3;
    }

    public final void run() {
        this.f5678a.m8771a(this.f5679b, this.f5680c, this.f5681d, this.f5682e);
    }
}
