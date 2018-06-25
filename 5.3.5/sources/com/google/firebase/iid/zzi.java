package com.google.firebase.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.iid.zzj;

@Hide
public class zzi implements Parcelable {
    public static final Creator<zzi> CREATOR = new zzj();
    private Messenger zzinb;
    private com.google.android.gms.iid.zzi zzinc;

    public static final class zza extends ClassLoader {
        protected final Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
            if (!"com.google.android.gms.iid.MessengerCompat".equals(str)) {
                return super.loadClass(str, z);
            }
            if (FirebaseInstanceId.zzclf()) {
                Log.d("FirebaseInstanceId", "Using renamed FirebaseIidMessengerCompat class");
            }
            return zzi.class;
        }
    }

    public zzi(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.zzinb = new Messenger(iBinder);
        } else {
            this.zzinc = zzj.zzbc(iBinder);
        }
    }

    private final IBinder getBinder() {
        return this.zzinb != null ? this.zzinb.getBinder() : this.zzinc.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                z = getBinder().equals(((zzi) obj).getBinder());
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public int hashCode() {
        return getBinder().hashCode();
    }

    public final void send(Message message) throws RemoteException {
        if (this.zzinb != null) {
            this.zzinb.send(message);
        } else {
            this.zzinc.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzinb != null) {
            parcel.writeStrongBinder(this.zzinb.getBinder());
        } else {
            parcel.writeStrongBinder(this.zzinc.asBinder());
        }
    }
}
