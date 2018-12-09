package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.Log;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public final class PesReader implements TsPayloadReader {
    private static final int HEADER_SIZE = 9;
    private static final int MAX_HEADER_EXTENSION_SIZE = 10;
    private static final int PES_SCRATCH_SIZE = 10;
    private static final int STATE_FINDING_HEADER = 0;
    private static final int STATE_READING_BODY = 3;
    private static final int STATE_READING_HEADER = 1;
    private static final int STATE_READING_HEADER_EXTENSION = 2;
    private static final String TAG = "PesReader";
    private int bytesRead;
    private boolean dataAlignmentIndicator;
    private boolean dtsFlag;
    private int extendedHeaderLength;
    private int payloadSize;
    private final ParsableBitArray pesScratch = new ParsableBitArray(new byte[10]);
    private boolean ptsFlag;
    private final ElementaryStreamReader reader;
    private boolean seenFirstDts;
    private int state = 0;
    private long timeUs;
    private TimestampAdjuster timestampAdjuster;

    public PesReader(ElementaryStreamReader elementaryStreamReader) {
        this.reader = elementaryStreamReader;
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.bytesRead);
        if (min <= 0) {
            return true;
        }
        if (bArr == null) {
            parsableByteArray.skipBytes(min);
        } else {
            parsableByteArray.readBytes(bArr, this.bytesRead, min);
        }
        this.bytesRead = min + this.bytesRead;
        return this.bytesRead == i;
    }

    private boolean parseHeader() {
        this.pesScratch.setPosition(0);
        int readBits = this.pesScratch.readBits(24);
        if (readBits != 1) {
            Log.w(TAG, "Unexpected start code prefix: " + readBits);
            this.payloadSize = -1;
            return false;
        }
        this.pesScratch.skipBits(8);
        int readBits2 = this.pesScratch.readBits(16);
        this.pesScratch.skipBits(5);
        this.dataAlignmentIndicator = this.pesScratch.readBit();
        this.pesScratch.skipBits(2);
        this.ptsFlag = this.pesScratch.readBit();
        this.dtsFlag = this.pesScratch.readBit();
        this.pesScratch.skipBits(6);
        this.extendedHeaderLength = this.pesScratch.readBits(8);
        if (readBits2 == 0) {
            this.payloadSize = -1;
        } else {
            this.payloadSize = ((readBits2 + 6) - 9) - this.extendedHeaderLength;
        }
        return true;
    }

    private void parseHeaderExtension() {
        this.pesScratch.setPosition(0);
        this.timeUs = C3446C.TIME_UNSET;
        if (this.ptsFlag) {
            this.pesScratch.skipBits(4);
            long readBits = ((long) this.pesScratch.readBits(3)) << 30;
            this.pesScratch.skipBits(1);
            readBits |= (long) (this.pesScratch.readBits(15) << 15);
            this.pesScratch.skipBits(1);
            readBits |= (long) this.pesScratch.readBits(15);
            this.pesScratch.skipBits(1);
            if (!this.seenFirstDts && this.dtsFlag) {
                this.pesScratch.skipBits(4);
                long readBits2 = ((long) this.pesScratch.readBits(3)) << 30;
                this.pesScratch.skipBits(1);
                readBits2 |= (long) (this.pesScratch.readBits(15) << 15);
                this.pesScratch.skipBits(1);
                readBits2 |= (long) this.pesScratch.readBits(15);
                this.pesScratch.skipBits(1);
                this.timestampAdjuster.adjustTsTimestamp(readBits2);
                this.seenFirstDts = true;
            }
            this.timeUs = this.timestampAdjuster.adjustTsTimestamp(readBits);
        }
    }

    private void setState(int i) {
        this.state = i;
        this.bytesRead = 0;
    }

    public final void consume(ParsableByteArray parsableByteArray, boolean z) {
        if (z) {
            switch (this.state) {
                case 2:
                    Log.w(TAG, "Unexpected start indicator reading extended header");
                    break;
                case 3:
                    if (this.payloadSize != -1) {
                        Log.w(TAG, "Unexpected start indicator: expected " + this.payloadSize + " more bytes");
                    }
                    this.reader.packetFinished();
                    break;
            }
            setState(1);
        }
        while (parsableByteArray.bytesLeft() > 0) {
            switch (this.state) {
                case 0:
                    parsableByteArray.skipBytes(parsableByteArray.bytesLeft());
                    break;
                case 1:
                    if (!continueRead(parsableByteArray, this.pesScratch.data, 9)) {
                        break;
                    }
                    setState(parseHeader() ? 2 : 0);
                    break;
                case 2:
                    if (continueRead(parsableByteArray, this.pesScratch.data, Math.min(10, this.extendedHeaderLength)) && continueRead(parsableByteArray, null, this.extendedHeaderLength)) {
                        parseHeaderExtension();
                        this.reader.packetStarted(this.timeUs, this.dataAlignmentIndicator);
                        setState(3);
                        break;
                    }
                case 3:
                    int bytesLeft = parsableByteArray.bytesLeft();
                    int i = this.payloadSize == -1 ? 0 : bytesLeft - this.payloadSize;
                    if (i > 0) {
                        bytesLeft -= i;
                        parsableByteArray.setLimit(parsableByteArray.getPosition() + bytesLeft);
                    }
                    this.reader.consume(parsableByteArray);
                    if (this.payloadSize == -1) {
                        break;
                    }
                    this.payloadSize -= bytesLeft;
                    if (this.payloadSize != 0) {
                        break;
                    }
                    this.reader.packetFinished();
                    setState(1);
                    break;
                default:
                    break;
            }
        }
    }

    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        this.timestampAdjuster = timestampAdjuster;
        this.reader.createTracks(extractorOutput, trackIdGenerator);
    }

    public final void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.seenFirstDts = false;
        this.reader.seek();
    }
}
