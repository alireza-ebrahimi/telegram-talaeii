package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;

@Hide
public final class zzccl {
    private boolean zzarf = false;
    private zzccm zzhqe = null;

    public final void initialize(Context context) {
        Throwable e;
        synchronized (this) {
            if (this.zzarf) {
                return;
            }
            try {
                this.zzhqe = zzccn.asInterface(DynamiteModule.zza(context, DynamiteModule.zzhdn, ModuleDescriptor.MODULE_ID).zzhk("com.google.android.gms.flags.impl.FlagProviderImpl"));
                this.zzhqe.init(zzn.zzz(context));
                this.zzarf = true;
            } catch (zzc e2) {
                e = e2;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
            } catch (RemoteException e3) {
                e = e3;
                Log.w("FlagValueProvider", "Failed to initialize flags module.", e);
            }
        }
    }

    public final <T> T zzb(zzcce<T> zzcce) {
        synchronized (this) {
            if (this.zzarf) {
                return zzcce.zza(this.zzhqe);
            }
            T zzje = zzcce.zzje();
            return zzje;
        }
    }
}
