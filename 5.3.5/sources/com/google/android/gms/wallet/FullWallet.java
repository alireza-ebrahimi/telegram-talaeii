package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class FullWallet extends zzbgl implements ReflectedParcelable {
    public static final Creator<FullWallet> CREATOR = new zzk();
    private String zzlkc;
    private String zzlkd;
    private ProxyCard zzlke;
    private String zzlkf;
    private zza zzlkg;
    private zza zzlkh;
    private String[] zzlki;
    private UserAddress zzlkj;
    private UserAddress zzlkk;
    private InstrumentInfo[] zzlkl;
    private PaymentMethodToken zzlkm;

    private FullWallet() {
    }

    FullWallet(String str, String str2, ProxyCard proxyCard, String str3, zza zza, zza zza2, String[] strArr, UserAddress userAddress, UserAddress userAddress2, InstrumentInfo[] instrumentInfoArr, PaymentMethodToken paymentMethodToken) {
        this.zzlkc = str;
        this.zzlkd = str2;
        this.zzlke = proxyCard;
        this.zzlkf = str3;
        this.zzlkg = zza;
        this.zzlkh = zza2;
        this.zzlki = strArr;
        this.zzlkj = userAddress;
        this.zzlkk = userAddress2;
        this.zzlkl = instrumentInfoArr;
        this.zzlkm = paymentMethodToken;
    }

    public final UserAddress getBuyerBillingAddress() {
        return this.zzlkj;
    }

    public final UserAddress getBuyerShippingAddress() {
        return this.zzlkk;
    }

    public final String getEmail() {
        return this.zzlkf;
    }

    public final String getGoogleTransactionId() {
        return this.zzlkc;
    }

    public final InstrumentInfo[] getInstrumentInfos() {
        return this.zzlkl;
    }

    public final String getMerchantTransactionId() {
        return this.zzlkd;
    }

    public final String[] getPaymentDescriptions() {
        return this.zzlki;
    }

    public final PaymentMethodToken getPaymentMethodToken() {
        return this.zzlkm;
    }

    public final ProxyCard getProxyCard() {
        return this.zzlke;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlkc, false);
        zzbgo.zza(parcel, 3, this.zzlkd, false);
        zzbgo.zza(parcel, 4, this.zzlke, i, false);
        zzbgo.zza(parcel, 5, this.zzlkf, false);
        zzbgo.zza(parcel, 6, this.zzlkg, i, false);
        zzbgo.zza(parcel, 7, this.zzlkh, i, false);
        zzbgo.zza(parcel, 8, this.zzlki, false);
        zzbgo.zza(parcel, 9, this.zzlkj, i, false);
        zzbgo.zza(parcel, 10, this.zzlkk, i, false);
        zzbgo.zza(parcel, 11, this.zzlkl, i, false);
        zzbgo.zza(parcel, 12, this.zzlkm, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
