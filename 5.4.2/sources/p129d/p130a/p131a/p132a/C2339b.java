package p129d.p130a.p131a.p132a;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.text.Bidi;
import p129d.p130a.p131a.p132a.C2339b;
import p129d.p130a.p131a.p132a.C2343c.C2340a;
import p129d.p130a.p131a.p132a.C2343c.C2341b;
import p129d.p130a.p131a.p132a.C2343c.C2342c;

/* renamed from: d.a.a.a.b */
public class C2339b {
    @Deprecated
    /* renamed from: A */
    C2336c f7823A;
    /* renamed from: B */
    C2337d f7824B;
    /* renamed from: C */
    boolean f7825C;
    @Deprecated
    /* renamed from: D */
    boolean f7826D;
    /* renamed from: E */
    ViewGroup f7827E;
    /* renamed from: F */
    View f7828F;
    /* renamed from: G */
    final float f7829G;
    /* renamed from: H */
    final OnGlobalLayoutListener f7830H;
    /* renamed from: I */
    boolean f7831I;
    /* renamed from: J */
    boolean f7832J;
    /* renamed from: K */
    boolean f7833K = true;
    /* renamed from: L */
    Alignment f7834L;
    /* renamed from: M */
    Alignment f7835M;
    /* renamed from: N */
    RectF f7836N;
    /* renamed from: O */
    float f7837O;
    /* renamed from: P */
    int f7838P;
    /* renamed from: Q */
    int f7839Q;
    /* renamed from: a */
    C2322d f7840a;
    /* renamed from: b */
    C2338e f7841b;
    /* renamed from: c */
    View f7842c;
    /* renamed from: d */
    PointF f7843d;
    /* renamed from: e */
    float f7844e;
    /* renamed from: f */
    float f7845f;
    /* renamed from: g */
    PointF f7846g = new PointF();
    /* renamed from: h */
    float f7847h;
    /* renamed from: i */
    float f7848i;
    /* renamed from: j */
    String f7849j;
    /* renamed from: k */
    String f7850k;
    /* renamed from: l */
    float f7851l;
    /* renamed from: m */
    float f7852m;
    /* renamed from: n */
    boolean f7853n;
    /* renamed from: o */
    boolean f7854o;
    /* renamed from: p */
    boolean f7855p;
    /* renamed from: q */
    float f7856q;
    /* renamed from: r */
    int f7857r;
    /* renamed from: s */
    int f7858s;
    /* renamed from: t */
    ValueAnimator f7859t;
    /* renamed from: u */
    ValueAnimator f7860u;
    /* renamed from: v */
    Interpolator f7861v;
    /* renamed from: w */
    float f7862w;
    /* renamed from: x */
    int f7863x;
    /* renamed from: y */
    TextPaint f7864y;
    /* renamed from: z */
    TextPaint f7865z;

    /* renamed from: d.a.a.a.b$1 */
    class C23251 implements C2324a {
        /* renamed from: a */
        final /* synthetic */ C2339b f7743a;

        C23251(C2339b c2339b) {
            this.f7743a = c2339b;
        }

        /* renamed from: a */
        public void mo3376a() {
            if (!this.f7743a.f7825C) {
                if (this.f7743a.f7832J) {
                    this.f7743a.m11645d();
                }
                this.f7743a.m11643b(3);
            }
        }

        /* renamed from: a */
        public void mo3377a(MotionEvent motionEvent, boolean z) {
            if (!this.f7743a.f7826D) {
                if (z) {
                    if (this.f7743a.f7832J) {
                        this.f7743a.m11645d();
                        this.f7743a.f7826D = true;
                    }
                } else if (this.f7743a.f7831I) {
                    this.f7743a.m11646e();
                    this.f7743a.f7826D = true;
                }
                this.f7743a.m11641a(motionEvent, z);
            }
        }

        /* renamed from: b */
        public void mo3378b() {
            if (!this.f7743a.f7825C) {
                if (this.f7743a.f7831I) {
                    this.f7743a.m11646e();
                }
                this.f7743a.m11643b(5);
            }
        }
    }

    /* renamed from: d.a.a.a.b$2 */
    class C23262 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2339b f7744a;

        C23262(C2339b c2339b) {
            this.f7744a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f7744a.f7841b.f7803g = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.f7744a.f7841b.f7804h = (int) ((1.0f - (VERSION.SDK_INT >= 12 ? valueAnimator.getAnimatedFraction() : (this.f7744a.f7847h * 6.0f) / ((this.f7744a.f7841b.f7803g - this.f7744a.f7844e) - this.f7744a.f7847h))) * ((float) this.f7744a.f7863x));
        }
    }

    /* renamed from: d.a.a.a.b$3 */
    class C23273 implements OnGlobalLayoutListener {
        /* renamed from: a */
        final /* synthetic */ C2339b f7745a;

        C23273(C2339b c2339b) {
            this.f7745a = c2339b;
        }

        public void onGlobalLayout() {
            this.f7745a.m11649h();
        }
    }

    /* renamed from: d.a.a.a.b$4 */
    class C23284 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2339b f7746a;

        C23284(C2339b c2339b) {
            this.f7746a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.f7746a.f7848i = ((1.0f - floatValue) / 4.0f) + 1.0f;
            this.f7746a.f7841b.f7802f = this.f7746a.f7845f * this.f7746a.f7848i;
            this.f7746a.f7841b.f7801e = this.f7746a.f7844e * this.f7746a.f7848i;
            this.f7746a.f7841b.f7800d.setAlpha((int) (((float) this.f7746a.f7839Q) * floatValue));
            this.f7746a.f7841b.f7799c.setAlpha((int) (((float) this.f7746a.f7838P) * floatValue));
            if (this.f7746a.f7865z != null) {
                this.f7746a.f7865z.setAlpha((int) (((float) this.f7746a.f7858s) * floatValue));
            }
            if (this.f7746a.f7864y != null) {
                this.f7746a.f7864y.setAlpha((int) (floatValue * ((float) this.f7746a.f7857r)));
            }
            if (this.f7746a.f7841b.f7805i != null) {
                this.f7746a.f7841b.f7805i.setAlpha(this.f7746a.f7841b.f7799c.getAlpha());
            }
            this.f7746a.f7841b.invalidate();
        }
    }

    @TargetApi(11)
    /* renamed from: d.a.a.a.b$a */
    static class C2329a implements AnimatorListener {
        C2329a() {
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }
    }

    /* renamed from: d.a.a.a.b$5 */
    class C23305 extends C2329a {
        /* renamed from: a */
        final /* synthetic */ C2339b f7747a;

        C23305(C2339b c2339b) {
            this.f7747a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationCancel(Animator animator) {
            this.f7747a.m11640a(4);
        }

        @TargetApi(11)
        public void onAnimationEnd(Animator animator) {
            this.f7747a.m11640a(4);
        }
    }

    /* renamed from: d.a.a.a.b$6 */
    class C23316 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2339b f7748a;

        C23316(C2339b c2339b) {
            this.f7748a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f7748a.f7848i = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.f7748a.f7841b.f7802f = this.f7748a.f7845f * this.f7748a.f7848i;
            this.f7748a.f7841b.f7801e = this.f7748a.f7844e * this.f7748a.f7848i;
            this.f7748a.f7841b.f7799c.setAlpha((int) (((float) this.f7748a.f7838P) * this.f7748a.f7848i));
            this.f7748a.f7841b.f7800d.setAlpha((int) (((float) this.f7748a.f7839Q) * this.f7748a.f7848i));
            if (this.f7748a.f7865z != null) {
                this.f7748a.f7865z.setAlpha((int) (((float) this.f7748a.f7858s) * this.f7748a.f7848i));
            }
            if (this.f7748a.f7864y != null) {
                this.f7748a.f7864y.setAlpha((int) (((float) this.f7748a.f7857r) * this.f7748a.f7848i));
            }
            if (this.f7748a.f7841b.f7805i != null) {
                this.f7748a.f7841b.f7805i.setAlpha(this.f7748a.f7841b.f7799c.getAlpha());
            }
            this.f7748a.f7841b.f7798b.set(this.f7748a.f7841b.f7797a.x + ((this.f7748a.f7846g.x - this.f7748a.f7841b.f7797a.x) * this.f7748a.f7848i), this.f7748a.f7841b.f7797a.y + ((this.f7748a.f7846g.y - this.f7748a.f7841b.f7797a.y) * this.f7748a.f7848i));
            this.f7748a.f7841b.invalidate();
        }
    }

    /* renamed from: d.a.a.a.b$7 */
    class C23327 extends C2329a {
        /* renamed from: a */
        final /* synthetic */ C2339b f7749a;

        C23327(C2339b c2339b) {
            this.f7749a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationCancel(Animator animator) {
            this.f7749a.m11640a(6);
        }

        @TargetApi(11)
        public void onAnimationEnd(Animator animator) {
            this.f7749a.m11640a(6);
        }
    }

    /* renamed from: d.a.a.a.b$8 */
    class C23338 implements AnimatorUpdateListener {
        /* renamed from: a */
        final /* synthetic */ C2339b f7750a;

        C23338(C2339b c2339b) {
            this.f7750a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.f7750a.f7848i = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.f7750a.f7841b.f7802f = this.f7750a.f7845f * this.f7750a.f7848i;
            this.f7750a.f7841b.f7801e = this.f7750a.f7844e * this.f7750a.f7848i;
            this.f7750a.f7841b.f7800d.setAlpha((int) (((float) this.f7750a.f7839Q) * this.f7750a.f7848i));
            this.f7750a.f7841b.f7799c.setAlpha((int) (((float) this.f7750a.f7838P) * this.f7750a.f7848i));
            if (this.f7750a.f7865z != null) {
                this.f7750a.f7865z.setAlpha((int) (((float) this.f7750a.f7858s) * this.f7750a.f7848i));
            }
            if (this.f7750a.f7864y != null) {
                this.f7750a.f7864y.setAlpha((int) (((float) this.f7750a.f7857r) * this.f7750a.f7848i));
            }
            if (this.f7750a.f7841b.f7805i != null) {
                this.f7750a.f7841b.f7805i.setAlpha(this.f7750a.f7841b.f7799c.getAlpha());
            }
            this.f7750a.f7841b.f7798b.set(this.f7750a.f7841b.f7797a.x + ((this.f7750a.f7846g.x - this.f7750a.f7841b.f7797a.x) * this.f7750a.f7848i), this.f7750a.f7841b.f7797a.y + ((this.f7750a.f7846g.y - this.f7750a.f7841b.f7797a.y) * this.f7750a.f7848i));
            this.f7750a.f7841b.invalidate();
        }
    }

    /* renamed from: d.a.a.a.b$9 */
    class C23349 extends C2329a {
        /* renamed from: a */
        final /* synthetic */ C2339b f7751a;

        C23349(C2339b c2339b) {
            this.f7751a = c2339b;
        }

        @TargetApi(11)
        public void onAnimationCancel(Animator animator) {
            animator.removeAllListeners();
            this.f7751a.f7848i = 1.0f;
            this.f7751a.f7841b.f7798b.set(this.f7751a.f7846g);
            this.f7751a.f7859t = null;
        }

        @TargetApi(11)
        public void onAnimationEnd(Animator animator) {
            animator.removeAllListeners();
            this.f7751a.f7859t = null;
            this.f7751a.f7848i = 1.0f;
            this.f7751a.f7841b.f7798b.set(this.f7751a.f7846g);
            if (this.f7751a.f7833K) {
                this.f7751a.m11648g();
            }
            this.f7751a.m11643b(2);
        }
    }

    /* renamed from: d.a.a.a.b$b */
    public static class C2335b {
        /* renamed from: A */
        private boolean f7752A;
        /* renamed from: B */
        private boolean f7753B;
        /* renamed from: C */
        private Typeface f7754C;
        /* renamed from: D */
        private Typeface f7755D;
        /* renamed from: E */
        private int f7756E;
        /* renamed from: F */
        private int f7757F;
        /* renamed from: G */
        private ColorStateList f7758G;
        /* renamed from: H */
        private Mode f7759H;
        /* renamed from: I */
        private boolean f7760I;
        /* renamed from: J */
        private int f7761J;
        /* renamed from: K */
        private View f7762K;
        /* renamed from: L */
        private boolean f7763L;
        /* renamed from: M */
        private int f7764M;
        /* renamed from: N */
        private int f7765N;
        /* renamed from: O */
        private View f7766O;
        /* renamed from: P */
        private float f7767P;
        /* renamed from: a */
        final C2322d f7768a;
        /* renamed from: b */
        private boolean f7769b;
        /* renamed from: c */
        private View f7770c;
        /* renamed from: d */
        private PointF f7771d;
        /* renamed from: e */
        private String f7772e;
        /* renamed from: f */
        private String f7773f;
        /* renamed from: g */
        private int f7774g;
        /* renamed from: h */
        private int f7775h;
        /* renamed from: i */
        private int f7776i;
        /* renamed from: j */
        private int f7777j;
        /* renamed from: k */
        private int f7778k;
        /* renamed from: l */
        private int f7779l;
        /* renamed from: m */
        private float f7780m;
        /* renamed from: n */
        private float f7781n;
        /* renamed from: o */
        private float f7782o;
        /* renamed from: p */
        private float f7783p;
        /* renamed from: q */
        private float f7784q;
        /* renamed from: r */
        private float f7785r;
        /* renamed from: s */
        private Interpolator f7786s;
        /* renamed from: t */
        private Drawable f7787t;
        /* renamed from: u */
        private boolean f7788u;
        @Deprecated
        /* renamed from: v */
        private C2336c f7789v;
        /* renamed from: w */
        private C2337d f7790w;
        /* renamed from: x */
        private boolean f7791x;
        /* renamed from: y */
        private float f7792y;
        /* renamed from: z */
        private boolean f7793z;

        public C2335b(Activity activity) {
            this(activity, 0);
        }

        public C2335b(Activity activity, int i) {
            this(new C2323a(activity), i);
        }

        public C2335b(C2322d c2322d, int i) {
            this.f7758G = null;
            this.f7759H = null;
            this.f7763L = true;
            this.f7764M = 8388611;
            this.f7765N = 8388611;
            this.f7768a = c2322d;
            if (i == 0) {
                TypedValue typedValue = new TypedValue();
                this.f7768a.mo3375e().resolveAttribute(C2340a.MaterialTapTargetPromptTheme, typedValue, true);
                i = typedValue.resourceId;
            }
            float f = this.f7768a.mo3374d().getDisplayMetrics().density;
            this.f7767P = 88.0f * f;
            TypedArray a = this.f7768a.mo3370a(i, C2342c.PromptView);
            this.f7774g = a.getColor(C2342c.PromptView_primaryTextColour, -1);
            this.f7775h = a.getColor(C2342c.PromptView_secondaryTextColour, Color.argb(179, 255, 255, 255));
            this.f7772e = a.getString(C2342c.PromptView_primaryText);
            this.f7773f = a.getString(C2342c.PromptView_secondaryText);
            this.f7776i = a.getColor(C2342c.PromptView_backgroundColour, Color.argb(244, 63, 81, 181));
            this.f7777j = a.getColor(C2342c.PromptView_focalColour, -1);
            this.f7780m = a.getDimension(C2342c.PromptView_focalRadius, 44.0f * f);
            this.f7781n = a.getDimension(C2342c.PromptView_primaryTextSize, 22.0f * f);
            this.f7782o = a.getDimension(C2342c.PromptView_secondaryTextSize, 18.0f * f);
            this.f7783p = a.getDimension(C2342c.PromptView_maxTextWidth, 400.0f * f);
            this.f7784q = a.getDimension(C2342c.PromptView_textPadding, 40.0f * f);
            this.f7785r = a.getDimension(C2342c.PromptView_focalToTextPadding, 20.0f * f);
            this.f7792y = a.getDimension(C2342c.PromptView_textSeparation, f * 16.0f);
            this.f7793z = a.getBoolean(C2342c.PromptView_autoDismiss, true);
            this.f7752A = a.getBoolean(C2342c.PromptView_autoFinish, true);
            this.f7753B = a.getBoolean(C2342c.PromptView_captureTouchEventOutsidePrompt, false);
            this.f7791x = a.getBoolean(C2342c.PromptView_captureTouchEventOnFocal, false);
            this.f7756E = a.getInt(C2342c.PromptView_primaryTextStyle, 0);
            this.f7757F = a.getInt(C2342c.PromptView_secondaryTextStyle, 0);
            this.f7754C = m11616a(a.getString(C2342c.PromptView_primaryTextFontFamily), a.getInt(C2342c.PromptView_primaryTextTypeface, 0), this.f7756E);
            this.f7755D = m11616a(a.getString(C2342c.PromptView_secondaryTextFontFamily), a.getInt(C2342c.PromptView_secondaryTextTypeface, 0), this.f7757F);
            this.f7778k = a.getInt(C2342c.PromptView_backgroundColourAlpha, 244);
            this.f7779l = a.getInt(C2342c.PromptView_focalColourAlpha, 255);
            this.f7761J = a.getColor(C2342c.PromptView_iconColourFilter, this.f7776i);
            this.f7758G = a.getColorStateList(C2342c.PromptView_iconTint);
            this.f7759H = m11618a(a.getInt(C2342c.PromptView_iconTintMode, -1), Mode.MULTIPLY);
            this.f7760I = true;
            int resourceId = a.getResourceId(C2342c.PromptView_target, 0);
            a.recycle();
            if (resourceId != 0) {
                this.f7770c = this.f7768a.mo3371a(resourceId);
                if (this.f7770c != null) {
                    this.f7769b = true;
                }
            }
            this.f7766O = this.f7768a.mo3371a(16908290);
        }

        /* renamed from: a */
        private Typeface m11616a(String str, int i, int i2) {
            Typeface typeface = null;
            if (str != null) {
                typeface = Typeface.create(str, i2);
                if (typeface != null) {
                    return typeface;
                }
            }
            switch (i) {
                case 1:
                    return Typeface.SANS_SERIF;
                case 2:
                    return Typeface.SERIF;
                case 3:
                    return Typeface.MONOSPACE;
                default:
                    return typeface;
            }
        }

        /* renamed from: a */
        private void m11617a(TextPaint textPaint, Typeface typeface, int i) {
            if (i > 0) {
                Typeface defaultFromStyle = typeface == null ? Typeface.defaultFromStyle(i) : Typeface.create(typeface, i);
                textPaint.setTypeface(defaultFromStyle);
                int style = i & ((defaultFromStyle != null ? defaultFromStyle.getStyle() : 0) ^ -1);
                textPaint.setFakeBoldText((style & 1) != 0);
                textPaint.setTextSkewX((style & 2) != 0 ? -0.25f : BitmapDescriptorFactory.HUE_RED);
                return;
            }
            textPaint.setTypeface(typeface);
        }

        /* renamed from: a */
        Mode m11618a(int i, Mode mode) {
            switch (i) {
                case 3:
                    return Mode.SRC_OVER;
                case 5:
                    return Mode.SRC_IN;
                case 9:
                    return Mode.SRC_ATOP;
                case 14:
                    return Mode.MULTIPLY;
                case 15:
                    return Mode.SCREEN;
                case 16:
                    return VERSION.SDK_INT >= 11 ? Mode.valueOf("ADD") : mode;
                default:
                    return mode;
            }
        }

        @SuppressLint({"RtlHardcoded"})
        /* renamed from: a */
        Alignment m11619a(int i, String str) {
            int absoluteGravity;
            if (m11632d()) {
                int c = m11631c();
                if (str != null && c == 1 && new Bidi(str, -2).isRightToLeft()) {
                    if (i == 8388611) {
                        i = 8388613;
                    } else if (i == 8388613) {
                        i = 8388611;
                    }
                }
                absoluteGravity = Gravity.getAbsoluteGravity(i, c);
            } else {
                absoluteGravity = (i & 8388611) == 8388611 ? 3 : (i & 8388613) == 8388613 ? 5 : i & 7;
            }
            switch (absoluteGravity) {
                case 1:
                    return Alignment.ALIGN_CENTER;
                case 5:
                    return Alignment.ALIGN_OPPOSITE;
                default:
                    return Alignment.ALIGN_NORMAL;
            }
        }

        /* renamed from: a */
        public C2335b m11620a(int i) {
            this.f7764M = i;
            this.f7765N = i;
            return this;
        }

        /* renamed from: a */
        public C2335b m11621a(Typeface typeface) {
            return m11622a(typeface, 0);
        }

        /* renamed from: a */
        public C2335b m11622a(Typeface typeface, int i) {
            this.f7754C = typeface;
            this.f7756E = i;
            return this;
        }

        /* renamed from: a */
        public C2335b m11623a(View view) {
            this.f7770c = view;
            this.f7769b = this.f7770c != null;
            return this;
        }

        @Deprecated
        /* renamed from: a */
        public C2335b m11624a(C2336c c2336c) {
            this.f7789v = c2336c;
            return this;
        }

        /* renamed from: a */
        public C2335b m11625a(String str) {
            this.f7772e = str;
            return this;
        }

        /* renamed from: a */
        public C2339b m11626a() {
            if (!this.f7769b || (this.f7772e == null && this.f7773f == null)) {
                return null;
            }
            C2339b c2339b = new C2339b(this.f7768a);
            if (this.f7770c != null) {
                c2339b.f7842c = this.f7770c;
                c2339b.f7841b.f7820x = this.f7770c;
            } else {
                c2339b.f7843d = this.f7771d;
            }
            c2339b.f7827E = this.f7768a.mo3372b();
            C2338e c2338e = c2339b.f7841b;
            boolean z = VERSION.SDK_INT >= 11 && this.f7763L;
            c2338e.f7816t = z;
            c2339b.f7833K = this.f7763L;
            c2339b.f7828F = this.f7766O;
            c2339b.f7849j = this.f7772e;
            c2339b.f7857r = Color.alpha(this.f7774g);
            c2339b.f7850k = this.f7773f;
            c2339b.f7858s = Color.alpha(this.f7775h);
            c2339b.f7851l = this.f7783p;
            c2339b.f7852m = this.f7784q;
            c2339b.f7856q = this.f7785r;
            c2339b.f7863x = 150;
            c2339b.f7837O = this.f7767P;
            c2339b.f7838P = this.f7778k;
            c2339b.f7839Q = this.f7779l;
            c2339b.f7841b.f7822z = this.f7792y;
            c2339b.f7823A = this.f7789v;
            c2339b.f7824B = this.f7790w;
            c2339b.f7841b.f7818v = this.f7791x;
            if (this.f7786s != null) {
                c2339b.f7861v = this.f7786s;
            } else {
                c2339b.f7861v = new AccelerateDecelerateInterpolator();
            }
            c2339b.f7844e = this.f7780m;
            c2339b.f7847h = (this.f7780m / 100.0f) * 10.0f;
            if (this.f7787t != null) {
                this.f7787t.mutate();
                this.f7787t.setBounds(0, 0, this.f7787t.getIntrinsicWidth(), this.f7787t.getIntrinsicHeight());
                if (this.f7760I) {
                    if (this.f7758G == null) {
                        this.f7787t.setColorFilter(this.f7761J, this.f7759H);
                        this.f7787t.setAlpha(Color.alpha(this.f7761J));
                    } else if (VERSION.SDK_INT >= 21) {
                        this.f7787t.setTintList(this.f7758G);
                    }
                }
            }
            c2339b.f7841b.f7796C = this.f7788u;
            c2339b.f7841b.f7805i = this.f7787t;
            c2339b.f7841b.f7800d = new Paint();
            c2339b.f7841b.f7800d.setColor(this.f7777j);
            c2339b.f7841b.f7800d.setAlpha(this.f7779l);
            c2339b.f7841b.f7800d.setAntiAlias(true);
            c2339b.f7841b.f7799c = new Paint();
            c2339b.f7841b.f7799c.setColor(this.f7776i);
            c2339b.f7841b.f7799c.setAlpha(this.f7778k);
            c2339b.f7841b.f7799c.setAntiAlias(true);
            if (this.f7772e != null) {
                c2339b.f7864y = new TextPaint();
                c2339b.f7864y.setColor(this.f7774g);
                c2339b.f7864y.setAlpha(Color.alpha(this.f7774g));
                c2339b.f7864y.setAntiAlias(true);
                c2339b.f7864y.setTextSize(this.f7781n);
                m11617a(c2339b.f7864y, this.f7754C, this.f7756E);
                c2339b.f7834L = m11619a(this.f7764M, this.f7772e);
            }
            if (this.f7773f != null) {
                c2339b.f7865z = new TextPaint();
                c2339b.f7865z.setColor(this.f7775h);
                c2339b.f7865z.setAlpha(Color.alpha(this.f7775h));
                c2339b.f7865z.setAntiAlias(true);
                c2339b.f7865z.setTextSize(this.f7782o);
                m11617a(c2339b.f7865z, this.f7755D, this.f7757F);
                c2339b.f7835M = m11619a(this.f7765N, this.f7773f);
            }
            c2339b.f7831I = this.f7793z;
            c2339b.f7832J = this.f7752A;
            c2339b.f7841b.f7795B = this.f7753B;
            if (this.f7762K == null) {
                c2339b.f7841b.f7821y = c2339b.f7841b.f7820x;
            } else {
                c2339b.f7841b.f7821y = this.f7762K;
            }
            return c2339b;
        }

        /* renamed from: b */
        public C2335b m11627b(Typeface typeface) {
            return m11628b(typeface, 0);
        }

        /* renamed from: b */
        public C2335b m11628b(Typeface typeface, int i) {
            this.f7755D = typeface;
            this.f7757F = i;
            return this;
        }

        /* renamed from: b */
        public C2335b m11629b(String str) {
            this.f7773f = str;
            return this;
        }

        /* renamed from: b */
        public C2339b m11630b() {
            C2339b a = m11626a();
            if (a != null) {
                a.m11638a();
            }
            return a;
        }

        @TargetApi(17)
        /* renamed from: c */
        int m11631c() {
            return this.f7768a.mo3374d().getConfiguration().getLayoutDirection();
        }

        /* renamed from: d */
        boolean m11632d() {
            return VERSION.SDK_INT >= 17;
        }
    }

    @Deprecated
    /* renamed from: d.a.a.a.b$c */
    public interface C2336c {
        @Deprecated
        void onHidePrompt(MotionEvent motionEvent, boolean z);

        @Deprecated
        void onHidePromptComplete();
    }

    /* renamed from: d.a.a.a.b$d */
    public interface C2337d {
        /* renamed from: a */
        void m11633a(C2339b c2339b, int i);
    }

    /* renamed from: d.a.a.a.b$e */
    static class C2338e extends View {
        /* renamed from: A */
        boolean f7794A;
        /* renamed from: B */
        boolean f7795B;
        /* renamed from: C */
        boolean f7796C;
        /* renamed from: a */
        PointF f7797a = new PointF();
        /* renamed from: b */
        PointF f7798b = new PointF();
        /* renamed from: c */
        Paint f7799c;
        /* renamed from: d */
        Paint f7800d;
        /* renamed from: e */
        float f7801e;
        /* renamed from: f */
        float f7802f;
        /* renamed from: g */
        float f7803g;
        /* renamed from: h */
        int f7804h;
        /* renamed from: i */
        Drawable f7805i;
        /* renamed from: j */
        float f7806j;
        /* renamed from: k */
        float f7807k;
        /* renamed from: l */
        float f7808l;
        /* renamed from: m */
        float f7809m;
        /* renamed from: n */
        float f7810n;
        /* renamed from: o */
        float f7811o;
        /* renamed from: p */
        float f7812p;
        /* renamed from: q */
        float f7813q;
        /* renamed from: r */
        Layout f7814r;
        /* renamed from: s */
        Layout f7815s;
        /* renamed from: t */
        boolean f7816t;
        /* renamed from: u */
        C2324a f7817u;
        /* renamed from: v */
        boolean f7818v;
        /* renamed from: w */
        Rect f7819w;
        /* renamed from: x */
        View f7820x;
        /* renamed from: y */
        View f7821y;
        /* renamed from: z */
        float f7822z;

        /* renamed from: d.a.a.a.b$e$a */
        public interface C2324a {
            /* renamed from: a */
            void mo3376a();

            @Deprecated
            /* renamed from: a */
            void mo3377a(MotionEvent motionEvent, boolean z);

            /* renamed from: b */
            void mo3378b();
        }

        public C2338e(Context context) {
            super(context);
            this.f7816t = VERSION.SDK_INT >= 11;
            this.f7819w = new Rect();
            setId(C2341b.material_target_prompt_view);
            setFocusableInTouchMode(true);
            requestFocus();
        }

        /* renamed from: a */
        boolean m11634a(float f, float f2, PointF pointF, float f3) {
            return Math.pow((double) (f - pointF.x), 2.0d) + Math.pow((double) (f2 - pointF.y), 2.0d) < Math.pow((double) f3, 2.0d);
        }

        public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
            if (this.f7796C && keyEvent.getKeyCode() == 4) {
                DispatcherState keyDispatcherState = getKeyDispatcherState();
                if (keyDispatcherState != null) {
                    if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                        keyDispatcherState.startTracking(keyEvent, this);
                        return true;
                    } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && keyDispatcherState.isTracking(keyEvent)) {
                        if (this.f7817u == null) {
                            return true;
                        }
                        this.f7817u.mo3378b();
                        this.f7817u.mo3377a(null, false);
                        return true;
                    }
                }
            }
            return super.dispatchKeyEventPreIme(keyEvent);
        }

        public void onDraw(Canvas canvas) {
            if (this.f7802f > BitmapDescriptorFactory.HUE_RED) {
                if (this.f7794A) {
                    canvas.clipRect(this.f7819w);
                }
                canvas.drawCircle(this.f7798b.x, this.f7798b.y, this.f7802f, this.f7799c);
                if (this.f7816t) {
                    int alpha = this.f7800d.getAlpha();
                    this.f7800d.setAlpha(this.f7804h);
                    canvas.drawCircle(this.f7797a.x, this.f7797a.y, this.f7803g, this.f7800d);
                    this.f7800d.setAlpha(alpha);
                }
                canvas.drawCircle(this.f7797a.x, this.f7797a.y, this.f7801e, this.f7800d);
                if (this.f7805i != null) {
                    canvas.translate(this.f7806j, this.f7807k);
                    this.f7805i.draw(canvas);
                    canvas.translate(-this.f7806j, -this.f7807k);
                } else if (this.f7821y != null) {
                    canvas.translate(this.f7806j, this.f7807k);
                    this.f7821y.draw(canvas);
                    canvas.translate(-this.f7806j, -this.f7807k);
                }
                canvas.translate(this.f7808l - this.f7809m, this.f7810n);
                if (this.f7814r != null) {
                    this.f7814r.draw(canvas);
                }
                if (this.f7815s != null) {
                    canvas.translate(((-(this.f7808l - this.f7809m)) + this.f7811o) - this.f7812p, this.f7813q);
                    this.f7815s.draw(canvas);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            boolean z = (!this.f7794A || this.f7819w.contains((int) x, (int) y)) && m11634a(x, y, this.f7798b, this.f7802f);
            if (z && m11634a(x, y, this.f7797a, this.f7801e)) {
                z = this.f7818v;
                if (this.f7817u != null) {
                    this.f7817u.mo3376a();
                    this.f7817u.mo3377a(motionEvent, true);
                }
            } else {
                if (!z) {
                    z = this.f7795B;
                }
                if (this.f7817u != null) {
                    this.f7817u.mo3378b();
                    this.f7817u.mo3377a(motionEvent, false);
                }
            }
            return z;
        }
    }

    C2339b(C2322d c2322d) {
        this.f7840a = c2322d;
        this.f7841b = new C2338e(this.f7840a.mo3373c());
        this.f7841b.f7817u = new C23251(this);
        Rect rect = new Rect();
        this.f7840a.mo3372b().getWindowVisibleDisplayFrame(rect);
        this.f7829G = (float) rect.top;
        this.f7830H = new C23273(this);
    }

    /* renamed from: a */
    private StaticLayout m11635a(String str, TextPaint textPaint, int i, Alignment alignment) {
        if (VERSION.SDK_INT < 23) {
            return new StaticLayout(str, textPaint, i, alignment, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        }
        Builder obtain = Builder.obtain(str, 0, str.length(), textPaint, i);
        obtain.setAlignment(alignment);
        return obtain.build();
    }

    /* renamed from: b */
    private boolean m11636b(Layout layout) {
        boolean z = true;
        if (layout == null) {
            return false;
        }
        boolean z2 = layout.getAlignment() == Alignment.ALIGN_OPPOSITE;
        if (VERSION.SDK_INT >= 14) {
            boolean isRtlCharAt = layout.isRtlCharAt(0);
            z2 = (!(z2 && isRtlCharAt) && (z2 || isRtlCharAt)) || isRtlCharAt;
            if (!z2 && layout.getAlignment() == Alignment.ALIGN_NORMAL && VERSION.SDK_INT >= 17) {
                if (!(isRtlCharAt && this.f7840a.mo3374d().getConfiguration().getLayoutDirection() == 1)) {
                    z = false;
                }
                return z;
            } else if (layout.getAlignment() == Alignment.ALIGN_OPPOSITE && isRtlCharAt) {
                return false;
            }
        }
        return z2;
    }

    /* renamed from: a */
    float m11637a(Layout layout) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (layout != null) {
            int i = 0;
            int lineCount = layout.getLineCount();
            while (i < lineCount) {
                float max = Math.max(f, layout.getLineWidth(i));
                i++;
                f = max;
            }
        }
        return f;
    }

    /* renamed from: a */
    public void m11638a() {
        this.f7827E.addView(this.f7841b);
        m11642b();
        m11643b(1);
        if (VERSION.SDK_INT >= 11) {
            m11647f();
            return;
        }
        this.f7848i = 1.0f;
        this.f7841b.f7802f = this.f7845f;
        this.f7841b.f7801e = this.f7844e;
        this.f7841b.f7800d.setAlpha(this.f7839Q);
        this.f7841b.f7799c.setAlpha(this.f7838P);
        this.f7865z.setAlpha(this.f7858s);
        this.f7864y.setAlpha(this.f7857r);
        m11643b(2);
    }

    /* renamed from: a */
    void m11639a(float f) {
        float f2;
        float f3;
        if (this.f7855p) {
            float f4 = this.f7841b.f7797a.x;
            float f5 = this.f7841b.f7808l - this.f7852m;
            if (this.f7853n) {
                f2 = this.f7852m + (this.f7841b.f7797a.y + this.f7844e);
                f3 = this.f7841b.f7810n;
            } else {
                f2 = this.f7841b.f7797a.y - ((this.f7844e + this.f7856q) + this.f7852m);
                f3 = this.f7841b.f7810n + ((float) this.f7841b.f7814r.getHeight());
                if (this.f7841b.f7815s != null) {
                    f3 += ((float) this.f7841b.f7815s.getHeight()) + this.f7841b.f7822z;
                }
            }
            float f6 = ((f5 + f) + this.f7852m) + this.f7852m;
            float f7 = (this.f7841b.f7797a.x - this.f7844e) - this.f7856q;
            float f8 = (this.f7841b.f7797a.x + this.f7844e) + this.f7856q;
            if (f5 <= f7 || f5 >= f8) {
                if (f6 > f7 && f6 < f8) {
                    if (this.f7853n) {
                        f4 += this.f7844e + this.f7856q;
                    } else {
                        f6 += this.f7844e + this.f7856q;
                    }
                }
            } else if (this.f7853n) {
                f4 -= this.f7844e - this.f7856q;
            } else {
                f5 -= this.f7844e - this.f7856q;
            }
            double pow = Math.pow((double) f5, 2.0d) + Math.pow((double) f3, 2.0d);
            double pow2 = ((Math.pow((double) f4, 2.0d) + Math.pow((double) f2, 2.0d)) - pow) / 2.0d;
            pow = ((pow - Math.pow((double) f6, 2.0d)) - Math.pow((double) f3, 2.0d)) / 2.0d;
            double d = 1.0d / ((double) (((f4 - f5) * (f3 - f3)) - ((f5 - f6) * (f2 - f3))));
            this.f7846g.set((float) (((((double) (f3 - f3)) * pow2) - (((double) (f2 - f3)) * pow)) * d), (float) (((pow * ((double) (f4 - f5))) - (pow2 * ((double) (f5 - f6)))) * d));
            this.f7845f = (float) Math.sqrt(Math.pow((double) (f3 - this.f7846g.y), 2.0d) + Math.pow((double) (f5 - this.f7846g.x), 2.0d));
        } else {
            this.f7846g.set(this.f7841b.f7797a.x, this.f7841b.f7797a.y);
            f3 = this.f7841b.f7808l;
            if (this.f7854o) {
                f = BitmapDescriptorFactory.HUE_RED;
            }
            f2 = this.f7852m + Math.abs((f3 + f) - this.f7841b.f7797a.x);
            f3 = this.f7844e + this.f7856q;
            if (this.f7841b.f7814r != null) {
                f3 += (float) this.f7841b.f7814r.getHeight();
            }
            if (this.f7841b.f7815s != null) {
                f3 += ((float) this.f7841b.f7815s.getHeight()) + this.f7841b.f7822z;
            }
            this.f7845f = (float) Math.sqrt(Math.pow((double) f3, 2.0d) + Math.pow((double) f2, 2.0d));
        }
        this.f7841b.f7798b.set(this.f7846g);
        this.f7841b.f7802f = this.f7845f * this.f7848i;
    }

    /* renamed from: a */
    void m11640a(int i) {
        if (VERSION.SDK_INT >= 11 && this.f7859t != null) {
            this.f7859t.removeAllUpdateListeners();
            this.f7859t = null;
        }
        m11644c();
        this.f7827E.removeView(this.f7841b);
        if (this.f7825C || this.f7826D) {
            m11653l();
            m11643b(i);
            this.f7825C = false;
            this.f7826D = false;
        }
    }

    @Deprecated
    /* renamed from: a */
    protected void m11641a(MotionEvent motionEvent, boolean z) {
        if (this.f7823A != null) {
            this.f7823A.onHidePrompt(motionEvent, z);
        }
    }

    /* renamed from: b */
    void m11642b() {
        ViewTreeObserver viewTreeObserver = this.f7827E.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(this.f7830H);
        }
    }

    /* renamed from: b */
    protected void m11643b(int i) {
        if (this.f7824B != null) {
            this.f7824B.m11633a(this, i);
        }
    }

    /* renamed from: c */
    void m11644c() {
        ViewTreeObserver viewTreeObserver = this.f7827E.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return;
        }
        if (VERSION.SDK_INT >= 16) {
            viewTreeObserver.removeOnGlobalLayoutListener(this.f7830H);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(this.f7830H);
        }
    }

    /* renamed from: d */
    public void m11645d() {
        if (VERSION.SDK_INT < 11) {
            m11640a(4);
        } else if (!this.f7825C && !this.f7826D) {
            this.f7825C = true;
            if (this.f7859t != null) {
                this.f7859t.removeAllListeners();
                this.f7859t.cancel();
                this.f7859t = null;
            }
            this.f7859t = ValueAnimator.ofFloat(new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
            this.f7859t.setDuration(225);
            this.f7859t.setInterpolator(this.f7861v);
            this.f7859t.addUpdateListener(new C23284(this));
            this.f7859t.addListener(new C23305(this));
            this.f7859t.start();
        }
    }

    /* renamed from: e */
    public void m11646e() {
        if (VERSION.SDK_INT < 11) {
            m11640a(6);
        } else if (!this.f7825C && !this.f7826D) {
            this.f7825C = true;
            if (this.f7859t != null) {
                this.f7859t.removeAllListeners();
                this.f7859t.cancel();
                this.f7859t = null;
            }
            this.f7859t = ValueAnimator.ofFloat(new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
            this.f7859t.setDuration(225);
            this.f7859t.setInterpolator(this.f7861v);
            this.f7859t.addUpdateListener(new C23316(this));
            this.f7859t.addListener(new C23327(this));
            this.f7859t.start();
        }
    }

    @TargetApi(11)
    /* renamed from: f */
    void m11647f() {
        if (this.f7865z != null) {
            this.f7865z.setAlpha(0);
        }
        if (this.f7864y != null) {
            this.f7864y.setAlpha(0);
        }
        this.f7841b.f7799c.setAlpha(0);
        this.f7841b.f7800d.setAlpha(0);
        this.f7841b.f7801e = BitmapDescriptorFactory.HUE_RED;
        this.f7841b.f7802f = BitmapDescriptorFactory.HUE_RED;
        this.f7841b.f7798b.set(this.f7841b.f7797a);
        if (this.f7841b.f7805i != null) {
            this.f7841b.f7805i.setAlpha(0);
        }
        this.f7848i = BitmapDescriptorFactory.HUE_RED;
        this.f7859t = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        this.f7859t.setInterpolator(this.f7861v);
        this.f7859t.setDuration(225);
        this.f7859t.addUpdateListener(new C23338(this));
        this.f7859t.addListener(new C23349(this));
        this.f7859t.start();
    }

    @TargetApi(11)
    /* renamed from: g */
    void m11648g() {
        if (this.f7859t != null) {
            this.f7859t.removeAllUpdateListeners();
            this.f7859t.cancel();
            this.f7859t = null;
        }
        this.f7859t = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, this.f7847h, BitmapDescriptorFactory.HUE_RED});
        this.f7859t.setInterpolator(this.f7861v);
        this.f7859t.setDuration(1000);
        this.f7859t.setStartDelay(225);
        this.f7859t.setRepeatCount(-1);
        this.f7859t.addUpdateListener(new AnimatorUpdateListener(this) {
            /* renamed from: a */
            boolean f7741a = true;
            /* renamed from: b */
            final /* synthetic */ C2339b f7742b;

            {
                this.f7742b = r2;
            }

            @TargetApi(11)
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                boolean z = this.f7741a;
                if (floatValue < this.f7742b.f7862w && this.f7741a) {
                    z = false;
                } else if (floatValue > this.f7742b.f7862w && !this.f7741a) {
                    z = true;
                }
                if (!(z == this.f7741a || z)) {
                    this.f7742b.f7860u.start();
                }
                this.f7741a = z;
                this.f7742b.f7862w = floatValue;
                this.f7742b.f7841b.f7801e = this.f7742b.f7844e + this.f7742b.f7862w;
                this.f7742b.f7841b.invalidate();
            }
        });
        this.f7859t.start();
        if (this.f7860u != null) {
            this.f7860u.removeAllUpdateListeners();
            this.f7860u.cancel();
            this.f7860u = null;
        }
        float f = this.f7844e + this.f7847h;
        this.f7860u = ValueAnimator.ofFloat(new float[]{f, f + (this.f7847h * 6.0f)});
        this.f7860u.setInterpolator(this.f7861v);
        this.f7860u.setDuration(500);
        this.f7860u.addUpdateListener(new C23262(this));
    }

    /* renamed from: h */
    void m11649h() {
        boolean z = false;
        m11652k();
        if (this.f7842c != null) {
            int[] iArr = new int[2];
            this.f7841b.getLocationInWindow(iArr);
            int[] iArr2 = new int[2];
            this.f7842c.getLocationInWindow(iArr2);
            this.f7841b.f7797a.x = (float) ((iArr2[0] - iArr[0]) + (this.f7842c.getWidth() / 2));
            this.f7841b.f7797a.y = (float) ((iArr2[1] - iArr[1]) + (this.f7842c.getHeight() / 2));
        } else {
            this.f7841b.f7797a.x = this.f7843d.x;
            this.f7841b.f7797a.y = this.f7843d.y;
        }
        this.f7853n = this.f7841b.f7797a.y > ((float) this.f7841b.f7819w.centerY());
        this.f7854o = this.f7841b.f7797a.x > ((float) this.f7841b.f7819w.centerX());
        if ((this.f7841b.f7797a.x > this.f7836N.left && this.f7841b.f7797a.x < this.f7836N.right) || (this.f7841b.f7797a.y > this.f7836N.top && this.f7841b.f7797a.y < this.f7836N.bottom)) {
            z = true;
        }
        this.f7855p = z;
        m11650i();
        m11651j();
    }

    /* renamed from: i */
    void m11650i() {
        float min;
        float max = Math.max(80.0f, Math.min(this.f7851l, ((float) (this.f7841b.f7794A ? this.f7841b.f7819w.right - this.f7841b.f7819w.left : this.f7827E.getWidth())) - (this.f7852m * 2.0f)));
        if (this.f7849j != null) {
            this.f7841b.f7814r = m11635a(this.f7849j, this.f7864y, (int) max, this.f7834L);
        } else {
            this.f7841b.f7814r = null;
        }
        if (this.f7850k != null) {
            this.f7841b.f7815s = m11635a(this.f7850k, this.f7865z, (int) max, this.f7835M);
        } else {
            this.f7841b.f7815s = null;
        }
        float max2 = Math.max(m11637a(this.f7841b.f7814r), m11637a(this.f7841b.f7815s));
        if (this.f7855p) {
            this.f7841b.f7808l = (float) this.f7841b.f7819w.left;
            min = Math.min(max2, max);
            if (this.f7854o) {
                this.f7841b.f7808l = (this.f7841b.f7797a.x - min) + this.f7856q;
            } else {
                this.f7841b.f7808l = (this.f7841b.f7797a.x - min) - this.f7856q;
            }
            if (this.f7841b.f7808l < ((float) this.f7841b.f7819w.left) + this.f7852m) {
                this.f7841b.f7808l = ((float) this.f7841b.f7819w.left) + this.f7852m;
            }
            if (this.f7841b.f7808l + min > ((float) this.f7841b.f7819w.right) - this.f7852m) {
                this.f7841b.f7808l = (((float) this.f7841b.f7819w.right) - this.f7852m) - min;
            }
        } else if (this.f7854o) {
            this.f7841b.f7808l = (((float) (this.f7841b.f7794A ? this.f7841b.f7819w.right : this.f7827E.getRight())) - this.f7852m) - max2;
        } else {
            this.f7841b.f7808l = ((float) (this.f7841b.f7794A ? this.f7841b.f7819w.left : this.f7827E.getLeft())) + this.f7852m;
        }
        this.f7841b.f7810n = this.f7841b.f7797a.y;
        C2338e c2338e;
        if (this.f7853n) {
            this.f7841b.f7810n = (this.f7841b.f7810n - this.f7844e) - this.f7856q;
            if (this.f7841b.f7814r != null) {
                c2338e = this.f7841b;
                c2338e.f7810n -= (float) this.f7841b.f7814r.getHeight();
            }
        } else {
            c2338e = this.f7841b;
            c2338e.f7810n += this.f7844e + this.f7856q;
        }
        if (this.f7850k != null) {
            if (this.f7853n) {
                this.f7841b.f7810n = (this.f7841b.f7810n - this.f7841b.f7822z) - ((float) this.f7841b.f7815s.getHeight());
            }
            if (this.f7841b.f7814r != null) {
                this.f7841b.f7813q = ((float) this.f7841b.f7814r.getHeight()) + this.f7841b.f7822z;
            }
        }
        m11639a(max2);
        this.f7841b.f7811o = this.f7841b.f7808l;
        this.f7841b.f7809m = BitmapDescriptorFactory.HUE_RED;
        this.f7841b.f7812p = BitmapDescriptorFactory.HUE_RED;
        min = max - max2;
        if (m11636b(this.f7841b.f7814r)) {
            this.f7841b.f7809m = min;
        }
        if (m11636b(this.f7841b.f7815s)) {
            this.f7841b.f7812p = min;
        }
    }

    /* renamed from: j */
    void m11651j() {
        if (this.f7841b.f7805i != null) {
            this.f7841b.f7806j = this.f7841b.f7797a.x - ((float) (this.f7841b.f7805i.getIntrinsicWidth() / 2));
            this.f7841b.f7807k = this.f7841b.f7797a.y - ((float) (this.f7841b.f7805i.getIntrinsicHeight() / 2));
        } else if (this.f7841b.f7821y != null) {
            int[] iArr = new int[2];
            this.f7841b.getLocationInWindow(iArr);
            int[] iArr2 = new int[2];
            this.f7841b.f7821y.getLocationInWindow(iArr2);
            this.f7841b.f7806j = (float) (iArr2[0] - iArr[0]);
            this.f7841b.f7807k = (float) (iArr2[1] - iArr[1]);
        }
    }

    /* renamed from: k */
    void m11652k() {
        if (this.f7828F != null) {
            this.f7841b.f7794A = true;
            this.f7841b.f7819w.set(0, 0, 0, 0);
            Point point = new Point();
            this.f7828F.getGlobalVisibleRect(this.f7841b.f7819w, point);
            if (point.y == 0) {
                Rect rect = this.f7841b.f7819w;
                rect.top = (int) (((float) rect.top) + this.f7829G);
            }
            this.f7836N = new RectF(this.f7841b.f7819w);
            this.f7836N.inset(this.f7837O, this.f7837O);
            return;
        }
        View a = this.f7840a.mo3371a(16908290);
        if (a != null) {
            a.getGlobalVisibleRect(this.f7841b.f7819w, new Point());
            this.f7836N = new RectF(this.f7841b.f7819w);
            this.f7836N.inset(this.f7837O, this.f7837O);
        }
        this.f7841b.f7794A = false;
    }

    @Deprecated
    /* renamed from: l */
    protected void m11653l() {
        if (this.f7823A != null) {
            this.f7823A.onHidePromptComplete();
        }
    }
}
