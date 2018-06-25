package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class LineItem extends zzbgl {
    public static final Creator<LineItem> CREATOR = new zzt();
    String description;
    String zzljt;
    String zzlju;
    String zzllg;
    String zzllh;
    int zzlli;

    public final class Builder {
        private /* synthetic */ LineItem zzllj;

        private Builder(LineItem lineItem) {
            this.zzllj = lineItem;
        }

        public final LineItem build() {
            return this.zzllj;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzllj.zzlju = str;
            return this;
        }

        public final Builder setDescription(String str) {
            this.zzllj.description = str;
            return this;
        }

        public final Builder setQuantity(String str) {
            this.zzllj.zzllg = str;
            return this;
        }

        public final Builder setRole(int i) {
            this.zzllj.zzlli = i;
            return this;
        }

        public final Builder setTotalPrice(String str) {
            this.zzllj.zzljt = str;
            return this;
        }

        public final Builder setUnitPrice(String str) {
            this.zzllj.zzllh = str;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    LineItem() {
        this.zzlli = 0;
    }

    LineItem(String str, String str2, String str3, String str4, int i, String str5) {
        this.description = str;
        this.zzllg = str2;
        this.zzllh = str3;
        this.zzljt = str4;
        this.zzlli = i;
        this.zzlju = str5;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzlju;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getQuantity() {
        return this.zzllg;
    }

    public final int getRole() {
        return this.zzlli;
    }

    public final String getTotalPrice() {
        return this.zzljt;
    }

    public final String getUnitPrice() {
        return this.zzllh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.description, false);
        zzbgo.zza(parcel, 3, this.zzllg, false);
        zzbgo.zza(parcel, 4, this.zzllh, false);
        zzbgo.zza(parcel, 5, this.zzljt, false);
        zzbgo.zzc(parcel, 6, this.zzlli);
        zzbgo.zza(parcel, 7, this.zzlju, false);
        zzbgo.zzai(parcel, zze);
    }
}
