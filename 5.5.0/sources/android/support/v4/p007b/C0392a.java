package android.support.v4.p007b;

import android.graphics.Color;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/* renamed from: android.support.v4.b.a */
public final class C0392a {
    /* renamed from: a */
    private static final ThreadLocal<double[]> f1156a = new ThreadLocal();

    /* renamed from: a */
    public static double m1823a(int i) {
        double[] a = C0392a.m1832a();
        C0392a.m1830a(i, a);
        return a[1] / 100.0d;
    }

    /* renamed from: a */
    private static float m1824a(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    /* renamed from: a */
    public static int m1825a(int i, int i2) {
        int alpha = Color.alpha(i2);
        int alpha2 = Color.alpha(i);
        int d = C0392a.m1835d(alpha2, alpha);
        return Color.argb(d, C0392a.m1827a(Color.red(i), alpha2, Color.red(i2), alpha, d), C0392a.m1827a(Color.green(i), alpha2, Color.green(i2), alpha, d), C0392a.m1827a(Color.blue(i), alpha2, Color.blue(i2), alpha, d));
    }

    /* renamed from: a */
    public static int m1826a(int i, int i2, float f) {
        int i3 = 0;
        int i4 = 255;
        if (Color.alpha(i2) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2));
        } else if (C0392a.m1833b(C0392a.m1834c(i, 255), i2) < ((double) f)) {
            return -1;
        } else {
            int i5 = 0;
            while (i5 <= 10 && i4 - i3 > 1) {
                int i6 = (i3 + i4) / 2;
                if (C0392a.m1833b(C0392a.m1834c(i, i6), i2) >= ((double) f)) {
                    i4 = i6;
                    i6 = i3;
                }
                i5++;
                i3 = i6;
            }
            return i4;
        }
    }

    /* renamed from: a */
    private static int m1827a(int i, int i2, int i3, int i4, int i5) {
        return i5 == 0 ? 0 : (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    /* renamed from: a */
    public static void m1828a(int i, int i2, int i3, double[] dArr) {
        if (dArr.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d = ((double) i) / 255.0d;
        double pow = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
        d = ((double) i2) / 255.0d;
        double pow2 = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
        d = ((double) i3) / 255.0d;
        d = d < 0.04045d ? d / 12.92d : Math.pow((d + 0.055d) / 1.055d, 2.4d);
        dArr[0] = 100.0d * (((0.4124d * pow) + (0.3576d * pow2)) + (0.1805d * d));
        dArr[1] = 100.0d * (((0.2126d * pow) + (0.7152d * pow2)) + (0.0722d * d));
        dArr[2] = ((d * 0.9505d) + ((pow2 * 0.1192d) + (pow * 0.0193d))) * 100.0d;
    }

    /* renamed from: a */
    public static void m1829a(int i, int i2, int i3, float[] fArr) {
        float f = ((float) i) / 255.0f;
        float f2 = ((float) i2) / 255.0f;
        float f3 = ((float) i3) / 255.0f;
        float max = Math.max(f, Math.max(f2, f3));
        float min = Math.min(f, Math.min(f2, f3));
        float f4 = max - min;
        float f5 = (max + min) / 2.0f;
        if (max == min) {
            f2 = BitmapDescriptorFactory.HUE_RED;
            f = BitmapDescriptorFactory.HUE_RED;
        } else {
            f = max == f ? ((f2 - f3) / f4) % 6.0f : max == f2 ? ((f3 - f) / f4) + 2.0f : ((f - f2) / f4) + 4.0f;
            f2 = f4 / (1.0f - Math.abs((2.0f * f5) - 1.0f));
        }
        f = (f * 60.0f) % 360.0f;
        if (f < BitmapDescriptorFactory.HUE_RED) {
            f += 360.0f;
        }
        fArr[0] = C0392a.m1824a(f, (float) BitmapDescriptorFactory.HUE_RED, 360.0f);
        fArr[1] = C0392a.m1824a(f2, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
        fArr[2] = C0392a.m1824a(f5, (float) BitmapDescriptorFactory.HUE_RED, 1.0f);
    }

    /* renamed from: a */
    public static void m1830a(int i, double[] dArr) {
        C0392a.m1828a(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    /* renamed from: a */
    public static void m1831a(int i, float[] fArr) {
        C0392a.m1829a(Color.red(i), Color.green(i), Color.blue(i), fArr);
    }

    /* renamed from: a */
    private static double[] m1832a() {
        double[] dArr = (double[]) f1156a.get();
        if (dArr != null) {
            return dArr;
        }
        Object obj = new double[3];
        f1156a.set(obj);
        return obj;
    }

    /* renamed from: b */
    public static double m1833b(int i, int i2) {
        if (Color.alpha(i2) != 255) {
            throw new IllegalArgumentException("background can not be translucent: #" + Integer.toHexString(i2));
        }
        if (Color.alpha(i) < 255) {
            i = C0392a.m1825a(i, i2);
        }
        double a = C0392a.m1823a(i) + 0.05d;
        double a2 = C0392a.m1823a(i2) + 0.05d;
        return Math.max(a, a2) / Math.min(a, a2);
    }

    /* renamed from: c */
    public static int m1834c(int i, int i2) {
        if (i2 >= 0 && i2 <= 255) {
            return (16777215 & i) | (i2 << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    /* renamed from: d */
    private static int m1835d(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }
}
