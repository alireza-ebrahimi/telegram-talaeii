package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

@TargetApi(24)
/* renamed from: android.support.v4.app.h */
class C0333h {
    /* renamed from: a */
    private final ActivityOptions f1028a;

    private C0333h(ActivityOptions activityOptions) {
        this.f1028a = activityOptions;
    }

    /* renamed from: a */
    public static C0333h m1446a(Context context, int i, int i2) {
        return new C0333h(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    /* renamed from: a */
    public Bundle m1447a() {
        return this.f1028a.toBundle();
    }
}
