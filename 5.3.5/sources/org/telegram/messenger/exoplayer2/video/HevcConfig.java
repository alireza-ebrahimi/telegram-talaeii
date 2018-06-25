package org.telegram.messenger.exoplayer2.video;

import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class HevcConfig {
    public final List<byte[]> initializationData;
    public final int nalUnitLengthFieldLength;

    public static HevcConfig parse(ParsableByteArray data) throws ParserException {
        try {
            int i;
            int numberOfNalUnits;
            int j;
            int nalUnitLength;
            data.skipBytes(21);
            int lengthSizeMinusOne = data.readUnsignedByte() & 3;
            int numberOfArrays = data.readUnsignedByte();
            int csdLength = 0;
            int csdStartPosition = data.getPosition();
            for (i = 0; i < numberOfArrays; i++) {
                data.skipBytes(1);
                numberOfNalUnits = data.readUnsignedShort();
                for (j = 0; j < numberOfNalUnits; j++) {
                    nalUnitLength = data.readUnsignedShort();
                    csdLength += nalUnitLength + 4;
                    data.skipBytes(nalUnitLength);
                }
            }
            data.setPosition(csdStartPosition);
            byte[] buffer = new byte[csdLength];
            int bufferPosition = 0;
            for (i = 0; i < numberOfArrays; i++) {
                data.skipBytes(1);
                numberOfNalUnits = data.readUnsignedShort();
                for (j = 0; j < numberOfNalUnits; j++) {
                    nalUnitLength = data.readUnsignedShort();
                    System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, buffer, bufferPosition, NalUnitUtil.NAL_START_CODE.length);
                    bufferPosition += NalUnitUtil.NAL_START_CODE.length;
                    System.arraycopy(data.data, data.getPosition(), buffer, bufferPosition, nalUnitLength);
                    bufferPosition += nalUnitLength;
                    data.skipBytes(nalUnitLength);
                }
            }
            return new HevcConfig(csdLength == 0 ? null : Collections.singletonList(buffer), lengthSizeMinusOne + 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParserException("Error parsing HEVC config", e);
        }
    }

    private HevcConfig(List<byte[]> initializationData, int nalUnitLengthFieldLength) {
        this.initializationData = initializationData;
        this.nalUnitLengthFieldLength = nalUnitLengthFieldLength;
    }
}
