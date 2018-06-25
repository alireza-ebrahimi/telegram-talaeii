package com.google.android.gms.wallet.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.wallet.C1806R;

@Class(creator = "WalletFragmentOptionsCreator")
@Reserved({1})
public final class WalletFragmentOptions extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<WalletFragmentOptions> CREATOR = new zzf();
    @Field(defaultValueUnchecked = "com.google.android.gms.wallet.WalletConstants.ENVIRONMENT_PRODUCTION", getter = "getEnvironment", id = 2)
    private int environment;
    @Field(defaultValueUnchecked = "com.google.android.gms.wallet.fragment.WalletFragmentMode.BUY_BUTTON", getter = "getMode", id = 5)
    private int mode;
    @Field(defaultValueUnchecked = "com.google.android.gms.wallet.WalletConstants.THEME_DARK", getter = "getTheme", id = 3)
    private int theme;
    @Field(getter = "getFragmentStyle", id = 4)
    private WalletFragmentStyle zzfz;

    public final class Builder {
        private final /* synthetic */ WalletFragmentOptions zzga;

        private Builder(WalletFragmentOptions walletFragmentOptions) {
            this.zzga = walletFragmentOptions;
        }

        public final WalletFragmentOptions build() {
            return this.zzga;
        }

        public final Builder setEnvironment(int i) {
            this.zzga.environment = i;
            return this;
        }

        public final Builder setFragmentStyle(int i) {
            this.zzga.zzfz = new WalletFragmentStyle().setStyleResourceId(i);
            return this;
        }

        public final Builder setFragmentStyle(WalletFragmentStyle walletFragmentStyle) {
            this.zzga.zzfz = walletFragmentStyle;
            return this;
        }

        public final Builder setMode(int i) {
            this.zzga.mode = i;
            return this;
        }

        public final Builder setTheme(int i) {
            this.zzga.theme = i;
            return this;
        }
    }

    private WalletFragmentOptions() {
        this.environment = 3;
        this.zzfz = new WalletFragmentStyle();
    }

    @Constructor
    WalletFragmentOptions(@Param(id = 2) int i, @Param(id = 3) int i2, @Param(id = 4) WalletFragmentStyle walletFragmentStyle, @Param(id = 5) int i3) {
        this.environment = i;
        this.theme = i2;
        this.zzfz = walletFragmentStyle;
        this.mode = i3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static WalletFragmentOptions zza(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1806R.styleable.WalletFragmentOptions);
        int i = obtainStyledAttributes.getInt(C1806R.styleable.WalletFragmentOptions_appTheme, 0);
        int i2 = obtainStyledAttributes.getInt(C1806R.styleable.WalletFragmentOptions_environment, 1);
        int resourceId = obtainStyledAttributes.getResourceId(C1806R.styleable.WalletFragmentOptions_fragmentStyle, 0);
        int i3 = obtainStyledAttributes.getInt(C1806R.styleable.WalletFragmentOptions_fragmentMode, 1);
        obtainStyledAttributes.recycle();
        WalletFragmentOptions walletFragmentOptions = new WalletFragmentOptions();
        walletFragmentOptions.theme = i;
        walletFragmentOptions.environment = i2;
        walletFragmentOptions.zzfz = new WalletFragmentStyle().setStyleResourceId(resourceId);
        walletFragmentOptions.zzfz.zza(context);
        walletFragmentOptions.mode = i3;
        return walletFragmentOptions;
    }

    public final int getEnvironment() {
        return this.environment;
    }

    public final WalletFragmentStyle getFragmentStyle() {
        return this.zzfz;
    }

    public final int getMode() {
        return this.mode;
    }

    public final int getTheme() {
        return this.theme;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, getEnvironment());
        SafeParcelWriter.writeInt(parcel, 3, getTheme());
        SafeParcelWriter.writeParcelable(parcel, 4, getFragmentStyle(), i, false);
        SafeParcelWriter.writeInt(parcel, 5, getMode());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final void zza(Context context) {
        if (this.zzfz != null) {
            this.zzfz.zza(context);
        }
    }
}
