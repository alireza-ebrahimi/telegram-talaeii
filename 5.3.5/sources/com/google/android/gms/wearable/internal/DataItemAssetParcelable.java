package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.DataItemAsset;

@KeepName
public class DataItemAssetParcelable extends zzbgl implements ReflectedParcelable, DataItemAsset {
    public static final Creator<DataItemAssetParcelable> CREATOR = new zzda();
    private final String zzbkr;
    private final String zzbzd;

    @Hide
    public DataItemAssetParcelable(DataItemAsset dataItemAsset) {
        this.zzbzd = (String) zzbq.checkNotNull(dataItemAsset.getId());
        this.zzbkr = (String) zzbq.checkNotNull(dataItemAsset.getDataItemKey());
    }

    @Hide
    DataItemAssetParcelable(String str, String str2) {
        this.zzbzd = str;
        this.zzbkr = str2;
    }

    public /* bridge */ /* synthetic */ Object freeze() {
        if (this != null) {
            return this;
        }
        throw null;
    }

    public String getDataItemKey() {
        return this.zzbkr;
    }

    @Hide
    public String getId() {
        return this.zzbzd;
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetParcelable[");
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

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, getId(), false);
        zzbgo.zza(parcel, 3, getDataItemKey(), false);
        zzbgo.zzai(parcel, zze);
    }
}
