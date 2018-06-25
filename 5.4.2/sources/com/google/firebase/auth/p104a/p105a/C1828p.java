package com.google.firebase.auth.p104a.p105a;

import android.os.Parcel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzaj;
import com.google.android.gms.internal.firebase_auth.zzao;
import com.google.android.gms.internal.firebase_auth.zzav;
import com.google.android.gms.internal.firebase_auth.zzb;
import com.google.android.gms.internal.firebase_auth.zzc;
import com.google.android.gms.internal.firebase_auth.zzx;
import com.google.firebase.auth.PhoneAuthCredential;

/* renamed from: com.google.firebase.auth.a.a.p */
public abstract class C1828p extends zzb implements C1827o {
    public C1828p() {
        super("com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                mo3002a((zzao) zzc.zza(parcel, zzao.CREATOR));
                break;
            case 2:
                mo3003a((zzao) zzc.zza(parcel, zzao.CREATOR), (zzaj) zzc.zza(parcel, zzaj.CREATOR));
                break;
            case 3:
                mo3005a((zzx) zzc.zza(parcel, zzx.CREATOR));
                break;
            case 4:
                mo3004a((zzav) zzc.zza(parcel, zzav.CREATOR));
                break;
            case 5:
                mo3000a((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 6:
                mo2999a();
                break;
            case 7:
                mo3008b();
                break;
            case 8:
                mo3007a(parcel.readString());
                break;
            case 9:
                mo3009b(parcel.readString());
                break;
            case 10:
                mo3006a((PhoneAuthCredential) zzc.zza(parcel, PhoneAuthCredential.CREATOR));
                break;
            case 11:
                mo3011c(parcel.readString());
                break;
            case 12:
                mo3001a((Status) zzc.zza(parcel, Status.CREATOR), (PhoneAuthCredential) zzc.zza(parcel, PhoneAuthCredential.CREATOR));
                break;
            case 13:
                mo3010c();
                break;
            default:
                return false;
        }
        return true;
    }
}
