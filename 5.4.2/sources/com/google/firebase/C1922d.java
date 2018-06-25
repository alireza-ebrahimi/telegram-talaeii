package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

/* renamed from: com.google.firebase.d */
public class C1922d implements StatusExceptionMapper {
    public Exception getException(Status status) {
        return status.getStatusCode() == 8 ? new C1812c(status.zzp()) : new C1813a(status.zzp());
    }
}
