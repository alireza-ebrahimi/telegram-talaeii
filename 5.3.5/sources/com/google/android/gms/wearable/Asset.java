package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public class Asset extends zzbgl implements ReflectedParcelable {
    public static final Creator<Asset> CREATOR = new zze();
    @Hide
    private Uri uri;
    private byte[] zzigl;
    private String zzlqn;
    @Hide
    private ParcelFileDescriptor zzlqo;

    @Hide
    Asset(byte[] bArr, String str, ParcelFileDescriptor parcelFileDescriptor, Uri uri) {
        this.zzigl = bArr;
        this.zzlqn = str;
        this.zzlqo = parcelFileDescriptor;
        this.uri = uri;
    }

    public static Asset createFromBytes(@NonNull byte[] bArr) {
        zzc.zzv(bArr);
        return new Asset(bArr, null, null, null);
    }

    public static Asset createFromFd(@NonNull ParcelFileDescriptor parcelFileDescriptor) {
        zzc.zzv(parcelFileDescriptor);
        return new Asset(null, null, parcelFileDescriptor, null);
    }

    public static Asset createFromRef(@NonNull String str) {
        zzc.zzv(str);
        return new Asset(null, str, null, null);
    }

    public static Asset createFromUri(@NonNull Uri uri) {
        zzc.zzv(uri);
        return new Asset(null, null, null, uri);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Asset)) {
            return false;
        }
        Asset asset = (Asset) obj;
        return Arrays.equals(this.zzigl, asset.zzigl) && zzbg.equal(this.zzlqn, asset.zzlqn) && zzbg.equal(this.zzlqo, asset.zzlqo) && zzbg.equal(this.uri, asset.uri);
    }

    @Hide
    public final byte[] getData() {
        return this.zzigl;
    }

    public String getDigest() {
        return this.zzlqn;
    }

    public ParcelFileDescriptor getFd() {
        return this.zzlqo;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Arrays.deepHashCode(new Object[]{this.zzigl, this.zzlqn, this.zzlqo, this.uri});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asset[@");
        stringBuilder.append(Integer.toHexString(hashCode()));
        if (this.zzlqn == null) {
            stringBuilder.append(", nodigest");
        } else {
            stringBuilder.append(", ");
            stringBuilder.append(this.zzlqn);
        }
        if (this.zzigl != null) {
            stringBuilder.append(", size=");
            stringBuilder.append(this.zzigl.length);
        }
        if (this.zzlqo != null) {
            stringBuilder.append(", fd=");
            stringBuilder.append(this.zzlqo);
        }
        if (this.uri != null) {
            stringBuilder.append(", uri=");
            stringBuilder.append(this.uri);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        zzc.zzv(parcel);
        int i2 = i | 1;
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzigl, false);
        zzbgo.zza(parcel, 3, getDigest(), false);
        zzbgo.zza(parcel, 4, this.zzlqo, i2, false);
        zzbgo.zza(parcel, 5, this.uri, i2, false);
        zzbgo.zzai(parcel, zze);
    }
}
