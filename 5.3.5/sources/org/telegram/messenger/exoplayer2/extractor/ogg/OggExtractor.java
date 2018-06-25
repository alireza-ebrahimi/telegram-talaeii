package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.io.IOException;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public class OggExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new C17161();
    private static final int MAX_VERIFICATION_BYTES = 8;
    private ExtractorOutput output;
    private StreamReader streamReader;
    private boolean streamReaderInitialized;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.ogg.OggExtractor$1 */
    static class C17161 implements ExtractorsFactory {
        C17161() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new OggExtractor()};
        }
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        try {
            return sniffInternal(input);
        } catch (ParserException e) {
            return false;
        }
    }

    public void init(ExtractorOutput output) {
        this.output = output;
    }

    public void seek(long position, long timeUs) {
        if (this.streamReader != null) {
            this.streamReader.seek(position, timeUs);
        }
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        if (this.streamReader == null) {
            if (sniffInternal(input)) {
                input.resetPeekPosition();
            } else {
                throw new ParserException("Failed to determine bitstream type");
            }
        }
        if (!this.streamReaderInitialized) {
            TrackOutput trackOutput = this.output.track(0, 1);
            this.output.endTracks();
            this.streamReader.init(this.output, trackOutput);
            this.streamReaderInitialized = true;
        }
        return this.streamReader.read(input, seekPosition);
    }

    private boolean sniffInternal(ExtractorInput input) throws IOException, InterruptedException {
        OggPageHeader header = new OggPageHeader();
        if (!header.populate(input, true) || (header.type & 2) != 2) {
            return false;
        }
        int length = Math.min(header.bodySize, 8);
        ParsableByteArray scratch = new ParsableByteArray(length);
        input.peekFully(scratch.data, 0, length);
        if (FlacReader.verifyBitstreamType(resetPosition(scratch))) {
            this.streamReader = new FlacReader();
        } else if (VorbisReader.verifyBitstreamType(resetPosition(scratch))) {
            this.streamReader = new VorbisReader();
        } else if (!OpusReader.verifyBitstreamType(resetPosition(scratch))) {
            return false;
        } else {
            this.streamReader = new OpusReader();
        }
        return true;
    }

    private static ParsableByteArray resetPosition(ParsableByteArray scratch) {
        scratch.setPosition(0);
        return scratch;
    }
}
