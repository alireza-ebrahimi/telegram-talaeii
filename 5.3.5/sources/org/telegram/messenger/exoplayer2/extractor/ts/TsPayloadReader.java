package org.telegram.messenger.exoplayer2.extractor.ts;

import android.util.SparseArray;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public interface TsPayloadReader {

    public interface Factory {
        SparseArray<TsPayloadReader> createInitialPayloadReaders();

        TsPayloadReader createPayloadReader(int i, EsInfo esInfo);
    }

    public static final class EsInfo {
        public final byte[] descriptorBytes;
        public final List<TsPayloadReader$DvbSubtitleInfo> dvbSubtitleInfos;
        public final String language;
        public final int streamType;

        public EsInfo(int streamType, String language, List<TsPayloadReader$DvbSubtitleInfo> dvbSubtitleInfos, byte[] descriptorBytes) {
            List emptyList;
            this.streamType = streamType;
            this.language = language;
            if (dvbSubtitleInfos == null) {
                emptyList = Collections.emptyList();
            } else {
                emptyList = Collections.unmodifiableList(dvbSubtitleInfos);
            }
            this.dvbSubtitleInfos = emptyList;
            this.descriptorBytes = descriptorBytes;
        }
    }

    void consume(ParsableByteArray parsableByteArray, boolean z);

    void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader$TrackIdGenerator tsPayloadReader$TrackIdGenerator);

    void seek();
}
