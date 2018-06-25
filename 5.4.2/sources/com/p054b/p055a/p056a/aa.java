package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1324g;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.b.a.a.aa */
public class aa extends C1260a {
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3663c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3664l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3665m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3666n = null;
    /* renamed from: o */
    private static final /* synthetic */ C2428a f3667o = null;
    /* renamed from: a */
    private int f3668a = 0;
    /* renamed from: b */
    private int[] f3669b = new int[3];

    static {
        aa.m6536d();
    }

    public aa() {
        super("vmhd");
        m6532d(1);
    }

    /* renamed from: d */
    private static /* synthetic */ void m6536d() {
        C2441b c2441b = new C2441b("VideoMediaHeaderBox.java", aa.class);
        f3663c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getGraphicsmode", "com.coremedia.iso.boxes.VideoMediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 39);
        f3664l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getOpcolor", "com.coremedia.iso.boxes.VideoMediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "[I"), 43);
        f3665m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.VideoMediaHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 71);
        f3666n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setOpcolor", "com.coremedia.iso.boxes.VideoMediaHeaderBox", "[I", "opcolor", TtmlNode.ANONYMOUS_REGION_ID, "void"), 75);
        f3667o = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setGraphicsmode", "com.coremedia.iso.boxes.VideoMediaHeaderBox", "int", "graphicsmode", TtmlNode.ANONYMOUS_REGION_ID, "void"), 79);
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        this.f3668a = C1290e.m6670c(byteBuffer);
        this.f3669b = new int[3];
        for (int i = 0; i < 3; i++) {
            this.f3669b[i] = C1290e.m6670c(byteBuffer);
        }
    }

    /* renamed from: b */
    public int mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3663c, (Object) this, (Object) this));
        return this.f3668a;
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        C1291f.m6683b(byteBuffer, this.f3668a);
        for (int b : this.f3669b) {
            C1291f.m6683b(byteBuffer, b);
        }
    }

    protected long b_() {
        return 12;
    }

    /* renamed from: c */
    public int[] mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3664l, (Object) this, (Object) this));
        return this.f3669b;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3665m, (Object) this, (Object) this));
        return "VideoMediaHeaderBox[graphicsmode=" + mo1112b() + ";opcolor0=" + mo1115c()[0] + ";opcolor1=" + mo1115c()[1] + ";opcolor2=" + mo1115c()[2] + "]";
    }
}
