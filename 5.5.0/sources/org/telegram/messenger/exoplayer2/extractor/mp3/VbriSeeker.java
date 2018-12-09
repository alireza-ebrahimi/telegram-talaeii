package org.telegram.messenger.exoplayer2.extractor.mp3;

import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.MpegAudioHeader;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class VbriSeeker implements Seeker {
    private final long durationUs;
    private final long[] positions;
    private final long[] timesUs;

    private VbriSeeker(long[] jArr, long[] jArr2, long j) {
        this.timesUs = jArr;
        this.positions = jArr2;
        this.durationUs = j;
    }

    public static VbriSeeker create(MpegAudioHeader mpegAudioHeader, ParsableByteArray parsableByteArray, long j, long j2) {
        parsableByteArray.skipBytes(10);
        int readInt = parsableByteArray.readInt();
        if (readInt <= 0) {
            return null;
        }
        int i = mpegAudioHeader.sampleRate;
        long scaleLargeTimestamp = Util.scaleLargeTimestamp((long) readInt, ((long) (i >= 32000 ? 1152 : 576)) * C3446C.MICROS_PER_SECOND, (long) i);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        long j3 = j + ((long) mpegAudioHeader.frameSize);
        long[] jArr = new long[(readUnsignedShort + 1)];
        long[] jArr2 = new long[(readUnsignedShort + 1)];
        jArr[0] = 0;
        jArr2[0] = j3;
        for (readInt = 1; readInt < jArr.length; readInt++) {
            int readUnsignedByte;
            switch (readUnsignedShort3) {
                case 1:
                    readUnsignedByte = parsableByteArray.readUnsignedByte();
                    break;
                case 2:
                    readUnsignedByte = parsableByteArray.readUnsignedShort();
                    break;
                case 3:
                    readUnsignedByte = parsableByteArray.readUnsignedInt24();
                    break;
                case 4:
                    readUnsignedByte = parsableByteArray.readUnsignedIntToInt();
                    break;
                default:
                    return null;
            }
            j3 += (long) (readUnsignedByte * readUnsignedShort2);
            jArr[readInt] = (((long) readInt) * scaleLargeTimestamp) / ((long) readUnsignedShort);
            jArr2[readInt] = j2 == -1 ? j3 : Math.min(j2, j3);
        }
        return new VbriSeeker(jArr, jArr2, scaleLargeTimestamp);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPosition(long j) {
        return this.positions[Util.binarySearchFloor(this.timesUs, j, true, true)];
    }

    public long getTimeUs(long j) {
        return this.timesUs[Util.binarySearchFloor(this.positions, j, true, true)];
    }

    public boolean isSeekable() {
        return true;
    }
}
