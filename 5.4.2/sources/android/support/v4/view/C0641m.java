package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.v4.view.C0640l.C0639a;
import android.view.LayoutInflater;

@TargetApi(21)
/* renamed from: android.support.v4.view.m */
class C0641m {
    /* renamed from: a */
    static void m3155a(LayoutInflater layoutInflater, C0365n c0365n) {
        layoutInflater.setFactory2(c0365n != null ? new C0639a(c0365n) : null);
    }
}
