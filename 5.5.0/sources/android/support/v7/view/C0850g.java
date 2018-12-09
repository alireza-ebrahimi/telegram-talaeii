package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v4.p018c.p019a.C0393a;
import android.support.v4.view.C0616d;
import android.support.v4.view.C0652q;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.view.menu.C0876j;
import android.support.v7.view.menu.C0881k;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* renamed from: android.support.v7.view.g */
public class C0850g extends MenuInflater {
    /* renamed from: a */
    static final Class<?>[] f2038a = new Class[]{Context.class};
    /* renamed from: b */
    static final Class<?>[] f2039b = f2038a;
    /* renamed from: c */
    final Object[] f2040c;
    /* renamed from: d */
    final Object[] f2041d = this.f2040c;
    /* renamed from: e */
    Context f2042e;
    /* renamed from: f */
    private Object f2043f;

    /* renamed from: android.support.v7.view.g$a */
    private static class C0848a implements OnMenuItemClickListener {
        /* renamed from: a */
        private static final Class<?>[] f2009a = new Class[]{MenuItem.class};
        /* renamed from: b */
        private Object f2010b;
        /* renamed from: c */
        private Method f2011c;

        public C0848a(Object obj, String str) {
            this.f2010b = obj;
            Class cls = obj.getClass();
            try {
                this.f2011c = cls.getMethod(str, f2009a);
            } catch (Throwable e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.f2011c.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.f2011c.invoke(this.f2010b, new Object[]{menuItem})).booleanValue();
                }
                this.f2011c.invoke(this.f2010b, new Object[]{menuItem});
                return true;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: android.support.v7.view.g$b */
    private class C0849b {
        /* renamed from: a */
        C0616d f2012a;
        /* renamed from: b */
        final /* synthetic */ C0850g f2013b;
        /* renamed from: c */
        private Menu f2014c;
        /* renamed from: d */
        private int f2015d;
        /* renamed from: e */
        private int f2016e;
        /* renamed from: f */
        private int f2017f;
        /* renamed from: g */
        private int f2018g;
        /* renamed from: h */
        private boolean f2019h;
        /* renamed from: i */
        private boolean f2020i;
        /* renamed from: j */
        private boolean f2021j;
        /* renamed from: k */
        private int f2022k;
        /* renamed from: l */
        private int f2023l;
        /* renamed from: m */
        private CharSequence f2024m;
        /* renamed from: n */
        private CharSequence f2025n;
        /* renamed from: o */
        private int f2026o;
        /* renamed from: p */
        private char f2027p;
        /* renamed from: q */
        private char f2028q;
        /* renamed from: r */
        private int f2029r;
        /* renamed from: s */
        private boolean f2030s;
        /* renamed from: t */
        private boolean f2031t;
        /* renamed from: u */
        private boolean f2032u;
        /* renamed from: v */
        private int f2033v;
        /* renamed from: w */
        private int f2034w;
        /* renamed from: x */
        private String f2035x;
        /* renamed from: y */
        private String f2036y;
        /* renamed from: z */
        private String f2037z;

        public C0849b(C0850g c0850g, Menu menu) {
            this.f2013b = c0850g;
            this.f2014c = menu;
            m4051a();
        }

        /* renamed from: a */
        private char m4048a(String str) {
            return str == null ? '\u0000' : str.charAt(0);
        }

        /* renamed from: a */
        private <T> T m4049a(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor constructor = this.f2013b.f2042e.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return constructor.newInstance(objArr);
            } catch (Throwable e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }

        /* renamed from: a */
        private void m4050a(MenuItem menuItem) {
            boolean z = true;
            menuItem.setChecked(this.f2030s).setVisible(this.f2031t).setEnabled(this.f2032u).setCheckable(this.f2029r >= 1).setTitleCondensed(this.f2025n).setIcon(this.f2026o).setAlphabeticShortcut(this.f2027p).setNumericShortcut(this.f2028q);
            if (this.f2033v >= 0) {
                C0652q.m3191a(menuItem, this.f2033v);
            }
            if (this.f2037z != null) {
                if (this.f2013b.f2042e.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener(new C0848a(this.f2013b.m4059a(), this.f2037z));
            }
            if (menuItem instanceof C0876j) {
                C0876j c0876j = (C0876j) menuItem;
            }
            if (this.f2029r >= 2) {
                if (menuItem instanceof C0876j) {
                    ((C0876j) menuItem).m4275a(true);
                } else if (menuItem instanceof C0881k) {
                    ((C0881k) menuItem).m4304a(true);
                }
            }
            if (this.f2035x != null) {
                C0652q.m3189a(menuItem, (View) m4049a(this.f2035x, C0850g.f2038a, this.f2013b.f2040c));
            } else {
                z = false;
            }
            if (this.f2034w > 0) {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    C0652q.m3192b(menuItem, this.f2034w);
                }
            }
            if (this.f2012a != null) {
                C0652q.m3188a(menuItem, this.f2012a);
            }
        }

        /* renamed from: a */
        public void m4051a() {
            this.f2015d = 0;
            this.f2016e = 0;
            this.f2017f = 0;
            this.f2018g = 0;
            this.f2019h = true;
            this.f2020i = true;
        }

        /* renamed from: a */
        public void m4052a(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = this.f2013b.f2042e.obtainStyledAttributes(attributeSet, C0747j.MenuGroup);
            this.f2015d = obtainStyledAttributes.getResourceId(C0747j.MenuGroup_android_id, 0);
            this.f2016e = obtainStyledAttributes.getInt(C0747j.MenuGroup_android_menuCategory, 0);
            this.f2017f = obtainStyledAttributes.getInt(C0747j.MenuGroup_android_orderInCategory, 0);
            this.f2018g = obtainStyledAttributes.getInt(C0747j.MenuGroup_android_checkableBehavior, 0);
            this.f2019h = obtainStyledAttributes.getBoolean(C0747j.MenuGroup_android_visible, true);
            this.f2020i = obtainStyledAttributes.getBoolean(C0747j.MenuGroup_android_enabled, true);
            obtainStyledAttributes.recycle();
        }

        /* renamed from: b */
        public void m4053b() {
            this.f2021j = true;
            m4050a(this.f2014c.add(this.f2015d, this.f2022k, this.f2023l, this.f2024m));
        }

        /* renamed from: b */
        public void m4054b(AttributeSet attributeSet) {
            boolean z = true;
            TypedArray obtainStyledAttributes = this.f2013b.f2042e.obtainStyledAttributes(attributeSet, C0747j.MenuItem);
            this.f2022k = obtainStyledAttributes.getResourceId(C0747j.MenuItem_android_id, 0);
            this.f2023l = (obtainStyledAttributes.getInt(C0747j.MenuItem_android_menuCategory, this.f2016e) & -65536) | (obtainStyledAttributes.getInt(C0747j.MenuItem_android_orderInCategory, this.f2017f) & 65535);
            this.f2024m = obtainStyledAttributes.getText(C0747j.MenuItem_android_title);
            this.f2025n = obtainStyledAttributes.getText(C0747j.MenuItem_android_titleCondensed);
            this.f2026o = obtainStyledAttributes.getResourceId(C0747j.MenuItem_android_icon, 0);
            this.f2027p = m4048a(obtainStyledAttributes.getString(C0747j.MenuItem_android_alphabeticShortcut));
            this.f2028q = m4048a(obtainStyledAttributes.getString(C0747j.MenuItem_android_numericShortcut));
            if (obtainStyledAttributes.hasValue(C0747j.MenuItem_android_checkable)) {
                this.f2029r = obtainStyledAttributes.getBoolean(C0747j.MenuItem_android_checkable, false) ? 1 : 0;
            } else {
                this.f2029r = this.f2018g;
            }
            this.f2030s = obtainStyledAttributes.getBoolean(C0747j.MenuItem_android_checked, false);
            this.f2031t = obtainStyledAttributes.getBoolean(C0747j.MenuItem_android_visible, this.f2019h);
            this.f2032u = obtainStyledAttributes.getBoolean(C0747j.MenuItem_android_enabled, this.f2020i);
            this.f2033v = obtainStyledAttributes.getInt(C0747j.MenuItem_showAsAction, -1);
            this.f2037z = obtainStyledAttributes.getString(C0747j.MenuItem_android_onClick);
            this.f2034w = obtainStyledAttributes.getResourceId(C0747j.MenuItem_actionLayout, 0);
            this.f2035x = obtainStyledAttributes.getString(C0747j.MenuItem_actionViewClass);
            this.f2036y = obtainStyledAttributes.getString(C0747j.MenuItem_actionProviderClass);
            if (this.f2036y == null) {
                z = false;
            }
            if (z && this.f2034w == 0 && this.f2035x == null) {
                this.f2012a = (C0616d) m4049a(this.f2036y, C0850g.f2039b, this.f2013b.f2041d);
            } else {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.f2012a = null;
            }
            obtainStyledAttributes.recycle();
            this.f2021j = false;
        }

        /* renamed from: c */
        public SubMenu m4055c() {
            this.f2021j = true;
            SubMenu addSubMenu = this.f2014c.addSubMenu(this.f2015d, this.f2022k, this.f2023l, this.f2024m);
            m4050a(addSubMenu.getItem());
            return addSubMenu;
        }

        /* renamed from: d */
        public boolean m4056d() {
            return this.f2021j;
        }
    }

    public C0850g(Context context) {
        super(context);
        this.f2042e = context;
        this.f2040c = new Object[]{context};
    }

    /* renamed from: a */
    private Object m4057a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? m4057a(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    private void m4058a(org.xmlpull.v1.XmlPullParser r11, android.util.AttributeSet r12, android.view.Menu r13) {
        /*
        r10 = this;
        r4 = 0;
        r1 = 1;
        r6 = 0;
        r7 = new android.support.v7.view.g$b;
        r7.<init>(r10, r13);
        r0 = r11.getEventType();
    L_0x000c:
        r2 = 2;
        if (r0 != r2) goto L_0x004c;
    L_0x000f:
        r0 = r11.getName();
        r2 = "menu";
        r2 = r0.equals(r2);
        if (r2 == 0) goto L_0x0032;
    L_0x001c:
        r0 = r11.next();
    L_0x0020:
        r2 = r4;
        r5 = r6;
        r3 = r0;
        r0 = r6;
    L_0x0024:
        if (r0 != 0) goto L_0x00e6;
    L_0x0026:
        switch(r3) {
            case 1: goto L_0x00dd;
            case 2: goto L_0x0053;
            case 3: goto L_0x008c;
            default: goto L_0x0029;
        };
    L_0x0029:
        r3 = r5;
    L_0x002a:
        r5 = r11.next();
        r9 = r3;
        r3 = r5;
        r5 = r9;
        goto L_0x0024;
    L_0x0032:
        r1 = new java.lang.RuntimeException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Expecting menu, got ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        r1.<init>(r0);
        throw r1;
    L_0x004c:
        r0 = r11.next();
        if (r0 != r1) goto L_0x000c;
    L_0x0052:
        goto L_0x0020;
    L_0x0053:
        if (r5 == 0) goto L_0x0057;
    L_0x0055:
        r3 = r5;
        goto L_0x002a;
    L_0x0057:
        r3 = r11.getName();
        r8 = "group";
        r8 = r3.equals(r8);
        if (r8 == 0) goto L_0x0069;
    L_0x0064:
        r7.m4052a(r12);
        r3 = r5;
        goto L_0x002a;
    L_0x0069:
        r8 = "item";
        r8 = r3.equals(r8);
        if (r8 == 0) goto L_0x0077;
    L_0x0072:
        r7.m4054b(r12);
        r3 = r5;
        goto L_0x002a;
    L_0x0077:
        r8 = "menu";
        r8 = r3.equals(r8);
        if (r8 == 0) goto L_0x0089;
    L_0x0080:
        r3 = r7.m4055c();
        r10.m4058a(r11, r12, r3);
        r3 = r5;
        goto L_0x002a;
    L_0x0089:
        r2 = r3;
        r3 = r1;
        goto L_0x002a;
    L_0x008c:
        r3 = r11.getName();
        if (r5 == 0) goto L_0x009b;
    L_0x0092:
        r8 = r3.equals(r2);
        if (r8 == 0) goto L_0x009b;
    L_0x0098:
        r2 = r4;
        r3 = r6;
        goto L_0x002a;
    L_0x009b:
        r8 = "group";
        r8 = r3.equals(r8);
        if (r8 == 0) goto L_0x00a9;
    L_0x00a4:
        r7.m4051a();
        r3 = r5;
        goto L_0x002a;
    L_0x00a9:
        r8 = "item";
        r8 = r3.equals(r8);
        if (r8 == 0) goto L_0x00d0;
    L_0x00b2:
        r3 = r7.m4056d();
        if (r3 != 0) goto L_0x0029;
    L_0x00b8:
        r3 = r7.f2012a;
        if (r3 == 0) goto L_0x00ca;
    L_0x00bc:
        r3 = r7.f2012a;
        r3 = r3.mo746e();
        if (r3 == 0) goto L_0x00ca;
    L_0x00c4:
        r7.m4055c();
        r3 = r5;
        goto L_0x002a;
    L_0x00ca:
        r7.m4053b();
        r3 = r5;
        goto L_0x002a;
    L_0x00d0:
        r8 = "menu";
        r3 = r3.equals(r8);
        if (r3 == 0) goto L_0x0029;
    L_0x00d9:
        r0 = r1;
        r3 = r5;
        goto L_0x002a;
    L_0x00dd:
        r0 = new java.lang.RuntimeException;
        r1 = "Unexpected end of document";
        r0.<init>(r1);
        throw r0;
    L_0x00e6:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.view.g.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.Menu):void");
    }

    /* renamed from: a */
    Object m4059a() {
        if (this.f2043f == null) {
            this.f2043f = m4057a(this.f2042e);
        }
        return this.f2043f;
    }

    public void inflate(int i, Menu menu) {
        if (menu instanceof C0393a) {
            XmlResourceParser xmlResourceParser = null;
            try {
                xmlResourceParser = this.f2042e.getResources().getLayout(i);
                m4058a(xmlResourceParser, Xml.asAttributeSet(xmlResourceParser), menu);
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
            } catch (Throwable e) {
                throw new InflateException("Error inflating menu XML", e);
            } catch (Throwable e2) {
                throw new InflateException("Error inflating menu XML", e2);
            } catch (Throwable th) {
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
            }
        } else {
            super.inflate(i, menu);
        }
    }
}
