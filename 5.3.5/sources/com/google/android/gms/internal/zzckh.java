package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

final class zzckh<V> extends FutureTask<V> implements Comparable<zzckh> {
    private final String zzjnl;
    private /* synthetic */ zzcke zzjnm;
    private final long zzjnn = zzcke.zzjnk.getAndIncrement();
    final boolean zzjno;

    zzckh(zzcke zzcke, Runnable runnable, boolean z, String str) {
        this.zzjnm = zzcke;
        super(runnable, null);
        zzbq.checkNotNull(str);
        this.zzjnl = str;
        this.zzjno = false;
        if (this.zzjnn == Long.MAX_VALUE) {
            zzcke.zzayp().zzbau().log("Tasks index overflow");
        }
    }

    zzckh(zzcke zzcke, Callable<V> callable, boolean z, String str) {
        this.zzjnm = zzcke;
        super(callable);
        zzbq.checkNotNull(str);
        this.zzjnl = str;
        this.zzjno = z;
        if (this.zzjnn == Long.MAX_VALUE) {
            zzcke.zzayp().zzbau().log("Tasks index overflow");
        }
    }

    public final /* synthetic */ int compareTo(@NonNull Object obj) {
        zzckh zzckh = (zzckh) obj;
        if (this.zzjno != zzckh.zzjno) {
            return this.zzjno ? -1 : 1;
        } else {
            if (this.zzjnn < zzckh.zzjnn) {
                return -1;
            }
            if (this.zzjnn > zzckh.zzjnn) {
                return 1;
            }
            this.zzjnm.zzayp().zzbav().zzj("Two tasks share the same index. index", Long.valueOf(this.zzjnn));
            return 0;
        }
    }

    protected final void setException(Throwable th) {
        this.zzjnm.zzayp().zzbau().zzj(this.zzjnl, th);
        if (th instanceof zzckf) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
