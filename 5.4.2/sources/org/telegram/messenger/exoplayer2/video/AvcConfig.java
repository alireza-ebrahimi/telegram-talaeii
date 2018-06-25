package org.telegram.messenger.exoplayer2.video;

import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.util.CodecSpecificDataUtil;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil.SpsData;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class AvcConfig {
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthAspectRatio;
    public final int width;

    private AvcConfig(List<byte[]> list, int i, int i2, int i3, float f) {
        this.initializationData = list;
        this.nalUnitLengthFieldLength = i;
        this.width = i2;
        this.height = i3;
        this.pixelWidthAspectRatio = f;
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray parsableByteArray) {
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(readUnsignedShort);
        return CodecSpecificDataUtil.buildNalUnit(parsableByteArray.data, position, readUnsignedShort);
    }

    public static AvcConfig parse(ParsableByteArray parsableByteArray) {
        int i = -1;
        int i2 = 0;
        try {
            parsableByteArray.skipBytes(4);
            int readUnsignedByte = (parsableByteArray.readUnsignedByte() & 3) + 1;
            if (readUnsignedByte == 3) {
                throw new IllegalStateException();
            }
            int i3;
            List arrayList = new ArrayList();
            int readUnsignedByte2 = parsableByteArray.readUnsignedByte() & 31;
            for (i3 = 0; i3 < readUnsignedByte2; i3++) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
            }
            i3 = parsableByteArray.readUnsignedByte();
            while (i2 < i3) {
                arrayList.add(buildNalUnitForChild(parsableByteArray));
                i2++;
            }
            float f = 1.0f;
            if (readUnsignedByte2 > 0) {
                SpsData parseSpsNalUnit = NalUnitUtil.parseSpsNalUnit((byte[]) arrayList.get(0), readUnsignedByte, ((byte[]) arrayList.get(0)).length);
                i3 = parseSpsNalUnit.width;
                i = parseSpsNalUnit.height;
                f = parseSpsNalUnit.pixelWidthAspectRatio;
            } else {
                i3 = -1;
            }
            return new AvcConfig(arrayList, readUnsignedByte, i3, i, f);
        } catch (Throwable e) {
            throw new ParserException("Error parsing AVC config", e);
        }
    }
}
