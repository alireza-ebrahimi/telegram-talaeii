package org.telegram.messenger.exoplayer2.audio;

import org.telegram.messenger.exoplayer2.audio.AudioTrack.Listener;

final class MediaCodecAudioRenderer$AudioTrackListener implements Listener {
    final /* synthetic */ MediaCodecAudioRenderer this$0;

    private MediaCodecAudioRenderer$AudioTrackListener(MediaCodecAudioRenderer mediaCodecAudioRenderer) {
        this.this$0 = mediaCodecAudioRenderer;
    }

    public void onAudioSessionId(int audioSessionId) {
        MediaCodecAudioRenderer.access$100(this.this$0).audioSessionId(audioSessionId);
        this.this$0.onAudioSessionId(audioSessionId);
    }

    public void onPositionDiscontinuity() {
        this.this$0.onAudioTrackPositionDiscontinuity();
        MediaCodecAudioRenderer.access$202(this.this$0, true);
    }

    public void onUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        MediaCodecAudioRenderer.access$100(this.this$0).audioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        this.this$0.onAudioTrackUnderrun(bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
    }
}
