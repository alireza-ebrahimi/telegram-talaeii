package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;

public class BooleanResult implements Result {
    private final Status mStatus;
    private final boolean zzfsl;

    @Hide
    public BooleanResult(Status status, boolean z) {
        this.mStatus = (Status) zzbq.checkNotNull(status, "Status must not be null");
        this.zzfsl = z;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BooleanResult)) {
            return false;
        }
        BooleanResult booleanResult = (BooleanResult) obj;
        return this.mStatus.equals(booleanResult.mStatus) && this.zzfsl == booleanResult.zzfsl;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public boolean getValue() {
        return this.zzfsl;
    }

    public final int hashCode() {
        return (this.zzfsl ? 1 : 0) + ((this.mStatus.hashCode() + 527) * 31);
    }
}
