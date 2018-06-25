package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.wearable.zze;
import com.google.android.gms.internal.wearable.zzf;
import com.google.android.gms.internal.wearable.zzg;
import com.google.android.gms.internal.wearable.zzs;
import java.util.ArrayList;
import java.util.List;

@VisibleForTesting
public class DataMapItem {
    private final Uri uri;
    private final DataMap zzr;

    private DataMapItem(DataItem dataItem) {
        this.uri = dataItem.getUri();
        this.zzr = zza((DataItem) dataItem.freeze());
    }

    public static DataMapItem fromDataItem(DataItem dataItem) {
        Asserts.checkNotNull(dataItem, "dataItem must not be null");
        return new DataMapItem(dataItem);
    }

    private static DataMap zza(DataItem dataItem) {
        Throwable e;
        if (dataItem.getData() == null && dataItem.getAssets().size() > 0) {
            throw new IllegalArgumentException("Cannot create DataMapItem from a DataItem  that wasn't made with DataMapItem.");
        } else if (dataItem.getData() == null) {
            return new DataMap();
        } else {
            try {
                List arrayList = new ArrayList();
                int size = dataItem.getAssets().size();
                for (int i = 0; i < size; i++) {
                    DataItemAsset dataItemAsset = (DataItemAsset) dataItem.getAssets().get(Integer.toString(i));
                    if (dataItemAsset == null) {
                        String valueOf = String.valueOf(dataItem);
                        throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 64).append("Cannot find DataItemAsset referenced in data at ").append(i).append(" for ").append(valueOf).toString());
                    }
                    arrayList.add(Asset.createFromRef(dataItemAsset.getId()));
                }
                return zze.zza(new zzf(zzg.zza(dataItem.getData()), arrayList));
            } catch (zzs e2) {
                e = e2;
            } catch (NullPointerException e3) {
                e = e3;
            }
        }
        valueOf = String.valueOf(dataItem.getUri());
        String encodeToString = Base64.encodeToString(dataItem.getData(), 0);
        Log.w("DataItem", new StringBuilder((String.valueOf(valueOf).length() + 50) + String.valueOf(encodeToString).length()).append("Unable to parse datamap from dataItem. uri=").append(valueOf).append(", data=").append(encodeToString).toString());
        encodeToString = String.valueOf(dataItem.getUri());
        throw new IllegalStateException(new StringBuilder(String.valueOf(encodeToString).length() + 44).append("Unable to parse datamap from dataItem.  uri=").append(encodeToString).toString(), e);
    }

    public DataMap getDataMap() {
        return this.zzr;
    }

    public Uri getUri() {
        return this.uri;
    }
}
