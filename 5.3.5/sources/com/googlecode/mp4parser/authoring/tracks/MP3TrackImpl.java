package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.SLConfigDescriptor;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MP3TrackImpl extends AbstractTrack {
    private static final int[] BIT_RATE;
    private static final int ES_OBJECT_TYPE_INDICATION = 107;
    private static final int ES_STREAM_TYPE = 5;
    private static final int MPEG_L3 = 1;
    private static final int MPEG_V1 = 3;
    private static final int SAMPLES_PER_FRAME = 1152;
    private static final int[] SAMPLE_RATE;
    long avgBitRate;
    private final DataSource dataSource;
    private long[] durations;
    MP3Header firstHeader;
    long maxBitRate;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    TrackMetaData trackMetaData;

    class MP3Header {
        int bitRate;
        int bitRateIndex;
        int channelCount;
        int channelMode;
        int layer;
        int mpegVersion;
        int padding;
        int protectionAbsent;
        int sampleFrequencyIndex;
        int sampleRate;

        MP3Header() {
        }

        int getFrameLength() {
            return ((this.bitRate * 144) / this.sampleRate) + this.padding;
        }
    }

    static {
        int[] iArr = new int[4];
        iArr[0] = 44100;
        iArr[1] = 48000;
        iArr[2] = 32000;
        SAMPLE_RATE = iArr;
        iArr = new int[16];
        iArr[1] = 32000;
        iArr[2] = 40000;
        iArr[3] = 48000;
        iArr[4] = 56000;
        iArr[5] = SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT;
        iArr[6] = 80000;
        iArr[7] = 96000;
        iArr[8] = 112000;
        iArr[9] = 128000;
        iArr[10] = 160000;
        iArr[11] = 192000;
        iArr[12] = 224000;
        iArr[13] = 256000;
        iArr[14] = 320000;
        BIT_RATE = iArr;
    }

    public MP3TrackImpl(DataSource channel) throws IOException {
        this(channel, "eng");
    }

    public void close() throws IOException {
        this.dataSource.close();
    }

    public MP3TrackImpl(DataSource dataSource, String lang) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.dataSource = dataSource;
        this.samples = new LinkedList();
        this.firstHeader = readSamples(dataSource);
        double packetsPerSecond = ((double) this.firstHeader.sampleRate) / 1152.0d;
        double duration = ((double) this.samples.size()) / packetsPerSecond;
        long dataSize = 0;
        LinkedList<Integer> queue = new LinkedList();
        for (Sample sample : this.samples) {
            int size = (int) sample.getSize();
            dataSize += (long) size;
            queue.add(Integer.valueOf(size));
            while (((double) queue.size()) > packetsPerSecond) {
                queue.pop();
            }
            if (queue.size() == ((int) packetsPerSecond)) {
                int currSize = 0;
                Iterator it = queue.iterator();
                while (it.hasNext()) {
                    currSize += ((Integer) it.next()).intValue();
                }
                double currBitRate = ((8.0d * ((double) currSize)) / ((double) queue.size())) * packetsPerSecond;
                if (currBitRate > ((double) this.maxBitRate)) {
                    this.maxBitRate = (long) ((int) currBitRate);
                }
            }
        }
        this.avgBitRate = (long) ((int) (((double) (8 * dataSize)) / duration));
        this.sampleDescriptionBox = new SampleDescriptionBox();
        AudioSampleEntry audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE3);
        audioSampleEntry.setChannelCount(this.firstHeader.channelCount);
        audioSampleEntry.setSampleRate((long) this.firstHeader.sampleRate);
        audioSampleEntry.setDataReferenceIndex(1);
        audioSampleEntry.setSampleSize(16);
        ESDescriptorBox esds = new ESDescriptorBox();
        ESDescriptor descriptor = new ESDescriptor();
        descriptor.setEsId(0);
        SLConfigDescriptor slConfigDescriptor = new SLConfigDescriptor();
        slConfigDescriptor.setPredefined(2);
        descriptor.setSlConfigDescriptor(slConfigDescriptor);
        DecoderConfigDescriptor decoderConfigDescriptor = new DecoderConfigDescriptor();
        decoderConfigDescriptor.setObjectTypeIndication(107);
        decoderConfigDescriptor.setStreamType(5);
        decoderConfigDescriptor.setMaxBitRate(this.maxBitRate);
        decoderConfigDescriptor.setAvgBitRate(this.avgBitRate);
        descriptor.setDecoderConfigDescriptor(decoderConfigDescriptor);
        esds.setData(descriptor.serialize());
        audioSampleEntry.addBox(esds);
        this.sampleDescriptionBox.addBox(audioSampleEntry);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(lang);
        this.trackMetaData.setVolume(1.0f);
        this.trackMetaData.setTimescale((long) this.firstHeader.sampleRate);
        this.durations = new long[this.samples.size()];
        Arrays.fill(this.durations, 1152);
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSampleDurations() {
        return this.durations;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "soun";
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    private MP3Header readSamples(DataSource channel) throws IOException {
        MP3Header first = null;
        while (true) {
            long pos = channel.position();
            MP3Header hdr = readMP3Header(channel);
            if (hdr == null) {
                return first;
            }
            if (first == null) {
                first = hdr;
            }
            channel.position(pos);
            ByteBuffer data = ByteBuffer.allocate(hdr.getFrameLength());
            channel.read(data);
            data.rewind();
            this.samples.add(new SampleImpl(data));
        }
    }

    private MP3Header readMP3Header(DataSource channel) throws IOException {
        MP3Header hdr = new MP3Header();
        ByteBuffer bb = ByteBuffer.allocate(4);
        while (bb.position() < 4) {
            if (channel.read(bb) == -1) {
                return null;
            }
        }
        BitReaderBuffer brb = new BitReaderBuffer((ByteBuffer) bb.rewind());
        if (brb.readBits(11) != 2047) {
            throw new IOException("Expected Start Word 0x7ff");
        }
        hdr.mpegVersion = brb.readBits(2);
        if (hdr.mpegVersion != 3) {
            throw new IOException("Expected MPEG Version 1 (ISO/IEC 11172-3)");
        }
        hdr.layer = brb.readBits(2);
        if (hdr.layer != 1) {
            throw new IOException("Expected Layer III");
        }
        hdr.protectionAbsent = brb.readBits(1);
        hdr.bitRateIndex = brb.readBits(4);
        hdr.bitRate = BIT_RATE[hdr.bitRateIndex];
        if (hdr.bitRate == 0) {
            throw new IOException("Unexpected (free/bad) bit rate");
        }
        hdr.sampleFrequencyIndex = brb.readBits(2);
        hdr.sampleRate = SAMPLE_RATE[hdr.sampleFrequencyIndex];
        if (hdr.sampleRate == 0) {
            throw new IOException("Unexpected (reserved) sample rate frequency");
        }
        hdr.padding = brb.readBits(1);
        brb.readBits(1);
        hdr.channelMode = brb.readBits(2);
        hdr.channelCount = hdr.channelMode == 3 ? 1 : 2;
        return hdr;
    }

    public String toString() {
        return "MP3TrackImpl";
    }
}
