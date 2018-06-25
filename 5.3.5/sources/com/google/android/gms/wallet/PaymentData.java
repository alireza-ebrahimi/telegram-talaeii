package com.google.android.gms.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.internal.zzbgp;
import com.google.android.gms.internal.zzbgq;

public final class PaymentData extends zzbgl implements AutoResolvableResult {
    public static final Creator<PaymentData> CREATOR = new zzac();
    private String zzemh;
    private CardInfo zzlmx;
    private UserAddress zzlmy;
    private PaymentMethodToken zzlmz;
    private String zzlna;
    private Bundle zzlnb;

    private PaymentData() {
    }

    PaymentData(String str, CardInfo cardInfo, UserAddress userAddress, PaymentMethodToken paymentMethodToken, String str2, Bundle bundle) {
        this.zzemh = str;
        this.zzlmx = cardInfo;
        this.zzlmy = userAddress;
        this.zzlmz = paymentMethodToken;
        this.zzlna = str2;
        this.zzlnb = bundle;
    }

    @Nullable
    public static PaymentData getFromIntent(@NonNull Intent intent) {
        return (PaymentData) zzbgq.zza(intent, "com.google.android.gms.wallet.PaymentData", CREATOR);
    }

    public final CardInfo getCardInfo() {
        return this.zzlmx;
    }

    @Nullable
    public final String getEmail() {
        return this.zzemh;
    }

    @Nullable
    public final Bundle getExtraData() {
        return this.zzlnb;
    }

    public final String getGoogleTransactionId() {
        return this.zzlna;
    }

    @Nullable
    public final PaymentMethodToken getPaymentMethodToken() {
        return this.zzlmz;
    }

    @Nullable
    public final UserAddress getShippingAddress() {
        return this.zzlmy;
    }

    public final void putIntoIntent(@NonNull Intent intent) {
        zzbgq.zza((zzbgp) this, intent, "com.google.android.gms.wallet.PaymentData");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, this.zzemh, false);
        zzbgo.zza(parcel, 2, this.zzlmx, i, false);
        zzbgo.zza(parcel, 3, this.zzlmy, i, false);
        zzbgo.zza(parcel, 4, this.zzlmz, i, false);
        zzbgo.zza(parcel, 5, this.zzlna, false);
        zzbgo.zza(parcel, 6, this.zzlnb, false);
        zzbgo.zzai(parcel, zze);
    }
}
