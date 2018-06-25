package org.telegram.messenger.exoplayer2.extractor.ts;

import java.util.Arrays;

final class H262Reader$CsdBuffer {
    private static final byte[] START_CODE = new byte[]{(byte) 0, (byte) 0, (byte) 1};
    public byte[] data;
    private boolean isFilling;
    public int length;
    public int sequenceExtensionPosition;

    public H262Reader$CsdBuffer(int initialCapacity) {
        this.data = new byte[initialCapacity];
    }

    public void reset() {
        this.isFilling = false;
        this.length = 0;
        this.sequenceExtensionPosition = 0;
    }

    public boolean onStartCode(int startCodeValue, int bytesAlreadyPassed) {
        if (this.isFilling) {
            this.length -= bytesAlreadyPassed;
            if (this.sequenceExtensionPosition == 0 && startCodeValue == 181) {
                this.sequenceExtensionPosition = this.length;
            } else {
                this.isFilling = false;
                return true;
            }
        } else if (startCodeValue == 179) {
            this.isFilling = true;
        }
        onData(START_CODE, 0, START_CODE.length);
        return false;
    }

    public void onData(byte[] newData, int offset, int limit) {
        if (this.isFilling) {
            int readLength = limit - offset;
            if (this.data.length < this.length + readLength) {
                this.data = Arrays.copyOf(this.data, (this.length + readLength) * 2);
            }
            System.arraycopy(newData, offset, this.data, this.length, readLength);
            this.length += readLength;
        }
    }
}
