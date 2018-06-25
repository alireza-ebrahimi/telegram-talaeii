package com.p054b.p055a.p056a;

import com.p054b.p055a.C1289d;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1257a;
import com.p057c.p058a.C1324g;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.i */
public class C1267i extends C1257a {
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3680l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3681m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3682n = null;
    /* renamed from: o */
    private static final /* synthetic */ C2428a f3683o = null;
    /* renamed from: p */
    private static final /* synthetic */ C2428a f3684p = null;
    /* renamed from: q */
    private static final /* synthetic */ C2428a f3685q = null;
    /* renamed from: a */
    private String f3686a;
    /* renamed from: b */
    private long f3687b;
    /* renamed from: c */
    private List<String> f3688c = Collections.emptyList();

    static {
        C1267i.m6553d();
    }

    public C1267i() {
        super("ftyp");
    }

    public C1267i(String str, long j, List<String> list) {
        super("ftyp");
        this.f3686a = str;
        this.f3687b = j;
        this.f3688c = list;
    }

    /* renamed from: d */
    private static /* synthetic */ void m6553d() {
        C2441b c2441b = new C2441b("FileTypeBox.java", C1267i.class);
        f3680l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getMajorBrand", "com.coremedia.iso.boxes.FileTypeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 85);
        f3681m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setMajorBrand", "com.coremedia.iso.boxes.FileTypeBox", "java.lang.String", "majorBrand", TtmlNode.ANONYMOUS_REGION_ID, "void"), 94);
        f3682n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setMinorVersion", "com.coremedia.iso.boxes.FileTypeBox", "long", "minorVersion", TtmlNode.ANONYMOUS_REGION_ID, "void"), 103);
        f3683o = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getMinorVersion", "com.coremedia.iso.boxes.FileTypeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 113);
        f3684p = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getCompatibleBrands", "com.coremedia.iso.boxes.FileTypeBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.List"), 122);
        f3685q = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setCompatibleBrands", "com.coremedia.iso.boxes.FileTypeBox", "java.util.List", "compatibleBrands", TtmlNode.ANONYMOUS_REGION_ID, "void"), 126);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        this.f3686a = C1290e.m6677j(byteBuffer);
        this.f3687b = C1290e.m6667a(byteBuffer);
        int remaining = byteBuffer.remaining() / 4;
        this.f3688c = new LinkedList();
        for (int i = 0; i < remaining; i++) {
            this.f3688c.add(C1290e.m6677j(byteBuffer));
        }
    }

    /* renamed from: b */
    public String mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3680l, (Object) this, (Object) this));
        return this.f3686a;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        byteBuffer.put(C1289d.m6665a(this.f3686a));
        C1291f.m6684b(byteBuffer, this.f3687b);
        for (String a : this.f3688c) {
            byteBuffer.put(C1289d.m6665a(a));
        }
    }

    protected long b_() {
        return (long) ((this.f3688c.size() * 4) + 8);
    }

    /* renamed from: c */
    public long mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3683o, (Object) this, (Object) this));
        return this.f3687b;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FileTypeBox[");
        stringBuilder.append("majorBrand=").append(mo1112b());
        stringBuilder.append(";");
        stringBuilder.append("minorVersion=").append(mo1115c());
        for (String str : this.f3688c) {
            stringBuilder.append(";");
            stringBuilder.append("compatibleBrand=").append(str);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
