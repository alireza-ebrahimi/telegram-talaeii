package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ListUtils {
    private ListUtils() {
    }

    public static <T> ArrayList<T> copyAndRemoveElementFromListIfPresent(List<T> list, T t) {
        int size = list.size();
        ArrayList<T> arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            if (t == null || !t.equals(obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static <T> ArrayList<T> copyAndRemoveElementsFromListIfPresent(List<T> list, Collection<T> collection) {
        Preconditions.checkNotNull(collection);
        ArrayList<T> arrayList = new ArrayList(list.size());
        for (Object next : list) {
            if (!collection.contains(next)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
