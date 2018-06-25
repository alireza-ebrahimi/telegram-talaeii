package org.telegram.customization.dynamicadapter.data;

public class ExtraData {
    public int poolId;
    long tagId;

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public int getPoolId() {
        return this.poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }
}
