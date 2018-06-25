package com.p096g.p097a;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.g.a.g */
public interface C1623g {

    /* renamed from: com.g.a.g$a */
    public static class C1621a {
        /* renamed from: a */
        final InputStream f4955a;
        /* renamed from: b */
        final Bitmap f4956b;
        /* renamed from: c */
        final boolean f4957c;
        /* renamed from: d */
        final long f4958d;

        /* renamed from: a */
        public InputStream m7993a() {
            return this.f4955a;
        }

        @Deprecated
        /* renamed from: b */
        public Bitmap m7994b() {
            return this.f4956b;
        }

        /* renamed from: c */
        public long m7995c() {
            return this.f4958d;
        }
    }

    /* renamed from: com.g.a.g$b */
    public static class C1622b extends IOException {
        /* renamed from: a */
        final boolean f4959a;
        /* renamed from: b */
        final int f4960b;
    }

    /* renamed from: a */
    C1621a m7996a(Uri uri, int i);
}
