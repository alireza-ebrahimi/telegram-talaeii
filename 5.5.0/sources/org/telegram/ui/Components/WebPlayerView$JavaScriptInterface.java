package org.telegram.ui.Components;

import android.webkit.JavascriptInterface;
import org.telegram.ui.Components.WebPlayerView.CallJavaResultInterface;

public class WebPlayerView$JavaScriptInterface {
    private final CallJavaResultInterface callJavaResultInterface;
    final /* synthetic */ WebPlayerView this$0;

    public WebPlayerView$JavaScriptInterface(WebPlayerView webPlayerView, CallJavaResultInterface callJavaResultInterface) {
        this.this$0 = webPlayerView;
        this.callJavaResultInterface = callJavaResultInterface;
    }

    @JavascriptInterface
    public void returnResultToJava(String str) {
        this.callJavaResultInterface.jsCallFinished(str);
    }
}
