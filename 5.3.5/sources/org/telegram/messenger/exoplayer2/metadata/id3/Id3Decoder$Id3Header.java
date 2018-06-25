package org.telegram.messenger.exoplayer2.metadata.id3;

final class Id3Decoder$Id3Header {
    private final int framesSize;
    private final boolean isUnsynchronized;
    private final int majorVersion;

    public Id3Decoder$Id3Header(int majorVersion, boolean isUnsynchronized, int framesSize) {
        this.majorVersion = majorVersion;
        this.isUnsynchronized = isUnsynchronized;
        this.framesSize = framesSize;
    }
}
