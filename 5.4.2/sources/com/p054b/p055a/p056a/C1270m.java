package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1316c;
import java.nio.ByteBuffer;
import java.util.Date;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p142a.C2437a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.m */
public class C1270m extends C1259c {
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3703n = null;
    /* renamed from: o */
    private static final /* synthetic */ C2428a f3704o = null;
    /* renamed from: p */
    private static final /* synthetic */ C2428a f3705p = null;
    /* renamed from: q */
    private static final /* synthetic */ C2428a f3706q = null;
    /* renamed from: r */
    private static final /* synthetic */ C2428a f3707r = null;
    /* renamed from: s */
    private static final /* synthetic */ C2428a f3708s = null;
    /* renamed from: t */
    private static final /* synthetic */ C2428a f3709t = null;
    /* renamed from: u */
    private static final /* synthetic */ C2428a f3710u = null;
    /* renamed from: v */
    private static final /* synthetic */ C2428a f3711v = null;
    /* renamed from: w */
    private static final /* synthetic */ C2428a f3712w = null;
    /* renamed from: x */
    private static final /* synthetic */ C2428a f3713x = null;
    /* renamed from: a */
    private Date f3714a = new Date();
    /* renamed from: b */
    private Date f3715b = new Date();
    /* renamed from: c */
    private long f3716c;
    /* renamed from: l */
    private long f3717l;
    /* renamed from: m */
    private String f3718m = "eng";

    static {
        C1270m.m6567g();
    }

    public C1270m() {
        super("mdhd");
    }

    /* renamed from: g */
    private static /* synthetic */ void m6567g() {
        C2441b c2441b = new C2441b("MediaHeaderBox.java", C1270m.class);
        f3703n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getCreationTime", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.Date"), 46);
        f3704o = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getModificationTime", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.Date"), 50);
        f3713x = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 118);
        f3705p = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getTimescale", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 54);
        f3706q = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getDuration", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 58);
        f3707r = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getLanguage", "com.coremedia.iso.boxes.MediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 62);
        f3708s = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setCreationTime", "com.coremedia.iso.boxes.MediaHeaderBox", "java.util.Date", "creationTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 79);
        f3709t = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setModificationTime", "com.coremedia.iso.boxes.MediaHeaderBox", "java.util.Date", "modificationTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 83);
        f3710u = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setTimescale", "com.coremedia.iso.boxes.MediaHeaderBox", "long", "timescale", TtmlNode.ANONYMOUS_REGION_ID, "void"), 87);
        f3711v = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setDuration", "com.coremedia.iso.boxes.MediaHeaderBox", "long", "duration", TtmlNode.ANONYMOUS_REGION_ID, "void"), 91);
        f3712w = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setLanguage", "com.coremedia.iso.boxes.MediaHeaderBox", "java.lang.String", "language", TtmlNode.ANONYMOUS_REGION_ID, "void"), 95);
    }

    /* renamed from: a */
    public void m6568a(long j) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3710u, this, this, C2437a.m11947a(j)));
        this.f3716c = j;
    }

    /* renamed from: a */
    public void m6569a(String str) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3712w, this, this, str));
        this.f3718m = str;
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        if (m6534o() == 1) {
            this.f3714a = C1316c.m6758a(C1290e.m6672e(byteBuffer));
            this.f3715b = C1316c.m6758a(C1290e.m6672e(byteBuffer));
            this.f3716c = C1290e.m6667a(byteBuffer);
            this.f3717l = C1290e.m6672e(byteBuffer);
        } else {
            this.f3714a = C1316c.m6758a(C1290e.m6667a(byteBuffer));
            this.f3715b = C1316c.m6758a(C1290e.m6667a(byteBuffer));
            this.f3716c = C1290e.m6667a(byteBuffer);
            this.f3717l = C1290e.m6667a(byteBuffer);
        }
        this.f3718m = C1290e.m6676i(byteBuffer);
        C1290e.m6670c(byteBuffer);
    }

    /* renamed from: a */
    public void m6571a(Date date) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3708s, this, this, date));
        this.f3714a = date;
    }

    /* renamed from: b */
    public Date mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3703n, (Object) this, (Object) this));
        return this.f3714a;
    }

    /* renamed from: b */
    public void m6573b(long j) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3711v, this, this, C2437a.m11947a(j)));
        this.f3717l = j;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        if (m6534o() == 1) {
            C1291f.m6680a(byteBuffer, C1316c.m6757a(this.f3714a));
            C1291f.m6680a(byteBuffer, C1316c.m6757a(this.f3715b));
            C1291f.m6684b(byteBuffer, this.f3716c);
            C1291f.m6680a(byteBuffer, this.f3717l);
        } else {
            C1291f.m6684b(byteBuffer, C1316c.m6757a(this.f3714a));
            C1291f.m6684b(byteBuffer, C1316c.m6757a(this.f3715b));
            C1291f.m6684b(byteBuffer, this.f3716c);
            C1291f.m6684b(byteBuffer, this.f3717l);
        }
        C1291f.m6681a(byteBuffer, this.f3718m);
        C1291f.m6683b(byteBuffer, 0);
    }

    protected long b_() {
        return ((m6534o() == 1 ? 4 + 28 : 4 + 16) + 2) + 2;
    }

    /* renamed from: c */
    public Date mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3704o, (Object) this, (Object) this));
        return this.f3715b;
    }

    /* renamed from: d */
    public long m6576d() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3705p, (Object) this, (Object) this));
        return this.f3716c;
    }

    /* renamed from: e */
    public long m6577e() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3706q, (Object) this, (Object) this));
        return this.f3717l;
    }

    /* renamed from: f */
    public String m6578f() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3707r, (Object) this, (Object) this));
        return this.f3718m;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3713x, (Object) this, (Object) this));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaHeaderBox[");
        stringBuilder.append("creationTime=").append(mo1112b());
        stringBuilder.append(";");
        stringBuilder.append("modificationTime=").append(mo1115c());
        stringBuilder.append(";");
        stringBuilder.append("timescale=").append(m6576d());
        stringBuilder.append(";");
        stringBuilder.append("duration=").append(m6577e());
        stringBuilder.append(";");
        stringBuilder.append("language=").append(m6578f());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
