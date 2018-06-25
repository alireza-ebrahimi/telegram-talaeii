package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.FrameLayout;
import org.telegram.ui.Components.Paint.Views.RotationGestureDetector.OnRotationGestureListener;

public class EntitiesContainerView extends FrameLayout implements OnScaleGestureListener, OnRotationGestureListener {
    private EntitiesContainerViewDelegate delegate;
    private ScaleGestureDetector gestureDetector;
    private boolean hasTransformed;
    private float previousAngle;
    private float previousScale = 1.0f;
    private RotationGestureDetector rotationGestureDetector;

    public interface EntitiesContainerViewDelegate {
        void onEntityDeselect();

        EntityView onSelectedEntityRequest();

        boolean shouldReceiveTouches();
    }

    public EntitiesContainerView(Context context, EntitiesContainerViewDelegate entitiesContainerViewDelegate) {
        super(context);
        this.gestureDetector = new ScaleGestureDetector(context, this);
        this.rotationGestureDetector = new RotationGestureDetector(this);
        this.delegate = entitiesContainerViewDelegate;
    }

    public int entitiesCount() {
        int count = 0;
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) instanceof EntityView) {
                count++;
            }
        }
        return count;
    }

    public void bringViewToFront(EntityView view) {
        if (indexOfChild(view) != getChildCount() - 1) {
            removeView(view);
            addView(view, getChildCount());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return ev.getPointerCount() == 2 && this.delegate.shouldReceiveTouches();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.delegate.onSelectedEntityRequest() == null) {
            return false;
        }
        if (event.getPointerCount() == 1) {
            int action = event.getActionMasked();
            if (action == 0) {
                this.hasTransformed = false;
            } else if (action == 1 || action == 2) {
                if (this.hasTransformed || this.delegate == null) {
                    return false;
                }
                this.delegate.onEntityDeselect();
                return false;
            }
        }
        this.gestureDetector.onTouchEvent(event);
        this.rotationGestureDetector.onTouchEvent(event);
        return true;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float sf = detector.getScaleFactor();
        this.delegate.onSelectedEntityRequest().scale(sf / this.previousScale);
        this.previousScale = sf;
        return false;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.previousScale = 1.0f;
        this.hasTransformed = true;
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public void onRotationBegin(RotationGestureDetector rotationDetector) {
        this.previousAngle = rotationDetector.getStartAngle();
        this.hasTransformed = true;
    }

    public void onRotation(RotationGestureDetector rotationDetector) {
        EntityView view = this.delegate.onSelectedEntityRequest();
        float angle = rotationDetector.getAngle();
        view.rotate(view.getRotation() + (this.previousAngle - angle));
        this.previousAngle = angle;
    }

    public void onRotationEnd(RotationGestureDetector rotationDetector) {
    }
}
