package org.telegram.ui.Components.Paint.Views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Paint.Swatch;

public class ColorPicker extends FrameLayout {
    private static final int[] COLORS = new int[]{-1431751, -2409774, -13610525, -11942419, -8337308, -205211, -223667, -16777216, -1};
    private static final float[] LOCATIONS = new float[]{0.0f, 0.14f, 0.24f, 0.39f, 0.49f, 0.62f, 0.73f, 0.85f, 1.0f};
    private Paint backgroundPaint = new Paint(1);
    private boolean changingWeight;
    private ColorPickerDelegate delegate;
    private boolean dragging;
    private float draggingFactor;
    private Paint gradientPaint = new Paint(1);
    private boolean interacting;
    private OvershootInterpolator interpolator = new OvershootInterpolator(1.02f);
    private float location = 1.0f;
    private RectF rectF = new RectF();
    private ImageView settingsButton;
    private Drawable shadowDrawable;
    private Paint swatchPaint = new Paint(1);
    private Paint swatchStrokePaint = new Paint(1);
    private ImageView undoButton;
    private boolean wasChangingWeight;
    private float weight = 0.27f;

    /* renamed from: org.telegram.ui.Components.Paint.Views.ColorPicker$1 */
    class C26511 implements OnClickListener {
        C26511() {
        }

        public void onClick(View v) {
            if (ColorPicker.this.delegate != null) {
                ColorPicker.this.delegate.onSettingsPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.Views.ColorPicker$2 */
    class C26522 implements OnClickListener {
        C26522() {
        }

        public void onClick(View v) {
            if (ColorPicker.this.delegate != null) {
                ColorPicker.this.delegate.onUndoPressed();
            }
        }
    }

    public interface ColorPickerDelegate {
        void onBeganColorPicking();

        void onColorValueChanged();

        void onFinishedColorPicking();

        void onSettingsPressed();

        void onUndoPressed();
    }

    public ColorPicker(Context context) {
        super(context);
        setWillNotDraw(false);
        this.shadowDrawable = getResources().getDrawable(R.drawable.knob_shadow);
        this.backgroundPaint.setColor(-1);
        this.swatchStrokePaint.setStyle(Style.STROKE);
        this.swatchStrokePaint.setStrokeWidth((float) AndroidUtilities.dp(1.0f));
        this.settingsButton = new ImageView(context);
        this.settingsButton.setScaleType(ScaleType.CENTER);
        this.settingsButton.setImageResource(R.drawable.photo_paint_brush);
        addView(this.settingsButton, LayoutHelper.createFrame(60, 52.0f));
        this.settingsButton.setOnClickListener(new C26511());
        this.undoButton = new ImageView(context);
        this.undoButton.setScaleType(ScaleType.CENTER);
        this.undoButton.setImageResource(R.drawable.photo_undo);
        addView(this.undoButton, LayoutHelper.createFrame(60, 52.0f));
        this.undoButton.setOnClickListener(new C26522());
        this.location = context.getSharedPreferences("paint", 0).getFloat("last_color_location", 1.0f);
        setLocation(this.location);
    }

    public void setUndoEnabled(boolean enabled) {
        this.undoButton.setAlpha(enabled ? 1.0f : 0.3f);
        this.undoButton.setEnabled(enabled);
    }

    public void setDelegate(ColorPickerDelegate colorPickerDelegate) {
        this.delegate = colorPickerDelegate;
    }

    public View getSettingsButton() {
        return this.settingsButton;
    }

    public void setSettingsButtonImage(int resId) {
        this.settingsButton.setImageResource(resId);
    }

    public Swatch getSwatch() {
        return new Swatch(colorForLocation(this.location), this.location, this.weight);
    }

    public void setSwatch(Swatch swatch) {
        setLocation(swatch.colorLocation);
        setWeight(swatch.brushWeight);
    }

    public int colorForLocation(float location) {
        if (location <= 0.0f) {
            return COLORS[0];
        }
        if (location >= 1.0f) {
            return COLORS[COLORS.length - 1];
        }
        int leftIndex = -1;
        int rightIndex = -1;
        for (int i = 1; i < LOCATIONS.length; i++) {
            if (LOCATIONS[i] > location) {
                leftIndex = i - 1;
                rightIndex = i;
                break;
            }
        }
        float leftLocation = LOCATIONS[leftIndex];
        return interpolateColors(COLORS[leftIndex], COLORS[rightIndex], (location - leftLocation) / (LOCATIONS[rightIndex] - leftLocation));
    }

    private int interpolateColors(int leftColor, int rightColor, float factor) {
        factor = Math.min(Math.max(factor, 0.0f), 1.0f);
        int r1 = Color.red(leftColor);
        int r2 = Color.red(rightColor);
        int g1 = Color.green(leftColor);
        int g2 = Color.green(rightColor);
        int b1 = Color.blue(leftColor);
        return Color.argb(255, Math.min(255, (int) (((float) r1) + (((float) (r2 - r1)) * factor))), Math.min(255, (int) (((float) g1) + (((float) (g2 - g1)) * factor))), Math.min(255, (int) (((float) b1) + (((float) (Color.blue(rightColor) - b1)) * factor))));
    }

    public void setLocation(float value) {
        this.location = value;
        int color = colorForLocation(value);
        this.swatchPaint.setColor(color);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        if (((double) hsv[0]) >= 0.001d || ((double) hsv[1]) >= 0.001d || hsv[2] <= 0.92f) {
            this.swatchStrokePaint.setColor(color);
        } else {
            int c = (int) ((1.0f - (((hsv[2] - 0.92f) / 0.08f) * 0.22f)) * 255.0f);
            this.swatchStrokePaint.setColor(Color.rgb(c, c, c));
        }
        invalidate();
    }

    public void setWeight(float value) {
        this.weight = value;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return false;
        }
        float x = event.getX() - this.rectF.left;
        float y = event.getY() - this.rectF.top;
        if (!this.interacting && y < ((float) (-AndroidUtilities.dp(10.0f)))) {
            return false;
        }
        int action = event.getActionMasked();
        if (action == 3 || action == 1 || action == 6) {
            if (this.interacting && this.delegate != null) {
                this.delegate.onFinishedColorPicking();
                getContext().getSharedPreferences("paint", 0).edit().putFloat("last_color_location", this.location).commit();
            }
            this.interacting = false;
            this.wasChangingWeight = this.changingWeight;
            this.changingWeight = false;
            setDragging(false, true);
            return false;
        } else if (action != 0 && action != 2) {
            return false;
        } else {
            if (!this.interacting) {
                this.interacting = true;
                if (this.delegate != null) {
                    this.delegate.onBeganColorPicking();
                }
            }
            setLocation(Math.max(0.0f, Math.min(1.0f, x / this.rectF.width())));
            setDragging(true, true);
            if (y < ((float) (-AndroidUtilities.dp(10.0f)))) {
                this.changingWeight = true;
                setWeight(Math.max(0.0f, Math.min(1.0f, ((-y) - ((float) AndroidUtilities.dp(10.0f))) / ((float) AndroidUtilities.dp(190.0f)))));
            }
            if (this.delegate != null) {
                this.delegate.onColorValueChanged();
            }
            return true;
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = right - left;
        int height = bottom - top;
        this.gradientPaint.setShader(new LinearGradient((float) AndroidUtilities.dp(56.0f), 0.0f, (float) (width - AndroidUtilities.dp(56.0f)), 0.0f, COLORS, LOCATIONS, TileMode.REPEAT));
        int y = height - AndroidUtilities.dp(32.0f);
        this.rectF.set((float) AndroidUtilities.dp(56.0f), (float) y, (float) (width - AndroidUtilities.dp(56.0f)), (float) (AndroidUtilities.dp(12.0f) + y));
        this.settingsButton.layout(width - this.settingsButton.getMeasuredWidth(), height - AndroidUtilities.dp(52.0f), width, height);
        this.undoButton.layout(0, height - AndroidUtilities.dp(52.0f), this.settingsButton.getMeasuredWidth(), height);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(this.rectF, (float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp(6.0f), this.gradientPaint);
        int cx = (int) (this.rectF.left + (this.rectF.width() * this.location));
        int cy = (int) (((this.draggingFactor * ((float) (-AndroidUtilities.dp(70.0f)))) + this.rectF.centerY()) - (this.changingWeight ? this.weight * ((float) AndroidUtilities.dp(190.0f)) : 0.0f));
        int side = (int) (((float) AndroidUtilities.dp(24.0f)) * ((this.draggingFactor + 1.0f) * 0.5f));
        this.shadowDrawable.setBounds(cx - side, cy - side, cx + side, cy + side);
        this.shadowDrawable.draw(canvas);
        float swatchRadius = (((float) ((int) Math.floor((double) (((float) AndroidUtilities.dp(4.0f)) + (((float) (AndroidUtilities.dp(19.0f) - AndroidUtilities.dp(4.0f))) * this.weight))))) * (this.draggingFactor + 1.0f)) / 2.0f;
        canvas.drawCircle((float) cx, (float) cy, ((float) (AndroidUtilities.dp(22.0f) / 2)) * (this.draggingFactor + 1.0f), this.backgroundPaint);
        canvas.drawCircle((float) cx, (float) cy, swatchRadius, this.swatchPaint);
        canvas.drawCircle((float) cx, (float) cy, swatchRadius - ((float) AndroidUtilities.dp(0.5f)), this.swatchStrokePaint);
    }

    private void setDraggingFactor(float factor) {
        this.draggingFactor = factor;
        invalidate();
    }

    public float getDraggingFactor() {
        return this.draggingFactor;
    }

    private void setDragging(boolean value, boolean animated) {
        if (this.dragging != value) {
            this.dragging = value;
            float target = this.dragging ? 1.0f : 0.0f;
            if (animated) {
                Animator a = ObjectAnimator.ofFloat(this, "draggingFactor", new float[]{this.draggingFactor, target});
                a.setInterpolator(this.interpolator);
                int duration = ScheduleDownloadActivity.CHECK_CELL2;
                if (this.wasChangingWeight) {
                    duration = (int) (((float) 300) + (this.weight * 75.0f));
                }
                a.setDuration((long) duration);
                a.start();
                return;
            }
            setDraggingFactor(target);
        }
    }
}
