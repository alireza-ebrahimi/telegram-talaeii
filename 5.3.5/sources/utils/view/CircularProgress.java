package utils.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import org.ir.talaeii.R;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.C0906R;

public class CircularProgress extends View {
    private static final int ANGLE_ANIMATOR_DURATION = 2000;
    private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
    private static final int DEFAULT_BORDER_WIDTH = 3;
    private static final int MIN_SWEEP_ANGLE = 30;
    private static final int SWEEP_ANIMATOR_DURATION = 900;
    private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private final RectF fBounds;
    private Property<CircularProgress, Float> mAngleProperty;
    private float mBorderWidth;
    private int[] mColors;
    private int mCurrentColorIndex;
    private float mCurrentGlobalAngle;
    private float mCurrentGlobalAngleOffset;
    private float mCurrentSweepAngle;
    private boolean mModeAppearing;
    private int mNextColorIndex;
    private ObjectAnimator mObjectAnimatorAngle;
    private ObjectAnimator mObjectAnimatorSweep;
    private Paint mPaint;
    private boolean mRunning;
    private Property<CircularProgress, Float> mSweepProperty;

    /* renamed from: utils.view.CircularProgress$3 */
    class C34783 implements AnimatorListener {
        C34783() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
            CircularProgress.this.toggleAppearingMode();
        }
    }

    public CircularProgress(Context context) {
        this(context, null);
    }

    public CircularProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.fBounds = new RectF();
        this.mModeAppearing = true;
        this.mAngleProperty = new Property<CircularProgress, Float>(Float.class, "angle") {
            public Float get(CircularProgress object) {
                return Float.valueOf(object.getCurrentGlobalAngle());
            }

            public void set(CircularProgress object, Float value) {
                object.setCurrentGlobalAngle(value.floatValue());
            }
        };
        this.mSweepProperty = new Property<CircularProgress, Float>(Float.class, "arc") {
            public Float get(CircularProgress object) {
                return Float.valueOf(object.getCurrentSweepAngle());
            }

            public void set(CircularProgress object, Float value) {
                object.setCurrentSweepAngle(value.floatValue());
            }
        };
        float density = context.getResources().getDisplayMetrics().density;
        TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.CircularProgress, defStyleAttr, 0);
        this.mBorderWidth = a.getDimension(0, 3.0f * density);
        a.recycle();
        this.mColors = new int[4];
        this.mColors[0] = context.getResources().getColor(R.color.red);
        this.mColors[1] = context.getResources().getColor(R.color.yellow);
        this.mColors[2] = context.getResources().getColor(R.color.green);
        this.mColors[3] = context.getResources().getColor(R.color.blue);
        this.mCurrentColorIndex = 0;
        this.mNextColorIndex = 1;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeCap(Cap.ROUND);
        this.mPaint.setStrokeWidth(this.mBorderWidth);
        this.mPaint.setColor(this.mColors[this.mCurrentColorIndex]);
        setupAnimations();
    }

    private static int gradient(int color1, int color2, float p) {
        return Color.argb(255, (int) ((((float) ((color2 & 16711680) >> 16)) * p) + (((float) ((color1 & 16711680) >> 16)) * (1.0f - p))), (int) ((((float) ((color2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)) * p) + (((float) ((color1 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8)) * (1.0f - p))), (int) ((((float) (color2 & 255)) * p) + (((float) (color1 & 255)) * (1.0f - p))));
    }

    private void start() {
        if (!isRunning()) {
            this.mRunning = true;
            this.mObjectAnimatorAngle.start();
            this.mObjectAnimatorSweep.start();
            invalidate();
        }
    }

    private void stop() {
        if (isRunning()) {
            this.mRunning = false;
            this.mObjectAnimatorAngle.cancel();
            this.mObjectAnimatorSweep.cancel();
            invalidate();
        }
    }

    private boolean isRunning() {
        return this.mRunning;
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            start();
        } else {
            stop();
        }
    }

    protected void onAttachedToWindow() {
        start();
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.fBounds.left = (this.mBorderWidth / 2.0f) + 0.5f;
        this.fBounds.right = (((float) w) - (this.mBorderWidth / 2.0f)) - 0.5f;
        this.fBounds.top = (this.mBorderWidth / 2.0f) + 0.5f;
        this.fBounds.bottom = (((float) h) - (this.mBorderWidth / 2.0f)) - 0.5f;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        float startAngle = this.mCurrentGlobalAngle - this.mCurrentGlobalAngleOffset;
        float sweepAngle = this.mCurrentSweepAngle;
        if (this.mModeAppearing) {
            this.mPaint.setColor(gradient(this.mColors[this.mCurrentColorIndex], this.mColors[this.mNextColorIndex], this.mCurrentSweepAngle / 300.0f));
            sweepAngle += 30.0f;
        } else {
            startAngle += sweepAngle;
            sweepAngle = (360.0f - sweepAngle) - 30.0f;
        }
        canvas.drawArc(this.fBounds, startAngle, sweepAngle, false, this.mPaint);
    }

    private void toggleAppearingMode() {
        this.mModeAppearing = !this.mModeAppearing;
        if (this.mModeAppearing) {
            int i = this.mCurrentColorIndex + 1;
            this.mCurrentColorIndex = i;
            this.mCurrentColorIndex = i % 4;
            i = this.mNextColorIndex + 1;
            this.mNextColorIndex = i;
            this.mNextColorIndex = i % 4;
            this.mCurrentGlobalAngleOffset = (this.mCurrentGlobalAngleOffset + 60.0f) % 360.0f;
        }
    }

    private void setupAnimations() {
        this.mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, this.mAngleProperty, new float[]{360.0f});
        this.mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
        this.mObjectAnimatorAngle.setDuration(FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
        this.mObjectAnimatorAngle.setRepeatMode(1);
        this.mObjectAnimatorAngle.setRepeatCount(-1);
        this.mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, this.mSweepProperty, new float[]{300.0f});
        this.mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
        this.mObjectAnimatorSweep.setDuration(900);
        this.mObjectAnimatorSweep.setRepeatMode(1);
        this.mObjectAnimatorSweep.setRepeatCount(-1);
        this.mObjectAnimatorSweep.addListener(new C34783());
    }

    public float getCurrentGlobalAngle() {
        return this.mCurrentGlobalAngle;
    }

    public void setCurrentGlobalAngle(float currentGlobalAngle) {
        this.mCurrentGlobalAngle = currentGlobalAngle;
        invalidate();
    }

    public float getCurrentSweepAngle() {
        return this.mCurrentSweepAngle;
    }

    public void setCurrentSweepAngle(float currentSweepAngle) {
        this.mCurrentSweepAngle = currentSweepAngle;
        invalidate();
    }
}
