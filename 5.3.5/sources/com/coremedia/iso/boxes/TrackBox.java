package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;
import java.util.List;

public class TrackBox extends AbstractContainerBox {
    public static final String TYPE = "trak";
    private SampleTableBox sampleTableBox;

    public TrackBox() {
        super(TYPE);
    }

    public TrackHeaderBox getTrackHeaderBox() {
        for (Box box : getBoxes()) {
            if (box instanceof TrackHeaderBox) {
                return (TrackHeaderBox) box;
            }
        }
        return null;
    }

    public SampleTableBox getSampleTableBox() {
        if (this.sampleTableBox != null) {
            return this.sampleTableBox;
        }
        MediaBox mdia = getMediaBox();
        if (mdia != null) {
            MediaInformationBox minf = mdia.getMediaInformationBox();
            if (minf != null) {
                this.sampleTableBox = minf.getSampleTableBox();
                return this.sampleTableBox;
            }
        }
        return null;
    }

    public MediaBox getMediaBox() {
        for (Box box : getBoxes()) {
            if (box instanceof MediaBox) {
                return (MediaBox) box;
            }
        }
        return null;
    }

    public void setBoxes(List<Box> boxes) {
        super.setBoxes(boxes);
        this.sampleTableBox = null;
    }
}
