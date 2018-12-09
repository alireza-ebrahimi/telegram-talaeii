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

    private float degreesToRadians(float f) {
        return (3.1415927f * f) / 180.0f;
    }

    private Point getActualCenterPoint() {
        return new Point((this.centerPoint.f10184x * this.actualAreaSize.width) + ((((float) getWidth()) - this.actualAreaSize.width) / 2.0f), ((((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) + ((((float) getHeight()) - this.actualAreaSize.height) / 2.0f)) - ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) + (this.centerPoint.f10185y * this.actualAreaSize.width));
    }

    private float getActualInnerRadius() {
        return (this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width) * this.falloff;
    }

    private float getActualOuterRadius() {
        return (this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width) * this.size;
    }

    private float getDistance(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        float x2 = motionEvent.getX(1);
        float y2 = motionEvent.getY(1);
        return (float) Math.sqrt((double) (((x - x2) * (x - x2)) + ((y - y2) * (y - y2))));
    }

    private void handlePan(int i, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Point actualCenterPoint = getActualCenterPoint();
        Point point = new Point(x - actualCenterPoint.f10184x, y - actualCenterPoint.f10185y);
        float sqrt = (float) Math.sqrt((double) ((point.f10184x * point.f10184x) + (point.f10185y * point.f10185y)));
        float f = this.actualAreaSize.width > this.actualAreaSize.height ? this.actualAreaSize.height : this.actualAreaSize.width;
        float f2 = f * this.falloff;
        float f3 = f * this.size;
        float abs = (float) Math.abs((((double) point.f10184x) * Math.cos(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)) + (((double) point.f10185y) * Math.sin(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)));
        Object obj;
        float f4;
        switch (i) {
            case 1:
                this.pointerStartX = motionEvent.getX();
                this.pointerStartY = motionEvent.getY();
                obj = Math.abs(f3 - f2) < BlurInsetProximity ? 1 : null;
                f4 = obj != null ? 0.0f : BlurViewRadiusInset;
                f = obj != null ? 0.0f : BlurViewRadiusInset;
                if (this.type == 0) {
                    if (sqrt < BlurViewCenterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                        this.startCenterPoint = actualCenterPoint;
                    } else if (abs > f2 - BlurViewRadiusInset && abs < f4 + f2) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = abs;
                        this.startRadius = f2;
                    } else if (abs > f3 - f && abs < BlurViewRadiusInset + f3) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = abs;
                        this.startRadius = f3;
                    } else if (abs <= f2 - BlurViewRadiusInset || abs >= BlurViewRadiusInset + f3) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlRotation;
                    }
                } else if (this.type == 1) {
                    if (sqrt < BlurViewCenterInset) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                        this.startCenterPoint = actualCenterPoint;
                    } else if (sqrt > f2 - BlurViewRadiusInset && sqrt < f4 + f2) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = sqrt;
                        this.startRadius = f2;
                    } else if (sqrt > f3 - f && sqrt < BlurViewRadiusInset + f3) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = sqrt;
                        this.startRadius = f3;
                    }
                }
                setSelected(true, true);
                return;
            case 2:
                Rect rect;
                Point point2;
                if (this.type != 0) {
                    if (this.type == 1) {
                        switch (this.activeControl) {
                            case BlurViewActiveControlCenter:
                                f = x - this.pointerStartX;
                                f4 = y - this.pointerStartY;
                                rect = new Rect((((float) getWidth()) - this.actualAreaSize.width) / 2.0f, (((float) getHeight()) - this.actualAreaSize.height) / 2.0f, this.actualAreaSize.width, this.actualAreaSize.height);
                                point2 = new Point(Math.max(rect.f10186x, Math.min(rect.f10186x + rect.width, f + this.startCenterPoint.f10184x)), Math.max(rect.f10187y, Math.min(rect.f10187y + rect.height, f4 + this.startCenterPoint.f10185y)));
                                this.centerPoint = new Point((point2.f10184x - rect.f10186x) / this.actualAreaSize.width, ((point2.f10185y - rect.f10187y) + ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) / this.actualAreaSize.width);
                                break;
                            case BlurViewActiveControlInnerRadius:
                                this.falloff = Math.min(Math.max(0.1f, ((sqrt - this.startDistance) + this.startRadius) / f), this.size - BlurMinimumDifference);
                                break;
                            case BlurViewActiveControlOuterRadius:
                                this.size = Math.max(this.falloff + BlurMinimumDifference, ((sqrt - this.startDistance) + this.startRadius) / f);
                                break;
                            default:
                                break;
                        }
                    }
                }
                switch (this.activeControl) {
                    case BlurViewActiveControlCenter:
                        f = x - this.pointerStartX;
                        f4 = y - this.pointerStartY;
                        rect = new Rect((((float) getWidth()) - this.actualAreaSize.width) / 2.0f, (((float) getHeight()) - this.actualAreaSize.height) / 2.0f, this.actualAreaSize.width, this.actualAreaSize.height);
                        point2 = new Point(Math.max(rect.f10186x, Math.min(rect.f10186x + rect.width, f + this.startCenterPoint.f10184x)), Math.max(rect.f10187y, Math.min(rect.f10187y + rect.height, f4 + this.startCenterPoint.f10185y)));
                        this.centerPoint = new Point((point2.f10184x - rect.f10186x) / this.actualAreaSize.width, ((point2.f10185y - rect.f10187y) + ((this.actualAreaSize.width - this.actualAreaSize.height) / 2.0f)) / this.actualAreaSize.width);
                        break;
                    case BlurViewActiveControlInnerRadius:
                        this.falloff = Math.min(Math.max(0.1f, ((abs - this.startDistance) + this.startRadius) / f), this.size - BlurMinimumDifference);
                        break;
                    case BlurViewActiveControlOuterRadius:
                        this.size = Math.max(this.falloff + BlurMinimumDifference, ((abs - this.startDistance) + this.startRadius) / f);
                        break;
                    case BlurViewActiveControlRotation:
                        sqrt = x - this.pointerStartX;
                        f2 = y - this.pointerStartY;
                        Object obj2 = x > actualCenterPoint.f10184x ? 1 : null;
                        obj = y > actualCenterPoint.f10185y ? 1 : null;
                        if (obj2 == null && obj == null) {
                            if (Math.abs(f2) > Math.abs(sqrt)) {
                                if (f2 < 0.0f) {
                                    obj2 = 1;
                                }
                                obj2 = null;
                            } else {
                                if (sqrt > 0.0f) {
                                    obj2 = 1;
                                }
                                obj2 = null;
                            }
                        } else if (obj2 == null || obj != null) {
                            if (obj2 == null || obj == null) {
                                if (Math.abs(f2) > Math.abs(sqrt)) {
                                    if (f2 < 0.0f) {
                                        obj2 = 1;
                                    }
                                    obj2 = null;
                                } else {
                                    if (sqrt < 0.0f) {
                                        obj2 = 1;
                                    }
                                    obj2 = null;
                                }
                            } else if (Math.abs(f2) > Math.abs(sqrt)) {
                                if (f2 > 0.0f) {
                                    obj2 = 1;
                                }
                                obj2 = null;
                            } else {
                                if (sqrt < 0.0f) {
                                    obj2 = 1;
                                }
                                obj2 = null;
                            }
                        } else if (Math.abs(f2) > Math.abs(sqrt)) {
                            if (f2 > 0.0f) {
                                obj2 = 1;
                            }
                            obj2 = null;
                        } else {
                            if (sqrt > 0.0f) {
                                obj2 = 1;
                            }
                            obj2 = null;
                        }
                        this.angle = (((((float) (((obj2 != null ? 1 : 0) * 2) - 1)) * ((float) Math.sqrt((double) ((sqrt * sqrt) + (f2 * f2))))) / 3.1415927f) / 1.15f) + this.angle;
                        this.pointerStartX = x;
                        this.pointerStartY = y;
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

    private void handlePinch(int i, MotionEvent motionEvent) {
        switch (i) {
            case 1:
                this.startPointerDistance = getDistance(motionEvent);
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
        float distance = getDistance(motionEvent);
        this.pointerScale += ((distance - this.startPointerDistance) / AndroidUtilities.density) * 0.01f;
        this.falloff = Math.max(0.1f, this.falloff * this.pointerScale);
        this.size = Math.max(this.falloff + BlurMinimumDifference, this.size * this.pointerScale);
        this.pointerScale = 1.0f;
        this.startPointerDistance = distance;
        invalidate();
        if (this.delegate != null) {
            this.delegate.valueChanged(this.centerPoint, this.falloff, this.size, degreesToRadians(this.angle) + 1.5707964f);
        }
    }

    private void setSelected(boolean z, boolean z2) {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Point actualCenterPoint = getActualCenterPoint();
        float actualInnerRadius = getActualInnerRadius();
        float actualOuterRadius = getActualOuterRadius();
        canvas.translate(actualCenterPoint.f10184x, actualCenterPoint.f10185y);
        int i;
        Canvas canvas2;
        if (this.type == 0) {
            float f;
            canvas.rotate(this.angle);
            float dp = (float) AndroidUtilities.dp(6.0f);
            float dp2 = (float) AndroidUtilities.dp(12.0f);
            float dp3 = (float) AndroidUtilities.dp(1.5f);
            for (i = 0; i < 30; i++) {
                canvas2 = canvas;
                canvas2.drawRect((dp2 + dp) * ((float) i), -actualInnerRadius, (((float) i) * (dp2 + dp)) + dp2, dp3 - actualInnerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (dp2 + dp)) - dp) - dp2, -actualInnerRadius, (((float) (-i)) * (dp2 + dp)) - dp, dp3 - actualInnerRadius, this.paint);
                canvas2 = canvas;
                f = actualInnerRadius;
                canvas2.drawRect((dp2 + dp) * ((float) i), f, dp2 + (((float) i) * (dp2 + dp)), dp3 + actualInnerRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (dp2 + dp)) - dp) - dp2, actualInnerRadius, (((float) (-i)) * (dp2 + dp)) - dp, dp3 + actualInnerRadius, this.paint);
            }
            actualInnerRadius = (float) AndroidUtilities.dp(6.0f);
            for (i = 0; i < 64; i++) {
                canvas2 = canvas;
                canvas2.drawRect((actualInnerRadius + dp) * ((float) i), -actualOuterRadius, actualInnerRadius + (((float) i) * (actualInnerRadius + dp)), dp3 - actualOuterRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (actualInnerRadius + dp)) - dp) - actualInnerRadius, -actualOuterRadius, (((float) (-i)) * (actualInnerRadius + dp)) - dp, dp3 - actualOuterRadius, this.paint);
                canvas2 = canvas;
                f = actualOuterRadius;
                canvas2.drawRect((actualInnerRadius + dp) * ((float) i), f, actualInnerRadius + (((float) i) * (actualInnerRadius + dp)), dp3 + actualOuterRadius, this.paint);
                canvas.drawRect(((((float) (-i)) * (actualInnerRadius + dp)) - dp) - actualInnerRadius, actualOuterRadius, (((float) (-i)) * (actualInnerRadius + dp)) - dp, dp3 + actualOuterRadius, this.paint);
            }
        } else if (this.type == 1) {
            this.arcRect.set(-actualInnerRadius, -actualInnerRadius, actualInnerRadius, actualInnerRadius);
            for (i = 0; i < 22; i++) {
                canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (6.15f + 10.2f) * ((float) i), 10.2f, false, this.arcPaint);
            }
            this.arcRect.set(-actualOuterRadius, -actualOuterRadius, actualOuterRadius, actualOuterRadius);
            for (i = 0; i < 64; i++) {
                canvas2 = canvas;
                canvas2.drawArc(this.arcRect, (2.02f + 3.6f) * ((float) i), 3.6f, false, this.arcPaint);
            }
        }
        canvas.drawCircle(0.0f, 0.0f, (float) AndroidUtilities.dp(8.0f), this.paint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
            case 5:
                if (motionEvent.getPointerCount() == 1) {
                    if (this.checkForMoving && !this.isMoving) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        Point actualCenterPoint = getActualCenterPoint();
                        Point point = new Point(x - actualCenterPoint.f10184x, y - actualCenterPoint.f10185y);
                        float sqrt = (float) Math.sqrt((double) ((point.f10184x * point.f10184x) + (point.f10185y * point.f10185y)));
                        float actualInnerRadius = getActualInnerRadius();
                        float actualOuterRadius = getActualOuterRadius();
                        Object obj = Math.abs(actualOuterRadius - actualInnerRadius) < BlurInsetProximity ? 1 : null;
                        y = obj != null ? 0.0f : BlurViewRadiusInset;
                        x = obj != null ? 0.0f : BlurViewRadiusInset;
                        if (this.type == 0) {
                            float abs = (float) Math.abs((((double) point.f10185y) * Math.sin(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)) + (((double) point.f10184x) * Math.cos(((double) degreesToRadians(this.angle)) + 1.5707963267948966d)));
                            if (sqrt < BlurViewCenterInset) {
                                this.isMoving = true;
                            } else if (abs > actualInnerRadius - BlurViewRadiusInset && abs < y + actualInnerRadius) {
                                this.isMoving = true;
                            } else if (abs > actualOuterRadius - x && abs < BlurViewRadiusInset + actualOuterRadius) {
                                this.isMoving = true;
                            } else if (abs <= actualInnerRadius - BlurViewRadiusInset || abs >= BlurViewRadiusInset + actualOuterRadius) {
                                this.isMoving = true;
                            }
                        } else if (this.type == 1) {
                            if (sqrt < BlurViewCenterInset) {
                                this.isMoving = true;
                            } else if (sqrt > actualInnerRadius - BlurViewRadiusInset && sqrt < y + actualInnerRadius) {
                                this.isMoving = true;
                            } else if (sqrt > actualOuterRadius - x && sqrt < BlurViewRadiusInset + actualOuterRadius) {
                                this.isMoving = true;
                            }
                        }
                        this.checkForMoving = false;
                        if (this.isMoving) {
                            handlePan(1, motionEvent);
                            break;
                        }
                    }
                }
                if (this.isMoving) {
                    handlePan(3, motionEvent);
                    this.checkForMoving = true;
                    this.isMoving = false;
                }
                if (motionEvent.getPointerCount() == 2) {
                    if (this.checkForZooming && !this.isZooming) {
                        handlePinch(1, motionEvent);
                        this.isZooming = true;
                        break;
                    }
                }
                handlePinch(3, motionEvent);
                this.checkForZooming = true;
                this.isZooming = false;
                break;
                break;
            case 1:
            case 3:
            case 6:
                if (this.isMoving) {
                    handlePan(3, motionEvent);
                    this.isMoving = false;
                } else if (this.isZooming) {
                    handlePinch(3, motionEvent);
                    this.isZooming = false;
                }
                this.checkForMoving = true;
                this.checkForZooming = true;
                break;
            case 2:
                if (!this.isMoving) {
                    if (this.isZooming) {
                        handlePinch(2, motionEvent);
                        break;
                    }
                }
                handlePan(2, motionEvent);
                break;
                break;
        }
        return true;
    }

    public void setActualAreaSize(float f, float f2) {
        this.actualAreaSize.width = f;
        this.actualAreaSize.height = f2;
    }

    public void setDelegate(PhotoFilterLinearBlurControlDelegate photoFilterLinearBlurControlDelegate) {
        this.delegate = photoFilterLinearBlurControlDelegate;
    }

    public void setType(int i) {
        this.type = i;
        invalidate();
    }
}
