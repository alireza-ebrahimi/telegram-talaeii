package com.persianswitch.sdk.base.log;

import android.support.annotation.NonNull;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class LogReporter {
    private static LogReporter instance;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean started = false;

    /* renamed from: com.persianswitch.sdk.base.log.LogReporter$1 */
    class C07751 implements Runnable {
        C07751() {
        }

        public void run() {
        }
    }

    public static LogReporter getInstance() {
        if (instance == null) {
            instance = new LogReporter();
        }
        return instance;
    }

    private LogReporter() {
        if (instance != null) {
            throw new InstantiationError();
        }
    }

    public synchronized void startReporter() {
        if (!this.started) {
            this.scheduler.scheduleAtFixedRate(reportTask(), 0, 30, TimeUnit.SECONDS);
        }
        this.started = true;
    }

    public synchronized void stopReporter() {
        this.scheduler.shutdown();
        this.started = false;
    }

    @NonNull
    private Runnable reportTask() {
        return new C07751();
    }
}
