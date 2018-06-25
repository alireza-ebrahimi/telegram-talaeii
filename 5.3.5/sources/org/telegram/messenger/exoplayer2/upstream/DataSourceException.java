package org.telegram.messenger.exoplayer2.upstream;

import java.io.IOException;

public final class DataSourceException extends IOException {
    public static final int POSITION_OUT_OF_RANGE = 0;
    public final int reason;

    public DataSourceException(int reason) {
        this.reason = reason;
    }
}
