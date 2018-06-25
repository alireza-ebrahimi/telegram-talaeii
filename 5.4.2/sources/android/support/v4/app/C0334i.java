package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

@TargetApi(16)
/* renamed from: android.support.v4.app.i */
class C0334i {
    /* renamed from: a */
    private final ActivityOptions f1029a;

    private C0334i(ActivityOptions activityOptions) {
        this.f1029a = activityOptions;
    }

    /* renamed from: a */
    public static C0334i m1448a(Context context, int i, int i2) {
        return new C0334i(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    /* renamed from: a */
    public Bundle m1449a() {
        return this.f1029a.toBundle();
    }
}
