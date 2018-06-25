package com.google.android.gms.common.server.response;

import java.io.BufferedReader;

final class zzb implements zza<Long> {
    zzb() {
    }

    public final /* synthetic */ Object zzh(FastParser fastParser, BufferedReader bufferedReader) {
        return Long.valueOf(fastParser.zze(bufferedReader));
    }
}
