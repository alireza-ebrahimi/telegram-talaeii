package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class TeeDataSource implements DataSource {
    private final DataSink dataSink;
    private final DataSource upstream;

    public TeeDataSource(DataSource dataSource, DataSink dataSink) {
        this.upstream = (DataSource) Assertions.checkNotNull(dataSource);
        this.dataSink = (DataSink) Assertions.checkNotNull(dataSink);
    }

    public void close() {
        try {
            this.upstream.close();
        } finally {
            this.dataSink.close();
        }
    }

    public Uri getUri() {
        return this.upstream.getUri();
    }

    public long open(DataSpec dataSpec) {
        long open = this.upstream.open(dataSpec);
        if (dataSpec.length == -1 && open != -1) {
            dataSpec = new DataSpec(dataSpec.uri, dataSpec.absoluteStreamPosition, dataSpec.position, open, dataSpec.key, dataSpec.flags);
        }
        this.dataSink.open(dataSpec);
        return open;
    }

    public int read(byte[] bArr, int i, int i2) {
        int read = this.upstream.read(bArr, i, i2);
        if (read > 0) {
            this.dataSink.write(bArr, i, read);
        }
        return read;
    }
}
