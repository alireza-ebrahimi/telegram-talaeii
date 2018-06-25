package com.mikhaellopez.circularprogressbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mikhaellopez.circularprogressbar.C2015a.C2013a;
import com.mikhaellopez.circularprogressbar.C2015a.C2014b;
import org.telegram.ui.ActionBar.Theme;

public class CircularProgressBar extends View {
    /* renamed from: a */
    private float f5930a = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: b */
    private float f5931b = getResources().getDimension(C2013a.default_stroke_width);
    /* renamed from: c */
    private float f5932c = getResources().getDimension(C2013a.default_background_stroke_width);
    /* renamed from: d */
    private int f5933d = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
    /* renamed from: e */
    private int f5934e = -7829368;
    /* renamed from: f */
    private int f5935f = -90;
    /* renamed from: g */
    private RectF f5936g;
    /* renamed from: h */
    private Paint f5937h;
    /* renamed from: i */
    private Paint f5938i;

    public CircularProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m9085a(context, attributeSet);
    }

    /* renamed from: a */
    private void m9085a(Context context, AttributeSet attributeSet) {
        this.f5936g = new RectF();
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C2014b.CircularProgressBar, 0, 0);
        try {
            this.f5930a = obtainStyledAttributes.getFloat(C2014b.CircularProgressBar_cpb_progress, this.f5930a);
            this.f5931b = obtainStyledAttributes.getDimension(C2014b.CircularProgressBar_cpb_progressbar_width, this.f5931b);
            this.f5932c = obtainStyledAttributes.getDimension(C2014b.CircularProgressBar_cpb_background_progressbar_width, this.f5932c);
            this.f5933d = obtainStyledAttributes.getInt(C2014b.CircularProgressBar_cpb_progressbar_color, this.f5933d);
            this.f5934e = obtainStyledAttributes.getInt(C2014b.CircularProgressBar_cpb_background_progressbar_color, this.f5934e);
            this.f5937h = new Paint(1);
            this.f5937h.setColor(this.f5934e);
            this.f5937h.setStyle(Style.STROKE);
            this.f5937h.setStrokeWidth(this.f5932c);
            this.f5938i = new Paint(1);
            this.f5938i.setColor(this.f5933d);
            this.f5938i.setStyle(Style.STROKE);
            this.f5938i.setStrokeWidth(this.f5931b);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    public void m9086a(float f, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "progress", new float[]{f});
        ofFloat.setDuration((long) i);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.start();
    }

    public int getBackgroundColor() {
        return this.f5934e;
    }

    public float getBackgroundProgressBarWidth() {
        return this.f5932c;
    }

    public int getColor() {
        return this.f5933d;
    }

    public float getProgress() {
        return this.f5930a;
    }

    public float getProgressBarWidth() {
        return this.f5931b;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(this.f5936g, this.f5937h);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.f5936g, (float) this.f5935f, (360.0f * this.f5930a) / 100.0f, false, this.f5938i);
    }

    protected void onMeasure(int i, int i2) {
        int min = Math.min(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
        setMeasuredDimension(min, min);
        float f = this.f5931b > this.f5932c ? this.f5931b : this.f5932c;
        this.f5936g.set((f / 2.0f) + BitmapDescriptorFactory.HUE_RED, (f / 2.0f) + BitmapDescriptorFactory.HUE_RED, ((float) min) - (f / 2.0f), ((float) min) - (f / 2.0f));
    }

    public void setBackgroundColor(int i) {
        this.f5934e = i;
        this.f5937h.setColor(i);
        invalidate();
        requestLayout();
    }

    public void setBackgroundProgressBarWidth(float f) {
        this.f5932c = f;
        this.f5937h.setStrokeWidth(f);
        requestLayout();
        invalidate();
    }

    public void setColor(int i) {
        this.f5933d = i;
        this.f5938i.setColor(i);
        invalidate();
        requestLayout();
    }

    public void setProgress(float f) {
        if (f > 100.0f) {
            f = 100.0f;
        }
        this.f5930a = f;
        invalidate();
    }

    public void setProgressBarWidth(float f) {
        this.f5931b = f;
        this.f5938i.setStrokeWidth(f);
        requestLayout();
        invalidate();
    }

    public void setProgressWithAnimation(float f) {
        m9086a(f, (int) ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
    }
}
