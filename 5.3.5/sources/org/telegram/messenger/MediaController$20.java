package org.telegram.messenger;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

class MediaController$20 implements OnCompletionListener {
    final /* synthetic */ MediaController this$0;
    final /* synthetic */ MessageObject val$messageObject;

    MediaController$20(MediaController this$0, MessageObject messageObject) {
        this.this$0 = this$0;
        this.val$messageObject = messageObject;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        if (MediaController.access$6300(this.this$0).isEmpty() || MediaController.access$6300(this.this$0).size() <= 1) {
            MediaController mediaController = this.this$0;
            boolean z = this.val$messageObject != null && this.val$messageObject.isVoice();
            mediaController.cleanupPlayer(true, true, z);
            return;
        }
        MediaController.access$6400(this.this$0, true);
    }
}
