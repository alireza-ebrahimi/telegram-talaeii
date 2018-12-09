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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Paint.Swatch;

public class ColorPicker extends FrameLayout {
    private static final int[] COLORS = new int[]{-1431751, -2409774, -13610525, -11942419, -8337308, -205211, -223667, Theme.ACTION_BAR_VIDEO_EDIT_COLOR, -1};
    private static final float[] LOCATIONS = new float[]{BitmapDescriptorFactory.HUE_RED, 0.14f, 0.24f, 0.39f, 0.49f, 0.62f, 0.73f, 0.85f, 1.0f};
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
    class C44891 implements OnClickListener {
        C44891() {
        }

        public void onClick(View view) {
            if (ColorPicker.this.delegate != null) {
                ColorPicker.this.delegate.onSettingsPressed();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.Views.ColorPicker$2 */
    class C44902 implements OnClickListener {
        C44902() {
        }

        public void onClick(View view) {
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
        this.settingsButton.setOnClickListener(new C44891());
        this.undoButton = new ImageView(context);
        this.undoButton.setScaleType(ScaleType.CENTER);
        this.undoButton.setImageResource(R.drawable.photo_undo);
        addView(this.undoButton, LayoutHelper.createFrame(60, 52.0f));
        this.undoButton.setOnClickListener(new C44902());
        this.location = context.getSharedPreferences("paint", 0).getFloat("last_color_location", 1.0f);
        setLocation(this.location);
    }

    private int interpolateColors(int i, int i2, float f) {
        float min = Math.min(Math.max(f, BitmapDescriptorFactory.HUE_RED), 1.0f);
        int red = Color.red(i);
        int red2 = Color.red(i2);
        int green = Color.green(i);
        int green2 = Color.green(i2);
        int blue = Color.blue(i);
        return Color.argb(255, Math.min(255, (int) ((((float) (red2 - red)) * min) + ((float) red))), Math.min(255, (int) (((float) green) + (((float) (green2 - green)) * min))), Math.min(255, (int) ((min * ((float) (Color.blue(i2) - blue))) + ((float) blue))));
    }

    private void setDragging(boolean z, boolean z2) {
        if (this.dragging != z) {
            this.dragging = z;
            float f = this.dragging ? 1.0f : BitmapDescriptorFactory.HUE_RED;
            if (z2) {
                Animator ofFloat = ObjectAnimator.ofFloat(this, "draggingFactor", new float[]{this.draggingFactor, f});
                ofFloat.setInterpolator(this.interpolator);
                int i = 300;
                if (this.wasChangingWeight) {
                    i = (int) (((float) 300) + (this.weight * 75.0f));
                }
                ofFloat.setDuration((long) i);
                ofFloat.start();
                return;
            }
            setDraggingFactor(f);
        }
    }

    private void setDraggingFactor(float f) {
        this.draggingFactor = f;
        invalidate();
    }

    public int colorForLocation(float f) {
        int i = -1;
        if (f <= BitmapDescriptorFactory.HUE_RED) {
            return COLORS[0];
        }
        if (f >= 1.0f) {
            return COLORS[COLORS.length - 1];
        }
        int i2 = 1;
        while (i2 < LOCATIONS.length) {
            if (LOCATIONS[i2] > f) {
                i = i2 - 1;
                break;
            }
            i2++;
        }
        i2 = -1;
        float f2 = LOCATIONS[i];
        return interpolateColors(COLORS[i], COLORS[i2], (f - f2) / (LOCATIONS[i2] - f2));
    }

    public float getDraggingFactor() {
        return this.draggingFactor;
    }

    public View getSettingsButton() {
        return this.settingsButton;
    }

    public Swatch getSwatch() {
        return new Swatch(colorForLocation(this.location), this.location, this.weight);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(this.rectF, (float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp(6.0f), this.gradientPaint);
        int width = (int) (this.rectF.left + (this.rectF.width() * this.location));
        int centerY = (int) (((this.draggingFactor * ((float) (-AndroidUtilities.dp(70.0f)))) + this.rectF.centerY()) - (this.changingWeight ? this.weight * ((float) AndroidUtilities.dp(190.0f)) : BitmapDescriptorFactory.HUE_RED));
        int dp = (int) (((float) AndroidUtilities.dp(24.0f)) * ((this.draggingFactor + 1.0f) * 0.5f));
        this.shadowDrawable.setBounds(width - dp, centerY - dp, width + dp, dp + centerY);
        this.shadowDrawable.draw(canvas);
        float floor = (((float) ((int) Math.floor((double) (((float) AndroidUtilities.dp(4.0f)) + (((float) (AndroidUtilities.dp(19.0f) - AndroidUtilities.dp(4.0f))) * this.weight))))) * (this.draggingFactor + 1.0f)) / 2.0f;
        canvas.drawCircle((float) width, (float) centerY, ((float) (AndroidUtilities.dp(22.0f) / 2)) * (this.draggingFactor + 1.0f), this.backgroundPaint);
        canvas.drawCircle((float) width, (float) centerY, floor, this.swatchPaint);
        canvas.drawCircle((float) width, (float) centerY, floor - ((float) AndroidUtilities.dp(0.5f)), this.swatchStrokePaint);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        this.gradientPaint.setShader(new LinearGradient((float) AndroidUtilities.dp(56.0f), BitmapDescriptorFactory.HUE_RED, (float) (i5 - AndroidUtilities.dp(56.0f)), BitmapDescriptorFactory.HUE_RED, COLORS, LOCATIONS, TileMode.REPEAT));
        int dp = i6 - AndroidUtilities.dp(32.0f);
        this.rectF.set((float) AndroidUtilities.dp(56.0f), (float) dp, (float) (i5 - AndroidUtilities.dp(56.0f)), (float) (dp + AndroidUtilities.dp(12.0f)));
        this.settingsButton.layout(i5 - this.settingsButton.getMeasuredWidth(), i6 - AndroidUtilities.dp(52.0f), i5, i6);
        this.undoButton.layout(0, i6 - AndroidUtilities.dp(52.0f), this.settingsButton.getMeasuredWidth(), i6);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        float x = motionEvent.getX() - this.rectF.left;
        float y = motionEvent.getY() - this.rectF.top;
        if (!this.interacting && y < ((float) (-AndroidUtilities.dp(10.0f)))) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 3 || actionMasked == 1 || actionMasked == 6) {
            if (this.interacting && this.delegate != null) {
                this.delegate.onFinishedColorPicking();
                getContext().getSharedPreferences("paint", 0).edit().putFloat("last_color_location", this.location).commit();
            }
            this.interacting = false;
            this.wasChangingWeight = this.changingWeight;
            this.changingWeight = false;
            setDragging(false, true);
            return false;
        } else if (actionMasked != 0 && actionMasked != 2) {
            return false;
        } else {
            if (!this.interacting) {
                this.interacting = true;
                if (this.delegate != null) {
                    this.delegate.onBeganColorPicking();
                }
            }
            setLocation(Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(1.0f, x / this.rectF.width())));
            setDragging(true, true);
            if (y < ((float) (-AndroidUtilities.dp(10.0f)))) {
                this.changingWeight = true;
                setWeight(Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(1.0f, ((-y) - ((float) AndroidUtilities.dp(10.0f))) / ((float) AndroidUtilities.dp(190.0f)))));
            }
            if (this.delegate != null) {
                this.delegate.onColorValueChanged();
            }
            return true;
        }
    }

    public void setDelegate(ColorPickerDelegate colorPickerDelegate) {
        this.delegate = colorPickerDelegate;
    }

    public void setLocation(float f) {
        this.location = f;
        int colorForLocation = colorForLocation(f);
        this.swatchPaint.setColor(colorForLocation);
        float[] fArr = new float[3];
        Color.colorToHSV(colorForLocation, fArr);
        if (((double) fArr[0]) >= 0.001d || ((double) fArr[1]) >= 0.001d || fArr[2] <= 0.92f) {
            this.swatchStrokePaint.setColor(colorForLocation);
        } else {
            colorForLocation = (int) ((1.0f - (((fArr[2] - 0.92f) / 0.08f) * 0.22f)) * 255.0f);
            this.swatchStrokePaint.setColor(Color.rgb(colorForLocation, colorForLocation, colorForLocation));
        }
        invalidate();
    }

    public void setSettingsButtonImage(int i) {
        this.settingsButton.setImageResource(i);
    }

    public void setSwatch(Swatch swatch) {
        setLocation(swatch.colorLocation);
        setWeight(swatch.brushWeight);
    }

    public void setUndoEnabled(boolean z) {
        this.undoButton.setAlpha(z ? 1.0f : 0.3f);
        this.undoButton.setEnabled(z);
    }

    public void setWeight(float f) {
        this.weight = f;
        invalidate();
    }
}
