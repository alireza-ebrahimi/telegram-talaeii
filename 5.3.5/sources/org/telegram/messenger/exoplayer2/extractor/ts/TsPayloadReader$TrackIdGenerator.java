package org.telegram.messenger.exoplayer2.extractor.ts;

public final class TsPayloadReader$TrackIdGenerator {
    private static final int ID_UNSET = Integer.MIN_VALUE;
    private final int firstTrackId;
    private String formatId;
    private final String formatIdPrefix;
    private int trackId;
    private final int trackIdIncrement;

    public TsPayloadReader$TrackIdGenerator(int firstTrackId, int trackIdIncrement) {
        this(Integer.MIN_VALUE, firstTrackId, trackIdIncrement);
    }

    public TsPayloadReader$TrackIdGenerator(int programNumber, int firstTrackId, int trackIdIncrement) {
        this.formatIdPrefix = programNumber != Integer.MIN_VALUE ? programNumber + "/" : "";
        this.firstTrackId = firstTrackId;
        this.trackIdIncrement = trackIdIncrement;
        this.trackId = Integer.MIN_VALUE;
    }

    public void generateNewId() {
        this.trackId = this.trackId == Integer.MIN_VALUE ? this.firstTrackId : this.trackId + this.trackIdIncrement;
        this.formatId = this.formatIdPrefix + this.trackId;
    }

    public int getTrackId() {
        maybeThrowUninitializedError();
        return this.trackId;
    }

    public String getFormatId() {
        maybeThrowUninitializedError();
        return this.formatId;
    }

    private void maybeThrowUninitializedError() {
        if (this.trackId == Integer.MIN_VALUE) {
            throw new IllegalStateException("generateNewId() must be called before retrieving ids.");
        }
    }
}
