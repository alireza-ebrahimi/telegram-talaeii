package android.support.v7.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ah;
import android.support.v7.app.C0765a.C0763b;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.widget.ag;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window.Callback;
import java.util.ArrayList;

/* renamed from: android.support.v7.app.o */
class C0807o extends C0765a {
    /* renamed from: a */
    ag f1870a;
    /* renamed from: b */
    Callback f1871b;
    /* renamed from: c */
    private boolean f1872c;
    /* renamed from: d */
    private boolean f1873d;
    /* renamed from: e */
    private ArrayList<C0763b> f1874e;
    /* renamed from: f */
    private final Runnable f1875f;

    /* renamed from: android.support.v7.app.o$a */
    private final class C0805a implements C0794a {
        /* renamed from: a */
        final /* synthetic */ C0807o f1867a;
        /* renamed from: b */
        private boolean f1868b;

        C0805a(C0807o c0807o) {
            this.f1867a = c0807o;
        }

        /* renamed from: a */
        public void mo657a(C0873h c0873h, boolean z) {
            if (!this.f1868b) {
                this.f1868b = true;
                this.f1867a.f1870a.mo990n();
                if (this.f1867a.f1871b != null) {
                    this.f1867a.f1871b.onPanelClosed(108, c0873h);
                }
                this.f1868b = false;
            }
        }

        /* renamed from: a */
        public boolean mo658a(C0873h c0873h) {
            if (this.f1867a.f1871b == null) {
                return false;
            }
            this.f1867a.f1871b.onMenuOpened(108, c0873h);
            return true;
        }
    }

    /* renamed from: android.support.v7.app.o$b */
    private final class C0806b implements C0777a {
        /* renamed from: a */
        final /* synthetic */ C0807o f1869a;

        C0806b(C0807o c0807o) {
            this.f1869a = c0807o;
        }

        /* renamed from: a */
        public void mo634a(C0873h c0873h) {
            if (this.f1869a.f1871b == null) {
                return;
            }
            if (this.f1869a.f1870a.mo985i()) {
                this.f1869a.f1871b.onPanelClosed(108, c0873h);
            } else if (this.f1869a.f1871b.onPreparePanel(0, null, c0873h)) {
                this.f1869a.f1871b.onMenuOpened(108, c0873h);
            }
        }

        /* renamed from: a */
        public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
            return false;
        }
    }

    /* renamed from: i */
    private Menu m3829i() {
        if (!this.f1872c) {
            this.f1870a.mo968a(new C0805a(this), new C0806b(this));
            this.f1872c = true;
        }
        return this.f1870a.mo994r();
    }

    /* renamed from: a */
    public int mo663a() {
        return this.f1870a.mo991o();
    }

    /* renamed from: a */
    public void mo664a(float f) {
        ah.m2821k(this.f1870a.mo965a(), f);
    }

    /* renamed from: a */
    public void mo665a(Configuration configuration) {
        super.mo665a(configuration);
    }

    /* renamed from: a */
    public void mo666a(CharSequence charSequence) {
        this.f1870a.mo972a(charSequence);
    }

    /* renamed from: a */
    public void mo667a(boolean z) {
    }

    /* renamed from: a */
    public boolean mo668a(int i, KeyEvent keyEvent) {
        Menu i2 = m3829i();
        if (i2 != null) {
            i2.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
            i2.performShortcut(i, keyEvent, 0);
        }
        return true;
    }

    /* renamed from: b */
    public boolean mo669b() {
        return this.f1870a.mo993q() == 0;
    }

    /* renamed from: c */
    public Context mo670c() {
        return this.f1870a.mo974b();
    }

    /* renamed from: c */
    public void mo671c(boolean z) {
    }

    /* renamed from: d */
    public void mo672d(boolean z) {
    }

    /* renamed from: e */
    public void mo673e(boolean z) {
        if (z != this.f1873d) {
            this.f1873d = z;
            int size = this.f1874e.size();
            for (int i = 0; i < size; i++) {
                ((C0763b) this.f1874e.get(i)).m3605a(z);
            }
        }
    }

    /* renamed from: e */
    public boolean mo674e() {
        this.f1870a.mo965a().removeCallbacks(this.f1875f);
        ah.m2787a(this.f1870a.mo965a(), this.f1875f);
        return true;
    }

    /* renamed from: f */
    public boolean mo675f() {
        if (!this.f1870a.mo978c()) {
            return false;
        }
        this.f1870a.mo979d();
        return true;
    }

    /* renamed from: g */
    public boolean mo676g() {
        ViewGroup a = this.f1870a.mo965a();
        if (a == null || a.hasFocus()) {
            return false;
        }
        a.requestFocus();
        return true;
    }

    /* renamed from: h */
    void mo677h() {
        this.f1870a.mo965a().removeCallbacks(this.f1875f);
    }
}
