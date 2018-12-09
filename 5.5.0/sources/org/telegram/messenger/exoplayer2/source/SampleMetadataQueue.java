package org.telegram.messenger.exoplayer2.source;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

final class SampleMetadataQueue {
    private static final int SAMPLE_CAPACITY_INCREMENT = 1000;
    private int absoluteStartIndex;
    private int capacity = 1000;
    private CryptoData[] cryptoDatas = new CryptoData[this.capacity];
    private int[] flags = new int[this.capacity];
    private Format[] formats = new Format[this.capacity];
    private long largestDiscardedTimestampUs = Long.MIN_VALUE;
    private long largestQueuedTimestampUs = Long.MIN_VALUE;
    private int length;
    private long[] offsets = new long[this.capacity];
    private int readPosition;
    private int relativeStartIndex;
    private int[] sizes = new int[this.capacity];
    private int[] sourceIds = new int[this.capacity];
    private long[] timesUs = new long[this.capacity];
    private Format upstreamFormat;
    private boolean upstreamFormatRequired = true;
    private boolean upstreamKeyframeRequired = true;
    private int upstreamSourceId;

    public static final class SampleExtrasHolder {
        public CryptoData cryptoData;
        public long offset;
        public int size;
    }

    private long discardSamples(int i) {
        this.largestDiscardedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(i));
        this.length -= i;
        this.absoluteStartIndex += i;
        this.relativeStartIndex += i;
        if (this.relativeStartIndex >= this.capacity) {
            this.relativeStartIndex -= this.capacity;
        }
        this.readPosition -= i;
        if (this.readPosition < 0) {
            this.readPosition = 0;
        }
        if (this.length != 0) {
            return this.offsets[this.relativeStartIndex];
        }
        int i2 = (this.relativeStartIndex == 0 ? this.capacity : this.relativeStartIndex) - 1;
        return ((long) this.sizes[i2]) + this.offsets[i2];
    }

    private int findSampleBefore(int i, int i2, long j, boolean z) {
        int i3 = -1;
        int i4 = 0;
        while (i4 < i2 && this.timesUs[i] <= j) {
            if (!(z && (this.flags[i] & 1) == 0)) {
                i3 = i4;
            }
            int i5 = i + 1;
            if (i5 == this.capacity) {
                i5 = 0;
            }
            i4++;
            i = i5;
        }
        return i3;
    }

    private long getLargestTimestamp(int i) {
        long j = Long.MIN_VALUE;
        if (i == 0) {
            return Long.MIN_VALUE;
        }
        int i2 = 0;
        int relativeIndex = getRelativeIndex(i - 1);
        while (i2 < i) {
            long max = Math.max(j, this.timesUs[relativeIndex]);
            if ((this.flags[relativeIndex] & 1) != 0) {
                return max;
            }
            int i3 = relativeIndex - 1;
            if (i3 == -1) {
                i3 = this.capacity - 1;
            }
            i2++;
            relativeIndex = i3;
            j = max;
        }
        return j;
    }

    private int getRelativeIndex(int i) {
        int i2 = this.relativeStartIndex + i;
        return i2 < this.capacity ? i2 : i2 - this.capacity;
    }

    public synchronized boolean advanceTo(long j, boolean z, boolean z2) {
        boolean z3 = false;
        synchronized (this) {
            int relativeIndex = getRelativeIndex(this.readPosition);
            if (hasNextSample() && j >= this.timesUs[relativeIndex] && (j <= this.largestQueuedTimestampUs || z2)) {
                int findSampleBefore = findSampleBefore(relativeIndex, this.length - this.readPosition, j, z);
                if (findSampleBefore != -1) {
                    this.readPosition += findSampleBefore;
                    z3 = true;
                }
            }
        }
        return z3;
    }

    public synchronized void advanceToEnd() {
        if (hasNextSample()) {
            this.readPosition = this.length;
        }
    }

    public synchronized boolean attemptSplice(long j) {
        boolean z = true;
        synchronized (this) {
            if (this.length == 0) {
                if (j <= this.largestDiscardedTimestampUs) {
                    z = false;
                }
            } else if (Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.readPosition)) >= j) {
                z = false;
            } else {
                int i = this.length;
                int relativeIndex = getRelativeIndex(this.length - 1);
                while (i > this.readPosition && this.timesUs[relativeIndex] >= j) {
                    i--;
                    relativeIndex--;
                    if (relativeIndex == -1) {
                        relativeIndex = this.capacity - 1;
                    }
                }
                discardUpstreamSamples(this.absoluteStartIndex + i);
            }
        }
        return z;
    }

    public synchronized void commitSample(long j, int i, long j2, int i2, CryptoData cryptoData) {
        if (this.upstreamKeyframeRequired) {
            if ((i & 1) != 0) {
                this.upstreamKeyframeRequired = false;
            }
        }
        Assertions.checkState(!this.upstreamFormatRequired);
        commitSampleTimestamp(j);
        int relativeIndex = getRelativeIndex(this.length);
        this.timesUs[relativeIndex] = j;
        this.offsets[relativeIndex] = j2;
        this.sizes[relativeIndex] = i2;
        this.flags[relativeIndex] = i;
        this.cryptoDatas[relativeIndex] = cryptoData;
        this.formats[relativeIndex] = this.upstreamFormat;
        this.sourceIds[relativeIndex] = this.upstreamSourceId;
        this.length++;
        if (this.length == this.capacity) {
            relativeIndex = this.capacity + 1000;
            Object obj = new int[relativeIndex];
            Object obj2 = new long[relativeIndex];
            Object obj3 = new long[relativeIndex];
            Object obj4 = new int[relativeIndex];
            Object obj5 = new int[relativeIndex];
            Object obj6 = new CryptoData[relativeIndex];
            Object obj7 = new Format[relativeIndex];
            int i3 = this.capacity - this.relativeStartIndex;
            System.arraycopy(this.offsets, this.relativeStartIndex, obj2, 0, i3);
            System.arraycopy(this.timesUs, this.relativeStartIndex, obj3, 0, i3);
            System.arraycopy(this.flags, this.relativeStartIndex, obj4, 0, i3);
            System.arraycopy(this.sizes, this.relativeStartIndex, obj5, 0, i3);
            System.arraycopy(this.cryptoDatas, this.relativeStartIndex, obj6, 0, i3);
            System.arraycopy(this.formats, this.relativeStartIndex, obj7, 0, i3);
            System.arraycopy(this.sourceIds, this.relativeStartIndex, obj, 0, i3);
            int i4 = this.relativeStartIndex;
            System.arraycopy(this.offsets, 0, obj2, i3, i4);
            System.arraycopy(this.timesUs, 0, obj3, i3, i4);
            System.arraycopy(this.flags, 0, obj4, i3, i4);
            System.arraycopy(this.sizes, 0, obj5, i3, i4);
            System.arraycopy(this.cryptoDatas, 0, obj6, i3, i4);
            System.arraycopy(this.formats, 0, obj7, i3, i4);
            System.arraycopy(this.sourceIds, 0, obj, i3, i4);
            this.offsets = obj2;
            this.timesUs = obj3;
            this.flags = obj4;
            this.sizes = obj5;
            this.cryptoDatas = obj6;
            this.formats = obj7;
            this.sourceIds = obj;
            this.relativeStartIndex = 0;
            this.length = this.capacity;
            this.capacity = relativeIndex;
        }
    }

    public synchronized void commitSampleTimestamp(long j) {
        this.largestQueuedTimestampUs = Math.max(this.largestQueuedTimestampUs, j);
    }

    public synchronized long discardTo(long j, boolean z, boolean z2) {
        long j2;
        if (this.length == 0 || j < this.timesUs[this.relativeStartIndex]) {
            j2 = -1;
        } else {
            int i;
            int findSampleBefore;
            if (z2) {
                if (this.readPosition != this.length) {
                    i = this.readPosition + 1;
                    findSampleBefore = findSampleBefore(this.relativeStartIndex, i, j, z);
                    j2 = findSampleBefore != -1 ? -1 : discardSamples(findSampleBefore);
                }
            }
            i = this.length;
            findSampleBefore = findSampleBefore(this.relativeStartIndex, i, j, z);
            if (findSampleBefore != -1) {
            }
        }
        return j2;
    }

    public synchronized long discardToEnd() {
        return this.length == 0 ? -1 : discardSamples(this.length);
    }

    public synchronized long discardToRead() {
        return this.readPosition == 0 ? -1 : discardSamples(this.readPosition);
    }

    public long discardUpstreamSamples(int i) {
        int writeIndex = getWriteIndex() - i;
        boolean z = writeIndex >= 0 && writeIndex <= this.length - this.readPosition;
        Assertions.checkArgument(z);
        this.length -= writeIndex;
        this.largestQueuedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.length));
        if (this.length == 0) {
            return 0;
        }
        int relativeIndex = getRelativeIndex(this.length - 1);
        return ((long) this.sizes[relativeIndex]) + this.offsets[relativeIndex];
    }

    public synchronized boolean format(Format format) {
        boolean z = false;
        synchronized (this) {
            if (format == null) {
                this.upstreamFormatRequired = true;
            } else {
                this.upstreamFormatRequired = false;
                if (!Util.areEqual(format, this.upstreamFormat)) {
                    this.upstreamFormat = format;
                    z = true;
                }
            }
        }
        return z;
    }

    public synchronized long getLargestQueuedTimestampUs() {
        return this.largestQueuedTimestampUs;
    }

    public int getReadIndex() {
        return this.absoluteStartIndex + this.readPosition;
    }

    public synchronized Format getUpstreamFormat() {
        return this.upstreamFormatRequired ? null : this.upstreamFormat;
    }

    public int getWriteIndex() {
        return this.absoluteStartIndex + this.length;
    }

    public synchronized boolean hasNextSample() {
        return this.readPosition != this.length;
    }

    public int peekSourceId() {
        return hasNextSample() ? this.sourceIds[getRelativeIndex(this.readPosition)] : this.upstreamSourceId;
    }

    public synchronized int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z, boolean z2, Format format, SampleExtrasHolder sampleExtrasHolder) {
        int i = -4;
        synchronized (this) {
            if (hasNextSample()) {
                int relativeIndex = getRelativeIndex(this.readPosition);
                if (z || this.formats[relativeIndex] != format) {
                    formatHolder.format = this.formats[relativeIndex];
                    i = -5;
                } else if (decoderInputBuffer.isFlagsOnly()) {
                    i = -3;
                } else {
                    decoderInputBuffer.timeUs = this.timesUs[relativeIndex];
                    decoderInputBuffer.setFlags(this.flags[relativeIndex]);
                    sampleExtrasHolder.size = this.sizes[relativeIndex];
                    sampleExtrasHolder.offset = this.offsets[relativeIndex];
                    sampleExtrasHolder.cryptoData = this.cryptoDatas[relativeIndex];
                    this.readPosition++;
                }
            } else if (z2) {
                decoderInputBuffer.setFlags(4);
            } else if (this.upstreamFormat == null || (!z && this.upstreamFormat == format)) {
                i = -3;
            } else {
                formatHolder.format = this.upstreamFormat;
                i = -5;
            }
        }
        return i;
    }

    public void reset(boolean z) {
        this.length = 0;
        this.absoluteStartIndex = 0;
        this.relativeStartIndex = 0;
        this.readPosition = 0;
        this.upstreamKeyframeRequired = true;
        this.largestDiscardedTimestampUs = Long.MIN_VALUE;
        this.largestQueuedTimestampUs = Long.MIN_VALUE;
        if (z) {
            this.upstreamFormat = null;
            this.upstreamFormatRequired = true;
        }
    }

    public synchronized void rewind() {
        this.readPosition = 0;
    }

    public void sourceId(int i) {
        this.upstreamSourceId = i;
    }
}
