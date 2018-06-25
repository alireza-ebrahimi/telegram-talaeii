package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;

@Hide
public final class zzbw implements DataApi {
    private static PendingResult<Status> zza(GoogleApiClient googleApiClient, DataListener dataListener, IntentFilter[] intentFilterArr) {
        return zzb.zza(googleApiClient, new zzce(intentFilterArr), dataListener);
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, DataListener dataListener) {
        return zza(googleApiClient, dataListener, new IntentFilter[]{zzgj.zzoe("com.google.android.gms.wearable.DATA_CHANGED")});
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, DataListener dataListener, Uri uri, int i) {
        zzc.zzb(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        zzbq.checkArgument(z, "invalid filter type");
        return zza(googleApiClient, dataListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.DATA_CHANGED", uri, i)});
    }

    public final PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, Uri uri) {
        return deleteDataItems(googleApiClient, uri, 0);
    }

    public final PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient googleApiClient, Uri uri, int i) {
        boolean z = true;
        zzc.zzb(uri, "uri must not be null");
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbq.checkArgument(z, "invalid filter type");
        return googleApiClient.zzd(new zzcb(this, googleApiClient, uri, i));
    }

    public final PendingResult<DataItemResult> getDataItem(GoogleApiClient googleApiClient, Uri uri) {
        return googleApiClient.zzd(new zzby(this, googleApiClient, uri));
    }

    public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient) {
        return googleApiClient.zzd(new zzbz(this, googleApiClient));
    }

    public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient, Uri uri) {
        return getDataItems(googleApiClient, uri, 0);
    }

    public final PendingResult<DataItemBuffer> getDataItems(GoogleApiClient googleApiClient, Uri uri, int i) {
        boolean z = true;
        zzc.zzb(uri, "uri must not be null");
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbq.checkArgument(z, "invalid filter type");
        return googleApiClient.zzd(new zzca(this, googleApiClient, uri, i));
    }

    public final PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient googleApiClient, Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        } else if (asset.getDigest() == null) {
            throw new IllegalArgumentException("invalid asset");
        } else if (asset.getData() == null) {
            return googleApiClient.zzd(new zzcc(this, googleApiClient, asset));
        } else {
            throw new IllegalArgumentException("invalid asset");
        }
    }

    public final PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient googleApiClient, DataItemAsset dataItemAsset) {
        return googleApiClient.zzd(new zzcd(this, googleApiClient, dataItemAsset));
    }

    public final PendingResult<DataItemResult> putDataItem(GoogleApiClient googleApiClient, PutDataRequest putDataRequest) {
        return googleApiClient.zzd(new zzbx(this, googleApiClient, putDataRequest));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, DataListener dataListener) {
        return googleApiClient.zzd(new zzcf(this, googleApiClient, dataListener));
    }
}
