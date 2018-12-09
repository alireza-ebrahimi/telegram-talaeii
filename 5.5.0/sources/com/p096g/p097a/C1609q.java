package com.p096g.p097a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import com.p096g.p097a.C1636m.C1632b;
import java.io.InputStream;

/* renamed from: com.g.a.q */
public abstract class C1609q {

    /* renamed from: com.g.a.q$a */
    public static final class C1642a {
        /* renamed from: a */
        private final C1632b f5056a;
        /* renamed from: b */
        private final Bitmap f5057b;
        /* renamed from: c */
        private final InputStream f5058c;
        /* renamed from: d */
        private final int f5059d;

        public C1642a(Bitmap bitmap, C1632b c1632b) {
            this((Bitmap) C1648v.m8056a((Object) bitmap, "bitmap == null"), null, c1632b, 0);
        }

        C1642a(Bitmap bitmap, InputStream inputStream, C1632b c1632b, int i) {
            int i2 = 1;
            int i3 = bitmap != null ? 1 : 0;
            if (inputStream == null) {
                i2 = 0;
            }
            if ((i2 ^ i3) == 0) {
                throw new AssertionError();
            }
            this.f5057b = bitmap;
            this.f5058c = inputStream;
            this.f5056a = (C1632b) C1648v.m8056a((Object) c1632b, "loadedFrom == null");
            this.f5059d = i;
        }

        public C1642a(InputStream inputStream, C1632b c1632b) {
            this(null, (InputStream) C1648v.m8056a((Object) inputStream, "stream == null"), c1632b, 0);
        }

        /* renamed from: a */
        public Bitmap m8040a() {
            return this.f5057b;
        }

        /* renamed from: b */
        public InputStream m8041b() {
            return this.f5058c;
        }

        /* renamed from: c */
        public C1632b m8042c() {
            return this.f5056a;
        }

        /* renamed from: d */
        int m8043d() {
            return this.f5059d;
        }
    }

    /* renamed from: a */
    static Options m7961a(C1640o c1640o) {
        boolean d = c1640o.m8031d();
        Object obj = c1640o.f5041q != null ? 1 : null;
        Options options = null;
        if (d || obj != null) {
            options = new Options();
            options.inJustDecodeBounds = d;
            if (obj != null) {
                options.inPreferredConfig = c1640o.f5041q;
            }
        }
        return options;
    }

    /* renamed from: a */
    static void m7962a(int i, int i2, int i3, int i4, Options options, C1640o c1640o) {
        int i5 = 1;
        if (i4 > i2 || i3 > i) {
            if (i2 == 0) {
                i5 = (int) Math.floor((double) (((float) i3) / ((float) i)));
            } else if (i == 0) {
                i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
            } else {
                i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
                int floor = (int) Math.floor((double) (((float) i3) / ((float) i)));
                i5 = c1640o.f5035k ? Math.max(i5, floor) : Math.min(i5, floor);
            }
        }
        options.inSampleSize = i5;
        options.inJustDecodeBounds = false;
    }

    /* renamed from: a */
    static void m7963a(int i, int i2, Options options, C1640o c1640o) {
        C1609q.m7962a(i, i2, options.outWidth, options.outHeight, options, c1640o);
    }

    /* renamed from: a */
    static boolean m7964a(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    /* renamed from: a */
    public abstract C1642a mo1243a(C1640o c1640o, int i);
}
