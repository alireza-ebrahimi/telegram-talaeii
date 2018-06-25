package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;
import org.telegram.messenger.exoplayer2.extractor.ChunkIndex;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.SeekMap.Unseekable;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.text.cea.CeaUtil;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;
import org.telegram.messenger.exoplayer2.util.Util;

public final class FragmentedMp4Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = new C34831();
    public static final int FLAG_ENABLE_CEA608_TRACK = 8;
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    private static final int FLAG_SIDELOADED = 16;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 32;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = new byte[]{(byte) -94, (byte) 57, (byte) 79, (byte) 82, (byte) 90, (byte) -101, (byte) 79, (byte) 20, (byte) -94, (byte) 68, (byte) 108, (byte) 66, (byte) 124, (byte) 100, (byte) -115, (byte) -12};
    private static final int SAMPLE_GROUP_TYPE_seig = Util.getIntegerCodeForString("seig");
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_ENCRYPTION_DATA = 2;
    private static final int STATE_READING_SAMPLE_CONTINUE = 4;
    private static final int STATE_READING_SAMPLE_START = 3;
    private static final String TAG = "FragmentedMp4Extractor";
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private TrackOutput[] cea608TrackOutputs;
    private final Stack<ContainerAtom> containerAtoms;
    private TrackBundle currentTrackBundle;
    private final ParsableByteArray defaultInitializationVector;
    private long durationUs;
    private final ParsableByteArray encryptionSignalByte;
    private long endOfMdatPosition;
    private TrackOutput eventMessageTrackOutput;
    private final byte[] extendedTypeScratch;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private boolean haveOutputSeekMap;
    private final ParsableByteArray nalBuffer;
    private final ParsableByteArray nalPrefix;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int pendingMetadataSampleBytes;
    private final LinkedList<MetadataSampleInfo> pendingMetadataSampleInfos;
    private boolean processSeiNalUnitPayload;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleSize;
    private long segmentIndexEarliestPresentationTimeUs;
    private final Track sideloadedTrack;
    private final TimestampAdjuster timestampAdjuster;
    private final SparseArray<TrackBundle> trackBundles;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor$1 */
    static class C34831 implements ExtractorsFactory {
        C34831() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new FragmentedMp4Extractor()};
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class MetadataSampleInfo {
        public final long presentationTimeDeltaUs;
        public final int size;

        public MetadataSampleInfo(long j, int i) {
            this.presentationTimeDeltaUs = j;
            this.size = i;
        }
    }

    private static final class TrackBundle {
        public int currentSampleInTrackRun;
        public int currentSampleIndex;
        public int currentTrackRunIndex;
        public DefaultSampleValues defaultSampleValues;
        public final TrackFragment fragment = new TrackFragment();
        public final TrackOutput output;
        public Track track;

        public TrackBundle(TrackOutput trackOutput) {
            this.output = trackOutput;
        }

        public void init(Track track, DefaultSampleValues defaultSampleValues) {
            this.track = (Track) Assertions.checkNotNull(track);
            this.defaultSampleValues = (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValues);
            this.output.format(track.format);
            reset();
        }

        public void reset() {
            this.fragment.reset();
            this.currentSampleIndex = 0;
            this.currentTrackRunIndex = 0;
            this.currentSampleInTrackRun = 0;
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.track.getSampleDescriptionEncryptionBox(this.fragment.header.sampleDescriptionIndex);
            this.output.format(this.track.format.copyWithDrmInitData(drmInitData.copyWithSchemeType(sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null)));
        }
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int i) {
        this(i, null);
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster) {
        this(i, timestampAdjuster, null);
    }

    public FragmentedMp4Extractor(int i, TimestampAdjuster timestampAdjuster, Track track) {
        this.flags = (track != null ? 16 : 0) | i;
        this.timestampAdjuster = timestampAdjuster;
        this.sideloadedTrack = track;
        this.atomHeader = new ParsableByteArray(16);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalPrefix = new ParsableByteArray(5);
        this.nalBuffer = new ParsableByteArray();
        this.encryptionSignalByte = new ParsableByteArray(1);
        this.defaultInitializationVector = new ParsableByteArray();
        this.extendedTypeScratch = new byte[16];
        this.containerAtoms = new Stack();
        this.pendingMetadataSampleInfos = new LinkedList();
        this.trackBundles = new SparseArray();
        this.durationUs = C3446C.TIME_UNSET;
        this.segmentIndexEarliestPresentationTimeUs = C3446C.TIME_UNSET;
        enterReadingAtomHeaderState();
    }

    private int appendSampleEncryptionData(TrackBundle trackBundle) {
        ParsableByteArray parsableByteArray;
        int i;
        TrackFragment trackFragment = trackBundle.fragment;
        TrackEncryptionBox sampleDescriptionEncryptionBox = trackFragment.trackEncryptionBox != null ? trackFragment.trackEncryptionBox : trackBundle.track.getSampleDescriptionEncryptionBox(trackFragment.header.sampleDescriptionIndex);
        if (sampleDescriptionEncryptionBox.initializationVectorSize != 0) {
            parsableByteArray = trackFragment.sampleEncryptionData;
            i = sampleDescriptionEncryptionBox.initializationVectorSize;
        } else {
            byte[] bArr = sampleDescriptionEncryptionBox.defaultInitializationVector;
            this.defaultInitializationVector.reset(bArr, bArr.length);
            parsableByteArray = this.defaultInitializationVector;
            i = bArr.length;
        }
        boolean z = trackFragment.sampleHasSubsampleEncryptionTable[trackBundle.currentSampleIndex];
        this.encryptionSignalByte.data[0] = (byte) ((z ? 128 : 0) | i);
        this.encryptionSignalByte.setPosition(0);
        TrackOutput trackOutput = trackBundle.output;
        trackOutput.sampleData(this.encryptionSignalByte, 1);
        trackOutput.sampleData(parsableByteArray, i);
        if (!z) {
            return i + 1;
        }
        parsableByteArray = trackFragment.sampleEncryptionData;
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(-2);
        readUnsignedShort = (readUnsignedShort * 6) + 2;
        trackOutput.sampleData(parsableByteArray, readUnsignedShort);
        return (i + 1) + readUnsignedShort;
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private static DrmInitData getDrmInitDataFromAtoms(List<LeafAtom> list) {
        int size = list.size();
        List list2 = null;
        for (int i = 0; i < size; i++) {
            LeafAtom leafAtom = (LeafAtom) list.get(i);
            if (leafAtom.type == Atom.TYPE_pssh) {
                if (list2 == null) {
                    list2 = new ArrayList();
                }
                byte[] bArr = leafAtom.data.data;
                UUID parseUuid = PsshAtomUtil.parseUuid(bArr);
                if (parseUuid == null) {
                    Log.w(TAG, "Skipped pssh atom (failed to extract uuid)");
                } else {
                    list2.add(new SchemeData(parseUuid, null, MimeTypes.VIDEO_MP4, bArr));
                }
            }
        }
        return list2 == null ? null : new DrmInitData(list2);
    }

    private static TrackBundle getNextFragmentRun(SparseArray<TrackBundle> sparseArray) {
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        int size = sparseArray.size();
        int i = 0;
        while (i < size) {
            TrackBundle trackBundle2;
            long j2;
            TrackBundle trackBundle3 = (TrackBundle) sparseArray.valueAt(i);
            long j3;
            if (trackBundle3.currentTrackRunIndex == trackBundle3.fragment.trunCount) {
                j3 = j;
                trackBundle2 = trackBundle;
                j2 = j3;
            } else {
                long j4 = trackBundle3.fragment.trunDataPosition[trackBundle3.currentTrackRunIndex];
                if (j4 < j) {
                    trackBundle2 = trackBundle3;
                    j2 = j4;
                } else {
                    j3 = j;
                    trackBundle2 = trackBundle;
                    j2 = j3;
                }
            }
            i++;
            trackBundle = trackBundle2;
            j = j2;
        }
        return trackBundle;
    }

    private void maybeInitExtraTracks() {
        if ((this.flags & 4) != 0 && this.eventMessageTrackOutput == null) {
            this.eventMessageTrackOutput = this.extractorOutput.track(this.trackBundles.size(), 4);
            this.eventMessageTrackOutput.format(Format.createSampleFormat(null, MimeTypes.APPLICATION_EMSG, Long.MAX_VALUE));
        }
        if ((this.flags & 8) != 0 && this.cea608TrackOutputs == null) {
            this.extractorOutput.track(this.trackBundles.size() + 1, 3).format(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
            this.cea608TrackOutputs = new TrackOutput[]{r0};
        }
    }

    private void onContainerAtomRead(ContainerAtom containerAtom) {
        if (containerAtom.type == Atom.TYPE_moov) {
            onMoovContainerAtomRead(containerAtom);
        } else if (containerAtom.type == Atom.TYPE_moof) {
            onMoofContainerAtomRead(containerAtom);
        } else if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(containerAtom);
        }
    }

    private void onEmsgLeafAtomRead(ParsableByteArray parsableByteArray) {
        if (this.eventMessageTrackOutput != null) {
            parsableByteArray.setPosition(12);
            parsableByteArray.readNullTerminatedString();
            parsableByteArray.readNullTerminatedString();
            long scaleLargeTimestamp = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), C3446C.MICROS_PER_SECOND, parsableByteArray.readUnsignedInt());
            parsableByteArray.setPosition(12);
            int bytesLeft = parsableByteArray.bytesLeft();
            this.eventMessageTrackOutput.sampleData(parsableByteArray, bytesLeft);
            if (this.segmentIndexEarliestPresentationTimeUs != C3446C.TIME_UNSET) {
                this.eventMessageTrackOutput.sampleMetadata(scaleLargeTimestamp + this.segmentIndexEarliestPresentationTimeUs, 1, bytesLeft, 0, null);
                return;
            }
            this.pendingMetadataSampleInfos.addLast(new MetadataSampleInfo(scaleLargeTimestamp, bytesLeft));
            this.pendingMetadataSampleBytes += bytesLeft;
        }
    }

    private void onLeafAtomRead(LeafAtom leafAtom, long j) {
        if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(leafAtom);
        } else if (leafAtom.type == Atom.TYPE_sidx) {
            Pair parseSidx = parseSidx(leafAtom.data, j);
            this.segmentIndexEarliestPresentationTimeUs = ((Long) parseSidx.first).longValue();
            this.extractorOutput.seekMap((SeekMap) parseSidx.second);
            this.haveOutputSeekMap = true;
        } else if (leafAtom.type == Atom.TYPE_emsg) {
            onEmsgLeafAtomRead(leafAtom.data);
        }
    }

    private void onMoofContainerAtomRead(ContainerAtom containerAtom) {
        parseMoof(containerAtom, this.trackBundles, this.flags, this.extendedTypeScratch);
        DrmInitData drmInitDataFromAtoms = getDrmInitDataFromAtoms(containerAtom.leafChildren);
        if (drmInitDataFromAtoms != null) {
            int size = this.trackBundles.size();
            for (int i = 0; i < size; i++) {
                ((TrackBundle) this.trackBundles.valueAt(i)).updateDrmInitData(drmInitDataFromAtoms);
            }
        }
    }

    private void onMoovContainerAtomRead(ContainerAtom containerAtom) {
        boolean z = true;
        int i = 0;
        Assertions.checkState(this.sideloadedTrack == null, "Unexpected moov box.");
        DrmInitData drmInitDataFromAtoms = getDrmInitDataFromAtoms(containerAtom.leafChildren);
        ContainerAtom containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_mvex);
        SparseArray sparseArray = new SparseArray();
        long j = C3446C.TIME_UNSET;
        int size = containerAtomOfType.leafChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            LeafAtom leafAtom = (LeafAtom) containerAtomOfType.leafChildren.get(i2);
            if (leafAtom.type == Atom.TYPE_trex) {
                Pair parseTrex = parseTrex(leafAtom.data);
                sparseArray.put(((Integer) parseTrex.first).intValue(), parseTrex.second);
            } else if (leafAtom.type == Atom.TYPE_mehd) {
                j = parseMehd(leafAtom.data);
            }
        }
        SparseArray sparseArray2 = new SparseArray();
        int size2 = containerAtom.containerChildren.size();
        for (size = 0; size < size2; size++) {
            Track parseTrak;
            ContainerAtom containerAtom2 = (ContainerAtom) containerAtom.containerChildren.get(size);
            if (containerAtom2.type == Atom.TYPE_trak) {
                parseTrak = AtomParsers.parseTrak(containerAtom2, containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd), j, drmInitDataFromAtoms, (this.flags & 32) != 0, false);
                if (parseTrak != null) {
                    sparseArray2.put(parseTrak.id, parseTrak);
                }
            }
        }
        int size3 = sparseArray2.size();
        if (this.trackBundles.size() == 0) {
            while (i < size3) {
                parseTrak = (Track) sparseArray2.valueAt(i);
                TrackBundle trackBundle = new TrackBundle(this.extractorOutput.track(i, parseTrak.type));
                trackBundle.init(parseTrak, (DefaultSampleValues) sparseArray.get(parseTrak.id));
                this.trackBundles.put(parseTrak.id, trackBundle);
                this.durationUs = Math.max(this.durationUs, parseTrak.durationUs);
                i++;
            }
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
            return;
        }
        if (this.trackBundles.size() != size3) {
            z = false;
        }
        Assertions.checkState(z);
        while (i < size3) {
            parseTrak = (Track) sparseArray2.valueAt(i);
            ((TrackBundle) this.trackBundles.get(parseTrak.id)).init(parseTrak, (DefaultSampleValues) sparseArray.get(parseTrak.id));
            i++;
        }
    }

    private static long parseMehd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
    }

    private static void parseMoof(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) {
        int size = containerAtom.containerChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ContainerAtom containerAtom2 = (ContainerAtom) containerAtom.containerChildren.get(i2);
            if (containerAtom2.type == Atom.TYPE_traf) {
                parseTraf(containerAtom2, sparseArray, i, bArr);
            }
        }
    }

    private static void parseSaio(ParsableByteArray parsableByteArray, TrackFragment trackFragment) {
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if ((Atom.parseFullAtomFlags(readInt) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt != 1) {
            throw new ParserException("Unexpected saio entry count: " + readUnsignedIntToInt);
        }
        readInt = Atom.parseFullAtomVersion(readInt);
        trackFragment.auxiliaryDataPosition = (readInt == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong()) + trackFragment.auxiliaryDataPosition;
    }

    private static void parseSaiz(TrackEncryptionBox trackEncryptionBox, ParsableByteArray parsableByteArray, TrackFragment trackFragment) {
        boolean z = true;
        int i = trackEncryptionBox.initializationVectorSize;
        parsableByteArray.setPosition(8);
        if ((Atom.parseFullAtomFlags(parsableByteArray.readInt()) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt != trackFragment.sampleCount) {
            throw new ParserException("Length mismatch: " + readUnsignedIntToInt + ", " + trackFragment.sampleCount);
        }
        if (readUnsignedByte == 0) {
            boolean[] zArr = trackFragment.sampleHasSubsampleEncryptionTable;
            int i2 = 0;
            readUnsignedByte = 0;
            while (i2 < readUnsignedIntToInt) {
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                int i3 = readUnsignedByte + readUnsignedByte2;
                zArr[i2] = readUnsignedByte2 > i;
                i2++;
                readUnsignedByte = i3;
            }
        } else {
            if (readUnsignedByte <= i) {
                z = false;
            }
            readUnsignedByte = (readUnsignedByte * readUnsignedIntToInt) + 0;
            Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, z);
        }
        trackFragment.initEncryptionData(readUnsignedByte);
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, int i, TrackFragment trackFragment) {
        parsableByteArray.setPosition(i + 8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        if ((parseFullAtomFlags & 1) != 0) {
            throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
        }
        boolean z = (parseFullAtomFlags & 2) != 0;
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt != trackFragment.sampleCount) {
            throw new ParserException("Length mismatch: " + readUnsignedIntToInt + ", " + trackFragment.sampleCount);
        }
        Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, z);
        trackFragment.initEncryptionData(parsableByteArray.bytesLeft());
        trackFragment.fillEncryptionData(parsableByteArray);
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, TrackFragment trackFragment) {
        parseSenc(parsableByteArray, 0, trackFragment);
    }

    private static void parseSgpd(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, String str, TrackFragment trackFragment) {
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == SAMPLE_GROUP_TYPE_seig) {
            if (Atom.parseFullAtomVersion(readInt) == 1) {
                parsableByteArray.skipBytes(4);
            }
            if (parsableByteArray.readInt() != 1) {
                throw new ParserException("Entry count in sbgp != 1 (unsupported).");
            }
            parsableByteArray2.setPosition(8);
            readInt = parsableByteArray2.readInt();
            if (parsableByteArray2.readInt() == SAMPLE_GROUP_TYPE_seig) {
                readInt = Atom.parseFullAtomVersion(readInt);
                if (readInt == 1) {
                    if (parsableByteArray2.readUnsignedInt() == 0) {
                        throw new ParserException("Variable length description in sgpd found (unsupported)");
                    }
                } else if (readInt >= 2) {
                    parsableByteArray2.skipBytes(4);
                }
                if (parsableByteArray2.readUnsignedInt() != 1) {
                    throw new ParserException("Entry count in sgpd != 1 (unsupported).");
                }
                parsableByteArray2.skipBytes(1);
                readInt = parsableByteArray2.readUnsignedByte();
                int i = (readInt & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                int i2 = readInt & 15;
                boolean z = parsableByteArray2.readUnsignedByte() == 1;
                if (z) {
                    int readUnsignedByte = parsableByteArray2.readUnsignedByte();
                    byte[] bArr = new byte[16];
                    parsableByteArray2.readBytes(bArr, 0, bArr.length);
                    byte[] bArr2 = null;
                    if (z && readUnsignedByte == 0) {
                        int readUnsignedByte2 = parsableByteArray2.readUnsignedByte();
                        bArr2 = new byte[readUnsignedByte2];
                        parsableByteArray2.readBytes(bArr2, 0, readUnsignedByte2);
                    }
                    trackFragment.definesEncryptionData = true;
                    trackFragment.trackEncryptionBox = new TrackEncryptionBox(z, str, readUnsignedByte, bArr, i, i2, bArr2);
                }
            }
        }
    }

    private static Pair<Long, ChunkIndex> parseSidx(ParsableByteArray parsableByteArray, long j) {
        long readUnsignedInt;
        long readUnsignedInt2;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(4);
        long readUnsignedInt3 = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            readUnsignedInt = parsableByteArray.readUnsignedInt() + j;
            readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        } else {
            readUnsignedInt = parsableByteArray.readUnsignedLongToLong() + j;
            readUnsignedInt2 = parsableByteArray.readUnsignedLongToLong();
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(readUnsignedInt2, C3446C.MICROS_PER_SECOND, readUnsignedInt3);
        parsableByteArray.skipBytes(2);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int[] iArr = new int[readUnsignedShort];
        long[] jArr = new long[readUnsignedShort];
        long[] jArr2 = new long[readUnsignedShort];
        long[] jArr3 = new long[readUnsignedShort];
        long j2 = readUnsignedInt;
        int i = 0;
        long j3 = readUnsignedInt2;
        readUnsignedInt2 = scaleLargeTimestamp;
        while (i < readUnsignedShort) {
            int readInt = parsableByteArray.readInt();
            if ((Integer.MIN_VALUE & readInt) != 0) {
                throw new ParserException("Unhandled indirect reference");
            }
            long readUnsignedInt4 = parsableByteArray.readUnsignedInt();
            iArr[i] = readInt & Integer.MAX_VALUE;
            jArr[i] = j2;
            jArr3[i] = readUnsignedInt2;
            readUnsignedInt2 = j3 + readUnsignedInt4;
            readUnsignedInt4 = Util.scaleLargeTimestamp(readUnsignedInt2, C3446C.MICROS_PER_SECOND, readUnsignedInt3);
            jArr2[i] = readUnsignedInt4 - jArr3[i];
            parsableByteArray.skipBytes(4);
            j2 += (long) iArr[i];
            i++;
            j3 = readUnsignedInt2;
            readUnsignedInt2 = readUnsignedInt4;
        }
        return Pair.create(Long.valueOf(scaleLargeTimestamp), new ChunkIndex(iArr, jArr, jArr2, jArr3));
    }

    private static long parseTfdt(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
    }

    private static TrackBundle parseTfhd(ParsableByteArray parsableByteArray, SparseArray<TrackBundle> sparseArray, int i) {
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        int readInt = parsableByteArray.readInt();
        if ((i & 16) != 0) {
            readInt = 0;
        }
        TrackBundle trackBundle = (TrackBundle) sparseArray.get(readInt);
        if (trackBundle == null) {
            return null;
        }
        if ((parseFullAtomFlags & 1) != 0) {
            long readUnsignedLongToLong = parsableByteArray.readUnsignedLongToLong();
            trackBundle.fragment.dataPosition = readUnsignedLongToLong;
            trackBundle.fragment.auxiliaryDataPosition = readUnsignedLongToLong;
        }
        DefaultSampleValues defaultSampleValues = trackBundle.defaultSampleValues;
        trackBundle.fragment.header = new DefaultSampleValues((parseFullAtomFlags & 2) != 0 ? parsableByteArray.readUnsignedIntToInt() - 1 : defaultSampleValues.sampleDescriptionIndex, (parseFullAtomFlags & 8) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration, (parseFullAtomFlags & 16) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.size, (parseFullAtomFlags & 32) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.flags);
        return trackBundle;
    }

    private static void parseTraf(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) {
        TrackBundle parseTfhd = parseTfhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tfhd).data, sparseArray, i);
        if (parseTfhd != null) {
            TrackFragment trackFragment = parseTfhd.fragment;
            long j = trackFragment.nextFragmentDecodeTime;
            parseTfhd.reset();
            if (containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt) != null && (i & 2) == 0) {
                j = parseTfdt(containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt).data);
            }
            parseTruns(containerAtom, parseTfhd, j, i);
            TrackEncryptionBox sampleDescriptionEncryptionBox = parseTfhd.track.getSampleDescriptionEncryptionBox(trackFragment.header.sampleDescriptionIndex);
            LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_saiz);
            if (leafAtomOfType != null) {
                parseSaiz(sampleDescriptionEncryptionBox, leafAtomOfType.data, trackFragment);
            }
            leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_saio);
            if (leafAtomOfType != null) {
                parseSaio(leafAtomOfType.data, trackFragment);
            }
            leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_senc);
            if (leafAtomOfType != null) {
                parseSenc(leafAtomOfType.data, trackFragment);
            }
            leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_sbgp);
            LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_sgpd);
            if (!(leafAtomOfType == null || leafAtomOfType2 == null)) {
                parseSgpd(leafAtomOfType.data, leafAtomOfType2.data, sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null, trackFragment);
            }
            int size = containerAtom.leafChildren.size();
            for (int i2 = 0; i2 < size; i2++) {
                LeafAtom leafAtom = (LeafAtom) containerAtom.leafChildren.get(i2);
                if (leafAtom.type == Atom.TYPE_uuid) {
                    parseUuid(leafAtom.data, trackFragment, bArr);
                }
            }
        }
    }

    private static Pair<Integer, DefaultSampleValues> parseTrex(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(12);
        return Pair.create(Integer.valueOf(parsableByteArray.readInt()), new DefaultSampleValues(parsableByteArray.readUnsignedIntToInt() - 1, parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readInt()));
    }

    private static int parseTrun(TrackBundle trackBundle, int i, long j, int i2, ParsableByteArray parsableByteArray, int i3) {
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        Track track = trackBundle.track;
        TrackFragment trackFragment = trackBundle.fragment;
        DefaultSampleValues defaultSampleValues = trackFragment.header;
        trackFragment.trunLength[i] = parsableByteArray.readUnsignedIntToInt();
        trackFragment.trunDataPosition[i] = trackFragment.dataPosition;
        if ((parseFullAtomFlags & 1) != 0) {
            long[] jArr = trackFragment.trunDataPosition;
            jArr[i] = jArr[i] + ((long) parsableByteArray.readInt());
        }
        Object obj = (parseFullAtomFlags & 4) != 0 ? 1 : null;
        int i4 = defaultSampleValues.flags;
        if (obj != null) {
            i4 = parsableByteArray.readUnsignedIntToInt();
        }
        Object obj2 = (parseFullAtomFlags & 256) != 0 ? 1 : null;
        Object obj3 = (parseFullAtomFlags & 512) != 0 ? 1 : null;
        Object obj4 = (parseFullAtomFlags & 1024) != 0 ? 1 : null;
        Object obj5 = (parseFullAtomFlags & 2048) != 0 ? 1 : null;
        long scaleLargeTimestamp = (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) ? Util.scaleLargeTimestamp(track.editListMediaTimes[0], 1000, track.timescale) : 0;
        int[] iArr = trackFragment.sampleSizeTable;
        int[] iArr2 = trackFragment.sampleCompositionTimeOffsetTable;
        long[] jArr2 = trackFragment.sampleDecodingTimeTable;
        boolean[] zArr = trackFragment.sampleIsSyncFrameTable;
        Object obj6 = (track.type != 2 || (i2 & 1) == 0) ? null : 1;
        int i5 = i3 + trackFragment.trunLength[i];
        long j2 = track.timescale;
        if (i > 0) {
            j = trackFragment.nextFragmentDecodeTime;
        }
        long j3 = j;
        while (i3 < i5) {
            int readUnsignedIntToInt = obj2 != null ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration;
            int readUnsignedIntToInt2 = obj3 != null ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.size;
            int readInt = (i3 != 0 || obj == null) ? obj4 != null ? parsableByteArray.readInt() : defaultSampleValues.flags : i4;
            if (obj5 != null) {
                iArr2[i3] = (int) ((((long) parsableByteArray.readInt()) * 1000) / j2);
            } else {
                iArr2[i3] = 0;
            }
            jArr2[i3] = Util.scaleLargeTimestamp(j3, 1000, j2) - scaleLargeTimestamp;
            iArr[i3] = readUnsignedIntToInt2;
            boolean z = ((readInt >> 16) & 1) == 0 && (obj6 == null || i3 == 0);
            zArr[i3] = z;
            j3 += (long) readUnsignedIntToInt;
            i3++;
        }
        trackFragment.nextFragmentDecodeTime = j3;
        return i5;
    }

    private static void parseTruns(ContainerAtom containerAtom, TrackBundle trackBundle, long j, int i) {
        List list = containerAtom.leafChildren;
        int size = list.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < size) {
            int readUnsignedIntToInt;
            LeafAtom leafAtom = (LeafAtom) list.get(i2);
            if (leafAtom.type == Atom.TYPE_trun) {
                ParsableByteArray parsableByteArray = leafAtom.data;
                parsableByteArray.setPosition(12);
                readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                if (readUnsignedIntToInt > 0) {
                    readUnsignedIntToInt += i3;
                    i3 = i4 + 1;
                    i2++;
                    i4 = i3;
                    i3 = readUnsignedIntToInt;
                }
            }
            readUnsignedIntToInt = i3;
            i3 = i4;
            i2++;
            i4 = i3;
            i3 = readUnsignedIntToInt;
        }
        trackBundle.currentTrackRunIndex = 0;
        trackBundle.currentSampleInTrackRun = 0;
        trackBundle.currentSampleIndex = 0;
        trackBundle.fragment.initTables(i4, i3);
        int i5 = 0;
        i3 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            leafAtom = (LeafAtom) list.get(i6);
            if (leafAtom.type == Atom.TYPE_trun) {
                int i7 = i3 + 1;
                i5 = parseTrun(trackBundle, i3, j, i, leafAtom.data, i5);
                i3 = i7;
            }
        }
    }

    private static void parseUuid(ParsableByteArray parsableByteArray, TrackFragment trackFragment, byte[] bArr) {
        parsableByteArray.setPosition(8);
        parsableByteArray.readBytes(bArr, 0, 16);
        if (Arrays.equals(bArr, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
            parseSenc(parsableByteArray, 16, trackFragment);
        }
    }

    private void processAtomEnded(long j) {
        while (!this.containerAtoms.isEmpty() && ((ContainerAtom) this.containerAtoms.peek()).endPosition == j) {
            onContainerAtomRead((ContainerAtom) this.containerAtoms.pop());
        }
        enterReadingAtomHeaderState();
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) {
        long length;
        if (this.atomHeaderBytesRead == 0) {
            if (!extractorInput.readFully(this.atomHeader.data, 0, 8, true)) {
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        if (this.atomSize == 1) {
            extractorInput.readFully(this.atomHeader.data, 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (this.atomSize == 0) {
            length = extractorInput.getLength();
            if (length == -1 && !this.containerAtoms.isEmpty()) {
                length = ((ContainerAtom) this.containerAtoms.peek()).endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - extractorInput.getPosition()) + ((long) this.atomHeaderBytesRead);
            }
        }
        if (this.atomSize < ((long) this.atomHeaderBytesRead)) {
            throw new ParserException("Atom size less than header length (unsupported).");
        }
        length = extractorInput.getPosition() - ((long) this.atomHeaderBytesRead);
        if (this.atomType == Atom.TYPE_moof) {
            int size = this.trackBundles.size();
            for (int i = 0; i < size; i++) {
                TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
                trackFragment.atomPosition = length;
                trackFragment.auxiliaryDataPosition = length;
                trackFragment.dataPosition = length;
            }
        }
        if (this.atomType == Atom.TYPE_mdat) {
            this.currentTrackBundle = null;
            this.endOfMdatPosition = this.atomSize + length;
            if (!this.haveOutputSeekMap) {
                this.extractorOutput.seekMap(new Unseekable(this.durationUs));
                this.haveOutputSeekMap = true;
            }
            this.parserState = 2;
            return true;
        }
        if (shouldParseContainerAtom(this.atomType)) {
            long position = (extractorInput.getPosition() + this.atomSize) - 8;
            this.containerAtoms.add(new ContainerAtom(this.atomType, position));
            if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                processAtomEnded(position);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            if (this.atomHeaderBytesRead != 8) {
                throw new ParserException("Leaf atom defines extended atom size (unsupported).");
            } else if (this.atomSize > 2147483647L) {
                throw new ParserException("Leaf atom with length > 2147483647 (unsupported).");
            } else {
                this.atomData = new ParsableByteArray((int) this.atomSize);
                System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                this.parserState = 1;
            }
        } else if (this.atomSize > 2147483647L) {
            throw new ParserException("Skipping atom with length > 2147483647 (unsupported).");
        } else {
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private void readAtomPayload(ExtractorInput extractorInput) {
        int i = ((int) this.atomSize) - this.atomHeaderBytesRead;
        if (this.atomData != null) {
            extractorInput.readFully(this.atomData.data, 8, i);
            onLeafAtomRead(new LeafAtom(this.atomType, this.atomData), extractorInput.getPosition());
        } else {
            extractorInput.skipFully(i);
        }
        processAtomEnded(extractorInput.getPosition());
    }

    private void readEncryptionData(ExtractorInput extractorInput) {
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        int size = this.trackBundles.size();
        int i = 0;
        while (i < size) {
            TrackBundle trackBundle2;
            long j2;
            TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
            long j3;
            if (!trackFragment.sampleEncryptionDataNeedsFill || trackFragment.auxiliaryDataPosition >= j) {
                j3 = j;
                trackBundle2 = trackBundle;
                j2 = j3;
            } else {
                j3 = trackFragment.auxiliaryDataPosition;
                trackBundle2 = (TrackBundle) this.trackBundles.valueAt(i);
                j2 = j3;
            }
            i++;
            trackBundle = trackBundle2;
            j = j2;
        }
        if (trackBundle == null) {
            this.parserState = 3;
            return;
        }
        int position = (int) (j - extractorInput.getPosition());
        if (position < 0) {
            throw new ParserException("Offset to encryption data was negative.");
        }
        extractorInput.skipFully(position);
        trackBundle.fragment.fillEncryptionData(extractorInput);
    }

    private boolean readSample(ExtractorInput extractorInput) {
        int position;
        if (this.parserState == 3) {
            if (this.currentTrackBundle == null) {
                TrackBundle nextFragmentRun = getNextFragmentRun(this.trackBundles);
                if (nextFragmentRun == null) {
                    position = (int) (this.endOfMdatPosition - extractorInput.getPosition());
                    if (position < 0) {
                        throw new ParserException("Offset to end of mdat was negative.");
                    }
                    extractorInput.skipFully(position);
                    enterReadingAtomHeaderState();
                    return false;
                }
                position = (int) (nextFragmentRun.fragment.trunDataPosition[nextFragmentRun.currentTrackRunIndex] - extractorInput.getPosition());
                if (position < 0) {
                    Log.w(TAG, "Ignoring negative offset to sample data.");
                    position = 0;
                }
                extractorInput.skipFully(position);
                this.currentTrackBundle = nextFragmentRun;
            }
            this.sampleSize = this.currentTrackBundle.fragment.sampleSizeTable[this.currentTrackBundle.currentSampleIndex];
            if (this.currentTrackBundle.fragment.definesEncryptionData) {
                this.sampleBytesWritten = appendSampleEncryptionData(this.currentTrackBundle);
                this.sampleSize += this.sampleBytesWritten;
            } else {
                this.sampleBytesWritten = 0;
            }
            if (this.currentTrackBundle.track.sampleTransformation == 1) {
                this.sampleSize -= 8;
                extractorInput.skipFully(8);
            }
            this.parserState = 4;
            this.sampleCurrentNalBytesRemaining = 0;
        }
        TrackFragment trackFragment = this.currentTrackBundle.fragment;
        Track track = this.currentTrackBundle.track;
        TrackOutput trackOutput = this.currentTrackBundle.output;
        int i = this.currentTrackBundle.currentSampleIndex;
        if (track.nalUnitLengthFieldLength != 0) {
            byte[] bArr = this.nalPrefix.data;
            bArr[0] = (byte) 0;
            bArr[1] = (byte) 0;
            bArr[2] = (byte) 0;
            int i2 = track.nalUnitLengthFieldLength + 1;
            int i3 = 4 - track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < this.sampleSize) {
                if (this.sampleCurrentNalBytesRemaining == 0) {
                    extractorInput.readFully(bArr, i3, i2);
                    this.nalPrefix.setPosition(0);
                    this.sampleCurrentNalBytesRemaining = this.nalPrefix.readUnsignedIntToInt() - 1;
                    this.nalStartCode.setPosition(0);
                    trackOutput.sampleData(this.nalStartCode, 4);
                    trackOutput.sampleData(this.nalPrefix, 1);
                    boolean z = this.cea608TrackOutputs != null && NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, bArr[4]);
                    this.processSeiNalUnitPayload = z;
                    this.sampleBytesWritten += 5;
                    this.sampleSize += i3;
                } else {
                    if (this.processSeiNalUnitPayload) {
                        this.nalBuffer.reset(this.sampleCurrentNalBytesRemaining);
                        extractorInput.readFully(this.nalBuffer.data, 0, this.sampleCurrentNalBytesRemaining);
                        trackOutput.sampleData(this.nalBuffer, this.sampleCurrentNalBytesRemaining);
                        int i4 = this.sampleCurrentNalBytesRemaining;
                        int unescapeStream = NalUnitUtil.unescapeStream(this.nalBuffer.data, this.nalBuffer.limit());
                        this.nalBuffer.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType) ? 1 : 0);
                        this.nalBuffer.setLimit(unescapeStream);
                        CeaUtil.consume(trackFragment.getSamplePresentationTime(i) * 1000, this.nalBuffer, this.cea608TrackOutputs);
                        position = i4;
                    } else {
                        position = trackOutput.sampleData(extractorInput, this.sampleCurrentNalBytesRemaining, false);
                    }
                    this.sampleBytesWritten += position;
                    this.sampleCurrentNalBytesRemaining -= position;
                }
            }
        } else {
            while (this.sampleBytesWritten < this.sampleSize) {
                this.sampleBytesWritten = trackOutput.sampleData(extractorInput, this.sampleSize - this.sampleBytesWritten, false) + this.sampleBytesWritten;
            }
        }
        long samplePresentationTime = trackFragment.getSamplePresentationTime(i) * 1000;
        if (this.timestampAdjuster != null) {
            samplePresentationTime = this.timestampAdjuster.adjustSampleTimestamp(samplePresentationTime);
        }
        position = trackFragment.sampleIsSyncFrameTable[i] ? 1 : 0;
        CryptoData cryptoData = null;
        if (trackFragment.definesEncryptionData) {
            i = 1073741824 | position;
            cryptoData = (trackFragment.trackEncryptionBox != null ? trackFragment.trackEncryptionBox : track.getSampleDescriptionEncryptionBox(trackFragment.header.sampleDescriptionIndex)).cryptoData;
        } else {
            i = position;
        }
        trackOutput.sampleMetadata(samplePresentationTime, i, this.sampleSize, 0, cryptoData);
        while (!this.pendingMetadataSampleInfos.isEmpty()) {
            MetadataSampleInfo metadataSampleInfo = (MetadataSampleInfo) this.pendingMetadataSampleInfos.removeFirst();
            this.pendingMetadataSampleBytes -= metadataSampleInfo.size;
            this.eventMessageTrackOutput.sampleMetadata(metadataSampleInfo.presentationTimeDeltaUs + samplePresentationTime, 1, metadataSampleInfo.size, this.pendingMetadataSampleBytes, null);
        }
        TrackBundle trackBundle = this.currentTrackBundle;
        trackBundle.currentSampleIndex++;
        trackBundle = this.currentTrackBundle;
        trackBundle.currentSampleInTrackRun++;
        if (this.currentTrackBundle.currentSampleInTrackRun == trackFragment.trunLength[this.currentTrackBundle.currentTrackRunIndex]) {
            trackBundle = this.currentTrackBundle;
            trackBundle.currentTrackRunIndex++;
            this.currentTrackBundle.currentSampleInTrackRun = 0;
            this.currentTrackBundle = null;
        }
        this.parserState = 3;
        return true;
    }

    private static boolean shouldParseContainerAtom(int i) {
        return i == Atom.TYPE_moov || i == Atom.TYPE_trak || i == Atom.TYPE_mdia || i == Atom.TYPE_minf || i == Atom.TYPE_stbl || i == Atom.TYPE_moof || i == Atom.TYPE_traf || i == Atom.TYPE_mvex || i == Atom.TYPE_edts;
    }

    private static boolean shouldParseLeafAtom(int i) {
        return i == Atom.TYPE_hdlr || i == Atom.TYPE_mdhd || i == Atom.TYPE_mvhd || i == Atom.TYPE_sidx || i == Atom.TYPE_stsd || i == Atom.TYPE_tfdt || i == Atom.TYPE_tfhd || i == Atom.TYPE_tkhd || i == Atom.TYPE_trex || i == Atom.TYPE_trun || i == Atom.TYPE_pssh || i == Atom.TYPE_saiz || i == Atom.TYPE_saio || i == Atom.TYPE_senc || i == Atom.TYPE_uuid || i == Atom.TYPE_sbgp || i == Atom.TYPE_sgpd || i == Atom.TYPE_elst || i == Atom.TYPE_mehd || i == Atom.TYPE_emsg;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        if (this.sideloadedTrack != null) {
            TrackBundle trackBundle = new TrackBundle(extractorOutput.track(0, this.sideloadedTrack.type));
            trackBundle.init(this.sideloadedTrack, new DefaultSampleValues(0, 0, 0, 0));
            this.trackBundles.put(0, trackBundle);
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
        }
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) {
        while (true) {
            switch (this.parserState) {
                case 0:
                    if (readAtomHeader(extractorInput)) {
                        break;
                    }
                    return -1;
                case 1:
                    readAtomPayload(extractorInput);
                    break;
                case 2:
                    readEncryptionData(extractorInput);
                    break;
                default:
                    if (!readSample(extractorInput)) {
                        break;
                    }
                    return 0;
            }
        }
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        int size = this.trackBundles.size();
        for (int i = 0; i < size; i++) {
            ((TrackBundle) this.trackBundles.valueAt(i)).reset();
        }
        this.pendingMetadataSampleInfos.clear();
        this.pendingMetadataSampleBytes = 0;
        this.containerAtoms.clear();
        enterReadingAtomHeaderState();
    }

    public boolean sniff(ExtractorInput extractorInput) {
        return Sniffer.sniffFragmented(extractorInput);
    }
}
