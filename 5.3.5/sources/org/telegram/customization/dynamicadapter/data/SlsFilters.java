package org.telegram.customization.dynamicadapter.data;

import java.util.ArrayList;

public class SlsFilters extends ObjBase {
    ArrayList<SlsFilter> filters;

    public ArrayList<SlsFilter> getFilters() {
        return this.filters;
    }

    public void setFilters(ArrayList<SlsFilter> filters) {
        this.filters = filters;
    }
}
