package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
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

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        if (data.bytesLeft() < OPUS_SIGNATURE.length) {
            return false;
        }
        byte[] header = new byte[OPUS_SIGNATURE.length];
        data.readBytes(header, 0, OPUS_SIGNATURE.length);
        return Arrays.equals(header, OPUS_SIGNATURE);
    }

    protected void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.headerRead = false;
        }
    }

    protected long preparePayload(ParsableByteArray packet) {
        return convertTimeToGranule(getPacketDurationUs(packet.data));
    }

    protected boolean readHeaders(ParsableByteArray packet, long position, SetupData setupData) throws IOException, InterruptedException {
        if (this.headerRead) {
            boolean z = packet.readInt() == OPUS_CODE;
            packet.setPosition(0);
            return z;
        }
        byte[] metadata = Arrays.copyOf(packet.data, packet.limit());
        int channelCount = metadata[9] & 255;
        int preskip = ((metadata[11] & 255) << 8) | (metadata[10] & 255);
        List<byte[]> initializationData = new ArrayList(3);
        initializationData.add(metadata);
        putNativeOrderLong(initializationData, preskip);
        putNativeOrderLong(initializationData, DEFAULT_SEEK_PRE_ROLL_SAMPLES);
        setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_OPUS, null, -1, -1, channelCount, SAMPLE_RATE, initializationData, null, 0, null);
        this.headerRead = true;
        return true;
    }

    private void putNativeOrderLong(List<byte[]> initializationData, int samples) {
        initializationData.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong((((long) samples) * C0907C.NANOS_PER_SECOND) / 48000).array());
    }

    private long getPacketDurationUs(byte[] packet) {
        int frames;
        int toc = packet[0] & 255;
        switch (toc & 3) {
            case 0:
                frames = 1;
                break;
            case 1:
            case 2:
                frames = 2;
                break;
            default:
                frames = packet[1] & 63;
                break;
        }
        int config = toc >> 3;
        int length = config & 3;
        if (config >= 16) {
            length = 2500 << length;
        } else if (config >= 12) {
            length = 10000 << (length & 1);
        } else if (length == 3) {
            length = 60000;
        } else {
            length = 10000 << length;
        }
        return (long) (frames * length);
    }
}
