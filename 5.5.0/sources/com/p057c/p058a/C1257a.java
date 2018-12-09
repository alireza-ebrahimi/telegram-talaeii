package com.p057c.p058a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1288c;
import com.p054b.p055a.C1289d;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p057c.p058a.p063b.C1313f;
import com.p057c.p058a.p063b.C1315b;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.c.a.a */
public abstract class C1257a implements C1248b {
    /* renamed from: a */
    private static C1313f f3646a = C1313f.m6751a(C1257a.class);
    /* renamed from: k */
    static final /* synthetic */ boolean f3647k = (!C1257a.class.desiredAssertionStatus());
    /* renamed from: b */
    private byte[] f3648b;
    /* renamed from: c */
    private C1246e f3649c;
    /* renamed from: d */
    protected String f3650d;
    /* renamed from: e */
    boolean f3651e;
    /* renamed from: f */
    boolean f3652f;
    /* renamed from: g */
    long f3653g;
    /* renamed from: h */
    long f3654h;
    /* renamed from: i */
    long f3655i = -1;
    /* renamed from: j */
    C1254e f3656j;
    /* renamed from: l */
    private ByteBuffer f3657l;
    /* renamed from: m */
    private ByteBuffer f3658m = null;

    protected C1257a(String str) {
        this.f3650d = str;
        this.f3652f = true;
        this.f3651e = true;
    }

    /* renamed from: b */
    private synchronized void mo1112b() {
        if (!this.f3652f) {
            try {
                f3646a.mo1122a("mem mapping " + getType());
                this.f3657l = this.f3656j.mo1105a(this.f3653g, this.f3655i);
                this.f3652f = true;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* renamed from: c */
    private boolean mo1115c() {
        int i = 8;
        if ("uuid".equals(getType())) {
            i = 24;
        }
        if (!this.f3652f) {
            return this.f3655i + ((long) i) < 4294967296L;
        } else {
            if (!this.f3651e) {
                return ((long) (i + this.f3657l.limit())) < 4294967296L;
            } else {
                return (b_() + ((long) (this.f3658m != null ? this.f3658m.limit() : 0))) + ((long) i) < 4294967296L;
            }
        }
    }

    /* renamed from: c */
    private boolean mo1109c(ByteBuffer byteBuffer) {
        ByteBuffer allocate = ByteBuffer.allocate(C1315b.m6756a(b_() + ((long) (this.f3658m != null ? this.f3658m.limit() : 0))));
        mo1113b(allocate);
        if (this.f3658m != null) {
            this.f3658m.rewind();
            while (this.f3658m.remaining() > 0) {
                allocate.put(this.f3658m);
            }
        }
        byteBuffer.rewind();
        allocate.rewind();
        if (byteBuffer.remaining() != allocate.remaining()) {
            System.err.print(getType() + ": remaining differs " + byteBuffer.remaining() + " vs. " + allocate.remaining());
            f3646a.mo1123b(getType() + ": remaining differs " + byteBuffer.remaining() + " vs. " + allocate.remaining());
            return false;
        }
        int position = byteBuffer.position();
        int limit = byteBuffer.limit() - 1;
        int limit2 = allocate.limit() - 1;
        while (limit >= position) {
            if (byteBuffer.get(limit) != allocate.get(limit2)) {
                f3646a.mo1123b(String.format("%s: buffers differ at %d: %2X/%2X", new Object[]{getType(), Integer.valueOf(limit), Byte.valueOf(byteBuffer.get(limit)), Byte.valueOf(allocate.get(limit2))}));
                byte[] bArr = new byte[byteBuffer.remaining()];
                byte[] bArr2 = new byte[allocate.remaining()];
                byteBuffer.get(bArr);
                allocate.get(bArr2);
                System.err.println("original      : " + C1288c.m6664a(bArr, 4));
                System.err.println("reconstructed : " + C1288c.m6664a(bArr2, 4));
                return false;
            }
            limit--;
            limit2--;
        }
        return true;
    }

    /* renamed from: d */
    private void mo1110d(ByteBuffer byteBuffer) {
        if (mo1115c()) {
            C1291f.m6684b(byteBuffer, getSize());
            byteBuffer.put(C1289d.m6665a(getType()));
        } else {
            C1291f.m6684b(byteBuffer, 1);
            byteBuffer.put(C1289d.m6665a(getType()));
            C1291f.m6680a(byteBuffer, getSize());
        }
        if ("uuid".equals(getType())) {
            byteBuffer.put(m6527m());
        }
    }

    /* renamed from: a */
    protected abstract void mo1111a(ByteBuffer byteBuffer);

    /* renamed from: b */
    protected abstract void mo1113b(ByteBuffer byteBuffer);

    protected abstract long b_();

    public void getBox(WritableByteChannel writableByteChannel) {
        int i = 8;
        int i2 = 16;
        ByteBuffer allocate;
        if (!this.f3652f) {
            if (!mo1115c()) {
                i = 16;
            }
            if (!"uuid".equals(getType())) {
                i2 = 0;
            }
            allocate = ByteBuffer.allocate(i + i2);
            mo1110d(allocate);
            writableByteChannel.write((ByteBuffer) allocate.rewind());
            this.f3656j.mo1104a(this.f3653g, this.f3655i, writableByteChannel);
        } else if (this.f3651e) {
            allocate = ByteBuffer.allocate(C1315b.m6756a(getSize()));
            mo1110d(allocate);
            mo1113b(allocate);
            if (this.f3658m != null) {
                this.f3658m.rewind();
                while (this.f3658m.remaining() > 0) {
                    allocate.put(this.f3658m);
                }
            }
            writableByteChannel.write((ByteBuffer) allocate.rewind());
        } else {
            if (!mo1115c()) {
                i = 16;
            }
            if (!"uuid".equals(getType())) {
                i2 = 0;
            }
            allocate = ByteBuffer.allocate(i + i2);
            mo1110d(allocate);
            writableByteChannel.write((ByteBuffer) allocate.rewind());
            writableByteChannel.write((ByteBuffer) this.f3657l.position(0));
        }
    }

    public C1246e getParent() {
        return this.f3649c;
    }

    public long getSize() {
        long j;
        int i = 0;
        if (!this.f3652f) {
            j = this.f3655i;
        } else if (this.f3651e) {
            j = b_();
        } else {
            j = (long) (this.f3657l != null ? this.f3657l.limit() : 0);
        }
        j += (long) (("uuid".equals(getType()) ? 16 : 0) + ((j >= 4294967288L ? 8 : 0) + 8));
        if (this.f3658m != null) {
            i = this.f3658m.limit();
        }
        return ((long) i) + j;
    }

    public String getType() {
        return this.f3650d;
    }

    /* renamed from: l */
    public final synchronized void m6526l() {
        mo1112b();
        f3646a.mo1122a("parsing details of " + getType());
        if (this.f3657l != null) {
            ByteBuffer byteBuffer = this.f3657l;
            this.f3651e = true;
            byteBuffer.rewind();
            mo1111a(byteBuffer);
            if (byteBuffer.remaining() > 0) {
                this.f3658m = byteBuffer.slice();
            }
            this.f3657l = null;
            if (!(f3647k || mo1109c(byteBuffer))) {
                throw new AssertionError();
            }
        }
    }

    /* renamed from: m */
    public byte[] m6527m() {
        return this.f3648b;
    }

    /* renamed from: n */
    public boolean m6528n() {
        return this.f3651e;
    }

    public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        this.f3653g = c1254e.mo1107b();
        this.f3654h = this.f3653g - ((long) byteBuffer.remaining());
        this.f3655i = j;
        this.f3656j = c1254e;
        c1254e.mo1106a(c1254e.mo1107b() + j);
        this.f3652f = false;
        this.f3651e = false;
    }

    public void setParent(C1246e c1246e) {
        this.f3649c = c1246e;
    }
}
