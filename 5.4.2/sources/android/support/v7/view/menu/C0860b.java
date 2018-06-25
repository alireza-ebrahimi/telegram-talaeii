package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.view.ah;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.view.menu.C0859o.C0794a;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* renamed from: android.support.v7.view.menu.b */
public abstract class C0860b implements C0859o {
    /* renamed from: a */
    protected Context f2108a;
    /* renamed from: b */
    protected Context f2109b;
    /* renamed from: c */
    protected C0873h f2110c;
    /* renamed from: d */
    protected LayoutInflater f2111d;
    /* renamed from: e */
    protected LayoutInflater f2112e;
    /* renamed from: f */
    protected C0079p f2113f;
    /* renamed from: g */
    private C0794a f2114g;
    /* renamed from: h */
    private int f2115h;
    /* renamed from: i */
    private int f2116i;
    /* renamed from: j */
    private int f2117j;

    public C0860b(Context context, int i, int i2) {
        this.f2108a = context;
        this.f2111d = LayoutInflater.from(context);
        this.f2115h = i;
        this.f2116i = i2;
    }

    /* renamed from: a */
    public C0794a m4118a() {
        return this.f2114g;
    }

    /* renamed from: a */
    public C0079p mo998a(ViewGroup viewGroup) {
        if (this.f2113f == null) {
            this.f2113f = (C0079p) this.f2111d.inflate(this.f2115h, viewGroup, false);
            this.f2113f.mo54a(this.f2110c);
            mo724b(true);
        }
        return this.f2113f;
    }

    /* renamed from: a */
    public View mo999a(C0876j c0876j, View view, ViewGroup viewGroup) {
        C0077a b = view instanceof C0077a ? (C0077a) view : m4131b(viewGroup);
        mo1000a(c0876j, b);
        return (View) b;
    }

    /* renamed from: a */
    public void m4121a(int i) {
        this.f2117j = i;
    }

    /* renamed from: a */
    public void mo719a(Context context, C0873h c0873h) {
        this.f2109b = context;
        this.f2112e = LayoutInflater.from(this.f2109b);
        this.f2110c = c0873h;
    }

    /* renamed from: a */
    public void mo720a(C0873h c0873h, boolean z) {
        if (this.f2114g != null) {
            this.f2114g.mo657a(c0873h, z);
        }
    }

    /* renamed from: a */
    public abstract void mo1000a(C0876j c0876j, C0077a c0077a);

    /* renamed from: a */
    public void mo721a(C0794a c0794a) {
        this.f2114g = c0794a;
    }

    /* renamed from: a */
    protected void m4126a(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewGroup) this.f2113f).addView(view, i);
    }

    /* renamed from: a */
    public boolean mo1002a(int i, C0876j c0876j) {
        return true;
    }

    /* renamed from: a */
    public boolean mo722a(C0873h c0873h, C0876j c0876j) {
        return false;
    }

    /* renamed from: a */
    public boolean mo723a(C0890u c0890u) {
        return this.f2114g != null ? this.f2114g.mo658a(c0890u) : false;
    }

    /* renamed from: a */
    protected boolean mo1003a(ViewGroup viewGroup, int i) {
        viewGroup.removeViewAt(i);
        return true;
    }

    /* renamed from: b */
    public C0077a m4131b(ViewGroup viewGroup) {
        return (C0077a) this.f2111d.inflate(this.f2116i, viewGroup, false);
    }

    /* renamed from: b */
    public void mo724b(boolean z) {
        ViewGroup viewGroup = (ViewGroup) this.f2113f;
        if (viewGroup != null) {
            int i;
            if (this.f2110c != null) {
                this.f2110c.m4253j();
                ArrayList i2 = this.f2110c.m4252i();
                int size = i2.size();
                int i3 = 0;
                i = 0;
                while (i3 < size) {
                    int i4;
                    C0876j c0876j = (C0876j) i2.get(i3);
                    if (mo1002a(i, c0876j)) {
                        View childAt = viewGroup.getChildAt(i);
                        C0876j itemData = childAt instanceof C0077a ? ((C0077a) childAt).getItemData() : null;
                        View a = mo999a(c0876j, childAt, viewGroup);
                        if (c0876j != itemData) {
                            a.setPressed(false);
                            ah.m2836z(a);
                        }
                        if (a != childAt) {
                            m4126a(a, i);
                        }
                        i4 = i + 1;
                    } else {
                        i4 = i;
                    }
                    i3++;
                    i = i4;
                }
            } else {
                i = 0;
            }
            while (i < viewGroup.getChildCount()) {
                if (!mo1003a(viewGroup, i)) {
                    i++;
                }
            }
        }
    }

    /* renamed from: b */
    public boolean mo725b() {
        return false;
    }

    /* renamed from: b */
    public boolean mo726b(C0873h c0873h, C0876j c0876j) {
        return false;
    }
}
