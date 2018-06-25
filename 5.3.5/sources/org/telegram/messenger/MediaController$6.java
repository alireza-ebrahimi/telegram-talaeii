package org.telegram.messenger;

import java.util.TimerTask;

class MediaController$6 extends TimerTask {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ MessageObject val$currentPlayingMessageObject;

    /* renamed from: org.telegram.messenger.MediaController$6$1 */
    class C14321 implements Runnable {
        C14321() {
        }

        public void run() {
            if (MediaController$6.this.val$currentPlayingMessageObject == null) {
                return;
            }
            if ((MediaController.access$2500(MediaController$6.this.this$0) != null || MediaController.access$2600(MediaController$6.this.this$0) != null || MediaController.access$2700(MediaController$6.this.this$0) != null) && !MediaController.access$2800(MediaController$6.this.this$0)) {
                try {
                    if (MediaController.access$2900(MediaController$6.this.this$0) != 0) {
                        MediaController.access$2910(MediaController$6.this.this$0);
                        return;
                    }
                    long progress;
                    float value;
                    if (MediaController.access$2700(MediaController$6.this.this$0) != null) {
                        progress = MediaController.access$2700(MediaController$6.this.this$0).getCurrentPosition();
                        value = ((float) MediaController.access$3000(MediaController$6.this.this$0)) / ((float) MediaController.access$2700(MediaController$6.this.this$0).getDuration());
                        if (progress <= MediaController.access$3000(MediaController$6.this.this$0) || value >= 1.0f) {
                            return;
                        }
                    } else if (MediaController.access$2500(MediaController$6.this.this$0) != null) {
                        progress = (long) MediaController.access$2500(MediaController$6.this.this$0).getCurrentPosition();
                        value = ((float) MediaController.access$3000(MediaController$6.this.this$0)) / ((float) MediaController.access$2500(MediaController$6.this.this$0).getDuration());
                        if (progress <= MediaController.access$3000(MediaController$6.this.this$0)) {
                            return;
                        }
                    } else {
                        progress = (long) ((int) (((float) MediaController.access$3100(MediaController$6.this.this$0)) / 48.0f));
                        value = ((float) MediaController.access$3100(MediaController$6.this.this$0)) / ((float) MediaController.access$3200(MediaController$6.this.this$0));
                        if (progress == MediaController.access$3000(MediaController$6.this.this$0)) {
                            return;
                        }
                    }
                    MediaController.access$3002(MediaController$6.this.this$0, progress);
                    MediaController$6.this.val$currentPlayingMessageObject.audioProgress = value;
                    MediaController$6.this.val$currentPlayingMessageObject.audioProgressSec = (int) (MediaController.access$3000(MediaController$6.this.this$0) / 1000);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, new Object[]{Integer.valueOf(MediaController$6.this.val$currentPlayingMessageObject.getId()), Float.valueOf(value)});
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    MediaController$6(MediaController this$0, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$currentPlayingMessageObject = messageObject;
    }

    public void run() {
        synchronized (MediaController.access$2400(this.this$0)) {
            AndroidUtilities.runOnUIThread(new C14321());
        }
    }
}
