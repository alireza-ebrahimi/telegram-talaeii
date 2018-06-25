package android.support.v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

@TargetApi(18)
class ae {
    /* renamed from: a */
    public static void m3319a(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    /* renamed from: a */
    public static Drawable[] m3320a(TextView textView) {
        return textView.getCompoundDrawablesRelative();
    }
}
