package android.support.v7.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.v4.content.p020a.C0402a;
import android.support.v4.view.as;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0739b;
import android.support.v7.p025a.C0748a.C0741d;
import android.support.v7.p025a.C0748a.C0747j;
import android.view.ViewConfiguration;
import org.telegram.ui.ChatActivity;

/* renamed from: android.support.v7.view.a */
public class C0842a {
    /* renamed from: a */
    private Context f1992a;

    private C0842a(Context context) {
        this.f1992a = context;
    }

    /* renamed from: a */
    public static C0842a m4013a(Context context) {
        return new C0842a(context);
    }

    /* renamed from: a */
    public int m4014a() {
        Resources resources = this.f1992a.getResources();
        int b = C0402a.m1861b(resources);
        int a = C0402a.m1860a(resources);
        return (C0402a.m1862c(resources) > 600 || b > 600 || ((b > 960 && a > 720) || (b > 720 && a > 960))) ? 5 : (b >= ChatActivity.startAllServices || ((b > 640 && a > 480) || (b > 480 && a > 640))) ? 4 : b >= 360 ? 3 : 2;
    }

    /* renamed from: b */
    public boolean m4015b() {
        return VERSION.SDK_INT >= 19 || !as.m2934a(ViewConfiguration.get(this.f1992a));
    }

    /* renamed from: c */
    public int m4016c() {
        return this.f1992a.getResources().getDisplayMetrics().widthPixels / 2;
    }

    /* renamed from: d */
    public boolean m4017d() {
        return this.f1992a.getResources().getBoolean(C0739b.abc_action_bar_embed_tabs);
    }

    /* renamed from: e */
    public int m4018e() {
        TypedArray obtainStyledAttributes = this.f1992a.obtainStyledAttributes(null, C0747j.ActionBar, C0738a.actionBarStyle, 0);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(C0747j.ActionBar_height, 0);
        Resources resources = this.f1992a.getResources();
        if (!m4017d()) {
            layoutDimension = Math.min(layoutDimension, resources.getDimensionPixelSize(C0741d.abc_action_bar_stacked_max_height));
        }
        obtainStyledAttributes.recycle();
        return layoutDimension;
    }

    /* renamed from: f */
    public boolean m4019f() {
        return this.f1992a.getApplicationInfo().targetSdkVersion < 14;
    }

    /* renamed from: g */
    public int m4020g() {
        return this.f1992a.getResources().getDimensionPixelSize(C0741d.abc_action_bar_stacked_tab_max_width);
    }
}
