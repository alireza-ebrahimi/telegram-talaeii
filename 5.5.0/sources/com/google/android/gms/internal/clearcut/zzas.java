package com.google.android.gms.internal.clearcut;

public abstract class zzas<MessageType extends zzas<MessageType, BuilderType>, BuilderType extends zzat<MessageType, BuilderType>> implements zzdo {
    private static boolean zzey = false;
    protected int zzex = 0;

    void zzf(int i) {
        throw new UnsupportedOperationException();
    }

    public final zzbb zzr() {
        try {
            zzbg zzk = zzbb.zzk(zzas());
            zzb(zzk.zzae());
            return zzk.zzad();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length()).append("Serializing ").append(name).append(" to a ").append(str).append(" threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzs() {
        throw new UnsupportedOperationException();
    }
}
