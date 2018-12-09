package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.internal.firebase_messaging.zze;
import com.google.android.gms.internal.firebase_messaging.zzf;

public class zzi implements Parcelable {
    public static final Creator<zzi> CREATOR = new ad();
    /* renamed from: a */
    private Messenger f5775a;
    /* renamed from: b */
    private zze f5776b;

    /* renamed from: com.google.firebase.iid.zzi$a */
    public static final class C1954a extends ClassLoader {
        protected final Class<?> loadClass(String str, boolean z) {
            if (!"com.google.android.gms.iid.MessengerCompat".equals(str)) {
                return super.loadClass(str, z);
            }
            if (FirebaseInstanceId.m8760g()) {
                Log.d("FirebaseInstanceId", "Using renamed FirebaseIidMessengerCompat class");
            }
            return zzi.class;
        }
    }

    public zzi(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.f5775a = new Messenger(iBinder);
        } else {
            this.f5776b = zzf.zza(iBinder);
        }
    }

    /* renamed from: a */
    private final IBinder m8898a() {
        return this.f5775a != null ? this.f5775a.getBinder() : this.f5776b.asBinder();
    }

    /* renamed from: a */
    public final void m8899a(Message message) {
        if (this.f5775a != null) {
            this.f5775a.send(message);
        } else {
            this.f5776b.send(message);
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                z = m8898a().equals(((zzi) obj).m8898a());
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public int hashCode() {
        return m8898a().hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.f5775a != null) {
            parcel.writeStrongBinder(this.f5775a.getBinder());
        } else {
            parcel.writeStrongBinder(this.f5776b.asBinder());
        }
    }
}
