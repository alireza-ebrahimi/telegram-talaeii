package com.google.firebase.auth.p104a.p105a;

import android.support.annotation.GuardedBy;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

/* renamed from: com.google.firebase.auth.a.a.a */
public abstract class C1826a {
    /* renamed from: a */
    private static Logger f5456a = new Logger("BiChannelGoogleApi", "FirebaseAuth: ");
    @GuardedBy("this")
    /* renamed from: b */
    private C1829b f5457b;

    /* renamed from: b */
    private final GoogleApi m8527b(C1834f c1834f) {
        C1829b b = m8528b();
        if (b.f5467c.mo3013a(c1834f)) {
            Logger logger = f5456a;
            String valueOf = String.valueOf(b.f5466b);
            logger.m8463w(new StringBuilder(String.valueOf(valueOf).length() + 43).append("getGoogleApiForMethod() returned Fallback: ").append(valueOf).toString(), new Object[0]);
            return b.f5466b;
        }
        logger = f5456a;
        valueOf = String.valueOf(b.f5465a);
        logger.m8463w(new StringBuilder(String.valueOf(valueOf).length() + 38).append("getGoogleApiForMethod() returned Gms: ").append(valueOf).toString(), new Object[0]);
        return b.f5465a;
    }

    /* renamed from: b */
    private final C1829b m8528b() {
        C1829b c1829b;
        synchronized (this) {
            if (this.f5457b == null) {
                this.f5457b = mo3014a();
            }
            c1829b = this.f5457b;
        }
        return c1829b;
    }

    /* renamed from: a */
    public final <TResult, A extends AnyClient> Task<TResult> m8529a(C1834f<A, TResult> c1834f) {
        GoogleApi b = m8527b(c1834f);
        return b == null ? Tasks.forException(C1842m.m8593a(new Status(17499, "Unable to connect to GoogleApi instance - Google Play Services may be unavailable"))) : b.doRead((TaskApiCall) c1834f);
    }

    /* renamed from: a */
    abstract C1829b mo3014a();
}
