package org.telegram.messenger.exoplayer2.extractor.mp3;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.exoplayer2.C3446C;
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

    private XingSeeker(long j, long j2, long j3) {
        this(j, j2, j3, null, 0, 0);
    }

    private XingSeeker(long j, long j2, long j3, long[] jArr, long j4, int i) {
        this.firstFramePosition = j;
        this.durationUs = j2;
        this.inputLength = j3;
        this.tableOfContents = jArr;
        this.sizeBytes = j4;
        this.headerSize = i;
    }

    public static XingSeeker create(MpegAudioHeader mpegAudioHeader, ParsableByteArray parsableByteArray, long j, long j2) {
        int i = mpegAudioHeader.samplesPerFrame;
        int i2 = mpegAudioHeader.sampleRate;
        long j3 = j + ((long) mpegAudioHeader.frameSize);
        int readInt = parsableByteArray.readInt();
        if ((readInt & 1) == 1) {
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (readUnsignedIntToInt != 0) {
                long scaleLargeTimestamp = Util.scaleLargeTimestamp((long) readUnsignedIntToInt, ((long) i) * C3446C.MICROS_PER_SECOND, (long) i2);
                if ((readInt & 6) != 6) {
                    return new XingSeeker(j3, scaleLargeTimestamp, j2);
                }
                long readUnsignedIntToInt2 = (long) parsableByteArray.readUnsignedIntToInt();
                parsableByteArray.skipBytes(1);
                long[] jArr = new long[99];
                for (readUnsignedIntToInt = 0; readUnsignedIntToInt < 99; readUnsignedIntToInt++) {
                    jArr[readUnsignedIntToInt] = (long) parsableByteArray.readUnsignedByte();
                }
                return new XingSeeker(j3, scaleLargeTimestamp, j2, jArr, readUnsignedIntToInt2, mpegAudioHeader.frameSize);
            }
        }
        return null;
    }

    private long getTimeUsForTocPosition(int i) {
        return (this.durationUs * ((long) i)) / 100;
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPosition(long j) {
        float f = 256.0f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        if (!isSeekable()) {
            return this.firstFramePosition;
        }
        float f3 = (((float) j) * 100.0f) / ((float) this.durationUs);
        if (f3 <= BitmapDescriptorFactory.HUE_RED) {
            f = BitmapDescriptorFactory.HUE_RED;
        } else if (f3 < 100.0f) {
            int i = (int) f3;
            if (i != 0) {
                f2 = (float) this.tableOfContents[i - 1];
            }
            if (i < 99) {
                f = (float) this.tableOfContents[i];
            }
            f = ((f - f2) * (f3 - ((float) i))) + f2;
        }
        return Math.min(this.firstFramePosition + Math.round((((double) f) * 0.00390625d) * ((double) this.sizeBytes)), this.inputLength != -1 ? this.inputLength - 1 : ((this.firstFramePosition - ((long) this.headerSize)) + this.sizeBytes) - 1);
    }

    public long getTimeUs(long j) {
        if (!isSeekable() || j < this.firstFramePosition) {
            return 0;
        }
        double d = (256.0d * ((double) (j - this.firstFramePosition))) / ((double) this.sizeBytes);
        int binarySearchFloor = Util.binarySearchFloor(this.tableOfContents, (long) d, true, false) + 1;
        long timeUsForTocPosition = getTimeUsForTocPosition(binarySearchFloor);
        long j2 = binarySearchFloor == 0 ? 0 : this.tableOfContents[binarySearchFloor - 1];
        long j3 = binarySearchFloor == 99 ? 256 : this.tableOfContents[binarySearchFloor];
        return timeUsForTocPosition + (j3 == j2 ? 0 : (long) ((((double) (getTimeUsForTocPosition(binarySearchFloor + 1) - timeUsForTocPosition)) * (d - ((double) j2))) / ((double) (j3 - j2))));
    }

    public boolean isSeekable() {
        return this.tableOfContents != null;
    }
}
