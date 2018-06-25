package com.google.firebase.iid;

import com.google.android.gms.tasks.Task;

final /* synthetic */ class af implements C1930n {
    /* renamed from: a */
    private final FirebaseInstanceId f5683a;
    /* renamed from: b */
    private final String f5684b;
    /* renamed from: c */
    private final String f5685c;
    /* renamed from: d */
    private final String f5686d;

    af(FirebaseInstanceId firebaseInstanceId, String str, String str2, String str3) {
        this.f5683a = firebaseInstanceId;
        this.f5684b = str;
        this.f5685c = str2;
        this.f5686d = str3;
    }

    /* renamed from: a */
    public final Task mo3047a() {
        return this.f5683a.m8766a(this.f5684b, this.f5685c, this.f5686d);
    }
}
