package com.google.android.gms.internal.clearcut;

final class zzfi extends IllegalArgumentException {
    zzfi(int i, int i2) {
        super("Unpaired surrogate at index " + i + " of " + i2);
    }
}
