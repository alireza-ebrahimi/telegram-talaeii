package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

@TargetApi(9)
/* renamed from: android.support.v4.view.k */
class C0638k {

    /* renamed from: android.support.v4.view.k$a */
    static class C0637a implements Factory {
        /* renamed from: a */
        final C0365n f1392a;

        C0637a(C0365n c0365n) {
            this.f1392a = c0365n;
        }

        public View onCreateView(String str, Context context, AttributeSet attributeSet) {
            return this.f1392a.mo285a(null, str, context, attributeSet);
        }

        public String toString() {
            return getClass().getName() + "{" + this.f1392a + "}";
        }
    }

    /* renamed from: a */
    static C0365n m3151a(LayoutInflater layoutInflater) {
        Factory factory = layoutInflater.getFactory();
        return factory instanceof C0637a ? ((C0637a) factory).f1392a : null;
    }

    /* renamed from: a */
    static void m3152a(LayoutInflater layoutInflater, C0365n c0365n) {
        layoutInflater.setFactory(c0365n != null ? new C0637a(c0365n) : null);
    }
}
