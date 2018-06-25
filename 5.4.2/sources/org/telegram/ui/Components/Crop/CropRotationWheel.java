package org.telegram.ui.Components.Crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class CropRotationWheel extends FrameLayout {
    private static final int DELTA_ANGLE = 5;
    private static final int MAX_ANGLE = 45;
    private ImageView aspectRatioButton;
    private Paint bluePaint;
    private TextView degreesLabel;
    private float prevX;
    protected float rotation;
    private ImageView rotation90Button;
    private RotationWheelListener rotationListener;
    private RectF tempRect = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    private Paint whitePaint = new Paint();

    /* renamed from: org.telegram.ui.Components.Crop.CropRotationWheel$1 */
    class C43831 implements OnClickListener {
        C43831() {
        }

        public void onClick(View view) {
            if (CropRotationWheel.this.rotationListener != null) {
                CropRotationWheel.this.rotationListener.aspectRatioPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.Crop.CropRotationWheel$2 */
    class C43842 implements OnClickListener {
        C43842() {
        }

        public void onClick(View view) {
            if (CropRotationWheel.this.rotationListener != null) {
                CropRotationWheel.this.rotationListener.rotate90Pressed();
            }
        }
    }

    public interface RotationWheelListener {
        void aspectRatioPressed();

        void onChange(float f);

        void onEnd(float f);

        void onStart();

        void rotate90Pressed();
    }

    public CropRotationWheel(Context context) {
        super(context);
        this.whitePaint.setStyle(Style.FILL);
        this.whitePaint.setColor(-1);
        this.whitePaint.setAlpha(255);
        this.whitePaint.setAntiAlias(true);
        this.bluePaint = new Paint();
        this.bluePaint.setStyle(Style.FILL);
        this.bluePaint.setColor(-11420173);
        this.bluePaint.setAlpha(255);
        this.bluePaint.setAntiAlias(true);
        this.aspectRatioButton = new ImageView(context);
        this.aspectRatioButton.setImageResource(R.drawable.tool_cropfix);
        this.aspectRatioButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.aspectRatioButton.setScaleType(ScaleType.CENTER);
        this.aspectRatioButton.setOnClickListener(new C43831());
        addView(this.aspectRatioButton, LayoutHelper.createFrame(70, 64, 19));
        this.rotation90Button = new ImageView(context);
        this.rotation90Button.setImageResource(R.drawable.tool_rotate);
        this.rotation90Button.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.rotation90Button.setScaleType(ScaleType.CENTER);
        this.rotation90Button.setOnClickListener(new C43842());
        addView(this.rotation90Button, LayoutHelper.createFrame(70, 64, 21));
        this.degreesLabel = new TextView(context);
        this.degreesLabel.setTextColor(-1);
        addView(this.degreesLabel, LayoutHelper.createFrame(-2, -2, 49));
        setWillNotDraw(false);
        setRotation(BitmapDescriptorFactory.HUE_RED, false);
    }

    protected void drawLine(Canvas canvas, int i, float f, int i2, int i3, boolean z, Paint paint) {
        int dp = (int) ((((float) i2) / 2.0f) - ((float) AndroidUtilities.dp(70.0f)));
        int cos = (int) (((double) dp) * Math.cos(Math.toRadians((double) (90.0f - (((float) (i * 5)) + f)))));
        int i4 = (i2 / 2) + cos;
        float abs = ((float) Math.abs(cos)) / ((float) dp);
        dp = Math.min(255, Math.max(0, (int) ((1.0f - (abs * abs)) * 255.0f)));
        Paint paint2 = z ? this.bluePaint : paint;
        paint2.setAlpha(dp);
        int i5 = z ? 4 : 2;
        dp = z ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(12.0f);
        canvas.drawRect((float) (i4 - (i5 / 2)), (float) ((i3 - dp) / 2), (float) ((i5 / 2) + i4), (float) ((dp + i3) / 2), paint2);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float f = (-this.rotation) * 2.0f;
        float f2 = f % 5.0f;
        int floor = (int) Math.floor((double) (f / 5.0f));
        int i = 0;
        while (i < 16) {
            Paint paint = this.whitePaint;
            if (i < floor || (i == 0 && f2 < BitmapDescriptorFactory.HUE_RED)) {
                paint = this.bluePaint;
            }
            boolean z = i == floor || (i == 0 && floor == -1);
            drawLine(canvas, i, f2, width, height, z, paint);
            if (i != 0) {
                int i2 = -i;
                drawLine(canvas, i2, f2, width, height, i2 == floor + 1, i2 > floor ? this.bluePaint : this.whitePaint);
            }
            i++;
        }
        this.bluePaint.setAlpha(255);
        this.tempRect.left = (float) ((width - AndroidUtilities.dp(2.5f)) / 2);
        this.tempRect.top = (float) ((height - AndroidUtilities.dp(22.0f)) / 2);
        this.tempRect.right = (float) ((AndroidUtilities.dp(2.5f) + width) / 2);
        this.tempRect.bottom = (float) ((AndroidUtilities.dp(22.0f) + height) / 2);
        canvas.drawRoundRect(this.tempRect, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.bluePaint);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(i), AndroidUtilities.dp(400.0f)), 1073741824), i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        if (actionMasked == 0) {
            this.prevX = x;
            if (this.rotationListener != null) {
                this.rotationListener.onStart();
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            if (this.rotationListener != null) {
                this.rotationListener.onEnd(this.rotation);
            }
        } else if (actionMasked == 2) {
            float f = this.prevX - x;
            f = Math.max(-45.0f, Math.min(45.0f, ((float) ((((double) (f / AndroidUtilities.density)) / 3.141592653589793d) / 1.649999976158142d)) + this.rotation));
            if (((double) Math.abs(f - this.rotation)) > 0.001d) {
                if (((double) Math.abs(f)) < 0.05d) {
                    f = BitmapDescriptorFactory.HUE_RED;
                }
                setRotation(f, false);
                if (this.rotationListener != null) {
                    this.rotationListener.onChange(this.rotation);
                }
                this.prevX = x;
            }
        }
        return true;
    }

    public void reset() {
        setRotation(BitmapDescriptorFactory.HUE_RED, false);
    }

    public void setAspectLock(boolean z) {
        this.aspectRatioButton.setColorFilter(z ? new PorterDuffColorFilter(-11420173, Mode.MULTIPLY) : null);
    }

    public void setFreeform(boolean z) {
        this.aspectRatioButton.setVisibility(z ? 0 : 8);
    }

    public void setListener(RotationWheelListener rotationWheelListener) {
        this.rotationListener = rotationWheelListener;
    }

    public void setRotation(float f, boolean z) {
        this.rotation = f;
        float f2 = this.rotation;
        if (((double) Math.abs(f2)) < 0.099d) {
            f2 = Math.abs(f2);
        }
        this.degreesLabel.setText(String.format("%.1fº", new Object[]{Float.valueOf(f2)}));
        invalidate();
    }
}
