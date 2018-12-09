package org.telegram.messenger.exoplayer2.extractor.ogg;

import java.util.ArrayList;
import java.util.List;
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

        public VorbisSetup(VorbisIdHeader vorbisIdHeader, CommentHeader commentHeader, byte[] bArr, Mode[] modeArr, int i) {
            this.idHeader = vorbisIdHeader;
            this.commentHeader = commentHeader;
            this.setupHeaderData = bArr;
            this.modes = modeArr;
            this.iLogModes = i;
        }
    }

    VorbisReader() {
    }

    static void appendNumberOfSamples(ParsableByteArray parsableByteArray, long j) {
        parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        parsableByteArray.data[parsableByteArray.limit() - 4] = (byte) ((int) (j & 255));
        parsableByteArray.data[parsableByteArray.limit() - 3] = (byte) ((int) ((j >>> 8) & 255));
        parsableByteArray.data[parsableByteArray.limit() - 2] = (byte) ((int) ((j >>> 16) & 255));
        parsableByteArray.data[parsableByteArray.limit() - 1] = (byte) ((int) ((j >>> 24) & 255));
    }

    private static int decodeBlockSize(byte b, VorbisSetup vorbisSetup) {
        return !vorbisSetup.modes[readBits(b, vorbisSetup.iLogModes, 1)].blockFlag ? vorbisSetup.idHeader.blockSize0 : vorbisSetup.idHeader.blockSize1;
    }

    static int readBits(byte b, int i, int i2) {
        return (b >> i2) & (255 >>> (8 - i));
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException e) {
            return false;
        }
    }

    protected void onSeekEnd(long j) {
        int i = 0;
        super.onSeekEnd(j);
        this.seenFirstAudioPacket = j != 0;
        if (this.vorbisIdHeader != null) {
            i = this.vorbisIdHeader.blockSize0;
        }
        this.previousPacketBlockSize = i;
    }

    protected long preparePayload(ParsableByteArray parsableByteArray) {
        int i = 0;
        if ((parsableByteArray.data[0] & 1) == 1) {
            return -1;
        }
        int decodeBlockSize = decodeBlockSize(parsableByteArray.data[0], this.vorbisSetup);
        if (this.seenFirstAudioPacket) {
            i = (this.previousPacketBlockSize + decodeBlockSize) / 4;
        }
        appendNumberOfSamples(parsableByteArray, (long) i);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = decodeBlockSize;
        return (long) i;
    }

    protected boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData) {
        if (this.vorbisSetup != null) {
            return false;
        }
        this.vorbisSetup = readSetupHeaders(parsableByteArray);
        if (this.vorbisSetup == null) {
            return true;
        }
        List arrayList = new ArrayList();
        arrayList.add(this.vorbisSetup.idHeader.data);
        arrayList.add(this.vorbisSetup.setupHeaderData);
        setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_VORBIS, null, this.vorbisSetup.idHeader.bitrateNominal, -1, this.vorbisSetup.idHeader.channels, (int) this.vorbisSetup.idHeader.sampleRate, arrayList, null, 0, null);
        return true;
    }

    VorbisSetup readSetupHeaders(ParsableByteArray parsableByteArray) {
        if (this.vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
            return null;
        } else if (this.commentHeader == null) {
            this.commentHeader = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            return null;
        } else {
            Object obj = new byte[parsableByteArray.limit()];
            System.arraycopy(parsableByteArray.data, 0, obj, 0, parsableByteArray.limit());
            Mode[] readVorbisModes = VorbisUtil.readVorbisModes(parsableByteArray, this.vorbisIdHeader.channels);
            return new VorbisSetup(this.vorbisIdHeader, this.commentHeader, obj, readVorbisModes, VorbisUtil.iLog(readVorbisModes.length - 1));
        }
    }

    protected void reset(boolean z) {
        super.reset(z);
        if (z) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }
}
