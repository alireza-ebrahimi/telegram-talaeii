package com.google.android.gms.wearable;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Class(creator = "PutDataRequestCreator")
@Reserved({1})
@VisibleForTesting
public class PutDataRequest extends AbstractSafeParcelable {
    public static final Creator<PutDataRequest> CREATOR = new zzh();
    public static final String WEAR_URI_SCHEME = "wear";
    private static final long zzt = TimeUnit.MINUTES.toMillis(30);
    private static final Random zzu = new SecureRandom();
    @Field(getter = "getData", id = 5)
    private byte[] data;
    @Field(getter = "getUri", id = 2)
    private final Uri uri;
    @Field(getter = "getAssetsInternal", id = 4)
    private final Bundle zzv;
    @Field(getter = "getSyncDeadline", id = 6)
    private long zzw;

    private PutDataRequest(Uri uri) {
        this(uri, new Bundle(), null, zzt);
    }

    @Constructor
    PutDataRequest(@Param(id = 2) Uri uri, @Param(id = 4) Bundle bundle, @Param(id = 5) byte[] bArr, @Param(id = 6) long j) {
        this.uri = uri;
        this.zzv = bundle;
        this.zzv.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        this.data = bArr;
        this.zzw = j;
    }

    public static PutDataRequest create(String str) {
        Asserts.checkNotNull(str, "path must not be null");
        return zza(zza(str));
    }

    public static PutDataRequest createFromDataItem(DataItem dataItem) {
        Asserts.checkNotNull(dataItem, "source must not be null");
        PutDataRequest zza = zza(dataItem.getUri());
        for (Entry entry : dataItem.getAssets().entrySet()) {
            if (((DataItemAsset) entry.getValue()).getId() == null) {
                String str = "Cannot create an asset for a put request without a digest: ";
                String valueOf = String.valueOf((String) entry.getKey());
                throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
            zza.putAsset((String) entry.getKey(), Asset.createFromRef(((DataItemAsset) entry.getValue()).getId()));
        }
        zza.setData(dataItem.getData());
        return zza;
    }

    public static PutDataRequest createWithAutoAppendedId(String str) {
        Asserts.checkNotNull(str, "pathPrefix must not be null");
        StringBuilder stringBuilder = new StringBuilder(str);
        if (!str.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append("PN").append(zzu.nextLong());
        return new PutDataRequest(zza(stringBuilder.toString()));
    }

    private static Uri zza(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("An empty path was supplied.");
        } else if (!str.startsWith("/")) {
            throw new IllegalArgumentException("A path must start with a single / .");
        } else if (!str.startsWith("//")) {
            return new Builder().scheme(WEAR_URI_SCHEME).path(str).build();
        } else {
            throw new IllegalArgumentException("A path must start with a single / .");
        }
    }

    public static PutDataRequest zza(Uri uri) {
        Asserts.checkNotNull(uri, "uri must not be null");
        return new PutDataRequest(uri);
    }

    @VisibleForTesting
    public Asset getAsset(String str) {
        Asserts.checkNotNull(str, "key must not be null");
        return (Asset) this.zzv.getParcelable(str);
    }

    public Map<String, Asset> getAssets() {
        Map hashMap = new HashMap();
        for (String str : this.zzv.keySet()) {
            hashMap.put(str, (Asset) this.zzv.getParcelable(str));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    @VisibleForTesting
    public byte[] getData() {
        return this.data;
    }

    public Uri getUri() {
        return this.uri;
    }

    public boolean hasAsset(String str) {
        Asserts.checkNotNull(str, "key must not be null");
        return this.zzv.containsKey(str);
    }

    public boolean isUrgent() {
        return this.zzw == 0;
    }

    public PutDataRequest putAsset(String str, Asset asset) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(asset);
        this.zzv.putParcelable(str, asset);
        return this;
    }

    public PutDataRequest removeAsset(String str) {
        Asserts.checkNotNull(str, "key must not be null");
        this.zzv.remove(str);
        return this;
    }

    public PutDataRequest setData(byte[] bArr) {
        this.data = bArr;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.zzw = 0;
        return this;
    }

    public String toString() {
        return toString(Log.isLoggable(DataMap.TAG, 3));
    }

    public String toString(boolean z) {
        StringBuilder stringBuilder = new StringBuilder("PutDataRequest[");
        String valueOf = String.valueOf(this.data == null ? "null" : Integer.valueOf(this.data.length));
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 7).append("dataSz=").append(valueOf).toString());
        stringBuilder.append(", numAssets=" + this.zzv.size());
        valueOf = String.valueOf(this.uri);
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 6).append(", uri=").append(valueOf).toString());
        stringBuilder.append(", syncDeadline=" + this.zzw);
        if (z) {
            stringBuilder.append("]\n  assets: ");
            for (String valueOf2 : this.zzv.keySet()) {
                String valueOf3 = String.valueOf(this.zzv.getParcelable(valueOf2));
                stringBuilder.append(new StringBuilder((String.valueOf(valueOf2).length() + 7) + String.valueOf(valueOf3).length()).append("\n    ").append(valueOf2).append(": ").append(valueOf3).toString());
            }
            stringBuilder.append("\n  ]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        Asserts.checkNotNull(parcel, "dest must not be null");
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getUri(), i, false);
        SafeParcelWriter.writeBundle(parcel, 4, this.zzv, false);
        SafeParcelWriter.writeByteArray(parcel, 5, getData(), false);
        SafeParcelWriter.writeLong(parcel, 6, this.zzw);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
