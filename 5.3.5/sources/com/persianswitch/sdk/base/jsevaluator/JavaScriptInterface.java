package com.persianswitch.sdk.base.jsevaluator;

import android.webkit.JavascriptInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;

public class JavaScriptInterface {
    private final CallJavaResultInterface mCallJavaResultInterface;

    public JavaScriptInterface(CallJavaResultInterface callJavaResult) {
        this.mCallJavaResultInterface = callJavaResult;
    }

    @JavascriptInterface
    public void returnResultToJava(String value, int callIndex) {
        this.mCallJavaResultInterface.jsCallFinished(value, Integer.valueOf(callIndex));
    }
}
