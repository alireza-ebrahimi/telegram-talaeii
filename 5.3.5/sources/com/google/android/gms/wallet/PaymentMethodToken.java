package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class PaymentMethodToken extends zzbgl {
    public static final Creator<PaymentMethodToken> CREATOR = new zzaf();
    private int zzlnl;
    private String zzlnm;

    private PaymentMethodToken() {
    }

    PaymentMethodToken(int i, String str) {
        this.zzlnl = i;
        this.zzlnm = str;
    }

    public final int getPaymentMethodTokenizationType() {
        return this.zzlnl;
    }

    public final String getToken() {
        return this.zzlnm;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.zzlnl);
        zzbgo.zza(parcel, 3, this.zzlnm, false);
        zzbgo.zzai(parcel, zze);
    }
}
