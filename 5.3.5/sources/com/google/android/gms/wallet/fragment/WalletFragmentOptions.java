package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.C0489R;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class WalletFragmentOptions extends zzbgl implements ReflectedParcelable {
    public static final Creator<WalletFragmentOptions> CREATOR = new zzf();
    private int mTheme;
    private int zzgpd;
    private int zzlod;
    private WalletFragmentStyle zzlpk;

    public final class Builder {
        private /* synthetic */ WalletFragmentOptions zzlpl;

        private Builder(WalletFragmentOptions walletFragmentOptions) {
            this.zzlpl = walletFragmentOptions;
        }

        public final WalletFragmentOptions build() {
            return this.zzlpl;
        }

        public final Builder setEnvironment(int i) {
            this.zzlpl.zzlod = i;
            return this;
        }

        public final Builder setFragmentStyle(int i) {
            this.zzlpl.zzlpk = new WalletFragmentStyle().setStyleResourceId(i);
            return this;
        }

        public final Builder setFragmentStyle(WalletFragmentStyle walletFragmentStyle) {
            this.zzlpl.zzlpk = walletFragmentStyle;
            return this;
        }

        public final Builder setMode(int i) {
            this.zzlpl.zzgpd = i;
            return this;
        }

        public final Builder setTheme(int i) {
            this.zzlpl.mTheme = i;
            return this;
        }
    }

    private WalletFragmentOptions() {
        this.zzlod = 3;
        this.zzlpk = new WalletFragmentStyle();
    }

    WalletFragmentOptions(int i, int i2, WalletFragmentStyle walletFragmentStyle, int i3) {
        this.zzlod = i;
        this.mTheme = i2;
        this.zzlpk = walletFragmentStyle;
        this.zzgpd = i3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Hide
    public static WalletFragmentOptions zza(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0489R.styleable.WalletFragmentOptions);
        int i = obtainStyledAttributes.getInt(C0489R.styleable.WalletFragmentOptions_appTheme, 0);
        int i2 = obtainStyledAttributes.getInt(C0489R.styleable.WalletFragmentOptions_environment, 1);
        int resourceId = obtainStyledAttributes.getResourceId(C0489R.styleable.WalletFragmentOptions_fragmentStyle, 0);
        int i3 = obtainStyledAttributes.getInt(C0489R.styleable.WalletFragmentOptions_fragmentMode, 1);
        obtainStyledAttributes.recycle();
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.mTheme = i;
        walletFragmentOptions.zzlod = i2;
        walletFragmentOptions.zzlpk = new WalletFragmentStyle().setStyleResourceId(resourceId);
        walletFragmentOptions.zzlpk.zzet(context);
        walletFragmentOptions.zzgpd = i3;
        return walletFragmentOptions;
    }

    public final int getEnvironment() {
        return this.zzlod;
    }

    public final WalletFragmentStyle getFragmentStyle() {
        return this.zzlpk;
    }

    public final int getMode() {
        return this.zzgpd;
    }

    public final int getTheme() {
        return this.mTheme;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, getEnvironment());
        zzbgo.zzc(parcel, 3, getTheme());
        zzbgo.zza(parcel, 4, getFragmentStyle(), i, false);
        zzbgo.zzc(parcel, 5, getMode());
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    public final void zzet(Context context) {
        if (this.zzlpk != null) {
            this.zzlpk.zzet(context);
        }
    }
}
