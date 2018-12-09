package org.telegram.messenger.exoplayer2.video;

import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class HevcConfig {
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;

    private HevcConfig(List<byte[]> list, int i) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i;
    }

    public static HevcConfig parse(ParsableByteArray parsableByteArray) {
        try {
            int readUnsignedShort;
            int i;
            int i2;
            parsableByteArray.skipBytes(21);
            int readUnsignedByte = parsableByteArray.readUnsignedByte() & 3;
            int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
            int position = parsableByteArray.getPosition();
            int i3 = 0;
            int i4 = 0;
            while (i3 < readUnsignedByte2) {
                parsableByteArray.skipBytes(1);
                readUnsignedShort = parsableByteArray.readUnsignedShort();
                i = i4;
                for (i2 = 0; i2 < readUnsignedShort; i2++) {
                    i4 = parsableByteArray.readUnsignedShort();
                    i += i4 + 4;
                    parsableByteArray.skipBytes(i4);
                }
                i3++;
                i4 = i;
            }
            parsableByteArray.setPosition(position);
            Object obj = new byte[i4];
            i3 = 0;
            i2 = 0;
            while (i3 < readUnsignedByte2) {
                parsableByteArray.skipBytes(1);
                readUnsignedShort = parsableByteArray.readUnsignedShort();
                i = i2;
                for (i2 = 0; i2 < readUnsignedShort; i2++) {
                    int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                    System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, obj, i, NalUnitUtil.NAL_START_CODE.length);
                    i += NalUnitUtil.NAL_START_CODE.length;
                    System.arraycopy(parsableByteArray.data, parsableByteArray.getPosition(), obj, i, readUnsignedShort2);
                    i += readUnsignedShort2;
                    parsableByteArray.skipBytes(readUnsignedShort2);
                }
                i3++;
                i2 = i;
            }
            return new HevcConfig(i4 == 0 ? null : Collections.singletonList(obj), readUnsignedByte + 1);
        } catch (Throwable e) {
            throw new ParserException("Error parsing HEVC config", e);
        }
    }
}
