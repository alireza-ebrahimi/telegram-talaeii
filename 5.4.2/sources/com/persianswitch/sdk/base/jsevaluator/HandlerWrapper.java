package com.persianswitch.sdk.base.jsevaluator;

import android.os.Handler;
import com.persianswitch.sdk.base.jsevaluator.interfaces.HandlerWrapperInterface;

public class HandlerWrapper implements HandlerWrapperInterface {
    /* renamed from: a */
    private final Handler f7056a = new Handler();

    /* renamed from: a */
    public void mo3246a(Runnable runnable) {
        this.f7056a.post(runnable);
    }
}
