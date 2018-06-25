package com.googlecode.mp4parser.authoring;

public class Edit {
    private double mediaRate;
    private long mediaTime;
    private double segmentDuration;
    private long timeScale;

    public Edit(long mediaTime, long timeScale, double mediaRate, double segmentDurationInMs) {
        this.timeScale = timeScale;
        this.segmentDuration = segmentDurationInMs;
        this.mediaTime = mediaTime;
        this.mediaRate = mediaRate;
    }

    public long getTimeScale() {
        return this.timeScale;
    }

    public double getSegmentDuration() {
        return this.segmentDuration;
    }

    public long getMediaTime() {
        return this.mediaTime;
    }

    public double getMediaRate() {
        return this.mediaRate;
    }
}
