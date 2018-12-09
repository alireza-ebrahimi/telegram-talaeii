package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.ICertData.Stub;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import javax.annotation.Nullable;

@Class(creator = "GoogleCertificatesQueryCreator")
public class GoogleCertificatesQuery extends AbstractSafeParcelable {
    public static final Creator<GoogleCertificatesQuery> CREATOR = new GoogleCertificatesQueryCreator();
    @Field(getter = "getCallingPackage", id = 1)
    private final String zzbh;
    @Field(getter = "getCallingCertificateBinder", id = 2, type = "android.os.IBinder")
    @Nullable
    private final CertData zzbi;
    @Field(getter = "getAllowTestKeys", id = 3)
    private final boolean zzbj;

    @Constructor
    GoogleCertificatesQuery(@Param(id = 1) String str, @Param(id = 2) @Nullable IBinder iBinder, @Param(id = 3) boolean z) {
        this.zzbh = str;
        this.zzbi = zza(iBinder);
        this.zzbj = z;
    }

    GoogleCertificatesQuery(String str, @Nullable CertData certData, boolean z) {
        this.zzbh = str;
        this.zzbi = certData;
        this.zzbj = z;
    }

    @Nullable
    private static CertData zza(@Nullable IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        try {
            CertData zzb;
            IObjectWrapper bytesWrapped = Stub.asInterface(iBinder).getBytesWrapped();
            byte[] bArr = bytesWrapped == null ? null : (byte[]) ObjectWrapper.unwrap(bytesWrapped);
            if (bArr != null) {
                zzb = new zzb(bArr);
            } else {
                Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
                zzb = null;
            }
            return zzb;
        } catch (Throwable e) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", e);
            return null;
        }
    }

    public boolean getAllowTestKeys() {
        return this.zzbj;
    }

    @Nullable
    public IBinder getCallingCertificateBinder() {
        if (this.zzbi != null) {
            return this.zzbi.asBinder();
        }
        Log.w("GoogleCertificatesQuery", "certificate binder is null");
        return null;
    }

    public String getCallingPackage() {
        return this.zzbh;
    }

    @Nullable
    public CertData getCertificate() {
        return this.zzbi;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getCallingPackage(), false);
        SafeParcelWriter.writeIBinder(parcel, 2, getCallingCertificateBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 3, getAllowTestKeys());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
