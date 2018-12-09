package com.google.android.gms.iid;

import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;

public class MessengerCompat implements ReflectedParcelable {
    public static final Creator<MessengerCompat> CREATOR = new zzq();
    private Messenger zzab;
    private zzl zzby;

    public MessengerCompat(IBinder iBinder) {
        if (VERSION.SDK_INT >= 21) {
            this.zzab = new Messenger(iBinder);
            return;
        }
        zzl zzl;
        if (iBinder == null) {
            zzl = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.iid.IMessengerCompat");
            zzl = queryLocalInterface instanceof zzl ? (zzl) queryLocalInterface : new zzm(iBinder);
        }
        this.zzby = zzl;
    }

    private final IBinder getBinder() {
        return this.zzab != null ? this.zzab.getBinder() : this.zzby.asBinder();
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

    public final void send(Message message) {
        if (this.zzab != null) {
            this.zzab.send(message);
        } else {
            this.zzby.send(message);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zzab != null) {
            parcel.writeStrongBinder(this.zzab.getBinder());
        } else {
            parcel.writeStrongBinder(this.zzby.asBinder());
        }
    }
}
