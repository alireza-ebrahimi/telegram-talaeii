package org.telegram.messenger.exoplayer2.drm;

import android.media.MediaDrm;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.KeyRequest;

class FrameworkMediaDrm$2 implements KeyRequest {
    final /* synthetic */ FrameworkMediaDrm this$0;
    final /* synthetic */ MediaDrm.KeyRequest val$request;

    FrameworkMediaDrm$2(FrameworkMediaDrm this$0, MediaDrm.KeyRequest keyRequest) {
        this.this$0 = this$0;
        this.val$request = keyRequest;
    }

    public byte[] getData() {
        return this.val$request.getData();
    }

    public String getDefaultUrl() {
        return this.val$request.getDefaultUrl();
    }
}
