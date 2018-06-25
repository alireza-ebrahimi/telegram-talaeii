package android.support.v4.app;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

/* renamed from: android.support.v4.app.o */
abstract class C0344o extends C0343n {
    /* renamed from: b */
    boolean f1059b;

    C0344o() {
    }

    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (!(this.f1059b || i == -1)) {
            C0342m.m1482b(i);
        }
        super.startActivityForResult(intent, i, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (!(this.a || i == -1)) {
            C0342m.m1482b(i);
        }
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }
}
