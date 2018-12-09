package com.google.android.gms.internal.clearcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzcw extends zzav<String> implements zzcx, RandomAccess {
    private static final zzcw zzlq;
    private static final zzcx zzlr = zzlq;
    private final List<Object> zzls;

    static {
        zzav zzcw = new zzcw();
        zzlq = zzcw;
        zzcw.zzv();
    }

    public zzcw() {
        this(10);
    }

    public zzcw(int i) {
        this(new ArrayList(i));
    }

    private zzcw(ArrayList<Object> arrayList) {
        this.zzls = arrayList;
    }

    private static String zze(Object obj) {
        return obj instanceof String ? (String) obj : obj instanceof zzbb ? ((zzbb) obj).zzz() : zzci.zzf((byte[]) obj);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        String str = (String) obj;
        zzw();
        this.zzls.add(i, str);
        this.modCount++;
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        Collection zzbt;
        zzw();
        if (collection instanceof zzcx) {
            zzbt = ((zzcx) collection).zzbt();
        }
        boolean addAll = this.zzls.addAll(i, zzbt);
        this.modCount++;
        return addAll;
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final void clear() {
        zzw();
        this.zzls.clear();
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzls.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        String zzz;
        if (obj instanceof zzbb) {
            zzbb zzbb = (zzbb) obj;
            zzz = zzbb.zzz();
            if (zzbb.zzaa()) {
                this.zzls.set(i, zzz);
            }
            return zzz;
        }
        byte[] bArr = (byte[]) obj;
        zzz = zzci.zzf(bArr);
        if (zzci.zze(bArr)) {
            this.zzls.set(i, zzz);
        }
        return zzz;
    }

    public final Object getRaw(int i) {
        return this.zzls.get(i);
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* synthetic */ Object remove(int i) {
        zzw();
        Object remove = this.zzls.remove(i);
        this.modCount++;
        return zze(remove);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        String str = (String) obj;
        zzw();
        return zze(this.zzls.set(i, str));
    }

    public final int size() {
        return this.zzls.size();
    }

    public final List<?> zzbt() {
        return Collections.unmodifiableList(this.zzls);
    }

    public final zzcx zzbu() {
        return zzu() ? new zzfa(this) : this;
    }

    public final /* synthetic */ zzcn zzi(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzls);
        return new zzcw(arrayList);
    }

    public final /* bridge */ /* synthetic */ boolean zzu() {
        return super.zzu();
    }
}
