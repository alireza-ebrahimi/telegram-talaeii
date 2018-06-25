package com.mohamadamin.persianmaterialdatetimepicker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.p022f.C0463k;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.c */
public class C2029c {
    /* renamed from: a */
    private static final C0463k<String, Typeface> f5951a = new C0463k();

    /* renamed from: a */
    public static Typeface m9113a(Context context, String str) {
        Typeface typeface;
        synchronized (f5951a) {
            if (f5951a.containsKey(str)) {
                typeface = (Typeface) f5951a.get(str);
            } else {
                typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s.ttf", new Object[]{str}));
                f5951a.put(str, typeface);
            }
        }
        return typeface;
    }
}
