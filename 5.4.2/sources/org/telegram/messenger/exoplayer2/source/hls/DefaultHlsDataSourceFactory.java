package org.telegram.messenger.exoplayer2.source.hls;

import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;

public final class DefaultHlsDataSourceFactory implements HlsDataSourceFactory {
    private final Factory dataSourceFactory;

    public DefaultHlsDataSourceFactory(Factory factory) {
        this.dataSourceFactory = factory;
    }

    public DataSource createDataSource(int i) {
        return this.dataSourceFactory.createDataSource();
    }
}
