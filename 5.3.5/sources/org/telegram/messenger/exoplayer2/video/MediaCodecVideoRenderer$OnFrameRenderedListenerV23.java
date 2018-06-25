package org.telegram.messenger.exoplayer2.video;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.OnFrameRenderedListener;
import android.os.Handler;
import android.support.annotation.NonNull;

@TargetApi(23)
final class MediaCodecVideoRenderer$OnFrameRenderedListenerV23 implements OnFrameRenderedListener {
    final /* synthetic */ MediaCodecVideoRenderer this$0;

    private MediaCodecVideoRenderer$OnFrameRenderedListenerV23(MediaCodecVideoRenderer mediaCodecVideoRenderer, MediaCodec codec) {
        this.this$0 = mediaCodecVideoRenderer;
        codec.setOnFrameRenderedListener(this, new Handler());
    }

    public void onFrameRendered(@NonNull MediaCodec codec, long presentationTimeUs, long nanoTime) {
        if (this == this.this$0.tunnelingOnFrameRenderedListener) {
            this.this$0.maybeNotifyRenderedFirstFrame();
        }
    }
}
