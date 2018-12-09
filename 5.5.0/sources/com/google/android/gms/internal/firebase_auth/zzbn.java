package com.google.android.gms.internal.firebase_auth;

public abstract class zzbn<MessageType extends zzbn<MessageType, BuilderType>, BuilderType extends zzbo<MessageType, BuilderType>> implements zzeh {
    private static boolean zzmb = false;
    protected int zzma = 0;

    public final zzbu zzbo() {
        try {
            zzbz zzl = zzbu.zzl(zzdq());
            zzb(zzl.zzcb());
            return zzl.zzca();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length()).append("Serializing ").append(name).append(" to a ").append(str).append(" threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzbp() {
        throw new UnsupportedOperationException();
    }

    void zzg(int i) {
        throw new UnsupportedOperationException();
    }
}
