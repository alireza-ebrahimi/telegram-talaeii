package org.telegram.messenger;

class MediaController$14 implements Runnable {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ float val$progress;

    /* renamed from: org.telegram.messenger.MediaController$14$1 */
    class C14171 implements Runnable {
        C14171() {
        }

        public void run() {
            if (!MediaController.access$2800(MediaController$14.this.this$0)) {
                MediaController.access$2902(MediaController$14.this.this$0, 3);
                MediaController.access$3102(MediaController$14.this.this$0, (long) (((float) MediaController.access$3200(MediaController$14.this.this$0)) * MediaController$14.this.val$progress));
                if (MediaController.access$2600(MediaController$14.this.this$0) != null) {
                    MediaController.access$2600(MediaController$14.this.this$0).play();
                }
                MediaController.access$3002(MediaController$14.this.this$0, (long) ((int) ((((float) MediaController.access$3200(MediaController$14.this.this$0)) / 48.0f) * MediaController$14.this.val$progress)));
                MediaController.access$3700(MediaController$14.this.this$0);
            }
        }
    }

    MediaController$14(MediaController this$0, float f) {
        this.this$0 = this$0;
        this.val$progress = f;
    }

    public void run() {
        MediaController.access$5100(this.this$0, this.val$progress);
        synchronized (MediaController.access$3800(this.this$0)) {
            MediaController.access$3900(this.this$0).addAll(MediaController.access$4000(this.this$0));
            MediaController.access$4000(this.this$0).clear();
        }
        AndroidUtilities.runOnUIThread(new C14171());
    }
}
