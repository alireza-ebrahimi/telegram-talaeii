package org.telegram.ui;

import org.json.JSONObject;
import org.telegram.messenger.FileLog;

class PaymentFormActivity$TelegramWebviewProxy$1 implements Runnable {
    final /* synthetic */ PaymentFormActivity$TelegramWebviewProxy this$1;
    final /* synthetic */ String val$eventData;
    final /* synthetic */ String val$eventName;

    PaymentFormActivity$TelegramWebviewProxy$1(PaymentFormActivity$TelegramWebviewProxy this$1, String str, String str2) {
        this.this$1 = this$1;
        this.val$eventName = str;
        this.val$eventData = str2;
    }

    public void run() {
        if (this.this$1.this$0.getParentActivity() != null && this.val$eventName.equals("payment_form_submit")) {
            try {
                JSONObject jsonObject = new JSONObject(this.val$eventData);
                this.this$1.this$0.paymentJson = jsonObject.getJSONObject("credentials").toString();
                this.this$1.this$0.cardName = jsonObject.getString("title");
            } catch (Throwable e) {
                this.this$1.this$0.paymentJson = this.val$eventData;
                FileLog.e(e);
            }
            this.this$1.this$0.goToNextStep();
        }
    }
}
