package com.p054b.p055a;

import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p057c.p058a.C1254e;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/* renamed from: com.b.a.a */
public abstract class C1287a implements C1286b {
    /* renamed from: b */
    private static Logger f3840b = Logger.getLogger(C1287a.class.getName());
    /* renamed from: a */
    ThreadLocal<ByteBuffer> f3841a = new C12451(this);

    /* renamed from: com.b.a.a$1 */
    class C12451 extends ThreadLocal<ByteBuffer> {
        /* renamed from: a */
        final /* synthetic */ C1287a f3602a;

        C12451(C1287a c1287a) {
            this.f3602a = c1287a;
        }

        /* renamed from: a */
        protected ByteBuffer m6479a() {
            return ByteBuffer.allocate(32);
        }

        protected /* synthetic */ Object initialValue() {
            return m6479a();
        }
    }

    /* renamed from: a */
    public C1248b mo1117a(C1254e c1254e, C1246e c1246e) {
        long b = c1254e.mo1107b();
        ((ByteBuffer) this.f3841a.get()).rewind().limit(8);
        int a;
        do {
            a = c1254e.mo1102a((ByteBuffer) this.f3841a.get());
            if (a == 8) {
                ((ByteBuffer) this.f3841a.get()).rewind();
                b = C1290e.m6667a((ByteBuffer) this.f3841a.get());
                if (b >= 8 || b <= 1) {
                    long j;
                    byte[] bArr;
                    String j2 = C1290e.m6677j((ByteBuffer) this.f3841a.get());
                    if (b == 1) {
                        ((ByteBuffer) this.f3841a.get()).limit(16);
                        c1254e.mo1102a((ByteBuffer) this.f3841a.get());
                        ((ByteBuffer) this.f3841a.get()).position(8);
                        b = C1290e.m6672e((ByteBuffer) this.f3841a.get()) - 16;
                    } else if (b == 0) {
                        long a2 = c1254e.mo1103a() - c1254e.mo1107b();
                        b = a2 + 8;
                        b = a2;
                    } else {
                        b -= 8;
                    }
                    if ("uuid".equals(j2)) {
                        ((ByteBuffer) this.f3841a.get()).limit(((ByteBuffer) this.f3841a.get()).limit() + 16);
                        c1254e.mo1102a((ByteBuffer) this.f3841a.get());
                        byte[] bArr2 = new byte[16];
                        for (int position = ((ByteBuffer) this.f3841a.get()).position() - 16; position < ((ByteBuffer) this.f3841a.get()).position(); position++) {
                            bArr2[position - (((ByteBuffer) this.f3841a.get()).position() - 16)] = ((ByteBuffer) this.f3841a.get()).get(position);
                        }
                        j = b - 16;
                        bArr = bArr2;
                    } else {
                        bArr = null;
                        j = b;
                    }
                    C1248b a3 = mo1119a(j2, bArr, c1246e instanceof C1248b ? ((C1248b) c1246e).getType() : TtmlNode.ANONYMOUS_REGION_ID);
                    a3.setParent(c1246e);
                    ((ByteBuffer) this.f3841a.get()).rewind();
                    a3.parse(c1254e, (ByteBuffer) this.f3841a.get(), j, this);
                    return a3;
                }
                f3840b.severe("Plausibility check failed: size < 8 (size = " + b + "). Stop parsing!");
                return null;
            }
        } while (a >= 0);
        c1254e.mo1106a(b);
        throw new EOFException();
    }

    /* renamed from: a */
    public abstract C1248b mo1119a(String str, byte[] bArr, String str2);
}
