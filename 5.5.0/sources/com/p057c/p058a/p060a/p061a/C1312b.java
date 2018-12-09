package com.p057c.p058a.p060a.p061a;

import com.p057c.p058a.C1324g;
import com.p057c.p058a.p060a.p061a.p062a.C1296b;
import com.p057c.p058a.p060a.p061a.p062a.C1303h;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.c.a.a.a.b */
public class C1312b extends C1311a {
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3974c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3975l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3976m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3977n = null;

    static {
        C1312b.m6749b();
    }

    public C1312b() {
        super("esds");
    }

    /* renamed from: b */
    private static /* synthetic */ void m6749b() {
        C2441b c2441b = new C2441b("ESDescriptorBox.java", C1312b.class);
        f3974c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getEsDescriptor", "com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor"), 33);
        f3975l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setEsDescriptor", "com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox", "com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor", "esDescriptor", TtmlNode.ANONYMOUS_REGION_ID, "void"), 37);
        f3976m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "equals", "com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox", "java.lang.Object", "o", TtmlNode.ANONYMOUS_REGION_ID, "boolean"), 42);
        f3977n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "hashCode", "com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 53);
    }

    /* renamed from: a */
    public void m6750a(C1303h c1303h) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3975l, this, this, c1303h));
        super.m6745a((C1296b) c1303h);
    }

    public boolean equals(Object obj) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3976m, this, this, obj));
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C1312b c1312b = (C1312b) obj;
        if (this.b != null) {
            if (this.b.equals(c1312b.b)) {
                return true;
            }
        } else if (c1312b.b == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3977n, (Object) this, (Object) this));
        return this.b != null ? this.b.hashCode() : 0;
    }
}
