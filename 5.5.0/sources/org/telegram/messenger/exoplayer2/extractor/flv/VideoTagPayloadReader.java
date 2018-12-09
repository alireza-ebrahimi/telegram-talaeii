package org.telegram.messenger.exoplayer2.extractor.flv;

import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.flv.TagPayloadReader.UnsupportedFormatException;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.video.AvcConfig;

final class VideoTagPayloadReader extends TagPayloadReader {
    private static final int AVC_PACKET_TYPE_AVC_NALU = 1;
    private static final int AVC_PACKET_TYPE_SEQUENCE_HEADER = 0;
    private static final int VIDEO_CODEC_AVC = 7;
    private static final int VIDEO_FRAME_KEYFRAME = 1;
    private static final int VIDEO_FRAME_VIDEO_INFO = 5;
    private int frameType;
    private boolean hasOutputFormat;
    private final ParsableByteArray nalLength = new ParsableByteArray(4);
    private final ParsableByteArray nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
    private int nalUnitLengthFieldLength;

    public VideoTagPayloadReader(TrackOutput trackOutput) {
        super(trackOutput);
    }

    protected boolean parseHeader(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = (readUnsignedByte >> 4) & 15;
        readUnsignedByte &= 15;
        if (readUnsignedByte != 7) {
            throw new UnsupportedFormatException("Video format not supported: " + readUnsignedByte);
        }
        this.frameType = i;
        return i != 5;
    }

    protected void parsePayload(ParsableByteArray parsableByteArray, long j) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        long readUnsignedInt24 = (((long) parsableByteArray.readUnsignedInt24()) * 1000) + j;
        if (readUnsignedByte == 0 && !this.hasOutputFormat) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray(new byte[parsableByteArray.bytesLeft()]);
            parsableByteArray.readBytes(parsableByteArray2.data, 0, parsableByteArray.bytesLeft());
            AvcConfig parse = AvcConfig.parse(parsableByteArray2);
            this.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
            this.output.format(Format.createVideoSampleFormat(null, "video/avc", null, -1, -1, parse.width, parse.height, -1.0f, parse.initializationData, -1, parse.pixelWidthAspectRatio, null));
            this.hasOutputFormat = true;
        } else if (readUnsignedByte == 1 && this.hasOutputFormat) {
            byte[] bArr = this.nalLength.data;
            bArr[0] = (byte) 0;
            bArr[1] = (byte) 0;
            bArr[2] = (byte) 0;
            readUnsignedByte = 4 - this.nalUnitLengthFieldLength;
            int i = 0;
            while (parsableByteArray.bytesLeft() > 0) {
                parsableByteArray.readBytes(this.nalLength.data, readUnsignedByte, this.nalUnitLengthFieldLength);
                this.nalLength.setPosition(0);
                int readUnsignedIntToInt = this.nalLength.readUnsignedIntToInt();
                this.nalStartCode.setPosition(0);
                this.output.sampleData(this.nalStartCode, 4);
                int i2 = i + 4;
                this.output.sampleData(parsableByteArray, readUnsignedIntToInt);
                i = i2 + readUnsignedIntToInt;
            }
            this.output.sampleMetadata(readUnsignedInt24, this.frameType == 1 ? 1 : 0, i, 0, null);
        }
    }

    public void seek() {
    }
}
