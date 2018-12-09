package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

final class zzjn {
    private long startTime;
    private final Clock zzro;

    public zzjn(Clock clock) {
        Preconditions.checkNotNull(clock);
        this.zzro = clock;
    }

    public final void clear() {
        this.startTime = 0;
    }

    public final void start() {
        this.startTime = this.zzro.elapsedRealtime();
    }

    public final boolean zzj(long j) {
        return this.startTime == 0 || this.zzro.elapsedRealtime() - this.startTime >= 3600000;
    }
}
