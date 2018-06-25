package com.google.android.gms.internal;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public final class zzaq {
    private final int zzcc;
    private final List<zzl> zzcd;
    private final int zzce;
    private final InputStream zzcf;

    public zzaq(int i, List<zzl> list) {
        this(i, list, -1, null);
    }

    public zzaq(int i, List<zzl> list, int i2, InputStream inputStream) {
        this.zzcc = i;
        this.zzcd = list;
        this.zzce = i2;
        this.zzcf = inputStream;
    }

    public final InputStream getContent() {
        return this.zzcf;
    }

    public final int getContentLength() {
        return this.zzce;
    }

    public final int getStatusCode() {
        return this.zzcc;
    }

    public final List<zzl> zzp() {
        return Collections.unmodifiableList(this.zzcd);
    }
}
