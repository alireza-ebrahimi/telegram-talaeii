package com.crashlytics.android.p064a;

import android.content.Context;
import android.os.Looper;
import p033b.p034a.p035a.p036a.p037a.p039b.C1131t;
import p033b.p034a.p035a.p036a.p037a.p042d.C1173g;
import p033b.p034a.p035a.p036a.p037a.p044f.C1192a;

/* renamed from: com.crashlytics.android.a.g */
class C1343g {
    /* renamed from: a */
    final Context f4081a;
    /* renamed from: b */
    final C1192a f4082b;

    public C1343g(Context context, C1192a c1192a) {
        this.f4081a = context;
        this.f4082b = c1192a;
    }

    /* renamed from: a */
    public C1363z m6850a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("AnswersFilesManagerProvider cannot be called on the main thread");
        }
        return new C1363z(this.f4081a, new af(), new C1131t(), new C1173g(this.f4081a, this.f4082b.mo1050a(), "session_analytics.tap", "session_analytics_to_send"));
    }
}
