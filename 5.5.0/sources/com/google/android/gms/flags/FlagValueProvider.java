package com.google.android.gms.flags;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;
import com.google.android.gms.flags.IFlagProvider.Stub;

public class FlagValueProvider {
    private boolean zzacf = false;
    private IFlagProvider zzacg = null;

    public <T> T getFlagValue(Flag<T> flag) {
        synchronized (this) {
            if (this.zzacf) {
                return flag.get(this.zzacg);
            }
            T t = flag.getDefault();
            return t;
        }
    }

    public void initialize(Context context) {
        Throwable e;
        synchronized (this) {
            if (this.zzacf) {
                return;
            }
            try {
                this.zzacg = Stub.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_REMOTE_VERSION_NO_FORCE_STAGING, ModuleDescriptor.MODULE_ID).instantiate("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.zzacg.init(ObjectWrapper.wrap(context));
                this.zzacf = true;
            } catch (LoadingException e2) {
                e = e2;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
            } catch (RemoteException e3) {
                e = e3;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
            }
        }
    }
}
