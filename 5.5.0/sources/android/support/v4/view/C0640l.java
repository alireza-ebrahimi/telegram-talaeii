package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.C0638k.C0637a;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

@TargetApi(11)
/* renamed from: android.support.v4.view.l */
class C0640l {
    /* renamed from: a */
    private static Field f1393a;
    /* renamed from: b */
    private static boolean f1394b;

    /* renamed from: android.support.v4.view.l$a */
    static class C0639a extends C0637a implements Factory2 {
        C0639a(C0365n c0365n) {
            super(c0365n);
        }

        public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
            return this.a.mo285a(view, str, context, attributeSet);
        }
    }

    /* renamed from: a */
    static void m3153a(LayoutInflater layoutInflater, C0365n c0365n) {
        Factory2 c0639a = c0365n != null ? new C0639a(c0365n) : null;
        layoutInflater.setFactory2(c0639a);
        Factory factory = layoutInflater.getFactory();
        if (factory instanceof Factory2) {
            C0640l.m3154a(layoutInflater, (Factory2) factory);
        } else {
            C0640l.m3154a(layoutInflater, c0639a);
        }
    }

    /* renamed from: a */
    static void m3154a(LayoutInflater layoutInflater, Factory2 factory2) {
        if (!f1394b) {
            try {
                f1393a = LayoutInflater.class.getDeclaredField("mFactory2");
                f1393a.setAccessible(true);
            } catch (Throwable e) {
                Log.e("LayoutInflaterCompatHC", "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", e);
            }
            f1394b = true;
        }
        if (f1393a != null) {
            try {
                f1393a.set(layoutInflater, factory2);
            } catch (Throwable e2) {
                Log.e("LayoutInflaterCompatHC", "forceSetFactory2 could not set the Factory2 on LayoutInflater " + layoutInflater + "; inflation may have unexpected results.", e2);
            }
        }
    }
}
