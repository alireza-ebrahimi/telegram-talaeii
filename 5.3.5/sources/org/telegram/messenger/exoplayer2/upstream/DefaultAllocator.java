package org.telegram.messenger.exoplayer2.upstream;

import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class DefaultAllocator implements Allocator {
    private static final int AVAILABLE_EXTRA_CAPACITY = 100;
    private int allocatedCount;
    private Allocation[] availableAllocations;
    private int availableCount;
    private final int individualAllocationSize;
    private final byte[] initialAllocationBlock;
    private final Allocation[] singleAllocationReleaseHolder;
    private int targetBufferSize;
    private final boolean trimOnReset;

    public DefaultAllocator(boolean trimOnReset, int individualAllocationSize) {
        this(trimOnReset, individualAllocationSize, 0);
    }

    public DefaultAllocator(boolean trimOnReset, int individualAllocationSize, int initialAllocationCount) {
        boolean z;
        boolean z2 = false;
        if (individualAllocationSize > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        if (initialAllocationCount >= 0) {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        this.trimOnReset = trimOnReset;
        this.individualAllocationSize = individualAllocationSize;
        this.availableCount = initialAllocationCount;
        this.availableAllocations = new Allocation[(initialAllocationCount + 100)];
        if (initialAllocationCount > 0) {
            this.initialAllocationBlock = new byte[(initialAllocationCount * individualAllocationSize)];
            for (int i = 0; i < initialAllocationCount; i++) {
                this.availableAllocations[i] = new Allocation(this.initialAllocationBlock, i * individualAllocationSize);
            }
        } else {
            this.initialAllocationBlock = null;
        }
        this.singleAllocationReleaseHolder = new Allocation[1];
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int targetBufferSize) {
        boolean targetBufferSizeReduced = targetBufferSize < this.targetBufferSize;
        this.targetBufferSize = targetBufferSize;
        if (targetBufferSizeReduced) {
            trim();
        }
    }

    public synchronized Allocation allocate() {
        Allocation allocation;
        this.allocatedCount++;
        if (this.availableCount > 0) {
            Allocation[] allocationArr = this.availableAllocations;
            int i = this.availableCount - 1;
            this.availableCount = i;
            allocation = allocationArr[i];
            this.availableAllocations[this.availableCount] = null;
        } else {
            allocation = new Allocation(new byte[this.individualAllocationSize], 0);
        }
        return allocation;
    }

    public synchronized void release(Allocation allocation) {
        this.singleAllocationReleaseHolder[0] = allocation;
        release(this.singleAllocationReleaseHolder);
    }

    public synchronized void release(Allocation[] allocations) {
        if (this.availableCount + allocations.length >= this.availableAllocations.length) {
            this.availableAllocations = (Allocation[]) Arrays.copyOf(this.availableAllocations, Math.max(this.availableAllocations.length * 2, this.availableCount + allocations.length));
        }
        for (Allocation allocation : allocations) {
            boolean z;
            if (allocation.data == this.initialAllocationBlock || allocation.data.length == this.individualAllocationSize) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkArgument(z);
            Allocation[] allocationArr = this.availableAllocations;
            int i = this.availableCount;
            this.availableCount = i + 1;
            allocationArr[i] = allocation;
        }
        this.allocatedCount -= allocations.length;
        notifyAll();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void trim() {
        /*
        r11 = this;
        monitor-enter(r11);
        r8 = r11.targetBufferSize;	 Catch:{ all -> 0x0050 }
        r9 = r11.individualAllocationSize;	 Catch:{ all -> 0x0050 }
        r6 = org.telegram.messenger.exoplayer2.util.Util.ceilDivide(r8, r9);	 Catch:{ all -> 0x0050 }
        r8 = 0;
        r9 = r11.allocatedCount;	 Catch:{ all -> 0x0050 }
        r9 = r6 - r9;
        r7 = java.lang.Math.max(r8, r9);	 Catch:{ all -> 0x0050 }
        r8 = r11.availableCount;	 Catch:{ all -> 0x0050 }
        if (r7 < r8) goto L_0x0018;
    L_0x0016:
        monitor-exit(r11);
        return;
    L_0x0018:
        r8 = r11.initialAllocationBlock;	 Catch:{ all -> 0x0050 }
        if (r8 == 0) goto L_0x005b;
    L_0x001c:
        r4 = 0;
        r8 = r11.availableCount;	 Catch:{ all -> 0x0050 }
        r1 = r8 + -1;
        r2 = r1;
        r5 = r4;
    L_0x0023:
        if (r5 > r2) goto L_0x0053;
    L_0x0025:
        r8 = r11.availableAllocations;	 Catch:{ all -> 0x0050 }
        r3 = r8[r5];	 Catch:{ all -> 0x0050 }
        r8 = r3.data;	 Catch:{ all -> 0x0050 }
        r9 = r11.initialAllocationBlock;	 Catch:{ all -> 0x0050 }
        if (r8 != r9) goto L_0x0035;
    L_0x002f:
        r4 = r5 + 1;
        r1 = r2;
    L_0x0032:
        r2 = r1;
        r5 = r4;
        goto L_0x0023;
    L_0x0035:
        r8 = r11.availableAllocations;	 Catch:{ all -> 0x0050 }
        r0 = r8[r2];	 Catch:{ all -> 0x0050 }
        r8 = r0.data;	 Catch:{ all -> 0x0050 }
        r9 = r11.initialAllocationBlock;	 Catch:{ all -> 0x0050 }
        if (r8 == r9) goto L_0x0043;
    L_0x003f:
        r1 = r2 + -1;
        r4 = r5;
        goto L_0x0032;
    L_0x0043:
        r8 = r11.availableAllocations;	 Catch:{ all -> 0x0050 }
        r4 = r5 + 1;
        r8[r5] = r0;	 Catch:{ all -> 0x0050 }
        r8 = r11.availableAllocations;	 Catch:{ all -> 0x0050 }
        r1 = r2 + -1;
        r8[r2] = r3;	 Catch:{ all -> 0x0050 }
        goto L_0x0032;
    L_0x0050:
        r8 = move-exception;
        monitor-exit(r11);
        throw r8;
    L_0x0053:
        r7 = java.lang.Math.max(r7, r5);	 Catch:{ all -> 0x0050 }
        r8 = r11.availableCount;	 Catch:{ all -> 0x0050 }
        if (r7 >= r8) goto L_0x0016;
    L_0x005b:
        r8 = r11.availableAllocations;	 Catch:{ all -> 0x0050 }
        r9 = r11.availableCount;	 Catch:{ all -> 0x0050 }
        r10 = 0;
        java.util.Arrays.fill(r8, r7, r9, r10);	 Catch:{ all -> 0x0050 }
        r11.availableCount = r7;	 Catch:{ all -> 0x0050 }
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.DefaultAllocator.trim():void");
    }

    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }
}
