package android.support.v7.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/* renamed from: android.support.v7.view.b */
public abstract class C0814b {
    /* renamed from: a */
    private Object f1893a;
    /* renamed from: b */
    private boolean f1894b;

    /* renamed from: android.support.v7.view.b$a */
    public interface C0797a {
        /* renamed from: a */
        void mo659a(C0814b c0814b);

        /* renamed from: a */
        boolean mo660a(C0814b c0814b, Menu menu);

        /* renamed from: a */
        boolean mo661a(C0814b c0814b, MenuItem menuItem);

        /* renamed from: b */
        boolean mo662b(C0814b c0814b, Menu menu);
    }

    /* renamed from: a */
    public abstract MenuInflater mo679a();

    /* renamed from: a */
    public abstract void mo680a(int i);

    /* renamed from: a */
    public abstract void mo681a(View view);

    /* renamed from: a */
    public abstract void mo682a(CharSequence charSequence);

    /* renamed from: a */
    public void m3858a(Object obj) {
        this.f1893a = obj;
    }

    /* renamed from: a */
    public void mo683a(boolean z) {
        this.f1894b = z;
    }

    /* renamed from: b */
    public abstract Menu mo684b();

    /* renamed from: b */
    public abstract void mo685b(int i);

    /* renamed from: b */
    public abstract void mo686b(CharSequence charSequence);

    /* renamed from: c */
    public abstract void mo687c();

    /* renamed from: d */
    public abstract void mo688d();

    /* renamed from: f */
    public abstract CharSequence mo689f();

    /* renamed from: g */
    public abstract CharSequence mo690g();

    /* renamed from: h */
    public boolean mo691h() {
        return false;
    }

    /* renamed from: i */
    public abstract View mo692i();

    /* renamed from: j */
    public Object m3869j() {
        return this.f1893a;
    }

    /* renamed from: k */
    public boolean m3870k() {
        return this.f1894b;
    }
}
