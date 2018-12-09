package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.p018c.p019a.C0395c;
import android.support.v4.p022f.C0464a;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

/* renamed from: android.support.v7.view.menu.c */
abstract class C0862c<T> extends C0861d<T> {
    /* renamed from: a */
    final Context f2119a;
    /* renamed from: c */
    private Map<C0394b, MenuItem> f2120c;
    /* renamed from: d */
    private Map<C0395c, SubMenu> f2121d;

    C0862c(Context context, T t) {
        super(t);
        this.f2119a = context;
    }

    /* renamed from: a */
    final MenuItem m4135a(MenuItem menuItem) {
        if (!(menuItem instanceof C0394b)) {
            return menuItem;
        }
        C0394b c0394b = (C0394b) menuItem;
        if (this.f2120c == null) {
            this.f2120c = new C0464a();
        }
        MenuItem menuItem2 = (MenuItem) this.f2120c.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        menuItem2 = C0886q.m4325a(this.f2119a, c0394b);
        this.f2120c.put(c0394b, menuItem2);
        return menuItem2;
    }

    /* renamed from: a */
    final SubMenu m4136a(SubMenu subMenu) {
        if (!(subMenu instanceof C0395c)) {
            return subMenu;
        }
        C0395c c0395c = (C0395c) subMenu;
        if (this.f2121d == null) {
            this.f2121d = new C0464a();
        }
        SubMenu subMenu2 = (SubMenu) this.f2121d.get(c0395c);
        if (subMenu2 != null) {
            return subMenu2;
        }
        subMenu2 = C0886q.m4326a(this.f2119a, c0395c);
        this.f2121d.put(c0395c, subMenu2);
        return subMenu2;
    }

    /* renamed from: a */
    final void m4137a() {
        if (this.f2120c != null) {
            this.f2120c.clear();
        }
        if (this.f2121d != null) {
            this.f2121d.clear();
        }
    }

    /* renamed from: a */
    final void m4138a(int i) {
        if (this.f2120c != null) {
            Iterator it = this.f2120c.keySet().iterator();
            while (it.hasNext()) {
                if (i == ((MenuItem) it.next()).getGroupId()) {
                    it.remove();
                }
            }
        }
    }

    /* renamed from: b */
    final void m4139b(int i) {
        if (this.f2120c != null) {
            Iterator it = this.f2120c.keySet().iterator();
            while (it.hasNext()) {
                if (i == ((MenuItem) it.next()).getItemId()) {
                    it.remove();
                    return;
                }
            }
        }
    }
}
