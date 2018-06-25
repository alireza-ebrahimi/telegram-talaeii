package com.google.android.gms.wearable;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzdng;
import com.google.android.gms.internal.zzdnh;
import com.google.android.gms.internal.zzdni;
import com.google.android.gms.internal.zzflr;
import java.util.ArrayList;
import java.util.List;

public class DataMapItem {
    private final Uri mUri;
    private final DataMap zzlqv;

    private DataMapItem(DataItem dataItem) {
        this.mUri = dataItem.getUri();
        this.zzlqv = zza((DataItem) dataItem.freeze());
    }

    public static DataMapItem fromDataItem(@NonNull DataItem dataItem) {
        zzc.zzb(dataItem, "dataItem must not be null");
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
                return zzdng.zza(new zzdnh(zzdni.zzad(dataItem.getData()), arrayList));
            } catch (zzflr e2) {
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
        return this.zzlqv;
    }

    public Uri getUri() {
        return this.mUri;
    }
}
