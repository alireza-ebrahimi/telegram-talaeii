package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.wearable.DataItemAsset;

@Hide
public final class zzcz implements DataItemAsset {
    private final String zzbkr;
    private final String zzbzd;

    public zzcz(DataItemAsset dataItemAsset) {
        this.zzbzd = dataItemAsset.getId();
        this.zzbkr = dataItemAsset.getDataItemKey();
    }

    public final /* bridge */ /* synthetic */ Object freeze() {
        if (this != null) {
            return this;
        }
        throw null;
    }

    public final String getDataItemKey() {
        return this.zzbkr;
    }

    @Hide
    public final String getId() {
        return this.zzbzd;
    }

    public final boolean isDataValid() {
        return true;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(hashCode()));
        if (this.zzbzd == null) {
            stringBuilder.append(",noid");
        } else {
            stringBuilder.append(",");
            stringBuilder.append(this.zzbzd);
        }
        stringBuilder.append(", key=");
        stringBuilder.append(this.zzbkr);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
