package org.telegram.messenger.exoplayer2.extractor.rawcc;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

public final class RawCcExtractor implements Extractor {
    private static final int HEADER_ID = Util.getIntegerCodeForString("RCC\u0001");
    private static final int HEADER_SIZE = 8;
    private static final int SCRATCH_SIZE = 9;
    private static final int STATE_READING_HEADER = 0;
    private static final int STATE_READING_SAMPLES = 2;
    private static final int STATE_READING_TIMESTAMP_AND_COUNT = 1;
    private static final int TIMESTAMP_SIZE_V0 = 4;
    private static final int TIMESTAMP_SIZE_V1 = 8;
    private final ParsableByteArray dataScratch = new ParsableByteArray(9);
    private final Format format;
    private int parserState = 0;
    private int remainingSampleCount;
    private int sampleBytesWritten;
    private long timestampUs;
    private TrackOutput trackOutput;
    private int version;

    public RawCcExtractor(Format format) {
        this.format = format;
    }

    public void init(ExtractorOutput output) {
        output.seekMap(new Unseekable(C0907C.TIME_UNSET));
        this.trackOutput = output.track(0, 3);
        output.endTracks();
        this.trackOutput.format(this.format);
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        input.peekFully(this.dataScratch.data, 0, 8);
        if (this.dataScratch.readInt() == HEADER_ID) {
            return true;
        }
        return false;
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        while (true) {
            switch (this.parserState) {
                case 0:
                    if (parseHeader(input)) {
                        this.parserState = 1;
                        break;
                    }
                    return -1;
                case 1:
                    if (parseTimestampAndSampleCount(input)) {
                        this.parserState = 2;
                        break;
                    }
                    this.parserState = 0;
                    return -1;
                case 2:
                    parseSamples(input);
                    this.parserState = 1;
                    return 0;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public void seek(long position, long timeUs) {
        this.parserState = 0;
    }

    public void release() {
    }

    private boolean parseHeader(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        if (!input.readFully(this.dataScratch.data, 0, 8, true)) {
            return false;
        }
        if (this.dataScratch.readInt() != HEADER_ID) {
            throw new IOException("Input not RawCC");
        }
        this.version = this.dataScratch.readUnsignedByte();
        return true;
    }

    private boolean parseTimestampAndSampleCount(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        if (this.version == 0) {
            if (!input.readFully(this.dataScratch.data, 0, 5, true)) {
                return false;
            }
            this.timestampUs = (this.dataScratch.readUnsignedInt() * 1000) / 45;
        } else if (this.version != 1) {
            throw new ParserException("Unsupported version number: " + this.version);
        } else if (!input.readFully(this.dataScratch.data, 0, 9, true)) {
            return false;
        } else {
            this.timestampUs = this.dataScratch.readLong();
        }
        this.remainingSampleCount = this.dataScratch.readUnsignedByte();
        this.sampleBytesWritten = 0;
        return true;
    }

    private void parseSamples(ExtractorInput input) throws IOException, InterruptedException {
        while (this.remainingSampleCount > 0) {
            this.dataScratch.reset();
            input.readFully(this.dataScratch.data, 0, 3);
            this.trackOutput.sampleData(this.dataScratch, 3);
            this.sampleBytesWritten += 3;
            this.remainingSampleCount--;
        }
        if (this.sampleBytesWritten > 0) {
            this.trackOutput.sampleMetadata(this.timestampUs, 1, this.sampleBytesWritten, 0, null);
        }
    }
}
