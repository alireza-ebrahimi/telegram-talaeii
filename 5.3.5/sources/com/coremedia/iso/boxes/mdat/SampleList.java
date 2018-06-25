package com.coremedia.iso.boxes.mdat;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsBox;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.samples.DefaultMp4SampleList;
import com.googlecode.mp4parser.authoring.samples.FragmentedMp4SampleList;
import java.util.AbstractList;
import java.util.List;

public class SampleList extends AbstractList<Sample> {
    List<Sample> samples;

    public SampleList(TrackBox trackBox, IsoFile... additionalFragments) {
        Container topLevel = ((Box) trackBox.getParent()).getParent();
        if (!trackBox.getParent().getBoxes(MovieExtendsBox.class).isEmpty()) {
            this.samples = new FragmentedMp4SampleList(trackBox.getTrackHeaderBox().getTrackId(), topLevel, additionalFragments);
        } else if (additionalFragments.length > 0) {
            throw new RuntimeException("The TrackBox comes from a standard MP4 file. Only use the additionalFragments param if you are dealing with ( fragmented MP4 files AND additional fragments in standalone files )");
        } else {
            this.samples = new DefaultMp4SampleList(trackBox.getTrackHeaderBox().getTrackId(), topLevel);
        }
    }

    public Sample get(int index) {
        return (Sample) this.samples.get(index);
    }

    public int size() {
        return this.samples.size();
    }
}
