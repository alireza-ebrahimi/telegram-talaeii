package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1324g;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.u */
public class C1279u extends C1260a {
    /* renamed from: b */
    private static final /* synthetic */ C2428a f3782b = null;
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3783c = null;
    /* renamed from: a */
    private float f3784a;

    static {
        C1279u.m6612c();
    }

    public C1279u() {
        super("smhd");
    }

    /* renamed from: c */
    private static /* synthetic */ void m6612c() {
        C2441b c2441b = new C2441b("SoundMediaHeaderBox.java", C1279u.class);
        f3782b = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getBalance", "com.coremedia.iso.boxes.SoundMediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "float"), 36);
        f3783c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.SoundMediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 58);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        this.f3784a = C1290e.m6675h(byteBuffer);
        C1290e.m6670c(byteBuffer);
    }

    /* renamed from: b */
    public float mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3782b, (Object) this, (Object) this));
        return this.f3784a;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6686c(byteBuffer, (double) this.f3784a);
        C1291f.m6683b(byteBuffer, 0);
    }

    protected long b_() {
        return 8;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3783c, (Object) this, (Object) this));
        return "SoundMediaHeaderBox[balance=" + mo1112b() + "]";
    }
}
