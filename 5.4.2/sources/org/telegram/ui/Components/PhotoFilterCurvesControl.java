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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
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

    public PhotoFilterCurvesControl(Context context, CurvesToolValue curvesToolValue) {
        super(context);
        setWillNotDraw(false);
        this.curveValue = curvesToolValue;
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

    private void handlePan(int i, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (i) {
            case 1:
                selectSegmentWithPoint(x);
                return;
            case 2:
                float min = Math.min(2.0f, (this.lastY - y) / 8.0f);
                CurvesValue curvesValue = null;
                switch (this.curveValue.activeType) {
                    case 0:
                        curvesValue = this.curveValue.luminanceCurve;
                        break;
                    case 1:
                        curvesValue = this.curveValue.redCurve;
                        break;
                    case 2:
                        curvesValue = this.curveValue.greenCurve;
                        break;
                    case 3:
                        curvesValue = this.curveValue.blueCurve;
                        break;
                }
                switch (this.activeSegment) {
                    case 1:
                        curvesValue.blacksLevel = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(100.0f, min + curvesValue.blacksLevel));
                        break;
                    case 2:
                        curvesValue.shadowsLevel = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(100.0f, min + curvesValue.shadowsLevel));
                        break;
                    case 3:
                        curvesValue.midtonesLevel = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(100.0f, min + curvesValue.midtonesLevel));
                        break;
                    case 4:
                        curvesValue.highlightsLevel = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(100.0f, min + curvesValue.highlightsLevel));
                        break;
                    case 5:
                        curvesValue.whitesLevel = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(100.0f, min + curvesValue.whitesLevel));
                        break;
                }
                invalidate();
                if (this.delegate != null) {
                    this.delegate.valueChanged();
                }
                this.lastX = x;
                this.lastY = y;
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

    private void selectSegmentWithPoint(float f) {
        if (this.activeSegment == 0) {
            this.activeSegment = (int) Math.floor((double) (((f - this.actualArea.f10186x) / (this.actualArea.width / 5.0f)) + 1.0f));
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
        float f = this.actualArea.width / 5.0f;
        for (int i = 0; i < 4; i++) {
            canvas2 = canvas;
            canvas2.drawLine((((float) i) * f) + (this.actualArea.f10186x + f), this.actualArea.f10187y, (((float) i) * f) + (this.actualArea.f10186x + f), this.actualArea.height + this.actualArea.f10187y, this.paint);
        }
        canvas2 = canvas;
        canvas2.drawLine(this.actualArea.f10186x, this.actualArea.height + this.actualArea.f10187y, this.actualArea.width + this.actualArea.f10186x, this.actualArea.f10187y, this.paintDash);
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
        for (int i2 = 0; i2 < 5; i2++) {
            String format;
            switch (i2) {
                case 0:
                    format = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.blacksLevel / 100.0f)});
                    break;
                case 1:
                    format = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.shadowsLevel / 100.0f)});
                    break;
                case 2:
                    format = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.midtonesLevel / 100.0f)});
                    break;
                case 3:
                    format = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.highlightsLevel / 100.0f)});
                    break;
                case 4:
                    format = String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(curvesValue.whitesLevel / 100.0f)});
                    break;
                default:
                    format = TtmlNode.ANONYMOUS_REGION_ID;
                    break;
            }
            canvas.drawText(format, (((f - this.textPaint.measureText(format)) / 2.0f) + this.actualArea.f10186x) + (((float) i2) * f), (this.actualArea.f10187y + this.actualArea.height) - ((float) AndroidUtilities.dp(4.0f)), this.textPaint);
        }
        float[] interpolateCurve = curvesValue.interpolateCurve();
        invalidate();
        this.path.reset();
        for (int i3 = 0; i3 < interpolateCurve.length / 2; i3++) {
            if (i3 == 0) {
                this.path.moveTo(this.actualArea.f10186x + (interpolateCurve[i3 * 2] * this.actualArea.width), this.actualArea.f10187y + ((1.0f - interpolateCurve[(i3 * 2) + 1]) * this.actualArea.height));
            } else {
                this.path.lineTo(this.actualArea.f10186x + (interpolateCurve[i3 * 2] * this.actualArea.width), this.actualArea.f10187y + ((1.0f - interpolateCurve[(i3 * 2) + 1]) * this.actualArea.height));
            }
        }
        canvas.drawPath(this.path, this.paintCurve);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
            case 5:
                if (motionEvent.getPointerCount() != 1) {
                    if (this.isMoving) {
                        handlePan(3, motionEvent);
                        this.checkForMoving = true;
                        this.isMoving = false;
                        break;
                    }
                } else if (this.checkForMoving && !this.isMoving) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    this.lastX = x;
                    this.lastY = y;
                    if (x >= this.actualArea.f10186x && x <= this.actualArea.f10186x + this.actualArea.width && y >= this.actualArea.f10187y && y <= this.actualArea.f10187y + this.actualArea.height) {
                        this.isMoving = true;
                    }
                    this.checkForMoving = false;
                    if (this.isMoving) {
                        handlePan(1, motionEvent);
                        break;
                    }
                }
                break;
            case 1:
            case 3:
            case 6:
                if (this.isMoving) {
                    handlePan(3, motionEvent);
                    this.isMoving = false;
                }
                this.checkForMoving = true;
                break;
            case 2:
                if (this.isMoving) {
                    handlePan(2, motionEvent);
                    break;
                }
                break;
        }
        return true;
    }

    public void setActualArea(float f, float f2, float f3, float f4) {
        this.actualArea.f10186x = f;
        this.actualArea.f10187y = f2;
        this.actualArea.width = f3;
        this.actualArea.height = f4;
    }

    public void setDelegate(PhotoFilterCurvesControlDelegate photoFilterCurvesControlDelegate) {
        this.delegate = photoFilterCurvesControlDelegate;
    }
}
