package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.PhotoFilterView.CurvesToolValue;
import org.telegram.ui.Components.PhotoFilterView.CurvesValue;

public class PhotoFilterCurvesControl extends View {
    private static final int CurvesSegmentBlacks = 1;
    private static final int CurvesSegmentHighlights = 4;
    private static final int CurvesSegmentMidtones = 3;
    private static final int CurvesSegmentNone = 0;
    private static final int CurvesSegmentShadows = 2;
    private static final int CurvesSegmentWhites = 5;
    private static final int GestureStateBegan = 1;
    private static final int GestureStateCancelled = 4;
    private static final int GestureStateChanged = 2;
    private static final int GestureStateEnded = 3;
    private static final int GestureStateFailed = 5;
    private int activeSegment = 0;
    private Rect actualArea = new Rect();
    private boolean checkForMoving = true;
    private CurvesToolValue curveValue;
    private PhotoFilterCurvesControlDelegate delegate;
    private boolean isMoving;
    private float lastX;
    private float lastY;
    private Paint paint = new Paint(1);
    private Paint paintCurve = new Paint(1);
    private Paint paintDash = new Paint(1);
    private Path path = new Path();
    private TextPaint textPaint = new TextPaint(1);

    public interface PhotoFilterCurvesControlDelegate {
        void valueChanged();
    }

    public PhotoFilterCurvesControl(Context context, CurvesToolValue value) {
        super(context);
        setWillNotDraw(false);
        this.curveValue = value;
        this.paint.setColor(-1711276033);
        this.paint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
        this.paint.setStyle(Style.STROKE);
        this.paintDash.setColor(-1711276033);
        this.paintDash.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.paintDash.setStyle(Style.STROKE);
        this.paintCurve.setColor(-1);
        this.paintCurve.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.paintCurve.setStyle(Style.STROKE);
        this.textPaint.setColor(-4210753);
        this.textPaint.setTextSize((float) AndroidUtilities.dp(13.0f));
    }

    public void setDelegate(PhotoFilterCurvesControlDelegate photoFilterCurvesControlDelegate) {
        this.delegate = photoFilterCurvesControlDelegate;
    }

    public void setActualArea(float x, float y, float width, float height) {
        this.actualArea.f107x = x;
        this.actualArea.f108y = y;
        this.actualArea.width = width;
        this.actualArea.height = height;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
            case 5:
                if (event.getPointerCount() != 1) {
                    if (this.isMoving) {
                        handlePan(3, event);
                        this.checkForMoving = true;
                        this.isMoving = false;
                        break;
                    }
                } else if (this.checkForMoving && !this.isMoving) {
                    float locationX = event.getX();
                    float locationY = event.getY();
                    this.lastX = locationX;
                    this.lastY = locationY;
                    if (locationX >= this.actualArea.f107x && locationX <= this.actualArea.f107x + this.actualArea.width && locationY >= this.actualArea.f108y && locationY <= this.actualArea.f108y + this.actualArea.height) {
                        this.isMoving = true;
                    }
                    this.checkForMoving = false;
                    if (this.isMoving) {
                        handlePan(1, event);
                        break;
                    }
                }
                break;
            case 1:
            case 3:
            case 6:
                if (this.isMoving) {
                    handlePan(3, event);
                    this.isMoving = false;
                }
                this.checkForMoving = true;
                break;
            case 2:
                if (this.isMoving) {
                    handlePan(2, event);
                    break;
                }
                break;
        }
        return true;
    }

    private void handlePan(int state, MotionEvent event) {
        float locationX = event.getX();
        float locationY = event.getY();
        switch (state) {
            case 1:
                selectSegmentWithPoint(locationX);
                return;
            case 2:
                float delta = Math.min(2.0f, (this.lastY - locationY) / 8.0f);
                CurvesValue curveValue = null;
                switch (this.curveValue.activeType) {
                    case 0:
                        curveValue = this.curveValue.luminanceCurve;
                        break;
                    case 1:
                        curveValue = this.curveValue.redCurve;
                        break;
                    case 2:
                        curveValue = this.curveValue.greenCurve;
                        break;
                    case 3:
                        curveValue = this.curveValue.blueCurve;
                        break;
                }
                switch (this.activeSegment) {
                    case 1:
                        curveValue.blacksLevel = Math.max(0.0f, Math.min(100.0f, curveValue.blacksLevel + delta));
                        break;
                    case 2:
                        curveValue.shadowsLevel = Math.max(0.0f, Math.min(100.0f, curveValue.shadowsLevel + delta));
                        break;
                    case 3:
                        curveValue.midtonesLevel = Math.max(0.0f, Math.min(100.0f, curveValue.midtonesLevel + delta));
                        break;
                    case 4:
                        curveValue.highlightsLevel = Math.max(0.0f, Math.min(100.0f, curveValue.highlightsLevel + delta));
                        break;
                    case 5:
                        curveValue.whitesLevel = Math.max(0.0f, Math.min(100.0f, curveValue.whitesLevel + delta));
                        break;
                }
                invalidate();
                if (this.delegate != null) {
                    this.delegate.valueChanged();
                }
                this.lastX = locationX;
                this.lastY = locationY;
                return;
            case 3:
            case 4:
            case 5:
                unselectSegments();
                return;
            default:
                return;
        }
    }

    private void selectSegmentWithPoint(float pointx) {
        if (this.activeSegment == 0) {
            this.activeSegment = (int) Math.floor((double) (((pointx - this.actualArea.f107x) / (this.actualArea.width / 5.0f)) + 1.0f));
        }
    }

    private void unselectSegments() {
        if (this.activeSegment != 0) {
            this.activeSegment = 0;
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        int a;
        float segmentWidth = this.actualArea.width / 5.0f;
        for (int i = 0; i < 4; i++) {
            canvas2 = canvas;
            canvas2.drawLine((((float) i) * segmentWidth) + (this.actualArea.f107x + segmentWidth), this.actualArea.f108y, (((float) i) * segmentWidth) + (this.actualArea.f107x + segmentWidth), this.actualArea.height + this.actualArea.f108y, this.paint);
        }
        canvas2 = canvas;
        canvas2.drawLine(this.actualArea.f107x, this.actualArea.height + this.actualArea.f108y, this.actualArea.width + this.actualArea.f107x, this.actualArea.f108y, this.paintDash);
        CurvesValue curvesValue = null;
        switch (this.curveValue.activeType) {
            case 0:
                this.paintCurve.setColor(-1);
                curvesValue = this.curveValue.luminanceCurve;
                break;
            case 1:
                this.paintCurve.setColor(-1229492);
                curvesValue = this.curveValue.redCurve;
                break;
            case 2:
                this.paintCurve.setColor(-15667555);
                curvesValue = this.curveValue.greenCurve;
                break;
            case 3:
                this.paintCurve.setColor(-13404165);
                curvesValue = this.curveValue.blueCurve;
                break;
        }
        for (a = 0; a < 5; a++) {
            String str;
            switch (a) {
                case 0:
                    str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.blacksLevel / 100.0f)});
                    break;
                case 1:
                    str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.shadowsLevel / 100.0f)});
                    break;
                case 2:
                    str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.midtonesLevel / 100.0f)});
                    break;
                case 3:
                    str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.highlightsLevel / 100.0f)});
                    break;
                case 4:
                    str = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.whitesLevel / 100.0f)});
                    break;
                default:
                    str = "";
                    break;
            }
            canvas.drawText(str, (this.actualArea.f107x + ((segmentWidth - this.textPaint.measureText(str)) / 2.0f)) + (((float) a) * segmentWidth), (this.actualArea.f108y + this.actualArea.height) - ((float) AndroidUtilities.dp(4.0f)), this.textPaint);
        }
        float[] points = curvesValue.interpolateCurve();
        invalidate();
        this.path.reset();
        for (a = 0; a < points.length / 2; a++) {
            if (a == 0) {
                this.path.moveTo(this.actualArea.f107x + (points[a * 2] * this.actualArea.width), this.actualArea.f108y + ((1.0f - points[(a * 2) + 1]) * this.actualArea.height));
            } else {
                this.path.lineTo(this.actualArea.f107x + (points[a * 2] * this.actualArea.width), this.actualArea.f108y + ((1.0f - points[(a * 2) + 1]) * this.actualArea.height));
            }
        }
        canvas.drawPath(this.path, this.paintCurve);
    }
}
