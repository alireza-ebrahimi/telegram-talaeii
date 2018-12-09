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

    public void bringViewToFront(EntityView entityView) {
        if (indexOfChild(entityView) != getChildCount() - 1) {
            removeView(entityView);
            addView(entityView, getChildCount());
        }
    }

    public int entitiesCount() {
        int i = 0;
        int i2 = 0;
        while (i < getChildCount()) {
            if (getChildAt(i) instanceof EntityView) {
                i2++;
            }
            i++;
        }
        return i2;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return motionEvent.getPointerCount() == 2 && this.delegate.shouldReceiveTouches();
    }

    public void onRotation(RotationGestureDetector rotationGestureDetector) {
        EntityView onSelectedEntityRequest = this.delegate.onSelectedEntityRequest();
        float angle = rotationGestureDetector.getAngle();
        onSelectedEntityRequest.rotate((this.previousAngle - angle) + onSelectedEntityRequest.getRotation());
        this.previousAngle = angle;
    }

    public void onRotationBegin(RotationGestureDetector rotationGestureDetector) {
        this.previousAngle = rotationGestureDetector.getStartAngle();
        this.hasTransformed = true;
    }

    public void onRotationEnd(RotationGestureDetector rotationGestureDetector) {
    }

    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        this.delegate.onSelectedEntityRequest().scale(scaleFactor / this.previousScale);
        this.previousScale = scaleFactor;
        return false;
    }

    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        this.previousScale = 1.0f;
        this.hasTransformed = true;
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.delegate.onSelectedEntityRequest() == null) {
            return false;
        }
        if (motionEvent.getPointerCount() == 1) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.hasTransformed = false;
            } else if (actionMasked == 1 || actionMasked == 2) {
                if (this.hasTransformed || this.delegate == null) {
                    return false;
                }
                this.delegate.onEntityDeselect();
                return false;
            }
        }
        this.gestureDetector.onTouchEvent(motionEvent);
        this.rotationGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
}
