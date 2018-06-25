package com.googlecode.mp4parser.authoring.samples;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox.Entry;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentedMp4SampleList extends AbstractList<Sample> {
    private List<TrackFragmentBox> allTrafs;
    private int[] firstSamples;
    IsoFile[] fragments;
    private SoftReference<Sample>[] sampleCache;
    private int size_ = -1;
    Container topLevel;
    TrackBox trackBox = null;
    TrackExtendsBox trex = null;
    private Map<TrackRunBox, SoftReference<ByteBuffer>> trunDataCache = new HashMap();

    public FragmentedMp4SampleList(long track, Container topLevel, IsoFile... fragments) {
        this.topLevel = topLevel;
        this.fragments = fragments;
        for (TrackBox tb : Path.getPaths(topLevel, "moov[0]/trak")) {
            if (tb.getTrackHeaderBox().getTrackId() == track) {
                this.trackBox = tb;
            }
        }
        if (this.trackBox == null) {
            throw new RuntimeException("This MP4 does not contain track " + track);
        }
        for (TrackExtendsBox box : Path.getPaths(topLevel, "moov[0]/mvex[0]/trex")) {
            if (box.getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                this.trex = box;
            }
        }
        this.sampleCache = (SoftReference[]) Array.newInstance(SoftReference.class, size());
        initAllFragments();
    }

    private List<TrackFragmentBox> initAllFragments() {
        if (this.allTrafs != null) {
            return this.allTrafs;
        }
        List<TrackFragmentBox> trafs = new ArrayList();
        for (MovieFragmentBox moof : this.topLevel.getBoxes(MovieFragmentBox.class)) {
            for (TrackFragmentBox trackFragmentBox : moof.getBoxes(TrackFragmentBox.class)) {
                if (trackFragmentBox.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                    trafs.add(trackFragmentBox);
                }
            }
        }
        if (this.fragments != null) {
            for (IsoFile fragment : this.fragments) {
                for (MovieFragmentBox moof2 : fragment.getBoxes(MovieFragmentBox.class)) {
                    for (TrackFragmentBox trackFragmentBox2 : moof2.getBoxes(TrackFragmentBox.class)) {
                        if (trackFragmentBox2.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                            trafs.add(trackFragmentBox2);
                        }
                    }
                }
            }
        }
        this.allTrafs = trafs;
        int firstSample = 1;
        this.firstSamples = new int[this.allTrafs.size()];
        for (int i = 0; i < this.allTrafs.size(); i++) {
            this.firstSamples[i] = firstSample;
            firstSample += getTrafSize((TrackFragmentBox) this.allTrafs.get(i));
        }
        return trafs;
    }

    private int getTrafSize(TrackFragmentBox traf) {
        List<Box> boxes = traf.getBoxes();
        int size = 0;
        for (int i = 0; i < boxes.size(); i++) {
            Box b = (Box) boxes.get(i);
            if (b instanceof TrackRunBox) {
                size += CastUtils.l2i(((TrackRunBox) b).getSampleCount());
            }
        }
        return size;
    }

    public Sample get(int index) {
        if (this.sampleCache[index] != null) {
            Sample cachedSample = (Sample) this.sampleCache[index].get();
            if (cachedSample != null) {
                return cachedSample;
            }
        }
        int targetIndex = index + 1;
        int j = this.firstSamples.length - 1;
        while (targetIndex - this.firstSamples[j] < 0) {
            j--;
        }
        TrackFragmentBox trackFragmentBox = (TrackFragmentBox) this.allTrafs.get(j);
        int sampleIndexWithInTraf = targetIndex - this.firstSamples[j];
        int previousTrunsSize = 0;
        MovieFragmentBox moof = (MovieFragmentBox) trackFragmentBox.getParent();
        for (Box box : trackFragmentBox.getBoxes()) {
            if (box instanceof TrackRunBox) {
                TrackRunBox trun = (TrackRunBox) box;
                if (trun.getEntries().size() < sampleIndexWithInTraf - previousTrunsSize) {
                    previousTrunsSize += trun.getEntries().size();
                } else {
                    long sampleSize;
                    List<Entry> trackRunEntries = trun.getEntries();
                    TrackFragmentHeaderBox tfhd = trackFragmentBox.getTrackFragmentHeaderBox();
                    boolean sampleSizePresent = trun.isSampleSizePresent();
                    boolean hasDefaultSampleSize = tfhd.hasDefaultSampleSize();
                    long defaultSampleSize = 0;
                    if (!sampleSizePresent) {
                        if (hasDefaultSampleSize) {
                            defaultSampleSize = tfhd.getDefaultSampleSize();
                        } else if (this.trex == null) {
                            throw new RuntimeException("File doesn't contain trex box but track fragments aren't fully self contained. Cannot determine sample size.");
                        } else {
                            defaultSampleSize = this.trex.getDefaultSampleSize();
                        }
                    }
                    SoftReference<ByteBuffer> trunDataRef = (SoftReference) this.trunDataCache.get(trun);
                    ByteBuffer trunData = trunDataRef != null ? (ByteBuffer) trunDataRef.get() : null;
                    if (trunData == null) {
                        Container base;
                        long offset = 0;
                        if (tfhd.hasBaseDataOffset()) {
                            offset = 0 + tfhd.getBaseDataOffset();
                            base = moof.getParent();
                        } else {
                            Object base2 = moof;
                        }
                        if (trun.isDataOffsetPresent()) {
                            offset += (long) trun.getDataOffset();
                        }
                        int size = 0;
                        for (Entry e : trackRunEntries) {
                            if (sampleSizePresent) {
                                size = (int) (((long) size) + e.getSampleSize());
                            } else {
                                size = (int) (((long) size) + defaultSampleSize);
                            }
                        }
                        try {
                            trunData = base.getByteBuffer(offset, (long) size);
                            this.trunDataCache.put(trun, new SoftReference(trunData));
                        } catch (IOException e2) {
                            throw new RuntimeException(e2);
                        }
                    }
                    int offset2 = 0;
                    for (int i = 0; i < sampleIndexWithInTraf - previousTrunsSize; i++) {
                        if (sampleSizePresent) {
                            offset2 = (int) (((long) offset2) + ((Entry) trackRunEntries.get(i)).getSampleSize());
                        } else {
                            offset2 = (int) (((long) offset2) + defaultSampleSize);
                        }
                    }
                    if (sampleSizePresent) {
                        sampleSize = ((Entry) trackRunEntries.get(sampleIndexWithInTraf - previousTrunsSize)).getSampleSize();
                    } else {
                        sampleSize = defaultSampleSize;
                    }
                    final ByteBuffer finalTrunData = trunData;
                    final int finalOffset = offset2;
                    Sample sample = new Sample() {
                        public void writeTo(WritableByteChannel channel) throws IOException {
                            channel.write(asByteBuffer());
                        }

                        public long getSize() {
                            return sampleSize;
                        }

                        public ByteBuffer asByteBuffer() {
                            return (ByteBuffer) ((ByteBuffer) finalTrunData.position(finalOffset)).slice().limit(CastUtils.l2i(sampleSize));
                        }
                    };
                    this.sampleCache[index] = new SoftReference(sample);
                    return sample;
                }
            }
        }
        throw new RuntimeException("Couldn't find sample in the traf I was looking");
    }

    public int size() {
        if (this.size_ != -1) {
            return this.size_;
        }
        int i = 0;
        for (MovieFragmentBox moof : this.topLevel.getBoxes(MovieFragmentBox.class)) {
            for (TrackFragmentBox trackFragmentBox : moof.getBoxes(TrackFragmentBox.class)) {
                if (trackFragmentBox.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                    i = (int) (((long) i) + ((TrackRunBox) trackFragmentBox.getBoxes(TrackRunBox.class).get(0)).getSampleCount());
                }
            }
        }
        for (IsoFile fragment : this.fragments) {
            for (MovieFragmentBox moof2 : fragment.getBoxes(MovieFragmentBox.class)) {
                for (TrackFragmentBox trackFragmentBox2 : moof2.getBoxes(TrackFragmentBox.class)) {
                    if (trackFragmentBox2.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                        i = (int) (((long) i) + ((TrackRunBox) trackFragmentBox2.getBoxes(TrackRunBox.class).get(0)).getSampleCount());
                    }
                }
            }
        }
        this.size_ = i;
        return i;
    }
}
