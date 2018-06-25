package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractContainerBox;

public class SampleTableBox extends AbstractContainerBox {
    public static final String TYPE = "stbl";
    private SampleToChunkBox sampleToChunkBox;

    public SampleTableBox() {
        super(TYPE);
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        for (Box box : getBoxes()) {
            if (box instanceof SampleDescriptionBox) {
                return (SampleDescriptionBox) box;
            }
        }
        return null;
    }

    public SampleSizeBox getSampleSizeBox() {
        for (Box box : getBoxes()) {
            if (box instanceof SampleSizeBox) {
                return (SampleSizeBox) box;
            }
        }
        return null;
    }

    public SampleToChunkBox getSampleToChunkBox() {
        if (this.sampleToChunkBox != null) {
            return this.sampleToChunkBox;
        }
        for (Box box : getBoxes()) {
            if (box instanceof SampleToChunkBox) {
                this.sampleToChunkBox = (SampleToChunkBox) box;
                return this.sampleToChunkBox;
            }
        }
        return null;
    }

    public ChunkOffsetBox getChunkOffsetBox() {
        for (Box box : getBoxes()) {
            if (box instanceof ChunkOffsetBox) {
                return (ChunkOffsetBox) box;
            }
        }
        return null;
    }

    public TimeToSampleBox getTimeToSampleBox() {
        for (Box box : getBoxes()) {
            if (box instanceof TimeToSampleBox) {
                return (TimeToSampleBox) box;
            }
        }
        return null;
    }

    public SyncSampleBox getSyncSampleBox() {
        for (Box box : getBoxes()) {
            if (box instanceof SyncSampleBox) {
                return (SyncSampleBox) box;
            }
        }
        return null;
    }

    public CompositionTimeToSample getCompositionTimeToSample() {
        for (Box box : getBoxes()) {
            if (box instanceof CompositionTimeToSample) {
                return (CompositionTimeToSample) box;
            }
        }
        return null;
    }

    public SampleDependencyTypeBox getSampleDependencyTypeBox() {
        for (Box box : getBoxes()) {
            if (box instanceof SampleDependencyTypeBox) {
                return (SampleDependencyTypeBox) box;
            }
        }
        return null;
    }
}
