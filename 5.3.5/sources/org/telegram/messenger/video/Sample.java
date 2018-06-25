package org.telegram.messenger.video;

public class Sample {
    private long offset = 0;
    private long size = 0;

    public Sample(long offset, long size) {
        this.offset = offset;
        this.size = size;
    }

    public long getOffset() {
        return this.offset;
    }

    public long getSize() {
        return this.size;
    }
}
