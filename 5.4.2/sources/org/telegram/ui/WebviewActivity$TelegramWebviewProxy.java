package org.telegram.ui;

import android.webkit.JavascriptInterface;
import org.telegram.messenger.AndroidUtilities;

class WebviewActivity$TelegramWebviewProxy {
    final /* synthetic */ WebviewActivity this$0;

    private WebviewActivity$TelegramWebviewProxy(WebviewActivity webviewActivity) {
        this.this$0 = webviewActivity;
    }

    @JavascriptInterface
    public void postEvent(String str, String str2) {
        AndroidUtilities.runOnUIThread(new WebviewActivity$TelegramWebviewProxy$1(this, str));
    }
}
