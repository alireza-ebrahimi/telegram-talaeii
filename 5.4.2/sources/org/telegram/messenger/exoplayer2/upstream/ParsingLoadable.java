package org.telegram.messenger.exoplayer2.upstream;

import android.net.Uri;
import java.io.Closeable;
import java.io.InputStream;
import org.telegram.messenger.exoplayer2.upstream.Loader.Loadable;
import org.telegram.messenger.exoplayer2.util.Util;

public final class ParsingLoadable<T> implements Loadable {
    private volatile long bytesLoaded;
    private final DataSource dataSource;
    public final DataSpec dataSpec;
    private volatile boolean isCanceled;
    private final Parser<? extends T> parser;
    private volatile T result;
    public final int type;

    public interface Parser<T> {
        T parse(Uri uri, InputStream inputStream);
    }

    public ParsingLoadable(DataSource dataSource, Uri uri, int i, Parser<? extends T> parser) {
        this.dataSource = dataSource;
        this.dataSpec = new DataSpec(uri, 1);
        this.type = i;
        this.parser = parser;
    }

    public long bytesLoaded() {
        return this.bytesLoaded;
    }

    public final void cancelLoad() {
        this.isCanceled = true;
    }

    public final T getResult() {
        return this.result;
    }

    public final boolean isLoadCanceled() {
        return this.isCanceled;
    }

    public final void load() {
        Closeable dataSourceInputStream = new DataSourceInputStream(this.dataSource, this.dataSpec);
        try {
            dataSourceInputStream.open();
            this.result = this.parser.parse(this.dataSource.getUri(), dataSourceInputStream);
        } finally {
            this.bytesLoaded = dataSourceInputStream.bytesRead();
            Util.closeQuietly(dataSourceInputStream);
        }
    }
}
