package com.persianswitch.sdk.base.jsevaluator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.WebViewWrapperInterface;
import java.io.UnsupportedEncodingException;

@SuppressLint({"SetJavaScriptEnabled"})
public class WebViewWrapper implements WebViewWrapperInterface {
    protected WebView mWebView;

    public WebViewWrapper(Context context, CallJavaResultInterface callJavaResult) {
        this.mWebView = new WebView(context);
        this.mWebView.setWillNotDraw(true);
        WebSettings webSettings = this.mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        this.mWebView.addJavascriptInterface(new JavaScriptInterface(callJavaResult), JsEvaluator.JS_NAMESPACE);
    }

    public void loadJavaScript(String javascript) {
        try {
            this.mWebView.loadUrl("data:text/html;charset=utf-8;base64," + Base64.encodeToString(("<script>" + javascript + "</script>").getBytes("UTF-8"), 0));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (this.mWebView != null) {
            this.mWebView.removeJavascriptInterface(JsEvaluator.JS_NAMESPACE);
            this.mWebView.loadUrl("about:blank");
            this.mWebView.stopLoading();
            if (VERSION.SDK_INT < 19) {
                this.mWebView.freeMemory();
            }
            this.mWebView.clearHistory();
            this.mWebView.removeAllViews();
            this.mWebView.destroyDrawingCache();
            this.mWebView.destroy();
            this.mWebView = null;
        }
    }

    public WebView getWebView() {
        return this.mWebView;
    }
}
