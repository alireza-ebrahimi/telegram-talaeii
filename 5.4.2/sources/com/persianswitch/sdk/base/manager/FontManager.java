package com.persianswitch.sdk.base.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public final class FontManager {
    /* renamed from: a */
    private static FontManager f7072a;
    /* renamed from: b */
    private final Context f7073b;
    /* renamed from: c */
    private Typeface f7074c = Typeface.createFromAsset(this.f7073b.getAssets(), "nyekan.ttf");
    /* renamed from: d */
    private Typeface f7075d = null;

    private FontManager(Context context) {
        this.f7073b = context;
    }

    /* renamed from: a */
    private Typeface m10662a(String str) {
        return "fa".equals(str) ? this.f7074c : this.f7075d;
    }

    /* renamed from: a */
    public static FontManager m10663a(Context context) {
        if (f7072a == null) {
            f7072a = new FontManager(context);
        }
        return f7072a;
    }

    /* renamed from: a */
    public static void m10664a(View view) {
        if (view != null) {
            m10663a(view.getContext()).m10666b(view);
        }
    }

    /* renamed from: a */
    public void m10665a(View view, Typeface typeface) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                m10665a(((ViewGroup) view).getChildAt(i), typeface);
            }
        }
    }

    /* renamed from: b */
    public void m10666b(View view) {
        m10665a(view, m10662a(LanguageManager.m10669a(this.f7073b).m10678c()));
    }
}
