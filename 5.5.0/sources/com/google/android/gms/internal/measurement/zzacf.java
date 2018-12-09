package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzacf extends IOException {
    public zzacf(String str) {
        super(str);
    }

    static zzacf zzvq() {
        return new zzacf("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzacf zzvr() {
        return new zzacf("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzacf zzvs() {
        return new zzacf("CodedInputStream encountered a malformed varint.");
    }

    static zzacf zzvt() {
        return new zzacf("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
