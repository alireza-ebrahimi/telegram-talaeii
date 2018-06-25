package com.google.android.gms.internal.firebase_auth;

public abstract class zzbo<MessageType extends zzbn<MessageType, BuilderType>, BuilderType extends zzbo<MessageType, BuilderType>> implements zzei {
    public /* synthetic */ Object clone() {
        return zzbq();
    }

    protected abstract BuilderType zza(MessageType messageType);

    public final /* synthetic */ zzei zza(zzeh zzeh) {
        if (zzeb().getClass().isInstance(zzeh)) {
            return zza((zzbn) zzeh);
        }
        throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
    }

    public abstract BuilderType zzbq();
}
