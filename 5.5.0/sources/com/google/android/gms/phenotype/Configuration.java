package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@KeepForSdk
@Class(creator = "ConfigurationCreator")
@Reserved({1})
public class Configuration extends AbstractSafeParcelable implements Comparable<Configuration> {
    @KeepForSdk
    public static final Creator<Configuration> CREATOR = new zzc();
    @Field(id = 2)
    private final int zzc;
    @Field(id = 3)
    private final zzi[] zzd;
    @Field(id = 4)
    private final String[] zze;
    private final Map<String, zzi> zzf = new TreeMap();

    @Constructor
    public Configuration(@Param(id = 2) int i, @Param(id = 3) zzi[] zziArr, @Param(id = 4) String[] strArr) {
        this.zzc = i;
        this.zzd = zziArr;
        for (zzi zzi : zziArr) {
            this.zzf.put(zzi.name, zzi);
        }
        this.zze = strArr;
        if (this.zze != null) {
            Arrays.sort(this.zze);
        }
    }

    public /* synthetic */ int compareTo(Object obj) {
        return this.zzc - ((Configuration) obj).zzc;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Configuration)) {
            return false;
        }
        Configuration configuration = (Configuration) obj;
        return this.zzc == configuration.zzc && zzn.equals(this.zzf, configuration.zzf) && Arrays.equals(this.zze, configuration.zze);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Configuration(");
        stringBuilder.append(this.zzc);
        stringBuilder.append(", ");
        stringBuilder.append("(");
        for (zzi append : this.zzf.values()) {
            stringBuilder.append(append);
            stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(", ");
        stringBuilder.append("(");
        if (this.zze != null) {
            for (String append2 : this.zze) {
                stringBuilder.append(append2);
                stringBuilder.append(", ");
            }
        } else {
            stringBuilder.append("null");
        }
        stringBuilder.append(")");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzc);
        SafeParcelWriter.writeTypedArray(parcel, 3, this.zzd, i, false);
        SafeParcelWriter.writeStringArray(parcel, 4, this.zze, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
