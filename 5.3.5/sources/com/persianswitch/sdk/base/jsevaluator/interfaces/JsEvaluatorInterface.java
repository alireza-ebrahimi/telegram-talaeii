package com.persianswitch.sdk.base.jsevaluator.interfaces;

import android.webkit.WebView;

public interface JsEvaluatorInterface {
    void callFunction(String str, JsCallback jsCallback, String str2, Object... objArr);

    void destroy();

    void evaluate(String str);

    void evaluate(String str, JsCallback jsCallback);

    WebView getWebView();
}
