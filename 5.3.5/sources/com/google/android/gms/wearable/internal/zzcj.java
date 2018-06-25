package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataClient.GetFdForAssetResponse;
import com.google.android.gms.wearable.DataClient.OnDataChangedListener;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

public final class zzcj extends DataClient {
    private final DataApi zzluc = new zzbw();

    public zzcj(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, zza);
    }

    public zzcj(@NonNull Context context, @NonNull zza zza) {
        super(context, zza);
    }

    private final Task<Void> zza(OnDataChangedListener onDataChangedListener, IntentFilter[] intentFilterArr) {
        zzci zzb = zzcm.zzb(onDataChangedListener, getLooper(), "DataListener");
        return zza(new zzcv(onDataChangedListener, intentFilterArr, zzb, null), new zzcw(onDataChangedListener, zzb.zzakx(), null));
    }

    public final Task<Void> addListener(@NonNull OnDataChangedListener onDataChangedListener) {
        return zza(onDataChangedListener, new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.DATA_CHANGED")});
    }

    public final Task<Void> addListener(@NonNull OnDataChangedListener onDataChangedListener, @NonNull Uri uri, int i) {
        zzc.zzb(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        zzbq.checkArgument(z, "invalid filter type");
        return zza(onDataChangedListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.DATA_CHANGED", uri, i)});
    }

    public final Task<Integer> deleteDataItems(@NonNull Uri uri) {
        return zzbj.zza(this.zzluc.deleteDataItems(zzahw(), uri), zzcp.zzgui);
    }

    public final Task<Integer> deleteDataItems(@NonNull Uri uri, int i) {
        return zzbj.zza(this.zzluc.deleteDataItems(zzahw(), uri, i), zzcq.zzgui);
    }

    public final Task<DataItem> getDataItem(@NonNull Uri uri) {
        return zzbj.zza(this.zzluc.getDataItem(zzahw(), uri), zzcl.zzgui);
    }

    public final Task<DataItemBuffer> getDataItems() {
        return zzbj.zza(this.zzluc.getDataItems(zzahw()), zzcm.zzgui);
    }

    public final Task<DataItemBuffer> getDataItems(@NonNull Uri uri) {
        return zzbj.zza(this.zzluc.getDataItems(zzahw(), uri), zzcn.zzgui);
    }

    public final Task<DataItemBuffer> getDataItems(@NonNull Uri uri, int i) {
        return zzbj.zza(this.zzluc.getDataItems(zzahw(), uri, i), zzco.zzgui);
    }

    public final Task<GetFdForAssetResponse> getFdForAsset(@NonNull Asset asset) {
        return zzbj.zza(this.zzluc.getFdForAsset(zzahw(), asset), zzcr.zzgui);
    }

    public final Task<GetFdForAssetResponse> getFdForAsset(@NonNull DataItemAsset dataItemAsset) {
        return zzbj.zza(this.zzluc.getFdForAsset(zzahw(), dataItemAsset), zzcs.zzgui);
    }

    public final Task<DataItem> putDataItem(@NonNull PutDataRequest putDataRequest) {
        return zzbj.zza(this.zzluc.putDataItem(zzahw(), putDataRequest), zzck.zzgui);
    }

    public final Task<Boolean> removeListener(@NonNull OnDataChangedListener onDataChangedListener) {
        return zza(zzcm.zzb(onDataChangedListener, getLooper(), "DataListener").zzakx());
    }
}
