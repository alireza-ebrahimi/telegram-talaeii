package org.telegram.messenger.exoplayer2.source.hls;

import org.telegram.messenger.exoplayer2.upstream.DataSource;

public interface HlsDataSourceFactory {
    DataSource createDataSource(int i);
}
