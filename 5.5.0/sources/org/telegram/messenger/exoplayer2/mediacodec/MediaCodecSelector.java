package org.telegram.messenger.exoplayer2.mediacodec;

public interface MediaCodecSelector {
    public static final MediaCodecSelector DEFAULT = new C34941();

    /* renamed from: org.telegram.messenger.exoplayer2.mediacodec.MediaCodecSelector$1 */
    static class C34941 implements MediaCodecSelector {
        C34941() {
        }

        public MediaCodecInfo getDecoderInfo(String str, boolean z) {
            return MediaCodecUtil.getDecoderInfo(str, z);
        }

        public MediaCodecInfo getPassthroughDecoderInfo() {
            return MediaCodecUtil.getPassthroughDecoderInfo();
        }
    }

    MediaCodecInfo getDecoderInfo(String str, boolean z);

    MediaCodecInfo getPassthroughDecoderInfo();
}
