package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class LoyaltyPoints extends zzbgl {
    public static final Creator<LoyaltyPoints> CREATOR = new zzi();
    String label;
    TimeInterval zzllt;
    LoyaltyPointsBalance zzlpy;

    public final class Builder {
        private /* synthetic */ LoyaltyPoints zzlpz;

        private Builder(LoyaltyPoints loyaltyPoints) {
            this.zzlpz = loyaltyPoints;
        }

        public final LoyaltyPoints build() {
            return this.zzlpz;
        }

        public final Builder setBalance(LoyaltyPointsBalance loyaltyPointsBalance) {
            this.zzlpz.zzlpy = loyaltyPointsBalance;
            return this;
        }

        public final Builder setLabel(String str) {
            this.zzlpz.label = str;
            return this;
        }

        @Deprecated
        public final Builder setType(String str) {
            return this;
        }

        public final Builder setValidTimeInterval(TimeInterval timeInterval) {
            this.zzlpz.zzllt = timeInterval;
            return this;
        }
    }

    LoyaltyPoints() {
    }

    LoyaltyPoints(String str, LoyaltyPointsBalance loyaltyPointsBalance, TimeInterval timeInterval) {
        this.label = str;
        this.zzlpy = loyaltyPointsBalance;
        this.zzllt = timeInterval;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final LoyaltyPointsBalance getBalance() {
        return this.zzlpy;
    }

    public final String getLabel() {
        return this.label;
    }

    @Deprecated
    public final String getType() {
        return null;
    }

    public final TimeInterval getValidTimeInterval() {
        return this.zzllt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.label, false);
        zzbgo.zza(parcel, 3, this.zzlpy, i, false);
        zzbgo.zza(parcel, 5, this.zzllt, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
