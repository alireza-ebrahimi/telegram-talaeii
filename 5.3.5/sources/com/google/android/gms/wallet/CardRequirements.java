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

public final class CardRequirements extends zzbgl {
    public static final Creator<CardRequirements> CREATOR = new zze();
    ArrayList<Integer> zzljo;
    boolean zzljp;
    boolean zzljq;
    int zzljr;

    public final class Builder {
        private /* synthetic */ CardRequirements zzljs;

        private Builder(CardRequirements cardRequirements) {
            this.zzljs = cardRequirements;
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (this.zzljs.zzljo == null) {
                this.zzljs.zzljo = new ArrayList();
            }
            this.zzljs.zzljo.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(@NonNull Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            zzbq.checkArgument(z, "allowedCardNetworks can't be null or empty! You must provide a valid value from WalletConstants.CardNetwork.");
            if (this.zzljs.zzljo == null) {
                this.zzljs.zzljo = new ArrayList();
            }
            this.zzljs.zzljo.addAll(collection);
            return this;
        }

        public final CardRequirements build() {
            zzbq.checkNotNull(this.zzljs.zzljo, "Allowed card networks must be non-empty! You can set it through addAllowedCardNetwork() or addAllowedCardNetworks() in the CardRequirements Builder.");
            return this.zzljs;
        }

        public final Builder setAllowPrepaidCards(boolean z) {
            this.zzljs.zzljp = z;
            return this;
        }

        public final Builder setBillingAddressFormat(int i) {
            this.zzljs.zzljr = i;
            return this;
        }

        public final Builder setBillingAddressRequired(boolean z) {
            this.zzljs.zzljq = z;
            return this;
        }
    }

    private CardRequirements() {
        this.zzljp = true;
    }

    CardRequirements(ArrayList<Integer> arrayList, boolean z, boolean z2, int i) {
        this.zzljo = arrayList;
        this.zzljp = z;
        this.zzljq = z2;
        this.zzljr = i;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final boolean allowPrepaidCards() {
        return this.zzljp;
    }

    @Nullable
    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzljo;
    }

    public final int getBillingAddressFormat() {
        return this.zzljr;
    }

    public final boolean isBillingAddressRequired() {
        return this.zzljq;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzljo, false);
        zzbgo.zza(parcel, 2, this.zzljp);
        zzbgo.zza(parcel, 3, this.zzljq);
        zzbgo.zzc(parcel, 4, this.zzljr);
        zzbgo.zzai(parcel, zze);
    }
}
