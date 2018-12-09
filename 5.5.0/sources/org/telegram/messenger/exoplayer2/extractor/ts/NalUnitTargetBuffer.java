package org.telegram.messenger.exoplayer2.extractor.ts;

import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class NalUnitTargetBuffer {
    private boolean isCompleted;
    private boolean isFilling;
    public byte[] nalData;
    public int nalLength;
    private final int targetType;

    public NalUnitTargetBuffer(int i, int i2) {
        this.targetType = i;
        this.nalData = new byte[(i2 + 3)];
        this.nalData[2] = (byte) 1;
    }

    public void appendToNalUnit(byte[] bArr, int i, int i2) {
        if (this.isFilling) {
            int i3 = i2 - i;
            if (this.nalData.length < this.nalLength + i3) {
                this.nalData = Arrays.copyOf(this.nalData, (this.nalLength + i3) * 2);
            }
            System.arraycopy(bArr, i, this.nalData, this.nalLength, i3);
            this.nalLength = i3 + this.nalLength;
        }
    }

    public boolean endNalUnit(int i) {
        if (!this.isFilling) {
            return false;
        }
        this.nalLength -= i;
        this.isFilling = false;
        this.isCompleted = true;
        return true;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void reset() {
        this.isFilling = false;
        this.isCompleted = false;
    }

    public void startNalUnit(int i) {
        boolean z = true;
        Assertions.checkState(!this.isFilling);
        if (i != this.targetType) {
            z = false;
        }
        this.isFilling = z;
        if (this.isFilling) {
            this.nalLength = 3;
            this.isCompleted = false;
        }
    }
}
