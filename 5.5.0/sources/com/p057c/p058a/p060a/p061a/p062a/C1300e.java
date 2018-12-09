package com.p057c.p058a.p060a.p061a.p062a;

import com.p054b.p055a.C1288c;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@C1302g(a = {4})
/* renamed from: com.c.a.a.a.a.e */
public class C1300e extends C1296b {
    /* renamed from: k */
    private static Logger f3931k = Logger.getLogger(C1300e.class.getName());
    /* renamed from: a */
    int f3932a;
    /* renamed from: b */
    int f3933b;
    /* renamed from: c */
    int f3934c;
    /* renamed from: d */
    int f3935d;
    /* renamed from: e */
    long f3936e;
    /* renamed from: f */
    long f3937f;
    /* renamed from: g */
    C1301f f3938g;
    /* renamed from: h */
    C1297a f3939h;
    /* renamed from: i */
    List<C1308m> f3940i = new ArrayList();
    /* renamed from: j */
    byte[] f3941j;

    /* renamed from: a */
    public int m6717a() {
        return (this.f3939h == null ? 0 : this.f3939h.m6707a()) + 15;
    }

    /* renamed from: a */
    public void m6718a(int i) {
        this.f3932a = i;
    }

    /* renamed from: a */
    public void m6719a(long j) {
        this.f3936e = j;
    }

    /* renamed from: a */
    public void m6720a(C1297a c1297a) {
        this.f3939h = c1297a;
    }

    /* renamed from: a */
    public void mo1120a(ByteBuffer byteBuffer) {
        this.f3932a = C1290e.m6671d(byteBuffer);
        int d = C1290e.m6671d(byteBuffer);
        this.f3933b = d >>> 2;
        this.f3934c = (d >> 1) & 1;
        this.f3935d = C1290e.m6669b(byteBuffer);
        this.f3936e = C1290e.m6667a(byteBuffer);
        this.f3937f = C1290e.m6667a(byteBuffer);
        if (byteBuffer.remaining() > 2) {
            d = byteBuffer.position();
            C1296b a = C1307l.m6737a(this.f3932a, byteBuffer);
            int position = byteBuffer.position() - d;
            f3931k.finer(a + " - DecoderConfigDescr1 read: " + position + ", size: " + (a != null ? Integer.valueOf(a.m6696c()) : null));
            if (a != null) {
                d = a.m6696c();
                if (position < d) {
                    this.f3941j = new byte[(d - position)];
                    byteBuffer.get(this.f3941j);
                }
            }
            if (a instanceof C1301f) {
                this.f3938g = (C1301f) a;
            }
            if (a instanceof C1297a) {
                this.f3939h = (C1297a) a;
            }
        }
        while (byteBuffer.remaining() > 2) {
            long position2 = (long) byteBuffer.position();
            C1296b a2 = C1307l.m6737a(this.f3932a, byteBuffer);
            f3931k.finer(a2 + " - DecoderConfigDescr2 read: " + (((long) byteBuffer.position()) - position2) + ", size: " + (a2 != null ? Integer.valueOf(a2.m6696c()) : null));
            if (a2 instanceof C1308m) {
                this.f3940i.add((C1308m) a2);
            }
        }
    }

    /* renamed from: b */
    public ByteBuffer m6722b() {
        ByteBuffer allocate = ByteBuffer.allocate(m6717a());
        C1291f.m6687c(allocate, 4);
        C1291f.m6687c(allocate, m6717a() - 2);
        C1291f.m6687c(allocate, this.f3932a);
        C1291f.m6687c(allocate, ((this.f3933b << 2) | (this.f3934c << 1)) | 1);
        C1291f.m6679a(allocate, this.f3935d);
        C1291f.m6684b(allocate, this.f3936e);
        C1291f.m6684b(allocate, this.f3937f);
        if (this.f3939h != null) {
            allocate.put(this.f3939h.m6710b().array());
        }
        return allocate;
    }

    /* renamed from: b */
    public void m6723b(int i) {
        this.f3933b = i;
    }

    /* renamed from: b */
    public void m6724b(long j) {
        this.f3937f = j;
    }

    /* renamed from: c */
    public void m6725c(int i) {
        this.f3935d = i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecoderConfigDescriptor");
        stringBuilder.append("{objectTypeIndication=").append(this.f3932a);
        stringBuilder.append(", streamType=").append(this.f3933b);
        stringBuilder.append(", upStream=").append(this.f3934c);
        stringBuilder.append(", bufferSizeDB=").append(this.f3935d);
        stringBuilder.append(", maxBitRate=").append(this.f3936e);
        stringBuilder.append(", avgBitRate=").append(this.f3937f);
        stringBuilder.append(", decoderSpecificInfo=").append(this.f3938g);
        stringBuilder.append(", audioSpecificInfo=").append(this.f3939h);
        stringBuilder.append(", configDescriptorDeadBytes=").append(C1288c.m6663a(this.f3941j != null ? this.f3941j : new byte[0]));
        stringBuilder.append(", profileLevelIndicationDescriptors=").append(this.f3940i == null ? "null" : Arrays.asList(new List[]{this.f3940i}).toString());
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
