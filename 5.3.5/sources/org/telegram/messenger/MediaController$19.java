package org.telegram.messenger;

import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;

class MediaController$19 implements OnPlaybackPositionUpdateListener {
    final /* synthetic */ MediaController this$0;

    MediaController$19(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void onMarkerReached(AudioTrack audioTrack) {
        this.this$0.cleanupPlayer(true, true, true);
    }

    public void onPeriodicNotification(AudioTrack audioTrack) {
    }
}
