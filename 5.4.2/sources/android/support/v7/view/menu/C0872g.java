package android.support.v7.view.menu;

import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.view.menu.C0079p.C0077a;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* renamed from: android.support.v7.view.menu.g */
public class C0872g extends BaseAdapter {
    /* renamed from: a */
    static final int f2168a = C0744g.abc_popup_menu_item_layout;
    /* renamed from: b */
    C0873h f2169b;
    /* renamed from: c */
    private int f2170c = -1;
    /* renamed from: d */
    private boolean f2171d;
    /* renamed from: e */
    private final boolean f2172e;
    /* renamed from: f */
    private final LayoutInflater f2173f;

    public C0872g(C0873h c0873h, LayoutInflater layoutInflater, boolean z) {
        this.f2172e = z;
        this.f2173f = layoutInflater;
        this.f2169b = c0873h;
        m4206b();
    }

    /* renamed from: a */
    public C0873h m4203a() {
        return this.f2169b;
    }

    /* renamed from: a */
    public C0876j m4204a(int i) {
        ArrayList l = this.f2172e ? this.f2169b.m4255l() : this.f2169b.m4252i();
        if (this.f2170c >= 0 && i >= this.f2170c) {
            i++;
        }
        return (C0876j) l.get(i);
    }

    /* renamed from: a */
    public void m4205a(boolean z) {
        this.f2171d = z;
    }

    /* renamed from: b */
    void m4206b() {
        C0876j r = this.f2169b.m4261r();
        if (r != null) {
            ArrayList l = this.f2169b.m4255l();
            int size = l.size();
            for (int i = 0; i < size; i++) {
                if (((C0876j) l.get(i)) == r) {
                    this.f2170c = i;
                    return;
                }
            }
        }
        this.f2170c = -1;
    }

    public int getCount() {
        ArrayList l = this.f2172e ? this.f2169b.m4255l() : this.f2169b.m4252i();
        return this.f2170c < 0 ? l.size() : l.size() - 1;
    }

    public /* synthetic */ Object getItem(int i) {
        return m4204a(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = view == null ? this.f2173f.inflate(f2168a, viewGroup, false) : view;
        C0077a c0077a = (C0077a) inflate;
        if (this.f2171d) {
            ((ListMenuItemView) inflate).setForceShowIcon(true);
        }
        c0077a.mo43a(m4204a(i), 0);
        return inflate;
    }

    public void notifyDataSetChanged() {
        m4206b();
        super.notifyDataSetChanged();
    }
}
