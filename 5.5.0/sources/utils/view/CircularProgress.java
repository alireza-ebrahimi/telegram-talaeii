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
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;

public class CircularProgress extends View {
    /* renamed from: a */
    private static final Interpolator f10249a = new LinearInterpolator();
    /* renamed from: b */
    private static final Interpolator f10250b = new AccelerateDecelerateInterpolator();
    /* renamed from: c */
    private final RectF f10251c;
    /* renamed from: d */
    private ObjectAnimator f10252d;
    /* renamed from: e */
    private ObjectAnimator f10253e;
    /* renamed from: f */
    private boolean f10254f;
    /* renamed from: g */
    private Paint f10255g;
    /* renamed from: h */
    private float f10256h;
    /* renamed from: i */
    private float f10257i;
    /* renamed from: j */
    private float f10258j;
    /* renamed from: k */
    private float f10259k;
    /* renamed from: l */
    private boolean f10260l;
    /* renamed from: m */
    private int[] f10261m;
    /* renamed from: n */
    private int f10262n;
    /* renamed from: o */
    private int f10263o;
    /* renamed from: p */
    private Property<CircularProgress, Float> f10264p;
    /* renamed from: q */
    private Property<CircularProgress, Float> f10265q;

    /* renamed from: utils.view.CircularProgress$3 */
    class C53263 implements AnimatorListener {
        /* renamed from: a */
        final /* synthetic */ CircularProgress f10248a;

        C53263(CircularProgress circularProgress) {
            this.f10248a = circularProgress;
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
            this.f10248a.m14161d();
        }

        public void onAnimationStart(Animator animator) {
        }
    }

    public CircularProgress(Context context) {
        this(context, null);
    }

    public CircularProgress(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircularProgress(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f10251c = new RectF();
        this.f10254f = true;
        this.f10264p = new Property<CircularProgress, Float>(this, Float.class, "angle") {
            /* renamed from: a */
            final /* synthetic */ CircularProgress f10246a;

            /* renamed from: a */
            public Float m14152a(CircularProgress circularProgress) {
                return Float.valueOf(circularProgress.getCurrentGlobalAngle());
            }

            /* renamed from: a */
            public void m14153a(CircularProgress circularProgress, Float f) {
                circularProgress.setCurrentGlobalAngle(f.floatValue());
            }

            public /* synthetic */ Object get(Object obj) {
                return m14152a((CircularProgress) obj);
            }

            public /* synthetic */ void set(Object obj, Object obj2) {
                m14153a((CircularProgress) obj, (Float) obj2);
            }
        };
        this.f10265q = new Property<CircularProgress, Float>(this, Float.class, "arc") {
            /* renamed from: a */
            final /* synthetic */ CircularProgress f10247a;

            /* renamed from: a */
            public Float m14154a(CircularProgress circularProgress) {
                return Float.valueOf(circularProgress.getCurrentSweepAngle());
            }

            /* renamed from: a */
            public void m14155a(CircularProgress circularProgress, Float f) {
                circularProgress.setCurrentSweepAngle(f.floatValue());
            }

            public /* synthetic */ Object get(Object obj) {
                return m14154a((CircularProgress) obj);
            }

            public /* synthetic */ void set(Object obj, Object obj2) {
                m14155a((CircularProgress) obj, (Float) obj2);
            }
        };
        float f = context.getResources().getDisplayMetrics().density;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.CircularProgress, i, 0);
        this.f10259k = obtainStyledAttributes.getDimension(0, f * 3.0f);
        obtainStyledAttributes.recycle();
        this.f10261m = new int[4];
        this.f10261m[0] = context.getResources().getColor(R.color.red);
        this.f10261m[1] = context.getResources().getColor(R.color.yellow);
        this.f10261m[2] = context.getResources().getColor(R.color.green);
        this.f10261m[3] = context.getResources().getColor(R.color.blue);
        this.f10262n = 0;
        this.f10263o = 1;
        this.f10255g = new Paint();
        this.f10255g.setAntiAlias(true);
        this.f10255g.setStyle(Style.STROKE);
        this.f10255g.setStrokeCap(Cap.ROUND);
        this.f10255g.setStrokeWidth(this.f10259k);
        this.f10255g.setColor(this.f10261m[this.f10262n]);
        m14162e();
    }

    /* renamed from: a */
    private static int m14156a(int i, int i2, float f) {
        return Color.argb(255, (int) ((((float) ((i & 16711680) >> 16)) * (1.0f - f)) + (((float) ((16711680 & i2) >> 16)) * f)), (int) ((((float) ((i & 65280) >> 8)) * (1.0f - f)) + (((float) ((65280 & i2) >> 8)) * f)), (int) ((((float) (i & 255)) * (1.0f - f)) + (((float) (i2 & 255)) * f)));
    }

    /* renamed from: a */
    private void m14157a() {
        if (!m14160c()) {
            this.f10260l = true;
            this.f10253e.start();
            this.f10252d.start();
            invalidate();
        }
    }

    /* renamed from: b */
    private void m14159b() {
        if (m14160c()) {
            this.f10260l = false;
            this.f10253e.cancel();
            this.f10252d.cancel();
            invalidate();
        }
    }

    /* renamed from: c */
    private boolean m14160c() {
        return this.f10260l;
    }

    /* renamed from: d */
    private void m14161d() {
        this.f10254f = !this.f10254f;
        if (this.f10254f) {
            int i = this.f10262n + 1;
            this.f10262n = i;
            this.f10262n = i % 4;
            i = this.f10263o + 1;
            this.f10263o = i;
            this.f10263o = i % 4;
            this.f10256h = (this.f10256h + 60.0f) % 360.0f;
        }
    }

    /* renamed from: e */
    private void m14162e() {
        this.f10253e = ObjectAnimator.ofFloat(this, this.f10264p, new float[]{360.0f});
        this.f10253e.setInterpolator(f10249a);
        this.f10253e.setDuration(2000);
        this.f10253e.setRepeatMode(1);
        this.f10253e.setRepeatCount(-1);
        this.f10252d = ObjectAnimator.ofFloat(this, this.f10265q, new float[]{300.0f});
        this.f10252d.setInterpolator(f10250b);
        this.f10252d.setDuration(900);
        this.f10252d.setRepeatMode(1);
        this.f10252d.setRepeatCount(-1);
        this.f10252d.addListener(new C53263(this));
    }

    public void draw(Canvas canvas) {
        float f;
        super.draw(canvas);
        float f2 = this.f10257i - this.f10256h;
        float f3 = this.f10258j;
        if (this.f10254f) {
            this.f10255g.setColor(m14156a(this.f10261m[this.f10262n], this.f10261m[this.f10263o], this.f10258j / 300.0f));
            f = f3 + 30.0f;
        } else {
            f2 += f3;
            f = (360.0f - f3) - 30.0f;
        }
        canvas.drawArc(this.f10251c, f2, f, false, this.f10255g);
    }

    public float getCurrentGlobalAngle() {
        return this.f10257i;
    }

    public float getCurrentSweepAngle() {
        return this.f10258j;
    }

    protected void onAttachedToWindow() {
        m14157a();
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        m14159b();
        super.onDetachedFromWindow();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f10251c.left = (this.f10259k / 2.0f) + 0.5f;
        this.f10251c.right = (((float) i) - (this.f10259k / 2.0f)) - 0.5f;
        this.f10251c.top = (this.f10259k / 2.0f) + 0.5f;
        this.f10251c.bottom = (((float) i2) - (this.f10259k / 2.0f)) - 0.5f;
    }

    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i == 0) {
            m14157a();
        } else {
            m14159b();
        }
    }

    public void setCurrentGlobalAngle(float f) {
        this.f10257i = f;
        invalidate();
    }

    public void setCurrentSweepAngle(float f) {
        this.f10258j = f;
        invalidate();
    }
}
