package com.google.android.gms.wearable;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.internal.DataItemAssetParcelable;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PutDataRequest extends zzbgl {
    public static final Creator<PutDataRequest> CREATOR = new zzh();
    public static final String WEAR_URI_SCHEME = "wear";
    private static final long zzlqx = TimeUnit.MINUTES.toMillis(30);
    private static final Random zzlqy = new SecureRandom();
    private final Uri mUri;
    private byte[] zzigl;
    private final Bundle zzlqz;
    private long zzlra;

    private PutDataRequest(Uri uri) {
        this(uri, new Bundle(), null, zzlqx);
    }

    @Hide
    PutDataRequest(Uri uri, Bundle bundle, byte[] bArr, long j) {
        this.mUri = uri;
        this.zzlqz = bundle;
        this.zzlqz.setClassLoader(DataItemAssetParcelable.class.getClassLoader());
        this.zzigl = bArr;
        this.zzlra = j;
    }

    public static PutDataRequest create(@NonNull String str) {
        zzc.zzb(str, "path must not be null");
        return zzs(zzoc(str));
    }

    public static PutDataRequest createFromDataItem(@NonNull DataItem dataItem) {
        zzc.zzb(dataItem, "source must not be null");
        PutDataRequest zzs = zzs(dataItem.getUri());
        for (Entry entry : dataItem.getAssets().entrySet()) {
            if (((DataItemAsset) entry.getValue()).getId() == null) {
                String str = "Cannot create an asset for a put request without a digest: ";
                String valueOf = String.valueOf((String) entry.getKey());
                throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
            zzs.putAsset((String) entry.getKey(), Asset.createFromRef(((DataItemAsset) entry.getValue()).getId()));
        }
        zzs.setData(dataItem.getData());
        return zzs;
    }

    public static PutDataRequest createWithAutoAppendedId(@NonNull String str) {
        zzc.zzb(str, "pathPrefix must not be null");
        StringBuilder stringBuilder = new StringBuilder(str);
        if (!str.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append("PN").append(zzlqy.nextLong());
        return new PutDataRequest(zzoc(stringBuilder.toString()));
    }

    @Hide
    private static Uri zzoc(String str) {
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

    @Hide
    public static PutDataRequest zzs(@NonNull Uri uri) {
        zzc.zzb(uri, "uri must not be null");
        return new PutDataRequest(uri);
    }

    public Asset getAsset(@NonNull String str) {
        zzc.zzb(str, "key must not be null");
        return (Asset) this.zzlqz.getParcelable(str);
    }

    public Map<String, Asset> getAssets() {
        Map hashMap = new HashMap();
        for (String str : this.zzlqz.keySet()) {
            hashMap.put(str, (Asset) this.zzlqz.getParcelable(str));
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public byte[] getData() {
        return this.zzigl;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean hasAsset(@NonNull String str) {
        zzc.zzb(str, "key must not be null");
        return this.zzlqz.containsKey(str);
    }

    public boolean isUrgent() {
        return this.zzlra == 0;
    }

    public PutDataRequest putAsset(@NonNull String str, @NonNull Asset asset) {
        zzbq.checkNotNull(str);
        zzbq.checkNotNull(asset);
        this.zzlqz.putParcelable(str, asset);
        return this;
    }

    public PutDataRequest removeAsset(@NonNull String str) {
        zzc.zzb(str, "key must not be null");
        this.zzlqz.remove(str);
        return this;
    }

    public PutDataRequest setData(byte[] bArr) {
        this.zzigl = bArr;
        return this;
    }

    public PutDataRequest setUrgent() {
        this.zzlra = 0;
        return this;
    }

    public String toString() {
        return toString(Log.isLoggable(DataMap.TAG, 3));
    }

    public String toString(boolean z) {
        StringBuilder stringBuilder = new StringBuilder("PutDataRequest[");
        String valueOf = String.valueOf(this.zzigl == null ? "null" : Integer.valueOf(this.zzigl.length));
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 7).append("dataSz=").append(valueOf).toString());
        stringBuilder.append(", numAssets=" + this.zzlqz.size());
        valueOf = String.valueOf(this.mUri);
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 6).append(", uri=").append(valueOf).toString());
        stringBuilder.append(", syncDeadline=" + this.zzlra);
        if (z) {
            stringBuilder.append("]\n  assets: ");
            for (String valueOf2 : this.zzlqz.keySet()) {
                String valueOf3 = String.valueOf(this.zzlqz.getParcelable(valueOf2));
                stringBuilder.append(new StringBuilder((String.valueOf(valueOf2).length() + 7) + String.valueOf(valueOf3).length()).append("\n    ").append(valueOf2).append(": ").append(valueOf3).toString());
            }
            stringBuilder.append("\n  ]");
            return stringBuilder.toString();
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        zzc.zzb(parcel, "dest must not be null");
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getUri(), i, false);
        zzbgo.zza(parcel, 4, this.zzlqz, false);
        zzbgo.zza(parcel, 5, getData(), false);
        zzbgo.zza(parcel, 6, this.zzlra);
        zzbgo.zzai(parcel, zze);
    }
}
