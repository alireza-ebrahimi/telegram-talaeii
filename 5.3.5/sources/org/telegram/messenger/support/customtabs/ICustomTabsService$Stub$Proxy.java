package org.telegram.messenger.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

class ICustomTabsService$Stub$Proxy implements ICustomTabsService {
    private IBinder mRemote;

    ICustomTabsService$Stub$Proxy(IBinder remote) {
        this.mRemote = remote;
    }

    public IBinder asBinder() {
        return this.mRemote;
    }

    public String getInterfaceDescriptor() {
        return "android.support.customtabs.ICustomTabsService";
    }

    public boolean warmup(long flags) throws RemoteException {
        boolean _result = false;
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeLong(flags);
            this.mRemote.transact(2, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() != 0) {
                _result = true;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public boolean newSession(ICustomTabsCallback callback) throws RemoteException {
        boolean _result = false;
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
            this.mRemote.transact(3, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() != 0) {
                _result = true;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public boolean mayLaunchUrl(ICustomTabsCallback callback, Uri url, Bundle extras, List<Bundle> otherLikelyBundles) throws RemoteException {
        boolean _result = true;
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
            if (url != null) {
                _data.writeInt(1);
                url.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            if (extras != null) {
                _data.writeInt(1);
                extras.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            _data.writeTypedList(otherLikelyBundles);
            this.mRemote.transact(4, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() == 0) {
                _result = false;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public Bundle extraCommand(String commandName, Bundle args) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            Bundle _result;
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeString(commandName);
            if (args != null) {
                _data.writeInt(1);
                args.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            this.mRemote.transact(5, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() != 0) {
                _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
            } else {
                _result = null;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public boolean updateVisuals(ICustomTabsCallback callback, Bundle bundle) throws RemoteException {
        boolean _result = true;
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
            if (bundle != null) {
                _data.writeInt(1);
                bundle.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            this.mRemote.transact(6, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() == 0) {
                _result = false;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public boolean requestPostMessageChannel(ICustomTabsCallback callback, Uri postMessageOrigin) throws RemoteException {
        boolean _result = true;
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
            if (postMessageOrigin != null) {
                _data.writeInt(1);
                postMessageOrigin.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            this.mRemote.transact(7, _data, _reply, 0);
            _reply.readException();
            if (_reply.readInt() == 0) {
                _result = false;
            }
            _reply.recycle();
            _data.recycle();
            return _result;
        } catch (Throwable th) {
            _reply.recycle();
            _data.recycle();
        }
    }

    public int postMessage(ICustomTabsCallback callback, String message, Bundle extras) throws RemoteException {
        Parcel _data = Parcel.obtain();
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken("android.support.customtabs.ICustomTabsService");
            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
            _data.writeString(message);
            if (extras != null) {
                _data.writeInt(1);
                extras.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            this.mRemote.transact(8, _data, _reply, 0);
            _reply.readException();
            int _result = _reply.readInt();
            return _result;
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }
}
