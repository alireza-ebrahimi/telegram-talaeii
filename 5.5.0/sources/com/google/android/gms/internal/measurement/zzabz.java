package com.google.android.gms.internal.measurement;

import java.io.IOException;

public final class zzabz extends IOException {
    zzabz(int i, int i2) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
    }
}
