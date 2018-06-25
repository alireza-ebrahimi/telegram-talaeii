package org.telegram.messenger.exoplayer2.drm;

import android.media.MediaDrm;
import org.telegram.messenger.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;

class FrameworkMediaDrm$3 implements ProvisionRequest {
    final /* synthetic */ FrameworkMediaDrm this$0;
    final /* synthetic */ MediaDrm.ProvisionRequest val$provisionRequest;

    FrameworkMediaDrm$3(FrameworkMediaDrm this$0, MediaDrm.ProvisionRequest provisionRequest) {
        this.this$0 = this$0;
        this.val$provisionRequest = provisionRequest;
    }

    public byte[] getData() {
        return this.val$provisionRequest.getData();
    }

    public String getDefaultUrl() {
        return this.val$provisionRequest.getDefaultUrl();
    }
}
