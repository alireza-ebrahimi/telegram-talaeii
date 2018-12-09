package org.telegram.ui;

import org.json.JSONObject;
import org.telegram.messenger.FileLog;

class PaymentFormActivity$TelegramWebviewProxy$1 implements Runnable {
    final /* synthetic */ PaymentFormActivity$TelegramWebviewProxy this$1;
    final /* synthetic */ String val$eventData;
    final /* synthetic */ String val$eventName;

    PaymentFormActivity$TelegramWebviewProxy$1(PaymentFormActivity$TelegramWebviewProxy paymentFormActivity$TelegramWebviewProxy, String str, String str2) {
        this.this$1 = paymentFormActivity$TelegramWebviewProxy;
        this.val$eventName = str;
        this.val$eventData = str2;
    }

    public void run() {
        if (this.this$1.this$0.getParentActivity() != null && this.val$eventName.equals("payment_form_submit")) {
            try {
                JSONObject jSONObject = new JSONObject(this.val$eventData);
                this.this$1.this$0.paymentJson = jSONObject.getJSONObject("credentials").toString();
                this.this$1.this$0.cardName = jSONObject.getString("title");
            } catch (Throwable th) {
                this.this$1.this$0.paymentJson = this.val$eventData;
                FileLog.e(th);
            }
            this.this$1.this$0.goToNextStep();
        }
    }
}
