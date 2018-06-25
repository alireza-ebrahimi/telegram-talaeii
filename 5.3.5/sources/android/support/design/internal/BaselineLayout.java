package android.support.design.internal;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout extends ViewGroup {
    private int mBaseline = -1;

    public BaselineLayout(Context context) {
        super(context, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BaselineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int maxWidth = 0;
        int maxHeight = 0;
        int maxChildBaseline = -1;
        int maxChildDescent = -1;
        int childState = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int baseline = child.getBaseline();
                if (baseline != -1) {
                    maxChildBaseline = Math.max(maxChildBaseline, baseline);
                    maxChildDescent = Math.max(maxChildDescent, child.getMeasuredHeight() - baseline);
                }
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                childState = ViewUtils.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
            }
        }
        if (maxChildBaseline != -1) {
            maxHeight = Math.max(maxHeight, maxChildBaseline + Math.max(maxChildDescent, getPaddingBottom()));
            this.mBaseline = maxChildBaseline;
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max(maxWidth, getSuggestedMinimumWidth()), widthMeasureSpec, childState), ViewCompat.resolveSizeAndState(Math.max(maxHeight, getSuggestedMinimumHeight()), heightMeasureSpec, childState << 16));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int parentContentWidth = ((right - left) - getPaddingRight()) - parentLeft;
        int parentTop = getPaddingTop();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int childTop;
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                int childLeft = parentLeft + ((parentContentWidth - width) / 2);
                if (this.mBaseline == -1 || child.getBaseline() == -1) {
                    childTop = parentTop;
                } else {
                    childTop = (this.mBaseline + parentTop) - child.getBaseline();
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    public int getBaseline() {
        return this.mBaseline;
    }
}
