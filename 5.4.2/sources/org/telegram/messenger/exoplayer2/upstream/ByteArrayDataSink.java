package org.telegram.messenger.exoplayer2.upstream;

import java.io.ByteArrayOutputStream;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class ByteArrayDataSink implements DataSink {
    private ByteArrayOutputStream stream;

    public void close() {
        this.stream.close();
    }

    public byte[] getData() {
        return this.stream == null ? null : this.stream.toByteArray();
    }

    public void open(DataSpec dataSpec) {
        if (dataSpec.length == -1) {
            this.stream = new ByteArrayOutputStream();
            return;
        }
        Assertions.checkArgument(dataSpec.length <= 2147483647L);
        this.stream = new ByteArrayOutputStream((int) dataSpec.length);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.stream.write(bArr, i, i2);
    }
}
