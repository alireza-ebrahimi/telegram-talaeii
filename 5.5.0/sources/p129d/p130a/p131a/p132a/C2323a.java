package p129d.p130a.p131a.p132a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* renamed from: d.a.a.a.a */
public class C2323a implements C2322d {
    /* renamed from: a */
    private final Activity f7740a;

    public C2323a(Activity activity) {
        this.f7740a = activity;
    }

    /* renamed from: a */
    public TypedArray mo3370a(int i, int[] iArr) {
        return this.f7740a.obtainStyledAttributes(i, iArr);
    }

    /* renamed from: a */
    public View mo3371a(int i) {
        return this.f7740a.findViewById(i);
    }

    /* renamed from: a */
    public Window m11605a() {
        return this.f7740a.getWindow();
    }

    /* renamed from: b */
    public ViewGroup mo3372b() {
        return (ViewGroup) m11605a().getDecorView();
    }

    /* renamed from: c */
    public Context mo3373c() {
        return this.f7740a;
    }

    /* renamed from: d */
    public Resources mo3374d() {
        return this.f7740a.getResources();
    }

    /* renamed from: e */
    public Theme mo3375e() {
        return this.f7740a.getTheme();
    }
}
