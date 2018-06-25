package org.telegram.messenger.exoplayer2.upstream;

public final class Allocation {
    public final byte[] data;
    public final int offset;

    public Allocation(byte[] data, int offset) {
        this.data = data;
        this.offset = offset;
    }
}
