package com.p096g.p097a;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* renamed from: com.g.a.v */
final class C1648v {
    /* renamed from: a */
    static final StringBuilder f5087a = new StringBuilder();

    @TargetApi(12)
    /* renamed from: com.g.a.v$a */
    private static class C1647a {
        /* renamed from: a */
        static int m8054a(Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    /* renamed from: a */
    static int m8055a(Bitmap bitmap) {
        int a = VERSION.SDK_INT >= 12 ? C1647a.m8054a(bitmap) : bitmap.getRowBytes() * bitmap.getHeight();
        if (a >= 0) {
            return a;
        }
        throw new IllegalStateException("Negative size: " + bitmap);
    }

    /* renamed from: a */
    static <T> T m8056a(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    /* renamed from: a */
    static String m8057a(C1615b c1615b) {
        return C1648v.m8058a(c1615b, TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    static String m8058a(C1615b c1615b, String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        C1607a d = c1615b.m7975d();
        if (d != null) {
            stringBuilder.append(d.f4914b.m8028a());
        }
        List e = c1615b.m7976e();
        if (e != null) {
            int size = e.size();
            for (int i = 0; i < size; i++) {
                if (i > 0 || d != null) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(((C1607a) e.get(i)).f4914b.m8028a());
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    static String m8059a(C1640o c1640o) {
        String a = C1648v.m8060a(c1640o, f5087a);
        f5087a.setLength(0);
        return a;
    }

    /* renamed from: a */
    static String m8060a(C1640o c1640o, StringBuilder stringBuilder) {
        if (c1640o.f5030f != null) {
            stringBuilder.ensureCapacity(c1640o.f5030f.length() + 50);
            stringBuilder.append(c1640o.f5030f);
        } else if (c1640o.f5028d != null) {
            String uri = c1640o.f5028d.toString();
            stringBuilder.ensureCapacity(uri.length() + 50);
            stringBuilder.append(uri);
        } else {
            stringBuilder.ensureCapacity(50);
            stringBuilder.append(c1640o.f5029e);
        }
        stringBuilder.append('\n');
        if (c1640o.f5037m != BitmapDescriptorFactory.HUE_RED) {
            stringBuilder.append("rotation:").append(c1640o.f5037m);
            if (c1640o.f5040p) {
                stringBuilder.append('@').append(c1640o.f5038n).append('x').append(c1640o.f5039o);
            }
            stringBuilder.append('\n');
        }
        if (c1640o.m8031d()) {
            stringBuilder.append("resize:").append(c1640o.f5032h).append('x').append(c1640o.f5033i);
            stringBuilder.append('\n');
        }
        if (c1640o.f5034j) {
            stringBuilder.append("centerCrop").append('\n');
        } else if (c1640o.f5035k) {
            stringBuilder.append("centerInside").append('\n');
        }
        if (c1640o.f5031g != null) {
            int size = c1640o.f5031g.size();
            for (int i = 0; i < size; i++) {
                stringBuilder.append(((C1646u) c1640o.f5031g.get(i)).m8053a());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    /* renamed from: a */
    static void m8061a() {
        if (!C1648v.m8065b()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
    }

    /* renamed from: a */
    static void m8062a(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /* renamed from: a */
    static void m8063a(String str, String str2, String str3) {
        C1648v.m8064a(str, str2, str3, TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    static void m8064a(String str, String str2, String str3, String str4) {
        Log.d("Picasso", String.format("%1$-11s %2$-12s %3$s %4$s", new Object[]{str, str2, str3, str4}));
    }

    /* renamed from: b */
    static boolean m8065b() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /* renamed from: b */
    static byte[] m8066b(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    /* renamed from: c */
    static boolean m8067c(InputStream inputStream) {
        byte[] bArr = new byte[12];
        return inputStream.read(bArr, 0, 12) == 12 && "RIFF".equals(new String(bArr, 0, 4, "US-ASCII")) && "WEBP".equals(new String(bArr, 8, 4, "US-ASCII"));
    }
}
