package com.p072e.p073a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.p072e.p073a.C1482a.C1481a;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: com.e.a.l */
public class C1492l extends C1482a {
    /* renamed from: h */
    private static ThreadLocal<C1516a> f4518h = new ThreadLocal();
    /* renamed from: i */
    private static final ThreadLocal<ArrayList<C1492l>> f4519i = new C15111();
    /* renamed from: j */
    private static final ThreadLocal<ArrayList<C1492l>> f4520j = new C15122();
    /* renamed from: k */
    private static final ThreadLocal<ArrayList<C1492l>> f4521k = new C15133();
    /* renamed from: l */
    private static final ThreadLocal<ArrayList<C1492l>> f4522l = new C15144();
    /* renamed from: m */
    private static final ThreadLocal<ArrayList<C1492l>> f4523m = new C15155();
    /* renamed from: n */
    private static final Interpolator f4524n = new AccelerateDecelerateInterpolator();
    /* renamed from: o */
    private static final C1483k f4525o = new C1487d();
    /* renamed from: p */
    private static final C1483k f4526p = new C1484b();
    /* renamed from: z */
    private static long f4527z = 10;
    /* renamed from: A */
    private int f4528A = 0;
    /* renamed from: B */
    private int f4529B = 1;
    /* renamed from: C */
    private Interpolator f4530C = f4524n;
    /* renamed from: D */
    private ArrayList<C1517b> f4531D = null;
    /* renamed from: b */
    long f4532b;
    /* renamed from: c */
    long f4533c = -1;
    /* renamed from: d */
    int f4534d = 0;
    /* renamed from: e */
    boolean f4535e = false;
    /* renamed from: f */
    C1508j[] f4536f;
    /* renamed from: g */
    HashMap<String, C1508j> f4537g;
    /* renamed from: q */
    private boolean f4538q = false;
    /* renamed from: r */
    private int f4539r = 0;
    /* renamed from: s */
    private float f4540s = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: t */
    private boolean f4541t = false;
    /* renamed from: u */
    private long f4542u;
    /* renamed from: v */
    private boolean f4543v = false;
    /* renamed from: w */
    private boolean f4544w = false;
    /* renamed from: x */
    private long f4545x = 300;
    /* renamed from: y */
    private long f4546y = 0;

    /* renamed from: com.e.a.l$1 */
    static class C15111 extends ThreadLocal<ArrayList<C1492l>> {
        C15111() {
        }

        /* renamed from: a */
        protected ArrayList<C1492l> m7525a() {
            return new ArrayList();
        }

        protected /* synthetic */ Object initialValue() {
            return m7525a();
        }
    }

    /* renamed from: com.e.a.l$2 */
    static class C15122 extends ThreadLocal<ArrayList<C1492l>> {
        C15122() {
        }

        /* renamed from: a */
        protected ArrayList<C1492l> m7526a() {
            return new ArrayList();
        }

        protected /* synthetic */ Object initialValue() {
            return m7526a();
        }
    }

    /* renamed from: com.e.a.l$3 */
    static class C15133 extends ThreadLocal<ArrayList<C1492l>> {
        C15133() {
        }

        /* renamed from: a */
        protected ArrayList<C1492l> m7527a() {
            return new ArrayList();
        }

        protected /* synthetic */ Object initialValue() {
            return m7527a();
        }
    }

    /* renamed from: com.e.a.l$4 */
    static class C15144 extends ThreadLocal<ArrayList<C1492l>> {
        C15144() {
        }

        /* renamed from: a */
        protected ArrayList<C1492l> m7528a() {
            return new ArrayList();
        }

        protected /* synthetic */ Object initialValue() {
            return m7528a();
        }
    }

    /* renamed from: com.e.a.l$5 */
    static class C15155 extends ThreadLocal<ArrayList<C1492l>> {
        C15155() {
        }

        /* renamed from: a */
        protected ArrayList<C1492l> m7529a() {
            return new ArrayList();
        }

        protected /* synthetic */ Object initialValue() {
            return m7529a();
        }
    }

    /* renamed from: com.e.a.l$a */
    private static class C1516a extends Handler {
        private C1516a() {
        }

        public void handleMessage(Message message) {
            ArrayList arrayList;
            Object obj;
            ArrayList arrayList2;
            int size;
            int i;
            C1492l c1492l;
            ArrayList arrayList3 = (ArrayList) C1492l.f4519i.get();
            ArrayList arrayList4 = (ArrayList) C1492l.f4521k.get();
            switch (message.what) {
                case 0:
                    arrayList = (ArrayList) C1492l.f4520j.get();
                    if (arrayList3.size() > 0 || arrayList4.size() > 0) {
                        obj = null;
                    } else {
                        int i2 = 1;
                    }
                    while (arrayList.size() > 0) {
                        arrayList2 = (ArrayList) arrayList.clone();
                        arrayList.clear();
                        size = arrayList2.size();
                        for (i = 0; i < size; i++) {
                            c1492l = (C1492l) arrayList2.get(i);
                            if (c1492l.f4546y == 0) {
                                c1492l.m7392p();
                            } else {
                                arrayList4.add(c1492l);
                            }
                        }
                    }
                    break;
                case 1:
                    obj = 1;
                    break;
                default:
                    return;
            }
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            arrayList = (ArrayList) C1492l.f4523m.get();
            arrayList2 = (ArrayList) C1492l.f4522l.get();
            size = arrayList4.size();
            for (i = 0; i < size; i++) {
                c1492l = (C1492l) arrayList4.get(i);
                if (c1492l.mo1192a(currentAnimationTimeMillis)) {
                    arrayList.add(c1492l);
                }
            }
            size = arrayList.size();
            if (size > 0) {
                for (i = 0; i < size; i++) {
                    c1492l = (C1492l) arrayList.get(i);
                    c1492l.m7392p();
                    c1492l.f4543v = true;
                    arrayList4.remove(c1492l);
                }
                arrayList.clear();
            }
            i = arrayList3.size();
            int i3 = 0;
            while (i3 < i) {
                int i4;
                C1492l c1492l2 = (C1492l) arrayList3.get(i3);
                if (c1492l2.m7407e(currentAnimationTimeMillis)) {
                    arrayList2.add(c1492l2);
                }
                if (arrayList3.size() == i) {
                    i4 = i3 + 1;
                    i3 = i;
                } else {
                    i--;
                    arrayList2.remove(c1492l2);
                    i4 = i3;
                    i3 = i;
                }
                i = i3;
                i3 = i4;
            }
            if (arrayList2.size() > 0) {
                for (i3 = 0; i3 < arrayList2.size(); i3++) {
                    ((C1492l) arrayList2.get(i3)).mo1198e();
                }
                arrayList2.clear();
            }
            if (obj == null) {
                return;
            }
            if (!arrayList3.isEmpty() || !arrayList4.isEmpty()) {
                sendEmptyMessageDelayed(1, Math.max(0, C1492l.f4527z - (AnimationUtils.currentAnimationTimeMillis() - currentAnimationTimeMillis)));
            }
        }
    }

    /* renamed from: com.e.a.l$b */
    public interface C1517b {
        /* renamed from: a */
        void mo3496a(C1492l c1492l);
    }

    /* renamed from: a */
    private void m7379a(boolean z) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.f4538q = z;
        this.f4539r = 0;
        this.f4534d = 0;
        this.f4544w = true;
        this.f4541t = false;
        ((ArrayList) f4520j.get()).add(this);
        if (this.f4546y == 0) {
            m7404c(m7409g());
            this.f4534d = 0;
            this.f4543v = true;
            if (this.a != null) {
                ArrayList arrayList = (ArrayList) this.a.clone();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((C1481a) arrayList.get(i)).m7333a(this);
                }
            }
        }
        C1516a c1516a = (C1516a) f4518h.get();
        if (c1516a == null) {
            c1516a = new C1516a();
            f4518h.set(c1516a);
        }
        c1516a.sendEmptyMessage(0);
    }

    /* renamed from: a */
    private boolean mo1192a(long j) {
        if (this.f4541t) {
            long j2 = j - this.f4542u;
            if (j2 > this.f4546y) {
                this.f4532b = j - (j2 - this.f4546y);
                this.f4534d = 1;
                return true;
            }
        }
        this.f4541t = true;
        this.f4542u = j;
        return false;
    }

    /* renamed from: e */
    private void mo1198e() {
        ((ArrayList) f4519i.get()).remove(this);
        ((ArrayList) f4520j.get()).remove(this);
        ((ArrayList) f4521k.get()).remove(this);
        this.f4534d = 0;
        if (this.f4543v && this.a != null) {
            ArrayList arrayList = (ArrayList) this.a.clone();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((C1481a) arrayList.get(i)).m7334b(this);
            }
        }
        this.f4543v = false;
        this.f4544w = false;
    }

    /* renamed from: p */
    private void m7392p() {
        mo1197d();
        ((ArrayList) f4519i.get()).add(this);
        if (this.f4546y > 0 && this.a != null) {
            ArrayList arrayList = (ArrayList) this.a.clone();
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((C1481a) arrayList.get(i)).m7333a(this);
            }
        }
    }

    /* renamed from: a */
    public void mo1188a() {
        m7379a(false);
    }

    /* renamed from: a */
    void mo1193a(float f) {
        int i;
        float interpolation = this.f4530C.getInterpolation(f);
        this.f4540s = interpolation;
        for (C1508j a : this.f4536f) {
            a.mo1206a(interpolation);
        }
        if (this.f4531D != null) {
            int size = this.f4531D.size();
            for (i = 0; i < size; i++) {
                ((C1517b) this.f4531D.get(i)).mo3496a(this);
            }
        }
    }

    /* renamed from: a */
    public void m7395a(int i) {
        this.f4528A = i;
    }

    /* renamed from: a */
    public void m7396a(Interpolator interpolator) {
        if (interpolator != null) {
            this.f4530C = interpolator;
        } else {
            this.f4530C = new LinearInterpolator();
        }
    }

    /* renamed from: a */
    public void m7397a(C1517b c1517b) {
        if (this.f4531D == null) {
            this.f4531D = new ArrayList();
        }
        this.f4531D.add(c1517b);
    }

    /* renamed from: a */
    public void mo1194a(float... fArr) {
        if (fArr != null && fArr.length != 0) {
            if (this.f4536f == null || this.f4536f.length == 0) {
                m7400a(C1508j.m7493a(TtmlNode.ANONYMOUS_REGION_ID, fArr));
            } else {
                this.f4536f[0].mo1208a(fArr);
            }
            this.f4535e = false;
        }
    }

    /* renamed from: a */
    public void mo1195a(int... iArr) {
        if (iArr != null && iArr.length != 0) {
            if (this.f4536f == null || this.f4536f.length == 0) {
                m7400a(C1508j.m7494a(TtmlNode.ANONYMOUS_REGION_ID, iArr));
            } else {
                this.f4536f[0].mo1212a(iArr);
            }
            this.f4535e = false;
        }
    }

    /* renamed from: a */
    public void m7400a(C1508j... c1508jArr) {
        this.f4536f = c1508jArr;
        this.f4537g = new HashMap(r2);
        for (C1508j c1508j : c1508jArr) {
            this.f4537g.put(c1508j.m7509c(), c1508j);
        }
        this.f4535e = false;
    }

    /* renamed from: b */
    public C1492l mo1196b(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.f4545x = j;
        return this;
    }

    /* renamed from: b */
    public void mo1189b() {
        if (!((ArrayList) f4519i.get()).contains(this) && !((ArrayList) f4520j.get()).contains(this)) {
            this.f4541t = false;
            m7392p();
        } else if (!this.f4535e) {
            mo1197d();
        }
        if (this.f4528A <= 0 || (this.f4528A & 1) != 1) {
            mo1193a(1.0f);
        } else {
            mo1193a((float) BitmapDescriptorFactory.HUE_RED);
        }
        mo1198e();
    }

    /* renamed from: c */
    public /* synthetic */ C1482a mo1190c() {
        return mo1199f();
    }

    /* renamed from: c */
    public void m7404c(long j) {
        mo1197d();
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        if (this.f4534d != 1) {
            this.f4533c = j;
            this.f4534d = 2;
        }
        this.f4532b = currentAnimationTimeMillis - j;
        m7407e(currentAnimationTimeMillis);
    }

    public /* synthetic */ Object clone() {
        return mo1199f();
    }

    /* renamed from: d */
    void mo1197d() {
        if (!this.f4535e) {
            for (C1508j b : this.f4536f) {
                b.m7507b();
            }
            this.f4535e = true;
        }
    }

    /* renamed from: d */
    public void m7406d(long j) {
        this.f4546y = j;
    }

    /* renamed from: e */
    boolean m7407e(long j) {
        boolean z = false;
        if (this.f4534d == 0) {
            this.f4534d = 1;
            if (this.f4533c < 0) {
                this.f4532b = j;
            } else {
                this.f4532b = j - this.f4533c;
                this.f4533c = -1;
            }
        }
        switch (this.f4534d) {
            case 1:
            case 2:
                float f;
                float f2 = this.f4545x > 0 ? ((float) (j - this.f4532b)) / ((float) this.f4545x) : 1.0f;
                if (f2 < 1.0f) {
                    f = f2;
                } else if (this.f4539r < this.f4528A || this.f4528A == -1) {
                    if (this.a != null) {
                        int size = this.a.size();
                        for (int i = 0; i < size; i++) {
                            ((C1481a) this.a.get(i)).m7335c(this);
                        }
                    }
                    if (this.f4529B == 2) {
                        this.f4538q = !this.f4538q;
                    }
                    this.f4539r += (int) f2;
                    f = f2 % 1.0f;
                    this.f4532b += this.f4545x;
                } else {
                    f = Math.min(f2, 1.0f);
                    z = true;
                }
                if (this.f4538q) {
                    f = 1.0f - f;
                }
                mo1193a(f);
                break;
        }
        return z;
    }

    /* renamed from: f */
    public C1492l mo1199f() {
        int i = 0;
        C1492l c1492l = (C1492l) super.mo1190c();
        if (this.f4531D != null) {
            ArrayList arrayList = this.f4531D;
            c1492l.f4531D = new ArrayList();
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                c1492l.f4531D.add(arrayList.get(i2));
            }
        }
        c1492l.f4533c = -1;
        c1492l.f4538q = false;
        c1492l.f4539r = 0;
        c1492l.f4535e = false;
        c1492l.f4534d = 0;
        c1492l.f4541t = false;
        C1508j[] c1508jArr = this.f4536f;
        if (c1508jArr != null) {
            int length = c1508jArr.length;
            c1492l.f4536f = new C1508j[length];
            c1492l.f4537g = new HashMap(length);
            while (i < length) {
                C1508j a = c1508jArr[i].mo1205a();
                c1492l.f4536f[i] = a;
                c1492l.f4537g.put(a.m7509c(), a);
                i++;
            }
        }
        return c1492l;
    }

    /* renamed from: g */
    public long m7409g() {
        return (!this.f4535e || this.f4534d == 0) ? 0 : AnimationUtils.currentAnimationTimeMillis() - this.f4532b;
    }

    /* renamed from: h */
    public boolean m7410h() {
        return this.f4534d == 1 || this.f4543v;
    }

    /* renamed from: i */
    public void m7411i() {
        this.f4538q = !this.f4538q;
        if (this.f4534d == 1) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.f4532b = currentAnimationTimeMillis - (this.f4545x - (currentAnimationTimeMillis - this.f4532b));
            return;
        }
        m7379a(true);
    }

    public String toString() {
        String str = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.f4536f != null) {
            for (C1508j c1508j : this.f4536f) {
                str = str + "\n    " + c1508j.toString();
            }
        }
        return str;
    }
}
