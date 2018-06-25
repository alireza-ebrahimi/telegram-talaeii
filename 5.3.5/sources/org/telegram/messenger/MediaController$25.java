package org.telegram.messenger;

import android.os.Vibrator;

class MediaController$25 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ int val$send;

    /* renamed from: org.telegram.messenger.MediaController$25$1 */
    class C14271 implements Runnable {
        C14271() {
        }

        public void run() {
            int i = 1;
            NotificationCenter instance = NotificationCenter.getInstance();
            int i2 = NotificationCenter.recordStopped;
            Object[] objArr = new Object[1];
            if (MediaController$25.this.val$send != 2) {
                i = 0;
            }
            objArr[0] = Integer.valueOf(i);
            instance.postNotificationName(i2, objArr);
        }
    }

    MediaController$25(MediaController this$0, int i) {
        this.this$0 = this$0;
        this.val$send = i;
    }

    public void run() {
        if (MediaController.access$000(this.this$0) != null) {
            try {
                MediaController.access$1202(this.this$0, this.val$send);
                MediaController.access$000(this.this$0).stop();
            } catch (Exception e) {
                FileLog.e(e);
                if (MediaController.access$6600(this.this$0) != null) {
                    MediaController.access$6600(this.this$0).delete();
                }
            }
            if (this.val$send == 0) {
                MediaController.access$1300(this.this$0, 0);
            }
            try {
                ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(10);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            AndroidUtilities.runOnUIThread(new C14271());
        }
    }
}
