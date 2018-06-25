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

    public void reset(boolean resetUpstreamFormat) {
        this.length = 0;
        this.absoluteStartIndex = 0;
        this.relativeStartIndex = 0;
        this.readPosition = 0;
        this.upstreamKeyframeRequired = true;
        this.largestDiscardedTimestampUs = Long.MIN_VALUE;
        this.largestQueuedTimestampUs = Long.MIN_VALUE;
        if (resetUpstreamFormat) {
            this.upstreamFormat = null;
            this.upstreamFormatRequired = true;
        }
    }

    public int getWriteIndex() {
        return this.absoluteStartIndex + this.length;
    }

    public long discardUpstreamSamples(int discardFromIndex) {
        int discardCount = getWriteIndex() - discardFromIndex;
        boolean z = discardCount >= 0 && discardCount <= this.length - this.readPosition;
        Assertions.checkArgument(z);
        this.length -= discardCount;
        this.largestQueuedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.length));
        if (this.length == 0) {
            return 0;
        }
        int relativeLastWriteIndex = getRelativeIndex(this.length - 1);
        return this.offsets[relativeLastWriteIndex] + ((long) this.sizes[relativeLastWriteIndex]);
    }

    public void sourceId(int sourceId) {
        this.upstreamSourceId = sourceId;
    }

    public int getReadIndex() {
        return this.absoluteStartIndex + this.readPosition;
    }

    public int peekSourceId() {
        return hasNextSample() ? this.sourceIds[getRelativeIndex(this.readPosition)] : this.upstreamSourceId;
    }

    public synchronized boolean hasNextSample() {
        return this.readPosition != this.length;
    }

    public synchronized Format getUpstreamFormat() {
        return this.upstreamFormatRequired ? null : this.upstreamFormat;
    }

    public synchronized long getLargestQueuedTimestampUs() {
        return this.largestQueuedTimestampUs;
    }

    public synchronized void rewind() {
        this.readPosition = 0;
    }

    public synchronized int read(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired, boolean loadingFinished, Format downstreamFormat, SampleExtrasHolder extrasHolder) {
        int i = -4;
        synchronized (this) {
            if (hasNextSample()) {
                int relativeReadIndex = getRelativeIndex(this.readPosition);
                if (formatRequired || this.formats[relativeReadIndex] != downstreamFormat) {
                    formatHolder.format = this.formats[relativeReadIndex];
                    i = -5;
                } else if (buffer.isFlagsOnly()) {
                    i = -3;
                } else {
                    buffer.timeUs = this.timesUs[relativeReadIndex];
                    buffer.setFlags(this.flags[relativeReadIndex]);
                    extrasHolder.size = this.sizes[relativeReadIndex];
                    extrasHolder.offset = this.offsets[relativeReadIndex];
                    extrasHolder.cryptoData = this.cryptoDatas[relativeReadIndex];
                    this.readPosition++;
                }
            } else if (loadingFinished) {
                buffer.setFlags(4);
            } else if (this.upstreamFormat == null || (!formatRequired && this.upstreamFormat == downstreamFormat)) {
                i = -3;
            } else {
                formatHolder.format = this.upstreamFormat;
                i = -5;
            }
        }
        return i;
    }

    public synchronized boolean advanceTo(long timeUs, boolean toKeyframe, boolean allowTimeBeyondBuffer) {
        boolean z;
        int relativeReadIndex = getRelativeIndex(this.readPosition);
        if (!hasNextSample() || timeUs < this.timesUs[relativeReadIndex] || (timeUs > this.largestQueuedTimestampUs && !allowTimeBeyondBuffer)) {
            z = false;
        } else {
            int offset = findSampleBefore(relativeReadIndex, this.length - this.readPosition, timeUs, toKeyframe);
            if (offset == -1) {
                z = false;
            } else {
                this.readPosition += offset;
                z = true;
            }
        }
        return z;
    }

    public synchronized void advanceToEnd() {
        if (hasNextSample()) {
            this.readPosition = this.length;
        }
    }

    public synchronized long discardTo(long timeUs, boolean toKeyframe, boolean stopAtReadPosition) {
        long j;
        if (this.length == 0 || timeUs < this.timesUs[this.relativeStartIndex]) {
            j = -1;
        } else {
            int searchLength;
            int discardCount;
            if (stopAtReadPosition) {
                if (this.readPosition != this.length) {
                    searchLength = this.readPosition + 1;
                    discardCount = findSampleBefore(this.relativeStartIndex, searchLength, timeUs, toKeyframe);
                    if (discardCount != -1) {
                        j = -1;
                    } else {
                        j = discardSamples(discardCount);
                    }
                }
            }
            searchLength = this.length;
            discardCount = findSampleBefore(this.relativeStartIndex, searchLength, timeUs, toKeyframe);
            if (discardCount != -1) {
                j = discardSamples(discardCount);
            } else {
                j = -1;
            }
        }
        return j;
    }

    public synchronized long discardToRead() {
        long j;
        if (this.readPosition == 0) {
            j = -1;
        } else {
            j = discardSamples(this.readPosition);
        }
        return j;
    }

    public synchronized long discardToEnd() {
        long j;
        if (this.length == 0) {
            j = -1;
        } else {
            j = discardSamples(this.length);
        }
        return j;
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

    public synchronized void commitSample(long timeUs, int sampleFlags, long offset, int size, CryptoData cryptoData) {
        if (this.upstreamKeyframeRequired) {
            if ((sampleFlags & 1) != 0) {
                this.upstreamKeyframeRequired = false;
            }
        }
        Assertions.checkState(!this.upstreamFormatRequired);
        commitSampleTimestamp(timeUs);
        int relativeEndIndex = getRelativeIndex(this.length);
        this.timesUs[relativeEndIndex] = timeUs;
        this.offsets[relativeEndIndex] = offset;
        this.sizes[relativeEndIndex] = size;
        this.flags[relativeEndIndex] = sampleFlags;
        this.cryptoDatas[relativeEndIndex] = cryptoData;
        this.formats[relativeEndIndex] = this.upstreamFormat;
        this.sourceIds[relativeEndIndex] = this.upstreamSourceId;
        this.length++;
        if (this.length == this.capacity) {
            int newCapacity = this.capacity + 1000;
            int[] newSourceIds = new int[newCapacity];
            long[] newOffsets = new long[newCapacity];
            long[] newTimesUs = new long[newCapacity];
            int[] newFlags = new int[newCapacity];
            int[] newSizes = new int[newCapacity];
            CryptoData[] newCryptoDatas = new CryptoData[newCapacity];
            Format[] newFormats = new Format[newCapacity];
            int beforeWrap = this.capacity - this.relativeStartIndex;
            System.arraycopy(this.offsets, this.relativeStartIndex, newOffsets, 0, beforeWrap);
            System.arraycopy(this.timesUs, this.relativeStartIndex, newTimesUs, 0, beforeWrap);
            System.arraycopy(this.flags, this.relativeStartIndex, newFlags, 0, beforeWrap);
            System.arraycopy(this.sizes, this.relativeStartIndex, newSizes, 0, beforeWrap);
            System.arraycopy(this.cryptoDatas, this.relativeStartIndex, newCryptoDatas, 0, beforeWrap);
            System.arraycopy(this.formats, this.relativeStartIndex, newFormats, 0, beforeWrap);
            System.arraycopy(this.sourceIds, this.relativeStartIndex, newSourceIds, 0, beforeWrap);
            int afterWrap = this.relativeStartIndex;
            System.arraycopy(this.offsets, 0, newOffsets, beforeWrap, afterWrap);
            System.arraycopy(this.timesUs, 0, newTimesUs, beforeWrap, afterWrap);
            System.arraycopy(this.flags, 0, newFlags, beforeWrap, afterWrap);
            System.arraycopy(this.sizes, 0, newSizes, beforeWrap, afterWrap);
            System.arraycopy(this.cryptoDatas, 0, newCryptoDatas, beforeWrap, afterWrap);
            System.arraycopy(this.formats, 0, newFormats, beforeWrap, afterWrap);
            System.arraycopy(this.sourceIds, 0, newSourceIds, beforeWrap, afterWrap);
            this.offsets = newOffsets;
            this.timesUs = newTimesUs;
            this.flags = newFlags;
            this.sizes = newSizes;
            this.cryptoDatas = newCryptoDatas;
            this.formats = newFormats;
            this.sourceIds = newSourceIds;
            this.relativeStartIndex = 0;
            this.length = this.capacity;
            this.capacity = newCapacity;
        }
    }

    public synchronized void commitSampleTimestamp(long timeUs) {
        this.largestQueuedTimestampUs = Math.max(this.largestQueuedTimestampUs, timeUs);
    }

    public synchronized boolean attemptSplice(long timeUs) {
        boolean z = true;
        synchronized (this) {
            if (this.length == 0) {
                if (timeUs <= this.largestDiscardedTimestampUs) {
                    z = false;
                }
            } else if (Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.readPosition)) >= timeUs) {
                z = false;
            } else {
                int retainCount = this.length;
                int relativeSampleIndex = getRelativeIndex(this.length - 1);
                while (retainCount > this.readPosition && this.timesUs[relativeSampleIndex] >= timeUs) {
                    retainCount--;
                    relativeSampleIndex--;
                    if (relativeSampleIndex == -1) {
                        relativeSampleIndex = this.capacity - 1;
                    }
                }
                discardUpstreamSamples(this.absoluteStartIndex + retainCount);
            }
        }
        return z;
    }

    private int findSampleBefore(int relativeStartIndex, int length, long timeUs, boolean keyframe) {
        int sampleCountToTarget = -1;
        int searchIndex = relativeStartIndex;
        for (int i = 0; i < length && this.timesUs[searchIndex] <= timeUs; i++) {
            if (!(keyframe && (this.flags[searchIndex] & 1) == 0)) {
                sampleCountToTarget = i;
            }
            searchIndex++;
            if (searchIndex == this.capacity) {
                searchIndex = 0;
            }
        }
        return sampleCountToTarget;
    }

    private long discardSamples(int discardCount) {
        this.largestDiscardedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(discardCount));
        this.length -= discardCount;
        this.absoluteStartIndex += discardCount;
        this.relativeStartIndex += discardCount;
        if (this.relativeStartIndex >= this.capacity) {
            this.relativeStartIndex -= this.capacity;
        }
        this.readPosition -= discardCount;
        if (this.readPosition < 0) {
            this.readPosition = 0;
        }
        if (this.length != 0) {
            return this.offsets[this.relativeStartIndex];
        }
        int relativeLastDiscardIndex = (this.relativeStartIndex == 0 ? this.capacity : this.relativeStartIndex) - 1;
        return this.offsets[relativeLastDiscardIndex] + ((long) this.sizes[relativeLastDiscardIndex]);
    }

    private long getLargestTimestamp(int length) {
        if (length == 0) {
            return Long.MIN_VALUE;
        }
        long largestTimestampUs = Long.MIN_VALUE;
        int relativeSampleIndex = getRelativeIndex(length - 1);
        for (int i = 0; i < length; i++) {
            largestTimestampUs = Math.max(largestTimestampUs, this.timesUs[relativeSampleIndex]);
            if ((this.flags[relativeSampleIndex] & 1) != 0) {
                return largestTimestampUs;
            }
            relativeSampleIndex--;
            if (relativeSampleIndex == -1) {
                relativeSampleIndex = this.capacity - 1;
            }
        }
        return largestTimestampUs;
    }

    private int getRelativeIndex(int offset) {
        int relativeIndex = this.relativeStartIndex + offset;
        return relativeIndex < this.capacity ? relativeIndex : relativeIndex - this.capacity;
    }
}
