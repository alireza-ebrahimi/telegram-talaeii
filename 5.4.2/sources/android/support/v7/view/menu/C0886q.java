package android.support.v7.view.menu;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.p018c.p019a.C0393a;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.p018c.p019a.C0395c;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

/* renamed from: android.support.v7.view.menu.q */
public final class C0886q {
    /* renamed from: a */
    public static Menu m4324a(Context context, C0393a c0393a) {
        if (VERSION.SDK_INT >= 14) {
            return new C0887r(context, c0393a);
        }
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    public static MenuItem m4325a(Context context, C0394b c0394b) {
        if (VERSION.SDK_INT >= 16) {
            return new C0883l(context, c0394b);
        }
        if (VERSION.SDK_INT >= 14) {
            return new C0881k(context, c0394b);
        }
        throw new UnsupportedOperationException();
    }

    /* renamed from: a */
    public static SubMenu m4326a(Context context, C0395c c0395c) {
        if (VERSION.SDK_INT >= 14) {
            return new C0891v(context, c0395c);
        }
        throw new UnsupportedOperationException();
    }
}
