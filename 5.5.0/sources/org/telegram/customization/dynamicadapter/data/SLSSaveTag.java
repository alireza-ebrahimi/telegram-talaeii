package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SLSSaveTag {
    ArrayList<SlsTag> tags;
    long userId;

    public ArrayList<SlsTag> getTags() {
        return this.tags;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setTags(ArrayList<SlsTag> arrayList) {
        this.tags = arrayList;
    }

    public void setUserId(long j) {
        this.userId = j;
    }
}
