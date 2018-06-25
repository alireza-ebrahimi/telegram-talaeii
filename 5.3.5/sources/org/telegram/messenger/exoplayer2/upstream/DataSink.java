package org.telegram.messenger.exoplayer2.upstream;

import java.io.IOException;

public interface DataSink {
    void close() throws IOException;

    void open(DataSpec dataSpec) throws IOException;

    void write(byte[] bArr, int i, int i2) throws IOException;
}
