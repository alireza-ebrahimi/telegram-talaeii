package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.design.C0073a.C0065d;
import android.support.design.C0073a.C0069h;
import android.support.design.C0073a.C0071j;
import android.support.design.C0073a.C0072k;
import android.support.design.widget.C0201w.C0083c;
import android.support.design.widget.C0201w.C0154a;
import android.support.design.widget.C0201w.C0155b;
import android.support.v4.p022f.C0481j.C0478a;
import android.support.v4.p022f.C0481j.C0479b;
import android.support.v4.p022f.C0481j.C0480c;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.C0180e;
import android.support.v4.view.ViewPager.C0188f;
import android.support.v4.view.ViewPager.C0490a;
import android.support.v4.view.aa;
import android.support.v4.view.ab;
import android.support.v4.view.ah;
import android.support.v4.widget.C0737z;
import android.support.v7.app.C0765a.C0764c;
import android.support.v7.p025a.C0748a.C0747j;
import android.support.v7.p027c.p028a.C0825b;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@C0490a
/* renamed from: android.support.design.widget.t */
public class C0192t extends HorizontalScrollView {
    /* renamed from: n */
    private static final C0478a<C0187e> f644n = new C0480c(16);
    /* renamed from: A */
    private DataSetObserver f645A;
    /* renamed from: B */
    private C0189f f646B;
    /* renamed from: C */
    private C0181a f647C;
    /* renamed from: D */
    private boolean f648D;
    /* renamed from: E */
    private final C0478a<C0190g> f649E;
    /* renamed from: a */
    int f650a;
    /* renamed from: b */
    int f651b;
    /* renamed from: c */
    int f652c;
    /* renamed from: d */
    int f653d;
    /* renamed from: e */
    int f654e;
    /* renamed from: f */
    ColorStateList f655f;
    /* renamed from: g */
    float f656g;
    /* renamed from: h */
    float f657h;
    /* renamed from: i */
    final int f658i;
    /* renamed from: j */
    int f659j;
    /* renamed from: k */
    int f660k;
    /* renamed from: l */
    int f661l;
    /* renamed from: m */
    ViewPager f662m;
    /* renamed from: o */
    private final ArrayList<C0187e> f663o;
    /* renamed from: p */
    private C0187e f664p;
    /* renamed from: q */
    private final C0186d f665q;
    /* renamed from: r */
    private final int f666r;
    /* renamed from: s */
    private final int f667s;
    /* renamed from: t */
    private final int f668t;
    /* renamed from: u */
    private int f669u;
    /* renamed from: v */
    private C0182b f670v;
    /* renamed from: w */
    private final ArrayList<C0182b> f671w;
    /* renamed from: x */
    private C0182b f672x;
    /* renamed from: y */
    private C0201w f673y;
    /* renamed from: z */
    private aa f674z;

    /* renamed from: android.support.design.widget.t$1 */
    class C01791 implements C0083c {
        /* renamed from: a */
        final /* synthetic */ C0192t f605a;

        C01791(C0192t c0192t) {
            this.f605a = c0192t;
        }

        /* renamed from: a */
        public void mo58a(C0201w c0201w) {
            this.f605a.scrollTo(c0201w.m947c(), 0);
        }
    }

    /* renamed from: android.support.design.widget.t$a */
    private class C0181a implements C0180e {
        /* renamed from: a */
        final /* synthetic */ C0192t f606a;
        /* renamed from: b */
        private boolean f607b;

        C0181a(C0192t c0192t) {
            this.f606a = c0192t;
        }

        /* renamed from: a */
        public void mo164a(ViewPager viewPager, aa aaVar, aa aaVar2) {
            if (this.f606a.f662m == viewPager) {
                this.f606a.m908a(aaVar2, this.f607b);
            }
        }

        /* renamed from: a */
        void m847a(boolean z) {
            this.f607b = z;
        }
    }

    /* renamed from: android.support.design.widget.t$b */
    public interface C0182b {
        void onTabReselected(C0187e c0187e);

        void onTabSelected(C0187e c0187e);

        void onTabUnselected(C0187e c0187e);
    }

    /* renamed from: android.support.design.widget.t$c */
    private class C0183c extends DataSetObserver {
        /* renamed from: a */
        final /* synthetic */ C0192t f608a;

        C0183c(C0192t c0192t) {
            this.f608a = c0192t;
        }

        public void onChanged() {
            this.f608a.m915c();
        }

        public void onInvalidated() {
            this.f608a.m915c();
        }
    }

    /* renamed from: android.support.design.widget.t$d */
    private class C0186d extends LinearLayout {
        /* renamed from: a */
        int f616a = -1;
        /* renamed from: b */
        float f617b;
        /* renamed from: c */
        final /* synthetic */ C0192t f618c;
        /* renamed from: d */
        private int f619d;
        /* renamed from: e */
        private final Paint f620e;
        /* renamed from: f */
        private int f621f = -1;
        /* renamed from: g */
        private int f622g = -1;
        /* renamed from: h */
        private C0201w f623h;

        C0186d(C0192t c0192t, Context context) {
            this.f618c = c0192t;
            super(context);
            setWillNotDraw(false);
            this.f620e = new Paint();
        }

        /* renamed from: c */
        private void m850c() {
            int i;
            int i2;
            View childAt = getChildAt(this.f616a);
            if (childAt == null || childAt.getWidth() <= 0) {
                i = -1;
                i2 = -1;
            } else {
                i2 = childAt.getLeft();
                i = childAt.getRight();
                if (this.f617b > BitmapDescriptorFactory.HUE_RED && this.f616a < getChildCount() - 1) {
                    View childAt2 = getChildAt(this.f616a + 1);
                    i2 = (int) ((((float) i2) * (1.0f - this.f617b)) + (this.f617b * ((float) childAt2.getLeft())));
                    i = (int) ((((float) i) * (1.0f - this.f617b)) + (((float) childAt2.getRight()) * this.f617b));
                }
            }
            m853a(i2, i);
        }

        /* renamed from: a */
        void m851a(int i) {
            if (this.f620e.getColor() != i) {
                this.f620e.setColor(i);
                ah.m2799c(this);
            }
        }

        /* renamed from: a */
        void m852a(int i, float f) {
            if (this.f623h != null && this.f623h.m946b()) {
                this.f623h.m949e();
            }
            this.f616a = i;
            this.f617b = f;
            m850c();
        }

        /* renamed from: a */
        void m853a(int i, int i2) {
            if (i != this.f621f || i2 != this.f622g) {
                this.f621f = i;
                this.f622g = i2;
                ah.m2799c(this);
            }
        }

        /* renamed from: a */
        boolean m854a() {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (getChildAt(i).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* renamed from: b */
        float m855b() {
            return ((float) this.f616a) + this.f617b;
        }

        /* renamed from: b */
        void m856b(int i) {
            if (this.f619d != i) {
                this.f619d = i;
                ah.m2799c(this);
            }
        }

        /* renamed from: b */
        void m857b(final int i, int i2) {
            if (this.f623h != null && this.f623h.m946b()) {
                this.f623h.m949e();
            }
            Object obj = ah.m2812g(this) == 1 ? 1 : null;
            View childAt = getChildAt(i);
            if (childAt == null) {
                m850c();
                return;
            }
            int i3;
            int i4;
            final int left = childAt.getLeft();
            final int right = childAt.getRight();
            if (Math.abs(i - this.f616a) <= 1) {
                i3 = this.f621f;
                i4 = this.f622g;
            } else {
                int b = this.f618c.m910b(24);
                if (i < this.f616a) {
                    if (obj != null) {
                        i4 = left - b;
                        i3 = i4;
                    } else {
                        i4 = right + b;
                        i3 = i4;
                    }
                } else if (obj != null) {
                    i4 = right + b;
                    i3 = i4;
                } else {
                    i4 = left - b;
                    i3 = i4;
                }
            }
            if (i3 != left || i4 != right) {
                C0201w a = ad.m659a();
                this.f623h = a;
                a.m945a(C0126a.f427b);
                a.m942a((long) i2);
                a.m940a((float) BitmapDescriptorFactory.HUE_RED, 1.0f);
                a.m944a(new C0083c(this) {
                    /* renamed from: e */
                    final /* synthetic */ C0186d f613e;

                    /* renamed from: a */
                    public void mo58a(C0201w c0201w) {
                        float f = c0201w.m950f();
                        this.f613e.m853a(C0126a.m648a(i3, left, f), C0126a.m648a(i4, right, f));
                    }
                });
                a.m943a(new C0155b(this) {
                    /* renamed from: b */
                    final /* synthetic */ C0186d f615b;

                    /* renamed from: b */
                    public void mo141b(C0201w c0201w) {
                        this.f615b.f616a = i;
                        this.f615b.f617b = BitmapDescriptorFactory.HUE_RED;
                    }
                });
                a.m939a();
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.f621f >= 0 && this.f622g > this.f621f) {
                canvas.drawRect((float) this.f621f, (float) (getHeight() - this.f619d), (float) this.f622g, (float) getHeight(), this.f620e);
            }
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (this.f623h == null || !this.f623h.m946b()) {
                m850c();
                return;
            }
            this.f623h.m949e();
            m857b(this.f616a, Math.round(((float) this.f623h.m952h()) * (1.0f - this.f623h.m950f())));
        }

        protected void onMeasure(int i, int i2) {
            boolean z = false;
            super.onMeasure(i, i2);
            if (MeasureSpec.getMode(i) == 1073741824 && this.f618c.f661l == 1 && this.f618c.f660k == 1) {
                int childCount = getChildCount();
                int i3 = 0;
                int i4 = 0;
                while (i3 < childCount) {
                    View childAt = getChildAt(i3);
                    i3++;
                    i4 = childAt.getVisibility() == 0 ? Math.max(i4, childAt.getMeasuredWidth()) : i4;
                }
                if (i4 > 0) {
                    if (i4 * childCount <= getMeasuredWidth() - (this.f618c.m910b(16) * 2)) {
                        i3 = 0;
                        while (i3 < childCount) {
                            boolean z2;
                            LayoutParams layoutParams = (LayoutParams) getChildAt(i3).getLayoutParams();
                            if (layoutParams.width == i4 && layoutParams.weight == BitmapDescriptorFactory.HUE_RED) {
                                z2 = z;
                            } else {
                                layoutParams.width = i4;
                                layoutParams.weight = BitmapDescriptorFactory.HUE_RED;
                                z2 = true;
                            }
                            i3++;
                            z = z2;
                        }
                    } else {
                        this.f618c.f660k = 0;
                        this.f618c.m909a(false);
                        z = true;
                    }
                    if (z) {
                        super.onMeasure(i, i2);
                    }
                }
            }
        }
    }

    /* renamed from: android.support.design.widget.t$e */
    public static final class C0187e {
        /* renamed from: a */
        C0192t f624a;
        /* renamed from: b */
        C0190g f625b;
        /* renamed from: c */
        private Object f626c;
        /* renamed from: d */
        private Drawable f627d;
        /* renamed from: e */
        private CharSequence f628e;
        /* renamed from: f */
        private CharSequence f629f;
        /* renamed from: g */
        private int f630g = -1;
        /* renamed from: h */
        private View f631h;

        C0187e() {
        }

        /* renamed from: a */
        public C0187e m858a(int i) {
            return m860a(LayoutInflater.from(this.f625b.getContext()).inflate(i, this.f625b, false));
        }

        /* renamed from: a */
        public C0187e m859a(Drawable drawable) {
            this.f627d = drawable;
            m873i();
            return this;
        }

        /* renamed from: a */
        public C0187e m860a(View view) {
            this.f631h = view;
            m873i();
            return this;
        }

        /* renamed from: a */
        public C0187e m861a(CharSequence charSequence) {
            this.f628e = charSequence;
            m873i();
            return this;
        }

        /* renamed from: a */
        public C0187e m862a(Object obj) {
            this.f626c = obj;
            return this;
        }

        /* renamed from: a */
        public Object m863a() {
            return this.f626c;
        }

        /* renamed from: b */
        public C0187e m864b(CharSequence charSequence) {
            this.f629f = charSequence;
            m873i();
            return this;
        }

        /* renamed from: b */
        public View m865b() {
            return this.f631h;
        }

        /* renamed from: b */
        void m866b(int i) {
            this.f630g = i;
        }

        /* renamed from: c */
        public Drawable m867c() {
            return this.f627d;
        }

        /* renamed from: d */
        public int m868d() {
            return this.f630g;
        }

        /* renamed from: e */
        public CharSequence m869e() {
            return this.f628e;
        }

        /* renamed from: f */
        public void m870f() {
            if (this.f624a == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            this.f624a.m913b(this);
        }

        /* renamed from: g */
        public boolean m871g() {
            if (this.f624a != null) {
                return this.f624a.getSelectedTabPosition() == this.f630g;
            } else {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
        }

        /* renamed from: h */
        public CharSequence m872h() {
            return this.f629f;
        }

        /* renamed from: i */
        void m873i() {
            if (this.f625b != null) {
                this.f625b.m880b();
            }
        }

        /* renamed from: j */
        void m874j() {
            this.f624a = null;
            this.f625b = null;
            this.f626c = null;
            this.f627d = null;
            this.f628e = null;
            this.f629f = null;
            this.f630g = -1;
            this.f631h = null;
        }
    }

    /* renamed from: android.support.design.widget.t$f */
    public static class C0189f implements C0188f {
        /* renamed from: a */
        private final WeakReference<C0192t> f632a;
        /* renamed from: b */
        private int f633b;
        /* renamed from: c */
        private int f634c;

        public C0189f(C0192t c0192t) {
            this.f632a = new WeakReference(c0192t);
        }

        /* renamed from: a */
        void m875a() {
            this.f634c = 0;
            this.f633b = 0;
        }

        public void onPageScrollStateChanged(int i) {
            this.f633b = this.f634c;
            this.f634c = i;
        }

        public void onPageScrolled(int i, float f, int i2) {
            boolean z = false;
            C0192t c0192t = (C0192t) this.f632a.get();
            if (c0192t != null) {
                boolean z2 = this.f634c != 2 || this.f633b == 1;
                if (!(this.f634c == 2 && this.f633b == 0)) {
                    z = true;
                }
                c0192t.m902a(i, f, z2, z);
            }
        }

        public void onPageSelected(int i) {
            C0192t c0192t = (C0192t) this.f632a.get();
            if (c0192t != null && c0192t.getSelectedTabPosition() != i && i < c0192t.getTabCount()) {
                boolean z = this.f634c == 0 || (this.f634c == 2 && this.f633b == 0);
                c0192t.m914b(c0192t.m900a(i), z);
            }
        }
    }

    /* renamed from: android.support.design.widget.t$g */
    class C0190g extends LinearLayout implements OnLongClickListener {
        /* renamed from: a */
        final /* synthetic */ C0192t f635a;
        /* renamed from: b */
        private C0187e f636b;
        /* renamed from: c */
        private TextView f637c;
        /* renamed from: d */
        private ImageView f638d;
        /* renamed from: e */
        private View f639e;
        /* renamed from: f */
        private TextView f640f;
        /* renamed from: g */
        private ImageView f641g;
        /* renamed from: h */
        private int f642h = 2;

        public C0190g(C0192t c0192t, Context context) {
            this.f635a = c0192t;
            super(context);
            if (c0192t.f658i != 0) {
                ah.m2781a((View) this, C0825b.m3939b(context, c0192t.f658i));
            }
            ah.m2777a(this, c0192t.f650a, c0192t.f651b, c0192t.f652c, c0192t.f653d);
            setGravity(17);
            setOrientation(1);
            setClickable(true);
            ah.m2784a((View) this, ab.m2507a(getContext(), 1002));
        }

        /* renamed from: a */
        private float m876a(Layout layout, int i, float f) {
            return layout.getLineWidth(i) * (f / layout.getPaint().getTextSize());
        }

        /* renamed from: a */
        private void m877a(TextView textView, ImageView imageView) {
            Drawable c = this.f636b != null ? this.f636b.m867c() : null;
            CharSequence e = this.f636b != null ? this.f636b.m869e() : null;
            CharSequence h = this.f636b != null ? this.f636b.m872h() : null;
            if (imageView != null) {
                if (c != null) {
                    imageView.setImageDrawable(c);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
                imageView.setContentDescription(h);
            }
            boolean z = !TextUtils.isEmpty(e);
            if (textView != null) {
                if (z) {
                    textView.setText(e);
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText(null);
                }
                textView.setContentDescription(h);
            }
            if (imageView != null) {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) imageView.getLayoutParams();
                int b = (z && imageView.getVisibility() == 0) ? this.f635a.m910b(8) : 0;
                if (b != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = b;
                    imageView.requestLayout();
                }
            }
            if (z || TextUtils.isEmpty(h)) {
                setOnLongClickListener(null);
                setLongClickable(false);
                return;
            }
            setOnLongClickListener(this);
        }

        /* renamed from: a */
        void m878a() {
            m879a(null);
            setSelected(false);
        }

        /* renamed from: a */
        void m879a(C0187e c0187e) {
            if (c0187e != this.f636b) {
                this.f636b = c0187e;
                m880b();
            }
        }

        /* renamed from: b */
        final void m880b() {
            C0187e c0187e = this.f636b;
            View b = c0187e != null ? c0187e.m865b() : null;
            if (b != null) {
                C0190g parent = b.getParent();
                if (parent != this) {
                    if (parent != null) {
                        parent.removeView(b);
                    }
                    addView(b);
                }
                this.f639e = b;
                if (this.f637c != null) {
                    this.f637c.setVisibility(8);
                }
                if (this.f638d != null) {
                    this.f638d.setVisibility(8);
                    this.f638d.setImageDrawable(null);
                }
                this.f640f = (TextView) b.findViewById(16908308);
                if (this.f640f != null) {
                    this.f642h = C0737z.m3571a(this.f640f);
                }
                this.f641g = (ImageView) b.findViewById(16908294);
            } else {
                if (this.f639e != null) {
                    removeView(this.f639e);
                    this.f639e = null;
                }
                this.f640f = null;
                this.f641g = null;
            }
            if (this.f639e == null) {
                if (this.f638d == null) {
                    ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(C0069h.design_layout_tab_icon, this, false);
                    addView(imageView, 0);
                    this.f638d = imageView;
                }
                if (this.f637c == null) {
                    TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(C0069h.design_layout_tab_text, this, false);
                    addView(textView);
                    this.f637c = textView;
                    this.f642h = C0737z.m3571a(this.f637c);
                }
                C0737z.m3572a(this.f637c, this.f635a.f654e);
                if (this.f635a.f655f != null) {
                    this.f637c.setTextColor(this.f635a.f655f);
                }
                m877a(this.f637c, this.f638d);
            } else if (!(this.f640f == null && this.f641g == null)) {
                m877a(this.f640f, this.f641g);
            }
            boolean z = c0187e != null && c0187e.m871g();
            setSelected(z);
        }

        @TargetApi(14)
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(C0764c.class.getName());
        }

        @TargetApi(14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(C0764c.class.getName());
        }

        public boolean onLongClick(View view) {
            int[] iArr = new int[2];
            Rect rect = new Rect();
            getLocationOnScreen(iArr);
            getWindowVisibleDisplayFrame(rect);
            Context context = getContext();
            int width = getWidth();
            int height = getHeight();
            int i = iArr[1] + (height / 2);
            width = (width / 2) + iArr[0];
            if (ah.m2812g(view) == 0) {
                width = context.getResources().getDisplayMetrics().widthPixels - width;
            }
            Toast makeText = Toast.makeText(context, this.f636b.m872h(), 0);
            if (i < rect.height()) {
                makeText.setGravity(8388661, width, (iArr[1] + height) - rect.top);
            } else {
                makeText.setGravity(81, 0, height);
            }
            makeText.show();
            return true;
        }

        public void onMeasure(int i, int i2) {
            int i3 = 1;
            int size = MeasureSpec.getSize(i);
            int mode = MeasureSpec.getMode(i);
            int tabMaxWidth = this.f635a.getTabMaxWidth();
            if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
                i = MeasureSpec.makeMeasureSpec(this.f635a.f659j, Integer.MIN_VALUE);
            }
            super.onMeasure(i, i2);
            if (this.f637c != null) {
                getResources();
                float f = this.f635a.f656g;
                size = this.f642h;
                if (this.f638d != null && this.f638d.getVisibility() == 0) {
                    size = 1;
                } else if (this.f637c != null && this.f637c.getLineCount() > 1) {
                    f = this.f635a.f657h;
                }
                float textSize = this.f637c.getTextSize();
                int lineCount = this.f637c.getLineCount();
                int a = C0737z.m3571a(this.f637c);
                if (f != textSize || (a >= 0 && size != a)) {
                    if (this.f635a.f661l == 1 && f > textSize && lineCount == 1) {
                        Layout layout = this.f637c.getLayout();
                        if (layout == null || m876a(layout, 0, f) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()))) {
                            i3 = 0;
                        }
                    }
                    if (i3 != 0) {
                        this.f637c.setTextSize(0, f);
                        this.f637c.setMaxLines(size);
                        super.onMeasure(i, i2);
                    }
                }
            }
        }

        public boolean performClick() {
            boolean performClick = super.performClick();
            if (this.f636b == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.f636b.m870f();
            return true;
        }

        public void setSelected(boolean z) {
            Object obj = isSelected() != z ? 1 : null;
            super.setSelected(z);
            if (obj != null && z && VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            if (this.f637c != null) {
                this.f637c.setSelected(z);
            }
            if (this.f638d != null) {
                this.f638d.setSelected(z);
            }
            if (this.f639e != null) {
                this.f639e.setSelected(z);
            }
        }
    }

    /* renamed from: android.support.design.widget.t$h */
    public static class C0191h implements C0182b {
        /* renamed from: a */
        private final ViewPager f643a;

        public C0191h(ViewPager viewPager) {
            this.f643a = viewPager;
        }

        public void onTabReselected(C0187e c0187e) {
        }

        public void onTabSelected(C0187e c0187e) {
            this.f643a.setCurrentItem(c0187e.m868d());
        }

        public void onTabUnselected(C0187e c0187e) {
        }
    }

    public C0192t(Context context) {
        this(context, null);
    }

    public C0192t(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public C0192t(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f663o = new ArrayList();
        this.f659j = Integer.MAX_VALUE;
        this.f671w = new ArrayList();
        this.f649E = new C0479b(12);
        C0195v.m916a(context);
        setHorizontalScrollBarEnabled(false);
        this.f665q = new C0186d(this, context);
        super.addView(this.f665q, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0072k.TabLayout, i, C0071j.Widget_Design_TabLayout);
        this.f665q.m856b(obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabIndicatorHeight, 0));
        this.f665q.m851a(obtainStyledAttributes.getColor(C0072k.TabLayout_tabIndicatorColor, 0));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabPadding, 0);
        this.f653d = dimensionPixelSize;
        this.f652c = dimensionPixelSize;
        this.f651b = dimensionPixelSize;
        this.f650a = dimensionPixelSize;
        this.f650a = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabPaddingStart, this.f650a);
        this.f651b = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabPaddingTop, this.f651b);
        this.f652c = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabPaddingEnd, this.f652c);
        this.f653d = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabPaddingBottom, this.f653d);
        this.f654e = obtainStyledAttributes.getResourceId(C0072k.TabLayout_tabTextAppearance, C0071j.TextAppearance_Design_Tab);
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(this.f654e, C0747j.TextAppearance);
        try {
            this.f656g = (float) obtainStyledAttributes2.getDimensionPixelSize(C0747j.TextAppearance_android_textSize, 0);
            this.f655f = obtainStyledAttributes2.getColorStateList(C0747j.TextAppearance_android_textColor);
            if (obtainStyledAttributes.hasValue(C0072k.TabLayout_tabTextColor)) {
                this.f655f = obtainStyledAttributes.getColorStateList(C0072k.TabLayout_tabTextColor);
            }
            if (obtainStyledAttributes.hasValue(C0072k.TabLayout_tabSelectedTextColor)) {
                this.f655f = C0192t.m882a(this.f655f.getDefaultColor(), obtainStyledAttributes.getColor(C0072k.TabLayout_tabSelectedTextColor, 0));
            }
            this.f666r = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabMinWidth, -1);
            this.f667s = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabMaxWidth, -1);
            this.f658i = obtainStyledAttributes.getResourceId(C0072k.TabLayout_tabBackground, 0);
            this.f669u = obtainStyledAttributes.getDimensionPixelSize(C0072k.TabLayout_tabContentStart, 0);
            this.f661l = obtainStyledAttributes.getInt(C0072k.TabLayout_tabMode, 1);
            this.f660k = obtainStyledAttributes.getInt(C0072k.TabLayout_tabGravity, 0);
            obtainStyledAttributes.recycle();
            Resources resources = getResources();
            this.f657h = (float) resources.getDimensionPixelSize(C0065d.design_tab_text_size_2line);
            this.f668t = resources.getDimensionPixelSize(C0065d.design_tab_scrollable_min_width);
            m897g();
        } finally {
            obtainStyledAttributes2.recycle();
        }
    }

    /* renamed from: a */
    private int m881a(int i, float f) {
        int i2 = 0;
        if (this.f661l != 0) {
            return 0;
        }
        View childAt = this.f665q.getChildAt(i);
        View childAt2 = i + 1 < this.f665q.getChildCount() ? this.f665q.getChildAt(i + 1) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i2 = childAt2.getWidth();
        }
        int left = (childAt.getLeft() + (width / 2)) - (getWidth() / 2);
        i2 = (int) ((((float) (i2 + width)) * 0.5f) * f);
        return ah.m2812g(this) == 0 ? i2 + left : left - i2;
    }

    /* renamed from: a */
    private static ColorStateList m882a(int i, int i2) {
        r0 = new int[2][];
        int[] iArr = new int[]{SELECTED_STATE_SET, i2};
        r0[1] = EMPTY_STATE_SET;
        iArr[1] = i;
        return new ColorStateList(r0, iArr);
    }

    /* renamed from: a */
    private void m883a(C0178s c0178s) {
        C0187e a = m899a();
        if (c0178s.f602a != null) {
            a.m861a(c0178s.f602a);
        }
        if (c0178s.f603b != null) {
            a.m859a(c0178s.f603b);
        }
        if (c0178s.f604c != 0) {
            a.m858a(c0178s.f604c);
        }
        if (!TextUtils.isEmpty(c0178s.getContentDescription())) {
            a.m864b(c0178s.getContentDescription());
        }
        m904a(a);
    }

    /* renamed from: a */
    private void m884a(C0187e c0187e, int i) {
        c0187e.m866b(i);
        this.f663o.add(i, c0187e);
        int size = this.f663o.size();
        for (int i2 = i + 1; i2 < size; i2++) {
            ((C0187e) this.f663o.get(i2)).m866b(i2);
        }
    }

    /* renamed from: a */
    private void m885a(ViewPager viewPager, boolean z, boolean z2) {
        if (this.f662m != null) {
            if (this.f646B != null) {
                this.f662m.removeOnPageChangeListener(this.f646B);
            }
            if (this.f647C != null) {
                this.f662m.removeOnAdapterChangeListener(this.f647C);
            }
        }
        if (this.f672x != null) {
            m912b(this.f672x);
            this.f672x = null;
        }
        if (viewPager != null) {
            this.f662m = viewPager;
            if (this.f646B == null) {
                this.f646B = new C0189f(this);
            }
            this.f646B.m875a();
            viewPager.addOnPageChangeListener(this.f646B);
            this.f672x = new C0191h(viewPager);
            m903a(this.f672x);
            aa adapter = viewPager.getAdapter();
            if (adapter != null) {
                m908a(adapter, z);
            }
            if (this.f647C == null) {
                this.f647C = new C0181a(this);
            }
            this.f647C.m847a(z);
            viewPager.addOnAdapterChangeListener(this.f647C);
            m901a(viewPager.getCurrentItem(), (float) BitmapDescriptorFactory.HUE_RED, true);
        } else {
            this.f662m = null;
            m908a(null, false);
        }
        this.f648D = z2;
    }

    /* renamed from: a */
    private void m886a(View view) {
        if (view instanceof C0178s) {
            m883a((C0178s) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    /* renamed from: a */
    private void m887a(LayoutParams layoutParams) {
        if (this.f661l == 1 && this.f660k == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = BitmapDescriptorFactory.HUE_RED;
    }

    /* renamed from: c */
    private C0190g m888c(C0187e c0187e) {
        C0190g c0190g = this.f649E != null ? (C0190g) this.f649E.mo332a() : null;
        if (c0190g == null) {
            c0190g = new C0190g(this, getContext());
        }
        c0190g.m879a(c0187e);
        c0190g.setFocusable(true);
        c0190g.setMinimumWidth(getTabMinWidth());
        return c0190g;
    }

    /* renamed from: c */
    private void m889c(int i) {
        C0190g c0190g = (C0190g) this.f665q.getChildAt(i);
        this.f665q.removeViewAt(i);
        if (c0190g != null) {
            c0190g.m878a();
            this.f649E.mo333a(c0190g);
        }
        requestLayout();
    }

    /* renamed from: d */
    private void m890d() {
        int size = this.f663o.size();
        for (int i = 0; i < size; i++) {
            ((C0187e) this.f663o.get(i)).m873i();
        }
    }

    /* renamed from: d */
    private void m891d(int i) {
        if (i != -1) {
            if (getWindowToken() == null || !ah.m2767G(this) || this.f665q.m854a()) {
                m901a(i, (float) BitmapDescriptorFactory.HUE_RED, true);
                return;
            }
            int scrollX = getScrollX();
            int a = m881a(i, (float) BitmapDescriptorFactory.HUE_RED);
            if (scrollX != a) {
                m895f();
                this.f673y.m941a(scrollX, a);
                this.f673y.m939a();
            }
            this.f665q.m857b(i, 300);
        }
    }

    /* renamed from: d */
    private void m892d(C0187e c0187e) {
        this.f665q.addView(c0187e.f625b, c0187e.m868d(), m893e());
    }

    /* renamed from: e */
    private LayoutParams m893e() {
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        m887a(layoutParams);
        return layoutParams;
    }

    /* renamed from: e */
    private void m894e(C0187e c0187e) {
        for (int size = this.f671w.size() - 1; size >= 0; size--) {
            ((C0182b) this.f671w.get(size)).onTabSelected(c0187e);
        }
    }

    /* renamed from: f */
    private void m895f() {
        if (this.f673y == null) {
            this.f673y = ad.m659a();
            this.f673y.m945a(C0126a.f427b);
            this.f673y.m942a(300);
            this.f673y.m944a(new C01791(this));
        }
    }

    /* renamed from: f */
    private void m896f(C0187e c0187e) {
        for (int size = this.f671w.size() - 1; size >= 0; size--) {
            ((C0182b) this.f671w.get(size)).onTabUnselected(c0187e);
        }
    }

    /* renamed from: g */
    private void m897g() {
        ah.m2777a(this.f665q, this.f661l == 0 ? Math.max(0, this.f669u - this.f650a) : 0, 0, 0, 0);
        switch (this.f661l) {
            case 0:
                this.f665q.setGravity(8388611);
                break;
            case 1:
                this.f665q.setGravity(1);
                break;
        }
        m909a(true);
    }

    /* renamed from: g */
    private void m898g(C0187e c0187e) {
        for (int size = this.f671w.size() - 1; size >= 0; size--) {
            ((C0182b) this.f671w.get(size)).onTabReselected(c0187e);
        }
    }

    private int getDefaultHeight() {
        Object obj;
        int size = this.f663o.size();
        for (int i = 0; i < size; i++) {
            C0187e c0187e = (C0187e) this.f663o.get(i);
            if (c0187e != null && c0187e.m867c() != null && !TextUtils.isEmpty(c0187e.m869e())) {
                obj = 1;
                break;
            }
        }
        obj = null;
        return obj != null ? 72 : 48;
    }

    private float getScrollPosition() {
        return this.f665q.m855b();
    }

    private int getTabMinWidth() {
        return this.f666r != -1 ? this.f666r : this.f661l == 0 ? this.f668t : 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.f665q.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    private void setSelectedTabView(int i) {
        int childCount = this.f665q.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (i2 < childCount) {
                this.f665q.getChildAt(i2).setSelected(i2 == i);
                i2++;
            }
        }
    }

    /* renamed from: a */
    public C0187e m899a() {
        C0187e c0187e = (C0187e) f644n.mo332a();
        if (c0187e == null) {
            c0187e = new C0187e();
        }
        c0187e.f624a = this;
        c0187e.f625b = m888c(c0187e);
        return c0187e;
    }

    /* renamed from: a */
    public C0187e m900a(int i) {
        return (i < 0 || i >= getTabCount()) ? null : (C0187e) this.f663o.get(i);
    }

    /* renamed from: a */
    public void m901a(int i, float f, boolean z) {
        m902a(i, f, z, true);
    }

    /* renamed from: a */
    void m902a(int i, float f, boolean z, boolean z2) {
        int round = Math.round(((float) i) + f);
        if (round >= 0 && round < this.f665q.getChildCount()) {
            if (z2) {
                this.f665q.m852a(i, f);
            }
            if (this.f673y != null && this.f673y.m946b()) {
                this.f673y.m949e();
            }
            scrollTo(m881a(i, f), 0);
            if (z) {
                setSelectedTabView(round);
            }
        }
    }

    /* renamed from: a */
    public void m903a(C0182b c0182b) {
        if (!this.f671w.contains(c0182b)) {
            this.f671w.add(c0182b);
        }
    }

    /* renamed from: a */
    public void m904a(C0187e c0187e) {
        m906a(c0187e, this.f663o.isEmpty());
    }

    /* renamed from: a */
    public void m905a(C0187e c0187e, int i, boolean z) {
        if (c0187e.f624a != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        m884a(c0187e, i);
        m892d(c0187e);
        if (z) {
            c0187e.m870f();
        }
    }

    /* renamed from: a */
    public void m906a(C0187e c0187e, boolean z) {
        m905a(c0187e, this.f663o.size(), z);
    }

    /* renamed from: a */
    public void m907a(ViewPager viewPager, boolean z) {
        m885a(viewPager, z, false);
    }

    /* renamed from: a */
    void m908a(aa aaVar, boolean z) {
        if (!(this.f674z == null || this.f645A == null)) {
            this.f674z.unregisterDataSetObserver(this.f645A);
        }
        this.f674z = aaVar;
        if (z && aaVar != null) {
            if (this.f645A == null) {
                this.f645A = new C0183c(this);
            }
            aaVar.registerDataSetObserver(this.f645A);
        }
        m915c();
    }

    /* renamed from: a */
    void m909a(boolean z) {
        for (int i = 0; i < this.f665q.getChildCount(); i++) {
            View childAt = this.f665q.getChildAt(i);
            childAt.setMinimumWidth(getTabMinWidth());
            m887a((LayoutParams) childAt.getLayoutParams());
            if (z) {
                childAt.requestLayout();
            }
        }
    }

    public void addView(View view) {
        m886a(view);
    }

    public void addView(View view, int i) {
        m886a(view);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        m886a(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        m886a(view);
    }

    /* renamed from: b */
    int m910b(int i) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) i));
    }

    /* renamed from: b */
    public void m911b() {
        for (int childCount = this.f665q.getChildCount() - 1; childCount >= 0; childCount--) {
            m889c(childCount);
        }
        Iterator it = this.f663o.iterator();
        while (it.hasNext()) {
            C0187e c0187e = (C0187e) it.next();
            it.remove();
            c0187e.m874j();
            f644n.mo333a(c0187e);
        }
        this.f664p = null;
    }

    /* renamed from: b */
    public void m912b(C0182b c0182b) {
        this.f671w.remove(c0182b);
    }

    /* renamed from: b */
    void m913b(C0187e c0187e) {
        m914b(c0187e, true);
    }

    /* renamed from: b */
    void m914b(C0187e c0187e, boolean z) {
        C0187e c0187e2 = this.f664p;
        if (c0187e2 != c0187e) {
            int d = c0187e != null ? c0187e.m868d() : -1;
            if (z) {
                if ((c0187e2 == null || c0187e2.m868d() == -1) && d != -1) {
                    m901a(d, (float) BitmapDescriptorFactory.HUE_RED, true);
                } else {
                    m891d(d);
                }
                if (d != -1) {
                    setSelectedTabView(d);
                }
            }
            if (c0187e2 != null) {
                m896f(c0187e2);
            }
            this.f664p = c0187e;
            if (c0187e != null) {
                m894e(c0187e);
            }
        } else if (c0187e2 != null) {
            m898g(c0187e);
            m891d(c0187e.m868d());
        }
    }

    /* renamed from: c */
    void m915c() {
        m911b();
        if (this.f674z != null) {
            int i;
            int count = this.f674z.getCount();
            for (i = 0; i < count; i++) {
                m906a(m899a().m861a(this.f674z.getPageTitle(i)), false);
            }
            if (this.f662m != null && count > 0) {
                i = this.f662m.getCurrentItem();
                if (i != getSelectedTabPosition() && i < getTabCount()) {
                    m913b(m900a(i));
                }
            }
        }
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        return this.f664p != null ? this.f664p.m868d() : -1;
    }

    public int getTabCount() {
        return this.f663o.size();
    }

    public int getTabGravity() {
        return this.f660k;
    }

    int getTabMaxWidth() {
        return this.f659j;
    }

    public int getTabMode() {
        return this.f661l;
    }

    public ColorStateList getTabTextColors() {
        return this.f655f;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f662m == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                m885a((ViewPager) parent, true, true);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f648D) {
            setupWithViewPager(null);
            this.f648D = false;
        }
    }

    protected void onMeasure(int i, int i2) {
        int i3 = 1;
        int b = (m910b(getDefaultHeight()) + getPaddingTop()) + getPaddingBottom();
        switch (MeasureSpec.getMode(i2)) {
            case Integer.MIN_VALUE:
                i2 = MeasureSpec.makeMeasureSpec(Math.min(b, MeasureSpec.getSize(i2)), 1073741824);
                break;
            case 0:
                i2 = MeasureSpec.makeMeasureSpec(b, 1073741824);
                break;
        }
        b = MeasureSpec.getSize(i);
        if (MeasureSpec.getMode(i) != 0) {
            this.f659j = this.f667s > 0 ? this.f667s : b - m910b(56);
        }
        super.onMeasure(i, i2);
        if (getChildCount() == 1) {
            View childAt = getChildAt(0);
            switch (this.f661l) {
                case 0:
                    if (childAt.getMeasuredWidth() >= getMeasuredWidth()) {
                        b = 0;
                        break;
                    } else {
                        b = 1;
                        break;
                    }
                case 1:
                    if (childAt.getMeasuredWidth() == getMeasuredWidth()) {
                        i3 = 0;
                    }
                    b = i3;
                    break;
                default:
                    b = 0;
                    break;
            }
            if (b != 0) {
                childAt.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), C0192t.getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), childAt.getLayoutParams().height));
            }
        }
    }

    @Deprecated
    public void setOnTabSelectedListener(C0182b c0182b) {
        if (this.f670v != null) {
            m912b(this.f670v);
        }
        this.f670v = c0182b;
        if (c0182b != null) {
            m903a(c0182b);
        }
    }

    void setScrollAnimatorListener(C0154a c0154a) {
        m895f();
        this.f673y.m943a(c0154a);
    }

    public void setSelectedTabIndicatorColor(int i) {
        this.f665q.m851a(i);
    }

    public void setSelectedTabIndicatorHeight(int i) {
        this.f665q.m856b(i);
    }

    public void setTabGravity(int i) {
        if (this.f660k != i) {
            this.f660k = i;
            m897g();
        }
    }

    public void setTabMode(int i) {
        if (i != this.f661l) {
            this.f661l = i;
            m897g();
        }
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.f655f != colorStateList) {
            this.f655f = colorStateList;
            m890d();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(aa aaVar) {
        m908a(aaVar, false);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        m907a(viewPager, true);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }
}
