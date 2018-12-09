package com.persianswitch.p122a.p123a.p125a;

import com.persianswitch.p126b.C2115h;
import com.persianswitch.p126b.C2243e;
import com.persianswitch.p126b.C2244c;
import com.persianswitch.p126b.C2245f;
import com.persianswitch.p126b.C2249k;
import com.persianswitch.p126b.C2253l;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/* renamed from: com.persianswitch.a.a.a.k */
class C2118k {
    /* renamed from: a */
    private final C2249k f6427a;
    /* renamed from: b */
    private int f6428b;
    /* renamed from: c */
    private final C2243e f6429c = C2253l.m10358a(this.f6427a);

    /* renamed from: com.persianswitch.a.a.a.k$2 */
    class C21172 extends Inflater {
        /* renamed from: a */
        final /* synthetic */ C2118k f6426a;

        C21172(C2118k c2118k) {
            this.f6426a = c2118k;
        }

        public int inflate(byte[] bArr, int i, int i2) {
            int inflate = super.inflate(bArr, i, i2);
            if (inflate != 0 || !needsDictionary()) {
                return inflate;
            }
            setDictionary(C2125o.f6446a);
            return super.inflate(bArr, i, i2);
        }
    }

    public C2118k(C2243e c2243e) {
        this.f6427a = new C2249k(new C2115h(this, c2243e) {
            /* renamed from: a */
            final /* synthetic */ C2118k f6425a;

            /* renamed from: a */
            public long mo3105a(C2244c c2244c, long j) {
                if (this.f6425a.f6428b == 0) {
                    return -1;
                }
                long a = super.mo3105a(c2244c, Math.min(j, (long) this.f6425a.f6428b));
                if (a == -1) {
                    return -1;
                }
                this.f6425a.f6428b = (int) (((long) this.f6425a.f6428b) - a);
                return a;
            }
        }, new C21172(this));
    }

    /* renamed from: b */
    private C2245f m9557b() {
        return this.f6429c.mo3180c((long) this.f6429c.mo3190j());
    }

    /* renamed from: c */
    private void m9558c() {
        if (this.f6428b > 0) {
            this.f6427a.m10351b();
            if (this.f6428b != 0) {
                throw new IOException("compressedLimit > 0: " + this.f6428b);
            }
        }
    }

    /* renamed from: a */
    public List<C2102f> m9559a(int i) {
        this.f6428b += i;
        int j = this.f6429c.mo3190j();
        if (j < 0) {
            throw new IOException("numberOfPairs < 0: " + j);
        } else if (j > 1024) {
            throw new IOException("numberOfPairs > 1024: " + j);
        } else {
            List<C2102f> arrayList = new ArrayList(j);
            for (int i2 = 0; i2 < j; i2++) {
                C2245f d = m9557b().mo3215d();
                C2245f b = m9557b();
                if (d.mo3216e() == 0) {
                    throw new IOException("name.size == 0");
                }
                arrayList.add(new C2102f(d, b));
            }
            m9558c();
            return arrayList;
        }
    }

    /* renamed from: a */
    public void m9560a() {
        this.f6429c.close();
    }
}
