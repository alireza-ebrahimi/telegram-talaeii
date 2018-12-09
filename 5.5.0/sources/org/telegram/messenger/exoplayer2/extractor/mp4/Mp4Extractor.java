package org.telegram.messenger.exoplayer2.extractor.mp4;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorOutput;
import org.telegram.messenger.exoplayer2.extractor.ExtractorsFactory;
import org.telegram.messenger.exoplayer2.extractor.GaplessInfoHolder;
import org.telegram.messenger.exoplayer2.extractor.PositionHolder;
import org.telegram.messenger.exoplayer2.extractor.SeekMap;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.NalUnitUtil;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

public final class Mp4Extractor implements Extractor, SeekMap {
    private static final int BRAND_QUICKTIME = Util.getIntegerCodeForString("qt  ");
    public static final ExtractorsFactory FACTORY = new C34841();
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 1;
    private static final long RELOAD_MINIMUM_SEEK_DISTANCE = 262144;
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private final Stack<ContainerAtom> containerAtoms;
    private long durationUs;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private boolean isQuickTime;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private Mp4Track[] tracks;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.mp4.Mp4Extractor$1 */
    static class C34841 implements ExtractorsFactory {
        C34841() {
        }

        public Extractor[] createExtractors() {
            return new Extractor[]{new Mp4Extractor()};
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class Mp4Track {
        public int sampleIndex;
        public final TrackSampleTable sampleTable;
        public final Track track;
        public final TrackOutput trackOutput;

        public Mp4Track(Track track, TrackSampleTable trackSampleTable, TrackOutput trackOutput) {
            this.track = track;
            this.sampleTable = trackSampleTable;
            this.trackOutput = trackOutput;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface State {
    }

    public Mp4Extractor() {
        this(0);
    }

    public Mp4Extractor(int i) {
        this.flags = i;
        this.atomHeader = new ParsableByteArray(16);
        this.containerAtoms = new Stack();
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private int getTrackIndexOfEarliestCurrentSample() {
        int i = -1;
        long j = Long.MAX_VALUE;
        for (int i2 = 0; i2 < this.tracks.length; i2++) {
            Mp4Track mp4Track = this.tracks[i2];
            int i3 = mp4Track.sampleIndex;
            if (i3 != mp4Track.sampleTable.sampleCount) {
                long j2 = mp4Track.sampleTable.offsets[i3];
                if (j2 < j) {
                    j = j2;
                    i = i2;
                }
            }
        }
        return i;
    }

    private void processAtomEnded(long j) {
        while (!this.containerAtoms.isEmpty() && ((ContainerAtom) this.containerAtoms.peek()).endPosition == j) {
            ContainerAtom containerAtom = (ContainerAtom) this.containerAtoms.pop();
            if (containerAtom.type == Atom.TYPE_moov) {
                processMoovAtom(containerAtom);
                this.containerAtoms.clear();
                this.parserState = 2;
            } else if (!this.containerAtoms.isEmpty()) {
                ((ContainerAtom) this.containerAtoms.peek()).add(containerAtom);
            }
        }
        if (this.parserState != 2) {
            enterReadingAtomHeaderState();
        }
    }

    private static boolean processFtypAtom(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        if (parsableByteArray.readInt() == BRAND_QUICKTIME) {
            return true;
        }
        parsableByteArray.skipBytes(4);
        while (parsableByteArray.bytesLeft() > 0) {
            if (parsableByteArray.readInt() == BRAND_QUICKTIME) {
                return true;
            }
        }
        return false;
    }

    private void processMoovAtom(ContainerAtom containerAtom) {
        Metadata metadata;
        List arrayList = new ArrayList();
        GaplessInfoHolder gaplessInfoHolder = new GaplessInfoHolder();
        LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_udta);
        if (leafAtomOfType != null) {
            Metadata parseUdta = AtomParsers.parseUdta(leafAtomOfType, this.isQuickTime);
            if (parseUdta != null) {
                gaplessInfoHolder.setFromMetadata(parseUdta);
            }
            metadata = parseUdta;
        } else {
            metadata = null;
        }
        int i = 0;
        long j = Long.MAX_VALUE;
        long j2 = C3446C.TIME_UNSET;
        while (i < containerAtom.containerChildren.size()) {
            long j3;
            long j4;
            ContainerAtom containerAtom2 = (ContainerAtom) containerAtom.containerChildren.get(i);
            if (containerAtom2.type != Atom.TYPE_trak) {
                j3 = j;
                j4 = j2;
            } else {
                Track parseTrak = AtomParsers.parseTrak(containerAtom2, containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd), C3446C.TIME_UNSET, null, (this.flags & 1) != 0, this.isQuickTime);
                if (parseTrak == null) {
                    j3 = j;
                    j4 = j2;
                } else {
                    TrackSampleTable parseStbl = AtomParsers.parseStbl(parseTrak, containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia).getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl), gaplessInfoHolder);
                    if (parseStbl.sampleCount == 0) {
                        j3 = j;
                        j4 = j2;
                    } else {
                        Mp4Track mp4Track = new Mp4Track(parseTrak, parseStbl, this.extractorOutput.track(i, parseTrak.type));
                        Format copyWithMaxInputSize = parseTrak.format.copyWithMaxInputSize(parseStbl.maximumSize + 30);
                        if (parseTrak.type == 1) {
                            if (gaplessInfoHolder.hasGaplessInfo()) {
                                copyWithMaxInputSize = copyWithMaxInputSize.copyWithGaplessInfo(gaplessInfoHolder.encoderDelay, gaplessInfoHolder.encoderPadding);
                            }
                            if (metadata != null) {
                                copyWithMaxInputSize = copyWithMaxInputSize.copyWithMetadata(metadata);
                            }
                        }
                        mp4Track.trackOutput.format(copyWithMaxInputSize);
                        j2 = Math.max(j2, parseTrak.durationUs);
                        arrayList.add(mp4Track);
                        j3 = parseStbl.offsets[0];
                        if (j3 < j) {
                            j4 = j2;
                        } else {
                            j3 = j;
                            j4 = j2;
                        }
                    }
                }
            }
            i++;
            j = j3;
            j2 = j4;
        }
        this.durationUs = j2;
        this.tracks = (Mp4Track[]) arrayList.toArray(new Mp4Track[arrayList.size()]);
        this.extractorOutput.endTracks();
        this.extractorOutput.seekMap(this);
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) {
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
            long length = extractorInput.getLength();
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
        if (shouldParseContainerAtom(this.atomType)) {
            long position = (extractorInput.getPosition() + this.atomSize) - ((long) this.atomHeaderBytesRead);
            this.containerAtoms.add(new ContainerAtom(this.atomType, position));
            if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                processAtomEnded(position);
            } else {
                enterReadingAtomHeaderState();
            }
        } else if (shouldParseLeafAtom(this.atomType)) {
            Assertions.checkState(this.atomHeaderBytesRead == 8);
            Assertions.checkState(this.atomSize <= 2147483647L);
            this.atomData = new ParsableByteArray((int) this.atomSize);
            System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
            this.parserState = 1;
        } else {
            this.atomData = null;
            this.parserState = 1;
        }
        return true;
    }

    private boolean readAtomPayload(ExtractorInput extractorInput, PositionHolder positionHolder) {
        boolean z;
        long j = this.atomSize - ((long) this.atomHeaderBytesRead);
        long position = extractorInput.getPosition() + j;
        if (this.atomData != null) {
            extractorInput.readFully(this.atomData.data, this.atomHeaderBytesRead, (int) j);
            if (this.atomType == Atom.TYPE_ftyp) {
                this.isQuickTime = processFtypAtom(this.atomData);
                z = false;
            } else if (this.containerAtoms.isEmpty()) {
                z = false;
            } else {
                ((ContainerAtom) this.containerAtoms.peek()).add(new LeafAtom(this.atomType, this.atomData));
                z = false;
            }
        } else if (j < RELOAD_MINIMUM_SEEK_DISTANCE) {
            extractorInput.skipFully((int) j);
            z = false;
        } else {
            positionHolder.position = j + extractorInput.getPosition();
            z = true;
        }
        processAtomEnded(position);
        return z && this.parserState != 2;
    }

    private int readSample(ExtractorInput extractorInput, PositionHolder positionHolder) {
        int trackIndexOfEarliestCurrentSample = getTrackIndexOfEarliestCurrentSample();
        if (trackIndexOfEarliestCurrentSample == -1) {
            return -1;
        }
        Mp4Track mp4Track = this.tracks[trackIndexOfEarliestCurrentSample];
        TrackOutput trackOutput = mp4Track.trackOutput;
        int i = mp4Track.sampleIndex;
        long j = mp4Track.sampleTable.offsets[i];
        trackIndexOfEarliestCurrentSample = mp4Track.sampleTable.sizes[i];
        if (mp4Track.track.sampleTransformation == 1) {
            j += 8;
            trackIndexOfEarliestCurrentSample -= 8;
        }
        long position = (j - extractorInput.getPosition()) + ((long) this.sampleBytesWritten);
        if (position < 0 || position >= RELOAD_MINIMUM_SEEK_DISTANCE) {
            positionHolder.position = j;
            return 1;
        }
        int sampleData;
        extractorInput.skipFully((int) position);
        int i2;
        if (mp4Track.track.nalUnitLengthFieldLength != 0) {
            byte[] bArr = this.nalLength.data;
            bArr[0] = (byte) 0;
            bArr[1] = (byte) 0;
            bArr[2] = (byte) 0;
            i2 = mp4Track.track.nalUnitLengthFieldLength;
            int i3 = 4 - mp4Track.track.nalUnitLengthFieldLength;
            while (this.sampleBytesWritten < trackIndexOfEarliestCurrentSample) {
                if (this.sampleCurrentNalBytesRemaining == 0) {
                    extractorInput.readFully(this.nalLength.data, i3, i2);
                    this.nalLength.setPosition(0);
                    this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
                    this.nalStartCode.setPosition(0);
                    trackOutput.sampleData(this.nalStartCode, 4);
                    this.sampleBytesWritten += 4;
                    trackIndexOfEarliestCurrentSample += i3;
                } else {
                    sampleData = trackOutput.sampleData(extractorInput, this.sampleCurrentNalBytesRemaining, false);
                    this.sampleBytesWritten += sampleData;
                    this.sampleCurrentNalBytesRemaining -= sampleData;
                }
            }
            sampleData = trackIndexOfEarliestCurrentSample;
        } else {
            while (this.sampleBytesWritten < trackIndexOfEarliestCurrentSample) {
                i2 = trackOutput.sampleData(extractorInput, trackIndexOfEarliestCurrentSample - this.sampleBytesWritten, false);
                this.sampleBytesWritten += i2;
                this.sampleCurrentNalBytesRemaining -= i2;
            }
            sampleData = trackIndexOfEarliestCurrentSample;
        }
        trackOutput.sampleMetadata(mp4Track.sampleTable.timestampsUs[i], mp4Track.sampleTable.flags[i], sampleData, 0, null);
        mp4Track.sampleIndex++;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        return 0;
    }

    private static boolean shouldParseContainerAtom(int i) {
        return i == Atom.TYPE_moov || i == Atom.TYPE_trak || i == Atom.TYPE_mdia || i == Atom.TYPE_minf || i == Atom.TYPE_stbl || i == Atom.TYPE_edts;
    }

    private static boolean shouldParseLeafAtom(int i) {
        return i == Atom.TYPE_mdhd || i == Atom.TYPE_mvhd || i == Atom.TYPE_hdlr || i == Atom.TYPE_stsd || i == Atom.TYPE_stts || i == Atom.TYPE_stss || i == Atom.TYPE_ctts || i == Atom.TYPE_elst || i == Atom.TYPE_stsc || i == Atom.TYPE_stsz || i == Atom.TYPE_stz2 || i == Atom.TYPE_stco || i == Atom.TYPE_co64 || i == Atom.TYPE_tkhd || i == Atom.TYPE_ftyp || i == Atom.TYPE_udta;
    }

    private void updateSampleIndices(long j) {
        for (Mp4Track mp4Track : this.tracks) {
            TrackSampleTable trackSampleTable = mp4Track.sampleTable;
            int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j);
            if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j);
            }
            mp4Track.sampleIndex = indexOfEarlierOrEqualSynchronizationSample;
        }
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public long getPosition(long j) {
        long j2 = Long.MAX_VALUE;
        Mp4Track[] mp4TrackArr = this.tracks;
        int length = mp4TrackArr.length;
        int i = 0;
        while (i < length) {
            TrackSampleTable trackSampleTable = mp4TrackArr[i].sampleTable;
            int indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfEarlierOrEqualSynchronizationSample(j);
            if (indexOfEarlierOrEqualSynchronizationSample == -1) {
                indexOfEarlierOrEqualSynchronizationSample = trackSampleTable.getIndexOfLaterOrEqualSynchronizationSample(j);
            }
            long j3 = trackSampleTable.offsets[indexOfEarlierOrEqualSynchronizationSample];
            if (j3 >= j2) {
                j3 = j2;
            }
            i++;
            j2 = j3;
        }
        return j2;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    public boolean isSeekable() {
        return true;
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
                    if (!readAtomPayload(extractorInput, positionHolder)) {
                        break;
                    }
                    return 1;
                case 2:
                    return readSample(extractorInput, positionHolder);
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.containerAtoms.clear();
        this.atomHeaderBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        if (j == 0) {
            enterReadingAtomHeaderState();
        } else if (this.tracks != null) {
            updateSampleIndices(j2);
        }
    }

    public boolean sniff(ExtractorInput extractorInput) {
        return Sniffer.sniffUnfragmented(extractorInput);
    }
}
