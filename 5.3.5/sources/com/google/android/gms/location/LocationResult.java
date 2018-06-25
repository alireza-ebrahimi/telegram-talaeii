package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends zzbgl implements ReflectedParcelable {
    public static final Creator<LocationResult> CREATOR = new zzac();
    static final List<Location> zzisl = Collections.emptyList();
    private final List<Location> zzism;

    @Hide
    LocationResult(List<Location> list) {
        this.zzism = list;
    }

    public static LocationResult create(List<Location> list) {
        List list2;
        if (list == null) {
            list2 = zzisl;
        }
        return new LocationResult(list2);
    }

    public static LocationResult extractResult(Intent intent) {
        return !hasResult(intent) ? null : (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        return intent == null ? false : intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.zzism.size() != this.zzism.size()) {
            return false;
        }
        Iterator it = this.zzism.iterator();
        for (Location time : locationResult.zzism) {
            if (((Location) it.next()).getTime() != time.getTime()) {
                return false;
            }
        }
        return true;
    }

    public final Location getLastLocation() {
        int size = this.zzism.size();
        return size == 0 ? null : (Location) this.zzism.get(size - 1);
    }

    @NonNull
    public final List<Location> getLocations() {
        return this.zzism;
    }

    public final int hashCode() {
        int i = 17;
        for (Location time : this.zzism) {
            long time2 = time.getTime();
            i = ((int) (time2 ^ (time2 >>> 32))) + (i * 31);
        }
        return i;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzism);
        return new StringBuilder(String.valueOf(valueOf).length() + 27).append("LocationResult[locations: ").append(valueOf).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, getLocations(), false);
        zzbgo.zzai(parcel, zze);
    }
}
