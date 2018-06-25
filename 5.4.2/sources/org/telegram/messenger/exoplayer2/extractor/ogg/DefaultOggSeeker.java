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

        public long getDurationUs() {
            return DefaultOggSeeker.this.streamReader.convertGranuleToTime(DefaultOggSeeker.this.totalGranules);
        }

        public long getPosition(long j) {
            if (j == 0) {
                return DefaultOggSeeker.this.startPosition;
            }
            return DefaultOggSeeker.this.getEstimatedPosition(DefaultOggSeeker.this.startPosition, DefaultOggSeeker.this.streamReader.convertTimeToGranule(j), 30000);
        }

        public boolean isSeekable() {
            return true;
        }
    }

    public DefaultOggSeeker(long j, long j2, StreamReader streamReader, int i, long j3) {
        boolean z = j >= 0 && j2 > j;
        Assertions.checkArgument(z);
        this.streamReader = streamReader;
        this.startPosition = j;
        this.endPosition = j2;
        if (((long) i) == j2 - j) {
            this.totalGranules = j3;
            this.state = 3;
            return;
        }
        this.state = 0;
    }

    private long getEstimatedPosition(long j, long j2, long j3) {
        long j4 = ((((this.endPosition - this.startPosition) * j2) / this.totalGranules) - j3) + j;
        if (j4 < this.startPosition) {
            j4 = this.startPosition;
        }
        return j4 >= this.endPosition ? this.endPosition - 1 : j4;
    }

    public OggSeekMap createSeekMap() {
        return this.totalGranules != 0 ? new OggSeekMap() : null;
    }

    public long getNextSeekPosition(long j, ExtractorInput extractorInput) {
        if (this.start == this.end) {
            return -(this.startGranule + 2);
        }
        long position = extractorInput.getPosition();
        if (skipToNextPage(extractorInput, this.end)) {
            this.pageHeader.populate(extractorInput, false);
            extractorInput.resetPeekPosition();
            long j2 = j - this.pageHeader.granulePosition;
            int i = this.pageHeader.headerSize + this.pageHeader.bodySize;
            if (j2 < 0 || j2 > 72000) {
                if (j2 < 0) {
                    this.end = position;
                    this.endGranule = this.pageHeader.granulePosition;
                } else {
                    this.start = extractorInput.getPosition() + ((long) i);
                    this.startGranule = this.pageHeader.granulePosition;
                    if ((this.end - this.start) + ((long) i) < 100000) {
                        extractorInput.skipFully(i);
                        return -(this.startGranule + 2);
                    }
                }
                if (this.end - this.start < 100000) {
                    this.end = this.start;
                    return this.start;
                }
                return Math.min(Math.max((extractorInput.getPosition() - ((long) ((j2 <= 0 ? 2 : 1) * i))) + ((j2 * (this.end - this.start)) / (this.endGranule - this.startGranule)), this.start), this.end - 1);
            }
            extractorInput.skipFully(i);
            return -(this.pageHeader.granulePosition + 2);
        } else if (this.start != position) {
            return this.start;
        } else {
            throw new IOException("No ogg page can be found.");
        }
    }

    public long read(ExtractorInput extractorInput) {
        long j = 0;
        switch (this.state) {
            case 0:
                this.positionBeforeSeekToEnd = extractorInput.getPosition();
                this.state = 1;
                j = this.endPosition - 65307;
                if (j > this.positionBeforeSeekToEnd) {
                    return j;
                }
                break;
            case 1:
                break;
            case 2:
                if (this.targetGranule != 0) {
                    long nextSeekPosition = getNextSeekPosition(this.targetGranule, extractorInput);
                    if (nextSeekPosition >= 0) {
                        return nextSeekPosition;
                    }
                    ExtractorInput extractorInput2 = extractorInput;
                    j = skipToPageOfGranule(extractorInput2, this.targetGranule, -(nextSeekPosition + 2));
                }
                this.state = 3;
                return -(j + 2);
            case 3:
                return -1;
            default:
                throw new IllegalStateException();
        }
        this.totalGranules = readGranuleOfLastPage(extractorInput);
        this.state = 3;
        return this.positionBeforeSeekToEnd;
    }

    long readGranuleOfLastPage(ExtractorInput extractorInput) {
        skipToNextPage(extractorInput);
        this.pageHeader.reset();
        while ((this.pageHeader.type & 4) != 4 && extractorInput.getPosition() < this.endPosition) {
            this.pageHeader.populate(extractorInput, false);
            extractorInput.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
        }
        return this.pageHeader.granulePosition;
    }

    public void resetSeeking() {
        this.start = this.startPosition;
        this.end = this.endPosition;
        this.startGranule = 0;
        this.endGranule = this.totalGranules;
    }

    void skipToNextPage(ExtractorInput extractorInput) {
        if (!skipToNextPage(extractorInput, this.endPosition)) {
            throw new EOFException();
        }
    }

    boolean skipToNextPage(ExtractorInput extractorInput, long j) {
        long min = Math.min(3 + j, this.endPosition);
        byte[] bArr = new byte[2048];
        int length = bArr.length;
        while (true) {
            if (extractorInput.getPosition() + ((long) length) > min) {
                length = (int) (min - extractorInput.getPosition());
                if (length < 4) {
                    return false;
                }
            }
            extractorInput.peekFully(bArr, 0, length, false);
            int i = 0;
            while (i < length - 3) {
                if (bArr[i] == (byte) 79 && bArr[i + 1] == (byte) 103 && bArr[i + 2] == (byte) 103 && bArr[i + 3] == (byte) 83) {
                    extractorInput.skipFully(i);
                    return true;
                }
                i++;
            }
            extractorInput.skipFully(length - 3);
        }
    }

    long skipToPageOfGranule(ExtractorInput extractorInput, long j, long j2) {
        this.pageHeader.populate(extractorInput, false);
        while (this.pageHeader.granulePosition < j) {
            extractorInput.skipFully(this.pageHeader.headerSize + this.pageHeader.bodySize);
            j2 = this.pageHeader.granulePosition;
            this.pageHeader.populate(extractorInput, false);
        }
        extractorInput.resetPeekPosition();
        return j2;
    }

    public long startSeek(long j) {
        boolean z = this.state == 3 || this.state == 2;
        Assertions.checkArgument(z);
        this.targetGranule = j == 0 ? 0 : this.streamReader.convertTimeToGranule(j);
        this.state = 2;
        resetSeeking();
        return this.targetGranule;
    }
}
