package com.google.android.gms.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class zzfgj<MessageType extends zzfgj<MessageType, BuilderType>, BuilderType extends zzfgk<MessageType, BuilderType>> implements zzfjc {
    private static boolean zzpnp = false;
    protected int zzpno = 0;

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzfgk.zza((Iterable) iterable, (List) list);
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzhs()];
            zzfhg zzbc = zzfhg.zzbc(bArr);
            zza(zzbc);
            zzbc.zzcyx();
            return bArr;
        } catch (Throwable e) {
            String str = "byte array";
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length()).append("Serializing ").append(name).append(" to a ").append(str).append(" threw an IOException (should never happen).").toString(), e);
        }
    }

    public final zzfgs toByteString() {
        try {
            zzfgx zzle = zzfgs.zzle(zzhs());
            zza(zzle.zzcxw());
            return zzle.zzcxv();
        } catch (Throwable e) {
            String str = "ByteString";
            String name = getClass().getName();
            throw new RuntimeException(new StringBuilder((String.valueOf(name).length() + 62) + String.valueOf(str).length()).append("Serializing ").append(name).append(" to a ").append(str).append(" threw an IOException (should never happen).").toString(), e);
        }
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        zzfhg zzb = zzfhg.zzb(outputStream, zzfhg.zzlr(zzhs()));
        zza(zzb);
        zzb.flush();
    }
}
