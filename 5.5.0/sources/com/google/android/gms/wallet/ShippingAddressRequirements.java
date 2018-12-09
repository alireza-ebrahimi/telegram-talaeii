package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import java.util.ArrayList;
import java.util.Collection;

@Class(creator = "ShippingAddressRequirementsCreator")
public final class ShippingAddressRequirements extends AbstractSafeParcelable {
    public static final Creator<ShippingAddressRequirements> CREATOR = new zzam();
    @Field(id = 1)
    ArrayList<String> zzel;

    public final class Builder {
        private final /* synthetic */ ShippingAddressRequirements zzem;

        private Builder(ShippingAddressRequirements shippingAddressRequirements) {
            this.zzem = shippingAddressRequirements;
        }

        public final Builder addAllowedCountryCode(String str) {
            Preconditions.checkNotEmpty(str, "allowedCountryCode can't be null or empty! If you don't have restrictions, just leave it unset.");
            if (this.zzem.zzel == null) {
                this.zzem.zzel = new ArrayList();
            }
            this.zzem.zzel.add(str);
            return this;
        }

        public final Builder addAllowedCountryCodes(Collection<String> collection) {
            if (collection == null || collection.isEmpty()) {
                throw new IllegalArgumentException("allowedCountryCodes can't be null or empty! If you don't have restrictions, just leave it unset.");
            }
            if (this.zzem.zzel == null) {
                this.zzem.zzel = new ArrayList();
            }
            this.zzem.zzel.addAll(collection);
            return this;
        }

        public final ShippingAddressRequirements build() {
            return this.zzem;
        }
    }

    private ShippingAddressRequirements() {
    }

    @Constructor
    ShippingAddressRequirements(@Param(id = 1) ArrayList<String> arrayList) {
        this.zzel = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<String> getAllowedCountryCodes() {
        return this.zzel;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringList(parcel, 1, this.zzel, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
