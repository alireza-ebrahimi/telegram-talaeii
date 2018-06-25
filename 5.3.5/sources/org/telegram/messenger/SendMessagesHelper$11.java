package org.telegram.messenger;

class SendMessagesHelper$11 implements Runnable {
    final /* synthetic */ SendMessagesHelper this$0;
    final /* synthetic */ String val$path;

    /* renamed from: org.telegram.messenger.SendMessagesHelper$11$1 */
    class C16251 implements Runnable {
        C16251() {
        }

        public void run() {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.stopEncodingService, new Object[]{SendMessagesHelper$11.this.val$path});
        }
    }

    SendMessagesHelper$11(SendMessagesHelper this$0, String str) {
        this.this$0 = this$0;
        this.val$path = str;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C16251());
    }
}
