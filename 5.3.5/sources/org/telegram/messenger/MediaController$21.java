package org.telegram.messenger;

class MediaController$21 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$21(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        try {
            if (!(MediaController.access$6500(this.this$0) == null || MediaController.access$6500(this.this$0).audioProgress == 0.0f)) {
                MediaController.access$3102(this.this$0, (long) (((float) MediaController.access$3200(this.this$0)) * MediaController.access$6500(this.this$0).audioProgress));
                MediaController.access$5100(this.this$0, MediaController.access$6500(this.this$0).audioProgress);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        synchronized (MediaController.access$3800(this.this$0)) {
            MediaController.access$3900(this.this$0).addAll(MediaController.access$4000(this.this$0));
            MediaController.access$4000(this.this$0).clear();
        }
        MediaController.access$3602(this.this$0, false);
        MediaController.access$3700(this.this$0);
    }
}
