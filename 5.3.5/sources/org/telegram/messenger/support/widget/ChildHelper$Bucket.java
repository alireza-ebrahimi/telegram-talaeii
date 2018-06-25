package org.telegram.messenger.support.widget;

class ChildHelper$Bucket {
    static final int BITS_PER_WORD = 64;
    static final long LAST_BIT = Long.MIN_VALUE;
    long mData = 0;
    ChildHelper$Bucket next;

    ChildHelper$Bucket() {
    }

    void set(int index) {
        if (index >= 64) {
            ensureNext();
            this.next.set(index - 64);
            return;
        }
        this.mData |= 1 << index;
    }

    private void ensureNext() {
        if (this.next == null) {
            this.next = new ChildHelper$Bucket();
        }
    }

    void clear(int index) {
        if (index < 64) {
            this.mData &= (1 << index) ^ -1;
        } else if (this.next != null) {
            this.next.clear(index - 64);
        }
    }

    boolean get(int index) {
        if (index < 64) {
            return (this.mData & (1 << index)) != 0;
        } else {
            ensureNext();
            return this.next.get(index - 64);
        }
    }

    void reset() {
        this.mData = 0;
        if (this.next != null) {
            this.next.reset();
        }
    }

    void insert(int index, boolean value) {
        if (index >= 64) {
            ensureNext();
            this.next.insert(index - 64, value);
            return;
        }
        boolean lastBit = (this.mData & Long.MIN_VALUE) != 0;
        long mask = (1 << index) - 1;
        this.mData = (this.mData & mask) | ((this.mData & (-1 ^ mask)) << 1);
        if (value) {
            set(index);
        } else {
            clear(index);
        }
        if (lastBit || this.next != null) {
            ensureNext();
            this.next.insert(0, lastBit);
        }
    }

    boolean remove(int index) {
        if (index >= 64) {
            ensureNext();
            return this.next.remove(index - 64);
        }
        long mask = 1 << index;
        boolean z = (this.mData & mask) != 0;
        this.mData &= -1 ^ mask;
        mask--;
        this.mData = (this.mData & mask) | Long.rotateRight(this.mData & (-1 ^ mask), 1);
        if (this.next == null) {
            return z;
        }
        if (this.next.get(0)) {
            set(63);
        }
        this.next.remove(0);
        return z;
    }

    int countOnesBefore(int index) {
        if (this.next == null) {
            if (index >= 64) {
                return Long.bitCount(this.mData);
            }
            return Long.bitCount(this.mData & ((1 << index) - 1));
        } else if (index < 64) {
            return Long.bitCount(this.mData & ((1 << index) - 1));
        } else {
            return this.next.countOnesBefore(index - 64) + Long.bitCount(this.mData);
        }
    }

    public String toString() {
        if (this.next == null) {
            return Long.toBinaryString(this.mData);
        }
        return this.next.toString() + "xx" + Long.toBinaryString(this.mData);
    }
}
