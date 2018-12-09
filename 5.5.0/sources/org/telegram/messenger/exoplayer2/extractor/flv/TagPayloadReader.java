package org.telegram.messenger.exoplayer2.extractor.flv;

import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

abstract class TagPayloadReader {
    protected final TrackOutput output;

    public static final class UnsupportedFormatException extends ParserException {
        public UnsupportedFormatException(String str) {
            super(str);
        }
    }

    protected TagPayloadReader(TrackOutput trackOutput) {
        this.output = trackOutput;
    }

    public final void consume(ParsableByteArray parsableByteArray, long j) {
        if (parseHeader(parsableByteArray)) {
            parsePayload(parsableByteArray, j);
        }
    }

    protected abstract boolean parseHeader(ParsableByteArray parsableByteArray);

    protected abstract void parsePayload(ParsableByteArray parsableByteArray, long j);

    public abstract void seek();
}
