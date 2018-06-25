package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.coremedia.iso.boxes.AbstractMediaHeaderBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SoundMediaHeaderBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.google.android.gms.common.Scopes;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.AudioSpecificConfig;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.SLConfigDescriptor;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C0907C;

public class Track {
    private static Map<Integer, Integer> samplingFrequencyIndexMap = new HashMap();
    private Date creationTime = new Date();
    private long duration = 0;
    private boolean first = true;
    private String handler;
    private AbstractMediaHeaderBox headerBox = null;
    private int height;
    private boolean isAudio = false;
    private int[] sampleCompositions;
    private SampleDescriptionBox sampleDescriptionBox = null;
    private long[] sampleDurations;
    private ArrayList<SamplePresentationTime> samplePresentationTimes = new ArrayList();
    private ArrayList<Sample> samples = new ArrayList();
    private LinkedList<Integer> syncSamples = null;
    private int timeScale;
    private long trackId = 0;
    private float volume = 0.0f;
    private int width;

    /* renamed from: org.telegram.messenger.video.Track$1 */
    class C19001 implements Comparator<SamplePresentationTime> {
        C19001() {
        }

        public int compare(SamplePresentationTime o1, SamplePresentationTime o2) {
            if (o1.presentationTime > o2.presentationTime) {
                return 1;
            }
            if (o1.presentationTime < o2.presentationTime) {
                return -1;
            }
            return 0;
        }
    }

    private class SamplePresentationTime {
        private long dt;
        private int index;
        private long presentationTime;

        public SamplePresentationTime(int idx, long time) {
            this.index = idx;
            this.presentationTime = time;
        }
    }

    static {
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
    }

    public Track(int id, MediaFormat format, boolean audio) {
        this.trackId = (long) id;
        this.isAudio = audio;
        if (this.isAudio) {
            this.volume = 1.0f;
            this.timeScale = format.getInteger("sample-rate");
            this.handler = "soun";
            this.headerBox = new SoundMediaHeaderBox();
            this.sampleDescriptionBox = new SampleDescriptionBox();
            AudioSampleEntry audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE3);
            audioSampleEntry.setChannelCount(format.getInteger("channel-count"));
            audioSampleEntry.setSampleRate((long) format.getInteger("sample-rate"));
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
            decoderConfigDescriptor.setBufferSizeDB(1536);
            if (format.containsKey("max-bitrate")) {
                decoderConfigDescriptor.setMaxBitRate((long) format.getInteger("max-bitrate"));
            } else {
                decoderConfigDescriptor.setMaxBitRate(96000);
            }
            decoderConfigDescriptor.setAvgBitRate((long) this.timeScale);
            AudioSpecificConfig audioSpecificConfig = new AudioSpecificConfig();
            audioSpecificConfig.setAudioObjectType(2);
            audioSpecificConfig.setSamplingFrequencyIndex(((Integer) samplingFrequencyIndexMap.get(Integer.valueOf((int) audioSampleEntry.getSampleRate()))).intValue());
            audioSpecificConfig.setChannelConfiguration(audioSampleEntry.getChannelCount());
            decoderConfigDescriptor.setAudioSpecificInfo(audioSpecificConfig);
            descriptor.setDecoderConfigDescriptor(decoderConfigDescriptor);
            ByteBuffer data = descriptor.serialize();
            esds.setEsDescriptor(descriptor);
            esds.setData(data);
            audioSampleEntry.addBox(esds);
            this.sampleDescriptionBox.addBox(audioSampleEntry);
            return;
        }
        this.width = format.getInteger(SettingsJsonConstants.ICON_WIDTH_KEY);
        this.height = format.getInteger("height");
        this.timeScale = 90000;
        this.syncSamples = new LinkedList();
        this.handler = "vide";
        this.headerBox = new VideoMediaHeaderBox();
        this.sampleDescriptionBox = new SampleDescriptionBox();
        String mime = format.getString("mime");
        VisualSampleEntry visualSampleEntry;
        if (mime.equals("video/avc")) {
            visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE3);
            visualSampleEntry.setDataReferenceIndex(1);
            visualSampleEntry.setDepth(24);
            visualSampleEntry.setFrameCount(1);
            visualSampleEntry.setHorizresolution(72.0d);
            visualSampleEntry.setVertresolution(72.0d);
            visualSampleEntry.setWidth(this.width);
            visualSampleEntry.setHeight(this.height);
            AvcConfigurationBox avcConfigurationBox = new AvcConfigurationBox();
            if (format.getByteBuffer("csd-0") != null) {
                ArrayList<byte[]> spsArray = new ArrayList();
                ByteBuffer spsBuff = format.getByteBuffer("csd-0");
                spsBuff.position(4);
                Object spsBytes = new byte[spsBuff.remaining()];
                spsBuff.get(spsBytes);
                spsArray.add(spsBytes);
                ArrayList<byte[]> ppsArray = new ArrayList();
                ByteBuffer ppsBuff = format.getByteBuffer("csd-1");
                ppsBuff.position(4);
                byte[] ppsBytes = new byte[ppsBuff.remaining()];
                ppsBuff.get(ppsBytes);
                ppsArray.add(ppsBytes);
                avcConfigurationBox.setSequenceParameterSets(spsArray);
                avcConfigurationBox.setPictureParameterSets(ppsArray);
            }
            if (format.containsKey(Param.LEVEL)) {
                int level = format.getInteger(Param.LEVEL);
                if (level == 1) {
                    avcConfigurationBox.setAvcLevelIndication(1);
                } else if (level == 32) {
                    avcConfigurationBox.setAvcLevelIndication(2);
                } else if (level == 4) {
                    avcConfigurationBox.setAvcLevelIndication(11);
                } else if (level == 8) {
                    avcConfigurationBox.setAvcLevelIndication(12);
                } else if (level == 16) {
                    avcConfigurationBox.setAvcLevelIndication(13);
                } else if (level == 64) {
                    avcConfigurationBox.setAvcLevelIndication(21);
                } else if (level == 128) {
                    avcConfigurationBox.setAvcLevelIndication(22);
                } else if (level == 256) {
                    avcConfigurationBox.setAvcLevelIndication(3);
                } else if (level == 512) {
                    avcConfigurationBox.setAvcLevelIndication(31);
                } else if (level == 1024) {
                    avcConfigurationBox.setAvcLevelIndication(32);
                } else if (level == 2048) {
                    avcConfigurationBox.setAvcLevelIndication(4);
                } else if (level == 4096) {
                    avcConfigurationBox.setAvcLevelIndication(41);
                } else if (level == 8192) {
                    avcConfigurationBox.setAvcLevelIndication(42);
                } else if (level == 16384) {
                    avcConfigurationBox.setAvcLevelIndication(5);
                } else if (level == 32768) {
                    avcConfigurationBox.setAvcLevelIndication(51);
                } else if (level == 65536) {
                    avcConfigurationBox.setAvcLevelIndication(52);
                } else if (level == 2) {
                    avcConfigurationBox.setAvcLevelIndication(27);
                }
            } else {
                avcConfigurationBox.setAvcLevelIndication(13);
            }
            if (format.containsKey(Scopes.PROFILE)) {
                int profile = format.getInteger(Scopes.PROFILE);
                if (profile == 1) {
                    avcConfigurationBox.setAvcProfileIndication(66);
                } else if (profile == 2) {
                    avcConfigurationBox.setAvcProfileIndication(77);
                } else if (profile == 4) {
                    avcConfigurationBox.setAvcProfileIndication(88);
                } else if (profile == 8) {
                    avcConfigurationBox.setAvcProfileIndication(100);
                } else if (profile == 16) {
                    avcConfigurationBox.setAvcProfileIndication(110);
                } else if (profile == 32) {
                    avcConfigurationBox.setAvcProfileIndication(122);
                } else if (profile == 64) {
                    avcConfigurationBox.setAvcProfileIndication(244);
                }
            } else {
                avcConfigurationBox.setAvcProfileIndication(100);
            }
            avcConfigurationBox.setBitDepthLumaMinus8(-1);
            avcConfigurationBox.setBitDepthChromaMinus8(-1);
            avcConfigurationBox.setChromaFormat(-1);
            avcConfigurationBox.setConfigurationVersion(1);
            avcConfigurationBox.setLengthSizeMinusOne(3);
            avcConfigurationBox.setProfileCompatibility(0);
            visualSampleEntry.addBox(avcConfigurationBox);
            this.sampleDescriptionBox.addBox(visualSampleEntry);
        } else if (mime.equals("video/mp4v")) {
            visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE1);
            visualSampleEntry.setDataReferenceIndex(1);
            visualSampleEntry.setDepth(24);
            visualSampleEntry.setFrameCount(1);
            visualSampleEntry.setHorizresolution(72.0d);
            visualSampleEntry.setVertresolution(72.0d);
            visualSampleEntry.setWidth(this.width);
            visualSampleEntry.setHeight(this.height);
            this.sampleDescriptionBox.addBox(visualSampleEntry);
        }
    }

    public long getTrackId() {
        return this.trackId;
    }

    public void addSample(long offset, BufferInfo bufferInfo) {
        boolean isSyncFrame = (this.isAudio || (bufferInfo.flags & 1) == 0) ? false : true;
        this.samples.add(new Sample(offset, (long) bufferInfo.size));
        if (this.syncSamples != null && isSyncFrame) {
            this.syncSamples.add(Integer.valueOf(this.samples.size()));
        }
        this.samplePresentationTimes.add(new SamplePresentationTime(this.samplePresentationTimes.size(), ((bufferInfo.presentationTimeUs * ((long) this.timeScale)) + 500000) / C0907C.MICROS_PER_SECOND));
    }

    public void prepare() {
        int a;
        ArrayList<SamplePresentationTime> original = new ArrayList(this.samplePresentationTimes);
        Collections.sort(this.samplePresentationTimes, new C19001());
        long lastPresentationTimeUs = 0;
        this.sampleDurations = new long[this.samplePresentationTimes.size()];
        long minDelta = Long.MAX_VALUE;
        boolean outOfOrder = false;
        for (a = 0; a < this.samplePresentationTimes.size(); a++) {
            SamplePresentationTime presentationTime = (SamplePresentationTime) this.samplePresentationTimes.get(a);
            long delta = presentationTime.presentationTime - lastPresentationTimeUs;
            lastPresentationTimeUs = presentationTime.presentationTime;
            this.sampleDurations[presentationTime.index] = delta;
            if (presentationTime.index != 0) {
                this.duration += delta;
            }
            if (delta != 0) {
                minDelta = Math.min(minDelta, delta);
            }
            if (presentationTime.index != a) {
                outOfOrder = true;
            }
        }
        if (this.sampleDurations.length > 0) {
            this.sampleDurations[0] = minDelta;
            this.duration += minDelta;
        }
        for (a = 1; a < original.size(); a++) {
            ((SamplePresentationTime) original.get(a)).dt = this.sampleDurations[a] + ((SamplePresentationTime) original.get(a - 1)).dt;
        }
        if (outOfOrder) {
            this.sampleCompositions = new int[this.samplePresentationTimes.size()];
            for (a = 0; a < this.samplePresentationTimes.size(); a++) {
                presentationTime = (SamplePresentationTime) this.samplePresentationTimes.get(a);
                this.sampleCompositions[presentationTime.index] = (int) (presentationTime.presentationTime - presentationTime.dt);
            }
        }
    }

    public ArrayList<Sample> getSamples() {
        return this.samples;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getHandler() {
        return this.handler;
    }

    public AbstractMediaHeaderBox getMediaHeaderBox() {
        return this.headerBox;
    }

    public int[] getSampleCompositions() {
        return this.sampleCompositions;
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSyncSamples() {
        if (this.syncSamples == null || this.syncSamples.isEmpty()) {
            return null;
        }
        long[] returns = new long[this.syncSamples.size()];
        for (int i = 0; i < this.syncSamples.size(); i++) {
            returns[i] = (long) ((Integer) this.syncSamples.get(i)).intValue();
        }
        return returns;
    }

    public int getTimeScale() {
        return this.timeScale;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getVolume() {
        return this.volume;
    }

    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    public boolean isAudio() {
        return this.isAudio;
    }
}
