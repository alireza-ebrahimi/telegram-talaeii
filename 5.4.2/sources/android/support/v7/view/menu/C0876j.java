package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.p018c.p019a.C0394b;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0616d.C0615b;
import android.support.v4.view.C0652q.C0651e;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.view.menu.C0079p.C0077a;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.LinearLayout;

/* renamed from: android.support.v7.view.menu.j */
public final class C0876j implements C0394b {
    /* renamed from: w */
    private static String f2205w;
    /* renamed from: x */
    private static String f2206x;
    /* renamed from: y */
    private static String f2207y;
    /* renamed from: z */
    private static String f2208z;
    /* renamed from: a */
    C0873h f2209a;
    /* renamed from: b */
    private final int f2210b;
    /* renamed from: c */
    private final int f2211c;
    /* renamed from: d */
    private final int f2212d;
    /* renamed from: e */
    private final int f2213e;
    /* renamed from: f */
    private CharSequence f2214f;
    /* renamed from: g */
    private CharSequence f2215g;
    /* renamed from: h */
    private Intent f2216h;
    /* renamed from: i */
    private char f2217i;
    /* renamed from: j */
    private char f2218j;
    /* renamed from: k */
    private Drawable f2219k;
    /* renamed from: l */
    private int f2220l = 0;
    /* renamed from: m */
    private C0890u f2221m;
    /* renamed from: n */
    private Runnable f2222n;
    /* renamed from: o */
    private OnMenuItemClickListener f2223o;
    /* renamed from: p */
    private int f2224p = 16;
    /* renamed from: q */
    private int f2225q = 0;
    /* renamed from: r */
    private View f2226r;
    /* renamed from: s */
    private C0616d f2227s;
    /* renamed from: t */
    private C0651e f2228t;
    /* renamed from: u */
    private boolean f2229u = false;
    /* renamed from: v */
    private ContextMenuInfo f2230v;

    /* renamed from: android.support.v7.view.menu.j$1 */
    class C08751 implements C0615b {
        /* renamed from: a */
        final /* synthetic */ C0876j f2204a;

        C08751(C0876j c0876j) {
            this.f2204a = c0876j;
        }

        /* renamed from: a */
        public void mo742a(boolean z) {
            this.f2204a.f2209a.m4225a(this.f2204a);
        }
    }

    C0876j(C0873h c0873h, int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        this.f2209a = c0873h;
        this.f2210b = i2;
        this.f2211c = i;
        this.f2212d = i3;
        this.f2213e = i4;
        this.f2214f = charSequence;
        this.f2225q = i5;
    }

    /* renamed from: a */
    public C0394b m4267a(int i) {
        Context e = this.f2209a.m4247e();
        m4270a(LayoutInflater.from(e).inflate(i, new LinearLayout(e), false));
        return this;
    }

    /* renamed from: a */
    public C0394b mo708a(C0616d c0616d) {
        if (this.f2227s != null) {
            this.f2227s.m3103f();
        }
        this.f2226r = null;
        this.f2227s = c0616d;
        this.f2209a.m4238b(true);
        if (this.f2227s != null) {
            this.f2227s.mo752a(new C08751(this));
        }
        return this;
    }

    /* renamed from: a */
    public C0394b mo709a(C0651e c0651e) {
        this.f2228t = c0651e;
        return this;
    }

    /* renamed from: a */
    public C0394b m4270a(View view) {
        this.f2226r = view;
        this.f2227s = null;
        if (view != null && view.getId() == -1 && this.f2210b > 0) {
            view.setId(this.f2210b);
        }
        this.f2209a.m4236b(this);
        return this;
    }

    /* renamed from: a */
    public C0616d mo710a() {
        return this.f2227s;
    }

    /* renamed from: a */
    CharSequence m4272a(C0077a c0077a) {
        return (c0077a == null || !c0077a.mo44a()) ? getTitle() : getTitleCondensed();
    }

    /* renamed from: a */
    public void m4273a(C0890u c0890u) {
        this.f2221m = c0890u;
        c0890u.setHeaderTitle(getTitle());
    }

    /* renamed from: a */
    void m4274a(ContextMenuInfo contextMenuInfo) {
        this.f2230v = contextMenuInfo;
    }

    /* renamed from: a */
    public void m4275a(boolean z) {
        this.f2224p = (z ? 4 : 0) | (this.f2224p & -5);
    }

    /* renamed from: b */
    public C0394b m4276b(int i) {
        setShowAsAction(i);
        return this;
    }

    /* renamed from: b */
    void m4277b(boolean z) {
        int i = this.f2224p;
        this.f2224p = (z ? 2 : 0) | (this.f2224p & -3);
        if (i != this.f2224p) {
            this.f2209a.m4238b(false);
        }
    }

    /* renamed from: b */
    public boolean m4278b() {
        if ((this.f2223o != null && this.f2223o.onMenuItemClick(this)) || this.f2209a.mo758a(this.f2209a.mo763p(), (MenuItem) this)) {
            return true;
        }
        if (this.f2222n != null) {
            this.f2222n.run();
            return true;
        }
        if (this.f2216h != null) {
            try {
                this.f2209a.m4247e().startActivity(this.f2216h);
                return true;
            } catch (Throwable e) {
                Log.e("MenuItemImpl", "Can't find activity to handle intent; ignoring", e);
            }
        }
        return this.f2227s != null && this.f2227s.mo745d();
    }

    /* renamed from: c */
    public int m4279c() {
        return this.f2213e;
    }

    /* renamed from: c */
    boolean m4280c(boolean z) {
        int i = this.f2224p;
        this.f2224p = (z ? 0 : 8) | (this.f2224p & -9);
        return i != this.f2224p;
    }

    public boolean collapseActionView() {
        return (this.f2225q & 8) == 0 ? false : this.f2226r == null ? true : (this.f2228t == null || this.f2228t.mo750b(this)) ? this.f2209a.mo762d(this) : false;
    }

    /* renamed from: d */
    char m4281d() {
        return this.f2209a.mo759b() ? this.f2218j : this.f2217i;
    }

    /* renamed from: d */
    public void m4282d(boolean z) {
        if (z) {
            this.f2224p |= 32;
        } else {
            this.f2224p &= -33;
        }
    }

    /* renamed from: e */
    String m4283e() {
        char d = m4281d();
        if (d == '\u0000') {
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        StringBuilder stringBuilder = new StringBuilder(f2205w);
        switch (d) {
            case '\b':
                stringBuilder.append(f2207y);
                break;
            case '\n':
                stringBuilder.append(f2206x);
                break;
            case ' ':
                stringBuilder.append(f2208z);
                break;
            default:
                stringBuilder.append(d);
                break;
        }
        return stringBuilder.toString();
    }

    /* renamed from: e */
    public void m4284e(boolean z) {
        this.f2229u = z;
        this.f2209a.m4238b(false);
    }

    public boolean expandActionView() {
        return !m4293n() ? false : (this.f2228t == null || this.f2228t.mo749a(this)) ? this.f2209a.mo761c(this) : false;
    }

    /* renamed from: f */
    boolean m4285f() {
        return this.f2209a.mo760c() && m4281d() != '\u0000';
    }

    /* renamed from: g */
    public boolean m4286g() {
        return (this.f2224p & 4) != 0;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    public View getActionView() {
        if (this.f2226r != null) {
            return this.f2226r;
        }
        if (this.f2227s == null) {
            return null;
        }
        this.f2226r = this.f2227s.mo751a((MenuItem) this);
        return this.f2226r;
    }

    public char getAlphabeticShortcut() {
        return this.f2218j;
    }

    public int getGroupId() {
        return this.f2211c;
    }

    public Drawable getIcon() {
        if (this.f2219k != null) {
            return this.f2219k;
        }
        if (this.f2220l == 0) {
            return null;
        }
        Drawable b = C0825b.m3939b(this.f2209a.m4247e(), this.f2220l);
        this.f2220l = 0;
        this.f2219k = b;
        return b;
    }

    public Intent getIntent() {
        return this.f2216h;
    }

    @CapturedViewProperty
    public int getItemId() {
        return this.f2210b;
    }

    public ContextMenuInfo getMenuInfo() {
        return this.f2230v;
    }

    public char getNumericShortcut() {
        return this.f2217i;
    }

    public int getOrder() {
        return this.f2212d;
    }

    public SubMenu getSubMenu() {
        return this.f2221m;
    }

    @CapturedViewProperty
    public CharSequence getTitle() {
        return this.f2214f;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f2215g != null ? this.f2215g : this.f2214f;
        return (VERSION.SDK_INT >= 18 || charSequence == null || (charSequence instanceof String)) ? charSequence : charSequence.toString();
    }

    /* renamed from: h */
    public void m4287h() {
        this.f2209a.m4236b(this);
    }

    public boolean hasSubMenu() {
        return this.f2221m != null;
    }

    /* renamed from: i */
    public boolean m4288i() {
        return this.f2209a.m4260q();
    }

    public boolean isActionViewExpanded() {
        return this.f2229u;
    }

    public boolean isCheckable() {
        return (this.f2224p & 1) == 1;
    }

    public boolean isChecked() {
        return (this.f2224p & 2) == 2;
    }

    public boolean isEnabled() {
        return (this.f2224p & 16) != 0;
    }

    public boolean isVisible() {
        return (this.f2227s == null || !this.f2227s.mo753b()) ? (this.f2224p & 8) == 0 : (this.f2224p & 8) == 0 && this.f2227s.mo754c();
    }

    /* renamed from: j */
    public boolean m4289j() {
        return (this.f2224p & 32) == 32;
    }

    /* renamed from: k */
    public boolean m4290k() {
        return (this.f2225q & 1) == 1;
    }

    /* renamed from: l */
    public boolean m4291l() {
        return (this.f2225q & 2) == 2;
    }

    /* renamed from: m */
    public boolean m4292m() {
        return (this.f2225q & 4) == 4;
    }

    /* renamed from: n */
    public boolean m4293n() {
        if ((this.f2225q & 8) == 0) {
            return false;
        }
        if (this.f2226r == null && this.f2227s != null) {
            this.f2226r = this.f2227s.mo751a((MenuItem) this);
        }
        return this.f2226r != null;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    public /* synthetic */ MenuItem setActionView(int i) {
        return m4267a(i);
    }

    public /* synthetic */ MenuItem setActionView(View view) {
        return m4270a(view);
    }

    public MenuItem setAlphabeticShortcut(char c) {
        if (this.f2218j != c) {
            this.f2218j = Character.toLowerCase(c);
            this.f2209a.m4238b(false);
        }
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        int i = this.f2224p;
        this.f2224p = (z ? 1 : 0) | (this.f2224p & -2);
        if (i != this.f2224p) {
            this.f2209a.m4238b(false);
        }
        return this;
    }

    public MenuItem setChecked(boolean z) {
        if ((this.f2224p & 4) != 0) {
            this.f2209a.m4228a((MenuItem) this);
        } else {
            m4277b(z);
        }
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        if (z) {
            this.f2224p |= 16;
        } else {
            this.f2224p &= -17;
        }
        this.f2209a.m4238b(false);
        return this;
    }

    public MenuItem setIcon(int i) {
        this.f2219k = null;
        this.f2220l = i;
        this.f2209a.m4238b(false);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.f2220l = 0;
        this.f2219k = drawable;
        this.f2209a.m4238b(false);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.f2216h = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c) {
        if (this.f2217i != c) {
            this.f2217i = c;
            this.f2209a.m4238b(false);
        }
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setOnActionExpandListener()");
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.f2223o = onMenuItemClickListener;
        return this;
    }

    public MenuItem setShortcut(char c, char c2) {
        this.f2217i = c;
        this.f2218j = Character.toLowerCase(c2);
        this.f2209a.m4238b(false);
        return this;
    }

    public void setShowAsAction(int i) {
        switch (i & 3) {
            case 0:
            case 1:
            case 2:
                this.f2225q = i;
                this.f2209a.m4236b(this);
                return;
            default:
                throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
    }

    public /* synthetic */ MenuItem setShowAsActionFlags(int i) {
        return m4276b(i);
    }

    public MenuItem setTitle(int i) {
        return setTitle(this.f2209a.m4247e().getString(i));
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.f2214f = charSequence;
        this.f2209a.m4238b(false);
        if (this.f2221m != null) {
            this.f2221m.setHeaderTitle(charSequence);
        }
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f2215g = charSequence;
        if (charSequence == null) {
            CharSequence charSequence2 = this.f2214f;
        }
        this.f2209a.m4238b(false);
        return this;
    }

    public MenuItem setVisible(boolean z) {
        if (m4280c(z)) {
            this.f2209a.m4225a(this);
        }
        return this;
    }

    public String toString() {
        return this.f2214f != null ? this.f2214f.toString() : null;
    }
}
