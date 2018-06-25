package com.coremedia.iso.boxes.fragment;

import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox.Entry;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.DataSource;
import java.util.ArrayList;
import java.util.List;

public class MovieFragmentBox extends AbstractContainerBox {
    public static final String TYPE = "moof";

    public MovieFragmentBox() {
        super(TYPE);
    }

    public List<Long> getSyncSamples(SampleDependencyTypeBox sdtp) {
        List<Long> result = new ArrayList();
        long i = 1;
        for (Entry sampleEntry : sdtp.getEntries()) {
            if (sampleEntry.getSampleDependsOn() == 2) {
                result.add(Long.valueOf(i));
            }
            i++;
        }
        return result;
    }

    public int getTrackCount() {
        return getBoxes(TrackFragmentBox.class, false).size();
    }

    public long[] getTrackNumbers() {
        List<TrackFragmentBox> trackBoxes = getBoxes(TrackFragmentBox.class, false);
        long[] trackNumbers = new long[trackBoxes.size()];
        for (int trackCounter = 0; trackCounter < trackBoxes.size(); trackCounter++) {
            trackNumbers[trackCounter] = ((TrackFragmentBox) trackBoxes.get(trackCounter)).getTrackFragmentHeaderBox().getTrackId();
        }
        return trackNumbers;
    }

    public List<TrackFragmentHeaderBox> getTrackFragmentHeaderBoxes() {
        return getBoxes(TrackFragmentHeaderBox.class, true);
    }

    public List<TrackRunBox> getTrackRunBoxes() {
        return getBoxes(TrackRunBox.class, true);
    }

    public DataSource getFileChannel() {
        return this.dataSource;
    }
}
