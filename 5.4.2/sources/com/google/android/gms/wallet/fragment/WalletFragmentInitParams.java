package com.google.android.gms.wallet.fragment;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

@Class(creator = "WalletFragmentInitParamsCreator")
@Reserved({1})
public final class WalletFragmentInitParams extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<WalletFragmentInitParams> CREATOR = new zzd();
    @Field(getter = "getAccountName", id = 2)
    private String zzci;
    @Field(getter = "getMaskedWalletRequest", id = 3)
    private MaskedWalletRequest zzfi;
    @Field(getter = "getMaskedWallet", id = 5)
    private MaskedWallet zzfj;
    @Field(defaultValue = "-1", getter = "getMaskedWalletRequestCode", id = 4)
    private int zzfx;

    public final class Builder {
        private final /* synthetic */ WalletFragmentInitParams zzfy;

        private Builder(WalletFragmentInitParams walletFragmentInitParams) {
            this.zzfy = walletFragmentInitParams;
        }

        public final WalletFragmentInitParams build() {
            boolean z = true;
            boolean z2 = (this.zzfy.zzfj != null && this.zzfy.zzfi == null) || (this.zzfy.zzfj == null && this.zzfy.zzfi != null);
            Preconditions.checkState(z2, "Exactly one of MaskedWallet or MaskedWalletRequest is required");
            if (this.zzfy.zzfx < 0) {
                z = false;
            }
            Preconditions.checkState(z, "masked wallet request code is required and must be non-negative");
            return this.zzfy;
        }

        public final Builder setAccountName(String str) {
            this.zzfy.zzci = str;
            return this;
        }

        public final Builder setMaskedWallet(MaskedWallet maskedWallet) {
            this.zzfy.zzfj = maskedWallet;
            return this;
        }

        public final Builder setMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            this.zzfy.zzfi = maskedWalletRequest;
            return this;
        }

        public final Builder setMaskedWalletRequestCode(int i) {
            this.zzfy.zzfx = i;
            return this;
        }
    }

    private WalletFragmentInitParams() {
        this.zzfx = -1;
    }

    @Constructor
    WalletFragmentInitParams(@Param(id = 2) String str, @Param(id = 3) MaskedWalletRequest maskedWalletRequest, @Param(id = 4) int i, @Param(id = 5) MaskedWallet maskedWallet) {
        this.zzci = str;
        this.zzfi = maskedWalletRequest;
        this.zzfx = i;
        this.zzfj = maskedWallet;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getAccountName() {
        return this.zzci;
    }

    public final MaskedWallet getMaskedWallet() {
        return this.zzfj;
    }

    public final MaskedWalletRequest getMaskedWalletRequest() {
        return this.zzfi;
    }

    public final int getMaskedWalletRequestCode() {
        return this.zzfx;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getAccountName(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getMaskedWalletRequest(), i, false);
        SafeParcelWriter.writeInt(parcel, 4, getMaskedWalletRequestCode());
        SafeParcelWriter.writeParcelable(parcel, 5, getMaskedWallet(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
