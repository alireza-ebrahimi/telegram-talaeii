package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;

public abstract class StatsEvent extends zzbgl implements ReflectedParcelable {
    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long timeMillis = getTimeMillis();
        int eventType = getEventType();
        long zzann = zzann();
        String zzano = zzano();
        return new StringBuilder(String.valueOf(zzano).length() + 53).append(timeMillis).append("\t").append(eventType).append("\t").append(zzann).append(zzano).toString();
    }

    public abstract long zzann();

    public abstract String zzano();
}
