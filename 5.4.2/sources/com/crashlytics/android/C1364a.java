package com.crashlytics.android;

import com.crashlytics.android.p064a.C1333b;
import com.crashlytics.android.p065b.C1370c;
import com.crashlytics.android.p066c.C1446i;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import p033b.p034a.p035a.p036a.C1237i;
import p033b.p034a.p035a.p036a.C1238j;

/* renamed from: com.crashlytics.android.a */
public class C1364a extends C1237i<Void> implements C1238j {
    /* renamed from: a */
    public final C1333b f4127a;
    /* renamed from: b */
    public final C1370c f4128b;
    /* renamed from: c */
    public final C1446i f4129c;
    /* renamed from: d */
    public final Collection<? extends C1237i> f4130d;

    public C1364a() {
        this(new C1333b(), new C1370c(), new C1446i());
    }

    C1364a(C1333b c1333b, C1370c c1370c, C1446i c1446i) {
        this.f4127a = c1333b;
        this.f4128b = c1370c;
        this.f4129c = c1446i;
        this.f4130d = Collections.unmodifiableCollection(Arrays.asList(new C1237i[]{c1333b, c1370c, c1446i}));
    }

    /* renamed from: a */
    public String mo1080a() {
        return "2.7.1.19";
    }

    /* renamed from: b */
    public String mo1081b() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    /* renamed from: c */
    public Collection<? extends C1237i> mo1145c() {
        return this.f4130d;
    }

    /* renamed from: d */
    protected Void m6924d() {
        return null;
    }

    /* renamed from: e */
    protected /* synthetic */ Object mo1083e() {
        return m6924d();
    }
}
