package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.io.IOException;
import java.util.ArrayList;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.ogg.VorbisUtil.CommentHeader;
import org.telegram.messenger.exoplayer2.extractor.ogg.VorbisUtil.Mode;
import org.telegram.messenger.exoplayer2.extractor.ogg.VorbisUtil.VorbisIdHeader;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class VorbisReader extends StreamReader {
    private CommentHeader commentHeader;
    private int previousPacketBlockSize;
    private boolean seenFirstAudioPacket;
    private VorbisIdHeader vorbisIdHeader;
    private VorbisSetup vorbisSetup;

    static final class VorbisSetup {
        public final CommentHeader commentHeader;
        public final int iLogModes;
        public final VorbisIdHeader idHeader;
        public final Mode[] modes;
        public final byte[] setupHeaderData;

        public VorbisSetup(VorbisIdHeader idHeader, CommentHeader commentHeader, byte[] setupHeaderData, Mode[] modes, int iLogModes) {
            this.idHeader = idHeader;
            this.commentHeader = commentHeader;
            this.setupHeaderData = setupHeaderData;
            this.modes = modes;
            this.iLogModes = iLogModes;
        }
    }

    VorbisReader() {
    }

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, data, true);
        } catch (ParserException e) {
            return false;
        }
    }

    protected void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }

    protected void onSeekEnd(long currentGranule) {
        boolean z;
        int i = 0;
        super.onSeekEnd(currentGranule);
        if (currentGranule != 0) {
            z = true;
        } else {
            z = false;
        }
        this.seenFirstAudioPacket = z;
        if (this.vorbisIdHeader != null) {
            i = this.vorbisIdHeader.blockSize0;
        }
        this.previousPacketBlockSize = i;
    }

    protected long preparePayload(ParsableByteArray packet) {
        int samplesInPacket = 0;
        if ((packet.data[0] & 1) == 1) {
            return -1;
        }
        int packetBlockSize = decodeBlockSize(packet.data[0], this.vorbisSetup);
        if (this.seenFirstAudioPacket) {
            samplesInPacket = (this.previousPacketBlockSize + packetBlockSize) / 4;
        }
        appendNumberOfSamples(packet, (long) samplesInPacket);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = packetBlockSize;
        return (long) samplesInPacket;
    }

    protected boolean readHeaders(ParsableByteArray packet, long position, SetupData setupData) throws IOException, InterruptedException {
        if (this.vorbisSetup != null) {
            return false;
        }
        this.vorbisSetup = readSetupHeaders(packet);
        if (this.vorbisSetup == null) {
            return true;
        }
        ArrayList<byte[]> codecInitialisationData = new ArrayList();
        codecInitialisationData.add(this.vorbisSetup.idHeader.data);
        codecInitialisationData.add(this.vorbisSetup.setupHeaderData);
        setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_VORBIS, null, this.vorbisSetup.idHeader.bitrateNominal, -1, this.vorbisSetup.idHeader.channels, (int) this.vorbisSetup.idHeader.sampleRate, codecInitialisationData, null, 0, null);
        return true;
    }

    VorbisSetup readSetupHeaders(ParsableByteArray scratch) throws IOException {
        if (this.vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(scratch);
            return null;
        } else if (this.commentHeader == null) {
            this.commentHeader = VorbisUtil.readVorbisCommentHeader(scratch);
            return null;
        } else {
            byte[] setupHeaderData = new byte[scratch.limit()];
            System.arraycopy(scratch.data, 0, setupHeaderData, 0, scratch.limit());
            Mode[] modes = VorbisUtil.readVorbisModes(scratch, this.vorbisIdHeader.channels);
            return new VorbisSetup(this.vorbisIdHeader, this.commentHeader, setupHeaderData, modes, VorbisUtil.iLog(modes.length - 1));
        }
    }

    static int readBits(byte src, int length, int leastSignificantBitIndex) {
        return (src >> leastSignificantBitIndex) & (255 >>> (8 - length));
    }

    static void appendNumberOfSamples(ParsableByteArray buffer, long packetSampleCount) {
        buffer.setLimit(buffer.limit() + 4);
        buffer.data[buffer.limit() - 4] = (byte) ((int) (packetSampleCount & 255));
        buffer.data[buffer.limit() - 3] = (byte) ((int) ((packetSampleCount >>> 8) & 255));
        buffer.data[buffer.limit() - 2] = (byte) ((int) ((packetSampleCount >>> 16) & 255));
        buffer.data[buffer.limit() - 1] = (byte) ((int) ((packetSampleCount >>> 24) & 255));
    }

    private static int decodeBlockSize(byte firstByteOfAudioPacket, VorbisSetup vorbisSetup) {
        if (vorbisSetup.modes[readBits(firstByteOfAudioPacket, vorbisSetup.iLogModes, 1)].blockFlag) {
            return vorbisSetup.idHeader.blockSize1;
        }
        return vorbisSetup.idHeader.blockSize0;
    }
}
