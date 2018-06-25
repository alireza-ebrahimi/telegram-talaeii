package com.google.android.gms.wearable;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.Wearable.WearableOptions;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class DataClient extends GoogleApi<WearableOptions> {
    public static final String ACTION_DATA_CHANGED = "com.google.android.gms.wearable.DATA_CHANGED";
    public static final int FILTER_LITERAL = 0;
    public static final int FILTER_PREFIX = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterType {
    }

    public static abstract class GetFdForAssetResponse implements Releasable {
        public abstract ParcelFileDescriptor getFdForAsset();

        public abstract InputStream getInputStream();
    }

    public interface OnDataChangedListener extends DataListener {
        void onDataChanged(@NonNull DataEventBuffer dataEventBuffer);
    }

    @Hide
    public DataClient(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, Wearable.API, null, zza);
    }

    @Hide
    public DataClient(@NonNull Context context, @NonNull zza zza) {
        super(context, Wearable.API, null, zza);
    }

    public abstract Task<Void> addListener(@NonNull OnDataChangedListener onDataChangedListener);

    public abstract Task<Void> addListener(@NonNull OnDataChangedListener onDataChangedListener, @NonNull Uri uri, int i);

    public abstract Task<Integer> deleteDataItems(@NonNull Uri uri);

    public abstract Task<Integer> deleteDataItems(@NonNull Uri uri, int i);

    public abstract Task<DataItem> getDataItem(@NonNull Uri uri);

    public abstract Task<DataItemBuffer> getDataItems();

    public abstract Task<DataItemBuffer> getDataItems(@NonNull Uri uri);

    public abstract Task<DataItemBuffer> getDataItems(@NonNull Uri uri, int i);

    public abstract Task<GetFdForAssetResponse> getFdForAsset(@NonNull Asset asset);

    public abstract Task<GetFdForAssetResponse> getFdForAsset(@NonNull DataItemAsset dataItemAsset);

    public abstract Task<DataItem> putDataItem(@NonNull PutDataRequest putDataRequest);

    public abstract Task<Boolean> removeListener(@NonNull OnDataChangedListener onDataChangedListener);
}
