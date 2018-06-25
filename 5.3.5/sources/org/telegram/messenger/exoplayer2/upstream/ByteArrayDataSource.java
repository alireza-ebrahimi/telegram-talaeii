package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class ByteArrayDataSource implements DataSource {
    private int bytesRemaining;
    private final byte[] data;
    private int readPosition;
    private Uri uri;

    public ByteArrayDataSource(byte[] data) {
        Assertions.checkNotNull(data);
        Assertions.checkArgument(data.length > 0);
        this.data = data;
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        this.readPosition = (int) dataSpec.position;
        this.bytesRemaining = (int) (dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length);
        if (this.bytesRemaining > 0 && this.readPosition + this.bytesRemaining <= this.data.length) {
            return (long) this.bytesRemaining;
        }
        throw new IOException("Unsatisfiable range: [" + this.readPosition + ", " + dataSpec.length + "], length: " + this.data.length);
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        if (readLength == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        readLength = Math.min(readLength, this.bytesRemaining);
        System.arraycopy(this.data, this.readPosition, buffer, offset, readLength);
        this.readPosition += readLength;
        this.bytesRemaining -= readLength;
        return readLength;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void close() throws IOException {
        this.uri = null;
    }
}
