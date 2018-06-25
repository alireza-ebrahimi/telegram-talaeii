package android.support.v7.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.support.v4.p018c.p019a.C0393a;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0652q;
import android.support.v7.p025a.C0748a.C0739b;
import android.util.SparseArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: android.support.v7.view.menu.h */
public class C0873h implements C0393a {
    /* renamed from: d */
    private static final int[] f2174d = new int[]{1, 4, 5, 3, 2, 0};
    /* renamed from: a */
    CharSequence f2175a;
    /* renamed from: b */
    Drawable f2176b;
    /* renamed from: c */
    View f2177c;
    /* renamed from: e */
    private final Context f2178e;
    /* renamed from: f */
    private final Resources f2179f;
    /* renamed from: g */
    private boolean f2180g;
    /* renamed from: h */
    private boolean f2181h;
    /* renamed from: i */
    private C0777a f2182i;
    /* renamed from: j */
    private ArrayList<C0876j> f2183j;
    /* renamed from: k */
    private ArrayList<C0876j> f2184k;
    /* renamed from: l */
    private boolean f2185l;
    /* renamed from: m */
    private ArrayList<C0876j> f2186m;
    /* renamed from: n */
    private ArrayList<C0876j> f2187n;
    /* renamed from: o */
    private boolean f2188o;
    /* renamed from: p */
    private int f2189p = 0;
    /* renamed from: q */
    private ContextMenuInfo f2190q;
    /* renamed from: r */
    private boolean f2191r = false;
    /* renamed from: s */
    private boolean f2192s = false;
    /* renamed from: t */
    private boolean f2193t = false;
    /* renamed from: u */
    private boolean f2194u = false;
    /* renamed from: v */
    private boolean f2195v = false;
    /* renamed from: w */
    private ArrayList<C0876j> f2196w = new ArrayList();
    /* renamed from: x */
    private CopyOnWriteArrayList<WeakReference<C0859o>> f2197x = new CopyOnWriteArrayList();
    /* renamed from: y */
    private C0876j f2198y;
    /* renamed from: z */
    private boolean f2199z;

    /* renamed from: android.support.v7.view.menu.h$a */
    public interface C0777a {
        /* renamed from: a */
        void mo634a(C0873h c0873h);

        /* renamed from: a */
        boolean mo638a(C0873h c0873h, MenuItem menuItem);
    }

    /* renamed from: android.support.v7.view.menu.h$b */
    public interface C0857b {
        /* renamed from: a */
        boolean mo707a(C0876j c0876j);
    }

    public C0873h(Context context) {
        this.f2178e = context;
        this.f2179f = context.getResources();
        this.f2183j = new ArrayList();
        this.f2184k = new ArrayList();
        this.f2185l = true;
        this.f2186m = new ArrayList();
        this.f2187n = new ArrayList();
        this.f2188o = true;
        m4213e(true);
    }

    /* renamed from: a */
    private static int m4207a(ArrayList<C0876j> arrayList, int i) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((C0876j) arrayList.get(size)).m4279c() <= i) {
                return size + 1;
            }
        }
        return 0;
    }

    /* renamed from: a */
    private C0876j m4208a(int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        return new C0876j(this, i, i2, i3, i4, charSequence, i5);
    }

    /* renamed from: a */
    private void m4209a(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources d = m4244d();
        if (view != null) {
            this.f2177c = view;
            this.f2175a = null;
            this.f2176b = null;
        } else {
            if (i > 0) {
                this.f2175a = d.getText(i);
            } else if (charSequence != null) {
                this.f2175a = charSequence;
            }
            if (i2 > 0) {
                this.f2176b = C0235a.m1066a(m4247e(), i2);
            } else if (drawable != null) {
                this.f2176b = drawable;
            }
            this.f2177c = null;
        }
        m4238b(false);
    }

    /* renamed from: a */
    private void m4210a(int i, boolean z) {
        if (i >= 0 && i < this.f2183j.size()) {
            this.f2183j.remove(i);
            if (z) {
                m4238b(true);
            }
        }
    }

    /* renamed from: a */
    private boolean m4211a(C0890u c0890u, C0859o c0859o) {
        boolean z = false;
        if (this.f2197x.isEmpty()) {
            return false;
        }
        if (c0859o != null) {
            z = c0859o.mo723a(c0890u);
        }
        Iterator it = this.f2197x.iterator();
        boolean z2 = z;
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            C0859o c0859o2 = (C0859o) weakReference.get();
            if (c0859o2 == null) {
                this.f2197x.remove(weakReference);
                z = z2;
            } else {
                z = !z2 ? c0859o2.mo723a(c0890u) : z2;
            }
            z2 = z;
        }
        return z2;
    }

    /* renamed from: d */
    private void m4212d(boolean z) {
        if (!this.f2197x.isEmpty()) {
            m4250g();
            Iterator it = this.f2197x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                C0859o c0859o = (C0859o) weakReference.get();
                if (c0859o == null) {
                    this.f2197x.remove(weakReference);
                } else {
                    c0859o.mo724b(z);
                }
            }
            m4251h();
        }
    }

    /* renamed from: e */
    private void m4213e(boolean z) {
        boolean z2 = true;
        if (!(z && this.f2179f.getConfiguration().keyboard != 1 && this.f2179f.getBoolean(C0739b.abc_config_showMenuShortcutsWhenKeyboardPresent))) {
            z2 = false;
        }
        this.f2181h = z2;
    }

    /* renamed from: f */
    private static int m4214f(int i) {
        int i2 = (-65536 & i) >> 16;
        if (i2 >= 0 && i2 < f2174d.length) {
            return (f2174d[i2] << 16) | (65535 & i);
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    /* renamed from: a */
    public int m4215a(int i, int i2) {
        int size = size();
        if (i2 < 0) {
            i2 = 0;
        }
        for (int i3 = i2; i3 < size; i3++) {
            if (((C0876j) this.f2183j.get(i3)).getGroupId() == i) {
                return i3;
            }
        }
        return -1;
    }

    /* renamed from: a */
    public C0873h m4216a(int i) {
        this.f2189p = i;
        return this;
    }

    /* renamed from: a */
    protected C0873h m4217a(Drawable drawable) {
        m4209a(0, null, 0, drawable, null);
        return this;
    }

    /* renamed from: a */
    protected C0873h m4218a(View view) {
        m4209a(0, null, 0, null, view);
        return this;
    }

    /* renamed from: a */
    protected C0873h m4219a(CharSequence charSequence) {
        m4209a(0, charSequence, 0, null, null);
        return this;
    }

    /* renamed from: a */
    C0876j m4220a(int i, KeyEvent keyEvent) {
        List list = this.f2196w;
        list.clear();
        m4229a(list, i, keyEvent);
        if (list.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyData keyData = new KeyData();
        keyEvent.getKeyData(keyData);
        int size = list.size();
        if (size == 1) {
            return (C0876j) list.get(0);
        }
        boolean b = mo759b();
        for (int i2 = 0; i2 < size; i2++) {
            C0876j c0876j = (C0876j) list.get(i2);
            char alphabeticShortcut = b ? c0876j.getAlphabeticShortcut() : c0876j.getNumericShortcut();
            if (alphabeticShortcut == keyData.meta[0] && (metaState & 2) == 0) {
                return c0876j;
            }
            if (alphabeticShortcut == keyData.meta[2] && (metaState & 2) != 0) {
                return c0876j;
            }
            if (b && alphabeticShortcut == '\b' && i == 67) {
                return c0876j;
            }
        }
        return null;
    }

    /* renamed from: a */
    protected MenuItem m4221a(int i, int i2, int i3, CharSequence charSequence) {
        int f = C0873h.m4214f(i3);
        MenuItem a = m4208a(i, i2, i3, f, charSequence, this.f2189p);
        if (this.f2190q != null) {
            a.m4274a(this.f2190q);
        }
        this.f2183j.add(C0873h.m4207a(this.f2183j, f), a);
        m4238b(true);
        return a;
    }

    /* renamed from: a */
    protected String mo756a() {
        return "android:menu:actionviewstates";
    }

    /* renamed from: a */
    public void m4223a(Bundle bundle) {
        int size = size();
        int i = 0;
        SparseArray sparseArray = null;
        while (i < size) {
            MenuItem item = getItem(i);
            View a = C0652q.m3190a(item);
            if (!(a == null || a.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                a.saveHierarchyState(sparseArray);
                if (C0652q.m3194c(item)) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            SparseArray sparseArray2 = sparseArray;
            if (item.hasSubMenu()) {
                ((C0890u) item.getSubMenu()).m4223a(bundle);
            }
            i++;
            sparseArray = sparseArray2;
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(mo756a(), sparseArray);
        }
    }

    /* renamed from: a */
    public void mo757a(C0777a c0777a) {
        this.f2182i = c0777a;
    }

    /* renamed from: a */
    void m4225a(C0876j c0876j) {
        this.f2185l = true;
        m4238b(true);
    }

    /* renamed from: a */
    public void m4226a(C0859o c0859o) {
        m4227a(c0859o, this.f2178e);
    }

    /* renamed from: a */
    public void m4227a(C0859o c0859o, Context context) {
        this.f2197x.add(new WeakReference(c0859o));
        c0859o.mo719a(context, this);
        this.f2188o = true;
    }

    /* renamed from: a */
    void m4228a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.f2183j.size();
        m4250g();
        for (int i = 0; i < size; i++) {
            MenuItem menuItem2 = (C0876j) this.f2183j.get(i);
            if (menuItem2.getGroupId() == groupId && menuItem2.m4286g() && menuItem2.isCheckable()) {
                menuItem2.m4277b(menuItem2 == menuItem);
            }
        }
        m4251h();
    }

    /* renamed from: a */
    void m4229a(List<C0876j> list, int i, KeyEvent keyEvent) {
        boolean b = mo759b();
        int metaState = keyEvent.getMetaState();
        KeyData keyData = new KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.f2183j.size();
            for (int i2 = 0; i2 < size; i2++) {
                C0876j c0876j = (C0876j) this.f2183j.get(i2);
                if (c0876j.hasSubMenu()) {
                    ((C0873h) c0876j.getSubMenu()).m4229a((List) list, i, keyEvent);
                }
                char alphabeticShortcut = b ? c0876j.getAlphabeticShortcut() : c0876j.getNumericShortcut();
                if ((metaState & 5) == 0 && alphabeticShortcut != '\u0000' && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (b && alphabeticShortcut == '\b' && i == 67)) && c0876j.isEnabled())) {
                    list.add(c0876j);
                }
            }
        }
    }

    /* renamed from: a */
    public final void m4230a(boolean z) {
        if (!this.f2195v) {
            this.f2195v = true;
            Iterator it = this.f2197x.iterator();
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                C0859o c0859o = (C0859o) weakReference.get();
                if (c0859o == null) {
                    this.f2197x.remove(weakReference);
                } else {
                    c0859o.mo720a(this, z);
                }
            }
            this.f2195v = false;
        }
    }

    /* renamed from: a */
    boolean mo758a(C0873h c0873h, MenuItem menuItem) {
        return this.f2182i != null && this.f2182i.mo638a(c0873h, menuItem);
    }

    /* renamed from: a */
    public boolean m4232a(MenuItem menuItem, int i) {
        return m4233a(menuItem, null, i);
    }

    /* renamed from: a */
    public boolean m4233a(MenuItem menuItem, C0859o c0859o, int i) {
        C0876j c0876j = (C0876j) menuItem;
        if (c0876j == null || !c0876j.isEnabled()) {
            return false;
        }
        boolean b = c0876j.m4278b();
        C0616d a = c0876j.mo710a();
        boolean z = a != null && a.mo746e();
        boolean expandActionView;
        if (c0876j.m4293n()) {
            expandActionView = c0876j.expandActionView() | b;
            if (!expandActionView) {
                return expandActionView;
            }
            m4230a(true);
            return expandActionView;
        } else if (c0876j.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                m4230a(false);
            }
            if (!c0876j.hasSubMenu()) {
                c0876j.m4273a(new C0890u(m4247e(), this, c0876j));
            }
            C0890u c0890u = (C0890u) c0876j.getSubMenu();
            if (z) {
                a.mo744a((SubMenu) c0890u);
            }
            expandActionView = m4211a(c0890u, c0859o) | b;
            if (expandActionView) {
                return expandActionView;
            }
            m4230a(true);
            return expandActionView;
        } else {
            if ((i & 1) == 0) {
                m4230a(true);
            }
            return b;
        }
    }

    public MenuItem add(int i) {
        return m4221a(0, 0, 0, this.f2179f.getString(i));
    }

    public MenuItem add(int i, int i2, int i3, int i4) {
        return m4221a(i, i2, i3, this.f2179f.getString(i4));
    }

    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return m4221a(i, i2, i3, charSequence);
    }

    public MenuItem add(CharSequence charSequence) {
        return m4221a(0, 0, 0, charSequence);
    }

    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        PackageManager packageManager = this.f2178e.getPackageManager();
        List queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i5 = 0; i5 < size; i5++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivityOptions.get(i5);
            Intent intent2 = new Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    public SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.f2179f.getString(i));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.f2179f.getString(i4));
    }

    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        C0876j c0876j = (C0876j) m4221a(i, i2, i3, charSequence);
        C0890u c0890u = new C0890u(this.f2178e, this, c0876j);
        c0876j.m4273a(c0890u);
        return c0890u;
    }

    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    /* renamed from: b */
    public int m4234b(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((C0876j) this.f2183j.get(i2)).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    /* renamed from: b */
    public void m4235b(Bundle bundle) {
        if (bundle != null) {
            MenuItem item;
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(mo756a());
            int size = size();
            for (int i = 0; i < size; i++) {
                item = getItem(i);
                View a = C0652q.m3190a(item);
                if (!(a == null || a.getId() == -1)) {
                    a.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((C0890u) item.getSubMenu()).m4235b(bundle);
                }
            }
            int i2 = bundle.getInt("android:menu:expandedactionview");
            if (i2 > 0) {
                item = findItem(i2);
                if (item != null) {
                    C0652q.m3193b(item);
                }
            }
        }
    }

    /* renamed from: b */
    void m4236b(C0876j c0876j) {
        this.f2188o = true;
        m4238b(true);
    }

    /* renamed from: b */
    public void m4237b(C0859o c0859o) {
        Iterator it = this.f2197x.iterator();
        while (it.hasNext()) {
            WeakReference weakReference = (WeakReference) it.next();
            C0859o c0859o2 = (C0859o) weakReference.get();
            if (c0859o2 == null || c0859o2 == c0859o) {
                this.f2197x.remove(weakReference);
            }
        }
    }

    /* renamed from: b */
    public void m4238b(boolean z) {
        if (this.f2191r) {
            this.f2192s = true;
            if (z) {
                this.f2193t = true;
                return;
            }
            return;
        }
        if (z) {
            this.f2185l = true;
            this.f2188o = true;
        }
        m4212d(z);
    }

    /* renamed from: b */
    boolean mo759b() {
        return this.f2180g;
    }

    /* renamed from: c */
    public int m4240c(int i) {
        return m4215a(i, 0);
    }

    /* renamed from: c */
    public void m4241c(boolean z) {
        this.f2199z = z;
    }

    /* renamed from: c */
    public boolean mo760c() {
        return this.f2181h;
    }

    /* renamed from: c */
    public boolean mo761c(C0876j c0876j) {
        boolean z = false;
        if (!this.f2197x.isEmpty()) {
            m4250g();
            Iterator it = this.f2197x.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                C0859o c0859o = (C0859o) weakReference.get();
                if (c0859o == null) {
                    this.f2197x.remove(weakReference);
                    z = z2;
                } else {
                    z = c0859o.mo722a(this, c0876j);
                    if (z) {
                        break;
                    }
                }
                z2 = z;
            }
            z = z2;
            m4251h();
            if (z) {
                this.f2198y = c0876j;
            }
        }
        return z;
    }

    public void clear() {
        if (this.f2198y != null) {
            mo762d(this.f2198y);
        }
        this.f2183j.clear();
        m4238b(true);
    }

    public void clearHeader() {
        this.f2176b = null;
        this.f2175a = null;
        this.f2177c = null;
        m4238b(false);
    }

    public void close() {
        m4230a(true);
    }

    /* renamed from: d */
    Resources m4244d() {
        return this.f2179f;
    }

    /* renamed from: d */
    protected C0873h m4245d(int i) {
        m4209a(i, null, 0, null, null);
        return this;
    }

    /* renamed from: d */
    public boolean mo762d(C0876j c0876j) {
        boolean z = false;
        if (!this.f2197x.isEmpty() && this.f2198y == c0876j) {
            m4250g();
            Iterator it = this.f2197x.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                WeakReference weakReference = (WeakReference) it.next();
                C0859o c0859o = (C0859o) weakReference.get();
                if (c0859o == null) {
                    this.f2197x.remove(weakReference);
                    z = z2;
                } else {
                    z = c0859o.mo726b(this, c0876j);
                    if (z) {
                        break;
                    }
                }
                z2 = z;
            }
            z = z2;
            m4251h();
            if (z) {
                this.f2198y = null;
            }
        }
        return z;
    }

    /* renamed from: e */
    public Context m4247e() {
        return this.f2178e;
    }

    /* renamed from: e */
    protected C0873h m4248e(int i) {
        m4209a(0, null, i, null, null);
        return this;
    }

    /* renamed from: f */
    public void m4249f() {
        if (this.f2182i != null) {
            this.f2182i.mo634a(this);
        }
    }

    public MenuItem findItem(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            C0876j c0876j = (C0876j) this.f2183j.get(i2);
            if (c0876j.getItemId() == i) {
                return c0876j;
            }
            if (c0876j.hasSubMenu()) {
                MenuItem findItem = c0876j.getSubMenu().findItem(i);
                if (findItem != null) {
                    return findItem;
                }
            }
        }
        return null;
    }

    /* renamed from: g */
    public void m4250g() {
        if (!this.f2191r) {
            this.f2191r = true;
            this.f2192s = false;
            this.f2193t = false;
        }
    }

    public MenuItem getItem(int i) {
        return (MenuItem) this.f2183j.get(i);
    }

    /* renamed from: h */
    public void m4251h() {
        this.f2191r = false;
        if (this.f2192s) {
            this.f2192s = false;
            m4238b(this.f2193t);
        }
    }

    public boolean hasVisibleItems() {
        if (this.f2199z) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (((C0876j) this.f2183j.get(i)).isVisible()) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: i */
    public ArrayList<C0876j> m4252i() {
        if (!this.f2185l) {
            return this.f2184k;
        }
        this.f2184k.clear();
        int size = this.f2183j.size();
        for (int i = 0; i < size; i++) {
            C0876j c0876j = (C0876j) this.f2183j.get(i);
            if (c0876j.isVisible()) {
                this.f2184k.add(c0876j);
            }
        }
        this.f2185l = false;
        this.f2188o = true;
        return this.f2184k;
    }

    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return m4220a(i, keyEvent) != null;
    }

    /* renamed from: j */
    public void m4253j() {
        ArrayList i = m4252i();
        if (this.f2188o) {
            Iterator it = this.f2197x.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                int i3;
                WeakReference weakReference = (WeakReference) it.next();
                C0859o c0859o = (C0859o) weakReference.get();
                if (c0859o == null) {
                    this.f2197x.remove(weakReference);
                    i3 = i2;
                } else {
                    i3 = c0859o.mo725b() | i2;
                }
                i2 = i3;
            }
            if (i2 != 0) {
                this.f2186m.clear();
                this.f2187n.clear();
                i2 = i.size();
                for (int i4 = 0; i4 < i2; i4++) {
                    C0876j c0876j = (C0876j) i.get(i4);
                    if (c0876j.m4289j()) {
                        this.f2186m.add(c0876j);
                    } else {
                        this.f2187n.add(c0876j);
                    }
                }
            } else {
                this.f2186m.clear();
                this.f2187n.clear();
                this.f2187n.addAll(m4252i());
            }
            this.f2188o = false;
        }
    }

    /* renamed from: k */
    public ArrayList<C0876j> m4254k() {
        m4253j();
        return this.f2186m;
    }

    /* renamed from: l */
    public ArrayList<C0876j> m4255l() {
        m4253j();
        return this.f2187n;
    }

    /* renamed from: m */
    public CharSequence m4256m() {
        return this.f2175a;
    }

    /* renamed from: n */
    public Drawable m4257n() {
        return this.f2176b;
    }

    /* renamed from: o */
    public View m4258o() {
        return this.f2177c;
    }

    /* renamed from: p */
    public C0873h mo763p() {
        return this;
    }

    public boolean performIdentifierAction(int i, int i2) {
        return m4232a(findItem(i), i2);
    }

    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        MenuItem a = m4220a(i, keyEvent);
        boolean z = false;
        if (a != null) {
            z = m4232a(a, i2);
        }
        if ((i2 & 2) != 0) {
            m4230a(true);
        }
        return z;
    }

    /* renamed from: q */
    boolean m4260q() {
        return this.f2194u;
    }

    /* renamed from: r */
    public C0876j m4261r() {
        return this.f2198y;
    }

    public void removeGroup(int i) {
        int c = m4240c(i);
        if (c >= 0) {
            int size = this.f2183j.size() - c;
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= size || ((C0876j) this.f2183j.get(c)).getGroupId() != i) {
                    m4238b(true);
                } else {
                    m4210a(c, false);
                    i2 = i3;
                }
            }
            m4238b(true);
        }
    }

    public void removeItem(int i) {
        m4210a(m4234b(i), true);
    }

    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.f2183j.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0876j c0876j = (C0876j) this.f2183j.get(i2);
            if (c0876j.getGroupId() == i) {
                c0876j.m4275a(z2);
                c0876j.setCheckable(z);
            }
        }
    }

    public void setGroupEnabled(int i, boolean z) {
        int size = this.f2183j.size();
        for (int i2 = 0; i2 < size; i2++) {
            C0876j c0876j = (C0876j) this.f2183j.get(i2);
            if (c0876j.getGroupId() == i) {
                c0876j.setEnabled(z);
            }
        }
    }

    public void setGroupVisible(int i, boolean z) {
        int size = this.f2183j.size();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < size) {
            C0876j c0876j = (C0876j) this.f2183j.get(i2);
            boolean z3 = (c0876j.getGroupId() == i && c0876j.m4280c(z)) ? true : z2;
            i2++;
            z2 = z3;
        }
        if (z2) {
            m4238b(true);
        }
    }

    public void setQwertyMode(boolean z) {
        this.f2180g = z;
        m4238b(false);
    }

    public int size() {
        return this.f2183j.size();
    }
}
