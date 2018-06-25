package com.googlecode.mp4parser.authoring.tracks;

import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.AudioSpecificConfig;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.SLConfigDescriptor;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AACTrackImpl extends AbstractTrack {
    static Map<Integer, String> audioObjectTypes = new HashMap();
    public static Map<Integer, Integer> samplingFrequencyIndexMap = new HashMap();
    long avgBitRate;
    int bufferSizeDB;
    private DataSource dataSource;
    long[] decTimes;
    AdtsHeader firstHeader;
    private String lang;
    long maxBitRate;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    TrackMetaData trackMetaData;

    class AdtsHeader {
        int bufferFullness;
        int channelconfig;
        int copyrightStart;
        int copyrightedStream;
        int frameLength;
        int home;
        int layer;
        int mpegVersion;
        int numAacFramesPerAdtsFrame;
        int original;
        int profile;
        int protectionAbsent;
        int sampleFrequencyIndex;
        int sampleRate;

        AdtsHeader() {
        }

        int getSize() {
            return (this.protectionAbsent == 0 ? 2 : 0) + 7;
        }
    }

    static {
        audioObjectTypes.put(Integer.valueOf(1), "AAC Main");
        audioObjectTypes.put(Integer.valueOf(2), "AAC LC (Low Complexity)");
        audioObjectTypes.put(Integer.valueOf(3), "AAC SSR (Scalable Sample Rate)");
        audioObjectTypes.put(Integer.valueOf(4), "AAC LTP (Long Term Prediction)");
        audioObjectTypes.put(Integer.valueOf(5), "SBR (Spectral Band Replication)");
        audioObjectTypes.put(Integer.valueOf(6), "AAC Scalable");
        audioObjectTypes.put(Integer.valueOf(7), "TwinVQ");
        audioObjectTypes.put(Integer.valueOf(8), "CELP (Code Excited Linear Prediction)");
        audioObjectTypes.put(Integer.valueOf(9), "HXVC (Harmonic Vector eXcitation Coding)");
        audioObjectTypes.put(Integer.valueOf(10), "Reserved");
        audioObjectTypes.put(Integer.valueOf(11), "Reserved");
        audioObjectTypes.put(Integer.valueOf(12), "TTSI (Text-To-Speech Interface)");
        audioObjectTypes.put(Integer.valueOf(13), "Main Synthesis");
        audioObjectTypes.put(Integer.valueOf(14), "Wavetable Synthesis");
        audioObjectTypes.put(Integer.valueOf(15), "General MIDI");
        audioObjectTypes.put(Integer.valueOf(16), "Algorithmic Synthesis and Audio Effects");
        audioObjectTypes.put(Integer.valueOf(17), "ER (Error Resilient) AAC LC");
        audioObjectTypes.put(Integer.valueOf(18), "Reserved");
        audioObjectTypes.put(Integer.valueOf(19), "ER AAC LTP");
        audioObjectTypes.put(Integer.valueOf(20), "ER AAC Scalable");
        audioObjectTypes.put(Integer.valueOf(21), "ER TwinVQ");
        audioObjectTypes.put(Integer.valueOf(22), "ER BSAC (Bit-Sliced Arithmetic Coding)");
        audioObjectTypes.put(Integer.valueOf(23), "ER AAC LD (Low Delay)");
        audioObjectTypes.put(Integer.valueOf(24), "ER CELP");
        audioObjectTypes.put(Integer.valueOf(25), "ER HVXC");
        audioObjectTypes.put(Integer.valueOf(26), "ER HILN (Harmonic and Individual Lines plus Noise)");
        audioObjectTypes.put(Integer.valueOf(27), "ER Parametric");
        audioObjectTypes.put(Integer.valueOf(28), "SSC (SinuSoidal Coding)");
        audioObjectTypes.put(Integer.valueOf(29), "PS (Parametric Stereo)");
        audioObjectTypes.put(Integer.valueOf(30), "MPEG Surround");
        audioObjectTypes.put(Integer.valueOf(31), "(Escape value)");
        audioObjectTypes.put(Integer.valueOf(32), "Layer-1");
        audioObjectTypes.put(Integer.valueOf(33), "Layer-2");
        audioObjectTypes.put(Integer.valueOf(34), "Layer-3");
        audioObjectTypes.put(Integer.valueOf(35), "DST (Direct Stream Transfer)");
        audioObjectTypes.put(Integer.valueOf(36), "ALS (Audio Lossless)");
        audioObjectTypes.put(Integer.valueOf(37), "SLS (Scalable LosslesS)");
        audioObjectTypes.put(Integer.valueOf(38), "SLS non-core");
        audioObjectTypes.put(Integer.valueOf(39), "ER AAC ELD (Enhanced Low Delay)");
        audioObjectTypes.put(Integer.valueOf(40), "SMR (Symbolic Music Representation) Simple");
        audioObjectTypes.put(Integer.valueOf(41), "SMR Main");
        audioObjectTypes.put(Integer.valueOf(42), "USAC (Unified Speech and Audio Coding) (no SBR)");
        audioObjectTypes.put(Integer.valueOf(43), "SAOC (Spatial Audio Object Coding)");
        audioObjectTypes.put(Integer.valueOf(44), "LD MPEG Surround");
        audioObjectTypes.put(Integer.valueOf(45), "USAC");
        samplingFrequencyIndexMap.put(Integer.valueOf(96000), Integer.valueOf(0));
        samplingFrequencyIndexMap.put(Integer.valueOf(88200), Integer.valueOf(1));
        samplingFrequencyIndexMap.put(Integer.valueOf(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT), Integer.valueOf(2));
        samplingFrequencyIndexMap.put(Integer.valueOf(48000), Integer.valueOf(3));
        samplingFrequencyIndexMap.put(Integer.valueOf(44100), Integer.valueOf(4));
        samplingFrequencyIndexMap.put(Integer.valueOf(32000), Integer.valueOf(5));
        samplingFrequencyIndexMap.put(Integer.valueOf(24000), Integer.valueOf(6));
        samplingFrequencyIndexMap.put(Integer.valueOf(22050), Integer.valueOf(7));
        samplingFrequencyIndexMap.put(Integer.valueOf(16000), Integer.valueOf(8));
        samplingFrequencyIndexMap.put(Integer.valueOf(12000), Integer.valueOf(9));
        samplingFrequencyIndexMap.put(Integer.valueOf(11025), Integer.valueOf(10));
        samplingFrequencyIndexMap.put(Integer.valueOf(8000), Integer.valueOf(11));
        samplingFrequencyIndexMap.put(Integer.valueOf(0), Integer.valueOf(96000));
        samplingFrequencyIndexMap.put(Integer.valueOf(1), Integer.valueOf(88200));
        samplingFrequencyIndexMap.put(Integer.valueOf(2), Integer.valueOf(SettingsJsonConstants.SETTINGS_LOG_BUFFER_SIZE_DEFAULT));
        samplingFrequencyIndexMap.put(Integer.valueOf(3), Integer.valueOf(48000));
        samplingFrequencyIndexMap.put(Integer.valueOf(4), Integer.valueOf(44100));
        samplingFrequencyIndexMap.put(Integer.valueOf(5), Integer.valueOf(32000));
        samplingFrequencyIndexMap.put(Integer.valueOf(6), Integer.valueOf(24000));
        samplingFrequencyIndexMap.put(Integer.valueOf(7), Integer.valueOf(22050));
        samplingFrequencyIndexMap.put(Integer.valueOf(8), Integer.valueOf(16000));
        samplingFrequencyIndexMap.put(Integer.valueOf(9), Integer.valueOf(12000));
        samplingFrequencyIndexMap.put(Integer.valueOf(10), Integer.valueOf(11025));
        samplingFrequencyIndexMap.put(Integer.valueOf(11), Integer.valueOf(8000));
    }

    public void close() throws IOException {
        this.dataSource.close();
    }

    public AACTrackImpl(DataSource dataSource) throws IOException {
        this(dataSource, "eng");
    }

    public AACTrackImpl(DataSource dataSource, String lang) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.lang = "eng";
        this.lang = lang;
        this.dataSource = dataSource;
        this.samples = new ArrayList();
        this.firstHeader = readSamples(dataSource);
        double packetsPerSecond = ((double) this.firstHeader.sampleRate) / 1024.0d;
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
                double currBitrate = ((8.0d * ((double) currSize)) / ((double) queue.size())) * packetsPerSecond;
                if (currBitrate > ((double) this.maxBitRate)) {
                    this.maxBitRate = (long) ((int) currBitrate);
                }
            }
        }
        this.avgBitRate = (long) ((int) (((double) (8 * dataSize)) / duration));
        this.bufferSizeDB = 1536;
        this.sampleDescriptionBox = new SampleDescriptionBox();
        AudioSampleEntry audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE3);
        if (this.firstHeader.channelconfig == 7) {
            audioSampleEntry.setChannelCount(8);
        } else {
            audioSampleEntry.setChannelCount(this.firstHeader.channelconfig);
        }
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
        decoderConfigDescriptor.setObjectTypeIndication(64);
        decoderConfigDescriptor.setStreamType(5);
        decoderConfigDescriptor.setBufferSizeDB(this.bufferSizeDB);
        decoderConfigDescriptor.setMaxBitRate(this.maxBitRate);
        decoderConfigDescriptor.setAvgBitRate(this.avgBitRate);
        AudioSpecificConfig audioSpecificConfig = new AudioSpecificConfig();
        audioSpecificConfig.setAudioObjectType(2);
        audioSpecificConfig.setSamplingFrequencyIndex(this.firstHeader.sampleFrequencyIndex);
        audioSpecificConfig.setChannelConfiguration(this.firstHeader.channelconfig);
        decoderConfigDescriptor.setAudioSpecificInfo(audioSpecificConfig);
        descriptor.setDecoderConfigDescriptor(decoderConfigDescriptor);
        ByteBuffer data = descriptor.serialize();
        esds.setEsDescriptor(descriptor);
        esds.setData(data);
        audioSampleEntry.addBox(esds);
        this.sampleDescriptionBox.addBox(audioSampleEntry);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(lang);
        this.trackMetaData.setVolume(1.0f);
        this.trackMetaData.setTimescale((long) this.firstHeader.sampleRate);
        this.decTimes = new long[this.samples.size()];
        Arrays.fill(this.decTimes, PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSampleDurations() {
        return this.decTimes;
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

    public List<Sample> getSamples() {
        return this.samples;
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return null;
    }

    private AdtsHeader readADTSHeader(DataSource channel) throws IOException {
        AdtsHeader hdr = new AdtsHeader();
        ByteBuffer bb = ByteBuffer.allocate(7);
        while (bb.position() < 7) {
            if (channel.read(bb) == -1) {
                return null;
            }
        }
        BitReaderBuffer brb = new BitReaderBuffer((ByteBuffer) bb.rewind());
        if (brb.readBits(12) != 4095) {
            throw new IOException("Expected Start Word 0xfff");
        }
        hdr.mpegVersion = brb.readBits(1);
        hdr.layer = brb.readBits(2);
        hdr.protectionAbsent = brb.readBits(1);
        hdr.profile = brb.readBits(2) + 1;
        hdr.sampleFrequencyIndex = brb.readBits(4);
        hdr.sampleRate = ((Integer) samplingFrequencyIndexMap.get(Integer.valueOf(hdr.sampleFrequencyIndex))).intValue();
        brb.readBits(1);
        hdr.channelconfig = brb.readBits(3);
        hdr.original = brb.readBits(1);
        hdr.home = brb.readBits(1);
        hdr.copyrightedStream = brb.readBits(1);
        hdr.copyrightStart = brb.readBits(1);
        hdr.frameLength = brb.readBits(13);
        hdr.bufferFullness = brb.readBits(11);
        hdr.numAacFramesPerAdtsFrame = brb.readBits(2) + 1;
        if (hdr.numAacFramesPerAdtsFrame != 1) {
            throw new IOException("This muxer can only work with 1 AAC frame per ADTS frame");
        } else if (hdr.protectionAbsent != 0) {
            return hdr;
        } else {
            channel.read(ByteBuffer.allocate(2));
            return hdr;
        }
    }

    private AdtsHeader readSamples(DataSource channel) throws IOException {
        AdtsHeader first = null;
        while (true) {
            AdtsHeader hdr = readADTSHeader(channel);
            if (hdr == null) {
                return first;
            }
            if (first == null) {
                first = hdr;
            }
            final long currentPosition = channel.position();
            final long frameSize = (long) (hdr.frameLength - hdr.getSize());
            this.samples.add(new Sample() {
                public void writeTo(WritableByteChannel channel) throws IOException {
                    AACTrackImpl.this.dataSource.transferTo(currentPosition, frameSize, channel);
                }

                public long getSize() {
                    return frameSize;
                }

                public ByteBuffer asByteBuffer() {
                    try {
                        return AACTrackImpl.this.dataSource.map(currentPosition, frameSize);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            channel.position((channel.position() + ((long) hdr.frameLength)) - ((long) hdr.getSize()));
        }
    }

    public String toString() {
        return "AACTrackImpl{sampleRate=" + this.firstHeader.sampleRate + ", channelconfig=" + this.firstHeader.channelconfig + '}';
    }
}
