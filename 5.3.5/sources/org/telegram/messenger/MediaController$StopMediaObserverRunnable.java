package org.telegram.messenger;

final class MediaController$StopMediaObserverRunnable implements Runnable {
    public int currentObserverToken;
    final /* synthetic */ MediaController this$0;

    private MediaController$StopMediaObserverRunnable(MediaController mediaController) {
        this.this$0 = mediaController;
        this.currentObserverToken = 0;
    }

    public void run() {
        if (this.currentObserverToken == MediaController.access$1800(this.this$0)) {
            try {
                if (MediaController.access$1900(this.this$0) != null) {
                    ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.access$1900(this.this$0));
                    MediaController.access$1902(this.this$0, null);
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                if (MediaController.access$2000(this.this$0) != null) {
                    ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.access$2000(this.this$0));
                    MediaController.access$2002(this.this$0, null);
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }
}
