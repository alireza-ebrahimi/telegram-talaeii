package org.telegram.messenger;

class MediaController$17 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$17(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.cleanupPlayer(true, true);
    }
}
