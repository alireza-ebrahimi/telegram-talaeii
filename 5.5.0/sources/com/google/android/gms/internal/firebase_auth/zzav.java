package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzg.zzd;
import com.google.firebase.auth.p104a.p105a.C1794n;

@Class(creator = "ResetPasswordResponseCreator")
@Reserved({1})
public final class zzav extends AbstractSafeParcelable implements C1794n<zzav, zzd> {
    public static final Creator<zzav> CREATOR = new zzaw();
    @Field(getter = "getEmail", id = 2)
    private String zzah;
    @Field(getter = "getNewEmail", id = 3)
    private String zzas;
    @Field(getter = "getRequestType", id = 4)
    private String zzjw;

    @Constructor
    zzav(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3) {
        this.zzah = str;
        this.zzas = str2;
        this.zzjw = str3;
    }

    public final String getEmail() {
        return this.zzah;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzah, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzas, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzjw, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        String str;
        zzd zzd = (zzd) zzgt;
        this.zzah = Strings.emptyToNull(zzd.zzah);
        this.zzas = Strings.emptyToNull(zzd.zzas);
        switch (zzd.zzbc) {
            case 1:
                str = "PASSWORD_RESET";
                break;
            case 4:
                str = "VERIFY_EMAIL";
                break;
            default:
                str = null;
                break;
        }
        this.zzjw = str;
        return this;
    }

    public final Class<zzd> zzag() {
        return zzd.class;
    }

    public final String zzba() {
        return this.zzas;
    }

    public final String zzbb() {
        return this.zzjw;
    }
}
