package android.support.v7.p015d;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.p007b.C0392a;
import android.support.v4.p022f.C0464a;
import android.util.SparseBooleanArray;
import android.util.TimingLogger;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: android.support.v7.d.b */
public final class C0834b {
    /* renamed from: a */
    static final C0830b f1975a = new C08311();
    /* renamed from: b */
    private final List<C0833c> f1976b;
    /* renamed from: c */
    private final List<C0835c> f1977c;
    /* renamed from: d */
    private final Map<C0835c, C0833c> f1978d = new C0464a();
    /* renamed from: e */
    private final SparseBooleanArray f1979e = new SparseBooleanArray();
    /* renamed from: f */
    private final C0833c f1980f = m3987b();

    /* renamed from: android.support.v7.d.b$b */
    public interface C0830b {
        /* renamed from: a */
        boolean mo702a(int i, float[] fArr);
    }

    /* renamed from: android.support.v7.d.b$1 */
    static class C08311 implements C0830b {
        C08311() {
        }

        /* renamed from: a */
        private boolean m3971a(float[] fArr) {
            return fArr[2] <= 0.05f;
        }

        /* renamed from: b */
        private boolean m3972b(float[] fArr) {
            return fArr[2] >= 0.95f;
        }

        /* renamed from: c */
        private boolean m3973c(float[] fArr) {
            return fArr[0] >= 10.0f && fArr[0] <= 37.0f && fArr[1] <= 0.82f;
        }

        /* renamed from: a */
        public boolean mo702a(int i, float[] fArr) {
            return (m3972b(fArr) || m3971a(fArr) || m3973c(fArr)) ? false : true;
        }
    }

    /* renamed from: android.support.v7.d.b$a */
    public static final class C0832a {
        /* renamed from: a */
        private final List<C0833c> f1958a;
        /* renamed from: b */
        private final Bitmap f1959b;
        /* renamed from: c */
        private final List<C0835c> f1960c = new ArrayList();
        /* renamed from: d */
        private int f1961d = 16;
        /* renamed from: e */
        private int f1962e = 12544;
        /* renamed from: f */
        private int f1963f = -1;
        /* renamed from: g */
        private final List<C0830b> f1964g = new ArrayList();
        /* renamed from: h */
        private Rect f1965h;

        public C0832a(Bitmap bitmap) {
            if (bitmap == null || bitmap.isRecycled()) {
                throw new IllegalArgumentException("Bitmap is not valid");
            }
            this.f1964g.add(C0834b.f1975a);
            this.f1959b = bitmap;
            this.f1958a = null;
            this.f1960c.add(C0835c.f1981a);
            this.f1960c.add(C0835c.f1982b);
            this.f1960c.add(C0835c.f1983c);
            this.f1960c.add(C0835c.f1984d);
            this.f1960c.add(C0835c.f1985e);
            this.f1960c.add(C0835c.f1986f);
        }

        /* renamed from: a */
        private int[] m3975a(Bitmap bitmap) {
            int i = 0;
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Object obj = new int[(width * height)];
            bitmap.getPixels(obj, 0, width, 0, 0, width, height);
            if (this.f1965h == null) {
                return obj;
            }
            int width2 = this.f1965h.width();
            int height2 = this.f1965h.height();
            Object obj2 = new int[(width2 * height2)];
            while (i < height2) {
                System.arraycopy(obj, ((this.f1965h.top + i) * width) + this.f1965h.left, obj2, i * width2, width2);
                i++;
            }
            return obj2;
        }

        /* renamed from: b */
        private Bitmap m3976b(Bitmap bitmap) {
            double d = -1.0d;
            int width;
            if (this.f1962e > 0) {
                width = bitmap.getWidth() * bitmap.getHeight();
                if (width > this.f1962e) {
                    d = Math.sqrt(((double) this.f1962e) / ((double) width));
                }
            } else if (this.f1963f > 0) {
                width = Math.max(bitmap.getWidth(), bitmap.getHeight());
                if (width > this.f1963f) {
                    d = ((double) this.f1963f) / ((double) width);
                }
            }
            return d <= 0.0d ? bitmap : Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(((double) bitmap.getWidth()) * d), (int) Math.ceil(d * ((double) bitmap.getHeight())), false);
        }

        /* renamed from: a */
        public C0834b m3977a() {
            List a;
            TimingLogger timingLogger = null;
            if (this.f1959b != null) {
                Bitmap b = m3976b(this.f1959b);
                if (timingLogger != null) {
                    timingLogger.addSplit("Processed Bitmap");
                }
                Rect rect = this.f1965h;
                if (!(b == this.f1959b || rect == null)) {
                    double width = ((double) b.getWidth()) / ((double) this.f1959b.getWidth());
                    rect.left = (int) Math.floor(((double) rect.left) * width);
                    rect.top = (int) Math.floor(((double) rect.top) * width);
                    rect.right = Math.min((int) Math.ceil(((double) rect.right) * width), b.getWidth());
                    rect.bottom = Math.min((int) Math.ceil(width * ((double) rect.bottom)), b.getHeight());
                }
                C0829a c0829a = new C0829a(m3975a(b), this.f1961d, this.f1964g.isEmpty() ? timingLogger : (C0830b[]) this.f1964g.toArray(new C0830b[this.f1964g.size()]));
                if (b != this.f1959b) {
                    b.recycle();
                }
                a = c0829a.m3969a();
                if (timingLogger != null) {
                    timingLogger.addSplit("Color quantization completed");
                }
            } else {
                a = this.f1958a;
            }
            C0834b c0834b = new C0834b(a, this.f1960c);
            c0834b.m3993a();
            if (timingLogger != null) {
                timingLogger.addSplit("Created Palette");
                timingLogger.dumpToLog();
            }
            return c0834b;
        }
    }

    /* renamed from: android.support.v7.d.b$c */
    public static final class C0833c {
        /* renamed from: a */
        private final int f1966a;
        /* renamed from: b */
        private final int f1967b;
        /* renamed from: c */
        private final int f1968c;
        /* renamed from: d */
        private final int f1969d;
        /* renamed from: e */
        private final int f1970e;
        /* renamed from: f */
        private boolean f1971f;
        /* renamed from: g */
        private int f1972g;
        /* renamed from: h */
        private int f1973h;
        /* renamed from: i */
        private float[] f1974i;

        public C0833c(int i, int i2) {
            this.f1966a = Color.red(i);
            this.f1967b = Color.green(i);
            this.f1968c = Color.blue(i);
            this.f1969d = i;
            this.f1970e = i2;
        }

        /* renamed from: f */
        private void m3978f() {
            if (!this.f1971f) {
                int a = C0392a.m1826a(-1, this.f1969d, 4.5f);
                int a2 = C0392a.m1826a(-1, this.f1969d, 3.0f);
                if (a == -1 || a2 == -1) {
                    int a3 = C0392a.m1826a((int) Theme.ACTION_BAR_VIDEO_EDIT_COLOR, this.f1969d, 4.5f);
                    int a4 = C0392a.m1826a((int) Theme.ACTION_BAR_VIDEO_EDIT_COLOR, this.f1969d, 3.0f);
                    if (a3 == -1 || a3 == -1) {
                        this.f1973h = a != -1 ? C0392a.m1834c(-1, a) : C0392a.m1834c(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, a3);
                        this.f1972g = a2 != -1 ? C0392a.m1834c(-1, a2) : C0392a.m1834c(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, a4);
                        this.f1971f = true;
                        return;
                    }
                    this.f1973h = C0392a.m1834c(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, a3);
                    this.f1972g = C0392a.m1834c(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, a4);
                    this.f1971f = true;
                    return;
                }
                this.f1973h = C0392a.m1834c(-1, a);
                this.f1972g = C0392a.m1834c(-1, a2);
                this.f1971f = true;
            }
        }

        /* renamed from: a */
        public int m3979a() {
            return this.f1969d;
        }

        /* renamed from: b */
        public float[] m3980b() {
            if (this.f1974i == null) {
                this.f1974i = new float[3];
            }
            C0392a.m1829a(this.f1966a, this.f1967b, this.f1968c, this.f1974i);
            return this.f1974i;
        }

        /* renamed from: c */
        public int m3981c() {
            return this.f1970e;
        }

        /* renamed from: d */
        public int m3982d() {
            m3978f();
            return this.f1972g;
        }

        /* renamed from: e */
        public int m3983e() {
            m3978f();
            return this.f1973h;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            C0833c c0833c = (C0833c) obj;
            return this.f1970e == c0833c.f1970e && this.f1969d == c0833c.f1969d;
        }

        public int hashCode() {
            return (this.f1969d * 31) + this.f1970e;
        }

        public String toString() {
            return new StringBuilder(getClass().getSimpleName()).append(" [RGB: #").append(Integer.toHexString(m3979a())).append(']').append(" [HSL: ").append(Arrays.toString(m3980b())).append(']').append(" [Population: ").append(this.f1970e).append(']').append(" [Title Text: #").append(Integer.toHexString(m3982d())).append(']').append(" [Body Text: #").append(Integer.toHexString(m3983e())).append(']').toString();
        }
    }

    C0834b(List<C0833c> list, List<C0835c> list2) {
        this.f1976b = list;
        this.f1977c = list2;
    }

    /* renamed from: a */
    public static C0832a m3984a(Bitmap bitmap) {
        return new C0832a(bitmap);
    }

    /* renamed from: a */
    private boolean m3985a(C0833c c0833c, C0835c c0835c) {
        float[] b = c0833c.m3980b();
        return b[1] >= c0835c.m4001a() && b[1] <= c0835c.m4003c() && b[2] >= c0835c.m4004d() && b[2] <= c0835c.m4006f() && !this.f1979e.get(c0833c.m3979a());
    }

    /* renamed from: b */
    private float m3986b(C0833c c0833c, C0835c c0835c) {
        float f = BitmapDescriptorFactory.HUE_RED;
        float[] b = c0833c.m3980b();
        int c = this.f1980f != null ? this.f1980f.m3981c() : 1;
        float abs = c0835c.m4007g() > BitmapDescriptorFactory.HUE_RED ? (1.0f - Math.abs(b[1] - c0835c.m4002b())) * c0835c.m4007g() : BitmapDescriptorFactory.HUE_RED;
        float abs2 = c0835c.m4008h() > BitmapDescriptorFactory.HUE_RED ? (1.0f - Math.abs(b[2] - c0835c.m4005e())) * c0835c.m4008h() : BitmapDescriptorFactory.HUE_RED;
        if (c0835c.m4009i() > BitmapDescriptorFactory.HUE_RED) {
            f = c0835c.m4009i() * (((float) c0833c.m3981c()) / ((float) c));
        }
        return (abs + abs2) + f;
    }

    /* renamed from: b */
    private C0833c m3987b() {
        int i = Integer.MIN_VALUE;
        C0833c c0833c = null;
        int size = this.f1976b.size();
        int i2 = 0;
        while (i2 < size) {
            int c;
            C0833c c0833c2 = (C0833c) this.f1976b.get(i2);
            if (c0833c2.m3981c() > i) {
                c = c0833c2.m3981c();
            } else {
                c0833c2 = c0833c;
                c = i;
            }
            i2++;
            i = c;
            c0833c = c0833c2;
        }
        return c0833c;
    }

    /* renamed from: b */
    private C0833c m3988b(C0835c c0835c) {
        C0833c c = m3989c(c0835c);
        if (c != null && c0835c.m4010j()) {
            this.f1979e.append(c.m3979a(), true);
        }
        return c;
    }

    /* renamed from: c */
    private C0833c m3989c(C0835c c0835c) {
        float f = BitmapDescriptorFactory.HUE_RED;
        C0833c c0833c = null;
        int size = this.f1976b.size();
        int i = 0;
        while (i < size) {
            float f2;
            C0833c c0833c2 = (C0833c) this.f1976b.get(i);
            if (m3985a(c0833c2, c0835c)) {
                float b = m3986b(c0833c2, c0835c);
                if (c0833c == null || b > f) {
                    f2 = b;
                    i++;
                    f = f2;
                    c0833c = c0833c2;
                }
            }
            c0833c2 = c0833c;
            f2 = f;
            i++;
            f = f2;
            c0833c = c0833c2;
        }
        return c0833c;
    }

    /* renamed from: a */
    public int m3990a(int i) {
        return m3991a(C0835c.f1986f, i);
    }

    /* renamed from: a */
    public int m3991a(C0835c c0835c, int i) {
        C0833c a = m3992a(c0835c);
        return a != null ? a.m3979a() : i;
    }

    /* renamed from: a */
    public C0833c m3992a(C0835c c0835c) {
        return (C0833c) this.f1978d.get(c0835c);
    }

    /* renamed from: a */
    void m3993a() {
        int size = this.f1977c.size();
        for (int i = 0; i < size; i++) {
            C0835c c0835c = (C0835c) this.f1977c.get(i);
            c0835c.m4011k();
            this.f1978d.put(c0835c, m3988b(c0835c));
        }
        this.f1979e.clear();
    }
}
