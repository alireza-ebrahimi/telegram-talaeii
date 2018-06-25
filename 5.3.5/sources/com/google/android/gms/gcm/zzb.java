package com.google.android.gms.gcm;

import android.support.annotation.NonNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class zzb implements ThreadFactory {
    private final AtomicInteger zzijw = new AtomicInteger(1);

    zzb(GcmTaskService gcmTaskService) {
    }

    public final Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable, "gcm-task#" + this.zzijw.getAndIncrement());
        thread.setPriority(4);
        return thread;
    }
}
