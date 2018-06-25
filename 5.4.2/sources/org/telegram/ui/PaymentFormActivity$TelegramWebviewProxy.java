package org.telegram.ui;

import android.webkit.JavascriptInterface;
import org.telegram.messenger.AndroidUtilities;

class PaymentFormActivity$TelegramWebviewProxy {
    final /* synthetic */ PaymentFormActivity this$0;

    private PaymentFormActivity$TelegramWebviewProxy(PaymentFormActivity paymentFormActivity) {
        this.this$0 = paymentFormActivity;
    }

    @JavascriptInterface
    public void postEvent(String str, String str2) {
        AndroidUtilities.runOnUIThread(new PaymentFormActivity$TelegramWebviewProxy$1(this, str, str2));
    }
}
