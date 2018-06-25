package com.google.android.gms.common.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    public static <K, V> K getKeyFromMap(Map<K, V> map, K k) {
        if (map.containsKey(k)) {
            for (K next : map.keySet()) {
                if (next.equals(k)) {
                    return next;
                }
            }
        }
        return null;
    }

    public static void writeStringMapToJson(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        Object obj = 1;
        for (String str : hashMap.keySet()) {
            Object obj2;
            if (obj == null) {
                stringBuilder.append(",");
                obj2 = obj;
            } else {
                obj2 = null;
            }
            String str2 = (String) hashMap.get(str);
            stringBuilder.append("\"").append(str).append("\":");
            if (str2 == null) {
                stringBuilder.append("null");
                obj = obj2;
            } else {
                stringBuilder.append("\"").append(str2).append("\"");
                obj = obj2;
            }
        }
        stringBuilder.append("}");
    }
}
