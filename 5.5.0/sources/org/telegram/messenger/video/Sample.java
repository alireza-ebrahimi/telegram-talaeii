package org.telegram.messenger.video;

public class Sample {
    private long offset = 0;
    private long size = 0;

    public Sample(long j, long j2) {
        this.offset = j;
        this.size = j2;
    }

    public long getOffset() {
        return this.offset;
    }

    public long getSize() {
        return this.size;
    }
}
