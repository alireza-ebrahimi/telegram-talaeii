package org.telegram.ui.Components;

import android.webkit.JavascriptInterface;
import org.telegram.ui.Components.WebPlayerView.CallJavaResultInterface;

public class WebPlayerView$JavaScriptInterface {
    private final CallJavaResultInterface callJavaResultInterface;
    final /* synthetic */ WebPlayerView this$0;

    public WebPlayerView$JavaScriptInterface(WebPlayerView this$0, CallJavaResultInterface callJavaResult) {
        this.this$0 = this$0;
        this.callJavaResultInterface = callJavaResult;
    }

    @JavascriptInterface
    public void returnResultToJava(String value) {
        this.callJavaResultInterface.jsCallFinished(value);
    }
}
