package com.google.firebase.iid;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final /* synthetic */ class ag implements OnCompleteListener {
    /* renamed from: a */
    private final FirebaseInstanceId f5687a;
    /* renamed from: b */
    private final String f5688b;
    /* renamed from: c */
    private final String f5689c;
    /* renamed from: d */
    private final TaskCompletionSource f5690d;

    ag(FirebaseInstanceId firebaseInstanceId, String str, String str2, TaskCompletionSource taskCompletionSource) {
        this.f5687a = firebaseInstanceId;
        this.f5688b = str;
        this.f5689c = str2;
        this.f5690d = taskCompletionSource;
    }

    public final void onComplete(Task task) {
        this.f5687a.m8770a(this.f5688b, this.f5689c, this.f5690d, task);
    }
}
