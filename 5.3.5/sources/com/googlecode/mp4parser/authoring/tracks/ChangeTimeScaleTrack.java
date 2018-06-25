package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample.Entry;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ChangeTimeScaleTrack implements Track {
    private static final Logger LOG = Logger.getLogger(ChangeTimeScaleTrack.class.getName());
    List<Entry> ctts;
    long[] decodingTimes;
    Track source;
    long timeScale;

    public ChangeTimeScaleTrack(Track source, long targetTimeScale, long[] syncSamples) {
        this.source = source;
        this.timeScale = targetTimeScale;
        double timeScaleFactor = ((double) targetTimeScale) / ((double) source.getTrackMetaData().getTimescale());
        this.ctts = adjustCtts(source.getCompositionTimeEntries(), timeScaleFactor);
        this.decodingTimes = adjustTts(source.getSampleDurations(), timeScaleFactor, syncSamples, getTimes(source, syncSamples, targetTimeScale));
    }

    private static long[] getTimes(Track track, long[] syncSamples, long targetTimeScale) {
        long[] syncSampleTimes = new long[syncSamples.length];
        long currentDuration = 0;
        int currentSyncSampleIndex = 0;
        for (int currentSample = 1; ((long) currentSample) <= syncSamples[syncSamples.length - 1]; currentSample++) {
            if (((long) currentSample) == syncSamples[currentSyncSampleIndex]) {
                int currentSyncSampleIndex2 = currentSyncSampleIndex + 1;
                syncSampleTimes[currentSyncSampleIndex] = (currentDuration * targetTimeScale) / track.getTrackMetaData().getTimescale();
                currentSyncSampleIndex = currentSyncSampleIndex2;
            }
            currentDuration += track.getSampleDurations()[currentSample - 1];
        }
        return syncSampleTimes;
    }

    static List<Entry> adjustCtts(List<Entry> source, double timeScaleFactor) {
        if (source == null) {
            return null;
        }
        List<Entry> entries2 = new ArrayList(source.size());
        for (Entry entry : source) {
            entries2.add(new Entry(entry.getCount(), (int) Math.round(((double) entry.getOffset()) * timeScaleFactor)));
        }
        return entries2;
    }

    static long[] adjustTts(long[] sourceArray, double timeScaleFactor, long[] syncSample, long[] syncSampleTimes) {
        long summedDurations = 0;
        long[] scaledArray = new long[sourceArray.length];
        for (int i = 1; i <= sourceArray.length; i++) {
            long x = Math.round(((double) sourceArray[i - 1]) * timeScaleFactor);
            int ssIndex = Arrays.binarySearch(syncSample, (long) (i + 1));
            if (ssIndex >= 0 && syncSampleTimes[ssIndex] != summedDurations) {
                long correction = syncSampleTimes[ssIndex] - (summedDurations + x);
                LOG.finest(String.format("Sample %d %d / %d - correct by %d", new Object[]{Integer.valueOf(i), Long.valueOf(summedDurations), Long.valueOf(syncSampleTimes[ssIndex]), Long.valueOf(correction)}));
                x += correction;
            }
            summedDurations += x;
            scaledArray[i - 1] = x;
        }
        return scaledArray;
    }

    public void close() throws IOException {
        this.source.close();
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.source.getSampleDescriptionBox();
    }

    public long[] getSampleDurations() {
        return this.decodingTimes;
    }

    public List<Entry> getCompositionTimeEntries() {
        return this.ctts;
    }

    public long[] getSyncSamples() {
        return this.source.getSyncSamples();
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.source.getSampleDependencies();
    }

    public TrackMetaData getTrackMetaData() {
        TrackMetaData trackMetaData = (TrackMetaData) this.source.getTrackMetaData().clone();
        trackMetaData.setTimescale(this.timeScale);
        return trackMetaData;
    }

    public String getHandler() {
        return this.source.getHandler();
    }

    public List<Sample> getSamples() {
        return this.source.getSamples();
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.source.getSubsampleInformationBox();
    }

    public long getDuration() {
        long duration = 0;
        for (long delta : this.decodingTimes) {
            duration += delta;
        }
        return duration;
    }

    public String toString() {
        return "ChangeTimeScaleTrack{source=" + this.source + '}';
    }

    public String getName() {
        return "timeScale(" + this.source.getName() + ")";
    }

    public List<Edit> getEdits() {
        return this.source.getEdits();
    }

    public Map<GroupEntry, long[]> getSampleGroups() {
        return this.source.getSampleGroups();
    }
}
