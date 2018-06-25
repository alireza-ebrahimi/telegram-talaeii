package com.persianswitch.sdk.base.jsevaluator;

import android.os.Handler;
import com.persianswitch.sdk.base.jsevaluator.interfaces.HandlerWrapperInterface;

public class HandlerWrapper implements HandlerWrapperInterface {
    private final Handler mHandler = new Handler();

    public void post(Runnable r) {
        this.mHandler.post(r);
    }
}
