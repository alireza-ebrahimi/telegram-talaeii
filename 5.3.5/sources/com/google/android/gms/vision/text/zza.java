package com.google.android.gms.vision.text;

import java.util.Comparator;
import java.util.Map.Entry;

final class zza implements Comparator<Entry<String, Integer>> {
    zza(TextBlock textBlock) {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Integer) ((Entry) obj).getValue()).compareTo((Integer) ((Entry) obj2).getValue());
    }
}
