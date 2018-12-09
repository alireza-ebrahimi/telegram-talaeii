package com.google.android.gms.internal.clearcut;

public abstract class zzat<MessageType extends zzas<MessageType, BuilderType>, BuilderType extends zzat<MessageType, BuilderType>> implements zzdp {
    public /* synthetic */ Object clone() {
        return zzt();
    }

    protected abstract BuilderType zza(MessageType messageType);

    public final /* synthetic */ zzdp zza(zzdo zzdo) {
        if (zzbe().getClass().isInstance(zzdo)) {
            return zza((zzas) zzdo);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }

    public abstract BuilderType zzt();
}
