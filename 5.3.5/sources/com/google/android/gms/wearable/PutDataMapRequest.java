package com.google.android.gms.wearable;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzdng;
import com.google.android.gms.internal.zzdnh;
import com.google.android.gms.internal.zzfls;

public class PutDataMapRequest {
    private final DataMap zzlqv = new DataMap();
    private final PutDataRequest zzlqw;

    private PutDataMapRequest(PutDataRequest putDataRequest, DataMap dataMap) {
        this.zzlqw = putDataRequest;
        if (dataMap != null) {
            this.zzlqv.putAll(dataMap);
        }
    }

    public static PutDataMapRequest create(@NonNull String str) {
        zzc.zzb(str, "path must not be null");
        return new PutDataMapRequest(PutDataRequest.create(str), null);
    }

    public static PutDataMapRequest createFromDataMapItem(@NonNull DataMapItem dataMapItem) {
        zzc.zzb(dataMapItem, "source must not be null");
        return new PutDataMapRequest(PutDataRequest.zzs(dataMapItem.getUri()), dataMapItem.getDataMap());
    }

    public static PutDataMapRequest createWithAutoAppendedId(@NonNull String str) {
        zzc.zzb(str, "pathPrefix must not be null");
        return new PutDataMapRequest(PutDataRequest.createWithAutoAppendedId(str), null);
    }

    public PutDataRequest asPutDataRequest() {
        zzdnh zza = zzdng.zza(this.zzlqv);
        this.zzlqw.setData(zzfls.zzc(zza.zzlwk));
        int size = zza.zzlwl.size();
        int i = 0;
        while (i < size) {
            String num = Integer.toString(i);
            Asset asset = (Asset) zza.zzlwl.get(i);
            String valueOf;
            if (num == null) {
                valueOf = String.valueOf(asset);
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 26).append("asset key cannot be null: ").append(valueOf).toString());
            } else if (asset == null) {
                String str = "asset cannot be null: key=";
                valueOf = String.valueOf(num);
                throw new IllegalStateException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            } else {
                if (Log.isLoggable(DataMap.TAG, 3)) {
                    String str2 = DataMap.TAG;
                    String valueOf2 = String.valueOf(asset);
                    Log.d(str2, new StringBuilder((String.valueOf(num).length() + 33) + String.valueOf(valueOf2).length()).append("asPutDataRequest: adding asset: ").append(num).append(" ").append(valueOf2).toString());
                }
                this.zzlqw.putAsset(num, asset);
                i++;
            }
        }
        return this.zzlqw;
    }

    public DataMap getDataMap() {
        return this.zzlqv;
    }

    public Uri getUri() {
        return this.zzlqw.getUri();
    }

    public boolean isUrgent() {
        return this.zzlqw.isUrgent();
    }

    public PutDataMapRequest setUrgent() {
        this.zzlqw.setUrgent();
        return this;
    }
}
