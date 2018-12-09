package com.persianswitch.sdk.base.jsevaluator;

import android.webkit.JavascriptInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;

public class JavaScriptInterface {
    /* renamed from: a */
    private final CallJavaResultInterface f7057a;

    public JavaScriptInterface(CallJavaResultInterface callJavaResultInterface) {
        this.f7057a = callJavaResultInterface;
    }

    @JavascriptInterface
    public void returnResultToJava(String str, int i) {
        this.f7057a.mo3247a(str, Integer.valueOf(i));
    }
}
