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

    public DefaultAllocator(boolean z, int i) {
        this(z, i, 0);
    }

    public DefaultAllocator(boolean z, int i, int i2) {
        int i3 = 0;
        Assertions.checkArgument(i > 0);
        Assertions.checkArgument(i2 >= 0);
        this.trimOnReset = z;
        this.individualAllocationSize = i;
        this.availableCount = i2;
        this.availableAllocations = new Allocation[(i2 + 100)];
        if (i2 > 0) {
            this.initialAllocationBlock = new byte[(i2 * i)];
            while (i3 < i2) {
                this.availableAllocations[i3] = new Allocation(this.initialAllocationBlock, i3 * i);
                i3++;
            }
        } else {
            this.initialAllocationBlock = null;
        }
        this.singleAllocationReleaseHolder = new Allocation[1];
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

    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }

    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    public synchronized void release(Allocation allocation) {
        this.singleAllocationReleaseHolder[0] = allocation;
        release(this.singleAllocationReleaseHolder);
    }

    public synchronized void release(Allocation[] allocationArr) {
        if (this.availableCount + allocationArr.length >= this.availableAllocations.length) {
            this.availableAllocations = (Allocation[]) Arrays.copyOf(this.availableAllocations, Math.max(this.availableAllocations.length * 2, this.availableCount + allocationArr.length));
        }
        for (Allocation allocation : allocationArr) {
            boolean z = allocation.data == this.initialAllocationBlock || allocation.data.length == this.individualAllocationSize;
            Assertions.checkArgument(z);
            Allocation[] allocationArr2 = this.availableAllocations;
            int i = this.availableCount;
            this.availableCount = i + 1;
            allocationArr2[i] = allocation;
        }
        this.allocatedCount -= allocationArr.length;
        notifyAll();
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int i) {
        Object obj = i < this.targetBufferSize ? 1 : null;
        this.targetBufferSize = i;
        if (obj != null) {
            trim();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void trim() {
        /*
        r7 = this;
        r1 = 0;
        monitor-enter(r7);
        r0 = r7.targetBufferSize;	 Catch:{ all -> 0x005e }
        r2 = r7.individualAllocationSize;	 Catch:{ all -> 0x005e }
        r0 = org.telegram.messenger.exoplayer2.util.Util.ceilDivide(r0, r2);	 Catch:{ all -> 0x005e }
        r2 = 0;
        r3 = r7.allocatedCount;	 Catch:{ all -> 0x005e }
        r0 = r0 - r3;
        r3 = java.lang.Math.max(r2, r0);	 Catch:{ all -> 0x005e }
        r0 = r7.availableCount;	 Catch:{ all -> 0x005e }
        if (r3 < r0) goto L_0x0018;
    L_0x0016:
        monitor-exit(r7);
        return;
    L_0x0018:
        r0 = r7.initialAllocationBlock;	 Catch:{ all -> 0x005e }
        if (r0 == 0) goto L_0x0061;
    L_0x001c:
        r0 = r7.availableCount;	 Catch:{ all -> 0x005e }
        r0 = r0 + -1;
    L_0x0020:
        if (r1 > r0) goto L_0x004b;
    L_0x0022:
        r2 = r7.availableAllocations;	 Catch:{ all -> 0x005e }
        r4 = r2[r1];	 Catch:{ all -> 0x005e }
        r2 = r4.data;	 Catch:{ all -> 0x005e }
        r5 = r7.initialAllocationBlock;	 Catch:{ all -> 0x005e }
        if (r2 != r5) goto L_0x002f;
    L_0x002c:
        r1 = r1 + 1;
        goto L_0x0020;
    L_0x002f:
        r2 = r7.availableAllocations;	 Catch:{ all -> 0x005e }
        r5 = r2[r0];	 Catch:{ all -> 0x005e }
        r2 = r5.data;	 Catch:{ all -> 0x005e }
        r6 = r7.initialAllocationBlock;	 Catch:{ all -> 0x005e }
        if (r2 == r6) goto L_0x003c;
    L_0x0039:
        r0 = r0 + -1;
        goto L_0x0020;
    L_0x003c:
        r6 = r7.availableAllocations;	 Catch:{ all -> 0x005e }
        r2 = r1 + 1;
        r6[r1] = r5;	 Catch:{ all -> 0x005e }
        r5 = r7.availableAllocations;	 Catch:{ all -> 0x005e }
        r1 = r0 + -1;
        r5[r0] = r4;	 Catch:{ all -> 0x005e }
        r0 = r1;
        r1 = r2;
        goto L_0x0020;
    L_0x004b:
        r0 = java.lang.Math.max(r3, r1);	 Catch:{ all -> 0x005e }
        r1 = r7.availableCount;	 Catch:{ all -> 0x005e }
        if (r0 >= r1) goto L_0x0016;
    L_0x0053:
        r1 = r7.availableAllocations;	 Catch:{ all -> 0x005e }
        r2 = r7.availableCount;	 Catch:{ all -> 0x005e }
        r3 = 0;
        java.util.Arrays.fill(r1, r0, r2, r3);	 Catch:{ all -> 0x005e }
        r7.availableCount = r0;	 Catch:{ all -> 0x005e }
        goto L_0x0016;
    L_0x005e:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
    L_0x0061:
        r0 = r3;
        goto L_0x0053;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.upstream.DefaultAllocator.trim():void");
    }
}
