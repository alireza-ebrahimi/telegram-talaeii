package utils.view.VideoController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;

public class LightView extends View {
    /* renamed from: a */
    float f10316a = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: b */
    float f10317b = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: c */
    float f10318c = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: d */
    float f10319d = 15.0f;
    /* renamed from: e */
    float f10320e = 30.0f;
    /* renamed from: f */
    Paint f10321f;
    /* renamed from: g */
    float f10322g = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: h */
    Bitmap f10323h;
    /* renamed from: i */
    RectF f10324i;

    public LightView(Context context) {
        super(context);
        m14200a(context);
    }

    public LightView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14200a(context);
    }

    public LightView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14200a(context);
    }

    /* renamed from: a */
    void m14200a(Context context) {
        this.f10321f = new Paint(1);
        this.f10321f.setStyle(Style.STROKE);
        try {
            this.f10323h = BitmapFactory.decodeResource(getResources(), R.drawable.light);
        } catch (Exception e) {
        }
    }

    protected void onDraw(Canvas canvas) {
        float measuredWidth = (float) (getMeasuredWidth() / 2);
        float measuredHeight = (float) (getMeasuredHeight() / 2);
        this.f10316a = measuredWidth - (this.f10319d / 2.0f);
        this.f10317b = (measuredWidth - (this.f10319d / 2.0f)) - (this.f10320e / 2.0f);
        this.f10318c = (measuredWidth - (this.f10319d / 2.0f)) - this.f10320e;
        this.f10321f.setStrokeWidth(this.f10319d);
        this.f10321f.setColor(Color.parseColor("#454547"));
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10316a, this.f10321f);
        this.f10321f.setColor(Color.parseColor("#747476"));
        this.f10321f.setStrokeWidth(this.f10320e);
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10317b, this.f10321f);
        this.f10321f.setColor(Color.parseColor("#464648"));
        this.f10321f.setStyle(Style.FILL);
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10318c, this.f10321f);
        canvas.drawBitmap(this.f10323h, measuredWidth - ((float) (this.f10323h.getWidth() / 2)), measuredWidth - ((float) (this.f10323h.getHeight() / 2)), this.f10321f);
        this.f10321f.setColor(-1);
        this.f10321f.setStrokeWidth(BitmapDescriptorFactory.HUE_RED);
        this.f10321f.setTextSize(40.0f);
        try {
            canvas.drawText(getContext().getString(R.string.light_fa), measuredWidth - (this.f10321f.measureText(getContext().getString(R.string.light_fa)) / 2.0f), (((float) (this.f10323h.getHeight() / 2)) + measuredWidth) + 40.0f, this.f10321f);
        } catch (Exception e) {
        }
        this.f10321f.setStyle(Style.STROKE);
        this.f10321f.setStrokeWidth(this.f10320e);
        this.f10321f.setColor(-1);
        if (this.f10324i == null) {
            this.f10324i = new RectF(measuredWidth - this.f10317b, measuredHeight - this.f10317b, measuredWidth + this.f10317b, measuredHeight + this.f10317b);
        }
        canvas.drawArc(this.f10324i, 270.0f, (360.0f * this.f10322g) / 100.0f, false, this.f10321f);
        super.onDraw(canvas);
    }

    public void setProgress(float f) {
        this.f10322g = f;
        if (this.f10322g >= 100.0f) {
            this.f10322g = 100.0f;
        }
        if (this.f10322g <= BitmapDescriptorFactory.HUE_RED) {
            this.f10322g = BitmapDescriptorFactory.HUE_RED;
        }
        postInvalidate();
    }
}
