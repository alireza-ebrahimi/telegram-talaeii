package com.p054b.p055a.p056a;

import com.p054b.p055a.C1286b;
import com.p057c.p058a.C1254e;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* renamed from: com.b.a.a.b */
public interface C1248b {
    void getBox(WritableByteChannel writableByteChannel);

    C1246e getParent();

    long getSize();

    String getType();

    void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b);

    void setParent(C1246e c1246e);
}
