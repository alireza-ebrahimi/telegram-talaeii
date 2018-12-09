package com.google.android.gms.dynamic;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.stable.zza;
import com.google.android.gms.internal.stable.zzb;

public interface IObjectWrapper extends IInterface {

    public static abstract class Stub extends zzb implements IObjectWrapper {

        public static class Proxy extends zza implements IObjectWrapper {
            Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.dynamic.IObjectWrapper");
            }
        }

        public Stub() {
            super("com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static IObjectWrapper asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            return queryLocalInterface instanceof IObjectWrapper ? (IObjectWrapper) queryLocalInterface : new Proxy(iBinder);
        }
    }
}
