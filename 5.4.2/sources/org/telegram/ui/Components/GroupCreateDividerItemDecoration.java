package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.ui.ActionBar.Theme;

public class GroupCreateDividerItemDecoration extends ItemDecoration {
    private boolean searching;
    private boolean single;

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        rect.top = 1;
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        int width = recyclerView.getWidth();
        int childCount = recyclerView.getChildCount() - (this.single ? 0 : 1);
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            recyclerView.getChildAdapterPosition(childAt);
            int bottom = childAt.getBottom();
            canvas.drawLine(LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : (float) AndroidUtilities.dp(72.0f), (float) bottom, (float) (width - (LocaleController.isRTL ? AndroidUtilities.dp(72.0f) : 0)), (float) bottom, Theme.dividerPaint);
        }
    }

    public void setSearching(boolean z) {
        this.searching = z;
    }

    public void setSingle(boolean z) {
        this.single = z;
    }
}
