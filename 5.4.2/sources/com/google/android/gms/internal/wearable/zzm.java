package com.google.android.gms.internal.wearable;

import java.io.IOException;

public final class zzm extends IOException {
    zzm(int i, int i2) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
    }
}
