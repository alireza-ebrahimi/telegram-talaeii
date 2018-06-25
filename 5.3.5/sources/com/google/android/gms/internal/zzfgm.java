package com.google.android.gms.internal;

public abstract class zzfgm<MessageType extends zzfjc> implements zzfjl<MessageType> {
    private static final zzfhm zzpns = zzfhm.zzczf();

    public final /* synthetic */ Object zzc(zzfhb zzfhb, zzfhm zzfhm) throws zzfie {
        zzfjc zzfjc = (zzfjc) zze(zzfhb, zzfhm);
        if (zzfjc == null || zzfjc.isInitialized()) {
            return zzfjc;
        }
        zzfkm zzfkm = zzfjc instanceof zzfgj ? new zzfkm((zzfgj) zzfjc) : zzfjc instanceof zzfgl ? new zzfkm((zzfgl) zzfjc) : new zzfkm(zzfjc);
        throw zzfkm.zzdbz().zzi(zzfjc);
    }
}
