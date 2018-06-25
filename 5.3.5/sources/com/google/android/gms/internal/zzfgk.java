package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzfgk<MessageType extends zzfgj<MessageType, BuilderType>, BuilderType extends zzfgk<MessageType, BuilderType>> implements zzfjd {
    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzfhz.checkNotNull(iterable);
        if (iterable instanceof zzfil) {
            List zzdap = ((zzfil) iterable).zzdap();
            zzfil zzfil = (zzfil) list;
            int size = list.size();
            for (Object next : zzdap) {
                if (next == null) {
                    String str = "Element at index " + (zzfil.size() - size) + " is null.";
                    for (int size2 = zzfil.size() - 1; size2 >= size; size2--) {
                        zzfil.remove(size2);
                    }
                    throw new NullPointerException(str);
                } else if (next instanceof zzfgs) {
                    zzfil.zzba((zzfgs) next);
                } else {
                    zzfil.add((String) next);
                }
            }
        } else if (iterable instanceof zzfjm) {
            list.addAll((Collection) iterable);
        } else {
            zzb((Iterable) iterable, (List) list);
        }
    }

    private static <T> void zzb(Iterable<T> iterable, List<? super T> list) {
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(((Collection) iterable).size() + list.size());
        }
        int size = list.size();
        for (Object next : iterable) {
            if (next == null) {
                String str = "Element at index " + (list.size() - size) + " is null.";
                for (int size2 = list.size() - 1; size2 >= size; size2--) {
                    list.remove(size2);
                }
                throw new NullPointerException(str);
            }
            list.add(next);
        }
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzcxj();
    }

    protected abstract BuilderType zza(MessageType messageType);

    public abstract BuilderType zza(zzfhb zzfhb, zzfhm zzfhm) throws IOException;

    public /* synthetic */ zzfjd zzb(zzfhb zzfhb, zzfhm zzfhm) throws IOException {
        return zza(zzfhb, zzfhm);
    }

    public abstract BuilderType zzcxj();

    public final /* synthetic */ zzfjd zzd(zzfjc zzfjc) {
        if (zzczu().getClass().isInstance(zzfjc)) {
            return zza((zzfgj) zzfjc);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }
}
