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

public class VolumnView extends View {
    /* renamed from: a */
    float f10331a = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: b */
    float f10332b = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: c */
    float f10333c = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: d */
    float f10334d = 15.0f;
    /* renamed from: e */
    float f10335e = 30.0f;
    /* renamed from: f */
    Paint f10336f;
    /* renamed from: g */
    float f10337g = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: h */
    Bitmap f10338h;
    /* renamed from: i */
    RectF f10339i;

    public VolumnView(Context context) {
        super(context);
        m14203a(context);
    }

    public VolumnView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14203a(context);
    }

    public VolumnView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14203a(context);
    }

    /* renamed from: a */
    void m14203a(Context context) {
        this.f10336f = new Paint(1);
        this.f10336f.setStyle(Style.STROKE);
        try {
            this.f10338h = BitmapFactory.decodeResource(getResources(), R.drawable.ling);
        } catch (Exception e) {
        }
    }

    protected void onDraw(Canvas canvas) {
        float measuredWidth = (float) (getMeasuredWidth() / 2);
        float measuredHeight = (float) (getMeasuredHeight() / 2);
        this.f10331a = measuredWidth - (this.f10334d / 2.0f);
        this.f10332b = (measuredWidth - (this.f10334d / 2.0f)) - (this.f10335e / 2.0f);
        this.f10333c = (measuredWidth - (this.f10334d / 2.0f)) - this.f10335e;
        this.f10336f.setStrokeWidth(this.f10334d);
        this.f10336f.setColor(Color.parseColor("#454547"));
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10331a, this.f10336f);
        this.f10336f.setColor(Color.parseColor("#747476"));
        this.f10336f.setStrokeWidth(this.f10335e);
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10332b, this.f10336f);
        this.f10336f.setColor(Color.parseColor("#464648"));
        this.f10336f.setStyle(Style.FILL);
        canvas.drawCircle(measuredWidth, measuredHeight, this.f10333c, this.f10336f);
        canvas.drawBitmap(this.f10338h, measuredWidth - ((float) (this.f10338h.getWidth() / 2)), measuredWidth - ((float) (this.f10338h.getHeight() / 2)), this.f10336f);
        this.f10336f.setColor(-1);
        this.f10336f.setStrokeWidth(BitmapDescriptorFactory.HUE_RED);
        this.f10336f.setTextSize(40.0f);
        try {
            canvas.drawText(getContext().getString(R.string.sound_fa), measuredWidth - (this.f10336f.measureText(getContext().getString(R.string.sound_fa)) / 2.0f), (((float) (this.f10338h.getHeight() / 2)) + measuredWidth) + 40.0f, this.f10336f);
        } catch (Exception e) {
        }
        this.f10336f.setStyle(Style.STROKE);
        this.f10336f.setStrokeWidth(this.f10335e);
        this.f10336f.setColor(-1);
        if (this.f10339i == null) {
            this.f10339i = new RectF(measuredWidth - this.f10332b, measuredHeight - this.f10332b, measuredWidth + this.f10332b, measuredHeight + this.f10332b);
        }
        canvas.drawArc(this.f10339i, 270.0f, (360.0f * this.f10337g) / 100.0f, false, this.f10336f);
        super.onDraw(canvas);
    }

    public void setProgress(float f) {
        this.f10337g = f;
        if (this.f10337g >= 100.0f) {
            this.f10337g = 100.0f;
        }
        if (this.f10337g <= BitmapDescriptorFactory.HUE_RED) {
            this.f10337g = BitmapDescriptorFactory.HUE_RED;
        }
        postInvalidate();
    }
}
