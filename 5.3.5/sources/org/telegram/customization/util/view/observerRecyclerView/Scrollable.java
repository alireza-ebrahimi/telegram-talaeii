package org.telegram.customization.util.view.observerRecyclerView;

import android.view.ViewGroup;

public interface Scrollable {
    int getCurrentScrollY();

    void scrollVerticallyTo(int i);

    void setScrollViewCallbacks(ObservableScrollViewCallbacks observableScrollViewCallbacks);

    void setTouchInterceptionViewGroup(ViewGroup viewGroup);
}
