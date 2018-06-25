package org.telegram.messenger.secretmedia;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.upstream.AssetDataSource;
import org.telegram.messenger.exoplayer2.upstream.ContentDataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.DefaultHttpDataSource;
import org.telegram.messenger.exoplayer2.upstream.FileDataSource;
import org.telegram.messenger.exoplayer2.upstream.TransferListener;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class ExtendedDefaultDataSource implements DataSource {
    private static final String SCHEME_ASSET = "asset";
    private static final String SCHEME_CONTENT = "content";
    private final DataSource assetDataSource;
    private final DataSource baseDataSource;
    private final DataSource contentDataSource;
    private DataSource dataSource;
    private final DataSource encryptedFileDataSource;
    private final DataSource fileDataSource;

    public ExtendedDefaultDataSource(Context context, TransferListener<? super DataSource> listener, String userAgent, boolean allowCrossProtocolRedirects) {
        this(context, listener, userAgent, 8000, 8000, allowCrossProtocolRedirects);
    }

    public ExtendedDefaultDataSource(Context context, TransferListener<? super DataSource> listener, String userAgent, int connectTimeoutMillis, int readTimeoutMillis, boolean allowCrossProtocolRedirects) {
        this(context, listener, new DefaultHttpDataSource(userAgent, null, listener, connectTimeoutMillis, readTimeoutMillis, allowCrossProtocolRedirects, null));
    }

    public ExtendedDefaultDataSource(Context context, TransferListener<? super DataSource> listener, DataSource baseDataSource) {
        this.baseDataSource = (DataSource) Assertions.checkNotNull(baseDataSource);
        this.fileDataSource = new FileDataSource(listener);
        this.encryptedFileDataSource = new EncryptedFileDataSource(listener);
        this.assetDataSource = new AssetDataSource(context, listener);
        this.contentDataSource = new ContentDataSource(context, listener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        Assertions.checkState(this.dataSource == null);
        String scheme = dataSpec.uri.getScheme();
        if (Util.isLocalFileUri(dataSpec.uri)) {
            if (dataSpec.uri.getPath().startsWith("/android_asset/")) {
                this.dataSource = this.assetDataSource;
            } else if (dataSpec.uri.getPath().endsWith(".enc")) {
                this.dataSource = this.encryptedFileDataSource;
            } else {
                this.dataSource = this.fileDataSource;
            }
        } else if (SCHEME_ASSET.equals(scheme)) {
            this.dataSource = this.assetDataSource;
        } else if ("content".equals(scheme)) {
            this.dataSource = this.contentDataSource;
        } else {
            this.dataSource = this.baseDataSource;
        }
        return this.dataSource.open(dataSpec);
    }

    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        return this.dataSource.read(buffer, offset, readLength);
    }

    public Uri getUri() {
        return this.dataSource == null ? null : this.dataSource.getUri();
    }

    public void close() throws IOException {
        if (this.dataSource != null) {
            try {
                this.dataSource.close();
            } finally {
                this.dataSource = null;
            }
        }
    }
}
