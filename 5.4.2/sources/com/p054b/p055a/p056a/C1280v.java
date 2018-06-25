package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.v */
public class C1280v extends C1261c {
    /* renamed from: b */
    private static final /* synthetic */ C2428a f3785b = null;
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3786c = null;
    /* renamed from: a */
    private long[] f3787a = new long[0];

    static {
        C1280v.m6616b();
    }

    public C1280v() {
        super("stco");
    }

    /* renamed from: b */
    private static /* synthetic */ void m6616b() {
        C2441b c2441b = new C2441b("StaticChunkOffsetBox.java", C1280v.class);
        f3785b = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getChunkOffsets", "com.coremedia.iso.boxes.StaticChunkOffsetBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "[J"), 39);
        f3786c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setChunkOffsets", "com.coremedia.iso.boxes.StaticChunkOffsetBox", "[J", "chunkOffsets", TtmlNode.ANONYMOUS_REGION_ID, "void"), 48);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        int a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        this.f3787a = new long[a];
        for (int i = 0; i < a; i++) {
            this.f3787a[i] = C1290e.m6667a(byteBuffer);
        }
    }

    /* renamed from: a */
    public void m6618a(long[] jArr) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3786c, this, this, jArr));
        this.f3787a = jArr;
    }

    /* renamed from: a */
    public long[] mo1116a() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3785b, (Object) this, (Object) this));
        return this.f3787a;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, (long) this.f3787a.length);
        for (long b : this.f3787a) {
            C1291f.m6684b(byteBuffer, b);
        }
    }

    protected long b_() {
        return (long) ((this.f3787a.length * 4) + 8);
    }
}
