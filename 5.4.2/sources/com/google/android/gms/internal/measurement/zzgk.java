package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

final class zzgk<V> extends FutureTask<V> implements Comparable<zzgk> {
    private final String zzamh;
    private final /* synthetic */ zzgh zzami;
    private final long zzamj = zzgh.zzamg.getAndIncrement();
    final boolean zzamk;

    zzgk(zzgh zzgh, Runnable runnable, boolean z, String str) {
        this.zzami = zzgh;
        super(runnable, null);
        Preconditions.checkNotNull(str);
        this.zzamh = str;
        this.zzamk = false;
        if (this.zzamj == Long.MAX_VALUE) {
            zzgh.zzgf().zzis().log("Tasks index overflow");
        }
    }

    zzgk(zzgh zzgh, Callable<V> callable, boolean z, String str) {
        this.zzami = zzgh;
        super(callable);
        Preconditions.checkNotNull(str);
        this.zzamh = str;
        this.zzamk = z;
        if (this.zzamj == Long.MAX_VALUE) {
            zzgh.zzgf().zzis().log("Tasks index overflow");
        }
    }

    public final /* synthetic */ int compareTo(Object obj) {
        zzgk zzgk = (zzgk) obj;
        if (this.zzamk != zzgk.zzamk) {
            return this.zzamk ? -1 : 1;
        } else {
            if (this.zzamj < zzgk.zzamj) {
                return -1;
            }
            if (this.zzamj > zzgk.zzamj) {
                return 1;
            }
            this.zzami.zzgf().zzit().zzg("Two tasks share the same index. index", Long.valueOf(this.zzamj));
            return 0;
        }
    }

    protected final void setException(Throwable th) {
        this.zzami.zzgf().zzis().zzg(this.zzamh, th);
        if (th instanceof zzgi) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
