package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;

public final class IsReadyToPayRequest extends zzbgl {
    @Hide
    public static final Creator<IsReadyToPayRequest> CREATOR = new zzr();
    ArrayList<Integer> zzljo;
    private String zzllb;
    private String zzllc;
    ArrayList<Integer> zzlld;
    boolean zzlle;

    public final class Builder {
        private /* synthetic */ IsReadyToPayRequest zzllf;

        private Builder(IsReadyToPayRequest isReadyToPayRequest) {
            this.zzllf = isReadyToPayRequest;
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (this.zzllf.zzljo == null) {
                this.zzllf.zzljo = new ArrayList();
            }
            this.zzllf.zzljo.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            zzbq.checkArgument(z, "allowedCardNetworks can't be null or empty. If you want the defaults, leave it unset.");
            if (this.zzllf.zzljo == null) {
                this.zzllf.zzljo = new ArrayList();
            }
            this.zzllf.zzljo.addAll(collection);
            return this;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (this.zzllf.zzlld == null) {
                this.zzllf.zzlld = new ArrayList();
            }
            this.zzllf.zzlld.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            zzbq.checkArgument(z, "allowedPaymentMethods can't be null or empty. If you want the default, leave it unset.");
            if (this.zzllf.zzlld == null) {
                this.zzllf.zzlld = new ArrayList();
            }
            this.zzllf.zzlld.addAll(collection);
            return this;
        }

        public final IsReadyToPayRequest build() {
            return this.zzllf;
        }

        public final Builder setExistingPaymentMethodRequired(boolean z) {
            this.zzllf.zzlle = z;
            return this;
        }
    }

    IsReadyToPayRequest() {
    }

    IsReadyToPayRequest(ArrayList<Integer> arrayList, String str, String str2, ArrayList<Integer> arrayList2, boolean z) {
        this.zzljo = arrayList;
        this.zzllb = str;
        this.zzllc = str2;
        this.zzlld = arrayList2;
        this.zzlle = z;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzljo;
    }

    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzlld;
    }

    public final boolean isExistingPaymentMethodRequired() {
        return this.zzlle;
    }

    @Hide
    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzljo, false);
        zzbgo.zza(parcel, 4, this.zzllb, false);
        zzbgo.zza(parcel, 5, this.zzllc, false);
        zzbgo.zza(parcel, 6, this.zzlld, false);
        zzbgo.zza(parcel, 7, this.zzlle);
        zzbgo.zzai(parcel, zze);
    }
}
