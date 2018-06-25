package org.telegram.ui.ActionBar;

import java.util.Comparator;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;

class Theme$5 implements Comparator<ThemeInfo> {
    Theme$5() {
    }

    public int compare(ThemeInfo themeInfo, ThemeInfo themeInfo2) {
        return (themeInfo.pathToFile == null && themeInfo.assetName == null) ? -1 : (themeInfo2.pathToFile == null && themeInfo2.assetName == null) ? 1 : themeInfo.name.compareTo(themeInfo2.name);
    }
}
