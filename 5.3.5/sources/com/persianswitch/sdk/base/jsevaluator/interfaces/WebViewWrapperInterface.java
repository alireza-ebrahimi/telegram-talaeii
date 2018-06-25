package com.persianswitch.sdk.base.jsevaluator.interfaces;

import android.webkit.WebView;

public interface WebViewWrapperInterface {
    void destroy();

    WebView getWebView();

    void loadJavaScript(String str);
}
