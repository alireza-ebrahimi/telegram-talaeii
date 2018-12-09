package org.telegram.messenger.exoplayer2.source;

import java.io.EOFException;
import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.source.SampleMetadataQueue.SampleExtrasHolder;
import org.telegram.messenger.exoplayer2.upstream.Allocation;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class SampleQueue implements TrackOutput {
    private static final int INITIAL_SCRATCH_SIZE = 32;
    private final int allocationLength;
    private final Allocator allocator;
    private Format downstreamFormat;
    private final SampleExtrasHolder extrasHolder = new SampleExtrasHolder();
    private AllocationNode firstAllocationNode = new AllocationNode(0, this.allocationLength);
    private Format lastUnadjustedFormat;
    private final SampleMetadataQueue metadataQueue = new SampleMetadataQueue();
    private boolean pendingFormatAdjustment;
    private boolean pendingSplice;
    private AllocationNode readAllocationNode = this.firstAllocationNode;
    private long sampleOffsetUs;
    private final ParsableByteArray scratch = new ParsableByteArray(32);
    private long totalBytesWritten;
    private UpstreamFormatChangedListener upstreamFormatChangeListener;
    private AllocationNode writeAllocationNode = this.firstAllocationNode;

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    private static final class AllocationNode {
        public Allocation allocation;
        public final long endPosition;
        public AllocationNode next;
        public final long startPosition;
        public boolean wasInitialized;

        public AllocationNode(long j, int i) {
            this.startPosition = j;
            this.endPosition = ((long) i) + j;
        }

        public AllocationNode clear() {
            this.allocation = null;
            AllocationNode allocationNode = this.next;
            this.next = null;
            return allocationNode;
        }

        public void initialize(Allocation allocation, AllocationNode allocationNode) {
            this.allocation = allocation;
            this.next = allocationNode;
            this.wasInitialized = true;
        }

        public int translateOffset(long j) {
            return ((int) (j - this.startPosition)) + this.allocation.offset;
        }
    }

    public SampleQueue(Allocator allocator) {
        this.allocator = allocator;
        this.allocationLength = allocator.getIndividualAllocationLength();
    }

    private void advanceReadTo(long j) {
        while (j >= this.readAllocationNode.endPosition) {
            this.readAllocationNode = this.readAllocationNode.next;
        }
    }

    private void clearAllocationNodes(AllocationNode allocationNode) {
        int i = 0;
        if (allocationNode.wasInitialized) {
            Allocation[] allocationArr = new Allocation[((this.writeAllocationNode.wasInitialized ? 1 : 0) + (((int) (this.writeAllocationNode.startPosition - allocationNode.startPosition)) / this.allocationLength))];
            while (i < allocationArr.length) {
                allocationArr[i] = allocationNode.allocation;
                allocationNode = allocationNode.clear();
                i++;
            }
            this.allocator.release(allocationArr);
        }
    }

    private void discardDownstreamTo(long j) {
        if (j != -1) {
            while (j >= this.firstAllocationNode.endPosition) {
                this.allocator.release(this.firstAllocationNode.allocation);
                this.firstAllocationNode = this.firstAllocationNode.clear();
            }
            if (this.readAllocationNode.startPosition < this.firstAllocationNode.startPosition) {
                this.readAllocationNode = this.firstAllocationNode;
            }
        }
    }

    private static Format getAdjustedSampleFormat(Format format, long j) {
        return format == null ? null : (j == 0 || format.subsampleOffsetUs == Long.MAX_VALUE) ? format : format.copyWithSubsampleOffsetUs(format.subsampleOffsetUs + j);
    }

    private void postAppend(int i) {
        this.totalBytesWritten += (long) i;
        if (this.totalBytesWritten == this.writeAllocationNode.endPosition) {
            this.writeAllocationNode = this.writeAllocationNode.next;
        }
    }

    private int preAppend(int i) {
        if (!this.writeAllocationNode.wasInitialized) {
            this.writeAllocationNode.initialize(this.allocator.allocate(), new AllocationNode(this.writeAllocationNode.endPosition, this.allocationLength));
        }
        return Math.min(i, (int) (this.writeAllocationNode.endPosition - this.totalBytesWritten));
    }

    private void readData(long j, ByteBuffer byteBuffer, int i) {
        advanceReadTo(j);
        while (i > 0) {
            int min = Math.min(i, (int) (this.readAllocationNode.endPosition - j));
            byteBuffer.put(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(j), min);
            i -= min;
            j += (long) min;
            if (j == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void readData(long j, byte[] bArr, int i) {
        advanceReadTo(j);
        int i2 = i;
        while (i2 > 0) {
            int min = Math.min(i2, (int) (this.readAllocationNode.endPosition - j));
            System.arraycopy(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(j), bArr, i - i2, min);
            i2 -= min;
            j += (long) min;
            if (j == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void readEncryptionData(DecoderInputBuffer decoderInputBuffer, SampleExtrasHolder sampleExtrasHolder) {
        long j;
        long j2;
        int i = 1;
        int i2 = 0;
        long j3 = sampleExtrasHolder.offset;
        this.scratch.reset(1);
        readData(j3, this.scratch.data, 1);
        j3++;
        byte b = this.scratch.data[0];
        int i3 = (b & 128) != 0 ? 1 : 0;
        int i4 = b & 127;
        if (decoderInputBuffer.cryptoInfo.iv == null) {
            decoderInputBuffer.cryptoInfo.iv = new byte[16];
        }
        readData(j3, decoderInputBuffer.cryptoInfo.iv, i4);
        j3 += (long) i4;
        if (i3 != 0) {
            this.scratch.reset(2);
            readData(j3, this.scratch.data, 2);
            j3 += 2;
            i = this.scratch.readUnsignedShort();
            j = j3;
        } else {
            j = j3;
        }
        int[] iArr = decoderInputBuffer.cryptoInfo.numBytesOfClearData;
        if (iArr == null || iArr.length < i) {
            iArr = new int[i];
        }
        int[] iArr2 = decoderInputBuffer.cryptoInfo.numBytesOfEncryptedData;
        if (iArr2 == null || iArr2.length < i) {
            iArr2 = new int[i];
        }
        if (i3 != 0) {
            i3 = i * 6;
            this.scratch.reset(i3);
            readData(j, this.scratch.data, i3);
            j += (long) i3;
            this.scratch.setPosition(0);
            while (i2 < i) {
                iArr[i2] = this.scratch.readUnsignedShort();
                iArr2[i2] = this.scratch.readUnsignedIntToInt();
                i2++;
            }
            j2 = j;
        } else {
            iArr[0] = 0;
            iArr2[0] = sampleExtrasHolder.size - ((int) (j - sampleExtrasHolder.offset));
            j2 = j;
        }
        CryptoData cryptoData = sampleExtrasHolder.cryptoData;
        decoderInputBuffer.cryptoInfo.set(i, iArr, iArr2, cryptoData.encryptionKey, decoderInputBuffer.cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        i3 = (int) (j2 - sampleExtrasHolder.offset);
        sampleExtrasHolder.offset += (long) i3;
        sampleExtrasHolder.size -= i3;
    }

    public boolean advanceTo(long j, boolean z, boolean z2) {
        return this.metadataQueue.advanceTo(j, z, z2);
    }

    public void advanceToEnd() {
        this.metadataQueue.advanceToEnd();
    }

    public void discardTo(long j, boolean z, boolean z2) {
        discardDownstreamTo(this.metadataQueue.discardTo(j, z, z2));
    }

    public void discardToEnd() {
        discardDownstreamTo(this.metadataQueue.discardToEnd());
    }

    public void discardToRead() {
        discardDownstreamTo(this.metadataQueue.discardToRead());
    }

    public void discardUpstreamSamples(int i) {
        this.totalBytesWritten = this.metadataQueue.discardUpstreamSamples(i);
        if (this.totalBytesWritten == 0 || this.totalBytesWritten == this.firstAllocationNode.startPosition) {
            clearAllocationNodes(this.firstAllocationNode);
            this.firstAllocationNode = new AllocationNode(this.totalBytesWritten, this.allocationLength);
            this.readAllocationNode = this.firstAllocationNode;
            this.writeAllocationNode = this.firstAllocationNode;
            return;
        }
        AllocationNode allocationNode = this.firstAllocationNode;
        while (this.totalBytesWritten > allocationNode.endPosition) {
            allocationNode = allocationNode.next;
        }
        AllocationNode allocationNode2 = allocationNode.next;
        clearAllocationNodes(allocationNode2);
        allocationNode.next = new AllocationNode(allocationNode.endPosition, this.allocationLength);
        this.writeAllocationNode = this.totalBytesWritten == allocationNode.endPosition ? allocationNode.next : allocationNode;
        if (this.readAllocationNode == allocationNode2) {
            this.readAllocationNode = allocationNode.next;
        }
    }

    public void format(Format format) {
        Format adjustedSampleFormat = getAdjustedSampleFormat(format, this.sampleOffsetUs);
        boolean format2 = this.metadataQueue.format(adjustedSampleFormat);
        this.lastUnadjustedFormat = format;
        this.pendingFormatAdjustment = false;
        if (this.upstreamFormatChangeListener != null && format2) {
            this.upstreamFormatChangeListener.onUpstreamFormatChanged(adjustedSampleFormat);
        }
    }

    public long getLargestQueuedTimestampUs() {
        return this.metadataQueue.getLargestQueuedTimestampUs();
    }

    public int getReadIndex() {
        return this.metadataQueue.getReadIndex();
    }

    public Format getUpstreamFormat() {
        return this.metadataQueue.getUpstreamFormat();
    }

    public int getWriteIndex() {
        return this.metadataQueue.getWriteIndex();
    }

    public boolean hasNextSample() {
        return this.metadataQueue.hasNextSample();
    }

    public int peekSourceId() {
        return this.metadataQueue.peekSourceId();
    }

    public int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z, boolean z2, long j) {
        switch (this.metadataQueue.read(formatHolder, decoderInputBuffer, z, z2, this.downstreamFormat, this.extrasHolder)) {
            case C3446C.RESULT_FORMAT_READ /*-5*/:
                this.downstreamFormat = formatHolder.format;
                return -5;
            case C3446C.RESULT_BUFFER_READ /*-4*/:
                if (!decoderInputBuffer.isEndOfStream()) {
                    if (decoderInputBuffer.timeUs < j) {
                        decoderInputBuffer.addFlag(Integer.MIN_VALUE);
                    }
                    if (decoderInputBuffer.isEncrypted()) {
                        readEncryptionData(decoderInputBuffer, this.extrasHolder);
                    }
                    decoderInputBuffer.ensureSpaceForWrite(this.extrasHolder.size);
                    readData(this.extrasHolder.offset, decoderInputBuffer.data, this.extrasHolder.size);
                }
                return -4;
            case -3:
                return -3;
            default:
                throw new IllegalStateException();
        }
    }

    public void reset() {
        reset(false);
    }

    public void reset(boolean z) {
        this.metadataQueue.reset(z);
        clearAllocationNodes(this.firstAllocationNode);
        this.firstAllocationNode = new AllocationNode(0, this.allocationLength);
        this.readAllocationNode = this.firstAllocationNode;
        this.writeAllocationNode = this.firstAllocationNode;
        this.totalBytesWritten = 0;
        this.allocator.trim();
    }

    public void rewind() {
        this.metadataQueue.rewind();
        this.readAllocationNode = this.firstAllocationNode;
    }

    public int sampleData(ExtractorInput extractorInput, int i, boolean z) {
        int read = extractorInput.read(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend(i));
        if (read != -1) {
            postAppend(read);
            return read;
        } else if (z) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void sampleData(ParsableByteArray parsableByteArray, int i) {
        while (i > 0) {
            int preAppend = preAppend(i);
            parsableByteArray.readBytes(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend);
            i -= preAppend;
            postAppend(preAppend);
        }
    }

    public void sampleMetadata(long j, int i, int i2, int i3, CryptoData cryptoData) {
        if (this.pendingFormatAdjustment) {
            format(this.lastUnadjustedFormat);
        }
        if (this.pendingSplice) {
            if ((i & 1) != 0 && this.metadataQueue.attemptSplice(j)) {
                this.pendingSplice = false;
            } else {
                return;
            }
        }
        this.metadataQueue.commitSample(j + this.sampleOffsetUs, i, (this.totalBytesWritten - ((long) i2)) - ((long) i3), i2, cryptoData);
    }

    public void setSampleOffsetUs(long j) {
        if (this.sampleOffsetUs != j) {
            this.sampleOffsetUs = j;
            this.pendingFormatAdjustment = true;
        }
    }

    public void setUpstreamFormatChangeListener(UpstreamFormatChangedListener upstreamFormatChangedListener) {
        this.upstreamFormatChangeListener = upstreamFormatChangedListener;
    }

    public void sourceId(int i) {
        this.metadataQueue.sourceId(i);
    }

    public void splice() {
        this.pendingSplice = true;
    }
}
