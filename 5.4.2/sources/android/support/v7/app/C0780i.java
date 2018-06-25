package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.C0773f.C0772a;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.C0847f.C0846a;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Window;
import android.view.Window.Callback;

@TargetApi(14)
/* renamed from: android.support.v7.app.i */
class C0780i extends C0779h {
    /* renamed from: t */
    private int f1799t = -100;
    /* renamed from: u */
    private boolean f1800u;
    /* renamed from: v */
    private boolean f1801v = true;
    /* renamed from: w */
    private C0784b f1802w;

    /* renamed from: android.support.v7.app.i$a */
    class C0774a extends C0772a {
        /* renamed from: c */
        final /* synthetic */ C0780i f1770c;

        C0774a(C0780i c0780i, Callback callback) {
            this.f1770c = c0780i;
            super(c0780i, callback);
        }

        /* renamed from: a */
        final ActionMode m3704a(ActionMode.Callback callback) {
            Object c0846a = new C0846a(this.f1770c.a, callback);
            C0814b b = this.f1770c.m3740b((C0797a) c0846a);
            return b != null ? c0846a.m4046b(b) : null;
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return this.f1770c.mo653o() ? m3704a(callback) : super.onWindowStartingActionMode(callback);
        }
    }

    /* renamed from: android.support.v7.app.i$b */
    final class C0784b {
        /* renamed from: a */
        final /* synthetic */ C0780i f1805a;
        /* renamed from: b */
        private C0810q f1806b;
        /* renamed from: c */
        private boolean f1807c;
        /* renamed from: d */
        private BroadcastReceiver f1808d;
        /* renamed from: e */
        private IntentFilter f1809e;

        /* renamed from: android.support.v7.app.i$b$1 */
        class C07831 extends BroadcastReceiver {
            /* renamed from: a */
            final /* synthetic */ C0784b f1804a;

            C07831(C0784b c0784b) {
                this.f1804a = c0784b;
            }

            public void onReceive(Context context, Intent intent) {
                this.f1804a.m3783b();
            }
        }

        C0784b(C0780i c0780i, C0810q c0810q) {
            this.f1805a = c0780i;
            this.f1806b = c0810q;
            this.f1807c = c0810q.m3852a();
        }

        /* renamed from: a */
        final int m3782a() {
            this.f1807c = this.f1806b.m3852a();
            return this.f1807c ? 2 : 1;
        }

        /* renamed from: b */
        final void m3783b() {
            boolean a = this.f1806b.m3852a();
            if (a != this.f1807c) {
                this.f1807c = a;
                this.f1805a.mo625i();
            }
        }

        /* renamed from: c */
        final void m3784c() {
            m3785d();
            if (this.f1808d == null) {
                this.f1808d = new C07831(this);
            }
            if (this.f1809e == null) {
                this.f1809e = new IntentFilter();
                this.f1809e.addAction("android.intent.action.TIME_SET");
                this.f1809e.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.f1809e.addAction("android.intent.action.TIME_TICK");
            }
            this.f1805a.a.registerReceiver(this.f1808d, this.f1809e);
        }

        /* renamed from: d */
        final void m3785d() {
            if (this.f1808d != null) {
                this.f1805a.a.unregisterReceiver(this.f1808d);
                this.f1808d = null;
            }
        }
    }

    C0780i(Context context, Window window, C0145d c0145d) {
        super(context, window, c0145d);
    }

    /* renamed from: h */
    private boolean m3766h(int i) {
        Resources resources = this.a.getResources();
        Configuration configuration = resources.getConfiguration();
        int i2 = configuration.uiMode & 48;
        int i3 = i == 2 ? 32 : 16;
        if (i2 == i3) {
            return false;
        }
        if (m3769y()) {
            ((Activity) this.a).recreate();
        } else {
            Configuration configuration2 = new Configuration(configuration);
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            configuration2.uiMode = i3 | (configuration2.uiMode & -49);
            resources.updateConfiguration(configuration2, displayMetrics);
            C0804n.m3820a(resources);
        }
        return true;
    }

    /* renamed from: w */
    private int m3767w() {
        return this.f1799t != -100 ? this.f1799t : C0769e.m3659j();
    }

    /* renamed from: x */
    private void m3768x() {
        if (this.f1802w == null) {
            this.f1802w = new C0784b(this, C0810q.m3848a(this.a));
        }
    }

    /* renamed from: y */
    private boolean m3769y() {
        if (!this.f1800u || !(this.a instanceof Activity)) {
            return false;
        }
        try {
            return (this.a.getPackageManager().getActivityInfo(new ComponentName(this.a, this.a.getClass()), 0).configChanges & 512) == 0;
        } catch (Throwable e) {
            Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
            return true;
        }
    }

    /* renamed from: a */
    Callback mo651a(Callback callback) {
        return new C0774a(this, callback);
    }

    /* renamed from: a */
    public void mo633a(Bundle bundle) {
        super.mo633a(bundle);
        if (bundle != null && this.f1799t == -100) {
            this.f1799t = bundle.getInt("appcompat:local_night_mode", -100);
        }
    }

    /* renamed from: c */
    public void mo621c() {
        super.mo621c();
        mo625i();
    }

    /* renamed from: c */
    public void mo622c(Bundle bundle) {
        super.mo622c(bundle);
        if (this.f1799t != -100) {
            bundle.putInt("appcompat:local_night_mode", this.f1799t);
        }
    }

    /* renamed from: d */
    int mo652d(int i) {
        switch (i) {
            case -100:
                return -1;
            case 0:
                m3768x();
                return this.f1802w.m3782a();
            default:
                return i;
        }
    }

    /* renamed from: d */
    public void mo623d() {
        super.mo623d();
        if (this.f1802w != null) {
            this.f1802w.m3785d();
        }
    }

    /* renamed from: g */
    public void mo624g() {
        super.mo624g();
        if (this.f1802w != null) {
            this.f1802w.m3785d();
        }
    }

    /* renamed from: i */
    public boolean mo625i() {
        boolean z = false;
        int w = m3767w();
        int d = mo652d(w);
        if (d != -1) {
            z = m3766h(d);
        }
        if (w == 0) {
            m3768x();
            this.f1802w.m3784c();
        }
        this.f1800u = true;
        return z;
    }

    /* renamed from: o */
    public boolean mo653o() {
        return this.f1801v;
    }
}
