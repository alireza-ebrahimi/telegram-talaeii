package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;

@Hide
public final class zzam {
    @NonNull
    private final String mPackageName;
    private final int zzggv = TsExtractor.TS_STREAM_TYPE_AC3;
    @NonNull
    private final String zzghk;
    private final boolean zzghl = false;

    public zzam(@NonNull String str, @NonNull String str2, boolean z, int i) {
        this.mPackageName = str;
        this.zzghk = str2;
    }

    @NonNull
    final String getPackageName() {
        return this.mPackageName;
    }

    final int zzamu() {
        return this.zzggv;
    }

    @NonNull
    final String zzamx() {
        return this.zzghk;
    }
}
