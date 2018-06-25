package org.telegram.ui.ActionBar;

import java.util.Comparator;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;

class Theme$5 implements Comparator<ThemeInfo> {
    Theme$5() {
    }

    public int compare(ThemeInfo o1, ThemeInfo o2) {
        if (o1.pathToFile == null && o1.assetName == null) {
            return -1;
        }
        if (o2.pathToFile == null && o2.assetName == null) {
            return 1;
        }
        return o1.name.compareTo(o2.name);
    }
}
