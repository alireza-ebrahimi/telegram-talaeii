package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternItem extends zzbgl {
    @Hide
    public static final Creator<PatternItem> CREATOR = new zzi();
    private static final String TAG = PatternItem.class.getSimpleName();
    private final int type;
    @Nullable
    private final Float zzjec;

    @Hide
    public PatternItem(int i, @Nullable Float f) {
        boolean z = true;
        if (i != 1 && (f == null || f.floatValue() < 0.0f)) {
            z = false;
        }
        String valueOf = String.valueOf(f);
        zzbq.checkArgument(z, new StringBuilder(String.valueOf(valueOf).length() + 45).append("Invalid PatternItem: type=").append(i).append(" length=").append(valueOf).toString());
        this.type = i;
        this.zzjec = f;
    }

    @Nullable
    @Hide
    static List<PatternItem> zzah(@Nullable List<PatternItem> list) {
        if (list == null) {
            return null;
        }
        List<PatternItem> arrayList = new ArrayList(list.size());
        for (Object obj : list) {
            Object obj2;
            if (obj2 != null) {
                switch (obj2.type) {
                    case 0:
                        Dash dash = new Dash(obj2.zzjec.floatValue());
                        break;
                    case 1:
                        obj2 = new Dot();
                        break;
                    case 2:
                        Gap gap = new Gap(obj2.zzjec.floatValue());
                        break;
                    default:
                        Log.w(TAG, "Unknown PatternItem type: " + obj2.type);
                        break;
                }
            }
            obj2 = null;
            arrayList.add(obj2);
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatternItem)) {
            return false;
        }
        PatternItem patternItem = (PatternItem) obj;
        return this.type == patternItem.type && zzbg.equal(this.zzjec, patternItem.zzjec);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.type), this.zzjec});
    }

    public String toString() {
        int i = this.type;
        String valueOf = String.valueOf(this.zzjec);
        return new StringBuilder(String.valueOf(valueOf).length() + 39).append("[PatternItem: type=").append(i).append(" length=").append(valueOf).append("]").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.type);
        zzbgo.zza(parcel, 3, this.zzjec, false);
        zzbgo.zzai(parcel, zze);
    }
}
