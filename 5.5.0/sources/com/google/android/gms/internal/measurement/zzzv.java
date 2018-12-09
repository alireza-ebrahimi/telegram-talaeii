package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzzv extends IOException {
    private zzaan zzbtd = null;

    public zzzv(String str) {
        super(str);
    }

    static zzzv zztv() {
        return new zzzv("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzzv zztw() {
        return new zzzv("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }
}
