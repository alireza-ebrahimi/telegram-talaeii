package com.persianswitch.sdk.base.jsevaluator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.WebViewWrapperInterface;
import java.io.UnsupportedEncodingException;
import org.telegram.messenger.exoplayer2.C3446C;

@SuppressLint({"SetJavaScriptEnabled"})
public class WebViewWrapper implements WebViewWrapperInterface {
    /* renamed from: a */
    protected WebView f7065a;

    public WebViewWrapper(Context context, CallJavaResultInterface callJavaResultInterface) {
        this.f7065a = new WebView(context);
        this.f7065a.setWillNotDraw(true);
        WebSettings settings = this.f7065a.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        this.f7065a.addJavascriptInterface(new JavaScriptInterface(callJavaResultInterface), "evgeniiJsEvaluator");
    }

    /* renamed from: a */
    public void mo3248a(String str) {
        try {
            this.f7065a.loadUrl("data:text/html;charset=utf-8;base64," + Base64.encodeToString(("<script>" + str + "</script>").getBytes(C3446C.UTF8_NAME), 0));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
