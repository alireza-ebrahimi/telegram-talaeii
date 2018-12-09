package android.support.v4.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

@TargetApi(16)
/* renamed from: android.support.v4.content.f */
class C0411f {
    /* renamed from: a */
    public static void m1877a(Context context, Intent intent, Bundle bundle) {
        context.startActivity(intent, bundle);
    }

    /* renamed from: a */
    public static void m1878a(Context context, Intent[] intentArr, Bundle bundle) {
        context.startActivities(intentArr, bundle);
    }
}
