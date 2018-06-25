package org.telegram.customization.util.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.TextView;
import org.ir.talaeii.R;

public class RecyclerSectionItemDecoration extends ItemDecoration {
    private TextView header;
    private final int headerOffset;
    private View headerView;
    private final SectionCallback sectionCallback;
    private final boolean sticky;

    public interface SectionCallback {
        CharSequence getSectionHeader(int i);

        boolean isSection(int i);
    }

    public RecyclerSectionItemDecoration(int headerHeight, boolean sticky, @NonNull SectionCallback sectionCallback) {
        this.headerOffset = headerHeight;
        this.sticky = sticky;
        this.sectionCallback = sectionCallback;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (this.sectionCallback.isSection(parent.getChildAdapterPosition(view))) {
            outRect.top = this.headerOffset;
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        super.onDrawOver(c, parent, state);
        if (this.headerView == null) {
            this.headerView = inflateHeaderView(parent);
        }
        CharSequence previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            CharSequence title = this.sectionCallback.getSectionHeader(position);
            this.header.setText(title);
            if (!previousHeader.equals(title) || this.sectionCallback.isSection(position)) {
                drawHeader(c, child, this.headerView);
                previousHeader = title;
            }
        }
    }

    private void drawHeader(Canvas c, View child, View headerView) {
        c.save();
        if (this.sticky) {
            c.translate(0.0f, (float) Math.max(0, child.getTop() - headerView.getHeight()));
        } else {
            c.translate(0.0f, (float) (child.getTop() - headerView.getHeight()));
        }
        headerView.draw(c);
        c.restore();
    }

    private View inflateHeaderView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_header, parent, false);
    }

    private void fixLayoutSize(View view, ViewGroup parent) {
        view.measure(ViewGroup.getChildMeasureSpec(MeasureSpec.makeMeasureSpec(parent.getWidth(), 1073741824), parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width), ViewGroup.getChildMeasureSpec(MeasureSpec.makeMeasureSpec(parent.getHeight(), 0), parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
