package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

@TargetApi(23)
/* renamed from: android.support.v4.app.g */
class C0332g {
    /* renamed from: a */
    private final ActivityOptions f1027a;

    private C0332g(ActivityOptions activityOptions) {
        this.f1027a = activityOptions;
    }

    /* renamed from: a */
    public static C0332g m1444a(Context context, int i, int i2) {
        return new C0332g(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    /* renamed from: a */
    public Bundle m1445a() {
        return this.f1027a.toBundle();
    }
}
