package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.io.EOFException;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class DefaultOggSeeker implements OggSeeker {
    private static final int DEFAULT_OFFSET = 30000;
    public static final int MATCH_BYTE_RANGE = 100000;
    public static final int MATCH_RANGE = 72000;
    private static final int STATE_IDLE = 3;
    private static final int STATE_READ_LAST_PAGE = 1;
    private static final int STATE_SEEK = 2;
    private static final int STATE_SEEK_TO_END = 0;
    private long end;
    private long endGranule;
    private final long endPosition;
    private final OggPageHeader pageHeader = new OggPageHeader();
    private long positionBeforeSeekToEnd;
    private long start;
    private long startGranule;
    private final long startPosition;
    private int state;
    private final StreamReader streamReader;
    private long targetGranule;
    private long totalGranules;

    private class OggSeekMap implements SeekMap {
        private OggSeekMap() {
        }

        public boolean isSeekable() {
            return true;
        }

        public long getPosition(long timeUs) {
            if (timeUs == 0) {
                return DefaultOggSeeker.this.startPosition;
            }
            return DefaultOggSeeker.this.getEstimatedPosition(DefaultOggSeeker.this.startPosition, DefaultOggSeeker.this.streamReader.convertTimeToGranule(timeUs), 30000);
        }

        public long getDurationUs() {
            return DefaultOggSeeker.this.streamReader.convertGranuleToTime(DefaultOggSeeker.this.totalGranules);
        }
    }

    public DefaultOggSeeker(long startPosition, long endPosition, StreamReader streamReader, int firstPayloadPageSize, long firstPayloadPageGranulePosition) {
        boolean z = startPosition >= 0 && endPosition > startPosition;
        Assertions.checkArgument(z);
        this.streamReader = streamReader;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        if (((long) firstPayloadPageSize) == endPosition - startPosition) {
            this.totalGranules = firstPayloadPageGranulePosition;
            this.state = 3;
            return;
        }
        this.state = 0;
    }

    public long read(ExtractorInput input) throws IOException, InterruptedException {
        switch (this.state) {
            case 0:
                this.positionBeforeSeekToEnd = input.getPosition();
                this.state = 1;
                long lastPageSearchPosition = this.endPosition - 65307;
                if (lastPageSearchPosition > this.positionBeforeSeekToEnd) {
                    return lastPageSearchPosition;
                }
                break;
            case 1:
                break;
            case 2:
                long currentGranule;
                if (this.targetGranule == 0) {
                    currentGranule = 0;
                } else {
                    long position = getNextSeekPosition(this.targetGranule, input);
                    if (position >= 0) {
                        return position;
                    }
                    ExtractorInput extractorInput = input;
                    currentGranule = skipToPageOfGranule(extractorInput, this.targetGranule, -(2 + position));
                }
                this.state = 3;
                return -(2 + currentGranule);
            case 3:
                return -1;
            default:
                throw new IllegalStateException();
        }
        this.totalGranules = readGranuleOfLastPage(input);
        this.state = 3;
        return this.positionBeforeSeekToEnd;
    }

    public long startSeek(long timeUs) {
        boolean z = this.state == 3 || this.state == 2;
        Assertions.checkArgument(z);
        this.targetGranule = timeUs == 0 ? 0 : this.streamReader.convertTimeToGranule(timeUs);
        this.state = 2;
        resetSeeking();
        return this.targetGranule;
    }

    public OggSeekMap createSeekMap() {
        return this.totalGranules != 0 ? new OggSeekMap() : null;
    }

    public void resetSeeking() {
        this.start = this.startPosition;
        this.end = this.endPosition;
        this.startGranule = 0;
        this.endGranule = this.totalGranules;
    }

    public long getNextSeekPosition(long targetGranule, ExtractorInput input) throws IOException, InterruptedException {
        if (this.start == this.end) {
            return -(this.startGranule + 2);
        }
        long initialPosition = input.getPosition();
        if (skipToNextPage(input, this.end)) {
            this.pageHeader.populate(input, false);
            input.resetPeekPosition();
            long granuleDistance = targetGranule - this.pageHeader.granulePosition;
            int pageSize = this.pageHeader.headerSize + this.pageHeader.bodySize;
            if (granuleDistance < 0 || granuleDistance > 72000) {
                if (granuleDistance < 0) {
                    this.end = initialPosition;
                    this.endGranule = this.pageHeader.granulePosition;
                } else {
                    this.start = input.getPosition() + ((long) pageSize);
                    this.startGranule = this.pageHeader.granulePosition;
                    if ((this.end - this.start) + ((long) pageSize) < 100000) {
                        input.skipFully(pageSize);
                        return -(this.startGranule + 2);
                    }
                }
                if (this.end - this.start < 100000) {
                    this.end = this.start;
                    return this.start;
                }
                return Math.min(Math.max((input.getPosition() - ((long) ((granuleDistance <= 0 ? 2 : 1) * pageSize))) + (((this.end - this.start) * granuleDistance) / (this.endGranule - this.startGranule)), this.start), this.end - 1);
            }
            input.skipFully(pageSize);
            return -(this.pageHeader.granulePosition + 2);
        } else if (this.start != initialPosition) {
            return this.start;
        } else {
            throw new IOException("No ogg page can be found.");
        }
    }

    private long getEstimatedPosition(long position, long granuleDistance, long offset) {
        position += (((this.endPosition - this.startPosition) * granuleDistance) / this.totalGranules) - offset;
        if (position < this.startPosition) {
            position = this.startPosition;
        }
        if (position >= this.endPosition) {
            return this.endPosition - 1;
        }
        return position;
    }

    void skipToNextPage(ExtractorInput input) throws IOException, InterruptedException {
        if (!skipToNextPage(input, this.endPosition)) {
            throw new EOFException();
        }
    }

    boolean skipToNextPage(ExtractorInput input, long until) throws IOException, InterruptedException {
        until = Math.min(3 + until, this.endPosition);
        byte[] buffer = new byte[2048];
        int peekLength = buffer.length;
        while (true) {
            if (input.getPosition() + ((long) peekLength) > until) {
                peekLength = (int) (until - input.getPosition());
                if (peekLength < 4) {
                    return false;
                }
            }
            input.peekFully(buffer, 0, peekLength, false);
            int i = 0;
            while (i < peekLength - 3) {
                if (buffer[i] == (byte) 79 && buffer[i + 1] == (byte) 103 && buffer[i + 2] == (byte) 103 && buffer[i + 3] == (byte) 83) {
                    input.skipFully(i);
                    return true;
                }
                i++;
            }
            input.skipFully(peekLength - 3);
        }
    }

    long readGranuleOfLastPage(ExtractorInput input) throws IOException, InterruptedException {
        skipToNextPage(input);
        this.pageHeader.reset();
        while ((this.pageHeader.type & 4) != 4 && input.getPosition() < this.endPosition) {
            this.pageHeader.populate(input, false);
            input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
        }
        return this.pageHeader.granulePosition;
    }

    long skipToPageOfGranule(ExtractorInput input, long targetGranule, long currentGranule) throws IOException, InterruptedException {
        this.pageHeader.populate(input, false);
        while (this.pageHeader.granulePosition < targetGranule) {
            input.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
            currentGranule = this.pageHeader.granulePosition;
            this.pageHeader.populate(input, false);
        }
        input.resetPeekPosition();
        return currentGranule;
    }
}
