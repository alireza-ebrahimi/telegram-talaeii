package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Arrays;

@Class(creator = "AssetCreator")
@Reserved({1})
@VisibleForTesting
public class Asset extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<Asset> CREATOR = new zze();
    @Field(getter = "getData", id = 2)
    private byte[] data;
    @Field(id = 5)
    private Uri uri;
    @Field(getter = "getDigest", id = 3)
    private String zze;
    @Field(id = 4)
    private ParcelFileDescriptor zzf;

    @Constructor
    Asset(@Param(id = 2) byte[] bArr, @Param(id = 3) String str, @Param(id = 4) ParcelFileDescriptor parcelFileDescriptor, @Param(id = 5) Uri uri) {
        this.data = bArr;
        this.zze = str;
        this.zzf = parcelFileDescriptor;
        this.uri = uri;
    }

    @VisibleForTesting
    public static Asset createFromBytes(byte[] bArr) {
        Asserts.checkNotNull(bArr);
        return new Asset(bArr, null, null, null);
    }

    @VisibleForTesting
    public static Asset createFromFd(ParcelFileDescriptor parcelFileDescriptor) {
        Asserts.checkNotNull(parcelFileDescriptor);
        return new Asset(null, null, parcelFileDescriptor, null);
    }

    public static Asset createFromRef(String str) {
        Asserts.checkNotNull(str);
        return new Asset(null, str, null, null);
    }

    @VisibleForTesting
    public static Asset createFromUri(Uri uri) {
        Asserts.checkNotNull(uri);
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
        return Arrays.equals(this.data, asset.data) && Objects.equal(this.zze, asset.zze) && Objects.equal(this.zzf, asset.zzf) && Objects.equal(this.uri, asset.uri);
    }

    public final byte[] getData() {
        return this.data;
    }

    public String getDigest() {
        return this.zze;
    }

    public ParcelFileDescriptor getFd() {
        return this.zzf;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return Arrays.deepHashCode(new Object[]{this.data, this.zze, this.zzf, this.uri});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asset[@");
        stringBuilder.append(Integer.toHexString(hashCode()));
        if (this.zze == null) {
            stringBuilder.append(", nodigest");
        } else {
            stringBuilder.append(", ");
            stringBuilder.append(this.zze);
        }
        if (this.data != null) {
            stringBuilder.append(", size=");
            stringBuilder.append(this.data.length);
        }
        if (this.zzf != null) {
            stringBuilder.append(", fd=");
            stringBuilder.append(this.zzf);
        }
        if (this.uri != null) {
            stringBuilder.append(", uri=");
            stringBuilder.append(this.uri);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        Asserts.checkNotNull(parcel);
        int i2 = i | 1;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByteArray(parcel, 2, this.data, false);
        SafeParcelWriter.writeString(parcel, 3, getDigest(), false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzf, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.uri, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
