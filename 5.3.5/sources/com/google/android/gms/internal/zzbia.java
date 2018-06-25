package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.internal.view.SupportMenu;
import android.util.SparseArray;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.common.util.zzr;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzbia extends zzbhs {
    public static final Creator<zzbia> CREATOR = new zzbib();
    private final String mClassName;
    private final int zzehz;
    private final zzbhv zzgiw;
    private final Parcel zzgjd;
    private final int zzgje = 2;
    private int zzgjf;
    private int zzgjg;

    zzbia(int i, Parcel parcel, zzbhv zzbhv) {
        this.zzehz = i;
        this.zzgjd = (Parcel) zzbq.checkNotNull(parcel);
        this.zzgiw = zzbhv;
        if (this.zzgiw == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zzgiw.zzanj();
        }
        this.zzgjf = 2;
    }

    private static void zza(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                stringBuilder.append(obj);
                return;
            case 7:
                stringBuilder.append("\"").append(zzq.zzha(obj.toString())).append("\"");
                return;
            case 8:
                stringBuilder.append("\"").append(zzc.zzj((byte[]) obj)).append("\"");
                return;
            case 9:
                stringBuilder.append("\"").append(zzc.zzk((byte[]) obj));
                stringBuilder.append("\"");
                return;
            case 10:
                zzr.zza(stringBuilder, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private final void zza(StringBuilder stringBuilder, zzbhq<?, ?> zzbhq, Parcel parcel, int i) {
        double[] dArr = null;
        int i2 = 0;
        int length;
        if (zzbhq.zzgir) {
            stringBuilder.append("[");
            int dataPosition;
            switch (zzbhq.zzgiq) {
                case 0:
                    int[] zzw = zzbgm.zzw(parcel, i);
                    length = zzw.length;
                    while (i2 < length) {
                        if (i2 != 0) {
                            stringBuilder.append(",");
                        }
                        stringBuilder.append(Integer.toString(zzw[i2]));
                        i2++;
                    }
                    break;
                case 1:
                    Object[] objArr;
                    length = zzbgm.zza(parcel, i);
                    dataPosition = parcel.dataPosition();
                    if (length != 0) {
                        int readInt = parcel.readInt();
                        objArr = new BigInteger[readInt];
                        while (i2 < readInt) {
                            objArr[i2] = new BigInteger(parcel.createByteArray());
                            i2++;
                        }
                        parcel.setDataPosition(length + dataPosition);
                    }
                    zzb.zza(stringBuilder, objArr);
                    break;
                case 2:
                    zzb.zza(stringBuilder, zzbgm.zzx(parcel, i));
                    break;
                case 3:
                    zzb.zza(stringBuilder, zzbgm.zzy(parcel, i));
                    break;
                case 4:
                    length = zzbgm.zza(parcel, i);
                    i2 = parcel.dataPosition();
                    if (length != 0) {
                        dArr = parcel.createDoubleArray();
                        parcel.setDataPosition(length + i2);
                    }
                    zzb.zza(stringBuilder, dArr);
                    break;
                case 5:
                    zzb.zza(stringBuilder, zzbgm.zzz(parcel, i));
                    break;
                case 6:
                    zzb.zza(stringBuilder, zzbgm.zzv(parcel, i));
                    break;
                case 7:
                    zzb.zza(stringBuilder, zzbgm.zzaa(parcel, i));
                    break;
                case 8:
                case 9:
                case 10:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case 11:
                    Parcel[] zzae = zzbgm.zzae(parcel, i);
                    dataPosition = zzae.length;
                    for (int i3 = 0; i3 < dataPosition; i3++) {
                        if (i3 > 0) {
                            stringBuilder.append(",");
                        }
                        zzae[i3].setDataPosition(0);
                        zza(stringBuilder, zzbhq.zzanh(), zzae[i3]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (zzbhq.zzgiq) {
            case 0:
                stringBuilder.append(zzbgm.zzg(parcel, i));
                return;
            case 1:
                stringBuilder.append(zzbgm.zzk(parcel, i));
                return;
            case 2:
                stringBuilder.append(zzbgm.zzi(parcel, i));
                return;
            case 3:
                stringBuilder.append(zzbgm.zzl(parcel, i));
                return;
            case 4:
                stringBuilder.append(zzbgm.zzn(parcel, i));
                return;
            case 5:
                stringBuilder.append(zzbgm.zzp(parcel, i));
                return;
            case 6:
                stringBuilder.append(zzbgm.zzc(parcel, i));
                return;
            case 7:
                stringBuilder.append("\"").append(zzq.zzha(zzbgm.zzq(parcel, i))).append("\"");
                return;
            case 8:
                stringBuilder.append("\"").append(zzc.zzj(zzbgm.zzt(parcel, i))).append("\"");
                return;
            case 9:
                stringBuilder.append("\"").append(zzc.zzk(zzbgm.zzt(parcel, i)));
                stringBuilder.append("\"");
                return;
            case 10:
                Bundle zzs = zzbgm.zzs(parcel, i);
                Set<String> keySet = zzs.keySet();
                keySet.size();
                stringBuilder.append("{");
                length = 1;
                for (String str : keySet) {
                    if (length == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(zzq.zzha(zzs.getString(str))).append("\"");
                    length = 0;
                }
                stringBuilder.append("}");
                return;
            case 11:
                Parcel zzad = zzbgm.zzad(parcel, i);
                zzad.setDataPosition(0);
                zza(stringBuilder, zzbhq.zzanh(), zzad);
                return;
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private final void zza(StringBuilder stringBuilder, Map<String, zzbhq<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Entry entry : map.entrySet()) {
            Entry entry2;
            sparseArray.put(((zzbhq) entry2.getValue()).zzgit, entry2);
        }
        stringBuilder.append('{');
        int zzd = zzbgm.zzd(parcel);
        Object obj = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            entry2 = (Entry) sparseArray.get(SupportMenu.USER_MASK & readInt);
            if (entry2 != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                String str = (String) entry2.getKey();
                zzbhq zzbhq = (zzbhq) entry2.getValue();
                stringBuilder.append("\"").append(str).append("\":");
                if (zzbhq.zzang()) {
                    switch (zzbhq.zzgiq) {
                        case 0:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, Integer.valueOf(zzbgm.zzg(parcel, readInt))));
                            break;
                        case 1:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, zzbgm.zzk(parcel, readInt)));
                            break;
                        case 2:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, Long.valueOf(zzbgm.zzi(parcel, readInt))));
                            break;
                        case 3:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, Float.valueOf(zzbgm.zzl(parcel, readInt))));
                            break;
                        case 4:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, Double.valueOf(zzbgm.zzn(parcel, readInt))));
                            break;
                        case 5:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, zzbgm.zzp(parcel, readInt)));
                            break;
                        case 6:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, Boolean.valueOf(zzbgm.zzc(parcel, readInt))));
                            break;
                        case 7:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, zzbgm.zzq(parcel, readInt)));
                            break;
                        case 8:
                        case 9:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, zzbgm.zzt(parcel, readInt)));
                            break;
                        case 10:
                            zzb(stringBuilder, zzbhq, zzbhp.zza(zzbhq, zzm(zzbgm.zzs(parcel, readInt))));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException("Unknown field out type = " + zzbhq.zzgiq);
                    }
                }
                zza(stringBuilder, zzbhq, parcel, readInt);
                obj = 1;
            }
        }
        if (parcel.dataPosition() != zzd) {
            throw new zzbgn("Overread allowed size end=" + zzd, parcel);
        }
        stringBuilder.append('}');
    }

    private Parcel zzanl() {
        switch (this.zzgjf) {
            case 0:
                this.zzgjg = zzbgo.zze(this.zzgjd);
                break;
            case 1:
                break;
        }
        zzbgo.zzai(this.zzgjd, this.zzgjg);
        this.zzgjf = 2;
        return this.zzgjd;
    }

    private final void zzb(StringBuilder stringBuilder, zzbhq<?, ?> zzbhq, Object obj) {
        if (zzbhq.zzgip) {
            ArrayList arrayList = (ArrayList) obj;
            stringBuilder.append("[");
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    stringBuilder.append(",");
                }
                zza(stringBuilder, zzbhq.zzgio, arrayList.get(i));
            }
            stringBuilder.append("]");
            return;
        }
        zza(stringBuilder, zzbhq.zzgio, obj);
    }

    private static HashMap<String, String> zzm(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    public String toString() {
        zzbq.checkNotNull(this.zzgiw, "Cannot convert to JSON on client side.");
        Parcel zzanl = zzanl();
        zzanl.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        zza(stringBuilder, this.zzgiw.zzgz(this.mClassName), zzanl);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcelable parcelable;
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, zzanl(), false);
        switch (this.zzgje) {
            case 0:
                parcelable = null;
                break;
            case 1:
                parcelable = this.zzgiw;
                break;
            case 2:
                parcelable = this.zzgiw;
                break;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.zzgje);
        }
        zzbgo.zza(parcel, 3, parcelable, i, false);
        zzbgo.zzai(parcel, zze);
    }

    public final Map<String, zzbhq<?, ?>> zzabz() {
        return this.zzgiw == null ? null : this.zzgiw.zzgz(this.mClassName);
    }

    public final Object zzgx(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public final boolean zzgy(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }
}
