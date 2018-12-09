package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.t */
public class C1278t extends C1259c {
    /* renamed from: b */
    private static final /* synthetic */ C2428a f3777b = null;
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3778c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3779l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3780m = null;
    /* renamed from: a */
    List<C1277a> f3781a = Collections.emptyList();

    /* renamed from: com.b.a.a.t$a */
    public static class C1277a {
        /* renamed from: a */
        long f3774a;
        /* renamed from: b */
        long f3775b;
        /* renamed from: c */
        long f3776c;

        public C1277a(long j, long j2, long j3) {
            this.f3774a = j;
            this.f3775b = j2;
            this.f3776c = j3;
        }

        /* renamed from: a */
        public long m6604a() {
            return this.f3774a;
        }

        /* renamed from: b */
        public long m6605b() {
            return this.f3775b;
        }

        /* renamed from: c */
        public long m6606c() {
            return this.f3776c;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            C1277a c1277a = (C1277a) obj;
            return this.f3774a != c1277a.f3774a ? false : this.f3776c != c1277a.f3776c ? false : this.f3775b == c1277a.f3775b;
        }

        public int hashCode() {
            return (((((int) (this.f3774a ^ (this.f3774a >>> 32))) * 31) + ((int) (this.f3775b ^ (this.f3775b >>> 32)))) * 31) + ((int) (this.f3776c ^ (this.f3776c >>> 32)));
        }

        public String toString() {
            return "Entry{firstChunk=" + this.f3774a + ", samplesPerChunk=" + this.f3775b + ", sampleDescriptionIndex=" + this.f3776c + '}';
        }
    }

    static {
        C1278t.m6607c();
    }

    public C1278t() {
        super("stsc");
    }

    /* renamed from: c */
    private static /* synthetic */ void m6607c() {
        C2441b c2441b = new C2441b("SampleToChunkBox.java", C1278t.class);
        f3777b = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getEntries", "com.coremedia.iso.boxes.SampleToChunkBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.List"), 47);
        f3778c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setEntries", "com.coremedia.iso.boxes.SampleToChunkBox", "java.util.List", "entries", TtmlNode.ANONYMOUS_REGION_ID, "void"), 51);
        f3779l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.SampleToChunkBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 84);
        f3780m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "blowup", "com.coremedia.iso.boxes.SampleToChunkBox", "int", "chunkCount", TtmlNode.ANONYMOUS_REGION_ID, "[J"), 95);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        int a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        this.f3781a = new ArrayList(a);
        for (int i = 0; i < a; i++) {
            this.f3781a.add(new C1277a(C1290e.m6667a(byteBuffer), C1290e.m6667a(byteBuffer), C1290e.m6667a(byteBuffer)));
        }
    }

    /* renamed from: a */
    public void m6609a(List<C1277a> list) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3778c, this, this, list));
        this.f3781a = list;
    }

    /* renamed from: b */
    public List<C1277a> mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3777b, (Object) this, (Object) this));
        return this.f3781a;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, (long) this.f3781a.size());
        for (C1277a c1277a : this.f3781a) {
            C1291f.m6684b(byteBuffer, c1277a.m6604a());
            C1291f.m6684b(byteBuffer, c1277a.m6605b());
            C1291f.m6684b(byteBuffer, c1277a.m6606c());
        }
    }

    protected long b_() {
        return (long) ((this.f3781a.size() * 12) + 8);
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3779l, (Object) this, (Object) this));
        return "SampleToChunkBox[entryCount=" + this.f3781a.size() + "]";
    }
}
