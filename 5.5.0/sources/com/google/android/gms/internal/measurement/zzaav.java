package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

final class zzaav {
    private static final zzaav zzbua = new zzaav();
    private final zzaay zzbub;
    private final ConcurrentMap<Class<?>, zzaax<?>> zzbuc = new ConcurrentHashMap();

    private zzaav() {
        zzaay zzaay = null;
        String[] strArr = new String[]{"com.google.protobuf.AndroidProto3SchemaFactory"};
        for (int i = 0; i <= 0; i++) {
            zzaay = zzfj(strArr[0]);
            if (zzaay != null) {
                break;
            }
        }
        if (zzaay == null) {
            zzaay = new zzaaf();
        }
        this.zzbub = zzaay;
    }

    private static zzaay zzfj(String str) {
        try {
            return (zzaay) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    public static zzaav zzum() {
        return zzbua;
    }

    public final <T> zzaax<T> zzt(T t) {
        Class cls = t.getClass();
        zzzt.zza(cls, "messageType");
        zzaax<T> zzaax = (zzaax) this.zzbuc.get(cls);
        if (zzaax != null) {
            return zzaax;
        }
        zzaax<T> zzg = this.zzbub.zzg(cls);
        zzzt.zza(cls, "messageType");
        zzzt.zza(zzg, "schema");
        zzaax = (zzaax) this.zzbuc.putIfAbsent(cls, zzg);
        return zzaax != null ? zzaax : zzg;
    }
}
