package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.C0873h.C0777a;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* renamed from: android.support.v7.view.menu.u */
public class C0890u extends C0873h implements SubMenu {
    /* renamed from: d */
    private C0873h f2272d;
    /* renamed from: e */
    private C0876j f2273e;

    public C0890u(Context context, C0873h c0873h, C0876j c0876j) {
        super(context);
        this.f2272d = c0873h;
        this.f2273e = c0876j;
    }

    /* renamed from: a */
    public String mo756a() {
        int itemId = this.f2273e != null ? this.f2273e.getItemId() : 0;
        return itemId == 0 ? null : super.mo756a() + ":" + itemId;
    }

    /* renamed from: a */
    public void mo757a(C0777a c0777a) {
        this.f2272d.mo757a(c0777a);
    }

    /* renamed from: a */
    boolean mo758a(C0873h c0873h, MenuItem menuItem) {
        return super.mo758a(c0873h, menuItem) || this.f2272d.mo758a(c0873h, menuItem);
    }

    /* renamed from: b */
    public boolean mo759b() {
        return this.f2272d.mo759b();
    }

    /* renamed from: c */
    public boolean mo760c() {
        return this.f2272d.mo760c();
    }

    /* renamed from: c */
    public boolean mo761c(C0876j c0876j) {
        return this.f2272d.mo761c(c0876j);
    }

    /* renamed from: d */
    public boolean mo762d(C0876j c0876j) {
        return this.f2272d.mo762d(c0876j);
    }

    public MenuItem getItem() {
        return this.f2273e;
    }

    /* renamed from: p */
    public C0873h mo763p() {
        return this.f2272d.mo763p();
    }

    /* renamed from: s */
    public Menu m4353s() {
        return this.f2272d;
    }

    public SubMenu setHeaderIcon(int i) {
        return (SubMenu) super.m4248e(i);
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        return (SubMenu) super.m4217a(drawable);
    }

    public SubMenu setHeaderTitle(int i) {
        return (SubMenu) super.m4245d(i);
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        return (SubMenu) super.m4219a(charSequence);
    }

    public SubMenu setHeaderView(View view) {
        return (SubMenu) super.m4218a(view);
    }

    public SubMenu setIcon(int i) {
        this.f2273e.setIcon(i);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        this.f2273e.setIcon(drawable);
        return this;
    }

    public void setQwertyMode(boolean z) {
        this.f2272d.setQwertyMode(z);
    }
}
