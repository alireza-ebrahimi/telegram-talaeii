package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzbid implements ThreadFactory {
    private final int mPriority;
    private final String zzgln;
    private final AtomicInteger zzglo;
    private final ThreadFactory zzglp;

    public zzbid(String str) {
        this(str, 0);
    }

    private zzbid(String str, int i) {
        this.zzglo = new AtomicInteger();
        this.zzglp = Executors.defaultThreadFactory();
        this.zzgln = (String) zzbq.checkNotNull(str, "Name must not be null");
        this.mPriority = 0;
    }

    public final Thread newThread(Runnable runnable) {
        Thread newThread = this.zzglp.newThread(new zzbie(runnable, 0));
        String str = this.zzgln;
        newThread.setName(new StringBuilder(String.valueOf(str).length() + 13).append(str).append("[").append(this.zzglo.getAndIncrement()).append("]").toString());
        return newThread;
    }
}
