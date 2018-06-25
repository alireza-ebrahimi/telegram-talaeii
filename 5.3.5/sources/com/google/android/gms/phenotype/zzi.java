package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;
import java.util.Comparator;

public final class zzi extends zzbgl implements Comparable<zzi> {
    public static final Creator<zzi> CREATOR = new zzk();
    private static Comparator<zzi> zzkgq = new zzj();
    public final String name;
    private String zzgim;
    private boolean zzili;
    private double zzilk;
    private long zzkgm;
    private byte[] zzkgn;
    private int zzkgo;
    public final int zzkgp;

    public zzi(String str, long j, boolean z, double d, String str2, byte[] bArr, int i, int i2) {
        this.name = str;
        this.zzkgm = j;
        this.zzili = z;
        this.zzilk = d;
        this.zzgim = str2;
        this.zzkgn = bArr;
        this.zzkgo = i;
        this.zzkgp = i2;
    }

    private static int compare(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        int i = 0;
        zzi zzi = (zzi) obj;
        int compareTo = this.name.compareTo(zzi.name);
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = compare(this.zzkgo, zzi.zzkgo);
        if (compareTo != 0) {
            return compareTo;
        }
        switch (this.zzkgo) {
            case 1:
                long j = this.zzkgm;
                long j2 = zzi.zzkgm;
                return j < j2 ? -1 : j != j2 ? 1 : 0;
            case 2:
                boolean z = this.zzili;
                return z != zzi.zzili ? z ? 1 : -1 : 0;
            case 3:
                return Double.compare(this.zzilk, zzi.zzilk);
            case 4:
                String str = this.zzgim;
                String str2 = zzi.zzgim;
                return str != str2 ? str == null ? -1 : str2 == null ? 1 : str.compareTo(str2) : 0;
            case 5:
                if (this.zzkgn == zzi.zzkgn) {
                    return 0;
                }
                if (this.zzkgn == null) {
                    return -1;
                }
                if (zzi.zzkgn == null) {
                    return 1;
                }
                while (i < Math.min(this.zzkgn.length, zzi.zzkgn.length)) {
                    compareTo = this.zzkgn[i] - zzi.zzkgn[i];
                    if (compareTo != 0) {
                        return compareTo;
                    }
                    i++;
                }
                return compare(this.zzkgn.length, zzi.zzkgn.length);
            default:
                throw new AssertionError("Invalid enum value: " + this.zzkgo);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (!zzn.equals(this.name, zzi.name) || this.zzkgo != zzi.zzkgo || this.zzkgp != zzi.zzkgp) {
            return false;
        }
        switch (this.zzkgo) {
            case 1:
                return this.zzkgm == zzi.zzkgm;
            case 2:
                return this.zzili == zzi.zzili;
            case 3:
                return this.zzilk == zzi.zzilk;
            case 4:
                return zzn.equals(this.zzgim, zzi.zzgim);
            case 5:
                return Arrays.equals(this.zzkgn, zzi.zzkgn);
            default:
                throw new AssertionError("Invalid enum value: " + this.zzkgo);
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flag(");
        stringBuilder.append(this.name);
        stringBuilder.append(", ");
        switch (this.zzkgo) {
            case 1:
                stringBuilder.append(this.zzkgm);
                break;
            case 2:
                stringBuilder.append(this.zzili);
                break;
            case 3:
                stringBuilder.append(this.zzilk);
                break;
            case 4:
                stringBuilder.append("'");
                stringBuilder.append(this.zzgim);
                stringBuilder.append("'");
                break;
            case 5:
                if (this.zzkgn != null) {
                    stringBuilder.append("'");
                    stringBuilder.append(Base64.encodeToString(this.zzkgn, 3));
                    stringBuilder.append("'");
                    break;
                }
                stringBuilder.append("null");
                break;
            default:
                String str = this.name;
                throw new AssertionError(new StringBuilder(String.valueOf(str).length() + 27).append("Invalid type: ").append(str).append(", ").append(this.zzkgo).toString());
        }
        stringBuilder.append(", ");
        stringBuilder.append(this.zzkgo);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzkgp);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.name, false);
        zzbgo.zza(parcel, 3, this.zzkgm);
        zzbgo.zza(parcel, 4, this.zzili);
        zzbgo.zza(parcel, 5, this.zzilk);
        zzbgo.zza(parcel, 6, this.zzgim, false);
        zzbgo.zza(parcel, 7, this.zzkgn, false);
        zzbgo.zzc(parcel, 8, this.zzkgo);
        zzbgo.zzc(parcel, 9, this.zzkgp);
        zzbgo.zzai(parcel, zze);
    }
}
