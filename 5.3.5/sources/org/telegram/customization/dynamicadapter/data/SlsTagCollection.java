package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SlsTagCollection extends ObjBase {
    ArrayList<SlsTag> tags;

    public ArrayList<SlsTag> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<SlsTag> tags) {
        this.tags = tags;
    }
}
