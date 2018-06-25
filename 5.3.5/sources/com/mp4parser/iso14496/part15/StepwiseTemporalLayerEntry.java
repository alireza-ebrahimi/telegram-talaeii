package com.mp4parser.iso14496.part15;

import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import java.nio.ByteBuffer;

public class StepwiseTemporalLayerEntry extends GroupEntry {
    public static final String TYPE = "stsa";

    public void parse(ByteBuffer byteBuffer) {
    }

    public String getType() {
        return TYPE;
    }

    public ByteBuffer get() {
        return ByteBuffer.allocate(0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 37;
    }
}
