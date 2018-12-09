package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;

public final class zzgm extends IOException {
    zzgm(int i, int i2) {
        super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
    }
}
