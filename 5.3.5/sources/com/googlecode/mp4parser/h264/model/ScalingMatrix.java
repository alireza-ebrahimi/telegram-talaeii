package com.googlecode.mp4parser.h264.model;

import java.util.Arrays;

public class ScalingMatrix {
    public ScalingList[] ScalingList4x4;
    public ScalingList[] ScalingList8x8;

    public String toString() {
        Object obj = null;
        StringBuilder append = new StringBuilder("ScalingMatrix{ScalingList4x4=").append(this.ScalingList4x4 == null ? null : Arrays.asList(this.ScalingList4x4)).append(LogCollector.LINE_SEPARATOR).append(", ScalingList8x8=");
        if (this.ScalingList8x8 != null) {
            obj = Arrays.asList(this.ScalingList8x8);
        }
        return append.append(obj).append(LogCollector.LINE_SEPARATOR).append('}').toString();
    }
}
