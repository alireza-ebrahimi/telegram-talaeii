package org.telegram.messenger;

class MediaController$9 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$9(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (MediaController.access$3600(this.this$0)) {
            MediaController.access$3700(this.this$0);
            return;
        }
        boolean was = false;
        while (true) {
            MediaController$AudioBuffer buffer = null;
            synchronized (MediaController.access$3800(this.this$0)) {
                if (!MediaController.access$3900(this.this$0).isEmpty()) {
                    buffer = (MediaController$AudioBuffer) MediaController.access$3900(this.this$0).get(0);
                    MediaController.access$3900(this.this$0).remove(0);
                }
                if (!MediaController.access$4000(this.this$0).isEmpty()) {
                    was = true;
                }
            }
            if (buffer == null) {
                break;
            }
            MediaController.access$4200(this.this$0, buffer.buffer, MediaController.access$4100(this.this$0), MediaController.readArgs);
            buffer.size = MediaController.readArgs[0];
            buffer.pcmOffset = (long) MediaController.readArgs[1];
            buffer.finished = MediaController.readArgs[2];
            if (buffer.finished == 1) {
                MediaController.access$3602(this.this$0, true);
            }
            if (buffer.size == 0) {
                break;
            }
            buffer.buffer.rewind();
            buffer.buffer.get(buffer.bufferBytes);
            synchronized (MediaController.access$3800(this.this$0)) {
                MediaController.access$4000(this.this$0).add(buffer);
            }
            was = true;
        }
        synchronized (MediaController.access$3800(this.this$0)) {
            MediaController.access$3900(this.this$0).add(buffer);
        }
        if (was) {
            MediaController.access$3700(this.this$0);
        }
    }
}
