package com.google.android.gms.internal.firebase_auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzdp extends zzbq<String> implements zzdq, RandomAccess {
    private static final zzdp zzsq;
    private static final zzdq zzsr = zzsq;
    private final List<Object> zzss;

    static {
        zzbq zzdp = new zzdp();
        zzsq = zzdp;
        zzdp.zzbs();
    }

    public zzdp() {
        this(10);
    }

    public zzdp(int i) {
        this(new ArrayList(i));
    }

    private zzdp(ArrayList<Object> arrayList) {
        this.zzss = arrayList;
    }

    private static String zzg(Object obj) {
        return obj instanceof String ? (String) obj : obj instanceof zzbu ? ((zzbu) obj).zzbw() : zzdd.zze((byte[]) obj);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzbt();
        this.zzss.add(i, str);
        this.modCount++;
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        Collection zzeo;
        zzbt();
        if (collection instanceof zzdq) {
            zzeo = ((zzdq) collection).zzeo();
        }
        boolean addAll = this.zzss.addAll(i, zzeo);
        this.modCount++;
        return addAll;
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final void clear() {
        zzbt();
        this.zzss.clear();
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzss.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        String zzbw;
        if (obj instanceof zzbu) {
            zzbu zzbu = (zzbu) obj;
            zzbw = zzbu.zzbw();
            if (zzbu.zzbx()) {
                this.zzss.set(i, zzbw);
            }
            return zzbw;
        }
        byte[] bArr = (byte[]) obj;
        zzbw = zzdd.zze(bArr);
        if (zzdd.zzd(bArr)) {
            this.zzss.set(i, zzbw);
        }
        return zzbw;
    }

    public final Object getRaw(int i) {
        return this.zzss.get(i);
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* synthetic */ Object remove(int i) {
        zzbt();
        Object remove = this.zzss.remove(i);
        this.modCount++;
        return zzg(remove);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzbt();
        return zzg(this.zzss.set(i, str));
    }

    public final int size() {
        return this.zzss.size();
    }

    public final /* bridge */ /* synthetic */ boolean zzbr() {
        return super.zzbr();
    }

    public final void zzc(zzbu zzbu) {
        zzbt();
        this.zzss.add(zzbu);
        this.modCount++;
    }

    public final List<?> zzeo() {
        return Collections.unmodifiableList(this.zzss);
    }

    public final zzdq zzep() {
        return zzbr() ? new zzfs(this) : this;
    }

    public final /* synthetic */ zzdg zzj(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzss);
        return new zzdp(arrayList);
    }
}
