package org.telegram.messenger.exoplayer2.drm;

import android.media.MediaDrm;
import android.media.MediaDrm.OnEventListener;
import android.support.annotation.NonNull;

class FrameworkMediaDrm$1 implements OnEventListener {
    final /* synthetic */ FrameworkMediaDrm this$0;
    final /* synthetic */ ExoMediaDrm.OnEventListener val$listener;

    FrameworkMediaDrm$1(FrameworkMediaDrm this$0, ExoMediaDrm.OnEventListener onEventListener) {
        this.this$0 = this$0;
        this.val$listener = onEventListener;
    }

    public void onEvent(@NonNull MediaDrm md, byte[] sessionId, int event, int extra, byte[] data) {
        this.val$listener.onEvent(this.this$0, sessionId, event, extra, data);
    }
}
