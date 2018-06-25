package org.telegram.messenger.exoplayer2.extractor.ts;

import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class NalUnitTargetBuffer {
    private boolean isCompleted;
    private boolean isFilling;
    public byte[] nalData;
    public int nalLength;
    private final int targetType;

    public NalUnitTargetBuffer(int targetType, int initialCapacity) {
        this.targetType = targetType;
        this.nalData = new byte[(initialCapacity + 3)];
        this.nalData[2] = (byte) 1;
    }

    public void reset() {
        this.isFilling = false;
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void startNalUnit(int type) {
        boolean z = true;
        Assertions.checkState(!this.isFilling);
        if (type != this.targetType) {
            z = false;
        }
        this.isFilling = z;
        if (this.isFilling) {
            this.nalLength = 3;
            this.isCompleted = false;
        }
    }

    public void appendToNalUnit(byte[] data, int offset, int limit) {
        if (this.isFilling) {
            int readLength = limit - offset;
            if (this.nalData.length < this.nalLength + readLength) {
                this.nalData = Arrays.copyOf(this.nalData, (this.nalLength + readLength) * 2);
            }
            System.arraycopy(data, offset, this.nalData, this.nalLength, readLength);
            this.nalLength += readLength;
        }
    }

    public boolean endNalUnit(int discardPadding) {
        if (!this.isFilling) {
            return false;
        }
        this.nalLength -= discardPadding;
        this.isFilling = false;
        this.isCompleted = true;
        return true;
    }
}
