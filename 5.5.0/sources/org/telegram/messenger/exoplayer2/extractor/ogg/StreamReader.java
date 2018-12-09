package org.telegram.messenger.exoplayer2.extractor.ogg;

import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

abstract class StreamReader {
    private static final int STATE_END_OF_INPUT = 3;
    private static final int STATE_READ_HEADERS = 0;
    private static final int STATE_READ_PAYLOAD = 2;
    private static final int STATE_SKIP_HEADERS = 1;
    private long currentGranule;
    private ExtractorOutput extractorOutput;
    private boolean formatSet;
    private long lengthOfReadPacket;
    private final OggPacket oggPacket = new OggPacket();
    private OggSeeker oggSeeker;
    private long payloadStartPosition;
    private int sampleRate;
    private boolean seekMapSet;
    private SetupData setupData;
    private int state;
    private long targetGranule;
    private TrackOutput trackOutput;

    static class SetupData {
        Format format;
        OggSeeker oggSeeker;

        SetupData() {
        }
    }

    private static final class UnseekableOggSeeker implements OggSeeker {
        private UnseekableOggSeeker() {
        }

        public SeekMap createSeekMap() {
            return new Unseekable(C3446C.TIME_UNSET);
        }

        public long read(ExtractorInput extractorInput) {
            return -1;
        }

        public long startSeek(long j) {
            return 0;
        }
    }

    private int readHeaders(ExtractorInput extractorInput) {
        boolean z = true;
        while (z) {
            if (this.oggPacket.populate(extractorInput)) {
                this.lengthOfReadPacket = extractorInput.getPosition() - this.payloadStartPosition;
                z = readHeaders(this.oggPacket.getPayload(), this.payloadStartPosition, this.setupData);
                if (z) {
                    this.payloadStartPosition = extractorInput.getPosition();
                }
            } else {
                this.state = 3;
                return -1;
            }
        }
        this.sampleRate = this.setupData.format.sampleRate;
        if (!this.formatSet) {
            this.trackOutput.format(this.setupData.format);
            this.formatSet = true;
        }
        if (this.setupData.oggSeeker != null) {
            this.oggSeeker = this.setupData.oggSeeker;
        } else if (extractorInput.getLength() == -1) {
            this.oggSeeker = new UnseekableOggSeeker();
        } else {
            OggPageHeader pageHeader = this.oggPacket.getPageHeader();
            this.oggSeeker = new DefaultOggSeeker(this.payloadStartPosition, extractorInput.getLength(), this, pageHeader.bodySize + pageHeader.headerSize, pageHeader.granulePosition);
        }
        this.setupData = null;
        this.state = 2;
        this.oggPacket.trimPayload();
        return 0;
    }

    private int readPayload(ExtractorInput extractorInput, PositionHolder positionHolder) {
        long read = this.oggSeeker.read(extractorInput);
        if (read >= 0) {
            positionHolder.position = read;
            return 1;
        }
        if (read < -1) {
            onSeekEnd(-(read + 2));
        }
        if (!this.seekMapSet) {
            this.extractorOutput.seekMap(this.oggSeeker.createSeekMap());
            this.seekMapSet = true;
        }
        if (this.lengthOfReadPacket > 0 || this.oggPacket.populate(extractorInput)) {
            this.lengthOfReadPacket = 0;
            ParsableByteArray payload = this.oggPacket.getPayload();
            long preparePayload = preparePayload(payload);
            if (preparePayload >= 0 && this.currentGranule + preparePayload >= this.targetGranule) {
                long convertGranuleToTime = convertGranuleToTime(this.currentGranule);
                this.trackOutput.sampleData(payload, payload.limit());
                this.trackOutput.sampleMetadata(convertGranuleToTime, 1, payload.limit(), 0, null);
                this.targetGranule = -1;
            }
            this.currentGranule += preparePayload;
            return 0;
        }
        this.state = 3;
        return -1;
    }

    protected long convertGranuleToTime(long j) {
        return (C3446C.MICROS_PER_SECOND * j) / ((long) this.sampleRate);
    }

    protected long convertTimeToGranule(long j) {
        return (((long) this.sampleRate) * j) / C3446C.MICROS_PER_SECOND;
    }

    void init(ExtractorOutput extractorOutput, TrackOutput trackOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = trackOutput;
        reset(true);
    }

    protected void onSeekEnd(long j) {
        this.currentGranule = j;
    }

    protected abstract long preparePayload(ParsableByteArray parsableByteArray);

    final int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        switch (this.state) {
            case 0:
                return readHeaders(extractorInput);
            case 1:
                extractorInput.skipFully((int) this.payloadStartPosition);
                this.state = 2;
                return 0;
            case 2:
                return readPayload(extractorInput, positionHolder);
            default:
                throw new IllegalStateException();
        }
    }

    protected abstract boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData);

    protected void reset(boolean z) {
        if (z) {
            this.setupData = new SetupData();
            this.payloadStartPosition = 0;
            this.state = 0;
        } else {
            this.state = 1;
        }
        this.targetGranule = -1;
        this.currentGranule = 0;
    }

    final void seek(long j, long j2) {
        this.oggPacket.reset();
        if (j == 0) {
            reset(!this.seekMapSet);
        } else if (this.state != 0) {
            this.targetGranule = this.oggSeeker.startSeek(j2);
            this.state = 2;
        }
    }
}
