package com.google.android.gms.internal.firebase_auth;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzes {
    private static final zzes zzud = new zzes();
    private final zzew zzue;
    private final ConcurrentMap<Class<?>, zzev<?>> zzuf = new ConcurrentHashMap();

    private zzes() {
        zzew zzew = null;
        String[] strArr = new String[]{"com.google.protobuf.AndroidProto3SchemaFactory"};
        for (int i = 0; i <= 0; i++) {
            zzew = zzaq(strArr[0]);
            if (zzew != null) {
                break;
            }
        }
        if (zzew == null) {
            zzew = new zzdw();
        }
        this.zzue = zzew;
    }

    private static zzew zzaq(String str) {
        try {
            return (zzew) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    public static zzes zzfg() {
        return zzud;
    }

    public final <T> zzev<T> zzf(Class<T> cls) {
        zzdd.zza((Object) cls, "messageType");
        zzev<T> zzev = (zzev) this.zzuf.get(cls);
        if (zzev != null) {
            return zzev;
        }
        zzev<T> zze = this.zzue.zze(cls);
        zzdd.zza((Object) cls, "messageType");
        zzdd.zza((Object) zze, "schema");
        zzev = (zzev) this.zzuf.putIfAbsent(cls, zze);
        return zzev != null ? zzev : zze;
    }

    public final <T> zzev<T> zzq(T t) {
        return zzf(t.getClass());
    }
}
