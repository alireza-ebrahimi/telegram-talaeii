package com.google.android.gms.internal.clearcut;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class zzy {
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzcq = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzcr = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzcs = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzct = new AtomicBoolean();
    private static HashMap<String, String> zzcu;
    private static final HashMap<String, Boolean> zzcv = new HashMap();
    private static final HashMap<String, Integer> zzcw = new HashMap();
    private static final HashMap<String, Long> zzcx = new HashMap();
    private static final HashMap<String, Float> zzcy = new HashMap();
    private static Object zzcz;
    private static boolean zzda;
    private static String[] zzdb = new String[0];

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        Object zzb = zzb(contentResolver);
        Long l = (Long) zza(zzcx, str, Long.valueOf(0));
        if (l != null) {
            return l.longValue();
        }
        Object obj;
        long j2;
        String zza = zza(contentResolver, str, null);
        if (zza == null) {
            obj = l;
            j2 = 0;
        } else {
            Long valueOf;
            try {
                long parseLong = Long.parseLong(zza);
                valueOf = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException e) {
                valueOf = l;
                j2 = 0;
            }
        }
        zza(zzb, zzcx, str, obj);
        return j2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap<java.lang.String, T> r2, java.lang.String r3, T r4) {
        /*
        r1 = com.google.android.gms.internal.clearcut.zzy.class;
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.clearcut.zzy.zza(java.util.HashMap, java.lang.String, java.lang.Object):T");
    }

    public static String zza(ContentResolver contentResolver, String str, String str2) {
        String str3 = null;
        synchronized (zzy.class) {
            zza(contentResolver);
            Object obj = zzcz;
            String str4;
            if (zzcu.containsKey(str)) {
                str4 = (String) zzcu.get(str);
                if (str4 != null) {
                    str3 = str4;
                }
            } else {
                String[] strArr = zzdb;
                int length = strArr.length;
                int i = 0;
                while (i < length) {
                    if (str.startsWith(strArr[i])) {
                        if (!zzda || zzcu.isEmpty()) {
                            zzcu.putAll(zza(contentResolver, zzdb));
                            zzda = true;
                            if (zzcu.containsKey(str)) {
                                str4 = (String) zzcu.get(str);
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
                        }
                    } catch (Throwable th) {
                        if (query != null) {
                            query.close();
                        }
                    }
                }
                zza(obj, str, null);
                if (query != null) {
                    query.close();
                }
            }
        }
        return str3;
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzcq, null, null, strArr, null);
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
        if (zzcu == null) {
            zzct.set(false);
            zzcu = new HashMap();
            zzcz = new Object();
            zzda = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzz(null));
        } else if (zzct.getAndSet(false)) {
            zzcu.clear();
            zzcv.clear();
            zzcw.clear();
            zzcx.clear();
            zzcy.clear();
            zzcz = new Object();
            zzda = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzy.class) {
            if (obj == zzcz) {
                zzcu.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzy.class) {
            if (obj == zzcz) {
                hashMap.put(str, t);
                zzcu.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb = zzb(contentResolver);
        Object obj = (Boolean) zza(zzcv, str, Boolean.valueOf(z));
        if (obj != null) {
            return obj.booleanValue();
        }
        Object zza = zza(contentResolver, str, null);
        if (!(zza == null || zza.equals(TtmlNode.ANONYMOUS_REGION_ID))) {
            if (zzcr.matcher(zza).matches()) {
                obj = Boolean.valueOf(true);
                z = true;
            } else if (zzcs.matcher(zza).matches()) {
                obj = Boolean.valueOf(false);
                z = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + zza + "\") as boolean");
            }
        }
        zza(zzb, zzcv, str, obj);
        return z;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzy.class) {
            zza(contentResolver);
            obj = zzcz;
        }
        return obj;
    }
}
