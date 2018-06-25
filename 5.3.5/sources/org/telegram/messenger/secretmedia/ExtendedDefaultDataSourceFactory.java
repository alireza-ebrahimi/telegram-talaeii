package org.telegram.messenger.secretmedia;

import android.content.Context;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import org.telegram.messenger.exoplayer2.upstream.TransferListener;

public final class ExtendedDefaultDataSourceFactory implements Factory {
    private final Factory baseDataSourceFactory;
    private final Context context;
    private final TransferListener<? super DataSource> listener;

    public ExtendedDefaultDataSourceFactory(Context context, String userAgent) {
        this(context, userAgent, null);
    }

    public ExtendedDefaultDataSourceFactory(Context context, String userAgent, TransferListener<? super DataSource> listener) {
        this(context, (TransferListener) listener, new DefaultHttpDataSourceFactory(userAgent, listener));
    }

    public ExtendedDefaultDataSourceFactory(Context context, TransferListener<? super DataSource> listener, Factory baseDataSourceFactory) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.baseDataSourceFactory = baseDataSourceFactory;
    }

    public ExtendedDefaultDataSource createDataSource() {
        return new ExtendedDefaultDataSource(this.context, this.listener, this.baseDataSourceFactory.createDataSource());
    }
}
