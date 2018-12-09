package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

final class zzer<T> {
    private final Map<T, zzhk<T>> zzeb = new HashMap();

    zzer() {
    }

    public final void zza(IBinder iBinder) {
        synchronized (this.zzeb) {
            zzep zzep;
            if (iBinder == null) {
                zzep = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wearable.internal.IWearableService");
                if (queryLocalInterface instanceof zzep) {
                    zzep = (zzep) queryLocalInterface;
                } else {
                    Object zzeq = new zzeq(iBinder);
                }
            }
            zzek zzgz = new zzgz();
            for (Entry entry : this.zzeb.entrySet()) {
                zzhk zzhk = (zzhk) entry.getValue();
                try {
                    zzep.zza(zzgz, new zzd(zzhk));
                    if (Log.isLoggable("WearableClient", 3)) {
                        String valueOf = String.valueOf(entry.getKey());
                        String valueOf2 = String.valueOf(zzhk);
                        Log.d("WearableClient", new StringBuilder((String.valueOf(valueOf).length() + 27) + String.valueOf(valueOf2).length()).append("onPostInitHandler: added: ").append(valueOf).append("/").append(valueOf2).toString());
                    }
                } catch (RemoteException e) {
                    String valueOf3 = String.valueOf(entry.getKey());
                    String valueOf4 = String.valueOf(zzhk);
                    Log.w("WearableClient", new StringBuilder((String.valueOf(valueOf3).length() + 32) + String.valueOf(valueOf4).length()).append("onPostInitHandler: Didn't add: ").append(valueOf3).append("/").append(valueOf4).toString());
                }
            }
        }
    }

    public final void zza(zzhg zzhg, ResultHolder<Status> resultHolder, T t) {
        synchronized (this.zzeb) {
            zzhk zzhk = (zzhk) this.zzeb.remove(t);
            if (zzhk == null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String valueOf = String.valueOf(t);
                    Log.v("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 25).append("remove Listener unknown: ").append(valueOf).toString());
                }
                resultHolder.setResult(new Status(WearableStatusCodes.UNKNOWN_LISTENER));
                return;
            }
            zzhk.clear();
            if (Log.isLoggable("WearableClient", 2)) {
                String valueOf2 = String.valueOf(t);
                Log.v("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 24).append("service.removeListener: ").append(valueOf2).toString());
            }
            ((zzep) zzhg.getService()).zza(new zzet(this.zzeb, t, resultHolder), new zzfw(zzhk));
        }
    }

    public final void zza(zzhg zzhg, ResultHolder<Status> resultHolder, T t, zzhk<T> zzhk) {
        synchronized (this.zzeb) {
            if (this.zzeb.get(t) != null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String valueOf = String.valueOf(t);
                    Log.v("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 20).append("duplicate listener: ").append(valueOf).toString());
                }
                resultHolder.setResult(new Status(WearableStatusCodes.DUPLICATE_LISTENER));
                return;
            }
            if (Log.isLoggable("WearableClient", 2)) {
                valueOf = String.valueOf(t);
                Log.v("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 14).append("new listener: ").append(valueOf).toString());
            }
            this.zzeb.put(t, zzhk);
            try {
                ((zzep) zzhg.getService()).zza(new zzes(this.zzeb, t, resultHolder), new zzd(zzhk));
            } catch (RemoteException e) {
                if (Log.isLoggable("WearableClient", 3)) {
                    String valueOf2 = String.valueOf(t);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 39).append("addListener failed, removing listener: ").append(valueOf2).toString());
                }
                this.zzeb.remove(t);
                throw e;
            }
        }
    }
}
