package android.support.v4.view;

import android.os.Build.VERSION;
import android.support.v4.p018c.p019a.C0394b;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/* renamed from: android.support.v4.view.q */
public final class C0652q {
    /* renamed from: a */
    static final C0647d f1396a;

    /* renamed from: android.support.v4.view.q$d */
    interface C0647d {
        /* renamed from: a */
        MenuItem mo560a(MenuItem menuItem, View view);

        /* renamed from: a */
        View mo561a(MenuItem menuItem);

        /* renamed from: a */
        void mo562a(MenuItem menuItem, int i);

        /* renamed from: b */
        MenuItem mo563b(MenuItem menuItem, int i);

        /* renamed from: b */
        boolean mo564b(MenuItem menuItem);

        /* renamed from: c */
        boolean mo565c(MenuItem menuItem);
    }

    /* renamed from: android.support.v4.view.q$a */
    static class C0648a implements C0647d {
        C0648a() {
        }

        /* renamed from: a */
        public MenuItem mo560a(MenuItem menuItem, View view) {
            return menuItem;
        }

        /* renamed from: a */
        public View mo561a(MenuItem menuItem) {
            return null;
        }

        /* renamed from: a */
        public void mo562a(MenuItem menuItem, int i) {
        }

        /* renamed from: b */
        public MenuItem mo563b(MenuItem menuItem, int i) {
            return menuItem;
        }

        /* renamed from: b */
        public boolean mo564b(MenuItem menuItem) {
            return false;
        }

        /* renamed from: c */
        public boolean mo565c(MenuItem menuItem) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.q$b */
    static class C0649b implements C0647d {
        C0649b() {
        }

        /* renamed from: a */
        public MenuItem mo560a(MenuItem menuItem, View view) {
            return C0653r.m3195a(menuItem, view);
        }

        /* renamed from: a */
        public View mo561a(MenuItem menuItem) {
            return C0653r.m3196a(menuItem);
        }

        /* renamed from: a */
        public void mo562a(MenuItem menuItem, int i) {
            C0653r.m3197a(menuItem, i);
        }

        /* renamed from: b */
        public MenuItem mo563b(MenuItem menuItem, int i) {
            return C0653r.m3198b(menuItem, i);
        }

        /* renamed from: b */
        public boolean mo564b(MenuItem menuItem) {
            return false;
        }

        /* renamed from: c */
        public boolean mo565c(MenuItem menuItem) {
            return false;
        }
    }

    /* renamed from: android.support.v4.view.q$c */
    static class C0650c extends C0649b {
        C0650c() {
        }

        /* renamed from: b */
        public boolean mo564b(MenuItem menuItem) {
            return C0654s.m3199a(menuItem);
        }

        /* renamed from: c */
        public boolean mo565c(MenuItem menuItem) {
            return C0654s.m3200b(menuItem);
        }
    }

    /* renamed from: android.support.v4.view.q$e */
    public interface C0651e {
        /* renamed from: a */
        boolean mo749a(MenuItem menuItem);

        /* renamed from: b */
        boolean mo750b(MenuItem menuItem);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            f1396a = new C0650c();
        } else if (VERSION.SDK_INT >= 11) {
            f1396a = new C0649b();
        } else {
            f1396a = new C0648a();
        }
    }

    /* renamed from: a */
    public static MenuItem m3188a(MenuItem menuItem, C0616d c0616d) {
        if (menuItem instanceof C0394b) {
            return ((C0394b) menuItem).mo708a(c0616d);
        }
        Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
        return menuItem;
    }

    /* renamed from: a */
    public static MenuItem m3189a(MenuItem menuItem, View view) {
        return menuItem instanceof C0394b ? ((C0394b) menuItem).setActionView(view) : f1396a.mo560a(menuItem, view);
    }

    /* renamed from: a */
    public static View m3190a(MenuItem menuItem) {
        return menuItem instanceof C0394b ? ((C0394b) menuItem).getActionView() : f1396a.mo561a(menuItem);
    }

    /* renamed from: a */
    public static void m3191a(MenuItem menuItem, int i) {
        if (menuItem instanceof C0394b) {
            ((C0394b) menuItem).setShowAsAction(i);
        } else {
            f1396a.mo562a(menuItem, i);
        }
    }

    /* renamed from: b */
    public static MenuItem m3192b(MenuItem menuItem, int i) {
        return menuItem instanceof C0394b ? ((C0394b) menuItem).setActionView(i) : f1396a.mo563b(menuItem, i);
    }

    /* renamed from: b */
    public static boolean m3193b(MenuItem menuItem) {
        return menuItem instanceof C0394b ? ((C0394b) menuItem).expandActionView() : f1396a.mo564b(menuItem);
    }

    /* renamed from: c */
    public static boolean m3194c(MenuItem menuItem) {
        return menuItem instanceof C0394b ? ((C0394b) menuItem).isActionViewExpanded() : f1396a.mo565c(menuItem);
    }
}
