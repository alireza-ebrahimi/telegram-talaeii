package utils.view.bottombar;

import android.support.annotation.NonNull;

class BatchTabPropertyApplier {
    private final BottomBar bottomBar;

    interface TabPropertyUpdater {
        void update(BottomBarTab bottomBarTab);
    }

    BatchTabPropertyApplier(@NonNull BottomBar bottomBar) {
        this.bottomBar = bottomBar;
    }

    void applyToAllTabs(TabPropertyUpdater propertyUpdater) {
        int tabCount = this.bottomBar.getTabCount();
        if (tabCount > 0) {
            for (int i = 0; i < tabCount; i++) {
                propertyUpdater.update(this.bottomBar.getTabAtPosition(i));
            }
        }
    }
}
