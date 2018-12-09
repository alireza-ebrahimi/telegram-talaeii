package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;

public final class DummyDataSource implements DataSource {
    public static final Factory FACTORY = new C35381();
    public static final DummyDataSource INSTANCE = new DummyDataSource();

    /* renamed from: org.telegram.messenger.exoplayer2.upstream.DummyDataSource$1 */
    static class C35381 implements Factory {
        C35381() {
        }

        public DataSource createDataSource() {
            return new DummyDataSource();
        }
    }

    private DummyDataSource() {
    }

    public void close() {
    }

    public Uri getUri() {
        return null;
    }

    public long open(DataSpec dataSpec) {
        throw new IOException("Dummy source");
    }

    public int read(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException();
    }
}
