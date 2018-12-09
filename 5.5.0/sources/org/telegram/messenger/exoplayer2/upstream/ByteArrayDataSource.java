package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class ByteArrayDataSource implements DataSource {
    private int bytesRemaining;
    private final byte[] data;
    private int readPosition;
    private Uri uri;

    public ByteArrayDataSource(byte[] bArr) {
        Assertions.checkNotNull(bArr);
        Assertions.checkArgument(bArr.length > 0);
        this.data = bArr;
    }

    public void close() {
        this.uri = null;
    }

    public Uri getUri() {
        return this.uri;
    }

    public long open(DataSpec dataSpec) {
        this.uri = dataSpec.uri;
        this.readPosition = (int) dataSpec.position;
        this.bytesRemaining = (int) (dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length);
        if (this.bytesRemaining > 0 && this.readPosition + this.bytesRemaining <= this.data.length) {
            return (long) this.bytesRemaining;
        }
        throw new IOException("Unsatisfiable range: [" + this.readPosition + ", " + dataSpec.length + "], length: " + this.data.length);
    }

    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        int min = Math.min(i2, this.bytesRemaining);
        System.arraycopy(this.data, this.readPosition, bArr, i, min);
        this.readPosition += min;
        this.bytesRemaining -= min;
        return min;
    }
}
