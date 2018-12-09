package com.p057c.p058a.p060a.p061a;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p060a.p061a.p062a.C1296b;
import com.p057c.p058a.p060a.p061a.p062a.C1307l;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.c.a.a.a.a */
public class C1311a extends C1259c {
    /* renamed from: c */
    private static Logger f3966c = Logger.getLogger(C1311a.class.getName());
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3967l = null;
    /* renamed from: m */
    private static final /* synthetic */ C2428a f3968m = null;
    /* renamed from: n */
    private static final /* synthetic */ C2428a f3969n = null;
    /* renamed from: o */
    private static final /* synthetic */ C2428a f3970o = null;
    /* renamed from: p */
    private static final /* synthetic */ C2428a f3971p = null;
    /* renamed from: a */
    protected C1296b f3972a;
    /* renamed from: b */
    protected ByteBuffer f3973b;

    static {
        C1311a.m6744b();
    }

    public C1311a(String str) {
        super(str);
    }

    /* renamed from: b */
    private static /* synthetic */ void m6744b() {
        C2441b c2441b = new C2441b("AbstractDescriptorBox.java", C1311a.class);
        f3967l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getData", "com.googlecode.mp4parser.boxes.mp4.AbstractDescriptorBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.nio.ByteBuffer"), 42);
        f3968m = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getDescriptor", "com.googlecode.mp4parser.boxes.mp4.AbstractDescriptorBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BaseDescriptor"), 58);
        f3969n = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getDescriptorAsString", "com.googlecode.mp4parser.boxes.mp4.AbstractDescriptorBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 62);
        f3970o = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setDescriptor", "com.googlecode.mp4parser.boxes.mp4.AbstractDescriptorBox", "com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BaseDescriptor", "descriptor", TtmlNode.ANONYMOUS_REGION_ID, "void"), 66);
        f3971p = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setData", "com.googlecode.mp4parser.boxes.mp4.AbstractDescriptorBox", "java.nio.ByteBuffer", DataBufferSafeParcelable.DATA_FIELD, TtmlNode.ANONYMOUS_REGION_ID, "void"), 70);
    }

    /* renamed from: a */
    public void m6745a(C1296b c1296b) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3970o, this, this, c1296b));
        this.f3972a = c1296b;
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        this.f3973b = byteBuffer.slice();
        byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
        try {
            this.f3973b.rewind();
            this.f3972a = C1307l.m6737a(-1, this.f3973b);
        } catch (Throwable e) {
            f3966c.log(Level.WARNING, "Error parsing ObjectDescriptor", e);
        } catch (Throwable e2) {
            f3966c.log(Level.WARNING, "Error parsing ObjectDescriptor", e2);
        }
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        this.f3973b.rewind();
        byteBuffer.put(this.f3973b);
    }

    protected long b_() {
        return (long) (this.f3973b.limit() + 4);
    }

    /* renamed from: e */
    public void m6748e(ByteBuffer byteBuffer) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3971p, this, this, byteBuffer));
        this.f3973b = byteBuffer;
    }
}
