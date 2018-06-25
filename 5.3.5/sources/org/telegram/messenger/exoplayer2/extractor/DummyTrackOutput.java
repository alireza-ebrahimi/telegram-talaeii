package org.telegram.messenger.exoplayer2.extractor;

import java.io.EOFException;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DummyTrackOutput implements TrackOutput {
    public void format(Format format) {
    }

    public int sampleData(ExtractorInput input, int length, boolean allowEndOfInput) throws IOException, InterruptedException {
        int bytesSkipped = input.skip(length);
        if (bytesSkipped != -1) {
            return bytesSkipped;
        }
        if (allowEndOfInput) {
            return -1;
        }
        throw new EOFException();
    }

    public void sampleData(ParsableByteArray data, int length) {
        data.skipBytes(length);
    }

    public void sampleMetadata(long timeUs, int flags, int size, int offset, CryptoData cryptoData) {
    }
}
