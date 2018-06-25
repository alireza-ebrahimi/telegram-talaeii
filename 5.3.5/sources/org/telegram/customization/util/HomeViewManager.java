package org.telegram.customization.util;

import java.util.ArrayList;
import java.util.Iterator;

public class HomeViewManager {
    private static HomeViewManager homeViewManager = null;
    public static ArrayList<String> tags = new ArrayList();

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        tags = tags;
    }

    public static HomeViewManager getInstance() {
        if (homeViewManager == null) {
            homeViewManager = new HomeViewManager();
        }
        return homeViewManager;
    }

    public void addViewsToStack(String tag) {
        boolean exist = false;
        Iterator it = tags.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).contentEquals(tag)) {
                exist = true;
            }
        }
        if (exist) {
            tags.remove(tag);
            tags.add(tag);
            return;
        }
        tags.add(tag);
    }
}
