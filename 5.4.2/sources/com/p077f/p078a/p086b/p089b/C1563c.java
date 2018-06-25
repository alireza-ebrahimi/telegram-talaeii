package com.p077f.p078a.p086b.p089b;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.p077f.p078a.p086b.C1570c;
import com.p077f.p078a.p086b.p087a.C1552d;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p087a.C1557h;
import com.p077f.p078a.p086b.p091d.C1572b;

/* renamed from: com.f.a.b.b.c */
public class C1563c {
    /* renamed from: a */
    private final String f4727a;
    /* renamed from: b */
    private final String f4728b;
    /* renamed from: c */
    private final String f4729c;
    /* renamed from: d */
    private final C1553e f4730d;
    /* renamed from: e */
    private final C1552d f4731e;
    /* renamed from: f */
    private final C1557h f4732f;
    /* renamed from: g */
    private final C1572b f4733g;
    /* renamed from: h */
    private final Object f4734h;
    /* renamed from: i */
    private final boolean f4735i;
    /* renamed from: j */
    private final Options f4736j = new Options();

    public C1563c(String str, String str2, String str3, C1553e c1553e, C1557h c1557h, C1572b c1572b, C1570c c1570c) {
        this.f4727a = str;
        this.f4728b = str2;
        this.f4729c = str3;
        this.f4730d = c1553e;
        this.f4731e = c1570c.m7778j();
        this.f4732f = c1557h;
        this.f4733g = c1572b;
        this.f4734h = c1570c.m7782n();
        this.f4735i = c1570c.m7781m();
        m7700a(c1570c.m7779k(), this.f4736j);
    }

    /* renamed from: a */
    private void m7700a(Options options, Options options2) {
        options2.inDensity = options.inDensity;
        options2.inDither = options.inDither;
        options2.inInputShareable = options.inInputShareable;
        options2.inJustDecodeBounds = options.inJustDecodeBounds;
        options2.inPreferredConfig = options.inPreferredConfig;
        options2.inPurgeable = options.inPurgeable;
        options2.inSampleSize = options.inSampleSize;
        options2.inScaled = options.inScaled;
        options2.inScreenDensity = options.inScreenDensity;
        options2.inTargetDensity = options.inTargetDensity;
        options2.inTempStorage = options.inTempStorage;
        if (VERSION.SDK_INT >= 10) {
            m7701b(options, options2);
        }
        if (VERSION.SDK_INT >= 11) {
            m7702c(options, options2);
        }
    }

    @TargetApi(10)
    /* renamed from: b */
    private void m7701b(Options options, Options options2) {
        options2.inPreferQualityOverSpeed = options.inPreferQualityOverSpeed;
    }

    @TargetApi(11)
    /* renamed from: c */
    private void m7702c(Options options, Options options2) {
        options2.inBitmap = options.inBitmap;
        options2.inMutable = options.inMutable;
    }

    /* renamed from: a */
    public String m7703a() {
        return this.f4727a;
    }

    /* renamed from: b */
    public String m7704b() {
        return this.f4728b;
    }

    /* renamed from: c */
    public C1553e m7705c() {
        return this.f4730d;
    }

    /* renamed from: d */
    public C1552d m7706d() {
        return this.f4731e;
    }

    /* renamed from: e */
    public C1557h m7707e() {
        return this.f4732f;
    }

    /* renamed from: f */
    public C1572b m7708f() {
        return this.f4733g;
    }

    /* renamed from: g */
    public Object m7709g() {
        return this.f4734h;
    }

    /* renamed from: h */
    public boolean m7710h() {
        return this.f4735i;
    }

    /* renamed from: i */
    public Options m7711i() {
        return this.f4736j;
    }
}
