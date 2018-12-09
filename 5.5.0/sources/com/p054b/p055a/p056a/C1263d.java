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

/* renamed from: com.b.a.a.d */
public class C1263d extends C1259c {
    /* renamed from: b */
    static final /* synthetic */ boolean f3673b = (!C1263d.class.desiredAssertionStatus());
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3674c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3675l = null;
    /* renamed from: a */
    List<C1262a> f3676a = Collections.emptyList();

    /* renamed from: com.b.a.a.d$a */
    public static class C1262a {
        /* renamed from: a */
        int f3671a;
        /* renamed from: b */
        int f3672b;

        public C1262a(int i, int i2) {
            this.f3671a = i;
            this.f3672b = i2;
        }

        /* renamed from: a */
        public int m6543a() {
            return this.f3671a;
        }

        /* renamed from: a */
        public void m6544a(int i) {
            this.f3671a = i;
        }

        /* renamed from: b */
        public int m6545b() {
            return this.f3672b;
        }

        public String toString() {
            return "Entry{count=" + this.f3671a + ", offset=" + this.f3672b + '}';
        }
    }

    static {
        C1263d.m6546b();
    }

    public C1263d() {
        super("ctts");
    }

    /* renamed from: b */
    private static /* synthetic */ void m6546b() {
        C2441b c2441b = new C2441b("CompositionTimeToSample.java", C1263d.class);
        f3674c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getEntries", "com.coremedia.iso.boxes.CompositionTimeToSample", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.List"), 57);
        f3675l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setEntries", "com.coremedia.iso.boxes.CompositionTimeToSample", "java.util.List", "entries", TtmlNode.ANONYMOUS_REGION_ID, "void"), 61);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        int a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        this.f3676a = new ArrayList(a);
        for (int i = 0; i < a; i++) {
            this.f3676a.add(new C1262a(C1315b.m6756a(C1290e.m6667a(byteBuffer)), byteBuffer.getInt()));
        }
    }

    /* renamed from: a */
    public void m6548a(List<C1262a> list) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3675l, this, this, list));
        this.f3676a = list;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, (long) this.f3676a.size());
        for (C1262a c1262a : this.f3676a) {
            C1291f.m6684b(byteBuffer, (long) c1262a.m6543a());
            byteBuffer.putInt(c1262a.m6545b());
        }
    }

    protected long b_() {
        return (long) ((this.f3676a.size() * 8) + 8);
    }
}
