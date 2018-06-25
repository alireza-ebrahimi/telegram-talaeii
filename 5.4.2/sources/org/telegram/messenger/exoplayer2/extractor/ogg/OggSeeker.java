package org.telegram.messenger.exoplayer2.extractor.ogg;

import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;

interface OggSeeker {
    SeekMap createSeekMap();

    long read(ExtractorInput extractorInput);

    long startSeek(long j);
}
