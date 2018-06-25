package org.telegram.messenger;

class MediaController$13 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$13(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (MediaController.access$4800(this.this$0) != null) {
            MediaController.access$4700(this.this$0).unregisterListener(this.this$0, MediaController.access$4800(this.this$0));
        }
        if (MediaController.access$4600(this.this$0) != null) {
            MediaController.access$4700(this.this$0).unregisterListener(this.this$0, MediaController.access$4600(this.this$0));
        }
        if (MediaController.access$4900(this.this$0) != null) {
            MediaController.access$4700(this.this$0).unregisterListener(this.this$0, MediaController.access$4900(this.this$0));
        }
        MediaController.access$4700(this.this$0).unregisterListener(this.this$0, MediaController.access$5000(this.this$0));
    }
}
