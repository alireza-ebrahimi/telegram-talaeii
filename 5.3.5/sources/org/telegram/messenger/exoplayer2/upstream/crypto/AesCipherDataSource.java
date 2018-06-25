package org.telegram.messenger.exoplayer2.upstream.crypto;

import android.net.Uri;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;

public final class AesCipherDataSource implements DataSource {
    private AesFlushingCipher cipher;
    private final byte[] secretKey;
    private final DataSource upstream;

    public AesCipherDataSource(byte[] secretKey, DataSource upstream) {
        this.upstream = upstream;
        this.secretKey = secretKey;
    }

    public long open(DataSpec dataSpec) throws IOException {
        long dataLength = this.upstream.open(dataSpec);
        this.cipher = new AesFlushingCipher(2, this.secretKey, CryptoUtil.getFNV64Hash(dataSpec.key), dataSpec.absoluteStreamPosition);
        return dataLength;
    }

    public int read(byte[] data, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        int read = this.upstream.read(data, offset, readLength);
        if (read == -1) {
            return -1;
        }
        this.cipher.updateInPlace(data, offset, read);
        return read;
    }

    public void close() throws IOException {
        this.cipher = null;
        this.upstream.close();
    }

    public Uri getUri() {
        return this.upstream.getUri();
    }
}
