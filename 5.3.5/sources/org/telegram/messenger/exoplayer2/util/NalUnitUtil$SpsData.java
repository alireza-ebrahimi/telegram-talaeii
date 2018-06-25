package org.telegram.messenger.exoplayer2.util;

public final class NalUnitUtil$SpsData {
    public final boolean deltaPicOrderAlwaysZeroFlag;
    public final boolean frameMbsOnlyFlag;
    public final int frameNumLength;
    public final int height;
    public final int picOrderCntLsbLength;
    public final int picOrderCountType;
    public final float pixelWidthAspectRatio;
    public final boolean separateColorPlaneFlag;
    public final int seqParameterSetId;
    public final int width;

    public NalUnitUtil$SpsData(int seqParameterSetId, int width, int height, float pixelWidthAspectRatio, boolean separateColorPlaneFlag, boolean frameMbsOnlyFlag, int frameNumLength, int picOrderCountType, int picOrderCntLsbLength, boolean deltaPicOrderAlwaysZeroFlag) {
        this.seqParameterSetId = seqParameterSetId;
        this.width = width;
        this.height = height;
        this.pixelWidthAspectRatio = pixelWidthAspectRatio;
        this.separateColorPlaneFlag = separateColorPlaneFlag;
        this.frameMbsOnlyFlag = frameMbsOnlyFlag;
        this.frameNumLength = frameNumLength;
        this.picOrderCountType = picOrderCountType;
        this.picOrderCntLsbLength = picOrderCntLsbLength;
        this.deltaPicOrderAlwaysZeroFlag = deltaPicOrderAlwaysZeroFlag;
    }
}
