package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.DataEntryUrlBox;
import com.coremedia.iso.boxes.DataInformationBox;
import com.coremedia.iso.boxes.DataReferenceBox;
import com.coremedia.iso.boxes.FileTypeBox;
import com.coremedia.iso.boxes.HandlerBox;
import com.coremedia.iso.boxes.MediaBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MediaInformationBox;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.StaticChunkOffsetBox;
import com.coremedia.iso.boxes.SyncSampleBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.mdat.MediaDataBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.util.Matrix;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MP4Builder {
    private Mp4Movie currentMp4Movie = null;
    private long dataOffset = 0;
    private FileChannel fc = null;
    private FileOutputStream fos = null;
    private InterleaveChunkMdat mdat = null;
    private ByteBuffer sizeBuffer = null;
    private HashMap<Track, long[]> track2SampleSizes = new HashMap();
    private boolean writeNewMdat = true;
    private long writedSinceLastMdat = 0;

    private class InterleaveChunkMdat implements Box {
        private long contentSize;
        private long dataOffset;
        private Container parent;

        private InterleaveChunkMdat() {
            this.contentSize = 1073741824;
            this.dataOffset = 0;
        }

        public Container getParent() {
            return this.parent;
        }

        public long getOffset() {
            return this.dataOffset;
        }

        public void setDataOffset(long offset) {
            this.dataOffset = offset;
        }

        public void setParent(Container parent) {
            this.parent = parent;
        }

        public void setContentSize(long contentSize) {
            this.contentSize = contentSize;
        }

        public long getContentSize() {
            return this.contentSize;
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

        public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
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
        }
    }

    public MP4Builder createMovie(Mp4Movie mp4Movie) throws Exception {
        this.currentMp4Movie = mp4Movie;
        this.fos = new FileOutputStream(mp4Movie.getCacheFile());
        this.fc = this.fos.getChannel();
        FileTypeBox fileTypeBox = createFileTypeBox();
        fileTypeBox.getBox(this.fc);
        this.dataOffset += fileTypeBox.getSize();
        this.writedSinceLastMdat += this.dataOffset;
        this.mdat = new InterleaveChunkMdat();
        this.sizeBuffer = ByteBuffer.allocateDirect(4);
        return this;
    }

    private void flushCurrentMdat() throws Exception {
        long oldPosition = this.fc.position();
        this.fc.position(this.mdat.getOffset());
        this.mdat.getBox(this.fc);
        this.fc.position(oldPosition);
        this.mdat.setDataOffset(0);
        this.mdat.setContentSize(0);
        this.fos.flush();
    }

    public boolean writeSampleData(int trackIndex, ByteBuffer byteBuf, BufferInfo bufferInfo, boolean writeLength) throws Exception {
        if (this.writeNewMdat) {
            this.mdat.setContentSize(0);
            this.mdat.getBox(this.fc);
            this.mdat.setDataOffset(this.dataOffset);
            this.dataOffset += 16;
            this.writedSinceLastMdat += 16;
            this.writeNewMdat = false;
        }
        this.mdat.setContentSize(this.mdat.getContentSize() + ((long) bufferInfo.size));
        this.writedSinceLastMdat += (long) bufferInfo.size;
        boolean flush = false;
        if (this.writedSinceLastMdat >= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID) {
            flushCurrentMdat();
            this.writeNewMdat = true;
            flush = true;
            this.writedSinceLastMdat -= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        this.currentMp4Movie.addSample(trackIndex, this.dataOffset, bufferInfo);
        byteBuf.position((!writeLength ? 0 : 4) + bufferInfo.offset);
        byteBuf.limit(bufferInfo.offset + bufferInfo.size);
        if (writeLength) {
            this.sizeBuffer.position(0);
            this.sizeBuffer.putInt(bufferInfo.size - 4);
            this.sizeBuffer.position(0);
            this.fc.write(this.sizeBuffer);
        }
        this.fc.write(byteBuf);
        this.dataOffset += (long) bufferInfo.size;
        if (flush) {
            this.fos.flush();
        }
        return flush;
    }

    public int addTrack(MediaFormat mediaFormat, boolean isAudio) {
        return this.currentMp4Movie.addTrack(mediaFormat, isAudio);
    }

    public void finishMovie() throws Exception {
        if (this.mdat.getContentSize() != 0) {
            flushCurrentMdat();
        }
        Iterator it = this.currentMp4Movie.getTracks().iterator();
        while (it.hasNext()) {
            Track track = (Track) it.next();
            List<Sample> samples = track.getSamples();
            long[] sizes = new long[samples.size()];
            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = ((Sample) samples.get(i)).getSize();
            }
            this.track2SampleSizes.put(track, sizes);
        }
        createMovieBox(this.currentMp4Movie).getBox(this.fc);
        this.fos.flush();
        this.fc.close();
        this.fos.close();
    }

    protected FileTypeBox createFileTypeBox() {
        LinkedList<String> minorBrands = new LinkedList();
        minorBrands.add("isom");
        minorBrands.add("iso2");
        minorBrands.add(VisualSampleEntry.TYPE3);
        minorBrands.add("mp41");
        return new FileTypeBox("isom", 512, minorBrands);
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public long getTimescale(Mp4Movie mp4Movie) {
        long timescale = 0;
        if (!mp4Movie.getTracks().isEmpty()) {
            timescale = (long) ((Track) mp4Movie.getTracks().iterator().next()).getTimeScale();
        }
        Iterator it = mp4Movie.getTracks().iterator();
        while (it.hasNext()) {
            timescale = gcd((long) ((Track) it.next()).getTimeScale(), timescale);
        }
        return timescale;
    }

    protected MovieBox createMovieBox(Mp4Movie movie) {
        MovieBox movieBox = new MovieBox();
        MovieHeaderBox mvhd = new MovieHeaderBox();
        mvhd.setCreationTime(new Date());
        mvhd.setModificationTime(new Date());
        mvhd.setMatrix(Matrix.ROTATE_0);
        long movieTimeScale = getTimescale(movie);
        long duration = 0;
        Iterator it = movie.getTracks().iterator();
        while (it.hasNext()) {
            Track track = (Track) it.next();
            track.prepare();
            long tracksDuration = (track.getDuration() * movieTimeScale) / ((long) track.getTimeScale());
            if (tracksDuration > duration) {
                duration = tracksDuration;
            }
        }
        mvhd.setDuration(duration);
        mvhd.setTimescale(movieTimeScale);
        mvhd.setNextTrackId((long) (movie.getTracks().size() + 1));
        movieBox.addBox(mvhd);
        it = movie.getTracks().iterator();
        while (it.hasNext()) {
            movieBox.addBox(createTrackBox((Track) it.next(), movie));
        }
        return movieBox;
    }

    protected TrackBox createTrackBox(Track track, Mp4Movie movie) {
        TrackBox trackBox = new TrackBox();
        TrackHeaderBox tkhd = new TrackHeaderBox();
        tkhd.setEnabled(true);
        tkhd.setInMovie(true);
        tkhd.setInPreview(true);
        if (track.isAudio()) {
            tkhd.setMatrix(Matrix.ROTATE_0);
        } else {
            tkhd.setMatrix(movie.getMatrix());
        }
        tkhd.setAlternateGroup(0);
        tkhd.setCreationTime(track.getCreationTime());
        tkhd.setDuration((track.getDuration() * getTimescale(movie)) / ((long) track.getTimeScale()));
        tkhd.setHeight((double) track.getHeight());
        tkhd.setWidth((double) track.getWidth());
        tkhd.setLayer(0);
        tkhd.setModificationTime(new Date());
        tkhd.setTrackId(track.getTrackId() + 1);
        tkhd.setVolume(track.getVolume());
        trackBox.addBox(tkhd);
        MediaBox mdia = new MediaBox();
        trackBox.addBox(mdia);
        MediaHeaderBox mdhd = new MediaHeaderBox();
        mdhd.setCreationTime(track.getCreationTime());
        mdhd.setDuration(track.getDuration());
        mdhd.setTimescale((long) track.getTimeScale());
        mdhd.setLanguage("eng");
        mdia.addBox(mdhd);
        HandlerBox hdlr = new HandlerBox();
        hdlr.setName(track.isAudio() ? "SoundHandle" : "VideoHandle");
        hdlr.setHandlerType(track.getHandler());
        mdia.addBox(hdlr);
        MediaInformationBox minf = new MediaInformationBox();
        minf.addBox(track.getMediaHeaderBox());
        DataInformationBox dinf = new DataInformationBox();
        DataReferenceBox dref = new DataReferenceBox();
        dinf.addBox(dref);
        DataEntryUrlBox url = new DataEntryUrlBox();
        url.setFlags(1);
        dref.addBox(url);
        minf.addBox(dinf);
        minf.addBox(createStbl(track));
        mdia.addBox(minf);
        return trackBox;
    }

    protected Box createStbl(Track track) {
        SampleTableBox stbl = new SampleTableBox();
        createStsd(track, stbl);
        createStts(track, stbl);
        createCtts(track, stbl);
        createStss(track, stbl);
        createStsc(track, stbl);
        createStsz(track, stbl);
        createStco(track, stbl);
        return stbl;
    }

    protected void createStsd(Track track, SampleTableBox stbl) {
        stbl.addBox(track.getSampleDescriptionBox());
    }

    protected void createCtts(Track track, SampleTableBox stbl) {
        int[] sampleCompositions = track.getSampleCompositions();
        if (sampleCompositions != null) {
            Entry lastEntry = null;
            List<Entry> entries = new ArrayList();
            for (int offset : sampleCompositions) {
                if (lastEntry == null || lastEntry.getOffset() != offset) {
                    lastEntry = new Entry(1, offset);
                    entries.add(lastEntry);
                } else {
                    lastEntry.setCount(lastEntry.getCount() + 1);
                }
            }
            CompositionTimeToSample ctts = new CompositionTimeToSample();
            ctts.setEntries(entries);
            stbl.addBox(ctts);
        }
    }

    protected void createStts(Track track, SampleTableBox stbl) {
        TimeToSampleBox.Entry lastEntry = null;
        List<TimeToSampleBox.Entry> entries = new ArrayList();
        long[] deltas = track.getSampleDurations();
        for (long delta : deltas) {
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

    protected void createStss(Track track, SampleTableBox stbl) {
        long[] syncSamples = track.getSyncSamples();
        if (syncSamples != null && syncSamples.length > 0) {
            SyncSampleBox stss = new SyncSampleBox();
            stss.setSampleNumber(syncSamples);
            stbl.addBox(stss);
        }
    }

    protected void createStsc(Track track, SampleTableBox stbl) {
        SampleToChunkBox stsc = new SampleToChunkBox();
        stsc.setEntries(new LinkedList());
        int lastChunkNumber = 1;
        int lastSampleCount = 0;
        int previousWritedChunkCount = -1;
        int samplesCount = track.getSamples().size();
        for (int a = 0; a < samplesCount; a++) {
            Sample sample = (Sample) track.getSamples().get(a);
            long lastOffset = sample.getOffset() + sample.getSize();
            lastSampleCount++;
            boolean write = false;
            if (a == samplesCount - 1) {
                write = true;
            } else if (lastOffset != ((Sample) track.getSamples().get(a + 1)).getOffset()) {
                write = true;
            }
            if (write) {
                if (previousWritedChunkCount != lastSampleCount) {
                    stsc.getEntries().add(new SampleToChunkBox.Entry((long) lastChunkNumber, (long) lastSampleCount, 1));
                    previousWritedChunkCount = lastSampleCount;
                }
                lastSampleCount = 0;
                lastChunkNumber++;
            }
        }
        stbl.addBox(stsc);
    }

    protected void createStsz(Track track, SampleTableBox stbl) {
        SampleSizeBox stsz = new SampleSizeBox();
        stsz.setSampleSizes((long[]) this.track2SampleSizes.get(track));
        stbl.addBox(stsz);
    }

    protected void createStco(Track track, SampleTableBox stbl) {
        ArrayList<Long> chunksOffsets = new ArrayList();
        long lastOffset = -1;
        Iterator it = track.getSamples().iterator();
        while (it.hasNext()) {
            Sample sample = (Sample) it.next();
            long offset = sample.getOffset();
            if (!(lastOffset == -1 || lastOffset == offset)) {
                lastOffset = -1;
            }
            if (lastOffset == -1) {
                chunksOffsets.add(Long.valueOf(offset));
            }
            lastOffset = offset + sample.getSize();
        }
        long[] chunkOffsetsLong = new long[chunksOffsets.size()];
        for (int a = 0; a < chunksOffsets.size(); a++) {
            chunkOffsetsLong[a] = ((Long) chunksOffsets.get(a)).longValue();
        }
        StaticChunkOffsetBox stco = new StaticChunkOffsetBox();
        stco.setChunkOffsets(chunkOffsetsLong);
        stbl.addBox(stco);
    }
}
