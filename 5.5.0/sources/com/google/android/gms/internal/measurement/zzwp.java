package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class zzwp {
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzbmt = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzbmu = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzbmv = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzbmw = new AtomicBoolean();
    private static HashMap<String, String> zzbmx;
    private static final HashMap<String, Boolean> zzbmy = new HashMap();
    private static final HashMap<String, Integer> zzbmz = new HashMap();
    private static final HashMap<String, Long> zzbna = new HashMap();
    private static final HashMap<String, Float> zzbnb = new HashMap();
    private static Object zzbnc;
    private static boolean zzbnd;
    private static String[] zzbne = new String[0];

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap<java.lang.String, T> r2, java.lang.String r3, T r4) {
        /*
        r1 = com.google.android.gms.internal.measurement.zzwp.class;
        monitor-enter(r1);
        r0 = r2.containsKey(r3);	 Catch:{ all -> 0x0016 }
        if (r0 == 0) goto L_0x0013;
    L_0x0009:
        r0 = r2.get(r3);	 Catch:{ all -> 0x0016 }
        if (r0 == 0) goto L_0x0011;
    L_0x000f:
        monitor-exit(r1);	 Catch:{ all -> 0x0016 }
    L_0x0010:
        return r0;
    L_0x0011:
        r0 = r4;
        goto L_0x000f;
    L_0x0013:
        monitor-exit(r1);	 Catch:{ all -> 0x0016 }
        r0 = 0;
        goto L_0x0010;
    L_0x0016:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0016 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzwp.zza(java.util.HashMap, java.lang.String, java.lang.Object):T");
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        String str3 = null;
        synchronized (zzwp.class) {
            zza(contentResolver);
            Object obj = zzbnc;
            String str4;
            if (zzbmx.containsKey(str)) {
                str4 = (String) zzbmx.get(str);
                if (str4 != null) {
                    str3 = str4;
                }
            } else {
                String[] strArr = zzbne;
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    if (str.startsWith(strArr[i])) {
                        if (!zzbnd || zzbmx.isEmpty()) {
                            zzbmx.putAll(zza(contentResolver, zzbne));
                            zzbnd = true;
                            if (zzbmx.containsKey(str)) {
                                str4 = (String) zzbmx.get(str);
                                if (str4 != null) {
                                    str3 = str4;
                                }
                            }
                        }
                    } else {
                        i++;
                    }
                }
                Cursor query = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            str4 = query.getString(1);
                            if (str4 != null && str4.equals(null)) {
                                str4 = null;
                            }
                            zza(obj, str, str4);
                            if (str4 != null) {
                                str3 = str4;
                            }
                            if (query != null) {
                                query.close();
                            }
                        } else {
                            zza(obj, str, null);
                            if (query != null) {
                                query.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (query != null) {
                            query.close();
                        }
                    }
                } else if (query != null) {
                    query.close();
                }
            }
        }
        return str3;
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzbmt, null, null, strArr, null);
        Map<String, String> treeMap = new TreeMap();
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    treeMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbmx == null) {
            zzbmw.set(false);
            zzbmx = new HashMap();
            zzbnc = new Object();
            zzbnd = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzwq(null));
        } else if (zzbmw.getAndSet(false)) {
            zzbmx.clear();
            zzbmy.clear();
            zzbmz.clear();
            zzbna.clear();
            zzbnb.clear();
            zzbnc = new Object();
            zzbnd = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzwp.class) {
            if (obj == zzbnc) {
                zzbmx.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzwp.class) {
            if (obj == zzbnc) {
                hashMap.put(str, t);
                zzbmx.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb = zzb(contentResolver);
        Object obj = (Boolean) zza(zzbmy, str, Boolean.valueOf(z));
        if (obj != null) {
            return obj.booleanValue();
        }
        Object zza = zza(contentResolver, str, null);
        if (!(zza == null || zza.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            if (zzbmu.matcher(zza).matches()) {
                obj = Boolean.valueOf(true);
                z = true;
            } else if (zzbmv.matcher(zza).matches()) {
                obj = Boolean.valueOf(false);
                z = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + zza + "\") as boolean");
            }
        }
        zza(zzb, zzbmy, str, obj);
        return z;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzwp.class) {
            zza(contentResolver);
            obj = zzbnc;
        }
        return obj;
    }
}
