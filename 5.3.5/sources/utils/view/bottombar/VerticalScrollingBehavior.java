package utils.view.bottombar;

import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

abstract class VerticalScrollingBehavior<V extends View> extends Behavior<V> {
    private int overScrollDirection = 0;
    private int scrollDirection = 0;
    private int totalDy = 0;
    private int totalDyUnconsumed = 0;

    abstract void onDirectionNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3);

    abstract boolean onNestedDirectionFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2, int i);

    abstract void onNestedVerticalOverScroll(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3);

    VerticalScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    VerticalScrollingBehavior() {
    }

    int getOverScrollDirection() {
        return this.overScrollDirection;
    }

    int getScrollDirection() {
        return this.scrollDirection;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & 2) != 0;
    }

    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyUnconsumed > 0 && this.totalDyUnconsumed < 0) {
            this.totalDyUnconsumed = 0;
            this.overScrollDirection = 1;
        } else if (dyUnconsumed < 0 && this.totalDyUnconsumed > 0) {
            this.totalDyUnconsumed = 0;
            this.overScrollDirection = -1;
        }
        this.totalDyUnconsumed += dyUnconsumed;
        onNestedVerticalOverScroll(coordinatorLayout, child, this.overScrollDirection, dyConsumed, this.totalDyUnconsumed);
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (dy > 0 && this.totalDy < 0) {
            this.totalDy = 0;
            this.scrollDirection = 1;
        } else if (dy < 0 && this.totalDy > 0) {
            this.totalDy = 0;
            this.scrollDirection = -1;
        }
        this.totalDy += dy;
        onDirectionNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, this.scrollDirection);
    }

    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY, boolean consumed) {
        super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        this.scrollDirection = velocityY > 0.0f ? 1 : -1;
        return onNestedDirectionFling(coordinatorLayout, child, target, velocityX, velocityY, this.scrollDirection);
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, V child, WindowInsetsCompat insets) {
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return super.onSaveInstanceState(parent, child);
    }
}
