package org.telegram.messenger.support.widget;

import android.view.View;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.ViewBoundsCheck.Callback;

class RecyclerView$LayoutManager$1 implements Callback {
    final /* synthetic */ LayoutManager this$0;

    RecyclerView$LayoutManager$1(LayoutManager this$0) {
        this.this$0 = this$0;
    }

    public int getChildCount() {
        return this.this$0.getChildCount();
    }

    public View getParent() {
        return this.this$0.mRecyclerView;
    }

    public View getChildAt(int index) {
        return this.this$0.getChildAt(index);
    }

    public int getParentStart() {
        return this.this$0.getPaddingLeft();
    }

    public int getParentEnd() {
        return this.this$0.getWidth() - this.this$0.getPaddingRight();
    }

    public int getChildStart(View view) {
        return this.this$0.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
    }

    public int getChildEnd(View view) {
        return this.this$0.getDecoratedRight(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
    }
}
