package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

public final class zzgs extends IOException {
    public zzgs(String str) {
        super(str);
    }

    public zzgs(String str, Exception exception) {
        super(str, exception);
    }

    static zzgs zzgp() {
        return new zzgs("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzgs zzgq() {
        return new zzgs("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzgs zzgr() {
        return new zzgs("CodedInputStream encountered a malformed varint.");
    }
}
