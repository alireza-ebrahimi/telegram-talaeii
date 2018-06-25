package org.telegram.messenger;

class MessagesController$43 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ long val$did;

    /* renamed from: org.telegram.messenger.MessagesController$43$1 */
    class C14861 implements Runnable {
        C14861() {
        }

        public void run() {
            NotificationsController.getInstance().removeNotificationsForDialog(MessagesController$43.this.val$did);
        }
    }

    MessagesController$43(MessagesController this$0, long j) {
        this.this$0 = this$0;
        this.val$did = j;
    }

    public void run() {
        AndroidUtilities.runOnUIThread(new C14861());
    }
}
