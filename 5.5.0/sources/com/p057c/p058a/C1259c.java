package com.p057c.p058a;

import com.google.android.gms.dynamite.ProviderConstants;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.p056a.C1258j;
import java.nio.ByteBuffer;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p142a.C2437a;
import org.p138a.p141b.p143b.C2441b;

/* renamed from: com.c.a.c */
public abstract class C1259c extends C1257a implements C1258j {
    /* renamed from: c */
    private static final /* synthetic */ C2428a f3659c = null;
    /* renamed from: l */
    private static final /* synthetic */ C2428a f3660l = null;
    /* renamed from: a */
    private int f3661a;
    /* renamed from: b */
    private int f3662b;

    static {
        C1259c.mo1112b();
    }

    protected C1259c(String str) {
        super(str);
    }

    /* renamed from: b */
    private static /* synthetic */ void mo1112b() {
        C2441b c2441b = new C2441b("AbstractFullBox.java", C1259c.class);
        f3659c = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setVersion", "com.googlecode.mp4parser.AbstractFullBox", "int", ProviderConstants.API_COLNAME_FEATURE_VERSION, TtmlNode.ANONYMOUS_REGION_ID, "void"), 51);
        f3660l = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setFlags", "com.googlecode.mp4parser.AbstractFullBox", "int", "flags", TtmlNode.ANONYMOUS_REGION_ID, "void"), 64);
    }

    /* renamed from: c */
    protected final long mo1109c(ByteBuffer byteBuffer) {
        this.f3661a = C1290e.m6671d(byteBuffer);
        this.f3662b = C1290e.m6669b(byteBuffer);
        return 4;
    }

    /* renamed from: c */
    public void m6531c(int i) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3659c, this, this, C2437a.m11946a(i)));
        this.f3661a = i;
    }

    /* renamed from: d */
    public void m6532d(int i) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3660l, this, this, C2437a.m11946a(i)));
        this.f3662b = i;
    }

    /* renamed from: d */
    protected final void mo1110d(ByteBuffer byteBuffer) {
        C1291f.m6687c(byteBuffer, this.f3661a);
        C1291f.m6679a(byteBuffer, this.f3662b);
    }

    /* renamed from: o */
    public int m6534o() {
        if (!this.e) {
            m6526l();
        }
        return this.f3661a;
    }

    /* renamed from: p */
    public int m6535p() {
        if (!this.e) {
            m6526l();
        }
        return this.f3662b;
    }
}
