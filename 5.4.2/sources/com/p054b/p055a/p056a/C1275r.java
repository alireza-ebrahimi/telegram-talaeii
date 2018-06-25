package com.p054b.p055a.p056a;

import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.r */
public class C1275r extends C1259c {
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3764l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3765m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3766n = null;
    /* renamed from: o */
    private static final /* synthetic */ C2428a f3767o = null;
    /* renamed from: p */
    private static final /* synthetic */ C2428a f3768p = null;
    /* renamed from: q */
    private static final /* synthetic */ C2428a f3769q = null;
    /* renamed from: r */
    private static final /* synthetic */ C2428a f3770r = null;
    /* renamed from: a */
    int f3771a;
    /* renamed from: b */
    private long f3772b;
    /* renamed from: c */
    private long[] f3773c = new long[0];

    static {
        C1275r.m6596e();
    }

    public C1275r() {
        super("stsz");
    }

    /* renamed from: e */
    private static /* synthetic */ void m6596e() {
        C2441b c2441b = new C2441b("SampleSizeBox.java", C1275r.class);
        f3764l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSampleSize", "com.coremedia.iso.boxes.SampleSizeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 50);
        f3765m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setSampleSize", "com.coremedia.iso.boxes.SampleSizeBox", "long", "sampleSize", TtmlNode.ANONYMOUS_REGION_ID, "void"), 54);
        f3766n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSampleSizeAtIndex", "com.coremedia.iso.boxes.SampleSizeBox", "int", C1797b.INDEX, TtmlNode.ANONYMOUS_REGION_ID, "long"), 59);
        f3767o = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSampleCount", "com.coremedia.iso.boxes.SampleSizeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 67);
        f3768p = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSampleSizes", "com.coremedia.iso.boxes.SampleSizeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "[J"), 76);
        f3769q = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setSampleSizes", "com.coremedia.iso.boxes.SampleSizeBox", "[J", "sampleSizes", TtmlNode.ANONYMOUS_REGION_ID, "void"), 80);
        f3770r = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.SampleSizeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 119);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        this.f3772b = C1290e.m6667a(byteBuffer);
        this.f3771a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        if (this.f3772b == 0) {
            this.f3773c = new long[this.f3771a];
            for (int i = 0; i < this.f3771a; i++) {
                this.f3773c[i] = C1290e.m6667a(byteBuffer);
            }
        }
    }

    /* renamed from: a */
    public void m6598a(long[] jArr) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3769q, this, this, jArr));
        this.f3773c = jArr;
    }

    /* renamed from: b */
    public long mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3764l, (Object) this, (Object) this));
        return this.f3772b;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, this.f3772b);
        if (this.f3772b == 0) {
            C1291f.m6684b(byteBuffer, (long) this.f3773c.length);
            for (long b : this.f3773c) {
                C1291f.m6684b(byteBuffer, b);
            }
            return;
        }
        C1291f.m6684b(byteBuffer, (long) this.f3771a);
    }

    protected long b_() {
        return (long) ((this.f3772b == 0 ? this.f3773c.length * 4 : 0) + 12);
    }

    /* renamed from: c */
    public long mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3767o, (Object) this, (Object) this));
        return this.f3772b > 0 ? (long) this.f3771a : (long) this.f3773c.length;
    }

    /* renamed from: d */
    public long[] m6602d() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3768p, (Object) this, (Object) this));
        return this.f3773c;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3770r, (Object) this, (Object) this));
        return "SampleSizeBox[sampleSize=" + mo1112b() + ";sampleCount=" + mo1115c() + "]";
    }
}
