package org.telegram.messenger.exoplayer2.util;

public final class NalUnitUtil$PpsData {
    public final boolean bottomFieldPicOrderInFramePresentFlag;
    public final int picParameterSetId;
    public final int seqParameterSetId;

    public NalUnitUtil$PpsData(int picParameterSetId, int seqParameterSetId, boolean bottomFieldPicOrderInFramePresentFlag) {
        this.picParameterSetId = picParameterSetId;
        this.seqParameterSetId = seqParameterSetId;
        this.bottomFieldPicOrderInFramePresentFlag = bottomFieldPicOrderInFramePresentFlag;
    }
}
