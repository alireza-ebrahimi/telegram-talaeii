package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzdf extends DataBufferRef implements DataItem {
    private final int zzdl;

    public zzdf(DataHolder dataHolder, int i, int i2) {
        super(dataHolder, i);
        this.zzdl = i2;
    }

    public final /* synthetic */ Object freeze() {
        return new zzdc(this);
    }

    public final Map<String, DataItemAsset> getAssets() {
        Map<String, DataItemAsset> hashMap = new HashMap(this.zzdl);
        for (int i = 0; i < this.zzdl; i++) {
            zzdb zzdb = new zzdb(this.mDataHolder, this.mDataRow + i);
            if (zzdb.getDataItemKey() != null) {
                hashMap.put(zzdb.getDataItemKey(), zzdb);
            }
        }
        return hashMap;
    }

    public final byte[] getData() {
        return getByteArray(DataBufferSafeParcelable.DATA_FIELD);
    }

    public final Uri getUri() {
        return Uri.parse(getString("path"));
    }

    public final DataItem setData(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        byte[] data = getData();
        Map assets = getAssets();
        StringBuilder stringBuilder = new StringBuilder("DataItemRef{ ");
        String valueOf = String.valueOf(getUri());
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 4).append("uri=").append(valueOf).toString());
        String valueOf2 = String.valueOf(data == null ? "null" : Integer.valueOf(data.length));
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf2).length() + 9).append(", dataSz=").append(valueOf2).toString());
        stringBuilder.append(", numAssets=" + assets.size());
        if (isLoggable && !assets.isEmpty()) {
            stringBuilder.append(", assets=[");
            valueOf2 = TtmlNode.ANONYMOUS_REGION_ID;
            String str = valueOf2;
            for (Entry entry : assets.entrySet()) {
                String str2 = (String) entry.getKey();
                valueOf2 = ((DataItemAsset) entry.getValue()).getId();
                stringBuilder.append(new StringBuilder(((String.valueOf(str).length() + 2) + String.valueOf(str2).length()) + String.valueOf(valueOf2).length()).append(str).append(str2).append(": ").append(valueOf2).toString());
                str = ", ";
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
