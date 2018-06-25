package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReaderVariable;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.OriginalFormatBox;
import com.coremedia.iso.boxes.ProtectionSchemeInformationBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SchemeInformationBox;
import com.coremedia.iso.boxes.SchemeTypeBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.MemoryDataSourceImpl;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.cenc.CencEncryptingSampleList;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.CencSampleEncryptionInformationGroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.RangeStartMap;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import com.mp4parser.iso14496.part15.HevcConfigurationBox;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat.Pair;
import com.mp4parser.iso23001.part7.TrackEncryptionBox;
import com.thin.downloadmanager.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.telegram.messenger.exoplayer2.C0907C;

public class CencEncryptingTrackImpl implements CencEncryptedTrack {
    List<CencSampleAuxiliaryDataFormat> cencSampleAuxiliaryData;
    UUID defaultKeyId;
    boolean dummyIvs;
    private final String encryptionAlgo;
    RangeStartMap<Integer, SecretKey> indexToKey;
    Map<UUID, SecretKey> keys;
    Map<GroupEntry, long[]> sampleGroups;
    List<Sample> samples;
    Track source;
    SampleDescriptionBox stsd;
    boolean subSampleEncryption;

    public CencEncryptingTrackImpl(Track source, UUID defaultKeyId, SecretKey key, boolean dummyIvs) {
        this(source, defaultKeyId, Collections.singletonMap(defaultKeyId, key), null, C0907C.CENC_TYPE_cenc, dummyIvs);
    }

    public CencEncryptingTrackImpl(Track source, UUID defaultKeyId, Map<UUID, SecretKey> keys, Map<CencSampleEncryptionInformationGroupEntry, long[]> keyRotation, String encryptionAlgo, boolean dummyIvs) {
        this(source, defaultKeyId, keys, keyRotation, encryptionAlgo, dummyIvs, false);
    }

    public CencEncryptingTrackImpl(Track source, UUID defaultKeyId, Map<UUID, SecretKey> keys, Map<CencSampleEncryptionInformationGroupEntry, long[]> keyRotation, String encryptionAlgo, boolean dummyIvs, boolean encryptButAllClear) {
        int i;
        this.keys = new HashMap();
        this.dummyIvs = false;
        this.subSampleEncryption = false;
        this.stsd = null;
        this.source = source;
        this.keys = keys;
        this.defaultKeyId = defaultKeyId;
        this.dummyIvs = dummyIvs;
        this.encryptionAlgo = encryptionAlgo;
        this.sampleGroups = new HashMap();
        for (Entry<GroupEntry, long[]> entry : source.getSampleGroups().entrySet()) {
            if (!(entry.getKey() instanceof CencSampleEncryptionInformationGroupEntry)) {
                this.sampleGroups.put((GroupEntry) entry.getKey(), (long[]) entry.getValue());
            }
        }
        if (keyRotation != null) {
            for (Entry<CencSampleEncryptionInformationGroupEntry, long[]> entry2 : keyRotation.entrySet()) {
                this.sampleGroups.put((GroupEntry) entry2.getKey(), (long[]) entry2.getValue());
            }
        }
        this.sampleGroups = new HashMap<GroupEntry, long[]>(this.sampleGroups) {
            public long[] put(GroupEntry key, long[] value) {
                if (!(key instanceof CencSampleEncryptionInformationGroupEntry)) {
                    return (long[]) super.put(key, value);
                }
                throw new RuntimeException("Please supply CencSampleEncryptionInformationGroupEntries in the constructor");
            }
        };
        this.samples = source.getSamples();
        this.cencSampleAuxiliaryData = new ArrayList();
        BigInteger bigInteger = new BigInteger(BuildConfig.VERSION_NAME);
        byte[] init = new byte[8];
        if (!dummyIvs) {
            new SecureRandom().nextBytes(init);
        }
        bigInteger = new BigInteger(1, init);
        List<CencSampleEncryptionInformationGroupEntry> groupEntries = new ArrayList();
        if (keyRotation != null) {
            groupEntries.addAll(keyRotation.keySet());
        }
        this.indexToKey = new RangeStartMap();
        int lastSampleGroupDescriptionIndex = -1;
        for (i = 0; i < source.getSamples().size(); i++) {
            int index = 0;
            for (int j = 0; j < groupEntries.size(); j++) {
                if (Arrays.binarySearch((long[]) getSampleGroups().get((GroupEntry) groupEntries.get(j)), (long) i) >= 0) {
                    index = j + 1;
                }
            }
            if (lastSampleGroupDescriptionIndex != index) {
                if (index == 0) {
                    this.indexToKey.put((Comparable) Integer.valueOf(i), (Object) (SecretKey) keys.get(defaultKeyId));
                } else if (((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).getKid() != null) {
                    SecretKey sk = (SecretKey) keys.get(((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).getKid());
                    if (sk == null) {
                        throw new RuntimeException("Key " + ((CencSampleEncryptionInformationGroupEntry) groupEntries.get(index - 1)).getKid() + " was not supplied for decryption");
                    }
                    this.indexToKey.put((Comparable) Integer.valueOf(i), (Object) sk);
                } else {
                    this.indexToKey.put(Integer.valueOf(i), null);
                }
                lastSampleGroupDescriptionIndex = index;
            }
        }
        int nalLengthSize = -1;
        for (Box box : source.getSampleDescriptionBox().getSampleEntry().getBoxes()) {
            if (box instanceof AvcConfigurationBox) {
                AvcConfigurationBox avcC = (AvcConfigurationBox) box;
                this.subSampleEncryption = true;
                nalLengthSize = avcC.getLengthSizeMinusOne() + 1;
            }
            if (box instanceof HevcConfigurationBox) {
                HevcConfigurationBox hvcC = (HevcConfigurationBox) box;
                this.subSampleEncryption = true;
                nalLengthSize = hvcC.getLengthSizeMinusOne() + 1;
            }
        }
        for (i = 0; i < this.samples.size(); i++) {
            Sample origSample = (Sample) this.samples.get(i);
            CencSampleAuxiliaryDataFormat e = new CencSampleAuxiliaryDataFormat();
            this.cencSampleAuxiliaryData.add(e);
            if (this.indexToKey.get(Integer.valueOf(i)) != null) {
                int i2;
                Object iv = ivInt.toByteArray();
                byte[] eightByteIv = new byte[8];
                int length = iv.length + -8 > 0 ? iv.length - 8 : 0;
                int length2 = 8 - iv.length < 0 ? 0 : 8 - iv.length;
                if (iv.length > 8) {
                    i2 = 8;
                } else {
                    i2 = iv.length;
                }
                System.arraycopy(iv, length, eightByteIv, length2, i2);
                e.iv = eightByteIv;
                ByteBuffer sample = (ByteBuffer) origSample.asByteBuffer().rewind();
                if (this.subSampleEncryption) {
                    if (encryptButAllClear) {
                        e.pairs = new Pair[]{e.createPair(sample.remaining(), 0)};
                    } else {
                        List<Pair> arrayList = new ArrayList(5);
                        while (sample.remaining() > 0) {
                            int clearBytes;
                            int nalLength = CastUtils.l2i(IsoTypeReaderVariable.read(sample, nalLengthSize));
                            int nalGrossSize = nalLength + nalLengthSize;
                            if (nalGrossSize >= 112) {
                                clearBytes = (nalGrossSize % 16) + 96;
                            } else {
                                clearBytes = nalGrossSize;
                            }
                            arrayList.add(e.createPair(clearBytes, (long) (nalGrossSize - clearBytes)));
                            sample.position(sample.position() + nalLength);
                        }
                        e.pairs = (Pair[]) arrayList.toArray(new Pair[arrayList.size()]);
                    }
                }
                BigInteger ivInt = ivInt.add(bigInteger);
            }
        }
        System.err.println("");
    }

    public UUID getDefaultKeyId() {
        return this.defaultKeyId;
    }

    public boolean hasSubSampleEncryption() {
        return this.subSampleEncryption;
    }

    public List<CencSampleAuxiliaryDataFormat> getSampleEncryptionEntries() {
        return this.cencSampleAuxiliaryData;
    }

    public synchronized SampleDescriptionBox getSampleDescriptionBox() {
        if (this.stsd == null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                this.source.getSampleDescriptionBox().getBox(Channels.newChannel(baos));
                this.stsd = (SampleDescriptionBox) new IsoFile(new MemoryDataSourceImpl(baos.toByteArray())).getBoxes().get(0);
                OriginalFormatBox originalFormatBox = new OriginalFormatBox();
                originalFormatBox.setDataFormat(this.stsd.getSampleEntry().getType());
                if (this.stsd.getSampleEntry() instanceof AudioSampleEntry) {
                    ((AudioSampleEntry) this.stsd.getSampleEntry()).setType(AudioSampleEntry.TYPE_ENCRYPTED);
                } else if (this.stsd.getSampleEntry() instanceof VisualSampleEntry) {
                    ((VisualSampleEntry) this.stsd.getSampleEntry()).setType(VisualSampleEntry.TYPE_ENCRYPTED);
                } else {
                    throw new RuntimeException("I don't know how to cenc " + this.stsd.getSampleEntry().getType());
                }
                ProtectionSchemeInformationBox sinf = new ProtectionSchemeInformationBox();
                sinf.addBox(originalFormatBox);
                SchemeTypeBox schm = new SchemeTypeBox();
                schm.setSchemeType(this.encryptionAlgo);
                schm.setSchemeVersion(65536);
                sinf.addBox(schm);
                SchemeInformationBox schi = new SchemeInformationBox();
                TrackEncryptionBox trackEncryptionBox = new TrackEncryptionBox();
                trackEncryptionBox.setDefaultIvSize(this.defaultKeyId == null ? 0 : 8);
                trackEncryptionBox.setDefaultAlgorithmId(this.defaultKeyId == null ? 0 : 1);
                trackEncryptionBox.setDefault_KID(this.defaultKeyId == null ? new UUID(0, 0) : this.defaultKeyId);
                schi.addBox(trackEncryptionBox);
                sinf.addBox(schi);
                this.stsd.getSampleEntry().addBox(sinf);
            } catch (IOException e) {
                throw new RuntimeException("Dumping stsd to memory failed");
            }
        }
        return this.stsd;
    }

    public long[] getSampleDurations() {
        return this.source.getSampleDurations();
    }

    public long getDuration() {
        return this.source.getDuration();
    }

    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return this.source.getCompositionTimeEntries();
    }

    public long[] getSyncSamples() {
        return this.source.getSyncSamples();
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.source.getSampleDependencies();
    }

    public TrackMetaData getTrackMetaData() {
        return this.source.getTrackMetaData();
    }

    public String getHandler() {
        return this.source.getHandler();
    }

    public List<Sample> getSamples() {
        return new CencEncryptingSampleList(this.indexToKey, this.source.getSamples(), this.cencSampleAuxiliaryData, this.encryptionAlgo);
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.source.getSubsampleInformationBox();
    }

    public void close() throws IOException {
        this.source.close();
    }

    public String getName() {
        return "enc(" + this.source.getName() + ")";
    }

    public List<Edit> getEdits() {
        return this.source.getEdits();
    }

    public Map<GroupEntry, long[]> getSampleGroups() {
        return this.sampleGroups;
    }
}
