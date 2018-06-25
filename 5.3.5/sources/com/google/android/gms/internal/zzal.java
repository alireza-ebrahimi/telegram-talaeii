package com.google.android.gms.internal;

import java.util.Comparator;

final class zzal implements Comparator<byte[]> {
    zzal() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((byte[]) obj).length - ((byte[]) obj2).length;
    }
}
