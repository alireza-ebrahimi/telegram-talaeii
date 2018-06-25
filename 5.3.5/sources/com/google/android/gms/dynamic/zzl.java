package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzl extends zzew implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        IObjectWrapper iObjectWrapper = null;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        IInterface zzarh;
        int id;
        boolean retainInstance;
        IBinder readStrongBinder;
        switch (i) {
            case 2:
                zzarh = zzarh();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzarh);
                break;
            case 3:
                Parcelable arguments = getArguments();
                parcel2.writeNoException();
                zzex.zzb(parcel2, arguments);
                break;
            case 4:
                id = getId();
                parcel2.writeNoException();
                parcel2.writeInt(id);
                break;
            case 5:
                zzarh = zzari();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzarh);
                break;
            case 6:
                zzarh = zzarj();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzarh);
                break;
            case 7:
                retainInstance = getRetainInstance();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 8:
                String tag = getTag();
                parcel2.writeNoException();
                parcel2.writeString(tag);
                break;
            case 9:
                zzarh = zzark();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzarh);
                break;
            case 10:
                id = getTargetRequestCode();
                parcel2.writeNoException();
                parcel2.writeInt(id);
                break;
            case 11:
                retainInstance = getUserVisibleHint();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 12:
                zzarh = getView();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzarh);
                break;
            case 13:
                retainInstance = isAdded();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 14:
                retainInstance = isDetached();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 15:
                retainInstance = isHidden();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 16:
                retainInstance = isInLayout();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 17:
                retainInstance = isRemoving();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 18:
                retainInstance = isResumed();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 19:
                retainInstance = isVisible();
                parcel2.writeNoException();
                zzex.zza(parcel2, retainInstance);
                break;
            case 20:
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    zzarh = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = zzarh instanceof IObjectWrapper ? (IObjectWrapper) zzarh : new zzm(readStrongBinder);
                }
                zzw(iObjectWrapper);
                parcel2.writeNoException();
                break;
            case 21:
                setHasOptionsMenu(zzex.zza(parcel));
                parcel2.writeNoException();
                break;
            case 22:
                setMenuVisibility(zzex.zza(parcel));
                parcel2.writeNoException();
                break;
            case 23:
                setRetainInstance(zzex.zza(parcel));
                parcel2.writeNoException();
                break;
            case 24:
                setUserVisibleHint(zzex.zza(parcel));
                parcel2.writeNoException();
                break;
            case 25:
                startActivity((Intent) zzex.zza(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                break;
            case 26:
                startActivityForResult((Intent) zzex.zza(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                break;
            case 27:
                readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    zzarh = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = zzarh instanceof IObjectWrapper ? (IObjectWrapper) zzarh : new zzm(readStrongBinder);
                }
                zzx(iObjectWrapper);
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
