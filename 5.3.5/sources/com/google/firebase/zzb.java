package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzda;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzb implements zzda {
    public final Exception zzt(Status status) {
        return status.getStatusCode() == 8 ? new FirebaseException(status.zzaif()) : new FirebaseApiNotAvailableException(status.zzaif());
    }
}
