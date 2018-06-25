package com.googlecode.mp4parser.authoring.tracks;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.DTSSpecificBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import java.io.EOFException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.telegram.customization.fetch.FetchService;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public class DTSTrackImpl extends AbstractTrack {
    private static final int BUFFER = 67108864;
    int bcCoreBitRate = 0;
    int bcCoreChannelMask = 0;
    int bcCoreMaxSampleRate = 0;
    int bitrate;
    int channelCount;
    int channelMask = 0;
    int codecDelayAtMaxFs = 0;
    int coreBitRate = 0;
    int coreChannelMask = 0;
    int coreFramePayloadInBytes = 0;
    int coreMaxSampleRate = 0;
    boolean coreSubStreamPresent = false;
    private int dataOffset = 0;
    private DataSource dataSource;
    DTSSpecificBox ddts = new DTSSpecificBox();
    int extAvgBitrate = 0;
    int extFramePayloadInBytes = 0;
    int extPeakBitrate = 0;
    int extSmoothBuffSize = 0;
    boolean extensionSubStreamPresent = false;
    int frameSize = 0;
    boolean isVBR = false;
    private String lang = "eng";
    int lbrCodingPresent = 0;
    int lsbTrimPercent = 0;
    int maxSampleRate = 0;
    int numExtSubStreams = 0;
    int numFramesTotal = 0;
    int numSamplesOrigAudioAtMaxFs = 0;
    SampleDescriptionBox sampleDescriptionBox;
    private long[] sampleDurations;
    int sampleSize;
    int samplerate;
    private List<Sample> samples;
    int samplesPerFrame;
    int samplesPerFrameAtMaxFs = 0;
    TrackMetaData trackMetaData = new TrackMetaData();
    String type = "none";

    class LookAhead {
        ByteBuffer buffer;
        long bufferStartPos;
        private final int corePresent;
        long dataEnd;
        DataSource dataSource;
        int inBufferPos = 0;
        long start;

        LookAhead(DataSource dataSource, long bufferStartPos, long dataSize, int corePresent) throws IOException {
            this.dataSource = dataSource;
            this.bufferStartPos = bufferStartPos;
            this.dataEnd = dataSize + bufferStartPos;
            this.corePresent = corePresent;
            fillBuffer();
        }

        public ByteBuffer findNextStart() throws IOException {
            while (true) {
                try {
                    if (this.corePresent == 1) {
                        if (nextFourEquals0x7FFE8001()) {
                            break;
                        }
                        discardByte();
                    } else if (nextFourEquals0x64582025()) {
                        break;
                    } else {
                        discardByte();
                    }
                } catch (EOFException e) {
                    return null;
                }
            }
            discardNext4AndMarkStart();
            while (true) {
                if (this.corePresent == 1) {
                    if (nextFourEquals0x7FFE8001orEof()) {
                        break;
                    }
                    discardQWord();
                } else if (nextFourEquals0x64582025orEof()) {
                    break;
                } else {
                    discardQWord();
                }
            }
            return getSample();
        }

        private void fillBuffer() throws IOException {
            System.err.println("Fill Buffer");
            this.buffer = this.dataSource.map(this.bufferStartPos, Math.min(this.dataEnd - this.bufferStartPos, 67108864));
        }

        private boolean nextFourEquals0x64582025() throws IOException {
            return nextFourEquals((byte) 100, (byte) 88, (byte) 32, (byte) 37);
        }

        private boolean nextFourEquals0x7FFE8001() throws IOException {
            return nextFourEquals(Byte.MAX_VALUE, (byte) -2, Byte.MIN_VALUE, (byte) 1);
        }

        private boolean nextFourEquals(byte a, byte b, byte c, byte d) throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 4) {
                if (this.buffer.get(this.inBufferPos) == a && this.buffer.get(this.inBufferPos + 1) == b && this.buffer.get(this.inBufferPos + 2) == c && this.buffer.get(this.inBufferPos + 3) == d) {
                    return true;
                }
                return false;
            } else if ((this.bufferStartPos + ((long) this.inBufferPos)) + 4 < this.dataSource.size()) {
                return false;
            } else {
                throw new EOFException();
            }
        }

        private boolean nextFourEquals0x64582025orEof() throws IOException {
            return nextFourEqualsOrEof((byte) 100, (byte) 88, (byte) 32, (byte) 37);
        }

        private boolean nextFourEquals0x7FFE8001orEof() throws IOException {
            return nextFourEqualsOrEof(Byte.MAX_VALUE, (byte) -2, Byte.MIN_VALUE, (byte) 1);
        }

        private boolean nextFourEqualsOrEof(byte a, byte b, byte c, byte d) throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 4) {
                if ((this.bufferStartPos + ((long) this.inBufferPos)) % 1048576 == 0) {
                    System.err.println((((this.bufferStartPos + ((long) this.inBufferPos)) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
                }
                if (this.buffer.get(this.inBufferPos) == a && this.buffer.get(this.inBufferPos + 1) == b && this.buffer.get(this.inBufferPos + 2) == c && this.buffer.get(this.inBufferPos + 3) == d) {
                    return true;
                }
                return false;
            } else if ((this.bufferStartPos + ((long) this.inBufferPos)) + 4 <= this.dataEnd) {
                this.bufferStartPos = this.start;
                this.inBufferPos = 0;
                fillBuffer();
                return nextFourEquals0x7FFE8001();
            } else if (this.bufferStartPos + ((long) this.inBufferPos) != this.dataEnd) {
                return false;
            } else {
                return true;
            }
        }

        private void discardByte() {
            this.inBufferPos++;
        }

        private void discardQWord() {
            this.inBufferPos += 4;
        }

        private void discardNext4AndMarkStart() {
            this.start = this.bufferStartPos + ((long) this.inBufferPos);
            this.inBufferPos += 4;
        }

        private ByteBuffer getSample() {
            if (this.start >= this.bufferStartPos) {
                this.buffer.position((int) (this.start - this.bufferStartPos));
                Buffer sample = this.buffer.slice();
                sample.limit((int) (((long) this.inBufferPos) - (this.start - this.bufferStartPos)));
                return (ByteBuffer) sample;
            }
            throw new RuntimeException("damn! NAL exceeds buffer");
        }
    }

    public DTSTrackImpl(DataSource dataSource, String lang) throws IOException {
        super(dataSource.toString());
        this.lang = lang;
        this.dataSource = dataSource;
        parse();
    }

    public DTSTrackImpl(DataSource dataSource) throws IOException {
        super(dataSource.toString());
        this.dataSource = dataSource;
        parse();
    }

    public void close() throws IOException {
        this.dataSource.close();
    }

    private void parse() throws IOException {
        if (readVariables()) {
            this.sampleDescriptionBox = new SampleDescriptionBox();
            AudioSampleEntry audioSampleEntry = new AudioSampleEntry(this.type);
            audioSampleEntry.setChannelCount(this.channelCount);
            audioSampleEntry.setSampleRate((long) this.samplerate);
            audioSampleEntry.setDataReferenceIndex(1);
            audioSampleEntry.setSampleSize(16);
            audioSampleEntry.addBox(this.ddts);
            this.sampleDescriptionBox.addBox(audioSampleEntry);
            this.trackMetaData.setCreationTime(new Date());
            this.trackMetaData.setModificationTime(new Date());
            this.trackMetaData.setLanguage(this.lang);
            this.trackMetaData.setTimescale((long) this.samplerate);
            return;
        }
        throw new IOException();
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    public List<Entry> getCompositionTimeEntries() {
        return null;
    }

    public long[] getSyncSamples() {
        return null;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return null;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "soun";
    }

    private void parseDtshdhdr(int size, ByteBuffer bb) {
        bb.getInt();
        bb.get();
        bb.getInt();
        bb.get();
        int bitwStreamMetadata = bb.getShort();
        bb.get();
        this.numExtSubStreams = bb.get();
        if ((bitwStreamMetadata & 1) == 1) {
            this.isVBR = true;
        }
        if ((bitwStreamMetadata & 8) == 8) {
            this.coreSubStreamPresent = true;
        }
        if ((bitwStreamMetadata & 16) == 16) {
            this.extensionSubStreamPresent = true;
            this.numExtSubStreams++;
        } else {
            this.numExtSubStreams = 0;
        }
        for (int i = 14; i < size; i++) {
            bb.get();
        }
    }

    private boolean parseCoressmd(int size, ByteBuffer bb) {
        this.coreMaxSampleRate = (bb.get() << 16) | (SupportMenu.USER_MASK & bb.getShort());
        this.coreBitRate = bb.getShort();
        this.coreChannelMask = bb.getShort();
        this.coreFramePayloadInBytes = bb.getInt();
        for (int i = 11; i < size; i++) {
            bb.get();
        }
        return true;
    }

    private boolean parseAuprhdr(int size, ByteBuffer bb) {
        bb.get();
        int bitwAupresData = bb.getShort();
        this.maxSampleRate = (bb.get() << 16) | (bb.getShort() & SupportMenu.USER_MASK);
        this.numFramesTotal = bb.getInt();
        this.samplesPerFrameAtMaxFs = bb.getShort();
        this.numSamplesOrigAudioAtMaxFs = (bb.get() << 32) | (bb.getInt() & SupportMenu.USER_MASK);
        this.channelMask = bb.getShort();
        this.codecDelayAtMaxFs = bb.getShort();
        int c = 21;
        if ((bitwAupresData & 3) == 3) {
            this.bcCoreMaxSampleRate = (bb.get() << 16) | (bb.getShort() & SupportMenu.USER_MASK);
            this.bcCoreBitRate = bb.getShort();
            this.bcCoreChannelMask = bb.getShort();
            c = 21 + 7;
        }
        if ((bitwAupresData & 4) > 0) {
            this.lsbTrimPercent = bb.get();
            c++;
        }
        if ((bitwAupresData & 8) > 0) {
            this.lbrCodingPresent = 1;
        }
        while (c < size) {
            bb.get();
            c++;
        }
        return true;
    }

    private boolean parseExtssmd(int size, ByteBuffer bb) {
        int i;
        this.extAvgBitrate = (bb.get() << 16) | (bb.getShort() & SupportMenu.USER_MASK);
        if (this.isVBR) {
            this.extPeakBitrate = (bb.get() << 16) | (bb.getShort() & SupportMenu.USER_MASK);
            this.extSmoothBuffSize = bb.getShort();
            i = 3 + 5;
        } else {
            this.extFramePayloadInBytes = bb.getInt();
            i = 3 + 4;
        }
        while (i < size) {
            bb.get();
            i++;
        }
        return true;
    }

    private boolean readVariables() throws IOException {
        ByteBuffer bb = this.dataSource.map(0, 25000);
        int testHeader1 = bb.getInt();
        int testHeader2 = bb.getInt();
        if (testHeader1 == 1146377032 && testHeader2 == 1145586770) {
            while (true) {
                if (!(testHeader1 == 1398035021 && testHeader2 == 1145132097) && bb.remaining() > 100) {
                    int size = (int) bb.getLong();
                    if (testHeader1 == 1146377032 && testHeader2 == 1145586770) {
                        parseDtshdhdr(size, bb);
                    } else if (testHeader1 == 1129271877 && testHeader2 == 1397968196) {
                        if (!parseCoressmd(size, bb)) {
                            return false;
                        }
                    } else if (testHeader1 == 1096110162 && testHeader2 == 759710802) {
                        if (!parseAuprhdr(size, bb)) {
                            return false;
                        }
                    } else if (testHeader1 != 1163416659 || testHeader2 != 1398754628) {
                        for (int i = 0; i < size; i++) {
                            bb.get();
                        }
                    } else if (!parseExtssmd(size, bb)) {
                        return false;
                    }
                    testHeader1 = bb.getInt();
                    testHeader2 = bb.getInt();
                }
            }
            long dataSize = bb.getLong();
            this.dataOffset = bb.position();
            int amode = -1;
            int extAudioId = 0;
            int extAudio = 0;
            int corePresent = -1;
            int extPresent = -1;
            int extXch = 0;
            int extXXch = 0;
            int extX96k = 0;
            int extXbr = 0;
            int extLbr = 0;
            int extXll = 0;
            int extCore = 0;
            boolean done = false;
            while (!done) {
                int offset = bb.position();
                int sync = bb.getInt();
                BitReaderBuffer brb;
                if (sync == 2147385345) {
                    if (corePresent == 1) {
                        done = true;
                    } else {
                        corePresent = 1;
                        brb = new BitReaderBuffer(bb);
                        int ftype = brb.readBits(1);
                        int shrt = brb.readBits(5);
                        int cpf = brb.readBits(1);
                        if (ftype != 1 || shrt != 31 || cpf != 0) {
                            return false;
                        }
                        this.samplesPerFrame = (brb.readBits(7) + 1) * 32;
                        int fsize = brb.readBits(14);
                        this.frameSize += fsize + 1;
                        amode = brb.readBits(6);
                        this.samplerate = getSampleRate(brb.readBits(4));
                        this.bitrate = getBitRate(brb.readBits(5));
                        if (brb.readBits(1) != 0) {
                            return false;
                        }
                        brb.readBits(1);
                        brb.readBits(1);
                        brb.readBits(1);
                        brb.readBits(1);
                        extAudioId = brb.readBits(3);
                        extAudio = brb.readBits(1);
                        brb.readBits(1);
                        brb.readBits(2);
                        brb.readBits(1);
                        if (cpf == 1) {
                            brb.readBits(16);
                        }
                        brb.readBits(1);
                        int vernum = brb.readBits(4);
                        brb.readBits(2);
                        switch (brb.readBits(3)) {
                            case 0:
                            case 1:
                                this.sampleSize = 16;
                                break;
                            case 2:
                            case 3:
                                this.sampleSize = 20;
                                break;
                            case 5:
                            case 6:
                                this.sampleSize = 24;
                                break;
                            default:
                                return false;
                        }
                        brb.readBits(1);
                        brb.readBits(1);
                        int i2;
                        switch (vernum) {
                            case 6:
                                i2 = -(brb.readBits(4) + 16);
                                break;
                            case 7:
                                i2 = -brb.readBits(4);
                                break;
                            default:
                                brb.readBits(4);
                                break;
                        }
                        bb.position((offset + fsize) + 1);
                    }
                } else if (sync == 1683496997) {
                    if (corePresent == -1) {
                        corePresent = 0;
                        this.samplesPerFrame = this.samplesPerFrameAtMaxFs;
                    }
                    extPresent = 1;
                    brb = new BitReaderBuffer(bb);
                    brb.readBits(8);
                    brb.readBits(2);
                    int nuBits4Header = 12;
                    int nuBits4ExSSFsize = 20;
                    if (brb.readBits(1) == 0) {
                        nuBits4Header = 8;
                        nuBits4ExSSFsize = 16;
                    }
                    int nuExtSSFsize = brb.readBits(nuBits4ExSSFsize) + 1;
                    bb.position(offset + (brb.readBits(nuBits4Header) + 1));
                    int extSync = bb.getInt();
                    if (extSync == 1515870810) {
                        if (extXch == 1) {
                            done = true;
                        }
                        extXch = 1;
                    } else if (extSync == 1191201283) {
                        if (extXXch == 1) {
                            done = true;
                        }
                        extXXch = 1;
                    } else if (extSync == 496366178) {
                        if (extX96k == 1) {
                            done = true;
                        }
                        extX96k = 1;
                    } else if (extSync == 1700671838) {
                        if (extXbr == 1) {
                            done = true;
                        }
                        extXbr = 1;
                    } else if (extSync == 176167201) {
                        if (extLbr == 1) {
                            done = true;
                        }
                        extLbr = 1;
                    } else if (extSync == 1101174087) {
                        if (extXll == 1) {
                            done = true;
                        }
                        extXll = 1;
                    } else if (extSync == 45126241) {
                        if (extCore == 1) {
                            done = true;
                        }
                        extCore = 1;
                    }
                    if (!done) {
                        this.frameSize += nuExtSSFsize;
                    }
                    bb.position(offset + nuExtSSFsize);
                } else {
                    throw new IOException("No DTS_SYNCWORD_* found at " + bb.position());
                }
            }
            int fd = -1;
            switch (this.samplesPerFrame) {
                case 512:
                    fd = 0;
                    break;
                case 1024:
                    fd = 1;
                    break;
                case 2048:
                    fd = 2;
                    break;
                case 4096:
                    fd = 3;
                    break;
            }
            if (fd == -1) {
                return false;
            }
            int coreLayout = 31;
            switch (amode) {
                case 0:
                case 2:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    coreLayout = amode;
                    break;
            }
            int streamContruction = 0;
            if (corePresent == 0) {
                if (extXll == 1) {
                    if (extCore == 0) {
                        streamContruction = 17;
                        this.type = AudioSampleEntry.TYPE11;
                    } else {
                        streamContruction = 21;
                        this.type = AudioSampleEntry.TYPE12;
                    }
                } else if (extLbr == 1) {
                    streamContruction = 18;
                    this.type = AudioSampleEntry.TYPE13;
                } else if (extCore == 1) {
                    this.type = AudioSampleEntry.TYPE12;
                    if (extXXch == 0 && extXll == 0) {
                        streamContruction = 19;
                    } else if (extXXch == 1 && extXll == 0) {
                        streamContruction = 20;
                    } else if (extXXch == 0 && extXll == 1) {
                        streamContruction = 21;
                    }
                }
                this.samplerate = this.maxSampleRate;
                this.sampleSize = 24;
            } else if (extPresent >= 1) {
                this.type = AudioSampleEntry.TYPE12;
                if (extAudio == 0) {
                    if (extCore == 0 && extXXch == 1 && extX96k == 0 && extXbr == 0 && extXll == 0 && extLbr == 0) {
                        streamContruction = 5;
                    } else if (extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 1 && extXll == 0 && extLbr == 0) {
                        streamContruction = 6;
                    } else if (extCore == 0 && extXXch == 1 && extX96k == 0 && extXbr == 1 && extXll == 0 && extLbr == 0) {
                        streamContruction = 9;
                    } else if (extCore == 0 && extXXch == 0 && extX96k == 1 && extXbr == 0 && extXll == 0 && extLbr == 0) {
                        streamContruction = 10;
                    } else if (extCore == 0 && extXXch == 1 && extX96k == 1 && extXbr == 0 && extXll == 0 && extLbr == 0) {
                        streamContruction = 13;
                    } else if (extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 0 && extXll == 1 && extLbr == 0) {
                        streamContruction = 14;
                    }
                } else if (extAudioId == 0 && extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 1 && extXll == 0 && extLbr == 0) {
                    streamContruction = 7;
                } else if (extAudioId == 6 && extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 1 && extXll == 0 && extLbr == 0) {
                    streamContruction = 8;
                } else if (extAudioId == 0 && extCore == 0 && extXXch == 0 && extX96k == 1 && extXbr == 0 && extXll == 0 && extLbr == 0) {
                    streamContruction = 11;
                } else if (extAudioId == 6 && extCore == 0 && extXXch == 0 && extX96k == 1 && extXbr == 0 && extXll == 0 && extLbr == 0) {
                    streamContruction = 12;
                } else if (extAudioId == 0 && extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 0 && extXll == 1 && extLbr == 0) {
                    streamContruction = 15;
                } else if (extAudioId == 2 && extCore == 0 && extXXch == 0 && extX96k == 0 && extXbr == 0 && extXll == 1 && extLbr == 0) {
                    streamContruction = 16;
                }
            } else if (extAudio > 0) {
                switch (extAudioId) {
                    case 0:
                        streamContruction = 2;
                        this.type = "dtsc";
                        break;
                    case 2:
                        streamContruction = 4;
                        this.type = "dtsc";
                        break;
                    case 6:
                        streamContruction = 3;
                        this.type = AudioSampleEntry.TYPE12;
                        break;
                    default:
                        streamContruction = 0;
                        this.type = AudioSampleEntry.TYPE12;
                        break;
                }
            } else {
                streamContruction = 1;
                this.type = "dtsc";
            }
            this.ddts.setDTSSamplingFrequency((long) this.maxSampleRate);
            if (this.isVBR) {
                this.ddts.setMaxBitRate((long) ((this.coreBitRate + this.extPeakBitrate) * 1000));
            } else {
                this.ddts.setMaxBitRate((long) ((this.coreBitRate + this.extAvgBitrate) * 1000));
            }
            this.ddts.setAvgBitRate((long) ((this.coreBitRate + this.extAvgBitrate) * 1000));
            this.ddts.setPcmSampleDepth(this.sampleSize);
            this.ddts.setFrameDuration(fd);
            this.ddts.setStreamConstruction(streamContruction);
            if ((this.coreChannelMask & 8) > 0 || (this.coreChannelMask & 4096) > 0) {
                this.ddts.setCoreLFEPresent(1);
            } else {
                this.ddts.setCoreLFEPresent(0);
            }
            this.ddts.setCoreLayout(coreLayout);
            this.ddts.setCoreSize(this.coreFramePayloadInBytes);
            this.ddts.setStereoDownmix(0);
            this.ddts.setRepresentationType(4);
            this.ddts.setChannelLayout(this.channelMask);
            if (this.coreMaxSampleRate <= 0 || this.extAvgBitrate <= 0) {
                this.ddts.setMultiAssetFlag(0);
            } else {
                this.ddts.setMultiAssetFlag(1);
            }
            this.ddts.setLBRDurationMod(this.lbrCodingPresent);
            this.ddts.setReservedBoxPresent(0);
            this.channelCount = 0;
            for (int bit = 0; bit < 16; bit++) {
                if (((this.channelMask >> bit) & 1) == 1) {
                    switch (bit) {
                        case 0:
                        case 3:
                        case 4:
                        case 7:
                        case 8:
                        case 12:
                        case 14:
                            this.channelCount++;
                            break;
                        default:
                            this.channelCount += 2;
                            break;
                    }
                }
            }
            this.samples = generateSamples(this.dataSource, this.dataOffset, dataSize, corePresent);
            this.sampleDurations = new long[this.samples.size()];
            Arrays.fill(this.sampleDurations, (long) this.samplesPerFrame);
            return true;
        }
        throw new IOException("data does not start with 'DTSHDHDR' as required for a DTS-HD file");
    }

    private List<Sample> generateSamples(DataSource dataSource, int dataOffset, long dataSize, int corePresent) throws IOException {
        LookAhead la = new LookAhead(dataSource, (long) dataOffset, dataSize, corePresent);
        List<Sample> mySamples = new ArrayList();
        while (true) {
            ByteBuffer sample = la.findNextStart();
            if (sample == null) {
                System.err.println("all samples found");
                return mySamples;
            }
            final ByteBuffer finalSample = sample;
            mySamples.add(new Sample() {
                public void writeTo(WritableByteChannel channel) throws IOException {
                    channel.write((ByteBuffer) finalSample.rewind());
                }

                public long getSize() {
                    return (long) finalSample.rewind().remaining();
                }

                public ByteBuffer asByteBuffer() {
                    return finalSample;
                }
            });
        }
    }

    private int getBitRate(int rate) throws IOException {
        switch (rate) {
            case 0:
                return 32;
            case 1:
                return 56;
            case 2:
                return 64;
            case 3:
                return 96;
            case 4:
                return 112;
            case 5:
                return 128;
            case 6:
                return PsExtractor.AUDIO_STREAM;
            case 7:
                return 224;
            case 8:
                return 256;
            case 9:
                return FetchService.ACTION_LOGGING;
            case 10:
                return 384;
            case 11:
                return 448;
            case 12:
                return 512;
            case 13:
                return 576;
            case 14:
                return 640;
            case 15:
                return 768;
            case 16:
                return 960;
            case 17:
                return 1024;
            case 18:
                return 1152;
            case 19:
                return 1280;
            case 20:
                return 1344;
            case 21:
                return 1408;
            case 22:
                return 1411;
            case 23:
                return 1472;
            case 24:
                return 1536;
            case 25:
                return -1;
            default:
                throw new IOException("Unknown bitrate value");
        }
    }

    private int getSampleRate(int sfreq) throws IOException {
        switch (sfreq) {
            case 1:
                return 8000;
            case 2:
                return 16000;
            case 3:
                return 32000;
            case 6:
                return 11025;
            case 7:
                return 22050;
            case 8:
                return 44100;
            case 11:
                return 12000;
            case 12:
                return 24000;
            case 13:
                return 48000;
            default:
                throw new IOException("Unknown Sample Rate");
        }
    }
}
