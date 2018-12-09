package com.p054b.p055a.p056a;

import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1290e;
import com.p054b.p055a.C1291f;
import com.p057c.p058a.C1249b;
import com.p057c.p058a.C1254e;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.b.a.a.h */
public class C1266h extends C1249b implements C1258j {
    /* renamed from: a */
    private int f3678a;
    /* renamed from: b */
    private int f3679b;

    public C1266h() {
        super("dref");
    }

    public void getBox(WritableByteChannel writableByteChannel) {
        writableByteChannel.write(m6488i());
        ByteBuffer allocate = ByteBuffer.allocate(8);
        C1291f.m6687c(allocate, this.f3678a);
        C1291f.m6679a(allocate, this.f3679b);
        C1291f.m6684b(allocate, (long) mo1093a().size());
        writableByteChannel.write((ByteBuffer) allocate.rewind());
        m6484a(writableByteChannel);
    }

    public long getSize() {
        long j = m6485j();
        long j2 = j + 8;
        int i = (this.e || (j + 8) + 8 >= 4294967296L) ? 16 : 8;
        return ((long) i) + j2;
    }

    public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        c1254e.mo1102a(allocate);
        allocate.rewind();
        this.f3678a = C1290e.m6671d(allocate);
        this.f3679b = C1290e.m6669b(allocate);
        mo1094a(c1254e, j - 8, c1286b);
    }
}
