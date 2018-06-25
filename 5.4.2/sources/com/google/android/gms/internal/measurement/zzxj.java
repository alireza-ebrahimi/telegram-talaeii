package com.google.android.gms.internal.measurement;

import java.io.PrintStream;
import java.util.List;

final class zzxj extends zzxg {
    private final zzxh zzboo = new zzxh();

    zzxj() {
    }

    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        List<Throwable> zza = this.zzboo.zza(th, false);
        if (zza != null) {
            synchronized (zza) {
                for (Throwable th2 : zza) {
                    printStream.print("Suppressed: ");
                    th2.printStackTrace(printStream);
                }
            }
        }
    }
}
