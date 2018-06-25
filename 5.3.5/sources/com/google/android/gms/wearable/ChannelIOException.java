package com.google.android.gms.wearable;

import java.io.IOException;

public class ChannelIOException extends IOException {
    private final int zzlqp;
    private final int zzlqq;

    public ChannelIOException(String str, int i, int i2) {
        super(str);
        this.zzlqp = i;
        this.zzlqq = i2;
    }

    public int getAppSpecificErrorCode() {
        return this.zzlqq;
    }

    public int getCloseReason() {
        return this.zzlqp;
    }
}
