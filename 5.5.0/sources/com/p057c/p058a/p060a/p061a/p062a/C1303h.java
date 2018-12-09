package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@C1302g(a = {3})
/* renamed from: com.c.a.a.a.a.h */
public class C1303h extends C1296b {
    /* renamed from: n */
    private static Logger f3943n = Logger.getLogger(C1303h.class.getName());
    /* renamed from: a */
    int f3944a;
    /* renamed from: b */
    int f3945b;
    /* renamed from: c */
    int f3946c;
    /* renamed from: d */
    int f3947d;
    /* renamed from: e */
    int f3948e;
    /* renamed from: f */
    int f3949f = 0;
    /* renamed from: g */
    String f3950g;
    /* renamed from: h */
    int f3951h;
    /* renamed from: i */
    int f3952i;
    /* renamed from: j */
    int f3953j;
    /* renamed from: k */
    C1300e f3954k;
    /* renamed from: l */
    C1309n f3955l;
    /* renamed from: m */
    List<C1296b> f3956m = new ArrayList();

    /* renamed from: a */
    public int m6729a() {
        int i = 5;
        if (this.f3945b > 0) {
            i = 7;
        }
        if (this.f3946c > 0) {
            i += this.f3949f + 1;
        }
        if (this.f3947d > 0) {
            i += 2;
        }
        return (i + this.f3954k.m6717a()) + this.f3955l.m6739a();
    }

    /* renamed from: a */
    public void m6730a(int i) {
        this.f3944a = i;
    }

    /* renamed from: a */
    public void m6731a(C1300e c1300e) {
        this.f3954k = c1300e;
    }

    /* renamed from: a */
    public void m6732a(C1309n c1309n) {
        this.f3955l = c1309n;
    }

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        C1296b a;
        long position;
        int i = 0;
        this.f3944a = C1290e.m6670c(byteBuffer);
        int d = C1290e.m6671d(byteBuffer);
        this.f3945b = d >>> 7;
        this.f3946c = (d >>> 6) & 1;
        this.f3947d = (d >>> 5) & 1;
        this.f3948e = d & 31;
        if (this.f3945b == 1) {
            this.f3952i = C1290e.m6670c(byteBuffer);
        }
        if (this.f3946c == 1) {
            this.f3949f = C1290e.m6671d(byteBuffer);
            this.f3950g = C1290e.m6668a(byteBuffer, this.f3949f);
        }
        if (this.f3947d == 1) {
            this.f3953j = C1290e.m6670c(byteBuffer);
        }
        d = (this.f3946c == 1 ? this.f3949f + 1 : 0) + ((((m6698e() + 1) + 2) + 1) + (this.f3945b == 1 ? 2 : 0));
        if (this.f3947d == 1) {
            i = 2;
        }
        int i2 = d + i;
        int position2 = byteBuffer.position();
        if (m6696c() > i2 + 2) {
            a = C1307l.m6737a(-1, byteBuffer);
            position = (long) (byteBuffer.position() - position2);
            f3943n.finer(a + " - ESDescriptor1 read: " + position + ", size: " + (a != null ? Integer.valueOf(a.m6696c()) : null));
            if (a != null) {
                i = a.m6696c();
                byteBuffer.position(position2 + i);
                i += i2;
            } else {
                i = (int) (((long) i2) + position);
            }
            if (a instanceof C1300e) {
                this.f3954k = (C1300e) a;
            }
        } else {
            i = i2;
        }
        position2 = byteBuffer.position();
        if (m6696c() > i + 2) {
            a = C1307l.m6737a(-1, byteBuffer);
            position = (long) (byteBuffer.position() - position2);
            f3943n.finer(a + " - ESDescriptor2 read: " + position + ", size: " + (a != null ? Integer.valueOf(a.m6696c()) : null));
            if (a != null) {
                i2 = a.m6696c();
                byteBuffer.position(position2 + i2);
                i += i2;
            } else {
                i = (int) (((long) i) + position);
            }
            if (a instanceof C1309n) {
                this.f3955l = (C1309n) a;
            }
        } else {
            f3943n.warning("SLConfigDescriptor is missing!");
        }
        while (m6696c() - i > 2) {
            i2 = byteBuffer.position();
            C1296b a2 = C1307l.m6737a(-1, byteBuffer);
            position = (long) (byteBuffer.position() - i2);
            f3943n.finer(a2 + " - ESDescriptor3 read: " + position + ", size: " + (a2 != null ? Integer.valueOf(a2.m6696c()) : null));
            if (a2 != null) {
                d = a2.m6696c();
                byteBuffer.position(i2 + d);
                i += d;
            } else {
                i = (int) (position + ((long) i));
            }
            this.f3956m.add(a2);
        }
    }

    /* renamed from: b */
    public ByteBuffer m6734b() {
        ByteBuffer allocate = ByteBuffer.allocate(m6729a());
        C1291f.m6687c(allocate, 3);
        C1291f.m6687c(allocate, m6729a() - 2);
        C1291f.m6683b(allocate, this.f3944a);
        C1291f.m6687c(allocate, (((this.f3945b << 7) | (this.f3946c << 6)) | (this.f3947d << 5)) | (this.f3948e & 31));
        if (this.f3945b > 0) {
            C1291f.m6683b(allocate, this.f3952i);
        }
        if (this.f3946c > 0) {
            C1291f.m6687c(allocate, this.f3949f);
            C1291f.m6685b(allocate, this.f3950g);
        }
        if (this.f3947d > 0) {
            C1291f.m6683b(allocate, this.f3953j);
        }
        ByteBuffer b = this.f3954k.m6722b();
        ByteBuffer b2 = this.f3955l.m6742b();
        allocate.put(b.array());
        allocate.put(b2.array());
        return allocate;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1303h c1303h = (C1303h) obj;
        if (this.f3946c != c1303h.f3946c) {
            return false;
        }
        if (this.f3949f != c1303h.f3949f) {
            return false;
        }
        if (this.f3952i != c1303h.f3952i) {
            return false;
        }
        if (this.f3944a != c1303h.f3944a) {
            return false;
        }
        if (this.f3953j != c1303h.f3953j) {
            return false;
        }
        if (this.f3947d != c1303h.f3947d) {
            return false;
        }
        if (this.f3951h != c1303h.f3951h) {
            return false;
        }
        if (this.f3945b != c1303h.f3945b) {
            return false;
        }
        if (this.f3948e != c1303h.f3948e) {
            return false;
        }
        if (this.f3950g == null ? c1303h.f3950g != null : !this.f3950g.equals(c1303h.f3950g)) {
            return false;
        }
        if (this.f3954k == null ? c1303h.f3954k != null : !this.f3954k.equals(c1303h.f3954k)) {
            return false;
        }
        if (this.f3956m == null ? c1303h.f3956m != null : !this.f3956m.equals(c1303h.f3956m)) {
            return false;
        }
        if (this.f3955l != null) {
            if (this.f3955l.equals(c1303h.f3955l)) {
                return true;
            }
        } else if (c1303h.f3955l == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.f3955l != null ? this.f3955l.hashCode() : 0) + (((this.f3954k != null ? this.f3954k.hashCode() : 0) + (((((((((this.f3950g != null ? this.f3950g.hashCode() : 0) + (((((((((((this.f3944a * 31) + this.f3945b) * 31) + this.f3946c) * 31) + this.f3947d) * 31) + this.f3948e) * 31) + this.f3949f) * 31)) * 31) + this.f3951h) * 31) + this.f3952i) * 31) + this.f3953j) * 31)) * 31)) * 31;
        if (this.f3956m != null) {
            i = this.f3956m.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ESDescriptor");
        stringBuilder.append("{esId=").append(this.f3944a);
        stringBuilder.append(", streamDependenceFlag=").append(this.f3945b);
        stringBuilder.append(", URLFlag=").append(this.f3946c);
        stringBuilder.append(", oCRstreamFlag=").append(this.f3947d);
        stringBuilder.append(", streamPriority=").append(this.f3948e);
        stringBuilder.append(", URLLength=").append(this.f3949f);
        stringBuilder.append(", URLString='").append(this.f3950g).append('\'');
        stringBuilder.append(", remoteODFlag=").append(this.f3951h);
        stringBuilder.append(", dependsOnEsId=").append(this.f3952i);
        stringBuilder.append(", oCREsId=").append(this.f3953j);
        stringBuilder.append(", decoderConfigDescriptor=").append(this.f3954k);
        stringBuilder.append(", slConfigDescriptor=").append(this.f3955l);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
