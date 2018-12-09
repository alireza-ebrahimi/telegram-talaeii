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

    public ExtendedDefaultDataSourceFactory(Context context, String str) {
        this(context, str, null);
    }

    public ExtendedDefaultDataSourceFactory(Context context, String str, TransferListener<? super DataSource> transferListener) {
        this(context, (TransferListener) transferListener, new DefaultHttpDataSourceFactory(str, transferListener));
    }

    public ExtendedDefaultDataSourceFactory(Context context, TransferListener<? super DataSource> transferListener, Factory factory) {
        this.context = context.getApplicationContext();
        this.listener = transferListener;
        this.baseDataSourceFactory = factory;
    }

    public ExtendedDefaultDataSource createDataSource() {
        return new ExtendedDefaultDataSource(this.context, this.listener, this.baseDataSourceFactory.createDataSource());
    }
}
