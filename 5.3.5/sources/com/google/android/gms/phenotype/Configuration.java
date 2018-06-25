package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

@KeepForSdk
public class Configuration extends zzbgl implements Comparable<Configuration> {
    @KeepForSdk
    public static final Creator<Configuration> CREATOR = new zzc();
    private Map<String, zzi> zzfqk = new TreeMap();
    private int zzkfq;
    private zzi[] zzkfr;
    private String[] zzkfs;

    public Configuration(int i, zzi[] zziArr, String[] strArr) {
        this.zzkfq = i;
        this.zzkfr = zziArr;
        for (zzi zzi : zziArr) {
            this.zzfqk.put(zzi.name, zzi);
        }
        this.zzkfs = strArr;
        if (this.zzkfs != null) {
            Arrays.sort(this.zzkfs);
        }
    }

    public /* synthetic */ int compareTo(Object obj) {
        return this.zzkfq - ((Configuration) obj).zzkfq;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Configuration)) {
            return false;
        }
        Configuration configuration = (Configuration) obj;
        return this.zzkfq == configuration.zzkfq && zzn.equals(this.zzfqk, configuration.zzfqk) && Arrays.equals(this.zzkfs, configuration.zzkfs);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Configuration(");
        stringBuilder.append(this.zzkfq);
        stringBuilder.append(", ");
        stringBuilder.append("(");
        for (zzi append : this.zzfqk.values()) {
            stringBuilder.append(append);
            stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(", ");
        stringBuilder.append("(");
        if (this.zzkfs != null) {
            for (String append2 : this.zzkfs) {
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
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.zzkfq);
        zzbgo.zza(parcel, 3, this.zzkfr, i, false);
        zzbgo.zza(parcel, 4, this.zzkfs, false);
        zzbgo.zzai(parcel, zze);
    }
}
