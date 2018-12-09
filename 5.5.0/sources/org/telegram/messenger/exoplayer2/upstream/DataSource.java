package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;

public interface DataSource {

    public interface Factory {
        DataSource createDataSource();
    }

    void close();

    Uri getUri();

    long open(DataSpec dataSpec);

    int read(byte[] bArr, int i, int i2);
}
