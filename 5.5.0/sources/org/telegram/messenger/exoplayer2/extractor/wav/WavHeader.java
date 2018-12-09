package org.telegram.messenger.exoplayer2.extractor.wav;

import org.telegram.messenger.exoplayer2.C3446C;

final class WavHeader {
    private final int averageBytesPerSecond;
    private final int bitsPerSample;
    private final int blockAlignment;
    private long dataSize;
    private long dataStartPosition;
    private final int encoding;
    private final int numChannels;
    private final int sampleRateHz;

    public WavHeader(int i, int i2, int i3, int i4, int i5, int i6) {
        this.numChannels = i;
        this.sampleRateHz = i2;
        this.averageBytesPerSecond = i3;
        this.blockAlignment = i4;
        this.bitsPerSample = i5;
        this.encoding = i6;
    }

    public int getBitrate() {
        return (this.sampleRateHz * this.bitsPerSample) * this.numChannels;
    }

    public int getBytesPerFrame() {
        return this.blockAlignment;
    }

    public long getDurationUs() {
        return ((this.dataSize / ((long) this.blockAlignment)) * C3446C.MICROS_PER_SECOND) / ((long) this.sampleRateHz);
    }

    public int getEncoding() {
        return this.encoding;
    }

    public int getNumChannels() {
        return this.numChannels;
    }

    public long getPosition(long j) {
        return Math.min((((((long) this.averageBytesPerSecond) * j) / C3446C.MICROS_PER_SECOND) / ((long) this.blockAlignment)) * ((long) this.blockAlignment), this.dataSize - ((long) this.blockAlignment)) + this.dataStartPosition;
    }

    public int getSampleRateHz() {
        return this.sampleRateHz;
    }

    public long getTimeUs(long j) {
        return (C3446C.MICROS_PER_SECOND * j) / ((long) this.averageBytesPerSecond);
    }

    public boolean hasDataBounds() {
        return (this.dataStartPosition == 0 || this.dataSize == 0) ? false : true;
    }

    public void setDataBounds(long j, long j2) {
        this.dataStartPosition = j;
        this.dataSize = j2;
    }
}
