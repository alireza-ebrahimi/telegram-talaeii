package org.telegram.messenger;

import android.telephony.PhoneStateListener;
import org.telegram.ui.Components.EmbedBottomSheet;

class MediaController$5 extends PhoneStateListener {
    final /* synthetic */ MediaController this$0;

    MediaController$5(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void onCallStateChanged(final int state, String incomingNumber) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                EmbedBottomSheet embedBottomSheet;
                if (state == 1) {
                    if (MediaController$5.this.this$0.isPlayingMessage(MediaController$5.this.this$0.getPlayingMessageObject()) && !MediaController$5.this.this$0.isMessagePaused()) {
                        MediaController$5.this.this$0.pauseMessage(MediaController$5.this.this$0.getPlayingMessageObject());
                    } else if (!(MediaController.access$2100(MediaController$5.this.this$0) == null && MediaController.access$2200(MediaController$5.this.this$0) == null)) {
                        MediaController$5.this.this$0.stopRecording(2);
                    }
                    embedBottomSheet = EmbedBottomSheet.getInstance();
                    if (embedBottomSheet != null) {
                        embedBottomSheet.pause();
                    }
                    MediaController.access$2302(MediaController$5.this.this$0, true);
                } else if (state == 0) {
                    MediaController.access$2302(MediaController$5.this.this$0, false);
                } else if (state == 2) {
                    embedBottomSheet = EmbedBottomSheet.getInstance();
                    if (embedBottomSheet != null) {
                        embedBottomSheet.pause();
                    }
                    MediaController.access$2302(MediaController$5.this.this$0, true);
                }
            }
        });
    }
}
