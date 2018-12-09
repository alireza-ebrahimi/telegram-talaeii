package com.google.firebase.auth.p104a.p105a;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.internal.firebase_auth.zza;
import com.google.android.gms.internal.firebase_auth.zzc;

/* renamed from: com.google.firebase.auth.a.a.r */
public final class C1844r extends zza implements C1843q {
    C1844r(IBinder iBinder) {
        super(iBinder, "com.google.firebase.auth.api.internal.IFirebaseAuthService");
    }

    /* renamed from: a */
    public final void mo3020a(String str, C1827o c1827o) {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) c1827o);
        transactAndReadExceptionReturnVoid(1, obtainAndWriteInterfaceToken);
    }
}
