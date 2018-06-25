package com.p069d.p070a.p071a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.p060a.p061a.p062a.C1298c;
import com.p057c.p058a.p060a.p061a.p062a.C1299d;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.d.a.a.b */
public class C1480b {
    /* renamed from: a */
    public int f4480a;
    /* renamed from: b */
    public int f4481b;
    /* renamed from: c */
    public int f4482c;
    /* renamed from: d */
    public int f4483d;
    /* renamed from: e */
    public int f4484e;
    /* renamed from: f */
    public List<byte[]> f4485f = new ArrayList();
    /* renamed from: g */
    public List<byte[]> f4486g = new ArrayList();
    /* renamed from: h */
    public boolean f4487h = true;
    /* renamed from: i */
    public int f4488i = 1;
    /* renamed from: j */
    public int f4489j = 0;
    /* renamed from: k */
    public int f4490k = 0;
    /* renamed from: l */
    public List<byte[]> f4491l = new ArrayList();
    /* renamed from: m */
    public int f4492m = 63;
    /* renamed from: n */
    public int f4493n = 7;
    /* renamed from: o */
    public int f4494o = 31;
    /* renamed from: p */
    public int f4495p = 31;
    /* renamed from: q */
    public int f4496q = 31;

    public C1480b(ByteBuffer byteBuffer) {
        int i;
        int i2 = 0;
        this.f4480a = C1290e.m6671d(byteBuffer);
        this.f4481b = C1290e.m6671d(byteBuffer);
        this.f4482c = C1290e.m6671d(byteBuffer);
        this.f4483d = C1290e.m6671d(byteBuffer);
        C1298c c1298c = new C1298c(byteBuffer);
        this.f4492m = c1298c.m6713a(6);
        this.f4484e = c1298c.m6713a(2);
        this.f4493n = c1298c.m6713a(3);
        int a = c1298c.m6713a(5);
        for (i = 0; i < a; i++) {
            Object obj = new byte[C1290e.m6670c(byteBuffer)];
            byteBuffer.get(obj);
            this.f4485f.add(obj);
        }
        long d = (long) C1290e.m6671d(byteBuffer);
        for (i = 0; ((long) i) < d; i++) {
            Object obj2 = new byte[C1290e.m6670c(byteBuffer)];
            byteBuffer.get(obj2);
            this.f4486g.add(obj2);
        }
        if (byteBuffer.remaining() < 4) {
            this.f4487h = false;
        }
        if (this.f4487h && (this.f4481b == 100 || this.f4481b == 110 || this.f4481b == 122 || this.f4481b == 144)) {
            c1298c = new C1298c(byteBuffer);
            this.f4494o = c1298c.m6713a(6);
            this.f4488i = c1298c.m6713a(2);
            this.f4495p = c1298c.m6713a(5);
            this.f4489j = c1298c.m6713a(3);
            this.f4496q = c1298c.m6713a(5);
            this.f4490k = c1298c.m6713a(3);
            d = (long) C1290e.m6671d(byteBuffer);
            while (((long) i2) < d) {
                Object obj3 = new byte[C1290e.m6670c(byteBuffer)];
                byteBuffer.get(obj3);
                this.f4491l.add(obj3);
                i2++;
            }
            return;
        }
        this.f4488i = -1;
        this.f4489j = -1;
        this.f4490k = -1;
    }

    /* renamed from: a */
    public long m7331a() {
        long j = 5 + 1;
        long j2 = j;
        for (byte[] length : this.f4485f) {
            j2 = ((long) length.length) + (j2 + 2);
        }
        j = j2 + 1;
        j2 = j;
        for (byte[] length2 : this.f4486g) {
            j2 = ((long) length2.length) + (j2 + 2);
        }
        if (this.f4487h && (this.f4481b == 100 || this.f4481b == 110 || this.f4481b == 122 || this.f4481b == 144)) {
            j = 4 + j2;
            j2 = j;
            for (byte[] length22 : this.f4491l) {
                j2 = ((long) length22.length) + (j2 + 2);
            }
        }
        return j2;
    }

    /* renamed from: a */
    public void m7332a(ByteBuffer byteBuffer) {
        C1291f.m6687c(byteBuffer, this.f4480a);
        C1291f.m6687c(byteBuffer, this.f4481b);
        C1291f.m6687c(byteBuffer, this.f4482c);
        C1291f.m6687c(byteBuffer, this.f4483d);
        C1299d c1299d = new C1299d(byteBuffer);
        c1299d.m6716a(this.f4492m, 6);
        c1299d.m6716a(this.f4484e, 2);
        c1299d.m6716a(this.f4493n, 3);
        c1299d.m6716a(this.f4486g.size(), 5);
        for (byte[] bArr : this.f4485f) {
            C1291f.m6683b(byteBuffer, bArr.length);
            byteBuffer.put(bArr);
        }
        C1291f.m6687c(byteBuffer, this.f4486g.size());
        for (byte[] bArr2 : this.f4486g) {
            C1291f.m6683b(byteBuffer, bArr2.length);
            byteBuffer.put(bArr2);
        }
        if (!this.f4487h) {
            return;
        }
        if (this.f4481b == 100 || this.f4481b == 110 || this.f4481b == 122 || this.f4481b == 144) {
            c1299d = new C1299d(byteBuffer);
            c1299d.m6716a(this.f4494o, 6);
            c1299d.m6716a(this.f4488i, 2);
            c1299d.m6716a(this.f4495p, 5);
            c1299d.m6716a(this.f4489j, 3);
            c1299d.m6716a(this.f4496q, 5);
            c1299d.m6716a(this.f4490k, 3);
            for (byte[] bArr22 : this.f4491l) {
                C1291f.m6683b(byteBuffer, bArr22.length);
                byteBuffer.put(bArr22);
            }
        }
    }

    public String toString() {
        return "AvcDecoderConfigurationRecord{configurationVersion=" + this.f4480a + ", avcProfileIndication=" + this.f4481b + ", profileCompatibility=" + this.f4482c + ", avcLevelIndication=" + this.f4483d + ", lengthSizeMinusOne=" + this.f4484e + ", hasExts=" + this.f4487h + ", chromaFormat=" + this.f4488i + ", bitDepthLumaMinus8=" + this.f4489j + ", bitDepthChromaMinus8=" + this.f4490k + ", lengthSizeMinusOnePaddingBits=" + this.f4492m + ", numberOfSequenceParameterSetsPaddingBits=" + this.f4493n + ", chromaFormatPaddingBits=" + this.f4494o + ", bitDepthLumaMinus8PaddingBits=" + this.f4495p + ", bitDepthChromaMinus8PaddingBits=" + this.f4496q + '}';
    }
}
