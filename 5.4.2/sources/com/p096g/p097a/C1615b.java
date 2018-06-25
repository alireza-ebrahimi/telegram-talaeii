package com.p096g.p097a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p096g.p097a.C1609q.C1642a;
import com.p096g.p097a.C1636m.C1632b;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: com.g.a.b */
class C1615b implements Runnable {
    /* renamed from: q */
    private static final Object f4930q = new Object();
    /* renamed from: r */
    private static final ThreadLocal<StringBuilder> f4931r = new C16081();
    /* renamed from: s */
    private static final AtomicInteger f4932s = new AtomicInteger();
    /* renamed from: t */
    private static final C1609q f4933t = new C16102();
    /* renamed from: a */
    final C1636m f4934a;
    /* renamed from: b */
    final C1620f f4935b;
    /* renamed from: c */
    final C1616c f4936c;
    /* renamed from: d */
    final C1643r f4937d;
    /* renamed from: e */
    final String f4938e;
    /* renamed from: f */
    final C1640o f4939f;
    /* renamed from: g */
    final int f4940g;
    /* renamed from: h */
    int f4941h;
    /* renamed from: i */
    final C1609q f4942i;
    /* renamed from: j */
    C1607a f4943j;
    /* renamed from: k */
    List<C1607a> f4944k;
    /* renamed from: l */
    Bitmap f4945l;
    /* renamed from: m */
    C1632b f4946m;
    /* renamed from: n */
    Exception f4947n;
    /* renamed from: o */
    int f4948o;
    /* renamed from: p */
    int f4949p;

    /* renamed from: com.g.a.b$1 */
    static class C16081 extends ThreadLocal<StringBuilder> {
        C16081() {
        }

        /* renamed from: a */
        protected StringBuilder m7960a() {
            return new StringBuilder("Picasso-");
        }

        protected /* synthetic */ Object initialValue() {
            return m7960a();
        }
    }

    /* renamed from: com.g.a.b$2 */
    static class C16102 extends C1609q {
        C16102() {
        }

        /* renamed from: a */
        public C1642a mo1243a(C1640o c1640o, int i) {
            throw new IllegalStateException("Unrecognized type of request: " + c1640o);
        }
    }

    /* renamed from: a */
    static Bitmap m7967a(C1640o c1640o, Bitmap bitmap, int i) {
        int ceil;
        int i2;
        int i3;
        Bitmap createBitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean z = c1640o.f5036l;
        Matrix matrix = new Matrix();
        if (c1640o.m8033f()) {
            int i4 = c1640o.f5032h;
            int i5 = c1640o.f5033i;
            float f = c1640o.f5037m;
            if (f != BitmapDescriptorFactory.HUE_RED) {
                if (c1640o.f5040p) {
                    matrix.setRotate(f, c1640o.f5038n, c1640o.f5039o);
                } else {
                    matrix.setRotate(f);
                }
            }
            float f2;
            if (c1640o.f5034j) {
                int i6;
                int i7;
                f2 = ((float) i4) / ((float) width);
                f = ((float) i5) / ((float) height);
                if (f2 > f) {
                    ceil = (int) Math.ceil((double) ((f / f2) * ((float) height)));
                    f = ((float) i5) / ((float) ceil);
                    i6 = 0;
                    i7 = (height - ceil) / 2;
                    i2 = width;
                } else {
                    ceil = (int) Math.ceil((double) ((f2 / f) * ((float) width)));
                    f2 = ((float) i4) / ((float) ceil);
                    i6 = (width - ceil) / 2;
                    i7 = 0;
                    i2 = ceil;
                    ceil = height;
                }
                if (C1615b.m7971a(z, width, height, i4, i5)) {
                    matrix.preScale(f2, f);
                }
                height = i2;
                i3 = i6;
                i2 = ceil;
                ceil = i7;
            } else if (c1640o.f5035k) {
                f = ((float) i4) / ((float) width);
                f2 = ((float) i5) / ((float) height);
                if (f >= f2) {
                    f = f2;
                }
                if (C1615b.m7971a(z, width, height, i4, i5)) {
                    matrix.preScale(f, f);
                }
                ceil = 0;
                i3 = 0;
                i2 = height;
                height = width;
            } else if (!((i4 == 0 && i5 == 0) || (i4 == width && i5 == height))) {
                f2 = i4 != 0 ? ((float) i4) / ((float) width) : ((float) i5) / ((float) height);
                f = i5 != 0 ? ((float) i5) / ((float) height) : ((float) i4) / ((float) width);
                if (C1615b.m7971a(z, width, height, i4, i5)) {
                    matrix.preScale(f2, f);
                }
            }
            if (i != 0) {
                matrix.preRotate((float) i);
            }
            createBitmap = Bitmap.createBitmap(bitmap, i3, ceil, height, i2, matrix, true);
            if (createBitmap != bitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        }
        ceil = 0;
        i3 = 0;
        i2 = height;
        height = width;
        if (i != 0) {
            matrix.preRotate((float) i);
        }
        createBitmap = Bitmap.createBitmap(bitmap, i3, ceil, height, i2, matrix, true);
        if (createBitmap != bitmap) {
            return bitmap;
        }
        bitmap.recycle();
        return createBitmap;
    }

    /* renamed from: a */
    static Bitmap m7968a(InputStream inputStream, C1640o c1640o) {
        InputStream c1625i = new C1625i(inputStream);
        long a = c1625i.m8002a((int) C3446C.DEFAULT_BUFFER_SEGMENT_SIZE);
        Options a2 = C1609q.m7961a(c1640o);
        boolean a3 = C1609q.m7964a(a2);
        boolean c = C1648v.m8067c(c1625i);
        c1625i.m8003a(a);
        if (c) {
            byte[] b = C1648v.m8066b(c1625i);
            if (a3) {
                BitmapFactory.decodeByteArray(b, 0, b.length, a2);
                C1609q.m7963a(c1640o.f5032h, c1640o.f5033i, a2, c1640o);
            }
            return BitmapFactory.decodeByteArray(b, 0, b.length, a2);
        }
        if (a3) {
            BitmapFactory.decodeStream(c1625i, null, a2);
            C1609q.m7963a(c1640o.f5032h, c1640o.f5033i, a2, c1640o);
            c1625i.m8003a(a);
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(c1625i, null, a2);
        if (decodeStream != null) {
            return decodeStream;
        }
        throw new IOException("Failed to decode stream.");
    }

    /* renamed from: a */
    static Bitmap m7969a(List<C1646u> list, Bitmap bitmap) {
        int size = list.size();
        int i = 0;
        Bitmap bitmap2 = bitmap;
        while (i < size) {
            final C1646u c1646u = (C1646u) list.get(i);
            try {
                bitmap = c1646u.m8052a(bitmap2);
                if (bitmap == null) {
                    final StringBuilder append = new StringBuilder().append("Transformation ").append(c1646u.m8053a()).append(" returned null after ").append(i).append(" previous transformation(s).\n\nTransformation list:\n");
                    for (C1646u c1646u2 : list) {
                        append.append(c1646u2.m8053a()).append('\n');
                    }
                    C1636m.f4988a.post(new Runnable() {
                        public void run() {
                            throw new NullPointerException(append.toString());
                        }
                    });
                    return null;
                } else if (bitmap == bitmap2 && bitmap2.isRecycled()) {
                    C1636m.f4988a.post(new Runnable() {
                        public void run() {
                            throw new IllegalStateException("Transformation " + c1646u2.m8053a() + " returned input Bitmap but recycled it.");
                        }
                    });
                    return null;
                } else if (bitmap == bitmap2 || bitmap2.isRecycled()) {
                    i++;
                    bitmap2 = bitmap;
                } else {
                    C1636m.f4988a.post(new Runnable() {
                        public void run() {
                            throw new IllegalStateException("Transformation " + c1646u2.m8053a() + " mutated input Bitmap but failed to recycle the original.");
                        }
                    });
                    return null;
                }
            } catch (final RuntimeException e) {
                C1636m.f4988a.post(new Runnable() {
                    public void run() {
                        throw new RuntimeException("Transformation " + c1646u2.m8053a() + " crashed with exception.", e);
                    }
                });
                return null;
            }
        }
        return bitmap2;
    }

    /* renamed from: a */
    static void m7970a(C1640o c1640o) {
        String c = c1640o.m8030c();
        StringBuilder stringBuilder = (StringBuilder) f4931r.get();
        stringBuilder.ensureCapacity("Picasso-".length() + c.length());
        stringBuilder.replace("Picasso-".length(), stringBuilder.length(), c);
        Thread.currentThread().setName(stringBuilder.toString());
    }

    /* renamed from: a */
    private static boolean m7971a(boolean z, int i, int i2, int i3, int i4) {
        return !z || i > i3 || i2 > i4;
    }

    /* renamed from: a */
    Bitmap m7972a() {
        Bitmap bitmap = null;
        if (C1626j.m8004a(this.f4940g)) {
            bitmap = this.f4936c.mo1245a(this.f4938e);
            if (bitmap != null) {
                this.f4937d.m8045a();
                this.f4946m = C1632b.MEMORY;
                if (this.f4934a.f4998k) {
                    C1648v.m8064a("Hunter", "decoded", this.f4939f.m8028a(), "from cache");
                }
                return bitmap;
            }
        }
        this.f4939f.f5027c = this.f4949p == 0 ? C1627k.OFFLINE.f4975d : this.f4941h;
        C1642a a = this.f4942i.mo1243a(this.f4939f, this.f4941h);
        if (a != null) {
            this.f4946m = a.m8042c();
            this.f4948o = a.m8043d();
            bitmap = a.m8040a();
            if (bitmap == null) {
                InputStream b = a.m8041b();
                try {
                    bitmap = C1615b.m7968a(b, this.f4939f);
                } finally {
                    C1648v.m8062a(b);
                }
            }
        }
        if (bitmap != null) {
            if (this.f4934a.f4998k) {
                C1648v.m8063a("Hunter", "decoded", this.f4939f.m8028a());
            }
            this.f4937d.m8047a(bitmap);
            if (this.f4939f.m8032e() || this.f4948o != 0) {
                synchronized (f4930q) {
                    if (this.f4939f.m8033f() || this.f4948o != 0) {
                        bitmap = C1615b.m7967a(this.f4939f, bitmap, this.f4948o);
                        if (this.f4934a.f4998k) {
                            C1648v.m8063a("Hunter", "transformed", this.f4939f.m8028a());
                        }
                    }
                    if (this.f4939f.m8034g()) {
                        bitmap = C1615b.m7969a(this.f4939f.f5031g, bitmap);
                        if (this.f4934a.f4998k) {
                            C1648v.m8064a("Hunter", "transformed", this.f4939f.m8028a(), "from custom transformations");
                        }
                    }
                }
                if (bitmap != null) {
                    this.f4937d.m8049b(bitmap);
                }
            }
        }
        return bitmap;
    }

    /* renamed from: b */
    Bitmap m7973b() {
        return this.f4945l;
    }

    /* renamed from: c */
    C1640o m7974c() {
        return this.f4939f;
    }

    /* renamed from: d */
    C1607a m7975d() {
        return this.f4943j;
    }

    /* renamed from: e */
    List<C1607a> m7976e() {
        return this.f4944k;
    }

    /* renamed from: f */
    Exception m7977f() {
        return this.f4947n;
    }

    /* renamed from: g */
    C1632b m7978g() {
        return this.f4946m;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r4 = this;
        r0 = r4.f4939f;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        com.p096g.p097a.C1615b.m7970a(r0);	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r0 = r4.f4934a;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r0 = r0.f4998k;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        if (r0 == 0) goto L_0x0018;
    L_0x000b:
        r0 = "Hunter";
        r1 = "executing";
        r2 = com.p096g.p097a.C1648v.m8057a(r4);	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        com.p096g.p097a.C1648v.m8063a(r0, r1, r2);	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
    L_0x0018:
        r0 = r4.m7972a();	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r4.f4945l = r0;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r0 = r4.f4945l;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        if (r0 != 0) goto L_0x0032;
    L_0x0022:
        r0 = r4.f4935b;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r0.m7992c(r4);	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
    L_0x0027:
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
    L_0x0031:
        return;
    L_0x0032:
        r0 = r4.f4935b;	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        r0.m7989a(r4);	 Catch:{ b -> 0x0038, a -> 0x0055, IOException -> 0x0068, OutOfMemoryError -> 0x007b, Exception -> 0x00aa }
        goto L_0x0027;
    L_0x0038:
        r0 = move-exception;
        r1 = r0.f4959a;	 Catch:{ all -> 0x00be }
        if (r1 == 0) goto L_0x0043;
    L_0x003d:
        r1 = r0.f4960b;	 Catch:{ all -> 0x00be }
        r2 = 504; // 0x1f8 float:7.06E-43 double:2.49E-321;
        if (r1 == r2) goto L_0x0045;
    L_0x0043:
        r4.f4947n = r0;	 Catch:{ all -> 0x00be }
    L_0x0045:
        r0 = r4.f4935b;	 Catch:{ all -> 0x00be }
        r0.m7992c(r4);	 Catch:{ all -> 0x00be }
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
        goto L_0x0031;
    L_0x0055:
        r0 = move-exception;
        r4.f4947n = r0;	 Catch:{ all -> 0x00be }
        r0 = r4.f4935b;	 Catch:{ all -> 0x00be }
        r0.m7991b(r4);	 Catch:{ all -> 0x00be }
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
        goto L_0x0031;
    L_0x0068:
        r0 = move-exception;
        r4.f4947n = r0;	 Catch:{ all -> 0x00be }
        r0 = r4.f4935b;	 Catch:{ all -> 0x00be }
        r0.m7991b(r4);	 Catch:{ all -> 0x00be }
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
        goto L_0x0031;
    L_0x007b:
        r0 = move-exception;
        r1 = new java.io.StringWriter;	 Catch:{ all -> 0x00be }
        r1.<init>();	 Catch:{ all -> 0x00be }
        r2 = r4.f4937d;	 Catch:{ all -> 0x00be }
        r2 = r2.m8050c();	 Catch:{ all -> 0x00be }
        r3 = new java.io.PrintWriter;	 Catch:{ all -> 0x00be }
        r3.<init>(r1);	 Catch:{ all -> 0x00be }
        r2.m8051a(r3);	 Catch:{ all -> 0x00be }
        r2 = new java.lang.RuntimeException;	 Catch:{ all -> 0x00be }
        r1 = r1.toString();	 Catch:{ all -> 0x00be }
        r2.<init>(r1, r0);	 Catch:{ all -> 0x00be }
        r4.f4947n = r2;	 Catch:{ all -> 0x00be }
        r0 = r4.f4935b;	 Catch:{ all -> 0x00be }
        r0.m7992c(r4);	 Catch:{ all -> 0x00be }
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
        goto L_0x0031;
    L_0x00aa:
        r0 = move-exception;
        r4.f4947n = r0;	 Catch:{ all -> 0x00be }
        r0 = r4.f4935b;	 Catch:{ all -> 0x00be }
        r0.m7992c(r4);	 Catch:{ all -> 0x00be }
        r0 = java.lang.Thread.currentThread();
        r1 = "Picasso-Idle";
        r0.setName(r1);
        goto L_0x0031;
    L_0x00be:
        r0 = move-exception;
        r1 = java.lang.Thread.currentThread();
        r2 = "Picasso-Idle";
        r1.setName(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.g.a.b.run():void");
    }
}
