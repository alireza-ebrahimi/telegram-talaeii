package org.telegram.messenger;

class MediaController$VideoConvertRunnable implements Runnable {
    private MessageObject messageObject;

    private MediaController$VideoConvertRunnable(MessageObject message) {
        this.messageObject = message;
    }

    public void run() {
        MediaController.access$8100(MediaController.getInstance(), this.messageObject);
    }

    public static void runConversion(final MessageObject obj) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread th = new Thread(new MediaController$VideoConvertRunnable(obj), "VideoConvertRunnable");
                    th.start();
                    th.join();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }).start();
    }
}
