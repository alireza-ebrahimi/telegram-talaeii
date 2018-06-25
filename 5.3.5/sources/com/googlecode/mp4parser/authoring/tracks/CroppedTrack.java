package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.TimeToSampleBox.Entry;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CroppedTrack extends AbstractTrack {
    static final /* synthetic */ boolean $assertionsDisabled = (!CroppedTrack.class.desiredAssertionStatus());
    private int fromSample;
    Track origTrack;
    private int toSample;

    public CroppedTrack(Track origTrack, long fromSample, long toSample) {
        super("crop(" + origTrack.getName() + ")");
        this.origTrack = origTrack;
        if (!$assertionsDisabled && fromSample > 2147483647L) {
            throw new AssertionError();
        } else if ($assertionsDisabled || toSample <= 2147483647L) {
            this.fromSample = (int) fromSample;
            this.toSample = (int) toSample;
        } else {
            throw new AssertionError();
        }
    }

    public void close() throws IOException {
        this.origTrack.close();
    }

    public List<Sample> getSamples() {
        return this.origTrack.getSamples().subList(this.fromSample, this.toSample);
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.origTrack.getSampleDescriptionBox();
    }

    public synchronized long[] getSampleDurations() {
        long[] decodingTimes;
        decodingTimes = new long[(this.toSample - this.fromSample)];
        System.arraycopy(this.origTrack.getSampleDurations(), this.fromSample, decodingTimes, 0, decodingTimes.length);
        return decodingTimes;
    }

    static List<Entry> getDecodingTimeEntries(List<Entry> origSamples, long fromSample, long toSample) {
        if (origSamples == null || origSamples.isEmpty()) {
            return null;
        }
        long current = 0;
        ListIterator<Entry> e = origSamples.listIterator();
        LinkedList<Entry> nuList = new LinkedList();
        while (true) {
            Entry currentEntry = (Entry) e.next();
            if (currentEntry.getCount() + current > fromSample) {
                break;
            }
            current += currentEntry.getCount();
        }
        if (currentEntry.getCount() + current >= toSample) {
            nuList.add(new Entry(toSample - fromSample, currentEntry.getDelta()));
            return nuList;
        }
        nuList.add(new Entry((currentEntry.getCount() + current) - fromSample, currentEntry.getDelta()));
        current += currentEntry.getCount();
        while (e.hasNext()) {
            currentEntry = (Entry) e.next();
            if (currentEntry.getCount() + current >= toSample) {
                break;
            }
            nuList.add(currentEntry);
            current += currentEntry.getCount();
        }
        nuList.add(new Entry(toSample - current, currentEntry.getDelta()));
        return nuList;
    }

    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return getCompositionTimeEntries(this.origTrack.getCompositionTimeEntries(), (long) this.fromSample, (long) this.toSample);
    }

    static List<CompositionTimeToSample.Entry> getCompositionTimeEntries(List<CompositionTimeToSample.Entry> origSamples, long fromSample, long toSample) {
        if (origSamples == null || origSamples.isEmpty()) {
            return null;
        }
        long current = 0;
        ListIterator<CompositionTimeToSample.Entry> e = origSamples.listIterator();
        ArrayList<CompositionTimeToSample.Entry> nuList = new ArrayList();
        while (true) {
            CompositionTimeToSample.Entry currentEntry = (CompositionTimeToSample.Entry) e.next();
            if (((long) currentEntry.getCount()) + current > fromSample) {
                break;
            }
            current += (long) currentEntry.getCount();
        }
        if (((long) currentEntry.getCount()) + current >= toSample) {
            nuList.add(new CompositionTimeToSample.Entry((int) (toSample - fromSample), currentEntry.getOffset()));
            return nuList;
        }
        nuList.add(new CompositionTimeToSample.Entry((int) ((((long) currentEntry.getCount()) + current) - fromSample), currentEntry.getOffset()));
        current += (long) currentEntry.getCount();
        while (e.hasNext()) {
            currentEntry = (CompositionTimeToSample.Entry) e.next();
            if (((long) currentEntry.getCount()) + current >= toSample) {
                break;
            }
            nuList.add(currentEntry);
            current += (long) currentEntry.getCount();
        }
        nuList.add(new CompositionTimeToSample.Entry((int) (toSample - current), currentEntry.getOffset()));
        return nuList;
    }

    public synchronized long[] getSyncSamples() {
        long[] syncSampleArray;
        if (this.origTrack.getSyncSamples() != null) {
            long[] origSyncSamples = this.origTrack.getSyncSamples();
            int i = 0;
            int j = origSyncSamples.length;
            while (i < origSyncSamples.length && origSyncSamples[i] < ((long) this.fromSample)) {
                i++;
            }
            while (j > 0 && ((long) this.toSample) < origSyncSamples[j - 1]) {
                j--;
            }
            syncSampleArray = Arrays.copyOfRange(this.origTrack.getSyncSamples(), i, j);
            for (int k = 0; k < syncSampleArray.length; k++) {
                syncSampleArray[k] = syncSampleArray[k] - ((long) this.fromSample);
            }
        } else {
            syncSampleArray = null;
        }
        return syncSampleArray;
    }

    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        if (this.origTrack.getSampleDependencies() == null || this.origTrack.getSampleDependencies().isEmpty()) {
            return null;
        }
        return this.origTrack.getSampleDependencies().subList(this.fromSample, this.toSample);
    }

    public TrackMetaData getTrackMetaData() {
        return this.origTrack.getTrackMetaData();
    }

    public String getHandler() {
        return this.origTrack.getHandler();
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.origTrack.getSubsampleInformationBox();
    }
}
