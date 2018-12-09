package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzzs.zza;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

final class zzaap {
    static String zza(zzaan zzaan, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("# ").append(str);
        zza(zzaan, stringBuilder, 0);
        return stringBuilder.toString();
    }

    private static void zza(zzaan zzaan, StringBuilder stringBuilder, int i) {
        Map hashMap = new HashMap();
        Map hashMap2 = new HashMap();
        Set<String> treeSet = new TreeSet();
        for (Method method : zzaan.getClass().getDeclaredMethods()) {
            hashMap2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                hashMap.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String valueOf;
            String valueOf2;
            Method method2;
            String replaceFirst = str.replaceFirst("get", TtmlNode.ANONYMOUS_REGION_ID);
            if (!(!replaceFirst.endsWith("List") || replaceFirst.endsWith("OrBuilderList") || replaceFirst.equals("List"))) {
                valueOf = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                valueOf2 = String.valueOf(replaceFirst.substring(1, replaceFirst.length() - 4));
                valueOf = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                method2 = (Method) hashMap.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zzb(stringBuilder, i, zzfi(valueOf), zzzs.zza(method2, (Object) zzaan, new Object[0]));
                }
            }
            if (replaceFirst.endsWith("Map") && !replaceFirst.equals("Map")) {
                valueOf = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                valueOf2 = String.valueOf(replaceFirst.substring(1, replaceFirst.length() - 3));
                valueOf = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                method2 = (Method) hashMap.get(str);
                if (method2 != null && method2.getReturnType().equals(Map.class) && !method2.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method2.getModifiers())) {
                    zzb(stringBuilder, i, zzfi(valueOf), zzzs.zza(method2, (Object) zzaan, new Object[0]));
                }
            }
            String str2 = "set";
            valueOf2 = String.valueOf(replaceFirst);
            if (((Method) hashMap2.get(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2))) != null) {
                if (replaceFirst.endsWith("Bytes")) {
                    str2 = "get";
                    valueOf2 = String.valueOf(replaceFirst.substring(0, replaceFirst.length() - 5));
                    if (hashMap.containsKey(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2))) {
                    }
                }
                str2 = String.valueOf(replaceFirst.substring(0, 1).toLowerCase());
                valueOf2 = String.valueOf(replaceFirst.substring(1));
                String concat = valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2);
                str2 = "get";
                valueOf2 = String.valueOf(replaceFirst);
                method2 = (Method) hashMap.get(valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                valueOf = "has";
                str2 = String.valueOf(replaceFirst);
                Method method3 = (Method) hashMap.get(str2.length() != 0 ? valueOf.concat(str2) : new String(valueOf));
                if (method2 != null) {
                    boolean equals;
                    zzaan zza = zzzs.zza(method2, (Object) zzaan, new Object[0]);
                    if (method3 == null) {
                        equals = zza instanceof Boolean ? !((Boolean) zza).booleanValue() : zza instanceof Integer ? ((Integer) zza).intValue() == 0 : zza instanceof Float ? ((Float) zza).floatValue() == BitmapDescriptorFactory.HUE_RED : zza instanceof Double ? ((Double) zza).doubleValue() == 0.0d : zza instanceof String ? zza.equals(TtmlNode.ANONYMOUS_REGION_ID) : zza instanceof zzyy ? zza.equals(zzyy.zzbrh) : zza instanceof zzaan ? zza == ((zzaan) zza).zzui() : zza instanceof Enum ? ((Enum) zza).ordinal() == 0 : false;
                        equals = !equals;
                    } else {
                        equals = ((Boolean) zzzs.zza(method3, (Object) zzaan, new Object[0])).booleanValue();
                    }
                    if (equals) {
                        zzb(stringBuilder, i, zzfi(concat), zza);
                    }
                }
            }
        }
        if (zzaan instanceof zza) {
            Iterator it = ((zza) zzaan).zzbsl.iterator();
            if (it.hasNext()) {
                ((Entry) it.next()).getKey();
                throw new NoSuchMethodError();
            }
        }
        if (((zzzs) zzaan).zzbsi != null) {
            zzabm zzabm = ((zzzs) zzaan).zzbsi;
        }
    }

    static final void zzb(StringBuilder stringBuilder, int i, String str, Object obj) {
        int i2 = 0;
        if (obj instanceof List) {
            for (Object zzb : (List) obj) {
                zzb(stringBuilder, i, str, zzb);
            }
        } else if (obj instanceof Map) {
            for (Entry zzb2 : ((Map) obj).entrySet()) {
                zzb(stringBuilder, i, str, zzb2);
            }
        } else {
            stringBuilder.append('\n');
            for (int i3 = 0; i3 < i; i3++) {
                stringBuilder.append(' ');
            }
            stringBuilder.append(str);
            if (obj instanceof String) {
                stringBuilder.append(": \"").append(zzabi.zza(zzyy.zzfg((String) obj))).append('\"');
            } else if (obj instanceof zzyy) {
                stringBuilder.append(": \"").append(zzabi.zza((zzyy) obj)).append('\"');
            } else if (obj instanceof zzzs) {
                stringBuilder.append(" {");
                zza((zzzs) obj, stringBuilder, i + 2);
                stringBuilder.append("\n");
                while (i2 < i) {
                    stringBuilder.append(' ');
                    i2++;
                }
                stringBuilder.append("}");
            } else if (obj instanceof Entry) {
                stringBuilder.append(" {");
                Entry entry = (Entry) obj;
                zzb(stringBuilder, i + 2, "key", entry.getKey());
                zzb(stringBuilder, i + 2, C1797b.VALUE, entry.getValue());
                stringBuilder.append("\n");
                while (i2 < i) {
                    stringBuilder.append(' ');
                    i2++;
                }
                stringBuilder.append("}");
            } else {
                stringBuilder.append(": ").append(obj.toString());
            }
        }
    }

    private static final String zzfi(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt)) {
                stringBuilder.append("_");
            }
            stringBuilder.append(Character.toLowerCase(charAt));
        }
        return stringBuilder.toString();
    }
}
