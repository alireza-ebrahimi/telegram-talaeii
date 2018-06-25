package org.telegram.messenger.exoplayer2.upstream.crypto;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.upstream.DataSink;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;

public final class AesCipherDataSink implements DataSink {
    private AesFlushingCipher cipher;
    private final byte[] scratch;
    private final byte[] secretKey;
    private final DataSink wrappedDataSink;

    public AesCipherDataSink(byte[] secretKey, DataSink wrappedDataSink) {
        this(secretKey, wrappedDataSink, null);
    }

    public AesCipherDataSink(byte[] secretKey, DataSink wrappedDataSink, byte[] scratch) {
        this.wrappedDataSink = wrappedDataSink;
        this.secretKey = secretKey;
        this.scratch = scratch;
    }

    public void open(DataSpec dataSpec) throws IOException {
        this.wrappedDataSink.open(dataSpec);
        this.cipher = new AesFlushingCipher(1, this.secretKey, CryptoUtil.getFNV64Hash(dataSpec.key), dataSpec.absoluteStreamPosition);
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        if (this.scratch == null) {
            this.cipher.updateInPlace(data, offset, length);
            this.wrappedDataSink.write(data, offset, length);
            return;
        }
        int bytesProcessed = 0;
        while (bytesProcessed < length) {
            int bytesToProcess = Math.min(length - bytesProcessed, this.scratch.length);
            this.cipher.update(data, offset + bytesProcessed, bytesToProcess, this.scratch, 0);
            this.wrappedDataSink.write(this.scratch, 0, bytesToProcess);
            bytesProcessed += bytesToProcess;
        }
    }

    public void close() throws IOException {
        this.cipher = null;
        this.wrappedDataSink.close();
    }
}
