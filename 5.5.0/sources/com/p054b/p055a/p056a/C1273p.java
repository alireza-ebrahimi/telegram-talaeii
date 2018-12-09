package com.p054b.p055a.p056a;

import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1259c;
import com.p057c.p058a.C1324g;
import com.p057c.p058a.p063b.C1316c;
import com.p057c.p058a.p063b.C1320g;
import java.nio.ByteBuffer;
import java.util.Date;
import org.p138a.p139a.C2434a.C2428a;
import org.p138a.p141b.p142a.C2437a;
import org.p138a.p141b.p143b.C2441b;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

/* renamed from: com.b.a.a.p */
public class C1273p extends C1259c {
    /* renamed from: A */
    private static final /* synthetic */ C2428a f3719A = null;
    /* renamed from: B */
    private static final /* synthetic */ C2428a f3720B = null;
    /* renamed from: C */
    private static final /* synthetic */ C2428a f3721C = null;
    /* renamed from: D */
    private static final /* synthetic */ C2428a f3722D = null;
    /* renamed from: E */
    private static final /* synthetic */ C2428a f3723E = null;
    /* renamed from: F */
    private static final /* synthetic */ C2428a f3724F = null;
    /* renamed from: G */
    private static final /* synthetic */ C2428a f3725G = null;
    /* renamed from: H */
    private static final /* synthetic */ C2428a f3726H = null;
    /* renamed from: I */
    private static final /* synthetic */ C2428a f3727I = null;
    /* renamed from: J */
    private static final /* synthetic */ C2428a f3728J = null;
    /* renamed from: K */
    private static final /* synthetic */ C2428a f3729K = null;
    /* renamed from: L */
    private static final /* synthetic */ C2428a f3730L = null;
    /* renamed from: M */
    private static final /* synthetic */ C2428a f3731M = null;
    /* renamed from: N */
    private static final /* synthetic */ C2428a f3732N = null;
    /* renamed from: O */
    private static final /* synthetic */ C2428a f3733O = null;
    /* renamed from: P */
    private static final /* synthetic */ C2428a f3734P = null;
    /* renamed from: Q */
    private static final /* synthetic */ C2428a f3735Q = null;
    /* renamed from: R */
    private static final /* synthetic */ C2428a f3736R = null;
    /* renamed from: S */
    private static final /* synthetic */ C2428a f3737S = null;
    /* renamed from: T */
    private static final /* synthetic */ C2428a f3738T = null;
    /* renamed from: U */
    private static final /* synthetic */ C2428a f3739U = null;
    /* renamed from: V */
    private static final /* synthetic */ C2428a f3740V = null;
    /* renamed from: W */
    private static final /* synthetic */ C2428a f3741W = null;
    /* renamed from: X */
    private static final /* synthetic */ C2428a f3742X = null;
    /* renamed from: Y */
    private static final /* synthetic */ C2428a f3743Y = null;
    /* renamed from: w */
    private static final /* synthetic */ C2428a f3744w = null;
    /* renamed from: x */
    private static final /* synthetic */ C2428a f3745x = null;
    /* renamed from: y */
    private static final /* synthetic */ C2428a f3746y = null;
    /* renamed from: z */
    private static final /* synthetic */ C2428a f3747z = null;
    /* renamed from: a */
    private Date f3748a;
    /* renamed from: b */
    private Date f3749b;
    /* renamed from: c */
    private long f3750c;
    /* renamed from: l */
    private long f3751l;
    /* renamed from: m */
    private double f3752m = 1.0d;
    /* renamed from: n */
    private float f3753n = 1.0f;
    /* renamed from: o */
    private C1320g f3754o = C1320g.f3985j;
    /* renamed from: p */
    private long f3755p;
    /* renamed from: q */
    private int f3756q;
    /* renamed from: r */
    private int f3757r;
    /* renamed from: s */
    private int f3758s;
    /* renamed from: t */
    private int f3759t;
    /* renamed from: u */
    private int f3760u;
    /* renamed from: v */
    private int f3761v;

    static {
        C1273p.m6580i();
    }

    public C1273p() {
        super("mvhd");
    }

    /* renamed from: i */
    private static /* synthetic */ void m6580i() {
        C2441b c2441b = new C2441b("MovieHeaderBox.java", C1273p.class);
        f3744w = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getCreationTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.Date"), 63);
        f3745x = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getModificationTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.util.Date"), 67);
        f3725G = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setModificationTime", "com.coremedia.iso.boxes.MovieHeaderBox", "java.util.Date", "modificationTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 203);
        f3726H = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setTimescale", "com.coremedia.iso.boxes.MovieHeaderBox", "long", "timescale", TtmlNode.ANONYMOUS_REGION_ID, "void"), 211);
        f3727I = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setDuration", "com.coremedia.iso.boxes.MovieHeaderBox", "long", "duration", TtmlNode.ANONYMOUS_REGION_ID, "void"), 215);
        f3728J = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setRate", "com.coremedia.iso.boxes.MovieHeaderBox", "double", "rate", TtmlNode.ANONYMOUS_REGION_ID, "void"), 222);
        f3729K = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setVolume", "com.coremedia.iso.boxes.MovieHeaderBox", "float", "volume", TtmlNode.ANONYMOUS_REGION_ID, "void"), 226);
        f3730L = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setMatrix", "com.coremedia.iso.boxes.MovieHeaderBox", "com.googlecode.mp4parser.util.Matrix", "matrix", TtmlNode.ANONYMOUS_REGION_ID, "void"), 230);
        f3731M = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setNextTrackId", "com.coremedia.iso.boxes.MovieHeaderBox", "long", "nextTrackId", TtmlNode.ANONYMOUS_REGION_ID, "void"), 234);
        f3732N = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getPreviewTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 238);
        f3733O = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setPreviewTime", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "previewTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 242);
        f3734P = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getPreviewDuration", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 246);
        f3746y = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getTimescale", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 71);
        f3735Q = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setPreviewDuration", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "previewDuration", TtmlNode.ANONYMOUS_REGION_ID, "void"), (int) Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        f3736R = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getPosterTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 254);
        f3737S = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setPosterTime", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "posterTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 258);
        f3738T = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSelectionTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 262);
        f3739U = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setSelectionTime", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "selectionTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 266);
        f3740V = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getSelectionDuration", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 270);
        f3741W = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setSelectionDuration", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "selectionDuration", TtmlNode.ANONYMOUS_REGION_ID, "void"), 274);
        f3742X = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getCurrentTime", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "int"), 278);
        f3743Y = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setCurrentTime", "com.coremedia.iso.boxes.MovieHeaderBox", "int", "currentTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 282);
        f3747z = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getDuration", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 75);
        f3719A = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getRate", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "double"), 79);
        f3720B = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getVolume", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "float"), 83);
        f3721C = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getMatrix", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "com.googlecode.mp4parser.util.Matrix"), 87);
        f3722D = c2441b.m11966a("method-execution", c2441b.m11967a("1", "getNextTrackId", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "long"), 91);
        f3723E = c2441b.m11966a("method-execution", c2441b.m11967a("1", "toString", "com.coremedia.iso.boxes.MovieHeaderBox", TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID, "java.lang.String"), 139);
        f3724F = c2441b.m11966a("method-execution", c2441b.m11967a("1", "setCreationTime", "com.coremedia.iso.boxes.MovieHeaderBox", "java.util.Date", "creationTime", TtmlNode.ANONYMOUS_REGION_ID, "void"), 195);
    }

    /* renamed from: a */
    public void m6581a(long j) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3726H, this, this, C2437a.m11947a(j)));
        this.f3750c = j;
    }

    /* renamed from: a */
    public void m6582a(C1320g c1320g) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3730L, this, this, c1320g));
        this.f3754o = c1320g;
    }

    /* renamed from: a */
    public void mo1111a(ByteBuffer byteBuffer) {
        mo1109c(byteBuffer);
        if (m6534o() == 1) {
            this.f3748a = C1316c.m6758a(C1290e.m6672e(byteBuffer));
            this.f3749b = C1316c.m6758a(C1290e.m6672e(byteBuffer));
            this.f3750c = C1290e.m6667a(byteBuffer);
            this.f3751l = C1290e.m6672e(byteBuffer);
        } else {
            this.f3748a = C1316c.m6758a(C1290e.m6667a(byteBuffer));
            this.f3749b = C1316c.m6758a(C1290e.m6667a(byteBuffer));
            this.f3750c = C1290e.m6667a(byteBuffer);
            this.f3751l = C1290e.m6667a(byteBuffer);
        }
        this.f3752m = C1290e.m6673f(byteBuffer);
        this.f3753n = C1290e.m6675h(byteBuffer);
        C1290e.m6670c(byteBuffer);
        C1290e.m6667a(byteBuffer);
        C1290e.m6667a(byteBuffer);
        this.f3754o = C1320g.m6763a(byteBuffer);
        this.f3756q = byteBuffer.getInt();
        this.f3757r = byteBuffer.getInt();
        this.f3758s = byteBuffer.getInt();
        this.f3759t = byteBuffer.getInt();
        this.f3760u = byteBuffer.getInt();
        this.f3761v = byteBuffer.getInt();
        this.f3755p = C1290e.m6667a(byteBuffer);
    }

    /* renamed from: a */
    public void m6584a(Date date) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3724F, this, this, date));
        this.f3748a = date;
        if (C1316c.m6757a(date) >= 4294967296L) {
            m6531c(1);
        }
    }

    /* renamed from: b */
    public Date mo1112b() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3744w, (Object) this, (Object) this));
        return this.f3748a;
    }

    /* renamed from: b */
    public void m6586b(long j) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3727I, this, this, C2437a.m11947a(j)));
        this.f3751l = j;
        if (j >= 4294967296L) {
            m6531c(1);
        }
    }

    /* renamed from: b */
    protected void mo1113b(ByteBuffer byteBuffer) {
        mo1110d(byteBuffer);
        if (m6534o() == 1) {
            C1291f.m6680a(byteBuffer, C1316c.m6757a(this.f3748a));
            C1291f.m6680a(byteBuffer, C1316c.m6757a(this.f3749b));
            C1291f.m6684b(byteBuffer, this.f3750c);
            C1291f.m6680a(byteBuffer, this.f3751l);
        } else {
            C1291f.m6684b(byteBuffer, C1316c.m6757a(this.f3748a));
            C1291f.m6684b(byteBuffer, C1316c.m6757a(this.f3749b));
            C1291f.m6684b(byteBuffer, this.f3750c);
            C1291f.m6684b(byteBuffer, this.f3751l);
        }
        C1291f.m6678a(byteBuffer, this.f3752m);
        C1291f.m6686c(byteBuffer, (double) this.f3753n);
        C1291f.m6683b(byteBuffer, 0);
        C1291f.m6684b(byteBuffer, 0);
        C1291f.m6684b(byteBuffer, 0);
        this.f3754o.m6764b(byteBuffer);
        byteBuffer.putInt(this.f3756q);
        byteBuffer.putInt(this.f3757r);
        byteBuffer.putInt(this.f3758s);
        byteBuffer.putInt(this.f3759t);
        byteBuffer.putInt(this.f3760u);
        byteBuffer.putInt(this.f3761v);
        C1291f.m6684b(byteBuffer, this.f3755p);
    }

    /* renamed from: b */
    public void m6588b(Date date) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3725G, this, this, date));
        this.f3749b = date;
        if (C1316c.m6757a(date) >= 4294967296L) {
            m6531c(1);
        }
    }

    protected long b_() {
        return (m6534o() == 1 ? 4 + 28 : 4 + 16) + 80;
    }

    /* renamed from: c */
    public Date mo1115c() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3745x, (Object) this, (Object) this));
        return this.f3749b;
    }

    /* renamed from: c */
    public void m6590c(long j) {
        C1324g.m6778a().m6780a(C2441b.m11965a(f3731M, this, this, C2437a.m11947a(j)));
        this.f3755p = j;
    }

    /* renamed from: d */
    public long m6591d() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3746y, (Object) this, (Object) this));
        return this.f3750c;
    }

    /* renamed from: e */
    public long m6592e() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3747z, (Object) this, (Object) this));
        return this.f3751l;
    }

    /* renamed from: f */
    public double m6593f() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3719A, (Object) this, (Object) this));
        return this.f3752m;
    }

    /* renamed from: g */
    public float m6594g() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3720B, (Object) this, (Object) this));
        return this.f3753n;
    }

    /* renamed from: h */
    public long m6595h() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3722D, (Object) this, (Object) this));
        return this.f3755p;
    }

    public String toString() {
        C1324g.m6778a().m6780a(C2441b.m11964a(f3723E, (Object) this, (Object) this));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MovieHeaderBox[");
        stringBuilder.append("creationTime=").append(mo1112b());
        stringBuilder.append(";");
        stringBuilder.append("modificationTime=").append(mo1115c());
        stringBuilder.append(";");
        stringBuilder.append("timescale=").append(m6591d());
        stringBuilder.append(";");
        stringBuilder.append("duration=").append(m6592e());
        stringBuilder.append(";");
        stringBuilder.append("rate=").append(m6593f());
        stringBuilder.append(";");
        stringBuilder.append("volume=").append(m6594g());
        stringBuilder.append(";");
        stringBuilder.append("matrix=").append(this.f3754o);
        stringBuilder.append(";");
        stringBuilder.append("nextTrackId=").append(m6595h());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
