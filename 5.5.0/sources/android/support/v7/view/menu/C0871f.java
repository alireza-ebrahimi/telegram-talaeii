package android.support.v7.view.menu;

import android.content.Context;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.view.menu.C0079p.C0077a;
import android.support.v7.view.menu.C0859o.C0794a;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;

/* renamed from: android.support.v7.view.menu.f */
public class C0871f implements C0859o, OnItemClickListener {
    /* renamed from: a */
    Context f2159a;
    /* renamed from: b */
    LayoutInflater f2160b;
    /* renamed from: c */
    C0873h f2161c;
    /* renamed from: d */
    ExpandedMenuView f2162d;
    /* renamed from: e */
    int f2163e;
    /* renamed from: f */
    int f2164f;
    /* renamed from: g */
    int f2165g;
    /* renamed from: h */
    C0870a f2166h;
    /* renamed from: i */
    private C0794a f2167i;

    /* renamed from: android.support.v7.view.menu.f$a */
    private class C0870a extends BaseAdapter {
        /* renamed from: a */
        final /* synthetic */ C0871f f2157a;
        /* renamed from: b */
        private int f2158b = -1;

        public C0870a(C0871f c0871f) {
            this.f2157a = c0871f;
            m4192a();
        }

        /* renamed from: a */
        public C0876j m4191a(int i) {
            ArrayList l = this.f2157a.f2161c.m4255l();
            int i2 = this.f2157a.f2163e + i;
            if (this.f2158b >= 0 && i2 >= this.f2158b) {
                i2++;
            }
            return (C0876j) l.get(i2);
        }

        /* renamed from: a */
        void m4192a() {
            C0876j r = this.f2157a.f2161c.m4261r();
            if (r != null) {
                ArrayList l = this.f2157a.f2161c.m4255l();
                int size = l.size();
                for (int i = 0; i < size; i++) {
                    if (((C0876j) l.get(i)) == r) {
                        this.f2158b = i;
                        return;
                    }
                }
            }
            this.f2158b = -1;
        }

        public int getCount() {
            int size = this.f2157a.f2161c.m4255l().size() - this.f2157a.f2163e;
            return this.f2158b < 0 ? size : size - 1;
        }

        public /* synthetic */ Object getItem(int i) {
            return m4191a(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = view == null ? this.f2157a.f2160b.inflate(this.f2157a.f2165g, viewGroup, false) : view;
            ((C0077a) inflate).mo43a(m4191a(i), 0);
            return inflate;
        }

        public void notifyDataSetChanged() {
            m4192a();
            super.notifyDataSetChanged();
        }
    }

    public C0871f(int i, int i2) {
        this.f2165g = i;
        this.f2164f = i2;
    }

    public C0871f(Context context, int i) {
        this(i, 0);
        this.f2159a = context;
        this.f2160b = LayoutInflater.from(this.f2159a);
    }

    /* renamed from: a */
    public C0079p m4193a(ViewGroup viewGroup) {
        if (this.f2162d == null) {
            this.f2162d = (ExpandedMenuView) this.f2160b.inflate(C0744g.abc_expanded_menu_layout, viewGroup, false);
            if (this.f2166h == null) {
                this.f2166h = new C0870a(this);
            }
            this.f2162d.setAdapter(this.f2166h);
            this.f2162d.setOnItemClickListener(this);
        }
        return this.f2162d;
    }

    /* renamed from: a */
    public ListAdapter m4194a() {
        if (this.f2166h == null) {
            this.f2166h = new C0870a(this);
        }
        return this.f2166h;
    }

    /* renamed from: a */
    public void mo719a(Context context, C0873h c0873h) {
        if (this.f2164f != 0) {
            this.f2159a = new ContextThemeWrapper(context, this.f2164f);
            this.f2160b = LayoutInflater.from(this.f2159a);
        } else if (this.f2159a != null) {
            this.f2159a = context;
            if (this.f2160b == null) {
                this.f2160b = LayoutInflater.from(this.f2159a);
            }
        }
        this.f2161c = c0873h;
        if (this.f2166h != null) {
            this.f2166h.notifyDataSetChanged();
        }
    }

    /* renamed from: a */
    public void mo720a(C0873h c0873h, boolean z) {
        if (this.f2167i != null) {
            this.f2167i.mo657a(c0873h, z);
        }
    }

    /* renamed from: a */
    public void mo721a(C0794a c0794a) {
        this.f2167i = c0794a;
    }

    /* renamed from: a */
    public boolean mo722a(C0873h c0873h, C0876j c0876j) {
        return false;
    }

    /* renamed from: a */
    public boolean mo723a(C0890u c0890u) {
        if (!c0890u.hasVisibleItems()) {
            return false;
        }
        new C0874i(c0890u).m4263a(null);
        if (this.f2167i != null) {
            this.f2167i.mo658a(c0890u);
        }
        return true;
    }

    /* renamed from: b */
    public void mo724b(boolean z) {
        if (this.f2166h != null) {
            this.f2166h.notifyDataSetChanged();
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

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.f2161c.m4233a(this.f2166h.m4191a(i), (C0859o) this, 0);
    }
}
