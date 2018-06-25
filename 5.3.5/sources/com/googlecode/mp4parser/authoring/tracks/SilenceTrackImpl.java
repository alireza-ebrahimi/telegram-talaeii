package com.googlecode.mp4parser.authoring.tracks;

import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SilenceTrackImpl implements Track {
    long[] decodingTimes;
    String name;
    List<Sample> samples = new LinkedList();
    Track source;

    public SilenceTrackImpl(Track ofType, long ms) {
        this.source = ofType;
        this.name = ms + "ms silence";
        if (AudioSampleEntry.TYPE3.equals(ofType.getSampleDescriptionBox().getSampleEntry().getType())) {
            int numFrames = CastUtils.l2i(((getTrackMetaData().getTimescale() * ms) / 1000) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            this.decodingTimes = new long[numFrames];
            Arrays.fill(this.decodingTimes, ((getTrackMetaData().getTimescale() * ms) / ((long) numFrames)) / 1000);
            int numFrames2 = numFrames;
            while (true) {
                numFrames = numFrames2 - 1;
                if (numFrames2 > 0) {
                    this.samples.add(new SampleImpl((ByteBuffer) ByteBuffer.wrap(new byte[]{(byte) 33, (byte) 16, (byte) 4, (byte) 96, (byte) -116, (byte) 28}).rewind()));
                    numFrames2 = numFrames;
                } else {
                    return;
                }
            }
        }
        throw new RuntimeException("Tracks of type " + ofType.getClass().getSimpleName() + " are not supported");
    }

    public void close() throws IOException {
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.source.getSampleDescriptionBox();
    }

    public long[] getSampleDurations() {
        return this.decodingTimes;
    }

    public long getDuration() {
        long duration = 0;
        for (long delta : this.decodingTimes) {
            duration += delta;
        }
        return duration;
    }

    public TrackMetaData getTrackMetaData() {
        return this.source.getTrackMetaData();
    }

    public String getHandler() {
        return this.source.getHandler();
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return null;
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

    public String getName() {
        return this.name;
    }

    public List<Edit> getEdits() {
        return null;
    }

    public Map<GroupEntry, long[]> getSampleGroups() {
        return this.source.getSampleGroups();
    }
}
