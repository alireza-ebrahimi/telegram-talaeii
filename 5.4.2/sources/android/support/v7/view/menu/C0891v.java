package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.p018c.p019a.C0395c;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

@TargetApi(14)
/* renamed from: android.support.v7.view.menu.v */
class C0891v extends C0887r implements SubMenu {
    C0891v(Context context, C0395c c0395c) {
        super(context, c0395c);
    }

    /* renamed from: b */
    public C0395c m4354b() {
        return (C0395c) this.b;
    }

    public void clearHeader() {
        m4354b().clearHeader();
    }

    public MenuItem getItem() {
        return m4135a(m4354b().getItem());
    }

    public SubMenu setHeaderIcon(int i) {
        m4354b().setHeaderIcon(i);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        m4354b().setHeaderIcon(drawable);
        return this;
    }

    public SubMenu setHeaderTitle(int i) {
        m4354b().setHeaderTitle(i);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        m4354b().setHeaderTitle(charSequence);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        m4354b().setHeaderView(view);
        return this;
    }

    public SubMenu setIcon(int i) {
        m4354b().setIcon(i);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        m4354b().setIcon(drawable);
        return this;
    }
}
