package com.google.android.gms.internal.clearcut;

import java.io.IOException;

public final class zzft extends IOException {
    zzft(int i, int i2) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
    }
}
