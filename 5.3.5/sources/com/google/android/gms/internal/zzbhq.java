package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import java.util.ArrayList;
import java.util.Map;

public final class zzbhq<I, O> extends zzbgl {
    public static final zzbht CREATOR = new zzbht();
    private final int zzehz;
    protected final int zzgio;
    protected final boolean zzgip;
    protected final int zzgiq;
    protected final boolean zzgir;
    protected final String zzgis;
    protected final int zzgit;
    protected final Class<? extends zzbhp> zzgiu;
    private String zzgiv;
    private zzbhv zzgiw;
    private zzbhr<I, O> zzgix;

    zzbhq(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, zzbhj zzbhj) {
        this.zzehz = i;
        this.zzgio = i2;
        this.zzgip = z;
        this.zzgiq = i3;
        this.zzgir = z2;
        this.zzgis = str;
        this.zzgit = i4;
        if (str2 == null) {
            this.zzgiu = null;
            this.zzgiv = null;
        } else {
            this.zzgiu = zzbia.class;
            this.zzgiv = str2;
        }
        if (zzbhj == null) {
            this.zzgix = null;
        } else {
            this.zzgix = zzbhj.zzand();
        }
    }

    private zzbhq(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends zzbhp> cls, zzbhr<I, O> zzbhr) {
        this.zzehz = 1;
        this.zzgio = i;
        this.zzgip = z;
        this.zzgiq = i2;
        this.zzgir = z2;
        this.zzgis = str;
        this.zzgit = i3;
        this.zzgiu = cls;
        if (cls == null) {
            this.zzgiv = null;
        } else {
            this.zzgiv = cls.getCanonicalName();
        }
        this.zzgix = zzbhr;
    }

    public static zzbhq zza(String str, int i, zzbhr<?, ?> zzbhr, boolean z) {
        return new zzbhq(7, false, 0, false, str, i, null, zzbhr);
    }

    public static <T extends zzbhp> zzbhq<T, T> zza(String str, int i, Class<T> cls) {
        return new zzbhq(11, false, 11, false, str, i, cls, null);
    }

    private String zzanf() {
        return this.zzgiv == null ? null : this.zzgiv;
    }

    public static <T extends zzbhp> zzbhq<ArrayList<T>, ArrayList<T>> zzb(String str, int i, Class<T> cls) {
        return new zzbhq(11, true, 11, true, str, i, cls, null);
    }

    public static zzbhq<Integer, Integer> zzj(String str, int i) {
        return new zzbhq(0, false, 0, false, str, i, null, null);
    }

    public static zzbhq<Boolean, Boolean> zzk(String str, int i) {
        return new zzbhq(6, false, 6, false, str, i, null, null);
    }

    public static zzbhq<String, String> zzl(String str, int i) {
        return new zzbhq(7, false, 7, false, str, i, null, null);
    }

    public static zzbhq<ArrayList<String>, ArrayList<String>> zzm(String str, int i) {
        return new zzbhq(7, true, 7, true, str, i, null, null);
    }

    public static zzbhq<byte[], byte[]> zzn(String str, int i) {
        return new zzbhq(8, false, 8, false, str, 4, null, null);
    }

    public final I convertBack(O o) {
        return this.zzgix.convertBack(o);
    }

    public final String toString() {
        zzbi zzg = zzbg.zzx(this).zzg("versionCode", Integer.valueOf(this.zzehz)).zzg("typeIn", Integer.valueOf(this.zzgio)).zzg("typeInArray", Boolean.valueOf(this.zzgip)).zzg("typeOut", Integer.valueOf(this.zzgiq)).zzg("typeOutArray", Boolean.valueOf(this.zzgir)).zzg("outputFieldName", this.zzgis).zzg("safeParcelFieldId", Integer.valueOf(this.zzgit)).zzg("concreteTypeName", zzanf());
        Class cls = this.zzgiu;
        if (cls != null) {
            zzg.zzg("concreteType.class", cls.getCanonicalName());
        }
        if (this.zzgix != null) {
            zzg.zzg("converterName", this.zzgix.getClass().getCanonicalName());
        }
        return zzg.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zzc(parcel, 2, this.zzgio);
        zzbgo.zza(parcel, 3, this.zzgip);
        zzbgo.zzc(parcel, 4, this.zzgiq);
        zzbgo.zza(parcel, 5, this.zzgir);
        zzbgo.zza(parcel, 6, this.zzgis, false);
        zzbgo.zzc(parcel, 7, this.zzgit);
        zzbgo.zza(parcel, 8, zzanf(), false);
        zzbgo.zza(parcel, 9, this.zzgix == null ? null : zzbhj.zza(this.zzgix), i, false);
        zzbgo.zzai(parcel, zze);
    }

    public final void zza(zzbhv zzbhv) {
        this.zzgiw = zzbhv;
    }

    public final int zzane() {
        return this.zzgit;
    }

    public final boolean zzang() {
        return this.zzgix != null;
    }

    public final Map<String, zzbhq<?, ?>> zzanh() {
        zzbq.checkNotNull(this.zzgiv);
        zzbq.checkNotNull(this.zzgiw);
        return this.zzgiw.zzgz(this.zzgiv);
    }
}
