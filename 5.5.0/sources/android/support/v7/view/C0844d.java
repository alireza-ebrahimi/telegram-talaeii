package android.support.v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources.Theme;
import android.support.v7.p025a.C0748a.C0746i;
import android.view.LayoutInflater;

/* renamed from: android.support.v7.view.d */
public class C0844d extends ContextWrapper {
    /* renamed from: a */
    private int f1993a;
    /* renamed from: b */
    private Theme f1994b;
    /* renamed from: c */
    private LayoutInflater f1995c;

    public C0844d(Context context, int i) {
        super(context);
        this.f1993a = i;
    }

    public C0844d(Context context, Theme theme) {
        super(context);
        this.f1994b = theme;
    }

    /* renamed from: b */
    private void m4023b() {
        boolean z = this.f1994b == null;
        if (z) {
            this.f1994b = getResources().newTheme();
            Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f1994b.setTo(theme);
            }
        }
        m4025a(this.f1994b, this.f1993a, z);
    }

    /* renamed from: a */
    public int m4024a() {
        return this.f1993a;
    }

    /* renamed from: a */
    protected void m4025a(Theme theme, int i, boolean z) {
        theme.applyStyle(i, true);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f1995c == null) {
            this.f1995c = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f1995c;
    }

    public Theme getTheme() {
        if (this.f1994b != null) {
            return this.f1994b;
        }
        if (this.f1993a == 0) {
            this.f1993a = C0746i.Theme_AppCompat_Light;
        }
        m4023b();
        return this.f1994b;
    }

    public void setTheme(int i) {
        if (this.f1993a != i) {
            this.f1993a = i;
            m4023b();
        }
    }
}
