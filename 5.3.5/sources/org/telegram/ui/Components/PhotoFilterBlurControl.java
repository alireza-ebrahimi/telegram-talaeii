package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;

public class PhotoFilterBlurControl extends FrameLayout {
    private static final float BlurInsetProximity = ((float) AndroidUtilities.dp(20.0f));
    private static final float BlurMinimumDifference = 0.02f;
    private static final float BlurMinimumFalloff = 0.1f;
    private static final float BlurViewCenterInset = ((float) AndroidUtilities.dp(30.0f));
    private static final float BlurViewRadiusInset = ((float) AndroidUtilities.dp(30.0f));
    private final int GestureStateBegan = 1;
    private final int GestureStateCancelled = 4;
    private final int GestureStateChanged = 2;
    private final int GestureStateEnded = 3;
    private final int GestureStateFailed = 5;
    private BlurViewActiveControl activeControl;
    private Size actualAreaSize = new Size();
    private float angle;
    private Paint arcPaint = new Paint(1);
    private RectF arcRect = new RectF();
    private Point centerPoint = new Point(0.5f, 0.5f);
    private boolean checkForMoving = true;
    private boolean checkForZooming;
    private PhotoFilterLinearBlurControlDelegate delegate;
    private float falloff = 0.15f;
    private boolean isMoving;
    private boolean isZooming;
    private Paint paint = new Paint(1);
    private float pointerScale = 1.0f;
    private float pointerStartX;
    private float pointerStartY;
    private float size = 0.35f;
    private Point startCenterPoint = new Point();
    private float startDistance;
    private float startPointerDistance;
    private float startRadius;
    private int type;

    private enum BlurViewActiveControl {
        BlurViewActiveControlNone,
        BlurViewActiveControlCenter,
        BlurViewActiveControlInnerRadius,
        BlurViewActiveControlOuterRadius,
        BlurViewActiveControlWholeArea,
        BlurViewActiveControlRotation
    }

    public interface PhotoFilterLinearBlurControlDelegate {
        void valueChanged(Point point, float f, float f2, float f3);
    }

    public PhotoFilterBlurControl(Context context) {
        super(context);
        setWillNotDraw(false);
        this.paint.setColor(-1);
        this.arcPaint.setColor(-1);
        this.arcPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.arcPaint.setStyle(Style.STROKE);
    }

    public void setType(int blurType) {
        this.type = blurType;
        invalidate();
    }

    public void setDelegate(PhotoFilterLinearBlurControlDelegate delegate) {
        this.delegate = delegate;
    }

    private float getDistance(MotionEvent event) {
        if (event.getPointerCount() != 2) {
            return 0.0f;
        }
        float x1 = event.getX(0);
        float y1 = event.getY(0);
        float x2 = event.getX(1);
        float y2 = event.getY(1);
        return (float) Math.sqrt((double) (((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2))));
    }

    private float degreesToRadians(float degrees) {
        return (3.1415927f * degrees) / 180.0f;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
            case 5:
                if (event.getPointerCount() == 1) {
                    if (this.checkForMoving && !this.isMoving) {
                        float locationX = event.getX();
                        float locationY = event.getY();
                        Point centerPoint = getActualCenterPoint();
                        Point delta = new Point(locationX - centerPoint.f105x, locationY - centerPoint.f106y);
                        float radialDistance = (float) Math.sqrt((double) ((delta.f105x * delta.f105x) + (delta.f106y * delta.f106y)));
                        float innerRadius = getActualInnerRadius();
                        float outerRadius = getActualOuterRadius();
                        boolean close = Math.abs(outerRadius - innerRadius) < BlurInsetProximity;
                        float innerRadiusOuterInset = close ? 0.0f : BlurViewRadiusInset;
                        float outerRadiusInnerInset = close ? 0.0f : BlurViewRadiusInset;
                        if (this.type == 0) {
                            float distance = (float) Math.abs((((double) delta.f105x) * Math.cos(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)) + (((double) delta.f106y) * Math.sin(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)));
                            if (radialDistance < BlurViewCenterInset) {
                                this.isMoving = true;
                            } else if (distance > innerRadius - BlurViewRadiusInset && distance < innerRadius + innerRadiusOuterInset) {
                                this.isMoving = true;
                            } else if (distance > outerRadius - outerRadiusInnerInset && distance < BlurViewRadiusInset + outerRadius) {
                                this.isMoving = true;
                            } else if (distance <= innerRadius - BlurViewRadiusInset || distance >= BlurViewRadiusInset + outerRadius) {
                                this.isMoving = true;
                            }
                        } else if (this.type == 1) {
                            if (radialDistance < BlurViewCenterInset) {
                                this.isMoving = true;
                            } else if (radialDistance > innerRadius - BlurViewRadiusInset && radialDistance < innerRadius + innerRadiusOuterInset) {
                                this.isMoving = true;
                            } else if (radialDistance > outerRadius - outerRadiusInnerInset && radialDistance < BlurViewRadiusInset + outerRadius) {
                                this.isMoving = true;
                            }
                        }
                        this.checkForMoving = false;
                        if (this.isMoving) {
                            handlePan(1, event);
                            break;
                        }
                    }
                }
                if (this.isMoving) {
                    handlePan(3, event);
                    this.checkForMoving = true;
                    this.isMoving = false;
                }
                if (event.getPointerCount() == 2) {
                    if (this.checkForZooming && !this.isZooming) {
                        handlePinch(1, event);
                        this.isZooming = true;
                        break;
                    }
                }
                handlePinch(3, event);
                this.checkForZooming = true;
                this.isZooming = false;
                break;
                break;
            case 1:
            case 3:
            case 6:
                if (this.isMoving) {
                    handlePan(3, event);
                    this.isMoving = false;
                } else if (this.isZooming) {
                    handlePinch(3, event);
                    this.isZooming = false;
                }
                this.checkForMoving = true;
                this.checkForZooming = true;
                break;
            case 2:
                if (!this.isMoving) {
                    if (this.isZooming) {
                        handlePinch(2, event);
                        break;
                    }
                }
                handlePan(2, event);
                break;
                break;
        }
        return true;
    }

    private void handlePan(int state, MotionEvent event) {
        float locationX = event.getX();
        float locationY = event.getY();
        Point actualCenterPoint = getActualCenterPoint();
        Point delta = new Point(locationX - actualCenterPoint.f105x, locationY - actualCenterPoint.f106y);
        float radialDistance = (float) Math.sqrt((double) ((delta.f105x * delta.f105x) + (delta.f106y * delta.f106y)));
        float shorterSide = this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width;
        float innerRadius = shorterSide * this.falloff;
        float outerRadius = shorterSide * this.size;
        float distance = (float) Math.abs((((double) delta.f105x) * Math.cos(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)) + (((double) delta.f106y) * Math.sin(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)));
        switch (state) {
            case 1:
                this.pointerStartX = event.getX();
                this.pointerStartY = event.getY();
                boolean close = Math.abs(outerRadius - innerRadius) < BlurInsetProximity;
                float innerRadiusOuterInset = close ? 0.0f : BlurViewRadiusInset;
                float outerRadiusInnerInset = close ? 0.0f : BlurViewRadiusInset;
                if (this.type == 0) {
                    if (radialDistance < BlurViewCenterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                        this.startCenterPoint = actualCenterPoint;
                    } else if (distance > innerRadius - BlurViewRadiusInset && distance < innerRadius + innerRadiusOuterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = distance;
                        this.startRadius = innerRadius;
                    } else if (distance > outerRadius - outerRadiusInnerInset && distance < BlurViewRadiusInset + outerRadius) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = distance;
                        this.startRadius = outerRadius;
                    } else if (distance <= innerRadius - BlurViewRadiusInset || distance >= BlurViewRadiusInset + outerRadius) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlRotation;
                    }
                } else if (this.type == 1) {
                    if (radialDistance < BlurViewCenterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                        this.startCenterPoint = actualCenterPoint;
                    } else if (radialDistance > innerRadius - BlurViewRadiusInset && radialDistance < innerRadius + innerRadiusOuterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = radialDistance;
                        this.startRadius = innerRadius;
                    } else if (radialDistance > outerRadius - outerRadiusInnerInset && radialDistance < BlurViewRadiusInset + outerRadius) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = radialDistance;
                        this.startRadius = outerRadius;
                    }
                }
                setSelected(true, true);
                return;
            case 2:
                float translationX;
                float translationY;
                Rect actualArea;
                Point point;
                if (this.type != 0) {
                    if (this.type == 1) {
                        switch (this.activeControl) {
                            case BlurViewActiveControlCenter:
                                translationX = locationX - this.pointerStartX;
                                translationY = locationY - this.pointerStartY;
                                actualArea = new Rect((((float) getWidth()) - this.actualAreaSize.width) / 2.0f, (((float) getHeight()) - this.actualAreaSize.height) / 2.0f, this.actualAreaSize.width, this.actualAreaSize.height);
                                point = new Point(Math.max(actualArea.f107x, Math.min(actualArea.f107x + actualArea.width, this.startCenterPoint.f105x + translationX)), Math.max(actualArea.f108y, Math.min(actualArea.f108y + actualArea.height, this.startCenterPoint.f106y + translationY)));
                                this.centerPoint = new Point((point.f105x - actualArea.f107x) / this.actualAreaSize.width, ((point.f106y - actualArea.f108y) + ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) / this.actualAreaSize.width);
                                break;
                            case BlurViewActiveControlInnerRadius:
                                this.falloff = Math.min(Math.max(0.1f, (this.startRadius + (radialDistance - this.startDistance)) / shorterSide), this.size - BlurMinimumDifference);
                                break;
                            case BlurViewActiveControlOuterRadius:
                                this.size = Math.max(this.falloff + BlurMinimumDifference, (this.startRadius + (radialDistance - this.startDistance)) / shorterSide);
                                break;
                            default:
                                break;
                        }
                    }
                }
                switch (this.activeControl) {
                    case BlurViewActiveControlCenter:
                        translationX = locationX - this.pointerStartX;
                        translationY = locationY - this.pointerStartY;
                        actualArea = new Rect((((float) getWidth()) - this.actualAreaSize.width) / 2.0f, (((float) getHeight()) - this.actualAreaSize.height) / 2.0f, this.actualAreaSize.width, this.actualAreaSize.height);
                        point = new Point(Math.max(actualArea.f107x, Math.min(actualArea.f107x + actualArea.width, this.startCenterPoint.f105x + translationX)), Math.max(actualArea.f108y, Math.min(actualArea.f108y + actualArea.height, this.startCenterPoint.f106y + translationY)));
                        this.centerPoint = new Point((point.f105x - actualArea.f107x) / this.actualAreaSize.width, ((point.f106y - actualArea.f108y) + ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) / this.actualAreaSize.width);
                        break;
                    case BlurViewActiveControlInnerRadius:
                        this.falloff = Math.min(Math.max(0.1f, (this.startRadius + (distance - this.startDistance)) / shorterSide), this.size - BlurMinimumDifference);
                        break;
                    case BlurViewActiveControlOuterRadius:
                        this.size = Math.max(this.falloff + BlurMinimumDifference, (this.startRadius + (distance - this.startDistance)) / shorterSide);
                        break;
                    case BlurViewActiveControlRotation:
                        translationX = locationX - this.pointerStartX;
                        translationY = locationY - this.pointerStartY;
                        boolean clockwise = false;
                        boolean right = locationX > actualCenterPoint.f105x;
                        boolean bottom = locationY > actualCenterPoint.f106y;
                        if (right || bottom) {
                            if (!right || bottom) {
                                if (right && bottom) {
                                    if (Math.abs(translationY) > Math.abs(translationX)) {
                                        if (translationY > 0.0f) {
                                            clockwise = true;
                                        }
                                    } else if (translationX < 0.0f) {
                                        clockwise = true;
                                    }
                                } else if (Math.abs(translationY) > Math.abs(translationX)) {
                                    if (translationY < 0.0f) {
                                        clockwise = true;
                                    }
                                } else if (translationX < 0.0f) {
                                    clockwise = true;
                                }
                            } else if (Math.abs(translationY) > Math.abs(translationX)) {
                                if (translationY > 0.0f) {
                                    clockwise = true;
                                }
                            } else if (translationX > 0.0f) {
                                clockwise = true;
                            }
                        } else if (Math.abs(translationY) > Math.abs(translationX)) {
                            if (translationY < 0.0f) {
                                clockwise = true;
                            }
                        } else if (translationX > 0.0f) {
                            clockwise = true;
                        }
                        this.angle = (((((float) (((clockwise ? 1 : 0) * 2) - 1)) * ((float) Math.sqrt((double) ((translationX * translationX) + (translationY * translationY))))) / 3.1415927f) / 1.15f) + this.angle;
                        this.pointerStartX = locationX;
                        this.pointerStartY = locationY;
                        break;
                }
                invalidate();
                if (this.delegate != null) {
                    this.delegate.valueChanged(this.centerPoint, this.falloff, this.size, degreesToRadians(this.angle) + 1.5707964f);
                    return;
                }
                return;
            case 3:
            case 4:
            case 5:
                this.activeControl = BlurViewActiveControl.BlurViewActiveControlNone;
                setSelected(false, true);
                return;
            default:
                return;
        }
    }

    private void handlePinch(int state, MotionEvent event) {
        switch (state) {
            case 1:
                this.startPointerDistance = getDistance(event);
                this.pointerScale = 1.0f;
                this.activeControl = BlurViewActiveControl.BlurViewActiveControlWholeArea;
                setSelected(true, true);
                break;
            case 2:
                break;
            case 3:
            case 4:
            case 5:
                this.activeControl = BlurViewActiveControl.BlurViewActiveControlNone;
                setSelected(false, true);
                return;
            default:
                return;
        }
        float newDistance = getDistance(event);
        this.pointerScale += ((newDistance - this.startPointerDistance) / AndroidUtilities.density) * 0.01f;
        this.falloff = Math.max(0.1f, this.falloff * this.pointerScale);
        this.size = Math.max(this.falloff + BlurMinimumDifference, this.size * this.pointerScale);
        this.pointerScale = 1.0f;
        this.startPointerDistance = newDistance;
        invalidate();
        if (this.delegate != null) {
            this.delegate.valueChanged(this.centerPoint, this.falloff, this.size, degreesToRadians(this.angle) + 1.5707964f);
        }
    }

    private void setSelected(boolean selected, boolean animated) {
    }

    public void setActualAreaSize(float width, float height) {
        this.actualAreaSize.width = width;
        this.actualAreaSize.height = height;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Point centerPoint = getActualCenterPoint();
        float innerRadius = getActualInnerRadius();
        float outerRadius = getActualOuterRadius();
        canvas.translate(centerPoint.f105x, centerPoint.f106y);
        int i;
        Canvas canvas2;
        if (this.type == 0) {
            float f;
            canvas.rotate(this.angle);
            float space = (float) AndroidUtilities.dp(6.0f);
            float length = (float) AndroidUtilities.dp(12.0f);
            float thickness = (float) AndroidUtilities.dp(1.5f);
            for (i = 0; i < 30; i++) {
                canvas2 = canvas;
                canvas2.drawRect((length + space) * ((float) i), -innerRadius, (((float) i) * (length + space)) + length, thickness - innerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (length + space)) - space) - length, -innerRadius, (((float) (-i)) * (length + space)) - space, thickness - innerRadius, this.paint);
                canvas2 = canvas;
                f = innerRadius;
                canvas2.drawRect((length + space) * ((float) i), f, length + (((float) i) * (length + space)), thickness + innerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (length + space)) - space) - length, innerRadius, (((float) (-i)) * (length + space)) - space, thickness + innerRadius, this.paint);
            }
            length = (float) AndroidUtilities.dp(6.0f);
            for (i = 0; i < 64; i++) {
                canvas2 = canvas;
                canvas2.drawRect((length + space) * ((float) i), -outerRadius, length + (((float) i) * (length + space)), thickness - outerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (length + space)) - space) - length, -outerRadius, (((float) (-i)) * (length + space)) - space, thickness - outerRadius, this.paint);
                canvas2 = canvas;
                f = outerRadius;
                canvas2.drawRect((length + space) * ((float) i), f, length + (((float) i) * (length + space)), thickness + outerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (length + space)) - space) - length, outerRadius, (((float) (-i)) * (length + space)) - space, thickness + outerRadius, this.paint);
            }
        } else if (this.type == 1) {
            this.arcRect.set(-innerRadius, -innerRadius, innerRadius, innerRadius);
            for (i = 0; i < 22; i++) {
                canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (6.15f + 10.2f) * ((float) i), 10.2f, false, this.arcPaint);
            }
            this.arcRect.set(-outerRadius, -outerRadius, outerRadius, outerRadius);
            for (i = 0; i < 64; i++) {
                canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (2.02f + 3.6f) * ((float) i), 3.6f, false, this.arcPaint);
            }
        }
        canvas.drawCircle(0.0f, 0.0f, (float) AndroidUtilities.dp(8.0f), this.paint);
    }

    private Point getActualCenterPoint() {
        return new Point((this.centerPoint.f105x * this.actualAreaSize.width) + ((((float) getWidth()) - this.actualAreaSize.width) / 2.0f), ((((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) + ((((float) getHeight()) - this.actualAreaSize.height) / 2.0f)) - ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) + (this.centerPoint.f106y * this.actualAreaSize.width));
    }

    private float getActualInnerRadius() {
        return (this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width) * this.falloff;
    }

    private float getActualOuterRadius() {
        return (this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width) * this.size;
    }
}
