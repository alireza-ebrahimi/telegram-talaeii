package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class LocationSettingsRequest extends zzbgl {
    public static final Creator<LocationSettingsRequest> CREATOR = new zzag();
    private final List<LocationRequest> zzisp;
    private final boolean zzisq;
    private final boolean zzisr;
    @Hide
    private zzae zziss;

    public static final class Builder {
        private boolean zzisq = false;
        private boolean zzisr = false;
        private zzae zziss = null;
        private final ArrayList<LocationRequest> zzist = new ArrayList();

        public final Builder addAllLocationRequests(Collection<LocationRequest> collection) {
            for (LocationRequest locationRequest : collection) {
                if (locationRequest != null) {
                    this.zzist.add(locationRequest);
                }
            }
            return this;
        }

        public final Builder addLocationRequest(@NonNull LocationRequest locationRequest) {
            if (locationRequest != null) {
                this.zzist.add(locationRequest);
            }
            return this;
        }

        public final LocationSettingsRequest build() {
            return new LocationSettingsRequest(this.zzist, this.zzisq, this.zzisr, null);
        }

        public final Builder setAlwaysShow(boolean z) {
            this.zzisq = z;
            return this;
        }

        public final Builder setNeedBle(boolean z) {
            this.zzisr = z;
            return this;
        }
    }

    @Hide
    LocationSettingsRequest(List<LocationRequest> list, boolean z, boolean z2, zzae zzae) {
        this.zzisp = list;
        this.zzisq = z;
        this.zzisr = z2;
        this.zziss = zzae;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, Collections.unmodifiableList(this.zzisp), false);
        zzbgo.zza(parcel, 2, this.zzisq);
        zzbgo.zza(parcel, 3, this.zzisr);
        zzbgo.zza(parcel, 5, this.zziss, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
