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
    private RectF tempRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    private Paint whitePaint = new Paint();

    /* renamed from: org.telegram.ui.Components.Crop.CropRotationWheel$1 */
    class C25451 implements OnClickListener {
        C25451() {
        }

        public void onClick(View v) {
            if (CropRotationWheel.this.rotationListener != null) {
                CropRotationWheel.this.rotationListener.aspectRatioPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.Crop.CropRotationWheel$2 */
    class C25462 implements OnClickListener {
        C25462() {
        }

        public void onClick(View v) {
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
        this.aspectRatioButton.setOnClickListener(new C25451());
        addView(this.aspectRatioButton, LayoutHelper.createFrame(70, 64, 19));
        this.rotation90Button = new ImageView(context);
        this.rotation90Button.setImageResource(R.drawable.tool_rotate);
        this.rotation90Button.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.rotation90Button.setScaleType(ScaleType.CENTER);
        this.rotation90Button.setOnClickListener(new C25462());
        addView(this.rotation90Button, LayoutHelper.createFrame(70, 64, 21));
        this.degreesLabel = new TextView(context);
        this.degreesLabel.setTextColor(-1);
        addView(this.degreesLabel, LayoutHelper.createFrame(-2, -2, 49));
        setWillNotDraw(false);
        setRotation(0.0f, false);
    }

    public void setFreeform(boolean freeform) {
        this.aspectRatioButton.setVisibility(freeform ? 0 : 8);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(widthMeasureSpec), AndroidUtilities.dp(400.0f)), 1073741824), heightMeasureSpec);
    }

    public void reset() {
        setRotation(0.0f, false);
    }

    public void setListener(RotationWheelListener listener) {
        this.rotationListener = listener;
    }

    public void setRotation(float rotation, boolean animated) {
        this.rotation = rotation;
        float value = this.rotation;
        if (((double) Math.abs(value)) < 0.099d) {
            value = Math.abs(value);
        }
        this.degreesLabel.setText(String.format("%.1fº", new Object[]{Float.valueOf(value)}));
        invalidate();
    }

    public void setAspectLock(boolean enabled) {
        this.aspectRatioButton.setColorFilter(enabled ? new PorterDuffColorFilter(-11420173, Mode.MULTIPLY) : null);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        float x = ev.getX();
        if (action == 0) {
            this.prevX = x;
            if (this.rotationListener != null) {
                this.rotationListener.onStart();
            }
        } else if (action == 1 || action == 3) {
            if (this.rotationListener != null) {
                this.rotationListener.onEnd(this.rotation);
            }
        } else if (action == 2) {
            float newAngle = Math.max(-45.0f, Math.min(45.0f, this.rotation + ((float) ((((double) ((this.prevX - x) / AndroidUtilities.density)) / 3.141592653589793d) / 1.649999976158142d))));
            if (((double) Math.abs(newAngle - this.rotation)) > 0.001d) {
                if (((double) Math.abs(newAngle)) < 0.05d) {
                    newAngle = 0.0f;
                }
                setRotation(newAngle, false);
                if (this.rotationListener != null) {
                    this.rotationListener.onChange(this.rotation);
                }
                this.prevX = x;
            }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        float angle = (-this.rotation) * 2.0f;
        float delta = angle % 5.0f;
        int segments = (int) Math.floor((double) (angle / 5.0f));
        for (int i = 0; i < 16; i++) {
            Paint paint = this.whitePaint;
            int a = i;
            if (a < segments || (a == 0 && delta < 0.0f)) {
                paint = this.bluePaint;
            }
            boolean z = a == segments || (a == 0 && segments == -1);
            drawLine(canvas, a, delta, width, height, z, paint);
            if (i != 0) {
                a = -i;
                paint = a > segments ? this.bluePaint : this.whitePaint;
                if (a == segments + 1) {
                    z = true;
                } else {
                    z = false;
                }
                drawLine(canvas, a, delta, width, height, z, paint);
            }
        }
        this.bluePaint.setAlpha(255);
        this.tempRect.left = (float) ((width - AndroidUtilities.dp(2.5f)) / 2);
        this.tempRect.top = (float) ((height - AndroidUtilities.dp(22.0f)) / 2);
        this.tempRect.right = (float) ((AndroidUtilities.dp(2.5f) + width) / 2);
        this.tempRect.bottom = (float) ((AndroidUtilities.dp(22.0f) + height) / 2);
        canvas.drawRoundRect(this.tempRect, (float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(2.0f), this.bluePaint);
    }

    protected void drawLine(Canvas canvas, int i, float delta, int width, int height, boolean center, Paint paint) {
        int radius = (int) ((((float) width) / 2.0f) - ((float) AndroidUtilities.dp(70.0f)));
        int val = (int) (((double) radius) * Math.cos(Math.toRadians((double) (90.0f - (((float) (i * 5)) + delta)))));
        int x = (width / 2) + val;
        float f = ((float) Math.abs(val)) / ((float) radius);
        int alpha = Math.min(255, Math.max(0, (int) ((1.0f - (f * f)) * 255.0f)));
        if (center) {
            paint = this.bluePaint;
        }
        paint.setAlpha(alpha);
        int w = center ? 4 : 2;
        int h = center ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(12.0f);
        canvas.drawRect((float) (x - (w / 2)), (float) ((height - h) / 2), (float) ((w / 2) + x), (float) ((height + h) / 2), paint);
    }
}
