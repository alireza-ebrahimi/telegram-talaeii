package com.googlecode.mp4parser.authoring.builder;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.DataEntryUrlBox;
import com.coremedia.iso.boxes.DataInformationBox;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.EditBox;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.EditListBox.Entry;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.HintMediaHeaderBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.NullMediaHeaderBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.SoundMediaHeaderBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.SubtitleMediaHeaderBox;
import com.coremedia.iso.boxes.SyncSampleBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.TrackReferenceTypeBox;
import com.coremedia.iso.boxes.VideoMediaHeaderBox;
import com.coremedia.iso.boxes.mdat.MediaDataBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.BasicContainer;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.tracks.CencEncryptedTrack;
import com.googlecode.mp4parser.boxes.dece.SampleEncryptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationOffsetsBox;
import com.mp4parser.iso14496.part12.SampleAuxiliaryInformationSizesBox;
import com.mp4parser.iso23001.part7.CencSampleAuxiliaryDataFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.exoplayer2.C0907C;

public class DefaultMp4Builder implements Mp4Builder {
    static final /* synthetic */ boolean $assertionsDisabled = (!DefaultMp4Builder.class.desiredAssertionStatus());
    private static Logger LOG = Logger.getLogger(DefaultMp4Builder.class.getName());
    Set<StaticChunkOffsetBox> chunkOffsetBoxes = new HashSet();
    private FragmentIntersectionFinder intersectionFinder;
    Set<SampleAuxiliaryInformationOffsetsBox> sampleAuxiliaryInformationOffsetsBoxes = new HashSet();
    HashMap<Track, List<Sample>> track2Sample = new HashMap();
    HashMap<Track, long[]> track2SampleSizes = new HashMap();

    private class InterleaveChunkMdat implements Box {
        List<List<Sample>> chunkList;
        long contentSize;
        Container parent;
        List<Track> tracks;

        private InterleaveChunkMdat(Movie movie, Map<Track, int[]> chunks, long contentSize) {
            this.chunkList = new ArrayList();
            this.contentSize = contentSize;
            this.tracks = movie.getTracks();
            for (int i = 0; i < ((int[]) chunks.values().iterator().next()).length; i++) {
                for (Track track : this.tracks) {
                    int[] chunkSizes = (int[]) chunks.get(track);
                    long firstSampleOfChunk = 0;
                    for (int j = 0; j < i; j++) {
                        firstSampleOfChunk += (long) chunkSizes[j];
                    }
                    this.chunkList.add(((List) DefaultMp4Builder.this.track2Sample.get(track)).subList(CastUtils.l2i(firstSampleOfChunk), CastUtils.l2i(((long) chunkSizes[i]) + firstSampleOfChunk)));
                }
            }
        }

        public Container getParent() {
            return this.parent;
        }

        public void setParent(Container parent) {
            this.parent = parent;
        }

        public long getOffset() {
            throw new RuntimeException("Doesn't have any meaning for programmatically created boxes");
        }

        public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        }

        public long getDataOffset() {
            long offset = 16;
            for (InterleaveChunkMdat b = this; b instanceof Box; b = b.getParent()) {
                for (Object box : b.getParent().getBoxes()) {
                    if (b == box) {
                        break;
                    }
                    offset += box.getSize();
                }
            }
            return offset;
        }

        public String getType() {
            return MediaDataBox.TYPE;
        }

        public long getSize() {
            return 16 + this.contentSize;
        }

        private boolean isSmallBox(long contentSize) {
            return 8 + contentSize < 4294967296L;
        }

        public void getBox(WritableByteChannel writableByteChannel) throws IOException {
            ByteBuffer bb = ByteBuffer.allocate(16);
            long size = getSize();
            if (isSmallBox(size)) {
                IsoTypeWriter.writeUInt32(bb, size);
            } else {
                IsoTypeWriter.writeUInt32(bb, 1);
            }
            bb.put(IsoFile.fourCCtoBytes(MediaDataBox.TYPE));
            if (isSmallBox(size)) {
                bb.put(new byte[8]);
            } else {
                IsoTypeWriter.writeUInt64(bb, size);
            }
            bb.rewind();
            writableByteChannel.write(bb);
            for (List<Sample> samples : this.chunkList) {
                for (Sample sample : samples) {
                    sample.writeTo(writableByteChannel);
                }
            }
        }
    }

    private static long sum(int[] ls) {
        long rc = 0;
        for (int i : ls) {
            rc += (long) i;
        }
        return rc;
    }

    private static long sum(long[] ls) {
        long rc = 0;
        for (long l : ls) {
            rc += l;
        }
        return rc;
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public void setIntersectionFinder(FragmentIntersectionFinder intersectionFinder) {
        this.intersectionFinder = intersectionFinder;
    }

    public Container build(Movie movie) {
        if (this.intersectionFinder == null) {
            this.intersectionFinder = new TwoSecondIntersectionFinder(movie, 2);
        }
        LOG.fine("Creating movie " + movie);
        for (Track track : movie.getTracks()) {
            int i;
            List<Sample> samples = track.getSamples();
            putSamples(track, samples);
            Object sizes = new long[samples.size()];
            for (i = 0; i < sizes.length; i++) {
                sizes[i] = ((Sample) samples.get(i)).getSize();
            }
            this.track2SampleSizes.put(track, sizes);
        }
        BasicContainer isoFile = new BasicContainer();
        isoFile.addBox(createFileTypeBox(movie));
        Map<Track, int[]> chunks = new HashMap();
        for (Track track2 : movie.getTracks()) {
            chunks.put(track2, getChunkSizes(track2, movie));
        }
        Box moov = createMovieBox(movie, chunks);
        isoFile.addBox(moov);
        long contentSize = 0;
        for (SampleSizeBox stsz : Path.getPaths(moov, "trak/mdia/minf/stbl/stsz")) {
            contentSize += sum(stsz.getSampleSizes());
        }
        InterleaveChunkMdat mdat = new InterleaveChunkMdat(movie, chunks, contentSize);
        isoFile.addBox(mdat);
        long dataOffset = mdat.getDataOffset();
        for (StaticChunkOffsetBox chunkOffsetBox : this.chunkOffsetBoxes) {
            long[] offsets = chunkOffsetBox.getChunkOffsets();
            for (i = 0; i < offsets.length; i++) {
                offsets[i] = offsets[i] + dataOffset;
            }
        }
        for (SampleAuxiliaryInformationOffsetsBox saio : this.sampleAuxiliaryInformationOffsetsBoxes) {
            long offset = saio.getSize() + 44;
            Box current = saio;
            while (true) {
                Container b = current.getParent();
                for (Box box : b.getBoxes()) {
                    if (box == current) {
                        break;
                    }
                    offset += box.getSize();
                }
                if (!(b instanceof Box)) {
                    break;
                }
                Object current2 = b;
            }
            long[] saioOffsets = saio.getOffsets();
            for (i = 0; i < saioOffsets.length; i++) {
                saioOffsets[i] = saioOffsets[i] + offset;
            }
            saio.setOffsets(saioOffsets);
        }
        return isoFile;
    }

    protected List<Sample> putSamples(Track track, List<Sample> samples) {
        return (List) this.track2Sample.put(track, samples);
    }

    protected FileTypeBox createFileTypeBox(Movie movie) {
        List<String> minorBrands = new LinkedList();
        minorBrands.add("isom");
        minorBrands.add("iso2");
        minorBrands.add(VisualSampleEntry.TYPE3);
        return new FileTypeBox("isom", 0, minorBrands);
    }

    protected MovieBox createMovieBox(Movie movie, Map<Track, int[]> chunks) {
        MovieBox movieBox = new MovieBox();
        MovieHeaderBox mvhd = new MovieHeaderBox();
        mvhd.setCreationTime(new Date());
        mvhd.setModificationTime(new Date());
        mvhd.setMatrix(movie.getMatrix());
        long movieTimeScale = getTimescale(movie);
        long duration = 0;
        for (Track track : movie.getTracks()) {
            long tracksDuration;
            if (track.getEdits() == null || track.getEdits().isEmpty()) {
                tracksDuration = (track.getDuration() * getTimescale(movie)) / track.getTrackMetaData().getTimescale();
            } else {
                long d = 0;
                for (Edit edit : track.getEdits()) {
                    d += (long) edit.getSegmentDuration();
                }
                tracksDuration = d * getTimescale(movie);
            }
            if (tracksDuration > duration) {
                duration = tracksDuration;
            }
        }
        mvhd.setDuration(duration);
        mvhd.setTimescale(movieTimeScale);
        long nextTrackId = 0;
        for (Track track2 : movie.getTracks()) {
            if (nextTrackId < track2.getTrackMetaData().getTrackId()) {
                nextTrackId = track2.getTrackMetaData().getTrackId();
            }
        }
        mvhd.setNextTrackId(nextTrackId + 1);
        movieBox.addBox(mvhd);
        for (Track track22 : movie.getTracks()) {
            movieBox.addBox(createTrackBox(track22, movie, chunks));
        }
        Box udta = createUdta(movie);
        if (udta != null) {
            movieBox.addBox(udta);
        }
        return movieBox;
    }

    protected Box createUdta(Movie movie) {
        return null;
    }

    protected TrackBox createTrackBox(Track track, Movie movie, Map<Track, int[]> chunks) {
        TrackBox trackBox = new TrackBox();
        TrackHeaderBox tkhd = new TrackHeaderBox();
        tkhd.setEnabled(true);
        tkhd.setInMovie(true);
        tkhd.setInPreview(true);
        tkhd.setInPoster(true);
        tkhd.setMatrix(track.getTrackMetaData().getMatrix());
        tkhd.setAlternateGroup(track.getTrackMetaData().getGroup());
        tkhd.setCreationTime(track.getTrackMetaData().getCreationTime());
        if (track.getEdits() == null || track.getEdits().isEmpty()) {
            tkhd.setDuration((track.getDuration() * getTimescale(movie)) / track.getTrackMetaData().getTimescale());
        } else {
            long d = 0;
            for (Edit edit : track.getEdits()) {
                d += (long) edit.getSegmentDuration();
            }
            tkhd.setDuration(track.getTrackMetaData().getTimescale() * d);
        }
        tkhd.setHeight(track.getTrackMetaData().getHeight());
        tkhd.setWidth(track.getTrackMetaData().getWidth());
        tkhd.setLayer(track.getTrackMetaData().getLayer());
        tkhd.setModificationTime(new Date());
        tkhd.setTrackId(track.getTrackMetaData().getTrackId());
        tkhd.setVolume(track.getTrackMetaData().getVolume());
        trackBox.addBox(tkhd);
        trackBox.addBox(createEdts(track, movie));
        MediaBox mdia = new MediaBox();
        trackBox.addBox(mdia);
        MediaHeaderBox mdhd = new MediaHeaderBox();
        mdhd.setCreationTime(track.getTrackMetaData().getCreationTime());
        mdhd.setDuration(track.getDuration());
        mdhd.setTimescale(track.getTrackMetaData().getTimescale());
        mdhd.setLanguage(track.getTrackMetaData().getLanguage());
        mdia.addBox(mdhd);
        HandlerBox hdlr = new HandlerBox();
        mdia.addBox(hdlr);
        hdlr.setHandlerType(track.getHandler());
        MediaInformationBox minf = new MediaInformationBox();
        if (track.getHandler().equals("vide")) {
            minf.addBox(new VideoMediaHeaderBox());
        } else if (track.getHandler().equals("soun")) {
            minf.addBox(new SoundMediaHeaderBox());
        } else if (track.getHandler().equals("text")) {
            minf.addBox(new NullMediaHeaderBox());
        } else if (track.getHandler().equals("subt")) {
            minf.addBox(new SubtitleMediaHeaderBox());
        } else if (track.getHandler().equals(TrackReferenceTypeBox.TYPE1)) {
            minf.addBox(new HintMediaHeaderBox());
        } else if (track.getHandler().equals("sbtl")) {
            minf.addBox(new NullMediaHeaderBox());
        }
        DataInformationBox dinf = new DataInformationBox();
        DataReferenceBox dref = new DataReferenceBox();
        dinf.addBox(dref);
        DataEntryUrlBox url = new DataEntryUrlBox();
        url.setFlags(1);
        dref.addBox(url);
        minf.addBox(dinf);
        minf.addBox(createStbl(track, movie, chunks));
        mdia.addBox(minf);
        return trackBox;
    }

    protected Box createEdts(Track track, Movie movie) {
        if (track.getEdits() == null || track.getEdits().size() <= 0) {
            return null;
        }
        EditListBox elst = new EditListBox();
        elst.setVersion(1);
        List<Entry> entries = new ArrayList();
        for (Edit edit : track.getEdits()) {
            entries.add(new Entry(elst, Math.round(edit.getSegmentDuration() * ((double) movie.getTimescale())), (edit.getMediaTime() * track.getTrackMetaData().getTimescale()) / edit.getTimeScale(), edit.getMediaRate()));
        }
        elst.setEntries(entries);
        EditBox edts = new EditBox();
        edts.addBox(elst);
        return edts;
    }

    protected Box createStbl(Track track, Movie movie, Map<Track, int[]> chunks) {
        SampleTableBox stbl = new SampleTableBox();
        createStsd(track, stbl);
        createStts(track, stbl);
        createCtts(track, stbl);
        createStss(track, stbl);
        createSdtp(track, stbl);
        createStsc(track, chunks, stbl);
        createStsz(track, stbl);
        createStco(track, movie, chunks, stbl);
        Map<String, List<GroupEntry>> groupEntryFamilies = new HashMap();
        for (Map.Entry<GroupEntry, long[]> sg : track.getSampleGroups().entrySet()) {
            String type = ((GroupEntry) sg.getKey()).getType();
            List<GroupEntry> groupEntries = (List) groupEntryFamilies.get(type);
            if (groupEntries == null) {
                groupEntries = new ArrayList();
                groupEntryFamilies.put(type, groupEntries);
            }
            groupEntries.add((GroupEntry) sg.getKey());
        }
        for (Map.Entry<String, List<GroupEntry>> sg2 : groupEntryFamilies.entrySet()) {
            SampleGroupDescriptionBox sgdb = new SampleGroupDescriptionBox();
            type = (String) sg2.getKey();
            sgdb.setGroupEntries((List) sg2.getValue());
            SampleToGroupBox sbgp = new SampleToGroupBox();
            sbgp.setGroupingType(type);
            SampleToGroupBox.Entry last = null;
            for (int i = 0; i < track.getSamples().size(); i++) {
                int index = 0;
                for (int j = 0; j < ((List) sg2.getValue()).size(); j++) {
                    if (Arrays.binarySearch((long[]) track.getSampleGroups().get((GroupEntry) ((List) sg2.getValue()).get(j)), (long) i) >= 0) {
                        index = j + 1;
                    }
                }
                if (last == null || last.getGroupDescriptionIndex() != index) {
                    last = new SampleToGroupBox.Entry(1, index);
                    sbgp.getEntries().add(last);
                } else {
                    last.setSampleCount(last.getSampleCount() + 1);
                }
            }
            stbl.addBox(sgdb);
            stbl.addBox(sbgp);
        }
        if (track instanceof CencEncryptedTrack) {
            createCencBoxes((CencEncryptedTrack) track, stbl, (int[]) chunks.get(track));
        }
        createSubs(track, stbl);
        return stbl;
    }

    protected void createSubs(Track track, SampleTableBox stbl) {
        if (track.getSubsampleInformationBox() != null) {
            stbl.addBox(track.getSubsampleInformationBox());
        }
    }

    protected void createCencBoxes(CencEncryptedTrack track, SampleTableBox stbl, int[] chunkSizes) {
        int i;
        SampleAuxiliaryInformationSizesBox saiz = new SampleAuxiliaryInformationSizesBox();
        saiz.setAuxInfoType(C0907C.CENC_TYPE_cenc);
        saiz.setFlags(1);
        List<CencSampleAuxiliaryDataFormat> sampleEncryptionEntries = track.getSampleEncryptionEntries();
        if (track.hasSubSampleEncryption()) {
            short[] sizes = new short[sampleEncryptionEntries.size()];
            for (i = 0; i < sizes.length; i++) {
                sizes[i] = (short) ((CencSampleAuxiliaryDataFormat) sampleEncryptionEntries.get(i)).getSize();
            }
            saiz.setSampleInfoSizes(sizes);
        } else {
            saiz.setDefaultSampleInfoSize(8);
            saiz.setSampleCount(track.getSamples().size());
        }
        SampleAuxiliaryInformationOffsetsBox saio = new SampleAuxiliaryInformationOffsetsBox();
        SampleEncryptionBox senc = new SampleEncryptionBox();
        senc.setSubSampleEncryption(track.hasSubSampleEncryption());
        senc.setEntries(sampleEncryptionEntries);
        long offset = (long) senc.getOffsetToFirstIV();
        int index = 0;
        long[] offsets = new long[chunkSizes.length];
        for (i = 0; i < chunkSizes.length; i++) {
            offsets[i] = offset;
            int j = 0;
            while (j < chunkSizes[i]) {
                offset += (long) ((CencSampleAuxiliaryDataFormat) sampleEncryptionEntries.get(index)).getSize();
                j++;
                index++;
            }
        }
        saio.setOffsets(offsets);
        stbl.addBox(saiz);
        stbl.addBox(saio);
        stbl.addBox(senc);
        this.sampleAuxiliaryInformationOffsetsBoxes.add(saio);
    }

    protected void createStsd(Track track, SampleTableBox stbl) {
        stbl.addBox(track.getSampleDescriptionBox());
    }

    protected void createStco(Track track, Movie movie, Map<Track, int[]> chunks, SampleTableBox stbl) {
        int[] tracksChunkSizes = (int[]) chunks.get(track);
        StaticChunkOffsetBox stco = new StaticChunkOffsetBox();
        this.chunkOffsetBoxes.add(stco);
        long offset = 0;
        long[] chunkOffset = new long[tracksChunkSizes.length];
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("Calculating chunk offsets for track_" + track.getTrackMetaData().getTrackId());
        }
        for (int i = 0; i < tracksChunkSizes.length; i++) {
            if (LOG.isLoggable(Level.FINER)) {
                LOG.finer("Calculating chunk offsets for track_" + track.getTrackMetaData().getTrackId() + " chunk " + i);
            }
            for (Track current : movie.getTracks()) {
                int j;
                if (LOG.isLoggable(Level.FINEST)) {
                    LOG.finest("Adding offsets of track_" + current.getTrackMetaData().getTrackId());
                }
                int[] chunkSizes = (int[]) chunks.get(current);
                long firstSampleOfChunk = 0;
                for (j = 0; j < i; j++) {
                    firstSampleOfChunk += (long) chunkSizes[j];
                }
                if (current == track) {
                    chunkOffset[i] = offset;
                }
                for (j = CastUtils.l2i(firstSampleOfChunk); ((long) j) < ((long) chunkSizes[i]) + firstSampleOfChunk; j++) {
                    offset += ((long[]) this.track2SampleSizes.get(current))[j];
                }
            }
        }
        stco.setChunkOffsets(chunkOffset);
        stbl.addBox(stco);
    }

    protected void createStsz(Track track, SampleTableBox stbl) {
        SampleSizeBox stsz = new SampleSizeBox();
        stsz.setSampleSizes((long[]) this.track2SampleSizes.get(track));
        stbl.addBox(stsz);
    }

    protected void createStsc(Track track, Map<Track, int[]> chunks, SampleTableBox stbl) {
        int[] tracksChunkSizes = (int[]) chunks.get(track);
        SampleToChunkBox stsc = new SampleToChunkBox();
        stsc.setEntries(new LinkedList());
        long lastChunkSize = -2147483648L;
        for (int i = 0; i < tracksChunkSizes.length; i++) {
            if (lastChunkSize != ((long) tracksChunkSizes[i])) {
                stsc.getEntries().add(new SampleToChunkBox.Entry((long) (i + 1), (long) tracksChunkSizes[i], 1));
                lastChunkSize = (long) tracksChunkSizes[i];
            }
        }
        stbl.addBox(stsc);
    }

    protected void createSdtp(Track track, SampleTableBox stbl) {
        if (track.getSampleDependencies() != null && !track.getSampleDependencies().isEmpty()) {
            SampleDependencyTypeBox sdtp = new SampleDependencyTypeBox();
            sdtp.setEntries(track.getSampleDependencies());
            stbl.addBox(sdtp);
        }
    }

    protected void createStss(Track track, SampleTableBox stbl) {
        long[] syncSamples = track.getSyncSamples();
        if (syncSamples != null && syncSamples.length > 0) {
            SyncSampleBox stss = new SyncSampleBox();
            stss.setSampleNumber(syncSamples);
            stbl.addBox(stss);
        }
    }

    protected void createCtts(Track track, SampleTableBox stbl) {
        List<CompositionTimeToSample.Entry> compositionTimeToSampleEntries = track.getCompositionTimeEntries();
        if (compositionTimeToSampleEntries != null && !compositionTimeToSampleEntries.isEmpty()) {
            CompositionTimeToSample ctts = new CompositionTimeToSample();
            ctts.setEntries(compositionTimeToSampleEntries);
            stbl.addBox(ctts);
        }
    }

    protected void createStts(Track track, SampleTableBox stbl) {
        TimeToSampleBox.Entry lastEntry = null;
        List<TimeToSampleBox.Entry> entries = new ArrayList();
        for (long delta : track.getSampleDurations()) {
            if (lastEntry == null || lastEntry.getDelta() != delta) {
                lastEntry = new TimeToSampleBox.Entry(1, delta);
                entries.add(lastEntry);
            } else {
                lastEntry.setCount(lastEntry.getCount() + 1);
            }
        }
        TimeToSampleBox stts = new TimeToSampleBox();
        stts.setEntries(entries);
        stbl.addBox(stts);
    }

    int[] getChunkSizes(Track track, Movie movie) {
        long[] referenceChunkStarts = this.intersectionFinder.sampleNumbers(track);
        int[] chunkSizes = new int[referenceChunkStarts.length];
        for (int i = 0; i < referenceChunkStarts.length; i++) {
            long end;
            long start = referenceChunkStarts[i] - 1;
            if (referenceChunkStarts.length == i + 1) {
                end = (long) track.getSamples().size();
            } else {
                end = referenceChunkStarts[i + 1] - 1;
            }
            chunkSizes[i] = CastUtils.l2i(end - start);
        }
        if ($assertionsDisabled || ((long) ((List) this.track2Sample.get(track)).size()) == sum(chunkSizes)) {
            return chunkSizes;
        }
        throw new AssertionError("The number of samples and the sum of all chunk lengths must be equal");
    }

    public long getTimescale(Movie movie) {
        long timescale = ((Track) movie.getTracks().iterator().next()).getTrackMetaData().getTimescale();
        for (Track track : movie.getTracks()) {
            timescale = gcd(track.getTrackMetaData().getTimescale(), timescale);
        }
        return timescale;
    }
}
