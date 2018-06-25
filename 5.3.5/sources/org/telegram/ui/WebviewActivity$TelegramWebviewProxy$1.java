package org.telegram.ui;

import org.telegram.messenger.FileLog;
import org.telegram.ui.Components.ShareAlert;

class WebviewActivity$TelegramWebviewProxy$1 implements Runnable {
    final /* synthetic */ WebviewActivity$TelegramWebviewProxy this$1;
    final /* synthetic */ String val$eventName;

    WebviewActivity$TelegramWebviewProxy$1(WebviewActivity$TelegramWebviewProxy this$1, String str) {
        this.this$1 = this$1;
        this.val$eventName = str;
    }

    public void run() {
        if (this.this$1.this$0.getParentActivity() != null) {
            FileLog.e(this.val$eventName);
            String str = this.val$eventName;
            boolean z = true;
            switch (str.hashCode()) {
                case -1788360622:
                    if (str.equals("share_game")) {
                        z = false;
                        break;
                    }
                    break;
                case 406539826:
                    if (str.equals("share_score")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    this.this$1.this$0.currentMessageObject.messageOwner.with_my_score = false;
                    break;
                case true:
                    this.this$1.this$0.currentMessageObject.messageOwner.with_my_score = true;
                    break;
            }
            this.this$1.this$0.showDialog(ShareAlert.createShareAlert(this.this$1.this$0.getParentActivity(), this.this$1.this$0.currentMessageObject, null, false, this.this$1.this$0.linkToCopy, false));
        }
    }
}
