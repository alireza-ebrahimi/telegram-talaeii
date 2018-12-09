package com.google.android.gms.common.util.concurrent;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public class HandlerExecutor implements Executor {
    private final Handler handler;

    public HandlerExecutor(Handler handler) {
        this(handler.getLooper());
    }

    public HandlerExecutor(Looper looper) {
        this.handler = new Handler(looper);
    }

    public void execute(Runnable runnable) {
        this.handler.post(runnable);
    }
}
