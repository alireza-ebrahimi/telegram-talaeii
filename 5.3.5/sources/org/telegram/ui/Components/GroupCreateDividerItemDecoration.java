package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.ui.ActionBar.Theme;

public class GroupCreateDividerItemDecoration extends ItemDecoration {
    private boolean searching;
    private boolean single;

    public void setSearching(boolean value) {
        this.searching = value;
    }

    public void setSingle(boolean value) {
        this.single = value;
    }

    public void onDraw(Canvas canvas, RecyclerView parent, State state) {
        int width = parent.getWidth();
        int childCount = parent.getChildCount() - (this.single ? 0 : 1);
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            int top = child.getBottom();
            canvas.drawLine(LocaleController.isRTL ? 0.0f : (float) AndroidUtilities.dp(72.0f), (float) top, (float) (width - (LocaleController.isRTL ? AndroidUtilities.dp(72.0f) : 0)), (float) top, Theme.dividerPaint);
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = 1;
    }
}
