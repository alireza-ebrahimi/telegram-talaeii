package com.p054b.p055a.p056a;

import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.c */
public abstract class C1261c extends C1259c {
    /* renamed from: a */
    private static final /* synthetic */ C2428a f3670a = null;

    static {
        C1261c.m6541b();
    }

    public C1261c(String str) {
        super(str);
    }

    /* renamed from: b */
    private static /* synthetic */ void m6541b() {
        C2441b c2441b = new C2441b("ChunkOffsetBox.java", C1261c.class);
        f3670a = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.ChunkOffsetBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 18);
    }

    /* renamed from: a */
    public abstract long[] mo1116a();

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3670a, (Object) this, (Object) this));
        return new StringBuilder(String.valueOf(getClass().getSimpleName())).append("[entryCount=").append(mo1116a().length).append("]").toString();
    }
}
