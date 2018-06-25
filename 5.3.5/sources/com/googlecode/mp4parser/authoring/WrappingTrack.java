package com.googlecode.mp4parser.authoring;

import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WrappingTrack implements Track {
    Track parent;

    public WrappingTrack(Track parent) {
        this.parent = parent;
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.parent.getSampleDescriptionBox();
    }

    public long[] getSampleDurations() {
        return this.parent.getSampleDurations();
    }

    public long getDuration() {
        return this.parent.getDuration();
    }

    public List<Entry> getCompositionTimeEntries() {
        return this.parent.getCompositionTimeEntries();
    }

    public long[] getSyncSamples() {
        return this.parent.getSyncSamples();
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.parent.getSampleDependencies();
    }

    public TrackMetaData getTrackMetaData() {
        return this.parent.getTrackMetaData();
    }

    public String getHandler() {
        return this.parent.getHandler();
    }

    public List<Sample> getSamples() {
        return this.parent.getSamples();
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.parent.getSubsampleInformationBox();
    }

    public String getName() {
        return new StringBuilder(String.valueOf(this.parent.getName())).append("'").toString();
    }

    public List<Edit> getEdits() {
        return this.parent.getEdits();
    }

    public void close() throws IOException {
        this.parent.close();
    }

    public Map<GroupEntry, long[]> getSampleGroups() {
        return this.parent.getSampleGroups();
    }
}
