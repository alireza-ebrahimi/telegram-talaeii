package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.C0625f;
import android.support.v4.view.ah;
import android.support.v7.p025a.C0748a.C0741d;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.widget.ar;
import android.support.v7.widget.as;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* renamed from: android.support.v7.view.menu.e */
final class C0869e extends C0868m implements C0859o, OnKeyListener, OnDismissListener {
    /* renamed from: a */
    final Handler f2132a;
    /* renamed from: b */
    final List<C0866a> f2133b = new ArrayList();
    /* renamed from: c */
    View f2134c;
    /* renamed from: d */
    boolean f2135d;
    /* renamed from: e */
    private final Context f2136e;
    /* renamed from: f */
    private final int f2137f;
    /* renamed from: g */
    private final int f2138g;
    /* renamed from: h */
    private final int f2139h;
    /* renamed from: i */
    private final boolean f2140i;
    /* renamed from: j */
    private final List<C0873h> f2141j = new LinkedList();
    /* renamed from: k */
    private final OnGlobalLayoutListener f2142k = new C08631(this);
    /* renamed from: l */
    private final ar f2143l = new C08652(this);
    /* renamed from: m */
    private int f2144m = 0;
    /* renamed from: n */
    private int f2145n = 0;
    /* renamed from: o */
    private View f2146o;
    /* renamed from: p */
    private int f2147p;
    /* renamed from: q */
    private boolean f2148q;
    /* renamed from: r */
    private boolean f2149r;
    /* renamed from: s */
    private int f2150s;
    /* renamed from: t */
    private int f2151t;
    /* renamed from: u */
    private boolean f2152u;
    /* renamed from: v */
    private boolean f2153v;
    /* renamed from: w */
    private C0794a f2154w;
    /* renamed from: x */
    private ViewTreeObserver f2155x;
    /* renamed from: y */
    private OnDismissListener f2156y;

    /* renamed from: android.support.v7.view.menu.e$1 */
    class C08631 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ C0869e f2122a;

        C08631(C0869e c0869e) {
            this.f2122a = c0869e;
        }

        public void onGlobalLayout() {
            if (this.f2122a.mo739d() && this.f2122a.f2133b.size() > 0 && !((C0866a) this.f2122a.f2133b.get(0)).f2128a.m5489g()) {
                View view = this.f2122a.f2134c;
                if (view == null || !view.isShown()) {
                    this.f2122a.mo736c();
                    return;
                }
                for (C0866a c0866a : this.f2122a.f2133b) {
                    c0866a.f2128a.mo729a();
                }
            }
        }
    }

    /* renamed from: android.support.v7.view.menu.e$2 */
    class C08652 implements ar {
        /* renamed from: a */
        final /* synthetic */ C0869e f2127a;

        C08652(C0869e c0869e) {
            this.f2127a = c0869e;
        }

        /* renamed from: a */
        public void mo727a(C0873h c0873h, MenuItem menuItem) {
            this.f2127a.f2132a.removeCallbacksAndMessages(c0873h);
        }

        /* renamed from: b */
        public void mo728b(final C0873h c0873h, final MenuItem menuItem) {
            int i;
            this.f2127a.f2132a.removeCallbacksAndMessages(null);
            int size = this.f2127a.f2133b.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (c0873h == ((C0866a) this.f2127a.f2133b.get(i2)).f2129b) {
                    i = i2;
                    break;
                }
            }
            i = -1;
            if (i != -1) {
                i++;
                final C0866a c0866a = i < this.f2127a.f2133b.size() ? (C0866a) this.f2127a.f2133b.get(i) : null;
                this.f2127a.f2132a.postAtTime(new Runnable(this) {
                    /* renamed from: d */
                    final /* synthetic */ C08652 f2126d;

                    public void run() {
                        if (c0866a != null) {
                            this.f2126d.f2127a.f2135d = true;
                            c0866a.f2129b.m4230a(false);
                            this.f2126d.f2127a.f2135d = false;
                        }
                        if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                            c0873h.m4232a(menuItem, 4);
                        }
                    }
                }, c0873h, SystemClock.uptimeMillis() + 200);
            }
        }
    }

    /* renamed from: android.support.v7.view.menu.e$a */
    private static class C0866a {
        /* renamed from: a */
        public final as f2128a;
        /* renamed from: b */
        public final C0873h f2129b;
        /* renamed from: c */
        public final int f2130c;

        public C0866a(as asVar, C0873h c0873h, int i) {
            this.f2128a = asVar;
            this.f2129b = c0873h;
            this.f2130c = i;
        }

        /* renamed from: a */
        public ListView m4144a() {
            return this.f2128a.mo740e();
        }
    }

    public C0869e(Context context, View view, int i, int i2, boolean z) {
        this.f2136e = context;
        this.f2146o = view;
        this.f2138g = i;
        this.f2139h = i2;
        this.f2140i = z;
        this.f2152u = false;
        this.f2147p = m4172i();
        Resources resources = context.getResources();
        this.f2137f = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(C0741d.abc_config_prefDialogWidth));
        this.f2132a = new Handler();
    }

    /* renamed from: a */
    private MenuItem m4166a(C0873h c0873h, C0873h c0873h2) {
        int size = c0873h.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = c0873h.getItem(i);
            if (item.hasSubMenu() && c0873h2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    /* renamed from: a */
    private View m4167a(C0866a c0866a, C0873h c0873h) {
        int i = 0;
        MenuItem a = m4166a(c0866a.f2129b, c0873h);
        if (a == null) {
            return null;
        }
        int headersCount;
        C0872g c0872g;
        int i2;
        ListView a2 = c0866a.m4144a();
        ListAdapter adapter = a2.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            headersCount = headerViewListAdapter.getHeadersCount();
            c0872g = (C0872g) headerViewListAdapter.getWrappedAdapter();
        } else {
            c0872g = (C0872g) adapter;
            headersCount = 0;
        }
        int count = c0872g.getCount();
        while (i < count) {
            if (a == c0872g.m4204a(i)) {
                i2 = i;
                break;
            }
            i++;
        }
        i2 = -1;
        if (i2 == -1) {
            return null;
        }
        i2 = (i2 + headersCount) - a2.getFirstVisiblePosition();
        return (i2 < 0 || i2 >= a2.getChildCount()) ? null : a2.getChildAt(i2);
    }

    /* renamed from: c */
    private void m4168c(C0873h c0873h) {
        View a;
        C0866a c0866a;
        LayoutInflater from = LayoutInflater.from(this.f2136e);
        Object c0872g = new C0872g(c0873h, from, this.f2140i);
        if (!mo739d() && this.f2152u) {
            c0872g.m4205a(true);
        } else if (mo739d()) {
            c0872g.m4205a(C0868m.m4151b(c0873h));
        }
        int a2 = C0868m.m4149a(c0872g, null, this.f2136e, this.f2137f);
        as h = m4171h();
        h.mo1012a((ListAdapter) c0872g);
        h.m5488g(a2);
        h.m5486e(this.f2145n);
        if (this.f2133b.size() > 0) {
            C0866a c0866a2 = (C0866a) this.f2133b.get(this.f2133b.size() - 1);
            a = m4167a(c0866a2, c0873h);
            c0866a = c0866a2;
        } else {
            a = null;
            c0866a = null;
        }
        if (a != null) {
            h.mo943b(false);
            h.m5504a(null);
            int d = m4169d(a2);
            boolean z = d == 1;
            this.f2147p = d;
            int[] iArr = new int[2];
            a.getLocationInWindow(iArr);
            int j = c0866a.f2128a.m5494j() + iArr[0];
            int k = iArr[1] + c0866a.f2128a.m5495k();
            int width = (this.f2145n & 5) == 5 ? z ? j + a2 : j - a.getWidth() : z ? a.getWidth() + j : j - a2;
            h.m5482c(width);
            h.m5483d(k);
        } else {
            if (this.f2148q) {
                h.m5482c(this.f2150s);
            }
            if (this.f2149r) {
                h.m5483d(this.f2151t);
            }
            h.m5473a(m4165g());
        }
        this.f2133b.add(new C0866a(h, c0873h, this.f2147p));
        h.mo729a();
        if (c0866a == null && this.f2153v && c0873h.m4256m() != null) {
            ViewGroup e = h.mo740e();
            FrameLayout frameLayout = (FrameLayout) from.inflate(C0744g.abc_popup_menu_header_item_layout, e, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            frameLayout.setEnabled(false);
            textView.setText(c0873h.m4256m());
            e.addHeaderView(frameLayout, null, false);
            h.mo729a();
        }
    }

    /* renamed from: d */
    private int m4169d(int i) {
        ListView a = ((C0866a) this.f2133b.get(this.f2133b.size() - 1)).m4144a();
        int[] iArr = new int[2];
        a.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.f2134c.getWindowVisibleDisplayFrame(rect);
        if (this.f2147p != 1) {
            return iArr[0] - i < 0 ? 1 : 0;
        } else {
            return (a.getWidth() + iArr[0]) + i > rect.right ? 0 : 1;
        }
    }

    /* renamed from: d */
    private int m4170d(C0873h c0873h) {
        int size = this.f2133b.size();
        for (int i = 0; i < size; i++) {
            if (c0873h == ((C0866a) this.f2133b.get(i)).f2129b) {
                return i;
            }
        }
        return -1;
    }

    /* renamed from: h */
    private as m4171h() {
        as asVar = new as(this.f2136e, null, this.f2138g, this.f2139h);
        asVar.m5503a(this.f2143l);
        asVar.m5475a((OnItemClickListener) this);
        asVar.m5477a((OnDismissListener) this);
        asVar.m5480b(this.f2146o);
        asVar.m5486e(this.f2145n);
        asVar.m5478a(true);
        return asVar;
    }

    /* renamed from: i */
    private int m4172i() {
        return ah.m2812g(this.f2146o) == 1 ? 0 : 1;
    }

    /* renamed from: a */
    public void mo729a() {
        if (!mo739d()) {
            for (C0873h c : this.f2141j) {
                m4168c(c);
            }
            this.f2141j.clear();
            this.f2134c = this.f2146o;
            if (this.f2134c != null) {
                Object obj = this.f2155x == null ? 1 : null;
                this.f2155x = this.f2134c.getViewTreeObserver();
                if (obj != null) {
                    this.f2155x.addOnGlobalLayoutListener(this.f2142k);
                }
            }
        }
    }

    /* renamed from: a */
    public void mo730a(int i) {
        if (this.f2144m != i) {
            this.f2144m = i;
            this.f2145n = C0625f.m3120a(i, ah.m2812g(this.f2146o));
        }
    }

    /* renamed from: a */
    public void mo731a(C0873h c0873h) {
        c0873h.m4227a((C0859o) this, this.f2136e);
        if (mo739d()) {
            m4168c(c0873h);
        } else {
            this.f2141j.add(c0873h);
        }
    }

    /* renamed from: a */
    public void mo720a(C0873h c0873h, boolean z) {
        int d = m4170d(c0873h);
        if (d >= 0) {
            int i = d + 1;
            if (i < this.f2133b.size()) {
                ((C0866a) this.f2133b.get(i)).f2129b.m4230a(false);
            }
            C0866a c0866a = (C0866a) this.f2133b.remove(d);
            c0866a.f2129b.m4237b((C0859o) this);
            if (this.f2135d) {
                c0866a.f2128a.m5506b(null);
                c0866a.f2128a.m5479b(0);
            }
            c0866a.f2128a.mo736c();
            d = this.f2133b.size();
            if (d > 0) {
                this.f2147p = ((C0866a) this.f2133b.get(d - 1)).f2130c;
            } else {
                this.f2147p = m4172i();
            }
            if (d == 0) {
                mo736c();
                if (this.f2154w != null) {
                    this.f2154w.mo657a(c0873h, true);
                }
                if (this.f2155x != null) {
                    if (this.f2155x.isAlive()) {
                        this.f2155x.removeGlobalOnLayoutListener(this.f2142k);
                    }
                    this.f2155x = null;
                }
                this.f2156y.onDismiss();
            } else if (z) {
                ((C0866a) this.f2133b.get(0)).f2129b.m4230a(false);
            }
        }
    }

    /* renamed from: a */
    public void mo721a(C0794a c0794a) {
        this.f2154w = c0794a;
    }

    /* renamed from: a */
    public void mo732a(View view) {
        if (this.f2146o != view) {
            this.f2146o = view;
            this.f2145n = C0625f.m3120a(this.f2144m, ah.m2812g(this.f2146o));
        }
    }

    /* renamed from: a */
    public void mo733a(OnDismissListener onDismissListener) {
        this.f2156y = onDismissListener;
    }

    /* renamed from: a */
    public void mo734a(boolean z) {
        this.f2152u = z;
    }

    /* renamed from: a */
    public boolean mo723a(C0890u c0890u) {
        for (C0866a c0866a : this.f2133b) {
            if (c0890u == c0866a.f2129b) {
                c0866a.m4144a().requestFocus();
                return true;
            }
        }
        if (!c0890u.hasVisibleItems()) {
            return false;
        }
        mo731a((C0873h) c0890u);
        if (this.f2154w != null) {
            this.f2154w.mo658a(c0890u);
        }
        return true;
    }

    /* renamed from: b */
    public void mo735b(int i) {
        this.f2148q = true;
        this.f2150s = i;
    }

    /* renamed from: b */
    public void mo724b(boolean z) {
        for (C0866a a : this.f2133b) {
            C0868m.m4150a(a.m4144a().getAdapter()).notifyDataSetChanged();
        }
    }

    /* renamed from: b */
    public boolean mo725b() {
        return false;
    }

    /* renamed from: c */
    public void mo736c() {
        int size = this.f2133b.size();
        if (size > 0) {
            C0866a[] c0866aArr = (C0866a[]) this.f2133b.toArray(new C0866a[size]);
            for (size--; size >= 0; size--) {
                C0866a c0866a = c0866aArr[size];
                if (c0866a.f2128a.mo739d()) {
                    c0866a.f2128a.mo736c();
                }
            }
        }
    }

    /* renamed from: c */
    public void mo737c(int i) {
        this.f2149r = true;
        this.f2151t = i;
    }

    /* renamed from: c */
    public void mo738c(boolean z) {
        this.f2153v = z;
    }

    /* renamed from: d */
    public boolean mo739d() {
        return this.f2133b.size() > 0 && ((C0866a) this.f2133b.get(0)).f2128a.mo739d();
    }

    /* renamed from: e */
    public ListView mo740e() {
        return this.f2133b.isEmpty() ? null : ((C0866a) this.f2133b.get(this.f2133b.size() - 1)).m4144a();
    }

    /* renamed from: f */
    protected boolean mo741f() {
        return false;
    }

    public void onDismiss() {
        C0866a c0866a;
        int size = this.f2133b.size();
        for (int i = 0; i < size; i++) {
            c0866a = (C0866a) this.f2133b.get(i);
            if (!c0866a.f2128a.mo739d()) {
                break;
            }
        }
        c0866a = null;
        if (c0866a != null) {
            c0866a.f2129b.m4230a(false);
        }
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        mo736c();
        return true;
    }
}
