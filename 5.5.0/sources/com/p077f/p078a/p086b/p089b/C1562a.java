package com.p077f.p078a.p086b.p089b;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import com.p077f.p078a.p086b.p087a.C1552d;
import com.p077f.p078a.p086b.p087a.C1553e;
import com.p077f.p078a.p086b.p091d.C1572b.C1574a;
import com.p077f.p078a.p095c.C1600a;
import com.p077f.p078a.p095c.C1601b;
import com.p077f.p078a.p095c.C1602c;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.f.a.b.b.a */
public class C1562a implements C1561b {
    /* renamed from: a */
    protected final boolean f4726a;

    /* renamed from: com.f.a.b.b.a$a */
    protected static class C1559a {
        /* renamed from: a */
        public final int f4722a;
        /* renamed from: b */
        public final boolean f4723b;

        protected C1559a() {
            this.f4722a = 0;
            this.f4723b = false;
        }

        protected C1559a(int i, boolean z) {
            this.f4722a = i;
            this.f4723b = z;
        }
    }

    /* renamed from: com.f.a.b.b.a$b */
    protected static class C1560b {
        /* renamed from: a */
        public final C1553e f4724a;
        /* renamed from: b */
        public final C1559a f4725b;

        protected C1560b(C1553e c1553e, C1559a c1559a) {
            this.f4724a = c1553e;
            this.f4725b = c1559a;
        }
    }

    public C1562a(boolean z) {
        this.f4726a = z;
    }

    /* renamed from: a */
    private boolean m7692a(String str, String str2) {
        return "image/jpeg".equalsIgnoreCase(str2) && C1574a.m7802a(str) == C1574a.FILE;
    }

    /* renamed from: a */
    protected Bitmap m7693a(Bitmap bitmap, C1563c c1563c, int i, boolean z) {
        Matrix matrix = new Matrix();
        C1552d d = c1563c.m7706d();
        if (d == C1552d.EXACTLY || d == C1552d.EXACTLY_STRETCHED) {
            float b = C1600a.m7929b(new C1553e(bitmap.getWidth(), bitmap.getHeight(), i), c1563c.m7705c(), c1563c.m7707e(), d == C1552d.EXACTLY_STRETCHED);
            if (Float.compare(b, 1.0f) != 0) {
                matrix.setScale(b, b);
                if (this.f4726a) {
                    C1602c.m7936a("Scale subsampled image (%1$s) to %2$s (scale = %3$.5f) [%4$s]", r2, r2.m7674a(b), Float.valueOf(b), c1563c.m7703a());
                }
            }
        }
        if (z) {
            matrix.postScale(-1.0f, 1.0f);
            if (this.f4726a) {
                C1602c.m7936a("Flip image horizontally [%s]", c1563c.m7703a());
            }
        }
        if (i != 0) {
            matrix.postRotate((float) i);
            if (this.f4726a) {
                C1602c.m7936a("Rotate image on %1$d° [%2$s]", Integer.valueOf(i), c1563c.m7703a());
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (createBitmap != bitmap) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    /* renamed from: a */
    public Bitmap mo1225a(C1563c c1563c) {
        Closeable b = m7698b(c1563c);
        if (b == null) {
            C1602c.m7942d("No stream for image [%s]", c1563c.m7703a());
            return null;
        }
        try {
            C1560b a = m7697a((InputStream) b, c1563c);
            b = m7699b(b, c1563c);
            Bitmap decodeStream = BitmapFactory.decodeStream(b, null, m7695a(a.f4724a, c1563c));
            if (decodeStream != null) {
                return m7693a(decodeStream, c1563c, a.f4725b.f4722a, a.f4725b.f4723b);
            }
            C1602c.m7942d("Image can't be decoded [%s]", c1563c.m7703a());
            return decodeStream;
        } finally {
            C1601b.m7930a(b);
        }
    }

    /* renamed from: a */
    protected Options m7695a(C1553e c1553e, C1563c c1563c) {
        int i;
        C1552d d = c1563c.m7706d();
        if (d == C1552d.NONE) {
            i = 1;
        } else if (d == C1552d.NONE_SAFE) {
            i = C1600a.m7926a(c1553e);
        } else {
            i = C1600a.m7927a(c1553e, c1563c.m7705c(), c1563c.m7707e(), d == C1552d.IN_SAMPLE_POWER_OF_2);
        }
        if (i > 1 && this.f4726a) {
            C1602c.m7936a("Subsample original image (%1$s) to %2$s (scale = %3$d) [%4$s]", c1553e, c1553e.m7675a(i), Integer.valueOf(i), c1563c.m7703a());
        }
        Options i2 = c1563c.m7711i();
        i2.inSampleSize = i;
        return i2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    protected com.p077f.p078a.p086b.p089b.C1562a.C1559a m7696a(java.lang.String r6) {
        /*
        r5 = this;
        r1 = 0;
        r0 = 1;
        r2 = new android.media.ExifInterface;	 Catch:{ IOException -> 0x002b }
        r3 = com.p077f.p078a.p086b.p091d.C1572b.C1574a.FILE;	 Catch:{ IOException -> 0x002b }
        r3 = r3.m7805c(r6);	 Catch:{ IOException -> 0x002b }
        r2.<init>(r3);	 Catch:{ IOException -> 0x002b }
        r3 = "Orientation";
        r4 = 1;
        r2 = r2.getAttributeInt(r3, r4);	 Catch:{ IOException -> 0x002b }
        switch(r2) {
            case 1: goto L_0x0018;
            case 2: goto L_0x0019;
            case 3: goto L_0x0023;
            case 4: goto L_0x0024;
            case 5: goto L_0x0028;
            case 6: goto L_0x001f;
            case 7: goto L_0x0020;
            case 8: goto L_0x0027;
            default: goto L_0x0018;
        };
    L_0x0018:
        r0 = r1;
    L_0x0019:
        r2 = new com.f.a.b.b.a$a;
        r2.<init>(r1, r0);
        return r2;
    L_0x001f:
        r0 = r1;
    L_0x0020:
        r1 = 90;
        goto L_0x0019;
    L_0x0023:
        r0 = r1;
    L_0x0024:
        r1 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        goto L_0x0019;
    L_0x0027:
        r0 = r1;
    L_0x0028:
        r1 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        goto L_0x0019;
    L_0x002b:
        r2 = move-exception;
        r2 = "Can't read EXIF tags from file [%s]";
        r0 = new java.lang.Object[r0];
        r0[r1] = r6;
        com.p077f.p078a.p095c.C1602c.m7941c(r2, r0);
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.f.a.b.b.a.a(java.lang.String):com.f.a.b.b.a$a");
    }

    /* renamed from: a */
    protected C1560b m7697a(InputStream inputStream, C1563c c1563c) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        String b = c1563c.m7704b();
        C1559a a = (c1563c.m7710h() && m7692a(b, options.outMimeType)) ? m7696a(b) : new C1559a();
        return new C1560b(new C1553e(options.outWidth, options.outHeight, a.f4722a), a);
    }

    /* renamed from: b */
    protected InputStream m7698b(C1563c c1563c) {
        return c1563c.m7708f().mo1227a(c1563c.m7704b(), c1563c.m7709g());
    }

    /* renamed from: b */
    protected InputStream m7699b(InputStream inputStream, C1563c c1563c) {
        if (inputStream.markSupported()) {
            try {
                inputStream.reset();
                return inputStream;
            } catch (IOException e) {
            }
        }
        C1601b.m7930a((Closeable) inputStream);
        return m7698b(c1563c);
    }
}
