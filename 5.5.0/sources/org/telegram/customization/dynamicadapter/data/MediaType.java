package org.telegram.customization.dynamicadapter.data;

public class MediaType extends ObjBase {
    long selectedMediaType;

    public MediaType() {
        this.type = 109;
    }

    public long getSelectedMediaType() {
        return this.selectedMediaType;
    }

    public void setSelectedMediaType(long j) {
        this.selectedMediaType = j;
    }
}
