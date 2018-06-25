package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ag;
import android.support.v4.view.C0081z;
import android.support.v4.view.C0365n;
import android.support.v4.view.C0636j;
import android.support.v4.view.ah;
import android.support.v4.view.as;
import android.support.v4.view.ax;
import android.support.v4.view.bc;
import android.support.v4.view.be;
import android.support.v4.widget.C0724s;
import android.support.v7.p025a.C0748a.C0738a;
import android.support.v7.p025a.C0748a.C0740c;
import android.support.v7.p025a.C0748a.C0743f;
import android.support.v7.p025a.C0748a.C0744g;
import android.support.v7.p025a.C0748a.C0746i;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.view.C0814b;
import android.support.v7.view.C0814b.C0797a;
import android.support.v7.view.C0844d;
import android.support.v7.view.C0845e;
import android.support.v7.view.menu.C0079p;
import android.support.v7.view.menu.C0859o.C0794a;
import android.support.v7.view.menu.C0871f;
import android.support.v7.view.menu.C0873h;
import android.support.v7.view.menu.C0873h.C0777a;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.C1069l;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.ContentFrameLayout.C0789a;
import android.support.v7.widget.ViewStubCompat;
import android.support.v7.widget.af;
import android.support.v7.widget.ak;
import android.support.v7.widget.ak.C0787a;
import android.support.v7.widget.bm;
import android.support.v7.widget.bp;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.xmlpull.v1.XmlPullParser;

@TargetApi(9)
/* renamed from: android.support.v7.app.k */
class C0778k extends C0773f implements C0365n, C0777a {
    /* renamed from: t */
    private static final boolean f1773t = (VERSION.SDK_INT < 21);
    /* renamed from: A */
    private View f1774A;
    /* renamed from: B */
    private boolean f1775B;
    /* renamed from: C */
    private boolean f1776C;
    /* renamed from: D */
    private boolean f1777D;
    /* renamed from: E */
    private C0800d[] f1778E;
    /* renamed from: F */
    private C0800d f1779F;
    /* renamed from: G */
    private boolean f1780G;
    /* renamed from: H */
    private final Runnable f1781H = new C07851(this);
    /* renamed from: I */
    private boolean f1782I;
    /* renamed from: J */
    private Rect f1783J;
    /* renamed from: K */
    private Rect f1784K;
    /* renamed from: L */
    private C0803m f1785L;
    /* renamed from: m */
    C0814b f1786m;
    /* renamed from: n */
    ActionBarContextView f1787n;
    /* renamed from: o */
    PopupWindow f1788o;
    /* renamed from: p */
    Runnable f1789p;
    /* renamed from: q */
    ax f1790q = null;
    /* renamed from: r */
    boolean f1791r;
    /* renamed from: s */
    int f1792s;
    /* renamed from: u */
    private af f1793u;
    /* renamed from: v */
    private C0795a f1794v;
    /* renamed from: w */
    private C0801e f1795w;
    /* renamed from: x */
    private boolean f1796x;
    /* renamed from: y */
    private ViewGroup f1797y;
    /* renamed from: z */
    private TextView f1798z;

    /* renamed from: android.support.v7.app.k$1 */
    class C07851 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0778k f1810a;

        C07851(C0778k c0778k) {
            this.f1810a = c0778k;
        }

        public void run() {
            if ((this.f1810a.f1792s & 1) != 0) {
                this.f1810a.m3756f(0);
            }
            if ((this.f1810a.f1792s & 4096) != 0) {
                this.f1810a.m3756f(108);
            }
            this.f1810a.f1791r = false;
            this.f1810a.f1792s = 0;
        }
    }

    /* renamed from: android.support.v7.app.k$2 */
    class C07862 implements C0081z {
        /* renamed from: a */
        final /* synthetic */ C0778k f1811a;

        C07862(C0778k c0778k) {
            this.f1811a = c0778k;
        }

        /* renamed from: a */
        public be mo57a(View view, be beVar) {
            int b = beVar.m3078b();
            int g = this.f1811a.m3757g(b);
            if (b != g) {
                beVar = beVar.m3077a(beVar.m3076a(), g, beVar.m3079c(), beVar.m3080d());
            }
            return ah.m2774a(view, beVar);
        }
    }

    /* renamed from: android.support.v7.app.k$3 */
    class C07883 implements C0787a {
        /* renamed from: a */
        final /* synthetic */ C0778k f1812a;

        C07883(C0778k c0778k) {
            this.f1812a = c0778k;
        }

        /* renamed from: a */
        public void mo654a(Rect rect) {
            rect.top = this.f1812a.m3757g(rect.top);
        }
    }

    /* renamed from: android.support.v7.app.k$4 */
    class C07904 implements C0789a {
        /* renamed from: a */
        final /* synthetic */ C0778k f1813a;

        C07904(C0778k c0778k) {
            this.f1813a = c0778k;
        }

        /* renamed from: a */
        public void mo655a() {
        }

        /* renamed from: b */
        public void mo656b() {
            this.f1813a.m3764v();
        }
    }

    /* renamed from: android.support.v7.app.k$5 */
    class C07925 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C0778k f1815a;

        /* renamed from: android.support.v7.app.k$5$1 */
        class C07911 extends bc {
            /* renamed from: a */
            final /* synthetic */ C07925 f1814a;

            C07911(C07925 c07925) {
                this.f1814a = c07925;
            }

            public void onAnimationEnd(View view) {
                ah.m2800c(this.f1814a.f1815a.f1787n, 1.0f);
                this.f1814a.f1815a.f1790q.m3022a(null);
                this.f1814a.f1815a.f1790q = null;
            }

            public void onAnimationStart(View view) {
                this.f1814a.f1815a.f1787n.setVisibility(0);
            }
        }

        C07925(C0778k c0778k) {
            this.f1815a = c0778k;
        }

        public void run() {
            this.f1815a.f1788o.showAtLocation(this.f1815a.f1787n, 55, 0, 0);
            this.f1815a.m3762t();
            if (this.f1815a.m3761s()) {
                ah.m2800c(this.f1815a.f1787n, (float) BitmapDescriptorFactory.HUE_RED);
                this.f1815a.f1790q = ah.m2827q(this.f1815a.f1787n).m3020a(1.0f);
                this.f1815a.f1790q.m3022a(new C07911(this));
                return;
            }
            ah.m2800c(this.f1815a.f1787n, 1.0f);
            this.f1815a.f1787n.setVisibility(0);
        }
    }

    /* renamed from: android.support.v7.app.k$6 */
    class C07936 extends bc {
        /* renamed from: a */
        final /* synthetic */ C0778k f1816a;

        C07936(C0778k c0778k) {
            this.f1816a = c0778k;
        }

        public void onAnimationEnd(View view) {
            ah.m2800c(this.f1816a.f1787n, 1.0f);
            this.f1816a.f1790q.m3022a(null);
            this.f1816a.f1790q = null;
        }

        public void onAnimationStart(View view) {
            this.f1816a.f1787n.setVisibility(0);
            this.f1816a.f1787n.sendAccessibilityEvent(32);
            if (this.f1816a.f1787n.getParent() instanceof View) {
                ah.m2834x((View) this.f1816a.f1787n.getParent());
            }
        }
    }

    /* renamed from: android.support.v7.app.k$a */
    private final class C0795a implements C0794a {
        /* renamed from: a */
        final /* synthetic */ C0778k f1817a;

        C0795a(C0778k c0778k) {
            this.f1817a = c0778k;
        }

        /* renamed from: a */
        public void mo657a(C0873h c0873h, boolean z) {
            this.f1817a.m3744b(c0873h);
        }

        /* renamed from: a */
        public boolean mo658a(C0873h c0873h) {
            Callback q = this.f1817a.m3702q();
            if (q != null) {
                q.onMenuOpened(108, c0873h);
            }
            return true;
        }
    }

    /* renamed from: android.support.v7.app.k$b */
    class C0798b implements C0797a {
        /* renamed from: a */
        final /* synthetic */ C0778k f1819a;
        /* renamed from: b */
        private C0797a f1820b;

        /* renamed from: android.support.v7.app.k$b$1 */
        class C07961 extends bc {
            /* renamed from: a */
            final /* synthetic */ C0798b f1818a;

            C07961(C0798b c0798b) {
                this.f1818a = c0798b;
            }

            public void onAnimationEnd(View view) {
                this.f1818a.f1819a.f1787n.setVisibility(8);
                if (this.f1818a.f1819a.f1788o != null) {
                    this.f1818a.f1819a.f1788o.dismiss();
                } else if (this.f1818a.f1819a.f1787n.getParent() instanceof View) {
                    ah.m2834x((View) this.f1818a.f1819a.f1787n.getParent());
                }
                this.f1818a.f1819a.f1787n.removeAllViews();
                this.f1818a.f1819a.f1790q.m3022a(null);
                this.f1818a.f1819a.f1790q = null;
            }
        }

        public C0798b(C0778k c0778k, C0797a c0797a) {
            this.f1819a = c0778k;
            this.f1820b = c0797a;
        }

        /* renamed from: a */
        public void mo659a(C0814b c0814b) {
            this.f1820b.mo659a(c0814b);
            if (this.f1819a.f1788o != null) {
                this.f1819a.b.getDecorView().removeCallbacks(this.f1819a.f1789p);
            }
            if (this.f1819a.f1787n != null) {
                this.f1819a.m3762t();
                this.f1819a.f1790q = ah.m2827q(this.f1819a.f1787n).m3020a((float) BitmapDescriptorFactory.HUE_RED);
                this.f1819a.f1790q.m3022a(new C07961(this));
            }
            if (this.f1819a.e != null) {
                this.f1819a.e.mo135b(this.f1819a.f1786m);
            }
            this.f1819a.f1786m = null;
        }

        /* renamed from: a */
        public boolean mo660a(C0814b c0814b, Menu menu) {
            return this.f1820b.mo660a(c0814b, menu);
        }

        /* renamed from: a */
        public boolean mo661a(C0814b c0814b, MenuItem menuItem) {
            return this.f1820b.mo661a(c0814b, menuItem);
        }

        /* renamed from: b */
        public boolean mo662b(C0814b c0814b, Menu menu) {
            return this.f1820b.mo662b(c0814b, menu);
        }
    }

    /* renamed from: android.support.v7.app.k$c */
    private class C0799c extends ContentFrameLayout {
        /* renamed from: a */
        final /* synthetic */ C0778k f1829a;

        public C0799c(C0778k c0778k, Context context) {
            this.f1829a = c0778k;
            super(context);
        }

        /* renamed from: a */
        private boolean m3807a(int i, int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return this.f1829a.mo639a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !m3807a((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            this.f1829a.m3754e(0);
            return true;
        }

        public void setBackgroundResource(int i) {
            setBackgroundDrawable(C0825b.m3939b(getContext(), i));
        }
    }

    /* renamed from: android.support.v7.app.k$d */
    protected static final class C0800d {
        /* renamed from: a */
        int f1830a;
        /* renamed from: b */
        int f1831b;
        /* renamed from: c */
        int f1832c;
        /* renamed from: d */
        int f1833d;
        /* renamed from: e */
        int f1834e;
        /* renamed from: f */
        int f1835f;
        /* renamed from: g */
        ViewGroup f1836g;
        /* renamed from: h */
        View f1837h;
        /* renamed from: i */
        View f1838i;
        /* renamed from: j */
        C0873h f1839j;
        /* renamed from: k */
        C0871f f1840k;
        /* renamed from: l */
        Context f1841l;
        /* renamed from: m */
        boolean f1842m;
        /* renamed from: n */
        boolean f1843n;
        /* renamed from: o */
        boolean f1844o;
        /* renamed from: p */
        public boolean f1845p;
        /* renamed from: q */
        boolean f1846q = false;
        /* renamed from: r */
        boolean f1847r;
        /* renamed from: s */
        Bundle f1848s;

        C0800d(int i) {
            this.f1830a = i;
        }

        /* renamed from: a */
        C0079p m3808a(C0794a c0794a) {
            if (this.f1839j == null) {
                return null;
            }
            if (this.f1840k == null) {
                this.f1840k = new C0871f(this.f1841l, C0744g.abc_list_menu_item_layout);
                this.f1840k.mo721a(c0794a);
                this.f1839j.m4226a(this.f1840k);
            }
            return this.f1840k.m4193a(this.f1836g);
        }

        /* renamed from: a */
        void m3809a(Context context) {
            TypedValue typedValue = new TypedValue();
            Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(C0738a.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            }
            newTheme.resolveAttribute(C0738a.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            } else {
                newTheme.applyStyle(C0746i.Theme_AppCompat_CompactMenu, true);
            }
            Context c0844d = new C0844d(context, 0);
            c0844d.getTheme().setTo(newTheme);
            this.f1841l = c0844d;
            TypedArray obtainStyledAttributes = c0844d.obtainStyledAttributes(C0747j.AppCompatTheme);
            this.f1831b = obtainStyledAttributes.getResourceId(C0747j.AppCompatTheme_panelBackground, 0);
            this.f1835f = obtainStyledAttributes.getResourceId(C0747j.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        /* renamed from: a */
        void m3810a(C0873h c0873h) {
            if (c0873h != this.f1839j) {
                if (this.f1839j != null) {
                    this.f1839j.m4237b(this.f1840k);
                }
                this.f1839j = c0873h;
                if (c0873h != null && this.f1840k != null) {
                    c0873h.m4226a(this.f1840k);
                }
            }
        }

        /* renamed from: a */
        public boolean m3811a() {
            return this.f1837h == null ? false : this.f1838i != null || this.f1840k.m4194a().getCount() > 0;
        }
    }

    /* renamed from: android.support.v7.app.k$e */
    private final class C0801e implements C0794a {
        /* renamed from: a */
        final /* synthetic */ C0778k f1849a;

        C0801e(C0778k c0778k) {
            this.f1849a = c0778k;
        }

        /* renamed from: a */
        public void mo657a(C0873h c0873h, boolean z) {
            Menu menu;
            Menu p = c0873h.mo763p();
            boolean z2 = p != c0873h;
            C0778k c0778k = this.f1849a;
            if (z2) {
                menu = p;
            }
            C0800d a = c0778k.m3724a(menu);
            if (a == null) {
                return;
            }
            if (z2) {
                this.f1849a.m3728a(a.f1830a, a, p);
                this.f1849a.m3732a(a, true);
                return;
            }
            this.f1849a.m3732a(a, z);
        }

        /* renamed from: a */
        public boolean mo658a(C0873h c0873h) {
            if (c0873h == null && this.f1849a.h) {
                Callback q = this.f1849a.m3702q();
                if (!(q == null || this.f1849a.m3701p())) {
                    q.onMenuOpened(108, c0873h);
                }
            }
            return true;
        }
    }

    C0778k(Context context, Window window, C0145d c0145d) {
        super(context, window, c0145d);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m3707a(android.support.v7.app.C0778k.C0800d r11, android.view.KeyEvent r12) {
        /*
        r10 = this;
        r1 = -1;
        r3 = 0;
        r9 = 1;
        r2 = -2;
        r0 = r11.f1844o;
        if (r0 != 0) goto L_0x000e;
    L_0x0008:
        r0 = r10.m3701p();
        if (r0 == 0) goto L_0x000f;
    L_0x000e:
        return;
    L_0x000f:
        r0 = r11.f1830a;
        if (r0 != 0) goto L_0x0034;
    L_0x0013:
        r4 = r10.a;
        r0 = r4.getResources();
        r0 = r0.getConfiguration();
        r0 = r0.screenLayout;
        r0 = r0 & 15;
        r5 = 4;
        if (r0 != r5) goto L_0x0048;
    L_0x0024:
        r0 = r9;
    L_0x0025:
        r4 = r4.getApplicationInfo();
        r4 = r4.targetSdkVersion;
        r5 = 11;
        if (r4 < r5) goto L_0x004a;
    L_0x002f:
        r4 = r9;
    L_0x0030:
        if (r0 == 0) goto L_0x0034;
    L_0x0032:
        if (r4 != 0) goto L_0x000e;
    L_0x0034:
        r0 = r10.m3702q();
        if (r0 == 0) goto L_0x004c;
    L_0x003a:
        r4 = r11.f1830a;
        r5 = r11.f1839j;
        r0 = r0.onMenuOpened(r4, r5);
        if (r0 != 0) goto L_0x004c;
    L_0x0044:
        r10.m3732a(r11, r9);
        goto L_0x000e;
    L_0x0048:
        r0 = r3;
        goto L_0x0025;
    L_0x004a:
        r4 = r3;
        goto L_0x0030;
    L_0x004c:
        r0 = r10.a;
        r4 = "window";
        r0 = r0.getSystemService(r4);
        r8 = r0;
        r8 = (android.view.WindowManager) r8;
        if (r8 == 0) goto L_0x000e;
    L_0x005a:
        r0 = r10.m3713b(r11, r12);
        if (r0 == 0) goto L_0x000e;
    L_0x0060:
        r0 = r11.f1836g;
        if (r0 == 0) goto L_0x0068;
    L_0x0064:
        r0 = r11.f1846q;
        if (r0 == 0) goto L_0x00f2;
    L_0x0068:
        r0 = r11.f1836g;
        if (r0 != 0) goto L_0x00e0;
    L_0x006c:
        r0 = r10.m3709a(r11);
        if (r0 == 0) goto L_0x000e;
    L_0x0072:
        r0 = r11.f1836g;
        if (r0 == 0) goto L_0x000e;
    L_0x0076:
        r0 = r10.m3714c(r11);
        if (r0 == 0) goto L_0x000e;
    L_0x007c:
        r0 = r11.m3811a();
        if (r0 == 0) goto L_0x000e;
    L_0x0082:
        r0 = r11.f1837h;
        r0 = r0.getLayoutParams();
        if (r0 != 0) goto L_0x0104;
    L_0x008a:
        r0 = new android.view.ViewGroup$LayoutParams;
        r0.<init>(r2, r2);
        r1 = r0;
    L_0x0090:
        r0 = r11.f1831b;
        r4 = r11.f1836g;
        r4.setBackgroundResource(r0);
        r0 = r11.f1837h;
        r0 = r0.getParent();
        if (r0 == 0) goto L_0x00aa;
    L_0x009f:
        r4 = r0 instanceof android.view.ViewGroup;
        if (r4 == 0) goto L_0x00aa;
    L_0x00a3:
        r0 = (android.view.ViewGroup) r0;
        r4 = r11.f1837h;
        r0.removeView(r4);
    L_0x00aa:
        r0 = r11.f1836g;
        r4 = r11.f1837h;
        r0.addView(r4, r1);
        r0 = r11.f1837h;
        r0 = r0.hasFocus();
        if (r0 != 0) goto L_0x00be;
    L_0x00b9:
        r0 = r11.f1837h;
        r0.requestFocus();
    L_0x00be:
        r1 = r2;
    L_0x00bf:
        r11.f1843n = r3;
        r0 = new android.view.WindowManager$LayoutParams;
        r3 = r11.f1833d;
        r4 = r11.f1834e;
        r5 = 1002; // 0x3ea float:1.404E-42 double:4.95E-321;
        r6 = 8519680; // 0x820000 float:1.1938615E-38 double:4.209281E-317;
        r7 = -3;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r1 = r11.f1832c;
        r0.gravity = r1;
        r1 = r11.f1835f;
        r0.windowAnimations = r1;
        r1 = r11.f1836g;
        r8.addView(r1, r0);
        r11.f1844o = r9;
        goto L_0x000e;
    L_0x00e0:
        r0 = r11.f1846q;
        if (r0 == 0) goto L_0x0076;
    L_0x00e4:
        r0 = r11.f1836g;
        r0 = r0.getChildCount();
        if (r0 <= 0) goto L_0x0076;
    L_0x00ec:
        r0 = r11.f1836g;
        r0.removeAllViews();
        goto L_0x0076;
    L_0x00f2:
        r0 = r11.f1838i;
        if (r0 == 0) goto L_0x0102;
    L_0x00f6:
        r0 = r11.f1838i;
        r0 = r0.getLayoutParams();
        if (r0 == 0) goto L_0x0102;
    L_0x00fe:
        r0 = r0.width;
        if (r0 == r1) goto L_0x00bf;
    L_0x0102:
        r1 = r2;
        goto L_0x00bf;
    L_0x0104:
        r1 = r0;
        goto L_0x0090;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.k.a(android.support.v7.app.k$d, android.view.KeyEvent):void");
    }

    /* renamed from: a */
    private void m3708a(C0873h c0873h, boolean z) {
        if (this.f1793u == null || !this.f1793u.mo775e() || (as.m2934a(ViewConfiguration.get(this.a)) && !this.f1793u.mo777g())) {
            C0800d a = m3723a(0, true);
            a.f1846q = true;
            m3732a(a, false);
            m3707a(a, null);
            return;
        }
        Callback q = m3702q();
        if (this.f1793u.mo776f() && z) {
            this.f1793u.mo779i();
            if (!m3701p()) {
                q.onPanelClosed(108, m3723a(0, true).f1839j);
            }
        } else if (q != null && !m3701p()) {
            if (this.f1791r && (this.f1792s & 1) != 0) {
                this.b.getDecorView().removeCallbacks(this.f1781H);
                this.f1781H.run();
            }
            C0800d a2 = m3723a(0, true);
            if (a2.f1839j != null && !a2.f1847r && q.onPreparePanel(0, a2.f1838i, a2.f1839j)) {
                q.onMenuOpened(108, a2.f1839j);
                this.f1793u.mo778h();
            }
        }
    }

    /* renamed from: a */
    private boolean m3709a(C0800d c0800d) {
        c0800d.m3809a(m3699n());
        c0800d.f1836g = new C0799c(this, c0800d.f1841l);
        c0800d.f1832c = 81;
        return true;
    }

    /* renamed from: a */
    private boolean m3710a(C0800d c0800d, int i, KeyEvent keyEvent, int i2) {
        boolean z = false;
        if (!keyEvent.isSystem()) {
            if ((c0800d.f1842m || m3713b(c0800d, keyEvent)) && c0800d.f1839j != null) {
                z = c0800d.f1839j.performShortcut(i, keyEvent, i2);
            }
            if (z && (i2 & 1) == 0 && this.f1793u == null) {
                m3732a(c0800d, true);
            }
        }
        return z;
    }

    /* renamed from: a */
    private boolean m3711a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        ViewParent decorView = this.b.getDecorView();
        ViewParent viewParent2 = viewParent;
        while (viewParent2 != null) {
            if (viewParent2 == decorView || !(viewParent2 instanceof View) || ah.m2769I((View) viewParent2)) {
                return false;
            }
            viewParent2 = viewParent2.getParent();
        }
        return true;
    }

    /* renamed from: b */
    private boolean m3712b(C0800d c0800d) {
        Context c0844d;
        C0873h c0873h;
        Context context = this.a;
        if ((c0800d.f1830a == 0 || c0800d.f1830a == 108) && this.f1793u != null) {
            TypedValue typedValue = new TypedValue();
            Theme theme = context.getTheme();
            theme.resolveAttribute(C0738a.actionBarTheme, typedValue, true);
            Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(C0738a.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(C0738a.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            Theme theme3 = theme2;
            if (theme3 != null) {
                c0844d = new C0844d(context, 0);
                c0844d.getTheme().setTo(theme3);
                c0873h = new C0873h(c0844d);
                c0873h.mo757a((C0777a) this);
                c0800d.m3810a(c0873h);
                return true;
            }
        }
        c0844d = context;
        c0873h = new C0873h(c0844d);
        c0873h.mo757a((C0777a) this);
        c0800d.m3810a(c0873h);
        return true;
    }

    /* renamed from: b */
    private boolean m3713b(C0800d c0800d, KeyEvent keyEvent) {
        if (m3701p()) {
            return false;
        }
        if (c0800d.f1842m) {
            return true;
        }
        if (!(this.f1779F == null || this.f1779F == c0800d)) {
            m3732a(this.f1779F, false);
        }
        Callback q = m3702q();
        if (q != null) {
            c0800d.f1838i = q.onCreatePanelView(c0800d.f1830a);
        }
        boolean z = c0800d.f1830a == 0 || c0800d.f1830a == 108;
        if (z && this.f1793u != null) {
            this.f1793u.mo780j();
        }
        if (c0800d.f1838i == null && !(z && (m3698m() instanceof C0807o))) {
            if (c0800d.f1839j == null || c0800d.f1847r) {
                if (c0800d.f1839j == null && (!m3712b(c0800d) || c0800d.f1839j == null)) {
                    return false;
                }
                if (z && this.f1793u != null) {
                    if (this.f1794v == null) {
                        this.f1794v = new C0795a(this);
                    }
                    this.f1793u.mo774a(c0800d.f1839j, this.f1794v);
                }
                c0800d.f1839j.m4250g();
                if (q.onCreatePanelMenu(c0800d.f1830a, c0800d.f1839j)) {
                    c0800d.f1847r = false;
                } else {
                    c0800d.m3810a(null);
                    if (!z || this.f1793u == null) {
                        return false;
                    }
                    this.f1793u.mo774a(null, this.f1794v);
                    return false;
                }
            }
            c0800d.f1839j.m4250g();
            if (c0800d.f1848s != null) {
                c0800d.f1839j.m4235b(c0800d.f1848s);
                c0800d.f1848s = null;
            }
            if (q.onPreparePanel(0, c0800d.f1838i, c0800d.f1839j)) {
                c0800d.f1845p = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
                c0800d.f1839j.setQwertyMode(c0800d.f1845p);
                c0800d.f1839j.m4251h();
            } else {
                if (z && this.f1793u != null) {
                    this.f1793u.mo774a(null, this.f1794v);
                }
                c0800d.f1839j.m4251h();
                return false;
            }
        }
        c0800d.f1842m = true;
        c0800d.f1843n = false;
        this.f1779F = c0800d;
        return true;
    }

    /* renamed from: c */
    private boolean m3714c(C0800d c0800d) {
        if (c0800d.f1838i != null) {
            c0800d.f1837h = c0800d.f1838i;
            return true;
        } else if (c0800d.f1839j == null) {
            return false;
        } else {
            if (this.f1795w == null) {
                this.f1795w = new C0801e(this);
            }
            c0800d.f1837h = (View) c0800d.m3808a(this.f1795w);
            return c0800d.f1837h != null;
        }
    }

    /* renamed from: d */
    private void mo652d(int i) {
        this.f1792s |= 1 << i;
        if (!this.f1791r) {
            ah.m2787a(this.b.getDecorView(), this.f1781H);
            this.f1791r = true;
        }
    }

    /* renamed from: d */
    private boolean m3716d(int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            C0800d a = m3723a(i, true);
            if (!a.f1844o) {
                return m3713b(a, keyEvent);
            }
        }
        return false;
    }

    /* renamed from: e */
    private boolean m3717e(int i, KeyEvent keyEvent) {
        boolean z = true;
        if (this.f1786m != null) {
            return false;
        }
        C0800d a = m3723a(i, true);
        if (i != 0 || this.f1793u == null || !this.f1793u.mo775e() || as.m2934a(ViewConfiguration.get(this.a))) {
            boolean z2;
            if (a.f1844o || a.f1843n) {
                z2 = a.f1844o;
                m3732a(a, true);
                z = z2;
            } else {
                if (a.f1842m) {
                    if (a.f1847r) {
                        a.f1842m = false;
                        z2 = m3713b(a, keyEvent);
                    } else {
                        z2 = true;
                    }
                    if (z2) {
                        m3707a(a, keyEvent);
                    }
                }
                z = false;
            }
        } else if (this.f1793u.mo776f()) {
            z = this.f1793u.mo779i();
        } else {
            if (!m3701p() && m3713b(a, keyEvent)) {
                z = this.f1793u.mo778h();
            }
            z = false;
        }
        if (z) {
            AudioManager audioManager = (AudioManager) this.a.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return z;
    }

    /* renamed from: h */
    private int m3718h(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i != 9) {
            return i;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    /* renamed from: w */
    private void m3719w() {
        if (!this.f1796x) {
            this.f1797y = m3720x();
            CharSequence r = m3703r();
            if (!TextUtils.isEmpty(r)) {
                mo643b(r);
            }
            m3721y();
            m3736a(this.f1797y);
            this.f1796x = true;
            C0800d a = m3723a(0, false);
            if (!m3701p()) {
                if (a == null || a.f1839j == null) {
                    mo652d(108);
                }
            }
        }
    }

    /* renamed from: x */
    private ViewGroup m3720x() {
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(C0747j.AppCompatTheme);
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTheme_windowActionBar)) {
            View view;
            if (obtainStyledAttributes.getBoolean(C0747j.AppCompatTheme_windowNoTitle, false)) {
                mo645c(1);
            } else if (obtainStyledAttributes.getBoolean(C0747j.AppCompatTheme_windowActionBar, false)) {
                mo645c(108);
            }
            if (obtainStyledAttributes.getBoolean(C0747j.AppCompatTheme_windowActionBarOverlay, false)) {
                mo645c(109);
            }
            if (obtainStyledAttributes.getBoolean(C0747j.AppCompatTheme_windowActionModeOverlay, false)) {
                mo645c(10);
            }
            this.k = obtainStyledAttributes.getBoolean(C0747j.AppCompatTheme_android_windowIsFloating, false);
            obtainStyledAttributes.recycle();
            this.b.getDecorView();
            LayoutInflater from = LayoutInflater.from(this.a);
            if (this.l) {
                View view2 = this.j ? (ViewGroup) from.inflate(C0744g.abc_screen_simple_overlay_action_mode, null) : (ViewGroup) from.inflate(C0744g.abc_screen_simple, null);
                if (VERSION.SDK_INT >= 21) {
                    ah.m2785a(view2, new C07862(this));
                    view = view2;
                } else {
                    ((ak) view2).setOnFitSystemWindowsListener(new C07883(this));
                    view = view2;
                }
            } else if (this.k) {
                r0 = (ViewGroup) from.inflate(C0744g.abc_dialog_title_material, null);
                this.i = false;
                this.h = false;
                view = r0;
            } else if (this.h) {
                TypedValue typedValue = new TypedValue();
                this.a.getTheme().resolveAttribute(C0738a.actionBarTheme, typedValue, true);
                r0 = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new C0844d(this.a, typedValue.resourceId) : this.a).inflate(C0744g.abc_screen_toolbar, null);
                this.f1793u = (af) r0.findViewById(C0743f.decor_content_parent);
                this.f1793u.setWindowCallback(m3702q());
                if (this.i) {
                    this.f1793u.mo773a(109);
                }
                if (this.f1775B) {
                    this.f1793u.mo773a(2);
                }
                if (this.f1776C) {
                    this.f1793u.mo773a(5);
                }
                view = r0;
            } else {
                view = null;
            }
            if (view == null) {
                throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.h + ", windowActionBarOverlay: " + this.i + ", android:windowIsFloating: " + this.k + ", windowActionModeOverlay: " + this.j + ", windowNoTitle: " + this.l + " }");
            }
            if (this.f1793u == null) {
                this.f1798z = (TextView) view.findViewById(C0743f.title);
            }
            bp.m5748b(view);
            ContentFrameLayout contentFrameLayout = (ContentFrameLayout) view.findViewById(C0743f.action_bar_activity_content);
            ViewGroup viewGroup = (ViewGroup) this.b.findViewById(16908290);
            if (viewGroup != null) {
                while (viewGroup.getChildCount() > 0) {
                    View childAt = viewGroup.getChildAt(0);
                    viewGroup.removeViewAt(0);
                    contentFrameLayout.addView(childAt);
                }
                viewGroup.setId(-1);
                contentFrameLayout.setId(16908290);
                if (viewGroup instanceof FrameLayout) {
                    ((FrameLayout) viewGroup).setForeground(null);
                }
            }
            this.b.setContentView(view);
            contentFrameLayout.setAttachListener(new C07904(this));
            return view;
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    /* renamed from: y */
    private void m3721y() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.f1797y.findViewById(16908290);
        View decorView = this.b.getDecorView();
        contentFrameLayout.m3805a(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(C0747j.AppCompatTheme);
        obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(C0747j.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(C0747j.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    /* renamed from: z */
    private void m3722z() {
        if (this.f1796x) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    /* renamed from: a */
    protected C0800d m3723a(int i, boolean z) {
        Object obj = this.f1778E;
        if (obj == null || obj.length <= i) {
            Object obj2 = new C0800d[(i + 1)];
            if (obj != null) {
                System.arraycopy(obj, 0, obj2, 0, obj.length);
            }
            this.f1778E = obj2;
            obj = obj2;
        }
        C0800d c0800d = obj[i];
        if (c0800d != null) {
            return c0800d;
        }
        c0800d = new C0800d(i);
        obj[i] = c0800d;
        return c0800d;
    }

    /* renamed from: a */
    C0800d m3724a(Menu menu) {
        C0800d[] c0800dArr = this.f1778E;
        int length = c0800dArr != null ? c0800dArr.length : 0;
        for (int i = 0; i < length; i++) {
            C0800d c0800d = c0800dArr[i];
            if (c0800d != null && c0800d.f1839j == menu) {
                return c0800d;
            }
        }
        return null;
    }

    /* renamed from: a */
    C0814b mo629a(C0797a c0797a) {
        C0814b c0814b;
        m3762t();
        if (this.f1786m != null) {
            this.f1786m.mo687c();
        }
        if (!(c0797a instanceof C0798b)) {
            c0797a = new C0798b(this, c0797a);
        }
        if (this.e == null || m3701p()) {
            c0814b = null;
        } else {
            try {
                c0814b = this.e.mo133a(c0797a);
            } catch (AbstractMethodError e) {
                c0814b = null;
            }
        }
        if (c0814b != null) {
            this.f1786m = c0814b;
        } else {
            if (this.f1787n == null) {
                if (this.k) {
                    Context c0844d;
                    TypedValue typedValue = new TypedValue();
                    Theme theme = this.a.getTheme();
                    theme.resolveAttribute(C0738a.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Theme newTheme = this.a.getResources().newTheme();
                        newTheme.setTo(theme);
                        newTheme.applyStyle(typedValue.resourceId, true);
                        c0844d = new C0844d(this.a, 0);
                        c0844d.getTheme().setTo(newTheme);
                    } else {
                        c0844d = this.a;
                    }
                    this.f1787n = new ActionBarContextView(c0844d);
                    this.f1788o = new PopupWindow(c0844d, null, C0738a.actionModePopupWindowStyle);
                    C0724s.m3532a(this.f1788o, 2);
                    this.f1788o.setContentView(this.f1787n);
                    this.f1788o.setWidth(-1);
                    c0844d.getTheme().resolveAttribute(C0738a.actionBarSize, typedValue, true);
                    this.f1787n.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, c0844d.getResources().getDisplayMetrics()));
                    this.f1788o.setHeight(-2);
                    this.f1789p = new C07925(this);
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.f1797y.findViewById(C0743f.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(m3699n()));
                        this.f1787n = (ActionBarContextView) viewStubCompat.m5206a();
                    }
                }
            }
            if (this.f1787n != null) {
                m3762t();
                this.f1787n.m4369c();
                C0814b c0845e = new C0845e(this.f1787n.getContext(), this.f1787n, c0797a, this.f1788o == null);
                if (c0797a.mo660a(c0845e, c0845e.mo684b())) {
                    c0845e.mo688d();
                    this.f1787n.m4366a(c0845e);
                    this.f1786m = c0845e;
                    if (m3761s()) {
                        ah.m2800c(this.f1787n, (float) BitmapDescriptorFactory.HUE_RED);
                        this.f1790q = ah.m2827q(this.f1787n).m3020a(1.0f);
                        this.f1790q.m3022a(new C07936(this));
                    } else {
                        ah.m2800c(this.f1787n, 1.0f);
                        this.f1787n.setVisibility(0);
                        this.f1787n.sendAccessibilityEvent(32);
                        if (this.f1787n.getParent() instanceof View) {
                            ah.m2834x((View) this.f1787n.getParent());
                        }
                    }
                    if (this.f1788o != null) {
                        this.b.getDecorView().post(this.f1789p);
                    }
                } else {
                    this.f1786m = null;
                }
            }
        }
        if (!(this.f1786m == null || this.e == null)) {
            this.e.mo134a(this.f1786m);
        }
        return this.f1786m;
    }

    /* renamed from: a */
    public View mo630a(int i) {
        m3719w();
        return this.b.findViewById(i);
    }

    /* renamed from: a */
    public final View mo285a(View view, String str, Context context, AttributeSet attributeSet) {
        View b = mo650b(view, str, context, attributeSet);
        return b != null ? b : m3749c(view, str, context, attributeSet);
    }

    /* renamed from: a */
    void m3728a(int i, C0800d c0800d, Menu menu) {
        if (menu == null) {
            if (c0800d == null && i >= 0 && i < this.f1778E.length) {
                c0800d = this.f1778E[i];
            }
            if (c0800d != null) {
                menu = c0800d.f1839j;
            }
        }
        if ((c0800d == null || c0800d.f1844o) && !m3701p()) {
            this.c.onPanelClosed(i, menu);
        }
    }

    /* renamed from: a */
    void mo631a(int i, Menu menu) {
        if (i == 108) {
            C0765a a = mo618a();
            if (a != null) {
                a.mo673e(false);
            }
        } else if (i == 0) {
            C0800d a2 = m3723a(i, true);
            if (a2.f1844o) {
                m3732a(a2, false);
            }
        }
    }

    /* renamed from: a */
    public void mo632a(Configuration configuration) {
        if (this.h && this.f1796x) {
            C0765a a = mo618a();
            if (a != null) {
                a.mo665a(configuration);
            }
        }
        C1069l.m5865a().m5886a(this.a);
        mo625i();
    }

    /* renamed from: a */
    public void mo633a(Bundle bundle) {
        if ((this.c instanceof Activity) && ag.m1191b((Activity) this.c) != null) {
            C0765a m = m3698m();
            if (m == null) {
                this.f1782I = true;
            } else {
                m.mo671c(true);
            }
        }
    }

    /* renamed from: a */
    void m3732a(C0800d c0800d, boolean z) {
        if (z && c0800d.f1830a == 0 && this.f1793u != null && this.f1793u.mo776f()) {
            m3744b(c0800d.f1839j);
            return;
        }
        WindowManager windowManager = (WindowManager) this.a.getSystemService("window");
        if (!(windowManager == null || !c0800d.f1844o || c0800d.f1836g == null)) {
            windowManager.removeView(c0800d.f1836g);
            if (z) {
                m3728a(c0800d.f1830a, c0800d, null);
            }
        }
        c0800d.f1842m = false;
        c0800d.f1843n = false;
        c0800d.f1844o = false;
        c0800d.f1837h = null;
        c0800d.f1846q = true;
        if (this.f1779F == c0800d) {
            this.f1779F = null;
        }
    }

    /* renamed from: a */
    public void mo634a(C0873h c0873h) {
        m3708a(c0873h, true);
    }

    /* renamed from: a */
    public void mo635a(View view) {
        m3719w();
        ViewGroup viewGroup = (ViewGroup) this.f1797y.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.c.onContentChanged();
    }

    /* renamed from: a */
    public void mo636a(View view, LayoutParams layoutParams) {
        m3719w();
        ViewGroup viewGroup = (ViewGroup) this.f1797y.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.c.onContentChanged();
    }

    /* renamed from: a */
    void m3736a(ViewGroup viewGroup) {
    }

    /* renamed from: a */
    boolean mo637a(int i, KeyEvent keyEvent) {
        C0765a a = mo618a();
        if (a != null && a.mo668a(i, keyEvent)) {
            return true;
        }
        if (this.f1779F == null || !m3710a(this.f1779F, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.f1779F == null) {
                C0800d a2 = m3723a(0, true);
                m3713b(a2, keyEvent);
                boolean a3 = m3710a(a2, keyEvent.getKeyCode(), keyEvent, 1);
                a2.f1842m = false;
                if (a3) {
                    return true;
                }
            }
            return false;
        } else if (this.f1779F == null) {
            return true;
        } else {
            this.f1779F.f1843n = true;
            return true;
        }
    }

    /* renamed from: a */
    public boolean mo638a(C0873h c0873h, MenuItem menuItem) {
        Callback q = m3702q();
        if (!(q == null || m3701p())) {
            C0800d a = m3724a(c0873h.mo763p());
            if (a != null) {
                return q.onMenuItemSelected(a.f1830a, menuItem);
            }
        }
        return false;
    }

    /* renamed from: a */
    boolean mo639a(KeyEvent keyEvent) {
        boolean z = true;
        if (keyEvent.getKeyCode() == 82 && this.c.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z = false;
        }
        return z ? m3751c(keyCode, keyEvent) : m3747b(keyCode, keyEvent);
    }

    /* renamed from: b */
    public C0814b m3740b(C0797a c0797a) {
        if (c0797a == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.f1786m != null) {
            this.f1786m.mo687c();
        }
        C0797a c0798b = new C0798b(this, c0797a);
        C0765a a = mo618a();
        if (a != null) {
            this.f1786m = a.mo693a(c0798b);
            if (!(this.f1786m == null || this.e == null)) {
                this.e.mo134a(this.f1786m);
            }
        }
        if (this.f1786m == null) {
            this.f1786m = mo629a(c0798b);
        }
        return this.f1786m;
    }

    /* renamed from: b */
    View mo650b(View view, String str, Context context, AttributeSet attributeSet) {
        if (this.c instanceof Factory) {
            View onCreateView = ((Factory) this.c).onCreateView(str, context, attributeSet);
            if (onCreateView != null) {
                return onCreateView;
            }
        }
        return null;
    }

    /* renamed from: b */
    public void mo640b(int i) {
        m3719w();
        ViewGroup viewGroup = (ViewGroup) this.f1797y.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.a).inflate(i, viewGroup);
        this.c.onContentChanged();
    }

    /* renamed from: b */
    public void mo641b(Bundle bundle) {
        m3719w();
    }

    /* renamed from: b */
    void m3744b(C0873h c0873h) {
        if (!this.f1777D) {
            this.f1777D = true;
            this.f1793u.mo781k();
            Callback q = m3702q();
            if (!(q == null || m3701p())) {
                q.onPanelClosed(108, c0873h);
            }
            this.f1777D = false;
        }
    }

    /* renamed from: b */
    public void mo642b(View view, LayoutParams layoutParams) {
        m3719w();
        ((ViewGroup) this.f1797y.findViewById(16908290)).addView(view, layoutParams);
        this.c.onContentChanged();
    }

    /* renamed from: b */
    void mo643b(CharSequence charSequence) {
        if (this.f1793u != null) {
            this.f1793u.setWindowTitle(charSequence);
        } else if (m3698m() != null) {
            m3698m().mo666a(charSequence);
        } else if (this.f1798z != null) {
            this.f1798z.setText(charSequence);
        }
    }

    /* renamed from: b */
    boolean m3747b(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                boolean z = this.f1780G;
                this.f1780G = false;
                C0800d a = m3723a(0, false);
                if (a == null || !a.f1844o) {
                    if (m3763u()) {
                        return true;
                    }
                } else if (z) {
                    return true;
                } else {
                    m3732a(a, true);
                    return true;
                }
                break;
            case 82:
                m3717e(0, keyEvent);
                return true;
        }
        return false;
    }

    /* renamed from: b */
    boolean mo644b(int i, Menu menu) {
        if (i != 108) {
            return false;
        }
        C0765a a = mo618a();
        if (a == null) {
            return true;
        }
        a.mo673e(true);
        return true;
    }

    /* renamed from: c */
    public View m3749c(View view, String str, Context context, AttributeSet attributeSet) {
        boolean z;
        if (this.f1785L == null) {
            this.f1785L = new C0803m();
        }
        if (f1773t) {
            boolean a = attributeSet instanceof XmlPullParser ? ((XmlPullParser) attributeSet).getDepth() > 1 : m3711a((ViewParent) view);
            z = a;
        } else {
            z = false;
        }
        return this.f1785L.m3819a(view, str, context, attributeSet, z, f1773t, true, bm.m5716a());
    }

    /* renamed from: c */
    public boolean mo645c(int i) {
        int h = m3718h(i);
        if (this.l && h == 108) {
            return false;
        }
        if (this.h && h == 1) {
            this.h = false;
        }
        switch (h) {
            case 1:
                m3722z();
                this.l = true;
                return true;
            case 2:
                m3722z();
                this.f1775B = true;
                return true;
            case 5:
                m3722z();
                this.f1776C = true;
                return true;
            case 10:
                m3722z();
                this.j = true;
                return true;
            case 108:
                m3722z();
                this.h = true;
                return true;
            case 109:
                m3722z();
                this.i = true;
                return true;
            default:
                return this.b.requestFeature(h);
        }
    }

    /* renamed from: c */
    boolean m3751c(int i, KeyEvent keyEvent) {
        boolean z = true;
        switch (i) {
            case 4:
                if ((keyEvent.getFlags() & 128) == 0) {
                    z = false;
                }
                this.f1780G = z;
                break;
            case 82:
                m3716d(0, keyEvent);
                return true;
        }
        if (VERSION.SDK_INT < 11) {
            mo637a(i, keyEvent);
        }
        return false;
    }

    /* renamed from: d */
    public void mo623d() {
        C0765a a = mo618a();
        if (a != null) {
            a.mo672d(false);
        }
    }

    /* renamed from: e */
    public void mo646e() {
        C0765a a = mo618a();
        if (a != null) {
            a.mo672d(true);
        }
    }

    /* renamed from: e */
    void m3754e(int i) {
        m3732a(m3723a(i, true), true);
    }

    /* renamed from: f */
    public void mo647f() {
        C0765a a = mo618a();
        if (a == null || !a.mo674e()) {
            mo652d(0);
        }
    }

    /* renamed from: f */
    void m3756f(int i) {
        C0800d a = m3723a(i, true);
        if (a.f1839j != null) {
            Bundle bundle = new Bundle();
            a.f1839j.m4223a(bundle);
            if (bundle.size() > 0) {
                a.f1848s = bundle;
            }
            a.f1839j.m4250g();
            a.f1839j.clear();
        }
        a.f1847r = true;
        a.f1846q = true;
        if ((i == 108 || i == 0) && this.f1793u != null) {
            a = m3723a(0, false);
            if (a != null) {
                a.f1842m = false;
                m3713b(a, null);
            }
        }
    }

    /* renamed from: g */
    int m3757g(int i) {
        int i2;
        int i3 = 1;
        int i4 = 0;
        if (this.f1787n == null || !(this.f1787n.getLayoutParams() instanceof MarginLayoutParams)) {
            i2 = 0;
        } else {
            int i5;
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.f1787n.getLayoutParams();
            if (this.f1787n.isShown()) {
                if (this.f1783J == null) {
                    this.f1783J = new Rect();
                    this.f1784K = new Rect();
                }
                Rect rect = this.f1783J;
                Rect rect2 = this.f1784K;
                rect.set(0, i, 0, 0);
                bp.m5746a(this.f1797y, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i;
                    if (this.f1774A == null) {
                        this.f1774A = new View(this.a);
                        this.f1774A.setBackgroundColor(this.a.getResources().getColor(C0740c.abc_input_method_navigation_guard));
                        this.f1797y.addView(this.f1774A, -1, new LayoutParams(-1, i));
                        i5 = 1;
                    } else {
                        LayoutParams layoutParams = this.f1774A.getLayoutParams();
                        if (layoutParams.height != i) {
                            layoutParams.height = i;
                            this.f1774A.setLayoutParams(layoutParams);
                        }
                        i5 = 1;
                    }
                } else {
                    i5 = 0;
                }
                if (this.f1774A == null) {
                    i3 = 0;
                }
                if (!(this.j || i3 == 0)) {
                    i = 0;
                }
                int i6 = i5;
                i5 = i3;
                i3 = i6;
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                i5 = 0;
            } else {
                i3 = 0;
                i5 = 0;
            }
            if (i3 != 0) {
                this.f1787n.setLayoutParams(marginLayoutParams);
            }
            i2 = i5;
        }
        if (this.f1774A != null) {
            View view = this.f1774A;
            if (i2 == 0) {
                i4 = 8;
            }
            view.setVisibility(i4);
        }
        return i;
    }

    /* renamed from: g */
    public void mo624g() {
        if (this.f1791r) {
            this.b.getDecorView().removeCallbacks(this.f1781H);
        }
        super.mo624g();
        if (this.f != null) {
            this.f.mo677h();
        }
    }

    /* renamed from: h */
    public void mo648h() {
        LayoutInflater from = LayoutInflater.from(this.a);
        if (from.getFactory() == null) {
            C0636j.m3150a(from, this);
        } else if (!(C0636j.m3149a(from) instanceof C0778k)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    /* renamed from: l */
    public void mo649l() {
        m3719w();
        if (this.h && this.f == null) {
            if (this.c instanceof Activity) {
                this.f = new C0817r((Activity) this.c, this.i);
            } else if (this.c instanceof Dialog) {
                this.f = new C0817r((Dialog) this.c);
            }
            if (this.f != null) {
                this.f.mo671c(this.f1782I);
            }
        }
    }

    /* renamed from: s */
    final boolean m3761s() {
        return this.f1796x && this.f1797y != null && ah.m2767G(this.f1797y);
    }

    /* renamed from: t */
    void m3762t() {
        if (this.f1790q != null) {
            this.f1790q.m3027b();
        }
    }

    /* renamed from: u */
    boolean m3763u() {
        if (this.f1786m != null) {
            this.f1786m.mo687c();
            return true;
        }
        C0765a a = mo618a();
        return a != null && a.mo675f();
    }

    /* renamed from: v */
    void m3764v() {
        if (this.f1793u != null) {
            this.f1793u.mo781k();
        }
        if (this.f1788o != null) {
            this.b.getDecorView().removeCallbacks(this.f1789p);
            if (this.f1788o.isShowing()) {
                try {
                    this.f1788o.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.f1788o = null;
        }
        m3762t();
        C0800d a = m3723a(0, false);
        if (a != null && a.f1839j != null) {
            a.f1839j.close();
        }
    }
}
