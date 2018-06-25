package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.internal.Preconditions;

public final class zzfv {
    private long value;
    private boolean zzaky;
    private final /* synthetic */ zzfs zzakz;
    private final long zzala;
    private final String zzny;

    public zzfv(zzfs zzfs, String str, long j) {
        this.zzakz = zzfs;
        Preconditions.checkNotEmpty(str);
        this.zzny = str;
        this.zzala = j;
    }

    public final long get() {
        if (!this.zzaky) {
            this.zzaky = true;
            this.value = this.zzakz.zzjf().getLong(this.zzny, this.zzala);
        }
        return this.value;
    }

    public final void set(long j) {
        Editor edit = this.zzakz.zzjf().edit();
        edit.putLong(this.zzny, j);
        edit.apply();
        this.value = j;
    }
}
