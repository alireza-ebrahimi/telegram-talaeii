package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SLSSaveTag {
    ArrayList<SlsTag> tags;
    long userId;

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<SlsTag> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<SlsTag> tags) {
        this.tags = tags;
    }
}
