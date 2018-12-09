package com.persianswitch.p126b;

import org.telegram.messenger.MessagesController;

/* renamed from: com.persianswitch.b.o */
final class C2257o {
    /* renamed from: a */
    final byte[] f6968a;
    /* renamed from: b */
    int f6969b;
    /* renamed from: c */
    int f6970c;
    /* renamed from: d */
    boolean f6971d;
    /* renamed from: e */
    boolean f6972e;
    /* renamed from: f */
    C2257o f6973f;
    /* renamed from: g */
    C2257o f6974g;

    C2257o() {
        this.f6968a = new byte[MessagesController.UPDATE_MASK_CHANNEL];
        this.f6972e = true;
        this.f6971d = false;
    }

    C2257o(C2257o c2257o) {
        this(c2257o.f6968a, c2257o.f6969b, c2257o.f6970c);
        c2257o.f6971d = true;
    }

    C2257o(byte[] bArr, int i, int i2) {
        this.f6968a = bArr;
        this.f6969b = i;
        this.f6970c = i2;
        this.f6972e = false;
        this.f6971d = true;
    }

    /* renamed from: a */
    public C2257o m10398a() {
        C2257o c2257o = this.f6973f != this ? this.f6973f : null;
        this.f6974g.f6973f = this.f6973f;
        this.f6973f.f6974g = this.f6974g;
        this.f6973f = null;
        this.f6974g = null;
        return c2257o;
    }

    /* renamed from: a */
    public C2257o m10399a(int i) {
        if (i <= 0 || i > this.f6970c - this.f6969b) {
            throw new IllegalArgumentException();
        }
        C2257o c2257o;
        if (i >= 1024) {
            c2257o = new C2257o(this);
        } else {
            c2257o = C2258p.m10403a();
            System.arraycopy(this.f6968a, this.f6969b, c2257o.f6968a, 0, i);
        }
        c2257o.f6970c = c2257o.f6969b + i;
        this.f6969b += i;
        this.f6974g.m10400a(c2257o);
        return c2257o;
    }

    /* renamed from: a */
    public C2257o m10400a(C2257o c2257o) {
        c2257o.f6974g = this;
        c2257o.f6973f = this.f6973f;
        this.f6973f.f6974g = c2257o;
        this.f6973f = c2257o;
        return c2257o;
    }

    /* renamed from: a */
    public void m10401a(C2257o c2257o, int i) {
        if (c2257o.f6972e) {
            if (c2257o.f6970c + i > MessagesController.UPDATE_MASK_CHANNEL) {
                if (c2257o.f6971d) {
                    throw new IllegalArgumentException();
                } else if ((c2257o.f6970c + i) - c2257o.f6969b > MessagesController.UPDATE_MASK_CHANNEL) {
                    throw new IllegalArgumentException();
                } else {
                    System.arraycopy(c2257o.f6968a, c2257o.f6969b, c2257o.f6968a, 0, c2257o.f6970c - c2257o.f6969b);
                    c2257o.f6970c -= c2257o.f6969b;
                    c2257o.f6969b = 0;
                }
            }
            System.arraycopy(this.f6968a, this.f6969b, c2257o.f6968a, c2257o.f6970c, i);
            c2257o.f6970c += i;
            this.f6969b += i;
            return;
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: b */
    public void m10402b() {
        if (this.f6974g == this) {
            throw new IllegalStateException();
        } else if (this.f6974g.f6972e) {
            int i = this.f6970c - this.f6969b;
            if (i <= (this.f6974g.f6971d ? 0 : this.f6974g.f6969b) + (8192 - this.f6974g.f6970c)) {
                m10401a(this.f6974g, i);
                m10398a();
                C2258p.m10404a(this);
            }
        }
    }
}
