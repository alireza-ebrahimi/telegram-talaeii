package org.telegram.ui.Components.Paint.Views;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private float angle;
    private float fX;
    private float fY;
    private OnRotationGestureListener mListener;
    private float sX;
    private float sY;
    private float startAngle;

    public interface OnRotationGestureListener {
        void onRotation(RotationGestureDetector rotationGestureDetector);

        void onRotationBegin(RotationGestureDetector rotationGestureDetector);

        void onRotationEnd(RotationGestureDetector rotationGestureDetector);
    }

    public RotationGestureDetector(OnRotationGestureListener onRotationGestureListener) {
        this.mListener = onRotationGestureListener;
    }

    private float angleBetweenLines(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        float toDegrees = ((float) Math.toDegrees((double) (((float) Math.atan2((double) (f2 - f4), (double) (f - f3))) - ((float) Math.atan2((double) (f6 - f8), (double) (f5 - f7)))))) % 360.0f;
        if (toDegrees < -180.0f) {
            toDegrees += 360.0f;
        }
        return toDegrees > 180.0f ? toDegrees - 360.0f : toDegrees;
    }

    public float getAngle() {
        return this.angle;
    }

    public float getStartAngle() {
        return this.startAngle;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
            case 5:
                this.sX = motionEvent.getX(0);
                this.sY = motionEvent.getY(0);
                this.fX = motionEvent.getX(1);
                this.fY = motionEvent.getY(1);
                break;
            case 1:
            case 3:
                this.startAngle = Float.NaN;
                break;
            case 2:
                float x = motionEvent.getX(0);
                float y = motionEvent.getY(0);
                this.angle = angleBetweenLines(this.fX, this.fY, this.sX, this.sY, motionEvent.getX(1), motionEvent.getY(1), x, y);
                if (this.mListener != null) {
                    if (!Float.isNaN(this.startAngle)) {
                        this.mListener.onRotation(this);
                        break;
                    }
                    this.startAngle = this.angle;
                    this.mListener.onRotationBegin(this);
                    break;
                }
                break;
            case 6:
                this.startAngle = Float.NaN;
                if (this.mListener != null) {
                    this.mListener.onRotationEnd(this);
                    break;
                }
                break;
        }
        return true;
    }
}
