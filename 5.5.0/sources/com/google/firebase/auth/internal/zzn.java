package com.google.firebase.auth.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.auth.FirebaseUserMetadata;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "DefaultFirebaseUserMetadataCreator")
public final class zzn implements FirebaseUserMetadata {
    public static final Creator<zzn> CREATOR = new C1874n();
    @Field(getter = "getLastSignInTimestamp", id = 1)
    /* renamed from: a */
    private long f5562a;
    @Field(getter = "getCreationTimestamp", id = 2)
    /* renamed from: b */
    private long f5563b;

    @Constructor
    public zzn(@Param(id = 1) long j, @Param(id = 2) long j2) {
        this.f5562a = j;
        this.f5563b = j2;
    }

    /* renamed from: a */
    public static zzn m8662a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return new zzn(jSONObject.getLong("lastSignInTimestamp"), jSONObject.getLong("creationTimestamp"));
        } catch (JSONException e) {
            return null;
        }
    }

    /* renamed from: a */
    public final long m8663a() {
        return this.f5562a;
    }

    /* renamed from: b */
    public final long m8664b() {
        return this.f5563b;
    }

    /* renamed from: c */
    public final JSONObject m8665c() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lastSignInTimestamp", this.f5562a);
            jSONObject.put("creationTimestamp", this.f5563b);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 1, m8663a());
        SafeParcelWriter.writeLong(parcel, 2, m8664b());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
