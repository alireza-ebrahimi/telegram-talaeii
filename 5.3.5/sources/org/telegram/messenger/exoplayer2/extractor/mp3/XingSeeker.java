package org.telegram.messenger.exoplayer2.extractor.mp3;

import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class XingSeeker implements Seeker {
    private final long durationUs;
    private final long firstFramePosition;
    private final int headerSize;
    private final long inputLength;
    private final long sizeBytes;
    private final long[] tableOfContents;

    public static XingSeeker create(MpegAudioHeader mpegAudioHeader, ParsableByteArray frame, long position, long inputLength) {
        int samplesPerFrame = mpegAudioHeader.samplesPerFrame;
        int sampleRate = mpegAudioHeader.sampleRate;
        long firstFramePosition = position + ((long) mpegAudioHeader.frameSize);
        int flags = frame.readInt();
        if ((flags & 1) == 1) {
            int frameCount = frame.readUnsignedIntToInt();
            if (frameCount != 0) {
                long durationUs = Util.scaleLargeTimestamp((long) frameCount, ((long) samplesPerFrame) * C0907C.MICROS_PER_SECOND, (long) sampleRate);
                if ((flags & 6) != 6) {
                    return new XingSeeker(firstFramePosition, durationUs, inputLength);
                }
                long sizeBytes = (long) frame.readUnsignedIntToInt();
                frame.skipBytes(1);
                long[] tableOfContents = new long[99];
                for (int i = 0; i < 99; i++) {
                    tableOfContents[i] = (long) frame.readUnsignedByte();
                }
                return new XingSeeker(firstFramePosition, durationUs, inputLength, tableOfContents, sizeBytes, mpegAudioHeader.frameSize);
            }
        }
        return null;
    }

    private XingSeeker(long firstFramePosition, long durationUs, long inputLength) {
        this(firstFramePosition, durationUs, inputLength, null, 0, 0);
    }

    private XingSeeker(long firstFramePosition, long durationUs, long inputLength, long[] tableOfContents, long sizeBytes, int headerSize) {
        this.firstFramePosition = firstFramePosition;
        this.durationUs = durationUs;
        this.inputLength = inputLength;
        this.tableOfContents = tableOfContents;
        this.sizeBytes = sizeBytes;
        this.headerSize = headerSize;
    }

    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    public long getPosition(long timeUs) {
        if (!isSeekable()) {
            return this.firstFramePosition;
        }
        float fx;
        float percent = (((float) timeUs) * 100.0f) / ((float) this.durationUs);
        if (percent <= 0.0f) {
            fx = 0.0f;
        } else if (percent >= 100.0f) {
            fx = 256.0f;
        } else {
            float fa;
            float fb;
            int a = (int) percent;
            if (a == 0) {
                fa = 0.0f;
            } else {
                fa = (float) this.tableOfContents[a - 1];
            }
            if (a < 99) {
                fb = (float) this.tableOfContents[a];
            } else {
                fb = 256.0f;
            }
            fx = fa + ((fb - fa) * (percent - ((float) a)));
        }
        return Math.min(Math.round((0.00390625d * ((double) fx)) * ((double) this.sizeBytes)) + this.firstFramePosition, this.inputLength != -1 ? this.inputLength - 1 : ((this.firstFramePosition - ((long) this.headerSize)) + this.sizeBytes) - 1);
    }

    public long getTimeUs(long position) {
        if (!isSeekable() || position < this.firstFramePosition) {
            return 0;
        }
        double offsetByte = (256.0d * ((double) (position - this.firstFramePosition))) / ((double) this.sizeBytes);
        int previousTocPosition = Util.binarySearchFloor(this.tableOfContents, (long) offsetByte, true, false) + 1;
        long previousTime = getTimeUsForTocPosition(previousTocPosition);
        long previousByte = previousTocPosition == 0 ? 0 : this.tableOfContents[previousTocPosition - 1];
        long nextByte = previousTocPosition == 99 ? 256 : this.tableOfContents[previousTocPosition];
        return previousTime + (nextByte == previousByte ? 0 : (long) ((((double) (getTimeUsForTocPosition(previousTocPosition + 1) - previousTime)) * (offsetByte - ((double) previousByte))) / ((double) (nextByte - previousByte))));
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    private long getTimeUsForTocPosition(int tocPosition) {
        return (this.durationUs * ((long) tocPosition)) / 100;
    }
}
