package android.support.v7.p015d;

import android.graphics.Color;
import android.support.v4.p007b.C0392a;
import android.support.v7.p015d.C0834b.C0830b;
import android.support.v7.p015d.C0834b.C0833c;
import android.util.TimingLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.telegram.tgnet.TLRPC;

/* renamed from: android.support.v7.d.a */
final class C0829a {
    /* renamed from: g */
    private static final Comparator<C0828a> f1951g = new C08271();
    /* renamed from: a */
    final int[] f1952a;
    /* renamed from: b */
    final int[] f1953b;
    /* renamed from: c */
    final List<C0833c> f1954c;
    /* renamed from: d */
    final TimingLogger f1955d = null;
    /* renamed from: e */
    final C0830b[] f1956e;
    /* renamed from: f */
    private final float[] f1957f = new float[3];

    /* renamed from: android.support.v7.d.a$1 */
    static class C08271 implements Comparator<C0828a> {
        C08271() {
        }

        /* renamed from: a */
        public int m3946a(C0828a c0828a, C0828a c0828a2) {
            return c0828a2.m3947a() - c0828a.m3947a();
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m3946a((C0828a) obj, (C0828a) obj2);
        }
    }

    /* renamed from: android.support.v7.d.a$a */
    private class C0828a {
        /* renamed from: a */
        final /* synthetic */ C0829a f1941a;
        /* renamed from: b */
        private int f1942b;
        /* renamed from: c */
        private int f1943c;
        /* renamed from: d */
        private int f1944d;
        /* renamed from: e */
        private int f1945e;
        /* renamed from: f */
        private int f1946f;
        /* renamed from: g */
        private int f1947g;
        /* renamed from: h */
        private int f1948h;
        /* renamed from: i */
        private int f1949i;
        /* renamed from: j */
        private int f1950j;

        C0828a(C0829a c0829a, int i, int i2) {
            this.f1941a = c0829a;
            this.f1942b = i;
            this.f1943c = i2;
            m3950d();
        }

        /* renamed from: a */
        final int m3947a() {
            return (((this.f1946f - this.f1945e) + 1) * ((this.f1948h - this.f1947g) + 1)) * ((this.f1950j - this.f1949i) + 1);
        }

        /* renamed from: b */
        final boolean m3948b() {
            return m3949c() > 1;
        }

        /* renamed from: c */
        final int m3949c() {
            return (this.f1943c + 1) - this.f1942b;
        }

        /* renamed from: d */
        final void m3950d() {
            int[] iArr = this.f1941a.f1952a;
            int[] iArr2 = this.f1941a.f1953b;
            int i = Integer.MIN_VALUE;
            int i2 = 0;
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MIN_VALUE;
            for (int i8 = this.f1942b; i8 <= this.f1943c; i8++) {
                int i9 = iArr[i8];
                i2 += iArr2[i9];
                int a = C0829a.m3955a(i9);
                int b = C0829a.m3962b(i9);
                i9 = C0829a.m3964c(i9);
                if (a > i3) {
                    i3 = a;
                }
                if (a < i6) {
                    i6 = a;
                }
                if (b > i7) {
                    i7 = b;
                }
                if (b < i5) {
                    i5 = b;
                }
                if (i9 > i) {
                    i = i9;
                }
                if (i9 < i4) {
                    i4 = i9;
                }
            }
            this.f1945e = i6;
            this.f1946f = i3;
            this.f1947g = i5;
            this.f1948h = i7;
            this.f1949i = i4;
            this.f1950j = i;
            this.f1944d = i2;
        }

        /* renamed from: e */
        final C0828a m3951e() {
            if (m3948b()) {
                int g = m3953g();
                C0828a c0828a = new C0828a(this.f1941a, g + 1, this.f1943c);
                this.f1943c = g;
                m3950d();
                return c0828a;
            }
            throw new IllegalStateException("Can not split a box with only 1 color");
        }

        /* renamed from: f */
        final int m3952f() {
            int i = this.f1946f - this.f1945e;
            int i2 = this.f1948h - this.f1947g;
            int i3 = this.f1950j - this.f1949i;
            return (i < i2 || i < i3) ? (i2 < i || i2 < i3) ? -1 : -2 : -3;
        }

        /* renamed from: g */
        final int m3953g() {
            int f = m3952f();
            int[] iArr = this.f1941a.f1952a;
            int[] iArr2 = this.f1941a.f1953b;
            C0829a.m3959a(iArr, f, this.f1942b, this.f1943c);
            Arrays.sort(iArr, this.f1942b, this.f1943c + 1);
            C0829a.m3959a(iArr, f, this.f1942b, this.f1943c);
            int i = this.f1944d / 2;
            f = 0;
            for (int i2 = this.f1942b; i2 <= this.f1943c; i2++) {
                f += iArr2[iArr[i2]];
                if (f >= i) {
                    return i2;
                }
            }
            return this.f1942b;
        }

        /* renamed from: h */
        final C0833c m3954h() {
            int i = 0;
            int[] iArr = this.f1941a.f1952a;
            int[] iArr2 = this.f1941a.f1953b;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = this.f1942b; i5 <= this.f1943c; i5++) {
                int i6 = iArr[i5];
                int i7 = iArr2[i6];
                i += i7;
                i4 += C0829a.m3955a(i6) * i7;
                i3 += C0829a.m3962b(i6) * i7;
                i2 += C0829a.m3964c(i6) * i7;
            }
            return new C0833c(C0829a.m3956a(Math.round(((float) i4) / ((float) i)), Math.round(((float) i3) / ((float) i)), Math.round(((float) i2) / ((float) i))), i);
        }
    }

    C0829a(int[] iArr, int i, C0830b[] c0830bArr) {
        int i2;
        int f;
        int i3 = 0;
        this.f1956e = c0830bArr;
        int[] iArr2 = new int[TLRPC.MESSAGE_FLAG_EDITED];
        this.f1953b = iArr2;
        for (i2 = 0; i2 < iArr.length; i2++) {
            f = C0829a.m3967f(iArr[i2]);
            iArr[i2] = f;
            iArr2[f] = iArr2[f] + 1;
        }
        i2 = 0;
        f = 0;
        while (i2 < iArr2.length) {
            if (iArr2[i2] > 0 && m3966e(i2)) {
                iArr2[i2] = 0;
            }
            if (iArr2[i2] > 0) {
                f++;
            }
            i2++;
        }
        int[] iArr3 = new int[f];
        this.f1952a = iArr3;
        int i4 = 0;
        for (i2 = 0; i2 < iArr2.length; i2++) {
            if (iArr2[i2] > 0) {
                int i5 = i4 + 1;
                iArr3[i4] = i2;
                i4 = i5;
            }
        }
        if (f <= i) {
            this.f1954c = new ArrayList();
            i2 = iArr3.length;
            while (i3 < i2) {
                f = iArr3[i3];
                this.f1954c.add(new C0833c(C0829a.m3968g(f), iArr2[f]));
                i3++;
            }
            return;
        }
        this.f1954c = m3965d(i);
    }

    /* renamed from: a */
    static int m3955a(int i) {
        return (i >> 10) & 31;
    }

    /* renamed from: a */
    static int m3956a(int i, int i2, int i3) {
        return Color.rgb(C0829a.m3963b(i, 5, 8), C0829a.m3963b(i2, 5, 8), C0829a.m3963b(i3, 5, 8));
    }

    /* renamed from: a */
    private List<C0833c> m3957a(Collection<C0828a> collection) {
        List arrayList = new ArrayList(collection.size());
        for (C0828a h : collection) {
            C0833c h2 = h.m3954h();
            if (!m3961a(h2)) {
                arrayList.add(h2);
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private void m3958a(PriorityQueue<C0828a> priorityQueue, int i) {
        while (priorityQueue.size() < i) {
            C0828a c0828a = (C0828a) priorityQueue.poll();
            if (c0828a != null && c0828a.m3948b()) {
                priorityQueue.offer(c0828a.m3951e());
                priorityQueue.offer(c0828a);
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* renamed from: a */
    static void m3959a(int[] r3, int r4, int r5, int r6) {
        /*
        switch(r4) {
            case -3: goto L_0x0003;
            case -2: goto L_0x0004;
            case -1: goto L_0x001f;
            default: goto L_0x0003;
        };
    L_0x0003:
        return;
    L_0x0004:
        if (r5 > r6) goto L_0x0003;
    L_0x0006:
        r0 = r3[r5];
        r1 = android.support.v7.p015d.C0829a.m3962b(r0);
        r1 = r1 << 10;
        r2 = android.support.v7.p015d.C0829a.m3955a(r0);
        r2 = r2 << 5;
        r1 = r1 | r2;
        r0 = android.support.v7.p015d.C0829a.m3964c(r0);
        r0 = r0 | r1;
        r3[r5] = r0;
        r5 = r5 + 1;
        goto L_0x0004;
    L_0x001f:
        if (r5 > r6) goto L_0x0003;
    L_0x0021:
        r0 = r3[r5];
        r1 = android.support.v7.p015d.C0829a.m3964c(r0);
        r1 = r1 << 10;
        r2 = android.support.v7.p015d.C0829a.m3962b(r0);
        r2 = r2 << 5;
        r1 = r1 | r2;
        r0 = android.support.v7.p015d.C0829a.m3955a(r0);
        r0 = r0 | r1;
        r3[r5] = r0;
        r5 = r5 + 1;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.d.a.a(int[], int, int, int):void");
    }

    /* renamed from: a */
    private boolean m3960a(int i, float[] fArr) {
        if (this.f1956e == null || this.f1956e.length <= 0) {
            return false;
        }
        for (C0830b a : this.f1956e) {
            if (!a.mo702a(i, fArr)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private boolean m3961a(C0833c c0833c) {
        return m3960a(c0833c.m3979a(), c0833c.m3980b());
    }

    /* renamed from: b */
    static int m3962b(int i) {
        return (i >> 5) & 31;
    }

    /* renamed from: b */
    private static int m3963b(int i, int i2, int i3) {
        return (i3 > i2 ? i << (i3 - i2) : i >> (i2 - i3)) & ((1 << i3) - 1);
    }

    /* renamed from: c */
    static int m3964c(int i) {
        return i & 31;
    }

    /* renamed from: d */
    private List<C0833c> m3965d(int i) {
        Collection priorityQueue = new PriorityQueue(i, f1951g);
        priorityQueue.offer(new C0828a(this, 0, this.f1952a.length - 1));
        m3958a((PriorityQueue) priorityQueue, i);
        return m3957a(priorityQueue);
    }

    /* renamed from: e */
    private boolean m3966e(int i) {
        int g = C0829a.m3968g(i);
        C0392a.m1831a(g, this.f1957f);
        return m3960a(g, this.f1957f);
    }

    /* renamed from: f */
    private static int m3967f(int i) {
        return ((C0829a.m3963b(Color.red(i), 8, 5) << 10) | (C0829a.m3963b(Color.green(i), 8, 5) << 5)) | C0829a.m3963b(Color.blue(i), 8, 5);
    }

    /* renamed from: g */
    private static int m3968g(int i) {
        return C0829a.m3956a(C0829a.m3955a(i), C0829a.m3962b(i), C0829a.m3964c(i));
    }

    /* renamed from: a */
    List<C0833c> m3969a() {
        return this.f1954c;
    }
}
