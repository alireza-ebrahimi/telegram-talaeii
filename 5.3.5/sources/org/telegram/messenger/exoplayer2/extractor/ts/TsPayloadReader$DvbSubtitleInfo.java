package org.telegram.messenger.exoplayer2.extractor.ts;

public final class TsPayloadReader$DvbSubtitleInfo {
    public final byte[] initializationData;
    public final String language;
    public final int type;

    public TsPayloadReader$DvbSubtitleInfo(String language, int type, byte[] initializationData) {
        this.language = language;
        this.type = type;
        this.initializationData = initializationData;
    }
}
