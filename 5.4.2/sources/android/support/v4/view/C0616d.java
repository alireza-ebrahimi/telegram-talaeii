package android.support.v4.view;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* renamed from: android.support.v4.view.d */
public abstract class C0616d {
    /* renamed from: a */
    private final Context f1359a;
    /* renamed from: b */
    private C0614a f1360b;
    /* renamed from: c */
    private C0615b f1361c;

    /* renamed from: android.support.v4.view.d$a */
    public interface C0614a {
        /* renamed from: a */
        void mo1001a(boolean z);
    }

    /* renamed from: android.support.v4.view.d$b */
    public interface C0615b {
        /* renamed from: a */
        void mo742a(boolean z);
    }

    public C0616d(Context context) {
        this.f1359a = context;
    }

    /* renamed from: a */
    public abstract View mo743a();

    /* renamed from: a */
    public View mo751a(MenuItem menuItem) {
        return mo743a();
    }

    /* renamed from: a */
    public void m3095a(C0614a c0614a) {
        this.f1360b = c0614a;
    }

    /* renamed from: a */
    public void mo752a(C0615b c0615b) {
        if (!(this.f1361c == null || c0615b == null)) {
            Log.w("ActionProvider(support)", "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.f1361c = c0615b;
    }

    /* renamed from: a */
    public void mo744a(SubMenu subMenu) {
    }

    /* renamed from: a */
    public void m3098a(boolean z) {
        if (this.f1360b != null) {
            this.f1360b.mo1001a(z);
        }
    }

    /* renamed from: b */
    public boolean mo753b() {
        return false;
    }

    /* renamed from: c */
    public boolean mo754c() {
        return true;
    }

    /* renamed from: d */
    public boolean mo745d() {
        return false;
    }

    /* renamed from: e */
    public boolean mo746e() {
        return false;
    }

    /* renamed from: f */
    public void m3103f() {
        this.f1361c = null;
        this.f1360b = null;
    }
}
