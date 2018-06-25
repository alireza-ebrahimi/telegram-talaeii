package org.telegram.messenger;

class SendMessagesHelper$20 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ String val$text;

    /* renamed from: org.telegram.messenger.SendMessagesHelper$20$1 */
    class C16451 implements Runnable {

        /* renamed from: org.telegram.messenger.SendMessagesHelper$20$1$1 */
        class C16441 implements Runnable {
            C16441() {
            }

            public void run() {
                String textFinal = SendMessagesHelper.access$1600(SendMessagesHelper$20.this.val$text);
                if (textFinal.length() != 0) {
                    int count = (int) Math.ceil((double) (((float) textFinal.length()) / 4096.0f));
                    for (int a = 0; a < count; a++) {
                        SendMessagesHelper.getInstance().sendMessage(textFinal.substring(a * 4096, Math.min((a + 1) * 4096, textFinal.length())), SendMessagesHelper$20.this.val$dialog_id, null, null, true, null, null, null);
                    }
                }
            }
        }

        C16451() {
        }

        public void run() {
            AndroidUtilities.runOnUIThread(new C16441());
        }
    }

    SendMessagesHelper$20(String str, long j) {
        this.val$text = str;
        this.val$dialog_id = j;
    }

    public void run() {
        Utilities.stageQueue.postRunnable(new C16451());
    }
}
