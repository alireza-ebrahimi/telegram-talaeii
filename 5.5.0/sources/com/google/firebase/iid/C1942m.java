package com.google.firebase.iid;

import android.util.Pair;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* renamed from: com.google.firebase.iid.m */
final /* synthetic */ class C1942m implements Continuation {
    /* renamed from: a */
    private final C1941l f5733a;
    /* renamed from: b */
    private final Pair f5734b;

    C1942m(C1941l c1941l, Pair pair) {
        this.f5733a = c1941l;
        this.f5734b = pair;
    }

    public final Object then(Task task) {
        return this.f5733a.m8860a(this.f5734b, task);
    }
}
