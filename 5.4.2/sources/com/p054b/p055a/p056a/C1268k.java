package com.p054b.p055a.p056a;

import com.p054b.p055a.C1289d;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.C1293h;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.k */
public class C1268k extends C1259c {
    /* renamed from: a */
    public static final Map<String, String> f3689a;
    /* renamed from: q */
    private static final /* synthetic */ C2428a f3690q = null;
    /* renamed from: r */
    private static final /* synthetic */ C2428a f3691r = null;
    /* renamed from: s */
    private static final /* synthetic */ C2428a f3692s = null;
    /* renamed from: t */
    private static final /* synthetic */ C2428a f3693t = null;
    /* renamed from: u */
    private static final /* synthetic */ C2428a f3694u = null;
    /* renamed from: v */
    private static final /* synthetic */ C2428a f3695v = null;
    /* renamed from: b */
    private String f3696b;
    /* renamed from: c */
    private String f3697c = null;
    /* renamed from: l */
    private long f3698l;
    /* renamed from: m */
    private long f3699m;
    /* renamed from: n */
    private long f3700n;
    /* renamed from: o */
    private boolean f3701o = true;
    /* renamed from: p */
    private long f3702p;

    static {
        C1268k.m6558d();
        Map hashMap = new HashMap();
        hashMap.put("odsm", "ObjectDescriptorStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("crsm", "ClockReferenceStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("sdsm", "SceneDescriptionStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("m7sm", "MPEG7Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("ocsm", "ObjectContentInfoStream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("ipsm", "IPMP Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("mjsm", "MPEG-J Stream - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        hashMap.put("mdir", "Apple Meta Data iTunes Reader");
        hashMap.put("mp7b", "MPEG-7 binary XML");
        hashMap.put("mp7t", "MPEG-7 XML");
        hashMap.put("vide", "Video Track");
        hashMap.put("soun", "Sound Track");
        hashMap.put("hint", "Hint Track");
        hashMap.put("appl", "Apple specific");
        hashMap.put("meta", "Timed Metadata track - defined in ISO/IEC JTC1/SC29/WG11 - CODING OF MOVING PICTURES AND AUDIO");
        f3689a = Collections.unmodifiableMap(hashMap);
    }

    public C1268k() {
        super("hdlr");
    }

    /* renamed from: d */
    private static /* synthetic */ void m6558d() {
        C2441b c2441b = new C2441b("HandlerBox.java", C1268k.class);
        f3690q = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getHandlerType", "com.coremedia.iso.boxes.HandlerBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 78);
        f3691r = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setName", "com.coremedia.iso.boxes.HandlerBox", "java.lang.String", "name", TtmlNode.ANONYMOUS_REGION_ID, "void"), 87);
        f3692s = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setHandlerType", "com.coremedia.iso.boxes.HandlerBox", "java.lang.String", "handlerType", TtmlNode.ANONYMOUS_REGION_ID, "void"), 91);
        f3693t = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getName", "com.coremedia.iso.boxes.HandlerBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 95);
        f3694u = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getHumanReadableTrackType", "com.coremedia.iso.boxes.HandlerBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 99);
        f3695v = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.HandlerBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 149);
    }

    /* renamed from: a */
    public void m6559a(String str) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3691r, this, this, str));
        this.f3697c = str;
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        this.f3702p = C1290e.m6667a(byteBuffer);
        this.f3696b = C1290e.m6677j(byteBuffer);
        this.f3698l = C1290e.m6667a(byteBuffer);
        this.f3699m = C1290e.m6667a(byteBuffer);
        this.f3700n = C1290e.m6667a(byteBuffer);
        if (byteBuffer.remaining() > 0) {
            this.f3697c = C1290e.m6668a(byteBuffer, byteBuffer.remaining());
            if (this.f3697c.endsWith("\u0000")) {
                this.f3697c = this.f3697c.substring(0, this.f3697c.length() - 1);
                this.f3701o = true;
                return;
            }
            this.f3701o = false;
            return;
        }
        this.f3701o = false;
    }

    /* renamed from: b */
    public String mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3690q, (Object) this, (Object) this));
        return this.f3696b;
    }

    /* renamed from: b */
    public void m6562b(String str) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3692s, this, this, str));
        this.f3696b = str;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6684b(byteBuffer, this.f3702p);
        byteBuffer.put(C1289d.m6665a(this.f3696b));
        C1291f.m6684b(byteBuffer, this.f3698l);
        C1291f.m6684b(byteBuffer, this.f3699m);
        C1291f.m6684b(byteBuffer, this.f3700n);
        if (this.f3697c != null) {
            byteBuffer.put(C1293h.m6691a(this.f3697c));
        }
        if (this.f3701o) {
            byteBuffer.put((byte) 0);
        }
    }

    protected long b_() {
        return this.f3701o ? (long) (C1293h.m6692b(this.f3697c) + 25) : (long) (C1293h.m6692b(this.f3697c) + 24);
    }

    /* renamed from: c */
    public String mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3693t, (Object) this, (Object) this));
        return this.f3697c;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3695v, (Object) this, (Object) this));
        return "HandlerBox[handlerType=" + mo1112b() + ";name=" + mo1115c() + "]";
    }
}
