package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

@TargetApi(21)
/* renamed from: android.support.v4.app.f */
class C0331f {
    /* renamed from: a */
    private final ActivityOptions f1026a;

    private C0331f(ActivityOptions activityOptions) {
        this.f1026a = activityOptions;
    }

    /* renamed from: a */
    public static C0331f m1442a(Context context, int i, int i2) {
        return new C0331f(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    /* renamed from: a */
    public Bundle m1443a() {
        return this.f1026a.toBundle();
    }
}
