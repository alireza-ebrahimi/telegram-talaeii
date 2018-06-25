package org.telegram.customization.util.view.observerRecyclerView;

import android.view.View;

public interface ScrollPositionChangesListener {
    void LeavedBoundaries(View view, int i);

    void ReachedEnd(View view);

    void ReachedStart(View view);

    void handleScrollState(ScrollState scrollState);
}
