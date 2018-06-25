package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.AC3SpecificBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AC3TrackImpl extends AbstractTrack {
    static int[][][][] bitRateAndFrameSizeTable = ((int[][][][]) Array.newInstance(Integer.TYPE, new int[]{19, 2, 3, 2}));
    private final DataSource dataSource;
    private long[] duration;
    private SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    private TrackMetaData trackMetaData;

    /* renamed from: com.googlecode.mp4parser.authoring.tracks.AC3TrackImpl$1SampleImpl */
    class AnonymousClass1SampleImpl implements Sample {
        private final DataSource dataSource;
        private final long size;
        private final long start;

        public AnonymousClass1SampleImpl(long start, long size, DataSource dataSource) {
            this.start = start;
            this.size = size;
            this.dataSource = dataSource;
        }

        public void writeTo(WritableByteChannel channel) throws IOException {
            this.dataSource.transferTo(this.start, this.size, channel);
        }

        public long getSize() {
            return this.size;
        }

        public ByteBuffer asByteBuffer() {
            try {
                return this.dataSource.map(this.start, this.size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AC3TrackImpl(DataSource dataSource) throws IOException {
        this(dataSource, "eng");
    }

    public AC3TrackImpl(DataSource dataSource, String lang) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.dataSource = dataSource;
        this.trackMetaData.setLanguage(lang);
        this.samples = readSamples();
        this.sampleDescriptionBox = new SampleDescriptionBox();
        AudioSampleEntry ase = createAudioSampleEntry();
        this.sampleDescriptionBox.addBox(ase);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(lang);
        this.trackMetaData.setTimescale(ase.getSampleRate());
        this.trackMetaData.setVolume(1.0f);
    }

    public void close() throws IOException {
        this.dataSource.close();
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public synchronized long[] getSampleDurations() {
        return this.duration;
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

    public SubSampleInformationBox getSubsampleInformationBox() {
        return null;
    }

    private AudioSampleEntry createAudioSampleEntry() throws IOException {
        BitReaderBuffer brb = new BitReaderBuffer(((Sample) this.samples.get(0)).asByteBuffer());
        if (brb.readBits(16) != 2935) {
            throw new RuntimeException("Stream doesn't seem to be AC3");
        }
        int samplerate;
        brb.readBits(16);
        int fscod = brb.readBits(2);
        switch (fscod) {
            case 0:
                samplerate = 48000;
                break;
            case 1:
                samplerate = 44100;
                break;
            case 2:
                samplerate = 32000;
                break;
            default:
                throw new RuntimeException("Unsupported Sample Rate");
        }
        int frmsizecod = brb.readBits(6);
        int bsid = brb.readBits(5);
        int bsmod = brb.readBits(3);
        int acmod = brb.readBits(3);
        if (bsid == 16) {
            throw new RuntimeException("You cannot read E-AC-3 track with AC3TrackImpl.class - user EC3TrackImpl.class");
        }
        if (bsid == 9) {
            samplerate /= 2;
        } else if (!(bsid == 8 || bsid == 6)) {
            throw new RuntimeException("Unsupported bsid");
        }
        if (acmod != 1 && (acmod & 1) == 1) {
            brb.readBits(2);
        }
        if ((acmod & 4) != 0) {
            brb.readBits(2);
        }
        if (acmod == 2) {
            brb.readBits(2);
        }
        switch (acmod) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                AudioSampleEntry audioSampleEntry;
                int lfeon = brb.readBits(1);
                AC3SpecificBox ac3;
                if (lfeon == 1) {
                    audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE8);
                    audioSampleEntry.setChannelCount(2);
                    audioSampleEntry.setSampleRate((long) samplerate);
                    audioSampleEntry.setDataReferenceIndex(1);
                    audioSampleEntry.setSampleSize(16);
                    ac3 = new AC3SpecificBox();
                    ac3.setAcmod(acmod);
                    ac3.setBitRateCode(frmsizecod >> 1);
                    ac3.setBsid(bsid);
                    ac3.setBsmod(bsmod);
                    ac3.setFscod(fscod);
                    ac3.setLfeon(lfeon);
                    ac3.setReserved(0);
                    audioSampleEntry.addBox(ac3);
                } else {
                    audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE8);
                    audioSampleEntry.setChannelCount(2);
                    audioSampleEntry.setSampleRate((long) samplerate);
                    audioSampleEntry.setDataReferenceIndex(1);
                    audioSampleEntry.setSampleSize(16);
                    ac3 = new AC3SpecificBox();
                    ac3.setAcmod(acmod);
                    ac3.setBitRateCode(frmsizecod >> 1);
                    ac3.setBsid(bsid);
                    ac3.setBsmod(bsmod);
                    ac3.setFscod(fscod);
                    ac3.setLfeon(lfeon);
                    ac3.setReserved(0);
                    audioSampleEntry.addBox(ac3);
                }
                return audioSampleEntry;
            default:
                throw new RuntimeException("Unsupported acmod");
        }
    }

    private int getFrameSize(int code, int fscod) {
        int frmsizecode = code >>> 1;
        int flag = code & 1;
        if (frmsizecode <= 18 && flag <= 1 && fscod <= 2) {
            return bitRateAndFrameSizeTable[frmsizecode][flag][fscod][1] * 2;
        }
        throw new RuntimeException("Cannot determine framesize of current sample");
    }

    private List<Sample> readSamples() throws IOException {
        ByteBuffer header = ByteBuffer.allocate(5);
        List<Sample> mysamples = new ArrayList();
        while (-1 != this.dataSource.read(header)) {
            int frameSize = getFrameSize(header.get(4) & 63, header.get(4) >> 6);
            mysamples.add(new AnonymousClass1SampleImpl(this.dataSource.position() - 5, (long) frameSize, this.dataSource));
            this.dataSource.position((this.dataSource.position() - 5) + ((long) frameSize));
            header.rewind();
        }
        this.duration = new long[mysamples.size()];
        Arrays.fill(this.duration, 1536);
        return mysamples;
    }

    static {
        bitRateAndFrameSizeTable[0][0][0][0] = 32;
        bitRateAndFrameSizeTable[0][1][0][0] = 32;
        bitRateAndFrameSizeTable[0][0][0][1] = 64;
        bitRateAndFrameSizeTable[0][1][0][1] = 64;
        bitRateAndFrameSizeTable[1][0][0][0] = 40;
        bitRateAndFrameSizeTable[1][1][0][0] = 40;
        bitRateAndFrameSizeTable[1][0][0][1] = 80;
        bitRateAndFrameSizeTable[1][1][0][1] = 80;
        bitRateAndFrameSizeTable[2][0][0][0] = 48;
        bitRateAndFrameSizeTable[2][1][0][0] = 48;
        bitRateAndFrameSizeTable[2][0][0][1] = 96;
        bitRateAndFrameSizeTable[2][1][0][1] = 96;
        bitRateAndFrameSizeTable[3][0][0][0] = 56;
        bitRateAndFrameSizeTable[3][1][0][0] = 56;
        bitRateAndFrameSizeTable[3][0][0][1] = 112;
        bitRateAndFrameSizeTable[3][1][0][1] = 112;
        bitRateAndFrameSizeTable[4][0][0][0] = 64;
        bitRateAndFrameSizeTable[4][1][0][0] = 64;
        bitRateAndFrameSizeTable[4][0][0][1] = 128;
        bitRateAndFrameSizeTable[4][1][0][1] = 128;
        bitRateAndFrameSizeTable[5][0][0][0] = 80;
        bitRateAndFrameSizeTable[5][1][0][0] = 80;
        bitRateAndFrameSizeTable[5][0][0][1] = 160;
        bitRateAndFrameSizeTable[5][1][0][1] = 160;
        bitRateAndFrameSizeTable[6][0][0][0] = 96;
        bitRateAndFrameSizeTable[6][1][0][0] = 96;
        bitRateAndFrameSizeTable[6][0][0][1] = 192;
        bitRateAndFrameSizeTable[6][1][0][1] = 192;
        bitRateAndFrameSizeTable[7][0][0][0] = 112;
        bitRateAndFrameSizeTable[7][1][0][0] = 112;
        bitRateAndFrameSizeTable[7][0][0][1] = 224;
        bitRateAndFrameSizeTable[7][1][0][1] = 224;
        bitRateAndFrameSizeTable[8][0][0][0] = 128;
        bitRateAndFrameSizeTable[8][1][0][0] = 128;
        bitRateAndFrameSizeTable[8][0][0][1] = 256;
        bitRateAndFrameSizeTable[8][1][0][1] = 256;
        bitRateAndFrameSizeTable[9][0][0][0] = 160;
        bitRateAndFrameSizeTable[9][1][0][0] = 160;
        bitRateAndFrameSizeTable[9][0][0][1] = 320;
        bitRateAndFrameSizeTable[9][1][0][1] = 320;
        bitRateAndFrameSizeTable[10][0][0][0] = 192;
        bitRateAndFrameSizeTable[10][1][0][0] = 192;
        bitRateAndFrameSizeTable[10][0][0][1] = 384;
        bitRateAndFrameSizeTable[10][1][0][1] = 384;
        bitRateAndFrameSizeTable[11][0][0][0] = 224;
        bitRateAndFrameSizeTable[11][1][0][0] = 224;
        bitRateAndFrameSizeTable[11][0][0][1] = 448;
        bitRateAndFrameSizeTable[11][1][0][1] = 448;
        bitRateAndFrameSizeTable[12][0][0][0] = 256;
        bitRateAndFrameSizeTable[12][1][0][0] = 256;
        bitRateAndFrameSizeTable[12][0][0][1] = 512;
        bitRateAndFrameSizeTable[12][1][0][1] = 512;
        bitRateAndFrameSizeTable[13][0][0][0] = 320;
        bitRateAndFrameSizeTable[13][1][0][0] = 320;
        bitRateAndFrameSizeTable[13][0][0][1] = 640;
        bitRateAndFrameSizeTable[13][1][0][1] = 640;
        bitRateAndFrameSizeTable[14][0][0][0] = 384;
        bitRateAndFrameSizeTable[14][1][0][0] = 384;
        bitRateAndFrameSizeTable[14][0][0][1] = 768;
        bitRateAndFrameSizeTable[14][1][0][1] = 768;
        bitRateAndFrameSizeTable[15][0][0][0] = 448;
        bitRateAndFrameSizeTable[15][1][0][0] = 448;
        bitRateAndFrameSizeTable[15][0][0][1] = 896;
        bitRateAndFrameSizeTable[15][1][0][1] = 896;
        bitRateAndFrameSizeTable[16][0][0][0] = 512;
        bitRateAndFrameSizeTable[16][1][0][0] = 512;
        bitRateAndFrameSizeTable[16][0][0][1] = 1024;
        bitRateAndFrameSizeTable[16][1][0][1] = 1024;
        bitRateAndFrameSizeTable[17][0][0][0] = 576;
        bitRateAndFrameSizeTable[17][1][0][0] = 576;
        bitRateAndFrameSizeTable[17][0][0][1] = 1152;
        bitRateAndFrameSizeTable[17][1][0][1] = 1152;
        bitRateAndFrameSizeTable[18][0][0][0] = 640;
        bitRateAndFrameSizeTable[18][1][0][0] = 640;
        bitRateAndFrameSizeTable[18][0][0][1] = 1280;
        bitRateAndFrameSizeTable[18][1][0][1] = 1280;
        bitRateAndFrameSizeTable[0][0][1][0] = 32;
        bitRateAndFrameSizeTable[0][1][1][0] = 32;
        bitRateAndFrameSizeTable[0][0][1][1] = 69;
        bitRateAndFrameSizeTable[0][1][1][1] = 70;
        bitRateAndFrameSizeTable[1][0][1][0] = 40;
        bitRateAndFrameSizeTable[1][1][1][0] = 40;
        bitRateAndFrameSizeTable[1][0][1][1] = 87;
        bitRateAndFrameSizeTable[1][1][1][1] = 88;
        bitRateAndFrameSizeTable[2][0][1][0] = 48;
        bitRateAndFrameSizeTable[2][1][1][0] = 48;
        bitRateAndFrameSizeTable[2][0][1][1] = 104;
        bitRateAndFrameSizeTable[2][1][1][1] = 105;
        bitRateAndFrameSizeTable[3][0][1][0] = 56;
        bitRateAndFrameSizeTable[3][1][1][0] = 56;
        bitRateAndFrameSizeTable[3][0][1][1] = 121;
        bitRateAndFrameSizeTable[3][1][1][1] = 122;
        bitRateAndFrameSizeTable[4][0][1][0] = 64;
        bitRateAndFrameSizeTable[4][1][1][0] = 64;
        bitRateAndFrameSizeTable[4][0][1][1] = 139;
        bitRateAndFrameSizeTable[4][1][1][1] = 140;
        bitRateAndFrameSizeTable[5][0][1][0] = 80;
        bitRateAndFrameSizeTable[5][1][1][0] = 80;
        bitRateAndFrameSizeTable[5][0][1][1] = 174;
        bitRateAndFrameSizeTable[5][1][1][1] = 175;
        bitRateAndFrameSizeTable[6][0][1][0] = 96;
        bitRateAndFrameSizeTable[6][1][1][0] = 96;
        bitRateAndFrameSizeTable[6][0][1][1] = 208;
        bitRateAndFrameSizeTable[6][1][1][1] = 209;
        bitRateAndFrameSizeTable[7][0][1][0] = 112;
        bitRateAndFrameSizeTable[7][1][1][0] = 112;
        bitRateAndFrameSizeTable[7][0][1][1] = 243;
        bitRateAndFrameSizeTable[7][1][1][1] = 244;
        bitRateAndFrameSizeTable[8][0][1][0] = 128;
        bitRateAndFrameSizeTable[8][1][1][0] = 128;
        bitRateAndFrameSizeTable[8][0][1][1] = 278;
        bitRateAndFrameSizeTable[8][1][1][1] = 279;
        bitRateAndFrameSizeTable[9][0][1][0] = 160;
        bitRateAndFrameSizeTable[9][1][1][0] = 160;
        bitRateAndFrameSizeTable[9][0][1][1] = 348;
        bitRateAndFrameSizeTable[9][1][1][1] = 349;
        bitRateAndFrameSizeTable[10][0][1][0] = 192;
        bitRateAndFrameSizeTable[10][1][1][0] = 192;
        bitRateAndFrameSizeTable[10][0][1][1] = 417;
        bitRateAndFrameSizeTable[10][1][1][1] = 418;
        bitRateAndFrameSizeTable[11][0][1][0] = 224;
        bitRateAndFrameSizeTable[11][1][1][0] = 224;
        bitRateAndFrameSizeTable[11][0][1][1] = 487;
        bitRateAndFrameSizeTable[11][1][1][1] = 488;
        bitRateAndFrameSizeTable[12][0][1][0] = 256;
        bitRateAndFrameSizeTable[12][1][1][0] = 256;
        bitRateAndFrameSizeTable[12][0][1][1] = 557;
        bitRateAndFrameSizeTable[12][1][1][1] = 558;
        bitRateAndFrameSizeTable[13][0][1][0] = 320;
        bitRateAndFrameSizeTable[13][1][1][0] = 320;
        bitRateAndFrameSizeTable[13][0][1][1] = 696;
        bitRateAndFrameSizeTable[13][1][1][1] = 697;
        bitRateAndFrameSizeTable[14][0][1][0] = 384;
        bitRateAndFrameSizeTable[14][1][1][0] = 384;
        bitRateAndFrameSizeTable[14][0][1][1] = 835;
        bitRateAndFrameSizeTable[14][1][1][1] = 836;
        bitRateAndFrameSizeTable[15][0][1][0] = 448;
        bitRateAndFrameSizeTable[15][1][1][0] = 448;
        bitRateAndFrameSizeTable[15][0][1][1] = 975;
        bitRateAndFrameSizeTable[15][1][1][1] = 975;
        bitRateAndFrameSizeTable[16][0][1][0] = 512;
        bitRateAndFrameSizeTable[16][1][1][0] = 512;
        bitRateAndFrameSizeTable[16][0][1][1] = 1114;
        bitRateAndFrameSizeTable[16][1][1][1] = 1115;
        bitRateAndFrameSizeTable[17][0][1][0] = 576;
        bitRateAndFrameSizeTable[17][1][1][0] = 576;
        bitRateAndFrameSizeTable[17][0][1][1] = 1253;
        bitRateAndFrameSizeTable[17][1][1][1] = 1254;
        bitRateAndFrameSizeTable[18][0][1][0] = 640;
        bitRateAndFrameSizeTable[18][1][1][0] = 640;
        bitRateAndFrameSizeTable[18][0][1][1] = 1393;
        bitRateAndFrameSizeTable[18][1][1][1] = 1394;
        bitRateAndFrameSizeTable[0][0][2][0] = 32;
        bitRateAndFrameSizeTable[0][1][2][0] = 32;
        bitRateAndFrameSizeTable[0][0][2][1] = 96;
        bitRateAndFrameSizeTable[0][1][2][1] = 96;
        bitRateAndFrameSizeTable[1][0][2][0] = 40;
        bitRateAndFrameSizeTable[1][1][2][0] = 40;
        bitRateAndFrameSizeTable[1][0][2][1] = 120;
        bitRateAndFrameSizeTable[1][1][2][1] = 120;
        bitRateAndFrameSizeTable[2][0][2][0] = 48;
        bitRateAndFrameSizeTable[2][1][2][0] = 48;
        bitRateAndFrameSizeTable[2][0][2][1] = 144;
        bitRateAndFrameSizeTable[2][1][2][1] = 144;
        bitRateAndFrameSizeTable[3][0][2][0] = 56;
        bitRateAndFrameSizeTable[3][1][2][0] = 56;
        bitRateAndFrameSizeTable[3][0][2][1] = 168;
        bitRateAndFrameSizeTable[3][1][2][1] = 168;
        bitRateAndFrameSizeTable[4][0][2][0] = 64;
        bitRateAndFrameSizeTable[4][1][2][0] = 64;
        bitRateAndFrameSizeTable[4][0][2][1] = 192;
        bitRateAndFrameSizeTable[4][1][2][1] = 192;
        bitRateAndFrameSizeTable[5][0][2][0] = 80;
        bitRateAndFrameSizeTable[5][1][2][0] = 80;
        bitRateAndFrameSizeTable[5][0][2][1] = 240;
        bitRateAndFrameSizeTable[5][1][2][1] = 240;
        bitRateAndFrameSizeTable[6][0][2][0] = 96;
        bitRateAndFrameSizeTable[6][1][2][0] = 96;
        bitRateAndFrameSizeTable[6][0][2][1] = 288;
        bitRateAndFrameSizeTable[6][1][2][1] = 288;
        bitRateAndFrameSizeTable[7][0][2][0] = 112;
        bitRateAndFrameSizeTable[7][1][2][0] = 112;
        bitRateAndFrameSizeTable[7][0][2][1] = 336;
        bitRateAndFrameSizeTable[7][1][2][1] = 336;
        bitRateAndFrameSizeTable[8][0][2][0] = 128;
        bitRateAndFrameSizeTable[8][1][2][0] = 128;
        bitRateAndFrameSizeTable[8][0][2][1] = 384;
        bitRateAndFrameSizeTable[8][1][2][1] = 384;
        bitRateAndFrameSizeTable[9][0][2][0] = 160;
        bitRateAndFrameSizeTable[9][1][2][0] = 160;
        bitRateAndFrameSizeTable[9][0][2][1] = 480;
        bitRateAndFrameSizeTable[9][1][2][1] = 480;
        bitRateAndFrameSizeTable[10][0][2][0] = 192;
        bitRateAndFrameSizeTable[10][1][2][0] = 192;
        bitRateAndFrameSizeTable[10][0][2][1] = 576;
        bitRateAndFrameSizeTable[10][1][2][1] = 576;
        bitRateAndFrameSizeTable[11][0][2][0] = 224;
        bitRateAndFrameSizeTable[11][1][2][0] = 224;
        bitRateAndFrameSizeTable[11][0][2][1] = 672;
        bitRateAndFrameSizeTable[11][1][2][1] = 672;
        bitRateAndFrameSizeTable[12][0][2][0] = 256;
        bitRateAndFrameSizeTable[12][1][2][0] = 256;
        bitRateAndFrameSizeTable[12][0][2][1] = 768;
        bitRateAndFrameSizeTable[12][1][2][1] = 768;
        bitRateAndFrameSizeTable[13][0][2][0] = 320;
        bitRateAndFrameSizeTable[13][1][2][0] = 320;
        bitRateAndFrameSizeTable[13][0][2][1] = 960;
        bitRateAndFrameSizeTable[13][1][2][1] = 960;
        bitRateAndFrameSizeTable[14][0][2][0] = 384;
        bitRateAndFrameSizeTable[14][1][2][0] = 384;
        bitRateAndFrameSizeTable[14][0][2][1] = 1152;
        bitRateAndFrameSizeTable[14][1][2][1] = 1152;
        bitRateAndFrameSizeTable[15][0][2][0] = 448;
        bitRateAndFrameSizeTable[15][1][2][0] = 448;
        bitRateAndFrameSizeTable[15][0][2][1] = 1344;
        bitRateAndFrameSizeTable[15][1][2][1] = 1344;
        bitRateAndFrameSizeTable[16][0][2][0] = 512;
        bitRateAndFrameSizeTable[16][1][2][0] = 512;
        bitRateAndFrameSizeTable[16][0][2][1] = 1536;
        bitRateAndFrameSizeTable[16][1][2][1] = 1536;
        bitRateAndFrameSizeTable[17][0][2][0] = 576;
        bitRateAndFrameSizeTable[17][1][2][0] = 576;
        bitRateAndFrameSizeTable[17][0][2][1] = 1728;
        bitRateAndFrameSizeTable[17][1][2][1] = 1728;
        bitRateAndFrameSizeTable[18][0][2][0] = 640;
        bitRateAndFrameSizeTable[18][1][2][0] = 640;
        bitRateAndFrameSizeTable[18][0][2][1] = 1920;
        bitRateAndFrameSizeTable[18][1][2][1] = 1920;
    }
}
