package org.telegram.messenger.exoplayer2.video;

import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.util.CodecSpecificDataUtil;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil$SpsData;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class AvcConfig {
    public final int height;
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;
    public final float pixelWidthAspectRatio;
    public final int width;

    public static AvcConfig parse(ParsableByteArray data) throws ParserException {
        try {
            data.skipBytes(4);
            int nalUnitLengthFieldLength = (data.readUnsignedByte() & 3) + 1;
            if (nalUnitLengthFieldLength == 3) {
                throw new IllegalStateException();
            }
            int j;
            List<byte[]> initializationData = new ArrayList();
            int numSequenceParameterSets = data.readUnsignedByte() & 31;
            for (j = 0; j < numSequenceParameterSets; j++) {
                initializationData.add(buildNalUnitForChild(data));
            }
            int numPictureParameterSets = data.readUnsignedByte();
            for (j = 0; j < numPictureParameterSets; j++) {
                initializationData.add(buildNalUnitForChild(data));
            }
            int width = -1;
            int height = -1;
            float pixelWidthAspectRatio = 1.0f;
            if (numSequenceParameterSets > 0) {
                NalUnitUtil$SpsData spsData = NalUnitUtil.parseSpsNalUnit((byte[]) initializationData.get(0), nalUnitLengthFieldLength, ((byte[]) initializationData.get(0)).length);
                width = spsData.width;
                height = spsData.height;
                pixelWidthAspectRatio = spsData.pixelWidthAspectRatio;
            }
            return new AvcConfig(initializationData, nalUnitLengthFieldLength, width, height, pixelWidthAspectRatio);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParserException("Error parsing AVC config", e);
        }
    }

    private AvcConfig(List<byte[]> initializationData, int nalUnitLengthFieldLength, int width, int height, float pixelWidthAspectRatio) {
        this.initializationData = initializationData;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength;
        this.width = width;
        this.height = height;
        this.pixelWidthAspectRatio = pixelWidthAspectRatio;
    }

    private static byte[] buildNalUnitForChild(ParsableByteArray data) {
        int length = data.readUnsignedShort();
        int offset = data.getPosition();
        data.skipBytes(length);
        return CodecSpecificDataUtil.buildNalUnit(data.data, offset, length);
    }
}
