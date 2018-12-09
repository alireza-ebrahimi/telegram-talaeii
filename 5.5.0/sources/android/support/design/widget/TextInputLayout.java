package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.design.C0073a.C0064c;
import android.support.design.C0073a.C0067f;
import android.support.design.C0073a.C0069h;
import android.support.design.C0073a.C0070i;
import android.support.design.C0073a.C0071j;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0201w.C0083c;
import android.support.v4.content.C0235a;
import android.support.v4.p007b.p008a.C0375a;
import android.support.v4.p014d.C0085g;
import android.support.v4.p014d.C0437f;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.C0074a;
import android.support.v4.view.ah;
import android.support.v4.view.bc;
import android.support.v4.view.p023a.C0531e;
import android.support.v4.widget.C0737z;
import android.support.v4.widget.Space;
import android.support.v7.p025a.C0748a.C0746i;
import android.support.v7.p027c.p028a.C0825b;
import android.support.v7.widget.C0855y;
import android.support.v7.widget.C1069l;
import android.support.v7.widget.ai;
import android.support.v7.widget.bk;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class TextInputLayout extends LinearLayout {
    /* renamed from: A */
    private Drawable f386A;
    /* renamed from: B */
    private Drawable f387B;
    /* renamed from: C */
    private ColorStateList f388C;
    /* renamed from: D */
    private boolean f389D;
    /* renamed from: E */
    private Mode f390E;
    /* renamed from: F */
    private boolean f391F;
    /* renamed from: G */
    private ColorStateList f392G;
    /* renamed from: H */
    private ColorStateList f393H;
    /* renamed from: I */
    private boolean f394I;
    /* renamed from: J */
    private boolean f395J;
    /* renamed from: K */
    private C0201w f396K;
    /* renamed from: L */
    private boolean f397L;
    /* renamed from: M */
    private boolean f398M;
    /* renamed from: N */
    private boolean f399N;
    /* renamed from: a */
    EditText f400a;
    /* renamed from: b */
    TextView f401b;
    /* renamed from: c */
    boolean f402c;
    /* renamed from: d */
    final C0149e f403d;
    /* renamed from: e */
    private final FrameLayout f404e;
    /* renamed from: f */
    private boolean f405f;
    /* renamed from: g */
    private CharSequence f406g;
    /* renamed from: h */
    private Paint f407h;
    /* renamed from: i */
    private final Rect f408i;
    /* renamed from: j */
    private LinearLayout f409j;
    /* renamed from: k */
    private int f410k;
    /* renamed from: l */
    private Typeface f411l;
    /* renamed from: m */
    private boolean f412m;
    /* renamed from: n */
    private int f413n;
    /* renamed from: o */
    private boolean f414o;
    /* renamed from: p */
    private CharSequence f415p;
    /* renamed from: q */
    private TextView f416q;
    /* renamed from: r */
    private int f417r;
    /* renamed from: s */
    private int f418s;
    /* renamed from: t */
    private int f419t;
    /* renamed from: u */
    private boolean f420u;
    /* renamed from: v */
    private boolean f421v;
    /* renamed from: w */
    private Drawable f422w;
    /* renamed from: x */
    private CharSequence f423x;
    /* renamed from: y */
    private CheckableImageButton f424y;
    /* renamed from: z */
    private boolean f425z;

    /* renamed from: android.support.design.widget.TextInputLayout$1 */
    class C01181 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ TextInputLayout f378a;

        C01181(TextInputLayout textInputLayout) {
            this.f378a = textInputLayout;
        }

        public void afterTextChanged(Editable editable) {
            this.f378a.m645a(!this.f378a.f399N);
            if (this.f378a.f402c) {
                this.f378a.m644a(editable.length());
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: android.support.design.widget.TextInputLayout$2 */
    class C01192 extends bc {
        /* renamed from: a */
        final /* synthetic */ TextInputLayout f379a;

        C01192(TextInputLayout textInputLayout) {
            this.f379a = textInputLayout;
        }

        public void onAnimationStart(View view) {
            view.setVisibility(0);
        }
    }

    /* renamed from: android.support.design.widget.TextInputLayout$4 */
    class C01214 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ TextInputLayout f382a;

        C01214(TextInputLayout textInputLayout) {
            this.f382a = textInputLayout;
        }

        public void onClick(View view) {
            this.f382a.m642a();
        }
    }

    /* renamed from: android.support.design.widget.TextInputLayout$5 */
    class C01225 implements C0083c {
        /* renamed from: a */
        final /* synthetic */ TextInputLayout f383a;

        C01225(TextInputLayout textInputLayout) {
            this.f383a = textInputLayout;
        }

        /* renamed from: a */
        public void mo58a(C0201w c0201w) {
            this.f383a.f403d.m721b(c0201w.m948d());
        }
    }

    static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = C0437f.m1919a(new C01231());
        /* renamed from: a */
        CharSequence f384a;

        /* renamed from: android.support.design.widget.TextInputLayout$SavedState$1 */
        static class C01231 implements C0085g<SavedState> {
            C01231() {
            }

            /* renamed from: a */
            public SavedState m624a(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState[] m625a(int i) {
                return new SavedState[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return m624a(parcel, classLoader);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m625a(i);
            }
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f384a = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.f384a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.f384a, parcel, i);
        }
    }

    /* renamed from: android.support.design.widget.TextInputLayout$a */
    private class C0124a extends C0074a {
        /* renamed from: a */
        final /* synthetic */ TextInputLayout f385a;

        C0124a(TextInputLayout textInputLayout) {
            this.f385a = textInputLayout;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onInitializeAccessibilityNodeInfo(View view, C0531e c0531e) {
            super.onInitializeAccessibilityNodeInfo(view, c0531e);
            c0531e.m2313b(TextInputLayout.class.getSimpleName());
            CharSequence g = this.f385a.f403d.m731g();
            if (!TextUtils.isEmpty(g)) {
                c0531e.m2320c(g);
            }
            if (this.f385a.f400a != null) {
                c0531e.m2318c(this.f385a.f400a);
            }
            g = this.f385a.f401b != null ? this.f385a.f401b.getText() : null;
            if (!TextUtils.isEmpty(g)) {
                c0531e.m2338j(true);
                c0531e.m2327e(g);
            }
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            CharSequence g = this.f385a.f403d.m731g();
            if (!TextUtils.isEmpty(g)) {
                accessibilityEvent.getText().add(g);
            }
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.f408i = new Rect();
        this.f403d = new C0149e(this);
        C0195v.m916a(context);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.f404e = new FrameLayout(context);
        this.f404e.setAddStatesFromChildren(true);
        addView(this.f404e);
        this.f403d.m717a(C0126a.f427b);
        this.f403d.m725b(new AccelerateInterpolator());
        this.f403d.m722b(8388659);
        bk a = bk.m5654a(context, attributeSet, C0072k.TextInputLayout, i, C0071j.Widget_Design_TextInputLayout);
        this.f405f = a.m5659a(C0072k.TextInputLayout_hintEnabled, true);
        setHint(a.m5663c(C0072k.TextInputLayout_android_hint));
        this.f395J = a.m5659a(C0072k.TextInputLayout_hintAnimationEnabled, true);
        if (a.m5671g(C0072k.TextInputLayout_android_textColorHint)) {
            ColorStateList e = a.m5667e(C0072k.TextInputLayout_android_textColorHint);
            this.f393H = e;
            this.f392G = e;
        }
        if (a.m5670g(C0072k.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(a.m5670g(C0072k.TextInputLayout_hintTextAppearance, 0));
        }
        this.f413n = a.m5670g(C0072k.TextInputLayout_errorTextAppearance, 0);
        boolean a2 = a.m5659a(C0072k.TextInputLayout_errorEnabled, false);
        boolean a3 = a.m5659a(C0072k.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(a.m5656a(C0072k.TextInputLayout_counterMaxLength, -1));
        this.f418s = a.m5670g(C0072k.TextInputLayout_counterTextAppearance, 0);
        this.f419t = a.m5670g(C0072k.TextInputLayout_counterOverflowTextAppearance, 0);
        this.f421v = a.m5659a(C0072k.TextInputLayout_passwordToggleEnabled, false);
        this.f422w = a.m5657a(C0072k.TextInputLayout_passwordToggleDrawable);
        this.f423x = a.m5663c(C0072k.TextInputLayout_passwordToggleContentDescription);
        if (a.m5671g(C0072k.TextInputLayout_passwordToggleTint)) {
            this.f389D = true;
            this.f388C = a.m5667e(C0072k.TextInputLayout_passwordToggleTint);
        }
        if (a.m5671g(C0072k.TextInputLayout_passwordToggleTintMode)) {
            this.f391F = true;
            this.f390E = ad.m658a(a.m5656a(C0072k.TextInputLayout_passwordToggleTintMode, -1), null);
        }
        a.m5658a();
        setErrorEnabled(a2);
        setCounterEnabled(a3);
        m641i();
        if (ah.m2803d(this) == 0) {
            ah.m2801c((View) this, 1);
        }
        ah.m2783a((View) this, new C0124a(this));
    }

    /* renamed from: a */
    private static void m626a(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                m626a((ViewGroup) childAt, z);
            }
        }
    }

    /* renamed from: a */
    private void m627a(TextView textView) {
        if (this.f409j != null) {
            this.f409j.removeView(textView);
            int i = this.f410k - 1;
            this.f410k = i;
            if (i == 0) {
                this.f409j.setVisibility(8);
            }
        }
    }

    /* renamed from: a */
    private void m628a(TextView textView, int i) {
        if (this.f409j == null) {
            this.f409j = new LinearLayout(getContext());
            this.f409j.setOrientation(0);
            addView(this.f409j, -1, -2);
            this.f409j.addView(new Space(getContext()), new LayoutParams(0, 0, 1.0f));
            if (this.f400a != null) {
                m634c();
            }
        }
        this.f409j.setVisibility(0);
        this.f409j.addView(textView, i);
        this.f410k++;
    }

    /* renamed from: a */
    private void m629a(final CharSequence charSequence, boolean z) {
        boolean z2 = true;
        this.f415p = charSequence;
        if (!this.f412m) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            z2 = false;
        }
        this.f414o = z2;
        ah.m2827q(this.f401b).m3027b();
        if (this.f414o) {
            this.f401b.setText(charSequence);
            this.f401b.setVisibility(0);
            if (z) {
                if (ah.m2806e(this.f401b) == 1.0f) {
                    ah.m2800c(this.f401b, (float) BitmapDescriptorFactory.HUE_RED);
                }
                ah.m2827q(this.f401b).m3020a(1.0f).m3021a(200).m3024a(C0126a.f429d).m3022a(new C01192(this)).m3029c();
            } else {
                ah.m2800c(this.f401b, 1.0f);
            }
        } else if (this.f401b.getVisibility() == 0) {
            if (z) {
                ah.m2827q(this.f401b).m3020a((float) BitmapDescriptorFactory.HUE_RED).m3021a(200).m3024a(C0126a.f428c).m3022a(new bc(this) {
                    /* renamed from: b */
                    final /* synthetic */ TextInputLayout f381b;

                    public void onAnimationEnd(View view) {
                        this.f381b.f401b.setText(charSequence);
                        view.setVisibility(4);
                    }
                }).m3029c();
            } else {
                this.f401b.setText(charSequence);
                this.f401b.setVisibility(4);
            }
        }
        m636d();
        m645a(z);
    }

    /* renamed from: a */
    private static boolean m631a(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    private void m632b() {
        int i;
        LayoutParams layoutParams = (LayoutParams) this.f404e.getLayoutParams();
        if (this.f405f) {
            if (this.f407h == null) {
                this.f407h = new Paint();
            }
            this.f407h.setTypeface(this.f403d.m720b());
            this.f407h.setTextSize(this.f403d.m729e());
            i = (int) (-this.f407h.ascent());
        } else {
            i = 0;
        }
        if (i != layoutParams.topMargin) {
            layoutParams.topMargin = i;
            this.f404e.requestLayout();
        }
    }

    /* renamed from: b */
    private void m633b(boolean z) {
        if (this.f396K != null && this.f396K.m946b()) {
            this.f396K.m949e();
        }
        if (z && this.f395J) {
            m643a(1.0f);
        } else {
            this.f403d.m721b(1.0f);
        }
        this.f394I = false;
    }

    /* renamed from: c */
    private void m634c() {
        ah.m2777a(this.f409j, ah.m2818j(this.f400a), 0, ah.m2820k(this.f400a), this.f400a.getPaddingBottom());
    }

    /* renamed from: c */
    private void m635c(boolean z) {
        if (this.f396K != null && this.f396K.m946b()) {
            this.f396K.m949e();
        }
        if (z && this.f395J) {
            m643a((float) BitmapDescriptorFactory.HUE_RED);
        } else {
            this.f403d.m721b((float) BitmapDescriptorFactory.HUE_RED);
        }
        this.f394I = true;
    }

    /* renamed from: d */
    private void m636d() {
        if (this.f400a != null) {
            Drawable background = this.f400a.getBackground();
            if (background != null) {
                m637e();
                if (ai.m5432b(background)) {
                    background = background.mutate();
                }
                if (this.f414o && this.f401b != null) {
                    background.setColorFilter(C1069l.m5861a(this.f401b.getCurrentTextColor(), Mode.SRC_IN));
                } else if (!this.f420u || this.f416q == null) {
                    C0375a.m1783f(background);
                    this.f400a.refreshDrawableState();
                } else {
                    background.setColorFilter(C1069l.m5861a(this.f416q.getCurrentTextColor(), Mode.SRC_IN));
                }
            }
        }
    }

    /* renamed from: e */
    private void m637e() {
        int i = VERSION.SDK_INT;
        if (i == 21 || i == 22) {
            Drawable background = this.f400a.getBackground();
            if (background != null && !this.f397L) {
                Drawable newDrawable = background.getConstantState().newDrawable();
                if (background instanceof DrawableContainer) {
                    this.f397L = C0151g.m744a((DrawableContainer) background, newDrawable.getConstantState());
                }
                if (!this.f397L) {
                    ah.m2781a(this.f400a, newDrawable);
                    this.f397L = true;
                }
            }
        }
    }

    /* renamed from: f */
    private void m638f() {
        if (this.f400a != null) {
            Drawable[] b;
            if (m640h()) {
                if (this.f424y == null) {
                    this.f424y = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(C0069h.design_text_input_password_icon, this.f404e, false);
                    this.f424y.setImageDrawable(this.f422w);
                    this.f424y.setContentDescription(this.f423x);
                    this.f404e.addView(this.f424y);
                    this.f424y.setOnClickListener(new C01214(this));
                }
                if (this.f400a != null && ah.m2826p(this.f400a) <= 0) {
                    this.f400a.setMinimumHeight(ah.m2826p(this.f424y));
                }
                this.f424y.setVisibility(0);
                this.f424y.setChecked(this.f425z);
                if (this.f386A == null) {
                    this.f386A = new ColorDrawable();
                }
                this.f386A.setBounds(0, 0, this.f424y.getMeasuredWidth(), 1);
                b = C0737z.m3574b(this.f400a);
                if (b[2] != this.f386A) {
                    this.f387B = b[2];
                }
                C0737z.m3573a(this.f400a, b[0], b[1], this.f386A, b[3]);
                this.f424y.setPadding(this.f400a.getPaddingLeft(), this.f400a.getPaddingTop(), this.f400a.getPaddingRight(), this.f400a.getPaddingBottom());
                return;
            }
            if (this.f424y != null && this.f424y.getVisibility() == 0) {
                this.f424y.setVisibility(8);
            }
            if (this.f386A != null) {
                b = C0737z.m3574b(this.f400a);
                if (b[2] == this.f386A) {
                    C0737z.m3573a(this.f400a, b[0], b[1], this.f387B, b[3]);
                    this.f386A = null;
                }
            }
        }
    }

    /* renamed from: g */
    private boolean m639g() {
        return this.f400a != null && (this.f400a.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    /* renamed from: h */
    private boolean m640h() {
        return this.f421v && (m639g() || this.f425z);
    }

    /* renamed from: i */
    private void m641i() {
        if (this.f422w == null) {
            return;
        }
        if (this.f389D || this.f391F) {
            this.f422w = C0375a.m1784g(this.f422w).mutate();
            if (this.f389D) {
                C0375a.m1773a(this.f422w, this.f388C);
            }
            if (this.f391F) {
                C0375a.m1776a(this.f422w, this.f390E);
            }
            if (this.f424y != null && this.f424y.getDrawable() != this.f422w) {
                this.f424y.setImageDrawable(this.f422w);
            }
        }
    }

    private void setEditText(EditText editText) {
        if (this.f400a != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof C0194u)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.f400a = editText;
        if (!m639g()) {
            this.f403d.m716a(this.f400a.getTypeface());
        }
        this.f403d.m711a(this.f400a.getTextSize());
        int gravity = this.f400a.getGravity();
        this.f403d.m722b((gravity & -113) | 48);
        this.f403d.m712a(gravity);
        this.f400a.addTextChangedListener(new C01181(this));
        if (this.f392G == null) {
            this.f392G = this.f400a.getHintTextColors();
        }
        if (this.f405f && TextUtils.isEmpty(this.f406g)) {
            setHint(this.f400a.getHint());
            this.f400a.setHint(null);
        }
        if (this.f416q != null) {
            m644a(this.f400a.getText().length());
        }
        if (this.f409j != null) {
            m634c();
        }
        m638f();
        m646a(false, true);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.f406g = charSequence;
        this.f403d.m718a(charSequence);
    }

    /* renamed from: a */
    void m642a() {
        if (this.f421v) {
            int selectionEnd = this.f400a.getSelectionEnd();
            if (m639g()) {
                this.f400a.setTransformationMethod(null);
                this.f425z = true;
            } else {
                this.f400a.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.f425z = false;
            }
            this.f424y.setChecked(this.f425z);
            this.f400a.setSelection(selectionEnd);
        }
    }

    /* renamed from: a */
    void m643a(float f) {
        if (this.f403d.m728d() != f) {
            if (this.f396K == null) {
                this.f396K = ad.m659a();
                this.f396K.m945a(C0126a.f426a);
                this.f396K.m942a(200);
                this.f396K.m944a(new C01225(this));
            }
            this.f396K.m940a(this.f403d.m728d(), f);
            this.f396K.m939a();
        }
    }

    /* renamed from: a */
    void m644a(int i) {
        boolean z = this.f420u;
        if (this.f417r == -1) {
            this.f416q.setText(String.valueOf(i));
            this.f420u = false;
        } else {
            this.f420u = i > this.f417r;
            if (z != this.f420u) {
                C0737z.m3572a(this.f416q, this.f420u ? this.f419t : this.f418s);
            }
            this.f416q.setText(getContext().getString(C0070i.character_counter_pattern, new Object[]{Integer.valueOf(i), Integer.valueOf(this.f417r)}));
        }
        if (this.f400a != null && z != this.f420u) {
            m645a(false);
            m636d();
        }
    }

    /* renamed from: a */
    void m645a(boolean z) {
        m646a(z, false);
    }

    /* renamed from: a */
    void m646a(boolean z, boolean z2) {
        Object obj = 1;
        boolean isEnabled = isEnabled();
        Object obj2 = (this.f400a == null || TextUtils.isEmpty(this.f400a.getText())) ? null : 1;
        boolean a = m631a(getDrawableState(), 16842908);
        if (TextUtils.isEmpty(getError())) {
            obj = null;
        }
        if (this.f392G != null) {
            this.f403d.m724b(this.f392G);
        }
        if (isEnabled && this.f420u && this.f416q != null) {
            this.f403d.m714a(this.f416q.getTextColors());
        } else if (isEnabled && a && this.f393H != null) {
            this.f403d.m714a(this.f393H);
        } else if (this.f392G != null) {
            this.f403d.m714a(this.f392G);
        }
        if (obj2 != null || (isEnabled() && (a || r1 != null))) {
            if (z2 || this.f394I) {
                m633b(z);
            }
        } else if (z2 || !this.f394I) {
            m635c(z);
        }
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.f404e.addView(view, layoutParams2);
            this.f404e.setLayoutParams(layoutParams);
            m632b();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.f399N = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.f399N = false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.f405f) {
            this.f403d.m715a(canvas);
        }
    }

    protected void drawableStateChanged() {
        boolean z = true;
        if (!this.f398M) {
            this.f398M = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            if (!(ah.m2767G(this) && isEnabled())) {
                z = false;
            }
            m645a(z);
            m636d();
            if ((this.f403d != null ? this.f403d.m719a(drawableState) | 0 : 0) != 0) {
                invalidate();
            }
            this.f398M = false;
        }
    }

    public int getCounterMaxLength() {
        return this.f417r;
    }

    public EditText getEditText() {
        return this.f400a;
    }

    public CharSequence getError() {
        return this.f412m ? this.f415p : null;
    }

    public CharSequence getHint() {
        return this.f405f ? this.f406g : null;
    }

    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.f423x;
    }

    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.f422w;
    }

    public Typeface getTypeface() {
        return this.f411l;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f405f && this.f400a != null) {
            Rect rect = this.f408i;
            C0210z.m991b(this, this.f400a, rect);
            int compoundPaddingLeft = rect.left + this.f400a.getCompoundPaddingLeft();
            int compoundPaddingRight = rect.right - this.f400a.getCompoundPaddingRight();
            this.f403d.m713a(compoundPaddingLeft, rect.top + this.f400a.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.f400a.getCompoundPaddingBottom());
            this.f403d.m723b(compoundPaddingLeft, getPaddingTop(), compoundPaddingRight, (i4 - i2) - getPaddingBottom());
            this.f403d.m730f();
        }
    }

    protected void onMeasure(int i, int i2) {
        m638f();
        super.onMeasure(i, i2);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            setError(savedState.f384a);
            requestLayout();
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        if (this.f414o) {
            savedState.f384a = getError();
        }
        return savedState;
    }

    public void setCounterEnabled(boolean z) {
        if (this.f402c != z) {
            if (z) {
                this.f416q = new C0855y(getContext());
                this.f416q.setId(C0067f.textinput_counter);
                if (this.f411l != null) {
                    this.f416q.setTypeface(this.f411l);
                }
                this.f416q.setMaxLines(1);
                try {
                    C0737z.m3572a(this.f416q, this.f418s);
                } catch (Exception e) {
                    C0737z.m3572a(this.f416q, C0746i.TextAppearance_AppCompat_Caption);
                    this.f416q.setTextColor(C0235a.m1075c(getContext(), C0064c.design_textinput_error_color_light));
                }
                m628a(this.f416q, -1);
                if (this.f400a == null) {
                    m644a(0);
                } else {
                    m644a(this.f400a.getText().length());
                }
            } else {
                m627a(this.f416q);
                this.f416q = null;
            }
            this.f402c = z;
        }
    }

    public void setCounterMaxLength(int i) {
        if (this.f417r != i) {
            if (i > 0) {
                this.f417r = i;
            } else {
                this.f417r = -1;
            }
            if (this.f402c) {
                m644a(this.f400a == null ? 0 : this.f400a.getText().length());
            }
        }
    }

    public void setEnabled(boolean z) {
        m626a((ViewGroup) this, z);
        super.setEnabled(z);
    }

    public void setError(CharSequence charSequence) {
        boolean z = ah.m2767G(this) && isEnabled() && (this.f401b == null || !TextUtils.equals(this.f401b.getText(), charSequence));
        m629a(charSequence, z);
    }

    public void setErrorEnabled(boolean z) {
        if (this.f412m != z) {
            if (this.f401b != null) {
                ah.m2827q(this.f401b).m3027b();
            }
            if (z) {
                int i;
                this.f401b = new C0855y(getContext());
                this.f401b.setId(C0067f.textinput_error);
                if (this.f411l != null) {
                    this.f401b.setTypeface(this.f411l);
                }
                try {
                    C0737z.m3572a(this.f401b, this.f413n);
                    if (VERSION.SDK_INT < 23 || this.f401b.getTextColors().getDefaultColor() != -65281) {
                        boolean z2 = false;
                    } else {
                        i = 1;
                    }
                } catch (Exception e) {
                    i = 1;
                }
                if (i != 0) {
                    C0737z.m3572a(this.f401b, C0746i.TextAppearance_AppCompat_Caption);
                    this.f401b.setTextColor(C0235a.m1075c(getContext(), C0064c.design_textinput_error_color_light));
                }
                this.f401b.setVisibility(4);
                ah.m2805d(this.f401b, 1);
                m628a(this.f401b, 0);
            } else {
                this.f414o = false;
                m636d();
                m627a(this.f401b);
                this.f401b = null;
            }
            this.f412m = z;
        }
    }

    public void setErrorTextAppearance(int i) {
        this.f413n = i;
        if (this.f401b != null) {
            C0737z.m3572a(this.f401b, i);
        }
    }

    public void setHint(CharSequence charSequence) {
        if (this.f405f) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean z) {
        this.f395J = z;
    }

    public void setHintEnabled(boolean z) {
        if (z != this.f405f) {
            this.f405f = z;
            CharSequence hint = this.f400a.getHint();
            if (!this.f405f) {
                if (!TextUtils.isEmpty(this.f406g) && TextUtils.isEmpty(hint)) {
                    this.f400a.setHint(this.f406g);
                }
                setHintInternal(null);
            } else if (!TextUtils.isEmpty(hint)) {
                if (TextUtils.isEmpty(this.f406g)) {
                    setHint(hint);
                }
                this.f400a.setHint(null);
            }
            if (this.f400a != null) {
                m632b();
            }
        }
    }

    public void setHintTextAppearance(int i) {
        this.f403d.m726c(i);
        this.f393H = this.f403d.m732h();
        if (this.f400a != null) {
            m645a(false);
            m632b();
        }
    }

    public void setPasswordVisibilityToggleContentDescription(int i) {
        setPasswordVisibilityToggleContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.f423x = charSequence;
        if (this.f424y != null) {
            this.f424y.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(int i) {
        setPasswordVisibilityToggleDrawable(i != 0 ? C0825b.m3939b(getContext(), i) : null);
    }

    public void setPasswordVisibilityToggleDrawable(Drawable drawable) {
        this.f422w = drawable;
        if (this.f424y != null) {
            this.f424y.setImageDrawable(drawable);
        }
    }

    public void setPasswordVisibilityToggleEnabled(boolean z) {
        if (this.f421v != z) {
            this.f421v = z;
            if (!(z || !this.f425z || this.f400a == null)) {
                this.f400a.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.f425z = false;
            m638f();
        }
    }

    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.f388C = colorStateList;
        this.f389D = true;
        m641i();
    }

    public void setPasswordVisibilityToggleTintMode(Mode mode) {
        this.f390E = mode;
        this.f391F = true;
        m641i();
    }

    public void setTypeface(Typeface typeface) {
        if (typeface != this.f411l) {
            this.f411l = typeface;
            this.f403d.m716a(typeface);
            if (this.f416q != null) {
                this.f416q.setTypeface(typeface);
            }
            if (this.f401b != null) {
                this.f401b.setTypeface(typeface);
            }
        }
    }
}
