package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class OpusReader extends StreamReader {
    private static final int DEFAULT_SEEK_PRE_ROLL_SAMPLES = 3840;
    private static final int OPUS_CODE = Util.getIntegerCodeForString("Opus");
    private static final byte[] OPUS_SIGNATURE = new byte[]{(byte) 79, (byte) 112, (byte) 117, (byte) 115, (byte) 72, (byte) 101, (byte) 97, (byte) 100};
    private static final int SAMPLE_RATE = 48000;
    private boolean headerRead;

    OpusReader() {
    }

    private long getPacketDurationUs(byte[] bArr) {
        int i;
        int i2 = bArr[0] & 255;
        switch (i2 & 3) {
            case 0:
                i = 1;
                break;
            case 1:
            case 2:
                i = 2;
                break;
            default:
                i = bArr[1] & 63;
                break;
        }
        int i3 = i2 >> 3;
        i2 = i3 & 3;
        i3 = i3 >= 16 ? 2500 << i2 : i3 >= 12 ? 10000 << (i2 & 1) : i2 == 3 ? 60000 : 10000 << i2;
        return (long) (i3 * i);
    }

    private void putNativeOrderLong(List<byte[]> list, int i) {
        list.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong((((long) i) * C3446C.NANOS_PER_SECOND) / 48000).array());
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < OPUS_SIGNATURE.length) {
            return false;
        }
        byte[] bArr = new byte[OPUS_SIGNATURE.length];
        parsableByteArray.readBytes(bArr, 0, OPUS_SIGNATURE.length);
        return Arrays.equals(bArr, OPUS_SIGNATURE);
    }

    protected long preparePayload(ParsableByteArray parsableByteArray) {
        return convertTimeToGranule(getPacketDurationUs(parsableByteArray.data));
    }

    protected boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData) {
        if (this.headerRead) {
            boolean z = parsableByteArray.readInt() == OPUS_CODE;
            parsableByteArray.setPosition(0);
            return z;
        }
        Object copyOf = Arrays.copyOf(parsableByteArray.data, parsableByteArray.limit());
        int i = copyOf[9] & 255;
        int i2 = ((copyOf[11] & 255) << 8) | (copyOf[10] & 255);
        List arrayList = new ArrayList(3);
        arrayList.add(copyOf);
        putNativeOrderLong(arrayList, i2);
        putNativeOrderLong(arrayList, DEFAULT_SEEK_PRE_ROLL_SAMPLES);
        setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_OPUS, null, -1, -1, i, SAMPLE_RATE, arrayList, null, 0, null);
        this.headerRead = true;
        return true;
    }

    protected void reset(boolean z) {
        super.reset(z);
        if (z) {
            this.headerRead = false;
        }
    }
}
