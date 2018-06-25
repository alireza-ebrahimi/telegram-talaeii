package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class zzf implements zzt {
    private final Map<String, List<zzr<?>>> zzp = new HashMap();
    private final zzd zzq;

    zzf(zzd zzd) {
        this.zzq = zzd;
    }

    private final synchronized boolean zzb(zzr<?> zzr) {
        boolean z = false;
        synchronized (this) {
            String url = zzr.getUrl();
            if (this.zzp.containsKey(url)) {
                List list = (List) this.zzp.get(url);
                if (list == null) {
                    list = new ArrayList();
                }
                zzr.zzb("waiting-for-response");
                list.add(zzr);
                this.zzp.put(url, list);
                if (zzaf.DEBUG) {
                    zzaf.zzb("Request for cacheKey=%s is in flight, putting on hold.", url);
                }
                z = true;
            } else {
                this.zzp.put(url, null);
                zzr.zza((zzt) this);
                if (zzaf.DEBUG) {
                    zzaf.zzb("new request, sending to network %s", url);
                }
            }
        }
        return z;
    }

    public final synchronized void zza(zzr<?> zzr) {
        String url = zzr.getUrl();
        List list = (List) this.zzp.remove(url);
        if (!(list == null || list.isEmpty())) {
            if (zzaf.DEBUG) {
                zzaf.zza("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(list.size()), url);
            }
            zzr zzr2 = (zzr) list.remove(0);
            this.zzp.put(url, list);
            zzr2.zza((zzt) this);
            try {
                this.zzq.zzi.put(zzr2);
            } catch (InterruptedException e) {
                zzaf.zzc("Couldn't add request to queue. %s", e.toString());
                Thread.currentThread().interrupt();
                this.zzq.quit();
            }
        }
    }

    public final void zza(zzr<?> zzr, zzx<?> zzx) {
        if (zzx.zzbg == null || zzx.zzbg.zza()) {
            zza(zzr);
            return;
        }
        String url = zzr.getUrl();
        synchronized (this) {
            List<zzr> list = (List) this.zzp.remove(url);
        }
        if (list != null) {
            if (zzaf.DEBUG) {
                zzaf.zza("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(list.size()), url);
            }
            for (zzr zzb : list) {
                this.zzq.zzk.zzb(zzb, zzx);
            }
        }
    }
}
