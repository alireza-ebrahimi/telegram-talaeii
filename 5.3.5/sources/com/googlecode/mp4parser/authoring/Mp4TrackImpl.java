package com.googlecode.mp4parser.authoring;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.SubSampleInformationBox.SubSampleEntry;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.SampleFlags;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.coremedia.iso.boxes.mdat.SampleList;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.BasicContainer;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Mp4TrackImpl extends AbstractTrack {
    private List<Entry> compositionTimeEntries;
    private long[] decodingTimes;
    IsoFile[] fragments;
    private String handler;
    private List<SampleDependencyTypeBox.Entry> sampleDependencies;
    private SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    private SubSampleInformationBox subSampleInformationBox = null;
    private long[] syncSamples = new long[0];
    TrackBox trackBox;
    private TrackMetaData trackMetaData = new TrackMetaData();

    public Mp4TrackImpl(String name, TrackBox trackBox, IsoFile... fragments) {
        super(name);
        long trackId = trackBox.getTrackHeaderBox().getTrackId();
        this.samples = new SampleList(trackBox, fragments);
        AbstractContainerBox stbl = trackBox.getMediaBox().getMediaInformationBox().getSampleTableBox();
        this.handler = trackBox.getMediaBox().getHandlerBox().getHandlerType();
        List<TimeToSampleBox.Entry> decodingTimeEntries = new ArrayList();
        this.compositionTimeEntries = new ArrayList();
        this.sampleDependencies = new ArrayList();
        decodingTimeEntries.addAll(stbl.getTimeToSampleBox().getEntries());
        if (stbl.getCompositionTimeToSample() != null) {
            this.compositionTimeEntries.addAll(stbl.getCompositionTimeToSample().getEntries());
        }
        if (stbl.getSampleDependencyTypeBox() != null) {
            this.sampleDependencies.addAll(stbl.getSampleDependencyTypeBox().getEntries());
        }
        if (stbl.getSyncSampleBox() != null) {
            this.syncSamples = stbl.getSyncSampleBox().getSampleNumber();
        }
        this.subSampleInformationBox = (SubSampleInformationBox) Path.getPath(stbl, SubSampleInformationBox.TYPE);
        List<MovieFragmentBox> movieFragmentBoxes = new ArrayList();
        movieFragmentBoxes.addAll(((Box) trackBox.getParent()).getParent().getBoxes(MovieFragmentBox.class));
        for (IsoFile boxes : fragments) {
            movieFragmentBoxes.addAll(boxes.getBoxes(MovieFragmentBox.class));
        }
        this.sampleDescriptionBox = stbl.getSampleDescriptionBox();
        List<MovieExtendsBox> movieExtendsBoxes = trackBox.getParent().getBoxes(MovieExtendsBox.class);
        if (movieExtendsBoxes.size() > 0) {
            for (MovieExtendsBox boxes2 : movieExtendsBoxes) {
                for (TrackExtendsBox trex : boxes2.getBoxes(TrackExtendsBox.class)) {
                    if (trex.getTrackId() == trackId) {
                        if (Path.getPaths(((Box) trackBox.getParent()).getParent(), "/moof/traf/subs").size() > 0) {
                            this.subSampleInformationBox = new SubSampleInformationBox();
                        }
                        List<Long> syncSampleList = new LinkedList();
                        long sampleNumber = 1;
                        for (MovieFragmentBox boxes3 : movieFragmentBoxes) {
                            for (AbstractContainerBox traf : boxes3.getBoxes(TrackFragmentBox.class)) {
                                if (traf.getTrackFragmentHeaderBox().getTrackId() == trackId) {
                                    SubSampleInformationBox subs = (SubSampleInformationBox) Path.getPath(traf, SubSampleInformationBox.TYPE);
                                    if (subs != null) {
                                        long difFromLastFragment = (sampleNumber - ((long) 0)) - 1;
                                        for (SubSampleEntry subSampleEntry : subs.getEntries()) {
                                            SubSampleEntry se = new SubSampleEntry();
                                            se.getSubsampleEntries().addAll(subSampleEntry.getSubsampleEntries());
                                            if (difFromLastFragment != 0) {
                                                se.setSampleDelta(subSampleEntry.getSampleDelta() + difFromLastFragment);
                                                difFromLastFragment = 0;
                                            } else {
                                                se.setSampleDelta(subSampleEntry.getSampleDelta());
                                            }
                                            this.subSampleInformationBox.getEntries().add(se);
                                        }
                                    }
                                    for (TrackRunBox trun : traf.getBoxes(TrackRunBox.class)) {
                                        TrackFragmentHeaderBox tfhd = ((TrackFragmentBox) trun.getParent()).getTrackFragmentHeaderBox();
                                        boolean first = true;
                                        for (TrackRunBox.Entry entry : trun.getEntries()) {
                                            SampleFlags sampleFlags;
                                            if (trun.isSampleDurationPresent()) {
                                                if (decodingTimeEntries.size() == 0 || ((TimeToSampleBox.Entry) decodingTimeEntries.get(decodingTimeEntries.size() - 1)).getDelta() != entry.getSampleDuration()) {
                                                    decodingTimeEntries.add(new TimeToSampleBox.Entry(1, entry.getSampleDuration()));
                                                } else {
                                                    TimeToSampleBox.Entry e = (TimeToSampleBox.Entry) decodingTimeEntries.get(decodingTimeEntries.size() - 1);
                                                    e.setCount(e.getCount() + 1);
                                                }
                                            } else if (tfhd.hasDefaultSampleDuration()) {
                                                decodingTimeEntries.add(new TimeToSampleBox.Entry(1, tfhd.getDefaultSampleDuration()));
                                            } else {
                                                decodingTimeEntries.add(new TimeToSampleBox.Entry(1, trex.getDefaultSampleDuration()));
                                            }
                                            if (trun.isSampleCompositionTimeOffsetPresent()) {
                                                if (this.compositionTimeEntries.size() == 0 || ((long) ((Entry) this.compositionTimeEntries.get(this.compositionTimeEntries.size() - 1)).getOffset()) != entry.getSampleCompositionTimeOffset()) {
                                                    this.compositionTimeEntries.add(new Entry(1, CastUtils.l2i(entry.getSampleCompositionTimeOffset())));
                                                } else {
                                                    Entry e2 = (Entry) this.compositionTimeEntries.get(this.compositionTimeEntries.size() - 1);
                                                    e2.setCount(e2.getCount() + 1);
                                                }
                                            }
                                            if (trun.isSampleFlagsPresent()) {
                                                sampleFlags = entry.getSampleFlags();
                                            } else if (first && trun.isFirstSampleFlagsPresent()) {
                                                sampleFlags = trun.getFirstSampleFlags();
                                            } else if (tfhd.hasDefaultSampleFlags()) {
                                                sampleFlags = tfhd.getDefaultSampleFlags();
                                            } else {
                                                sampleFlags = trex.getDefaultSampleFlags();
                                            }
                                            if (!(sampleFlags == null || sampleFlags.isSampleIsDifferenceSample())) {
                                                syncSampleList.add(Long.valueOf(sampleNumber));
                                            }
                                            sampleNumber++;
                                            first = false;
                                        }
                                    }
                                }
                            }
                        }
                        Object oldSS = this.syncSamples;
                        this.syncSamples = new long[(this.syncSamples.length + syncSampleList.size())];
                        System.arraycopy(oldSS, 0, this.syncSamples, 0, oldSS.length);
                        int i = oldSS.length;
                        for (Long syncSampleNumber : syncSampleList) {
                            int i2 = i + 1;
                            this.syncSamples[i] = syncSampleNumber.longValue();
                            i = i2;
                        }
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList = new ArrayList();
            for (MovieFragmentBox boxes4 : movieFragmentBoxes) {
                for (TrackFragmentBox traf2 : boxes4.getBoxes(TrackFragmentBox.class)) {
                    if (traf2.getTrackFragmentHeaderBox().getTrackId() == trackId) {
                        this.sampleGroups = getSampleGroups(Path.getPaths((Container) traf2, SampleGroupDescriptionBox.TYPE), Path.getPaths((Container) traf2, SampleToGroupBox.TYPE), this.sampleGroups);
                    }
                }
            }
        } else {
            this.sampleGroups = getSampleGroups(stbl.getBoxes(SampleGroupDescriptionBox.class), stbl.getBoxes(SampleToGroupBox.class), this.sampleGroups);
        }
        this.decodingTimes = TimeToSampleBox.blowupTimeToSamples(decodingTimeEntries);
        MediaHeaderBox mdhd = trackBox.getMediaBox().getMediaHeaderBox();
        TrackHeaderBox tkhd = trackBox.getTrackHeaderBox();
        this.trackMetaData.setTrackId(tkhd.getTrackId());
        this.trackMetaData.setCreationTime(mdhd.getCreationTime());
        this.trackMetaData.setLanguage(mdhd.getLanguage());
        this.trackMetaData.setModificationTime(mdhd.getModificationTime());
        this.trackMetaData.setTimescale(mdhd.getTimescale());
        this.trackMetaData.setHeight(tkhd.getHeight());
        this.trackMetaData.setWidth(tkhd.getWidth());
        this.trackMetaData.setLayer(tkhd.getLayer());
        this.trackMetaData.setMatrix(tkhd.getMatrix());
        EditListBox elst = (EditListBox) Path.getPath((AbstractContainerBox) trackBox, "edts/elst");
        MovieHeaderBox mvhd = (MovieHeaderBox) Path.getPath((AbstractContainerBox) trackBox, "../mvhd");
        if (elst != null) {
            for (EditListBox.Entry e3 : elst.getEntries()) {
                this.edits.add(new Edit(e3.getMediaTime(), mdhd.getTimescale(), e3.getMediaRate(), ((double) e3.getSegmentDuration()) / ((double) mvhd.getTimescale())));
            }
        }
    }

    private Map<GroupEntry, long[]> getSampleGroups(List<SampleGroupDescriptionBox> sgdbs, List<SampleToGroupBox> sbgps, Map<GroupEntry, long[]> sampleGroups) {
        for (SampleGroupDescriptionBox sgdb : sgdbs) {
            boolean found = false;
            for (SampleToGroupBox sbgp : sbgps) {
                if (sbgp.getGroupingType().equals(((GroupEntry) sgdb.getGroupEntries().get(0)).getType())) {
                    found = true;
                    int sampleNum = 0;
                    for (SampleToGroupBox.Entry entry : sbgp.getEntries()) {
                        if (entry.getGroupDescriptionIndex() > 0) {
                            GroupEntry groupEntry = (GroupEntry) sgdb.getGroupEntries().get(entry.getGroupDescriptionIndex() - 1);
                            long[] samples = (long[]) sampleGroups.get(groupEntry);
                            if (samples == null) {
                                samples = new long[0];
                            }
                            long[] nuSamples = new long[(CastUtils.l2i(entry.getSampleCount()) + samples.length)];
                            System.arraycopy(samples, 0, nuSamples, 0, samples.length);
                            for (int i = 0; ((long) i) < entry.getSampleCount(); i++) {
                                nuSamples[samples.length + i] = (long) (sampleNum + i);
                            }
                            sampleGroups.put(groupEntry, nuSamples);
                        }
                        sampleNum = (int) (((long) sampleNum) + entry.getSampleCount());
                    }
                }
            }
            if (!found) {
                throw new RuntimeException("Could not find SampleToGroupBox for " + ((GroupEntry) sgdb.getGroupEntries().get(0)).getType() + ".");
            }
        }
        return sampleGroups;
    }

    public void close() throws IOException {
        Container c = this.trackBox.getParent();
        if (c instanceof BasicContainer) {
            ((BasicContainer) c).close();
        }
        for (IsoFile fragment : this.fragments) {
            fragment.close();
        }
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public synchronized long[] getSampleDurations() {
        return this.decodingTimes;
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public List<Entry> getCompositionTimeEntries() {
        return this.compositionTimeEntries;
    }

    public long[] getSyncSamples() {
        if (this.syncSamples.length == this.samples.size()) {
            return null;
        }
        return this.syncSamples;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.sampleDependencies;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return this.handler;
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.subSampleInformationBox;
    }
}
