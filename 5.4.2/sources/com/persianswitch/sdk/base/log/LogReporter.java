package com.persianswitch.sdk.base.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class LogReporter {
    /* renamed from: a */
    private static LogReporter f7066a;
    /* renamed from: b */
    private final ScheduledExecutorService f7067b = Executors.newScheduledThreadPool(1);
    /* renamed from: c */
    private boolean f7068c = false;

    /* renamed from: com.persianswitch.sdk.base.log.LogReporter$1 */
    class C22671 implements Runnable {
        public void run() {
        }
    }

    private LogReporter() {
        if (f7066a != null) {
            throw new InstantiationError();
        }
    }
}
