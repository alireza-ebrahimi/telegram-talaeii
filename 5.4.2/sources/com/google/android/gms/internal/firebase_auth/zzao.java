package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.firebase_auth.zzgy.zza;
import com.google.firebase.auth.p104a.p105a.C1794n;
import org.json.JSONObject;

@Class(creator = "GetTokenResponseCreator")
@Reserved({1})
public final class zzao extends AbstractSafeParcelable implements C1794n<zzao, zza> {
    public static final Creator<zzao> CREATOR = new zzap();
    @Field(getter = "getRefreshToken", id = 2)
    private String zzai;
    @Field(getter = "getAccessToken", id = 3)
    private String zzdv;
    @Field(getter = "getExpiresIn", id = 4)
    private Long zzjx;
    @Field(getter = "getTokenType", id = 5)
    private String zzjy;
    @Field(getter = "getIssuedAt", id = 6)
    private Long zzjz;

    public zzao() {
        this.zzjz = Long.valueOf(System.currentTimeMillis());
    }

    public zzao(String str, String str2, Long l, String str3) {
        this(str, str2, l, str3, Long.valueOf(System.currentTimeMillis()));
    }

    @Constructor
    zzao(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) Long l, @Param(id = 5) String str3, @Param(id = 6) Long l2) {
        this.zzai = str;
        this.zzdv = str2;
        this.zzjx = l;
        this.zzjy = str3;
        this.zzjz = l2;
    }

    public static zzao zzs(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            zzao zzao = new zzao();
            zzao.zzai = jSONObject.optString("refresh_token", null);
            zzao.zzdv = jSONObject.optString("access_token", null);
            zzao.zzjx = Long.valueOf(jSONObject.optLong("expires_in"));
            zzao.zzjy = jSONObject.optString("token_type", null);
            zzao.zzjz = Long.valueOf(jSONObject.optLong("issued_at"));
            return zzao;
        } catch (Throwable e) {
            Log.d("GetTokenResponse", "Failed to read GetTokenResponse from JSONObject");
            throw new zzv(e);
        }
    }

    public final boolean isValid() {
        return DefaultClock.getInstance().currentTimeMillis() + 300000 < this.zzjz.longValue() + (this.zzjx.longValue() * 1000);
    }

    public final String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("refresh_token", this.zzai);
            jSONObject.put("access_token", this.zzdv);
            jSONObject.put("expires_in", this.zzjx);
            jSONObject.put("token_type", this.zzjy);
            jSONObject.put("issued_at", this.zzjz);
            return jSONObject.toString();
        } catch (Throwable e) {
            Log.d("GetTokenResponse", "Failed to convert GetTokenResponse to JSON");
            throw new zzv(e);
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzai, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzdv, false);
        SafeParcelWriter.writeLongObject(parcel, 4, Long.valueOf(zzaq()), false);
        SafeParcelWriter.writeString(parcel, 5, this.zzjy, false);
        SafeParcelWriter.writeLongObject(parcel, 6, Long.valueOf(this.zzjz.longValue()), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final /* synthetic */ C1794n zza(zzgt zzgt) {
        zza zza = (zza) zzgt;
        this.zzai = Strings.emptyToNull(zza.zzai);
        this.zzdv = Strings.emptyToNull(zza.zzdv);
        this.zzjx = Long.valueOf(zza.zzaj);
        this.zzjy = Strings.emptyToNull(zza.zzjy);
        this.zzjz = Long.valueOf(System.currentTimeMillis());
        return this;
    }

    public final Class<zza> zzag() {
        return zza.class;
    }

    public final String zzap() {
        return this.zzai;
    }

    public final long zzaq() {
        return this.zzjx == null ? 0 : this.zzjx.longValue();
    }

    public final String zzaw() {
        return this.zzdv;
    }

    public final String zzax() {
        return this.zzjy;
    }

    public final long zzay() {
        return this.zzjz.longValue();
    }

    public final void zzr(String str) {
        this.zzai = Preconditions.checkNotEmpty(str);
    }
}
