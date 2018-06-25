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

@Class(creator = "CardRequirementsCreator")
public final class CardRequirements extends AbstractSafeParcelable {
    public static final Creator<CardRequirements> CREATOR = new zze();
    @Field(id = 1)
    ArrayList<Integer> zzai;
    @Field(defaultValue = "true", id = 2)
    boolean zzaj;
    @Field(id = 3)
    boolean zzak;
    @Field(id = 4)
    int zzal;

    public final class Builder {
        private final /* synthetic */ CardRequirements zzam;

        private Builder(CardRequirements cardRequirements) {
            this.zzam = cardRequirements;
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (this.zzam.zzai == null) {
                this.zzam.zzai = new ArrayList();
            }
            this.zzam.zzai.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            Preconditions.checkArgument(z, "allowedCardNetworks can't be null or empty! You must provide a valid value from WalletConstants.CardNetwork.");
            if (this.zzam.zzai == null) {
                this.zzam.zzai = new ArrayList();
            }
            this.zzam.zzai.addAll(collection);
            return this;
        }

        public final CardRequirements build() {
            Preconditions.checkNotNull(this.zzam.zzai, "Allowed card networks must be non-empty! You can set it through addAllowedCardNetwork() or addAllowedCardNetworks() in the CardRequirements Builder.");
            return this.zzam;
        }

        public final Builder setAllowPrepaidCards(boolean z) {
            this.zzam.zzaj = z;
            return this;
        }

        public final Builder setBillingAddressFormat(int i) {
            this.zzam.zzal = i;
            return this;
        }

        public final Builder setBillingAddressRequired(boolean z) {
            this.zzam.zzak = z;
            return this;
        }
    }

    private CardRequirements() {
        this.zzaj = true;
    }

    @Constructor
    CardRequirements(@Param(id = 1) ArrayList<Integer> arrayList, @Param(id = 2) boolean z, @Param(id = 3) boolean z2, @Param(id = 4) int i) {
        this.zzai = arrayList;
        this.zzaj = z;
        this.zzak = z2;
        this.zzal = i;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final boolean allowPrepaidCards() {
        return this.zzaj;
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzai;
    }

    public final int getBillingAddressFormat() {
        return this.zzal;
    }

    public final boolean isBillingAddressRequired() {
        return this.zzak;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerList(parcel, 1, this.zzai, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzaj);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzak);
        SafeParcelWriter.writeInt(parcel, 4, this.zzal);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
