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
import org.ir.talaeii.R;

public class LightView extends View {
    Bitmap bitmap;
    RectF oval;
    Paint paint;
    float progress = 0.0f;
    float r1 = 0.0f;
    float r2 = 0.0f;
    float r3 = 0.0f;
    float w1 = 15.0f;
    float w2 = 30.0f;

    public LightView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LightView(Context context) {
        super(context);
        init(context);
    }

    void init(Context context) {
        this.paint = new Paint(1);
        this.paint.setStyle(Style.STROKE);
        try {
            this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.light);
        } catch (Exception e) {
        }
    }

    protected void onDraw(Canvas canvas) {
        float cx = (float) (getMeasuredWidth() / 2);
        float cy = (float) (getMeasuredHeight() / 2);
        this.r1 = cx - (this.w1 / 2.0f);
        this.r2 = (cx - (this.w1 / 2.0f)) - (this.w2 / 2.0f);
        this.r3 = (cx - (this.w1 / 2.0f)) - this.w2;
        this.paint.setStrokeWidth(this.w1);
        this.paint.setColor(Color.parseColor("#454547"));
        canvas.drawCircle(cx, cy, this.r1, this.paint);
        this.paint.setColor(Color.parseColor("#747476"));
        this.paint.setStrokeWidth(this.w2);
        canvas.drawCircle(cx, cy, this.r2, this.paint);
        this.paint.setColor(Color.parseColor("#464648"));
        this.paint.setStyle(Style.FILL);
        canvas.drawCircle(cx, cy, this.r3, this.paint);
        canvas.drawBitmap(this.bitmap, cx - ((float) (this.bitmap.getWidth() / 2)), cx - ((float) (this.bitmap.getHeight() / 2)), this.paint);
        this.paint.setColor(-1);
        this.paint.setStrokeWidth(0.0f);
        this.paint.setTextSize(40.0f);
        try {
            canvas.drawText(getContext().getString(R.string.light_fa), cx - (this.paint.measureText(getContext().getString(R.string.light_fa)) / 2.0f), (((float) (this.bitmap.getHeight() / 2)) + cx) + 40.0f, this.paint);
        } catch (Exception e) {
        }
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth(this.w2);
        this.paint.setColor(-1);
        if (this.oval == null) {
            this.oval = new RectF(cx - this.r2, cy - this.r2, this.r2 + cx, this.r2 + cy);
        }
        canvas.drawArc(this.oval, 270.0f, (360.0f * this.progress) / 100.0f, false, this.paint);
        super.onDraw(canvas);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if (this.progress >= 100.0f) {
            this.progress = 100.0f;
        }
        if (this.progress <= 0.0f) {
            this.progress = 0.0f;
        }
        postInvalidate();
    }
}
