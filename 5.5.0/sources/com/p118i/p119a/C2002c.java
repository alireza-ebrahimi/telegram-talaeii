package com.p118i.p119a;

import android.net.Uri;
import java.util.HashMap;

/* renamed from: com.i.a.c */
public class C2002c implements Comparable<C2002c> {
    /* renamed from: a */
    private int f5895a;
    /* renamed from: b */
    private int f5896b;
    /* renamed from: c */
    private Uri f5897c;
    /* renamed from: d */
    private Uri f5898d;
    /* renamed from: e */
    private C1997h f5899e;
    /* renamed from: f */
    private boolean f5900f = false;
    /* renamed from: g */
    private boolean f5901g = true;
    /* renamed from: h */
    private C2008d f5902h;
    /* renamed from: i */
    private C2009e f5903i;
    /* renamed from: j */
    private C2010f f5904j;
    /* renamed from: k */
    private Object f5905k;
    /* renamed from: l */
    private HashMap<String, String> f5906l;
    /* renamed from: m */
    private C2001a f5907m = C2001a.NORMAL;

    /* renamed from: com.i.a.c$a */
    public enum C2001a {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public C2002c(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equals("http") || scheme.equals("https"))) {
            throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
        }
        this.f5906l = new HashMap();
        this.f5895a = 1;
        this.f5897c = uri;
    }

    /* renamed from: a */
    public int m9045a(C2002c c2002c) {
        C2001a a = m9046a();
        C2001a a2 = c2002c.m9046a();
        return a == a2 ? this.f5896b - c2002c.f5896b : a2.ordinal() - a.ordinal();
    }

    /* renamed from: a */
    public C2001a m9046a() {
        return this.f5907m;
    }

    /* renamed from: a */
    public C2002c m9047a(Uri uri) {
        this.f5898d = uri;
        return this;
    }

    /* renamed from: a */
    public C2002c m9048a(C2001a c2001a) {
        this.f5907m = c2001a;
        return this;
    }

    /* renamed from: a */
    public C2002c m9049a(C2010f c2010f) {
        this.f5904j = c2010f;
        return this;
    }

    /* renamed from: a */
    public C2002c m9050a(C1997h c1997h) {
        this.f5899e = c1997h;
        return this;
    }

    /* renamed from: a */
    public C2002c m9051a(Object obj) {
        this.f5905k = obj;
        return this;
    }

    /* renamed from: a */
    final void m9052a(int i) {
        this.f5896b = i;
    }

    /* renamed from: a */
    void m9053a(C2008d c2008d) {
        this.f5902h = c2008d;
    }

    /* renamed from: b */
    public C1997h m9054b() {
        return this.f5899e == null ? new C1998a() : this.f5899e;
    }

    /* renamed from: b */
    void m9055b(int i) {
        this.f5895a = i;
    }

    /* renamed from: c */
    public final int m9056c() {
        return this.f5896b;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return m9045a((C2002c) obj);
    }

    /* renamed from: d */
    int m9057d() {
        return this.f5895a;
    }

    /* renamed from: e */
    C2009e m9058e() {
        return this.f5903i;
    }

    /* renamed from: f */
    C2010f m9059f() {
        return this.f5904j;
    }

    /* renamed from: g */
    public Uri m9060g() {
        return this.f5897c;
    }

    /* renamed from: h */
    public Uri m9061h() {
        return this.f5898d;
    }

    /* renamed from: i */
    public boolean m9062i() {
        return this.f5901g;
    }

    /* renamed from: j */
    public void m9063j() {
        this.f5900f = true;
    }

    /* renamed from: k */
    public boolean m9064k() {
        return this.f5900f;
    }

    /* renamed from: l */
    HashMap<String, String> m9065l() {
        return this.f5906l;
    }

    /* renamed from: m */
    void m9066m() {
        this.f5902h.m9076b(this);
    }
}
