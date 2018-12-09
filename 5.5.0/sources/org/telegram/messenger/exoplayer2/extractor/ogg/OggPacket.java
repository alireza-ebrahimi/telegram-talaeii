package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.util.Arrays;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class OggPacket {
    private int currentSegmentIndex = -1;
    private final ParsableByteArray packetArray = new ParsableByteArray(new byte[OggPageHeader.MAX_PAGE_PAYLOAD], 0);
    private final OggPageHeader pageHeader = new OggPageHeader();
    private boolean populated;
    private int segmentCount;

    OggPacket() {
    }

    private int calculatePacketSize(int i) {
        int i2 = 0;
        this.segmentCount = 0;
        while (this.segmentCount + i < this.pageHeader.pageSegmentCount) {
            int[] iArr = this.pageHeader.laces;
            int i3 = this.segmentCount;
            this.segmentCount = i3 + 1;
            int i4 = iArr[i3 + i];
            i2 += i4;
            if (i4 != 255) {
                break;
            }
        }
        return i2;
    }

    public OggPageHeader getPageHeader() {
        return this.pageHeader;
    }

    public ParsableByteArray getPayload() {
        return this.packetArray;
    }

    public boolean populate(ExtractorInput extractorInput) {
        Assertions.checkState(extractorInput != null);
        if (this.populated) {
            this.populated = false;
            this.packetArray.reset();
        }
        while (!this.populated) {
            int i;
            int i2;
            if (this.currentSegmentIndex < 0) {
                if (!this.pageHeader.populate(extractorInput, true)) {
                    return false;
                }
                i = this.pageHeader.headerSize;
                if ((this.pageHeader.type & 1) == 1 && this.packetArray.limit() == 0) {
                    i += calculatePacketSize(0);
                    i2 = this.segmentCount + 0;
                } else {
                    i2 = 0;
                }
                extractorInput.skipFully(i);
                this.currentSegmentIndex = i2;
            }
            i = calculatePacketSize(this.currentSegmentIndex);
            i2 = this.currentSegmentIndex + this.segmentCount;
            if (i > 0) {
                if (this.packetArray.capacity() < this.packetArray.limit() + i) {
                    this.packetArray.data = Arrays.copyOf(this.packetArray.data, this.packetArray.limit() + i);
                }
                extractorInput.readFully(this.packetArray.data, this.packetArray.limit(), i);
                this.packetArray.setLimit(i + this.packetArray.limit());
                this.populated = this.pageHeader.laces[i2 + -1] != 255;
            }
            this.currentSegmentIndex = i2 == this.pageHeader.pageSegmentCount ? -1 : i2;
        }
        return true;
    }

    public void reset() {
        this.pageHeader.reset();
        this.packetArray.reset();
        this.currentSegmentIndex = -1;
        this.populated = false;
    }

    public void trimPayload() {
        if (this.packetArray.data.length != OggPageHeader.MAX_PAGE_PAYLOAD) {
            this.packetArray.data = Arrays.copyOf(this.packetArray.data, Math.max(OggPageHeader.MAX_PAGE_PAYLOAD, this.packetArray.limit()));
        }
    }
}
