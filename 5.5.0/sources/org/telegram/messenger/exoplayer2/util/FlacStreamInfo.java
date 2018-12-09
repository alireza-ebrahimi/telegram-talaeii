package org.telegram.messenger.exoplayer2.util;

import org.telegram.messenger.exoplayer2.C3446C;

public final class FlacStreamInfo {
    public final int bitsPerSample;
    public final int channels;
    public final int maxBlockSize;
    public final int maxFrameSize;
    public final int minBlockSize;
    public final int minFrameSize;
    public final int sampleRate;
    public final long totalSamples;

    public FlacStreamInfo(int i, int i2, int i3, int i4, int i5, int i6, int i7, long j) {
        this.minBlockSize = i;
        this.maxBlockSize = i2;
        this.minFrameSize = i3;
        this.maxFrameSize = i4;
        this.sampleRate = i5;
        this.channels = i6;
        this.bitsPerSample = i7;
        this.totalSamples = j;
    }

    public FlacStreamInfo(byte[] bArr, int i) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(bArr);
        parsableBitArray.setPosition(i * 8);
        this.minBlockSize = parsableBitArray.readBits(16);
        this.maxBlockSize = parsableBitArray.readBits(16);
        this.minFrameSize = parsableBitArray.readBits(24);
        this.maxFrameSize = parsableBitArray.readBits(24);
        this.sampleRate = parsableBitArray.readBits(20);
        this.channels = parsableBitArray.readBits(3) + 1;
        this.bitsPerSample = parsableBitArray.readBits(5) + 1;
        this.totalSamples = (((long) parsableBitArray.readBits(32)) & 4294967295L) | ((((long) parsableBitArray.readBits(4)) & 15) << 32);
    }

    public int bitRate() {
        return this.bitsPerSample * this.sampleRate;
    }

    public long durationUs() {
        return (this.totalSamples * C3446C.MICROS_PER_SECOND) / ((long) this.sampleRate);
    }

    public int maxDecodedFrameSize() {
        return (this.maxBlockSize * this.channels) * 2;
    }
}
