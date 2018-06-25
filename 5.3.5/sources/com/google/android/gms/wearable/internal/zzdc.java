package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Hide
public final class zzdc implements DataItem {
    private Uri mUri;
    private byte[] zzigl;
    private Map<String, DataItemAsset> zzluf;

    public zzdc(DataItem dataItem) {
        this.mUri = dataItem.getUri();
        this.zzigl = dataItem.getData();
        Map hashMap = new HashMap();
        for (Entry entry : dataItem.getAssets().entrySet()) {
            if (entry.getKey() != null) {
                hashMap.put((String) entry.getKey(), (DataItemAsset) ((DataItemAsset) entry.getValue()).freeze());
            }
        }
        this.zzluf = Collections.unmodifiableMap(hashMap);
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        if (this != null) {
            return this;
        }
        throw null;
    }

    public final Map<String, DataItemAsset> getAssets() {
        return this.zzluf;
    }

    public final byte[] getData() {
        return this.zzigl;
    }

    public final Uri getUri() {
        return this.mUri;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final DataItem setData(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        boolean isLoggable = Log.isLoggable("DataItem", 3);
        StringBuilder stringBuilder = new StringBuilder("DataItemEntity{ ");
        String valueOf = String.valueOf(this.mUri);
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 4).append("uri=").append(valueOf).toString());
        valueOf = String.valueOf(this.zzigl == null ? "null" : Integer.valueOf(this.zzigl.length));
        stringBuilder.append(new StringBuilder(String.valueOf(valueOf).length() + 9).append(", dataSz=").append(valueOf).toString());
        stringBuilder.append(", numAssets=" + this.zzluf.size());
        if (isLoggable && !this.zzluf.isEmpty()) {
            stringBuilder.append(", assets=[");
            String str = "";
            for (Entry entry : this.zzluf.entrySet()) {
                String str2 = (String) entry.getKey();
                valueOf = ((DataItemAsset) entry.getValue()).getId();
                stringBuilder.append(new StringBuilder(((String.valueOf(str).length() + 2) + String.valueOf(str2).length()) + String.valueOf(valueOf).length()).append(str).append(str2).append(": ").append(valueOf).toString());
                str = ", ";
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
