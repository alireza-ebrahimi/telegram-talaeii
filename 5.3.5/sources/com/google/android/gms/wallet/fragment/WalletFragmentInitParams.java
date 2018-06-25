package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class WalletFragmentInitParams extends zzbgl implements ReflectedParcelable {
    public static final Creator<WalletFragmentInitParams> CREATOR = new zzd();
    private String zzehk;
    private MaskedWalletRequest zzlou;
    private MaskedWallet zzlov;
    private int zzlpi;

    public final class Builder {
        private /* synthetic */ WalletFragmentInitParams zzlpj;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.zzlpj = walletFragmentInitParams;
        }

        public final WalletFragmentInitParams build() {
            boolean z = true;
            boolean z2 = (this.zzlpj.zzlov != null && this.zzlpj.zzlou == null) || (this.zzlpj.zzlov == null && this.zzlpj.zzlou != null);
            zzbq.zza(z2, (Object) "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            if (this.zzlpj.zzlpi < 0) {
                z = false;
            }
            zzbq.zza(z, (Object) "masked wallet request code is required and must be non-negative");
            return this.zzlpj;
        }

        public final Builder setAccountName(String str) {
            this.zzlpj.zzehk = str;
            return this;
        }

        public final Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.zzlpj.zzlov = maskedWallet;
            return this;
        }

        public final Builder setMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            this.zzlpj.zzlou = maskedWalletRequest;
            return this;
        }

        public final Builder setMaskedWalletRequestCode(int i) {
            this.zzlpj.zzlpi = i;
            return this;
        }
    }

    private WalletFragmentInitParams() {
        this.zzlpi = -1;
    }

    WalletFragmentInitParams(String str, MaskedWalletRequest maskedWalletRequest, int i, MaskedWallet maskedWallet) {
        this.zzehk = str;
        this.zzlou = maskedWalletRequest;
        this.zzlpi = i;
        this.zzlov = maskedWallet;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getAccountName() {
        return this.zzehk;
    }

    public final MaskedWallet getMaskedWallet() {
        return this.zzlov;
    }

    public final MaskedWalletRequest getMaskedWalletRequest() {
        return this.zzlou;
    }

    public final int getMaskedWalletRequestCode() {
        return this.zzlpi;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getAccountName(), false);
        zzbgo.zza(parcel, 3, getMaskedWalletRequest(), i, false);
        zzbgo.zzc(parcel, 4, getMaskedWalletRequestCode());
        zzbgo.zza(parcel, 5, getMaskedWallet(), i, false);
        zzbgo.zzai(parcel, zze);
    }
}
