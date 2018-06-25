package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class ExperimentTokens extends zzbgl {
    @KeepForSdk
    public static final Creator<ExperimentTokens> CREATOR = new zzh();
    private static byte[][] zzfpb = new byte[0][];
    private static ExperimentTokens zzkfz = new ExperimentTokens("", null, zzfpb, zzfpb, zzfpb, zzfpb, null, null);
    private static final zza zzkgi = new zzd();
    private static final zza zzkgj = new zze();
    private static final zza zzkgk = new zzf();
    private static final zza zzkgl = new zzg();
    private String zzkga;
    private byte[] zzkgb;
    private byte[][] zzkgc;
    private byte[][] zzkgd;
    private byte[][] zzkge;
    private byte[][] zzkgf;
    private int[] zzkgg;
    private byte[][] zzkgh;

    interface zza {
    }

    public ExperimentTokens(String str, byte[] bArr, byte[][] bArr2, byte[][] bArr3, byte[][] bArr4, byte[][] bArr5, int[] iArr, byte[][] bArr6) {
        this.zzkga = str;
        this.zzkgb = bArr;
        this.zzkgc = bArr2;
        this.zzkgd = bArr3;
        this.zzkge = bArr4;
        this.zzkgf = bArr5;
        this.zzkgg = iArr;
        this.zzkgh = bArr6;
    }

    private static void zza(StringBuilder stringBuilder, String str, int[] iArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (iArr == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("(");
        int length = iArr.length;
        Object obj = 1;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            if (obj == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(i2);
            i++;
            obj = null;
        }
        stringBuilder.append(")");
    }

    private static void zza(StringBuilder stringBuilder, String str, byte[][] bArr) {
        stringBuilder.append(str);
        stringBuilder.append("=");
        if (bArr == null) {
            stringBuilder.append("null");
            return;
        }
        stringBuilder.append("(");
        int length = bArr.length;
        Object obj = 1;
        int i = 0;
        while (i < length) {
            byte[] bArr2 = bArr[i];
            if (obj == null) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("'");
            stringBuilder.append(Base64.encodeToString(bArr2, 3));
            stringBuilder.append("'");
            i++;
            obj = null;
        }
        stringBuilder.append(")");
    }

    private static List<String> zzb(byte[][] bArr) {
        if (bArr == null) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList(bArr.length);
        for (byte[] encodeToString : bArr) {
            arrayList.add(Base64.encodeToString(encodeToString, 3));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> zzd(int[] iArr) {
        if (iArr == null) {
            return Collections.emptyList();
        }
        List<Integer> arrayList = new ArrayList(iArr.length);
        for (int valueOf : iArr) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExperimentTokens)) {
            return false;
        }
        ExperimentTokens experimentTokens = (ExperimentTokens) obj;
        return zzn.equals(this.zzkga, experimentTokens.zzkga) && Arrays.equals(this.zzkgb, experimentTokens.zzkgb) && zzn.equals(zzb(this.zzkgc), zzb(experimentTokens.zzkgc)) && zzn.equals(zzb(this.zzkgd), zzb(experimentTokens.zzkgd)) && zzn.equals(zzb(this.zzkge), zzb(experimentTokens.zzkge)) && zzn.equals(zzb(this.zzkgf), zzb(experimentTokens.zzkgf)) && zzn.equals(zzd(this.zzkgg), zzd(experimentTokens.zzkgg)) && zzn.equals(zzb(this.zzkgh), zzb(experimentTokens.zzkgh));
    }

    public String toString() {
        String str;
        StringBuilder stringBuilder = new StringBuilder("ExperimentTokens");
        stringBuilder.append("(");
        if (this.zzkga == null) {
            str = "null";
        } else {
            str = this.zzkga;
            str = new StringBuilder(String.valueOf(str).length() + 2).append("'").append(str).append("'").toString();
        }
        stringBuilder.append(str);
        stringBuilder.append(", ");
        byte[] bArr = this.zzkgb;
        stringBuilder.append("direct");
        stringBuilder.append("=");
        if (bArr == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append("'");
            stringBuilder.append(Base64.encodeToString(bArr, 3));
            stringBuilder.append("'");
        }
        stringBuilder.append(", ");
        zza(stringBuilder, "GAIA", this.zzkgc);
        stringBuilder.append(", ");
        zza(stringBuilder, "PSEUDO", this.zzkgd);
        stringBuilder.append(", ");
        zza(stringBuilder, "ALWAYS", this.zzkge);
        stringBuilder.append(", ");
        zza(stringBuilder, "OTHER", this.zzkgf);
        stringBuilder.append(", ");
        zza(stringBuilder, "weak", this.zzkgg);
        stringBuilder.append(", ");
        zza(stringBuilder, "directs", this.zzkgh);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzkga, false);
        zzbgo.zza(parcel, 3, this.zzkgb, false);
        zzbgo.zza(parcel, 4, this.zzkgc, false);
        zzbgo.zza(parcel, 5, this.zzkgd, false);
        zzbgo.zza(parcel, 6, this.zzkge, false);
        zzbgo.zza(parcel, 7, this.zzkgf, false);
        zzbgo.zza(parcel, 8, this.zzkgg, false);
        zzbgo.zza(parcel, 9, this.zzkgh, false);
        zzbgo.zzai(parcel, zze);
    }
}
