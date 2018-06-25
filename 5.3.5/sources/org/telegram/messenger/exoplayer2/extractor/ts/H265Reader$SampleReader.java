package org.telegram.messenger.exoplayer2.extractor.ts;

import org.telegram.messenger.exoplayer2.extractor.TrackOutput;

final class H265Reader$SampleReader {
    private static final int FIRST_SLICE_FLAG_OFFSET = 2;
    private boolean isFirstParameterSet;
    private boolean isFirstSlice;
    private boolean lookingForFirstSliceFlag;
    private int nalUnitBytesRead;
    private boolean nalUnitHasKeyframeData;
    private long nalUnitStartPosition;
    private long nalUnitTimeUs;
    private final TrackOutput output;
    private boolean readingSample;
    private boolean sampleIsKeyframe;
    private long samplePosition;
    private long sampleTimeUs;
    private boolean writingParameterSets;

    public H265Reader$SampleReader(TrackOutput output) {
        this.output = output;
    }

    public void reset() {
        this.lookingForFirstSliceFlag = false;
        this.isFirstSlice = false;
        this.isFirstParameterSet = false;
        this.readingSample = false;
        this.writingParameterSets = false;
    }

    public void startNalUnit(long position, int offset, int nalUnitType, long pesTimeUs) {
        boolean z;
        boolean z2 = false;
        this.isFirstSlice = false;
        this.isFirstParameterSet = false;
        this.nalUnitTimeUs = pesTimeUs;
        this.nalUnitBytesRead = 0;
        this.nalUnitStartPosition = position;
        if (nalUnitType >= 32) {
            if (!this.writingParameterSets && this.readingSample) {
                outputSample(offset);
                this.readingSample = false;
            }
            if (nalUnitType <= 34) {
                this.isFirstParameterSet = !this.writingParameterSets;
                this.writingParameterSets = true;
            }
        }
        if (nalUnitType < 16 || nalUnitType > 21) {
            z = false;
        } else {
            z = true;
        }
        this.nalUnitHasKeyframeData = z;
        if (this.nalUnitHasKeyframeData || nalUnitType <= 9) {
            z2 = true;
        }
        this.lookingForFirstSliceFlag = z2;
    }

    public void readNalUnitData(byte[] data, int offset, int limit) {
        if (this.lookingForFirstSliceFlag) {
            int headerOffset = (offset + 2) - this.nalUnitBytesRead;
            if (headerOffset < limit) {
                boolean z;
                if ((data[headerOffset] & 128) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                this.isFirstSlice = z;
                this.lookingForFirstSliceFlag = false;
                return;
            }
            this.nalUnitBytesRead += limit - offset;
        }
    }

    public void endNalUnit(long position, int offset) {
        if (this.writingParameterSets && this.isFirstSlice) {
            this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
            this.writingParameterSets = false;
        } else if (this.isFirstParameterSet || this.isFirstSlice) {
            if (this.readingSample) {
                outputSample(offset + ((int) (position - this.nalUnitStartPosition)));
            }
            this.samplePosition = this.nalUnitStartPosition;
            this.sampleTimeUs = this.nalUnitTimeUs;
            this.readingSample = true;
            this.sampleIsKeyframe = this.nalUnitHasKeyframeData;
        }
    }

    private void outputSample(int offset) {
        this.output.sampleMetadata(this.sampleTimeUs, this.sampleIsKeyframe ? 1 : 0, (int) (this.nalUnitStartPosition - this.samplePosition), offset, null);
    }
}
