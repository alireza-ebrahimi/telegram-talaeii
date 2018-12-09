package org.telegram.messenger.exoplayer2.audio;

import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class Ac3Util {
    private static final int AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT = 1536;
    private static final int AUDIO_SAMPLES_PER_AUDIO_BLOCK = 256;
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD = new int[]{32, 40, 48, 56, 64, 80, 96, 112, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 320, 384, 448, 512, 576, 640};
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = new int[]{1, 2, 3, 6};
    private static final int[] CHANNEL_COUNT_BY_ACMOD = new int[]{2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] SAMPLE_RATE_BY_FSCOD = new int[]{48000, 44100, 32000};
    private static final int[] SAMPLE_RATE_BY_FSCOD2 = new int[]{24000, 22050, 16000};
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = new int[]{69, 87, 104, 121, 139, 174, 208, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    public static final class Ac3SyncFrameInfo {
        public final int channelCount;
        public final int frameSize;
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;

        private Ac3SyncFrameInfo(String str, int i, int i2, int i3, int i4) {
            this.mimeType = str;
            this.channelCount = i;
            this.sampleRate = i2;
            this.frameSize = i3;
            this.sampleCount = i4;
        }
    }

    private Ac3Util() {
    }

    public static int getAc3SyncframeAudioSampleCount() {
        return AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
    }

    private static int getAc3SyncframeSize(int i, int i2) {
        int i3 = i2 / 2;
        if (i < 0 || i >= SAMPLE_RATE_BY_FSCOD.length || i2 < 0 || i3 >= SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1.length) {
            return -1;
        }
        int i4 = SAMPLE_RATE_BY_FSCOD[i];
        if (i4 == 44100) {
            return (SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1[i3] + (i2 % 2)) * 2;
        }
        i3 = BITRATE_BY_HALF_FRMSIZECOD[i3];
        return i4 == 32000 ? i3 * 6 : i3 * 4;
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, DrmInitData drmInitData) {
        int i = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = CHANNEL_COUNT_BY_ACMOD[(readUnsignedByte & 56) >> 3];
        if ((readUnsignedByte & 4) != 0) {
            i2++;
        }
        return Format.createAudioSampleFormat(str, MimeTypes.AUDIO_AC3, null, -1, -1, i2, i, null, drmInitData, 0, str2);
    }

    public static Ac3SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray parsableBitArray) {
        int readBits;
        int readBits2;
        String str;
        int i = 1;
        int position = parsableBitArray.getPosition();
        parsableBitArray.skipBits(40);
        int i2 = parsableBitArray.readBits(5) == 16 ? 1 : 0;
        parsableBitArray.setPosition(position);
        if (i2 != 0) {
            int i3;
            String str2 = MimeTypes.AUDIO_E_AC3;
            parsableBitArray.skipBits(21);
            readBits = (parsableBitArray.readBits(11) + 1) * 2;
            readBits2 = parsableBitArray.readBits(2);
            if (readBits2 == 3) {
                i3 = SAMPLE_RATE_BY_FSCOD2[parsableBitArray.readBits(2)];
                i2 = 6;
            } else {
                i2 = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[parsableBitArray.readBits(2)];
                i3 = SAMPLE_RATE_BY_FSCOD[readBits2];
            }
            readBits2 = i2 * 256;
            i2 = parsableBitArray.readBits(3);
            int i4 = i3;
            str = str2;
            position = i4;
        } else {
            String str3 = MimeTypes.AUDIO_AC3;
            parsableBitArray.skipBits(32);
            position = parsableBitArray.readBits(2);
            readBits = getAc3SyncframeSize(position, parsableBitArray.readBits(6));
            parsableBitArray.skipBits(8);
            i2 = parsableBitArray.readBits(3);
            if (!((i2 & 1) == 0 || i2 == 1)) {
                parsableBitArray.skipBits(2);
            }
            if ((i2 & 4) != 0) {
                parsableBitArray.skipBits(2);
            }
            if (i2 == 2) {
                parsableBitArray.skipBits(2);
            }
            position = SAMPLE_RATE_BY_FSCOD[position];
            readBits2 = AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
            str = str3;
        }
        boolean readBit = parsableBitArray.readBit();
        i2 = CHANNEL_COUNT_BY_ACMOD[i2];
        if (!readBit) {
            i = 0;
        }
        return new Ac3SyncFrameInfo(str, i + i2, position, readBits, readBits2);
    }

    public static int parseAc3SyncframeSize(byte[] bArr) {
        return bArr.length < 5 ? -1 : getAc3SyncframeSize((bArr[4] & PsExtractor.AUDIO_STREAM) >> 6, bArr[4] & 63);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, DrmInitData drmInitData) {
        parsableByteArray.skipBytes(2);
        int i = SAMPLE_RATE_BY_FSCOD[(parsableByteArray.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = CHANNEL_COUNT_BY_ACMOD[(readUnsignedByte & 14) >> 1];
        if ((readUnsignedByte & 1) != 0) {
            i2++;
        }
        return Format.createAudioSampleFormat(str, MimeTypes.AUDIO_E_AC3, null, -1, -1, i2, i, null, drmInitData, 0, str2);
    }

    public static int parseEAc3SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        return (((byteBuffer.get(byteBuffer.position() + 4) & PsExtractor.AUDIO_STREAM) >> 6) == 3 ? 6 : BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4]) * 256;
    }
}
