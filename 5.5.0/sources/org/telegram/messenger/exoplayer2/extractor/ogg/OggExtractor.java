package org.telegram.messenger.exoplayer2.extractor.ogg;

import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public class OggExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new C34861();
    private static final int MAX_VERIFICATION_BYTES = 8;
    private ExtractorOutput output;
    private StreamReader streamReader;
    private boolean streamReaderInitialized;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.ogg.OggExtractor$1 */
    static class C34861 implements ExtractorsFactory {
        C34861() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new OggExtractor()};
        }
    }

    private static ParsableByteArray resetPosition(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(0);
        return parsableByteArray;
    }

    private boolean sniffInternal(ExtractorInput extractorInput) {
        OggPageHeader oggPageHeader = new OggPageHeader();
        if (!oggPageHeader.populate(extractorInput, true) || (oggPageHeader.type & 2) != 2) {
            return false;
        }
        int min = Math.min(oggPageHeader.bodySize, 8);
        ParsableByteArray parsableByteArray = new ParsableByteArray(min);
        extractorInput.peekFully(parsableByteArray.data, 0, min);
        if (FlacReader.verifyBitstreamType(resetPosition(parsableByteArray))) {
            this.streamReader = new FlacReader();
        } else if (VorbisReader.verifyBitstreamType(resetPosition(parsableByteArray))) {
            this.streamReader = new VorbisReader();
        } else if (!OpusReader.verifyBitstreamType(resetPosition(parsableByteArray))) {
            return false;
        } else {
            this.streamReader = new OpusReader();
        }
        return true;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.output = extractorOutput;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        if (this.streamReader == null) {
            if (sniffInternal(extractorInput)) {
                extractorInput.resetPeekPosition();
            } else {
                throw new ParserException("Failed to determine bitstream type");
            }
        }
        if (!this.streamReaderInitialized) {
            TrackOutput track = this.output.track(0, 1);
            this.output.endTracks();
            this.streamReader.init(this.output, track);
            this.streamReaderInitialized = true;
        }
        return this.streamReader.read(extractorInput, positionHolder);
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        if (this.streamReader != null) {
            this.streamReader.seek(j, j2);
        }
    }

    public boolean sniff(ExtractorInput extractorInput) {
        try {
            return sniffInternal(extractorInput);
        } catch (ParserException e) {
            return false;
        }
    }
}
