package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1315b;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.x */
public class C1283x extends C1259c {
    /* renamed from: b */
    static Map<List<C1282a>, SoftReference<long[]>> f3794b = new WeakHashMap();
    /* renamed from: c */
    static final /* synthetic */ boolean f3795c = (!C1283x.class.desiredAssertionStatus());
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3796l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3797m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3798n = null;
    /* renamed from: a */
    List<C1282a> f3799a = Collections.emptyList();

    /* renamed from: com.b.a.a.x$a */
    public static class C1282a {
        /* renamed from: a */
        long f3792a;
        /* renamed from: b */
        long f3793b;

        public C1282a(long j, long j2) {
            this.f3792a = j;
            this.f3793b = j2;
        }

        /* renamed from: a */
        public long m6625a() {
            return this.f3792a;
        }

        /* renamed from: a */
        public void m6626a(long j) {
            this.f3792a = j;
        }

        /* renamed from: b */
        public long m6627b() {
            return this.f3793b;
        }

        public String toString() {
            return "Entry{count=" + this.f3792a + ", delta=" + this.f3793b + '}';
        }
    }

    static {
        C1283x.m6628b();
    }

    public C1283x() {
        super("stts");
    }

    /* renamed from: b */
    private static /* synthetic */ void m6628b() {
        C2441b c2441b = new C2441b("TimeToSampleBox.java", C1283x.class);
        f3796l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getEntries", "com.coremedia.iso.boxes.TimeToSampleBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.List"), 79);
        f3797m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setEntries", "com.coremedia.iso.boxes.TimeToSampleBox", "java.util.List", "entries", TtmlNode.ANONYMOUS_REGION_ID, "void"), 83);
        f3798n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.TimeToSampleBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 87);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        int a = C1315b.m6756a(C1290e.m6667a(byteBuffer));
        this.f3799a = new ArrayList(a);
        for (int i = 0; i < a; i++) {
            this.f3799a.add(new C1282a(C1290e.m6667a(byteBuffer), C1290e.m6667a(byteBuffer)));
        }
    }

    /* renamed from: a */
    public void m6630a(List<C1282a> list) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3797m, this, this, list));
        this.f3799a = list;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, (long) this.f3799a.size());
        for (C1282a c1282a : this.f3799a) {
            C1291f.m6684b(byteBuffer, c1282a.m6625a());
            C1291f.m6684b(byteBuffer, c1282a.m6627b());
        }
    }

    protected long b_() {
        return (long) ((this.f3799a.size() * 8) + 8);
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3798n, (Object) this, (Object) this));
        return "TimeToSampleBox[entryCount=" + this.f3799a.size() + "]";
    }
}
