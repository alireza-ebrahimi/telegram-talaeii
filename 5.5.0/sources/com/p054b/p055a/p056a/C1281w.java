package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.w */
public class C1281w extends C1259c {
    /* renamed from: b */
    private static final /* synthetic */ C2428a f3788b = null;
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3789c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3790l = null;
    /* renamed from: a */
    private long[] f3791a;

    static {
        C1281w.m6621b();
    }

    public C1281w() {
        super("stss");
    }

    /* renamed from: b */
    private static /* synthetic */ void m6621b() {
        C2441b c2441b = new C2441b("SyncSampleBox.java", C1281w.class);
        f3788b = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSampleNumber", "com.coremedia.iso.boxes.SyncSampleBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "[J"), 46);
        f3789c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.SyncSampleBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 77);
        f3790l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setSampleNumber", "com.coremedia.iso.boxes.SyncSampleBox", "[J", "sampleNumber", TtmlNode.ANONYMOUS_REGION_ID, "void"), 81);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        int a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        this.f3791a = new long[a];
        for (int i = 0; i < a; i++) {
            this.f3791a[i] = C1290e.m6667a(byteBuffer);
        }
    }

    /* renamed from: a */
    public void m6623a(long[] jArr) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3790l, this, this, jArr));
        this.f3791a = jArr;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, (long) this.f3791a.length);
        for (long b : this.f3791a) {
            C1291f.m6684b(byteBuffer, b);
        }
    }

    protected long b_() {
        return (long) ((this.f3791a.length * 4) + 8);
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3789c, (Object) this, (Object) this));
        return "SyncSampleBox[entryCount=" + this.f3791a.length + "]";
    }
}
