package com.google.android.gms.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;

@Hide
public class MessengerCompat implements ReflectedParcelable {
    public static final Creator<MessengerCompat> CREATOR = new zzl();
    private Messenger zzinb;
    private zzi zzinc;

    public MessengerCompat(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.zzinb = new Messenger(iBinder);
            return;
        }
        zzi zzi;
        if (iBinder == null) {
            zzi = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
            zzi = queryLocalInterface instanceof zzi ? (zzi) queryLocalInterface : new zzk(iBinder);
        }
        this.zzinc = zzi;
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
                z = getBinder().equals(((MessengerCompat) obj).getBinder());
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
