package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbgq;
import java.util.Collections;
import java.util.List;

public class ActivityTransitionResult extends zzbgl {
    public static final Creator<ActivityTransitionResult> CREATOR = new zzg();
    private final List<ActivityTransitionEvent> zzirb;

    public ActivityTransitionResult(List<ActivityTransitionEvent> list) {
        zzbq.checkNotNull(list, "transitionEvents list can't be null.");
        if (!list.isEmpty()) {
            for (int i = 1; i < list.size(); i++) {
                zzbq.checkArgument(((ActivityTransitionEvent) list.get(i)).getElapsedRealTimeNanos() >= ((ActivityTransitionEvent) list.get(i + -1)).getElapsedRealTimeNanos());
            }
        }
        this.zzirb = Collections.unmodifiableList(list);
    }

    @Nullable
    public static ActivityTransitionResult extractResult(Intent intent) {
        return !hasResult(intent) ? null : (ActivityTransitionResult) zzbgq.zza(intent, "com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT", CREATOR);
    }

    public static boolean hasResult(@Nullable Intent intent) {
        return intent == null ? false : intent.hasExtra("com.google.android.location.internal.EXTRA_ACTIVITY_TRANSITION_RESULT");
    }

    public boolean equals(Object obj) {
        return this == obj ? true : (obj == null || getClass() != obj.getClass()) ? false : this.zzirb.equals(((ActivityTransitionResult) obj).zzirb);
    }

    public List<ActivityTransitionEvent> getTransitionEvents() {
        return this.zzirb;
    }

    public int hashCode() {
        return this.zzirb.hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, getTransitionEvents(), false);
        zzbgo.zzai(parcel, zze);
    }
}
