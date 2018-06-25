package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;

public final class ShippingAddressRequirements extends zzbgl {
    public static final Creator<ShippingAddressRequirements> CREATOR = new zzam();
    ArrayList<String> zzlnu;

    public final class Builder {
        private /* synthetic */ ShippingAddressRequirements zzlnv;

        private Builder(ShippingAddressRequirements shippingAddressRequirements) {
            this.zzlnv = shippingAddressRequirements;
        }

        public final Builder addAllowedCountryCode(@NonNull String str) {
            zzbq.zzh(str, "allowedCountryCode can't be null or empty! If you don't have restrictions, just leave it unset.");
            if (this.zzlnv.zzlnu == null) {
                this.zzlnv.zzlnu = new ArrayList();
            }
            this.zzlnv.zzlnu.add(str);
            return this;
        }

        public final Builder addAllowedCountryCodes(@NonNull Collection<String> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("allowedCountryCodes can't be null or empty! If you don't have restrictions, just leave it unset.");
            }
            if (this.zzlnv.zzlnu == null) {
                this.zzlnv.zzlnu = new ArrayList();
            }
            this.zzlnv.zzlnu.addAll(collection);
            return this;
        }

        public final ShippingAddressRequirements build() {
            return this.zzlnv;
        }
    }

    private ShippingAddressRequirements() {
    }

    ShippingAddressRequirements(ArrayList<String> arrayList) {
        this.zzlnu = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Nullable
    public final ArrayList<String> getAllowedCountryCodes() {
        return this.zzlnu;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzb(parcel, 1, this.zzlnu, false);
        zzbgo.zzai(parcel, zze);
    }
}
