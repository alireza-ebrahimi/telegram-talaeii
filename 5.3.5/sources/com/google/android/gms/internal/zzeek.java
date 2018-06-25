package com.google.android.gms.internal;

import java.util.Comparator;

public final class zzeek<A extends Comparable<A>> implements Comparator<A> {
    private static zzeek zzmzg = new zzeek();

    private zzeek() {
    }

    public static <T extends Comparable<T>> zzeek<T> zze(Class<T> cls) {
        return zzmzg;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo((Comparable) obj2);
    }
}
