package org.telegram.ui.Components.Crop;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import org.telegram.messenger.AndroidUtilities;

public class CropGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = -1;
    private int mActivePointerIndex = 0;
    private ScaleGestureDetector mDetector;
    private boolean mIsDragging;
    float mLastTouchX;
    float mLastTouchY;
    private CropGestureListener mListener;
    final float mMinimumVelocity;
    final float mTouchSlop = ((float) AndroidUtilities.dp(1.0f));
    private VelocityTracker mVelocityTracker;
    private boolean started;

    /* renamed from: org.telegram.ui.Components.Crop.CropGestureDetector$1 */
    class C43821 implements OnScaleGestureListener {
        C43821() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            if (Float.isNaN(scaleFactor) || Float.isInfinite(scaleFactor)) {
                return false;
            }
            CropGestureDetector.this.mListener.onScale(scaleFactor, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }

    public interface CropGestureListener {
        void onDrag(float f, float f2);

        void onFling(float f, float f2, float f3, float f4);

        void onScale(float f, float f2, float f3);
    }

    public CropGestureDetector(Context context) {
        this.mMinimumVelocity = (float) ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mDetector = new ScaleGestureDetector(context, new C43821());
    }

    float getActiveX(MotionEvent motionEvent) {
        try {
            return motionEvent.getX(this.mActivePointerIndex);
        } catch (Exception e) {
            return motionEvent.getX();
        }
    }

    float getActiveY(MotionEvent motionEvent) {
        try {
            return motionEvent.getY(this.mActivePointerIndex);
        } catch (Exception e) {
            return motionEvent.getY();
        }
    }

    public boolean isDragging() {
        return this.mIsDragging;
    }

    public boolean isScaling() {
        return this.mDetector.isInProgress();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        this.mDetector.onTouchEvent(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mActivePointerId = motionEvent.getPointerId(0);
                break;
            case 1:
            case 3:
                this.mActivePointerId = -1;
                break;
            case 6:
                int action = (65280 & motionEvent.getAction()) >> 8;
                if (motionEvent.getPointerId(action) == this.mActivePointerId) {
                    action = action == 0 ? 1 : 0;
                    this.mActivePointerId = motionEvent.getPointerId(action);
                    this.mLastTouchX = motionEvent.getX(action);
                    this.mLastTouchY = motionEvent.getY(action);
                    break;
                }
                break;
        }
        this.mActivePointerIndex = motionEvent.findPointerIndex(this.mActivePointerId != -1 ? this.mActivePointerId : 0);
        float activeX;
        float activeY;
        switch (motionEvent.getAction()) {
            case 0:
            case 2:
                if (!this.started) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.addMovement(motionEvent);
                    }
                    this.mLastTouchX = getActiveX(motionEvent);
                    this.mLastTouchY = getActiveY(motionEvent);
                    this.mIsDragging = false;
                    this.started = true;
                    break;
                }
                activeX = getActiveX(motionEvent);
                activeY = getActiveY(motionEvent);
                float f = activeX - this.mLastTouchX;
                float f2 = activeY - this.mLastTouchY;
                if (!this.mIsDragging) {
                    if (((float) Math.sqrt((double) ((f * f) + (f2 * f2)))) >= this.mTouchSlop) {
                        z = true;
                    }
                    this.mIsDragging = z;
                }
                if (this.mIsDragging) {
                    this.mListener.onDrag(f, f2);
                    this.mLastTouchX = activeX;
                    this.mLastTouchY = activeY;
                    if (this.mVelocityTracker != null) {
                        this.mVelocityTracker.addMovement(motionEvent);
                        break;
                    }
                }
                break;
            case 1:
                if (this.mIsDragging) {
                    if (this.mVelocityTracker != null) {
                        this.mLastTouchX = getActiveX(motionEvent);
                        this.mLastTouchY = getActiveY(motionEvent);
                        this.mVelocityTracker.addMovement(motionEvent);
                        this.mVelocityTracker.computeCurrentVelocity(1000);
                        activeX = this.mVelocityTracker.getXVelocity();
                        activeY = this.mVelocityTracker.getYVelocity();
                        if (Math.max(Math.abs(activeX), Math.abs(activeY)) >= this.mMinimumVelocity) {
                            this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, -activeX, -activeY);
                        }
                    }
                    this.mIsDragging = false;
                }
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                this.started = false;
                break;
            case 3:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                this.started = false;
                this.mIsDragging = false;
                break;
        }
        return true;
    }

    public void setOnGestureListener(CropGestureListener cropGestureListener) {
        this.mListener = cropGestureListener;
    }
}
