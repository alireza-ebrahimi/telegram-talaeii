package org.telegram.messenger.exoplayer2.upstream;

import android.content.Context;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;

public final class DefaultDataSourceFactory implements Factory {
    private final Factory baseDataSourceFactory;
    private final Context context;
    private final TransferListener<? super DataSource> listener;

    public DefaultDataSourceFactory(Context context, String userAgent) {
        this(context, userAgent, null);
    }

    public DefaultDataSourceFactory(Context context, String userAgent, TransferListener<? super DataSource> listener) {
        this(context, (TransferListener) listener, new DefaultHttpDataSourceFactory(userAgent, listener));
    }

    public DefaultDataSourceFactory(Context context, TransferListener<? super DataSource> listener, Factory baseDataSourceFactory) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.baseDataSourceFactory = baseDataSourceFactory;
    }

    public DefaultDataSource createDataSource() {
        return new DefaultDataSource(this.context, this.listener, this.baseDataSourceFactory.createDataSource());
    }
}
