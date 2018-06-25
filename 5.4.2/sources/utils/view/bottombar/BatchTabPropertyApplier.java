package utils.view.bottombar;

class BatchTabPropertyApplier {
    /* renamed from: a */
    private final BottomBar f10342a;

    interface TabPropertyUpdater {
        /* renamed from: a */
        void mo4648a(BottomBarTab bottomBarTab);
    }

    BatchTabPropertyApplier(BottomBar bottomBar) {
        this.f10342a = bottomBar;
    }

    /* renamed from: a */
    void m14207a(TabPropertyUpdater tabPropertyUpdater) {
        int tabCount = this.f10342a.getTabCount();
        if (tabCount > 0) {
            for (int i = 0; i < tabCount; i++) {
                tabPropertyUpdater.mo4648a(this.f10342a.m14257b(i));
            }
        }
    }
}
